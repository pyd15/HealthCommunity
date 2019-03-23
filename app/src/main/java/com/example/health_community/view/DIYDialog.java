package com.example.health_community.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.health_community.R;
import com.example.health_community.activity.BottomNavigationBarActivity;
import com.example.health_community.adapter.mutiLevelList.TreeAdapter;
import com.example.health_community.adapter.mutiLevelList.TreePoint;
import com.example.health_community.model.AppointMent;
import com.example.health_community.model.BigDepartment;
import com.example.health_community.model.Department;
import com.example.health_community.util.Constant;
import com.example.health_community.util.HttpAction;
import com.example.health_community.util.HttpUtil;
import com.example.health_community.util.JSonUtil;
import com.example.health_community.util.SPUtils;
import com.example.health_community.util.TreeUtils;
import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

import org.json.JSONException;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/18
 * desc:
 */
public class DIYDialog {
    View dialogView;
    Context context;
    Activity activity;
    private TreeAdapter adapter;
    private ListView listView;
    private EditText et_filter;
    private String[] appoint_date;
    private String[] hospital_big_department, hospital_internal, hospital_obstetrics_gynecology, hospital_orthopedics, hospital_oncology, hospital_surgery, hospital_subsidiary;
    List<BigDepartment> bigDepartments;
    List<Department> departments;
    List<AppointMent> appointMents;
    private List<TreePoint> pointList = new ArrayList<>();
    private HashMap<String, TreePoint> pointMap = new HashMap<>();
    CircleProgressDialog circleProgressDialog;
    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
    public static final String action = "jason.broadcast.action";

    //    Handler handler;

    private static final int REQUEST_COMPELETE = 1;

    //    public void initHandler() {
    //        Looper.prepare();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_COMPELETE:
                    initData();
                    break;
                default:
                    break;
            }
        }
    };
    private String hos_name;
    private BottomSheetDialog mBottomSheetDialog;
    private String date;
    private String responseString;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    //    }

    public void create(Activity activity, Context context, List<BigDepartment> bigDepartmentList) {
        //    public void create(Activity activity, Context context) {
        mBottomSheetDialog = new BottomSheetDialog(context);
        //        dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
        dialogView = activity.getLayoutInflater().inflate(R.layout.activity_self_appointment, null);
        this.context = context;
        this.activity = activity;
        this.bigDepartments = bigDepartmentList;
        init();
        addListener();
        Button btn_dialog_bottom_sheet_ok = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
        Button btn_dialog_bottom_sheet_cancel = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
        Button btn_back = dialogView.findViewById(R.id.btn_back);
        Button btn_submit = dialogView.findViewById(R.id.btn_submit);
        Button btn_pick_date = dialogView.findViewById(R.id.pick_date);

        ImageView img_bottom_dialog = dialogView.findViewById(R.id.img_bottom_dialog);
        //                    Glide.with(context).load(R.drawable.bottom_dialog).into(img_bottom_dialog);
        mBottomSheetDialog.setContentView(dialogView);

        btn_dialog_bottom_sheet_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        btn_dialog_bottom_sheet_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        btn_pick_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                calendar.setTime(new Date()); //需要将date数据转移到Calender对象中操作
                calendar.add(calendar.DATE, 7);//把日期往后增加n天.正数往后推,负数往前移动
//                calendar.getTime();   //这个时间就是日期往后推一天的结果

                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                        Log.e("date", date);
                        try {
                            Date bt = df.parse(date);
                            //            Date et = df.parse(end);et
                            Date et = new Date();
                            if (bt.before(et)) {
                                System.out.println(bt + "zao");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTime().getTime());
                datePickerDialog.show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppointMent appointMent = adapter.getAppointMent();
                if (date == null) {
                    Toast.makeText(context, "请选择日期！", Toast.LENGTH_SHORT).show();
                } else if (appointMent.getBig_depart_name() == null)
                    Toast.makeText(context, "请选择科室！", Toast.LENGTH_SHORT).show();
                else {
                    circleProgressDialog.showDialog();
                    appointMents = adapter.getAppointMents();
                    date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
                    Log.e("date", date);
                    for (int i = 0; i < appointMents.size(); i++) {
                        date = date + " " + appointMent.getAppoint_date();
                        Log.e("date", date);
                        appointMents.get(i).setAppoint_date(date);
                        Log.e("appointment", appointMents.get(i).toString());
                        //                        appointMent.save();
                    }
                    LitePal.saveAll(appointMents);
                    String json = JSonUtil.listToJsonGson(appointMents);
                    Log.e("appoint_json", json);
                    HttpUtil.sendOkHttpPostRequest(Constant.INSERT_APPOINTS_URL, json, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("appoint_response", "失败");
                            for (int i = 0; i < appointMents.size(); i++) {
                                appointMent.delete();
                            }
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            responseString = response.body().string();
                            Log.e("appoint_response", responseString);
                            if (responseString.equals("success_inserts")) {
                                SPUtils.setPrefInt(Constant.APPOINT_NUM, appointMents.size());
                                circleProgressDialog.dismiss();
                                mBottomSheetDialog.dismiss();
                                Intent intent = new Intent(action);
                                intent.putExtra(Constant.APPOINT_NUM, appointMents.size());
                                context.sendBroadcast(intent);
                                activity.finish();
                                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        }
                    });
                }
            }
        });
        circleProgressDialog = new CircleProgressDialog(context);
        circleProgressDialog.setText("预约中...");
        circleProgressDialog.setTextColor(Color.WHITE);
        mBottomSheetDialog.setTitle("预约挂号");
        mBottomSheetDialog.show();
        //        initHandler();
    }

    public void init() {
        adapter = new TreeAdapter(context, pointList, pointMap);
        adapter.setHospital(hos_name);
        listView = dialogView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        et_filter = dialogView.findViewById(R.id.et_filter);

        //        activity.runOnUiThread(new Runnable() {
        //            @Override
        //            public void run() {
        //        requestDepartments();
        //            }
        //        });
        //        stopForeground(true);
        //        getNotificationManager().notify(1, getNotification("下载成功!", -1));
        initData();
    }

    private void requestDepartments() {
        HttpUtil.sendOkHttpGetRequest("https://www.pydwp.xyz/JSoupDemo/hospital/getHospitalAndDepartments.do?hos_name=广州协佳医院", new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    bigDepartments = HttpAction.parseJSONWithGSON(responseData);
                    Message message = new Message();
                    message.what = REQUEST_COMPELETE;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void setHospital(String hos_name) {
        this.hos_name = hos_name;
    }

    //初始化数据
    //数据特点：TreePoint 之间的关系特点   id是任意唯一的。    如果为根节点 PARENTID  为"0"   如果没有子节点，也就是本身是叶子节点的时候ISLEAF = "1"
    //  DISPLAY_ORDER 是同一级中 显示的顺序
    //如果需要做选中 单选或者多选，只需要给TreePoint增加一个选中的属性，在ReasonAdapter中处理就好了
    private void initData() {
        hospital_big_department = context.getResources().getStringArray(R.array.hospital_big_department);
        hospital_internal = context.getResources().getStringArray(R.array.hospital_internal);
        hospital_obstetrics_gynecology = context.getResources().getStringArray(R.array.hospital_obstetrics_gynecology);
        hospital_orthopedics = context.getResources().getStringArray(R.array.hospital_orthopedics);
        hospital_oncology = context.getResources().getStringArray(R.array.hospital_oncology);
        hospital_surgery = context.getResources().getStringArray(R.array.hospital_surgery);
        hospital_subsidiary = context.getResources().getStringArray(R.array.hospital_subsidiary);
        int[] departs_num = {hospital_internal.length, hospital_obstetrics_gynecology.length,
                hospital_orthopedics.length, hospital_oncology.length, hospital_surgery.length, hospital_subsidiary.length};
        appoint_date = context.getResources().getStringArray(R.array.appoint_date);
        pointList.clear();
        int id = 1000;
        int parentId = 0;
        int parentId2 = 0;
        int parentId3 = 0;
        for (int i = 1; i <= hospital_big_department.length; i++) {
            id++;
            pointList.add(new TreePoint("" + id, hospital_big_department[i - 1], "" + parentId, "0", i));
            for (int j = 1; j <= departs_num[i - 1]; j++) {
                if (j == 1) {
                    parentId2 = id;
                }
                id++;
                switch (i) {
                    case 1:
                        pointList.add(new TreePoint("" + id, hospital_internal[j - 1], "" + parentId2, "0", j));
                        break;
                    case 2:
                        pointList.add(new TreePoint("" + id, hospital_obstetrics_gynecology[j - 1], "" + parentId2, "0", j));
                        break;
                    case 3:
                        pointList.add(new TreePoint("" + id, hospital_orthopedics[j - 1], "" + parentId2, "0", j));
                        break;
                    case 4:
                        pointList.add(new TreePoint("" + id, hospital_oncology[j - 1], "" + parentId2, "0", j));
                        break;
                    case 5:
                        pointList.add(new TreePoint("" + id, hospital_surgery[j - 1], "" + parentId2, "0", j));
                        break;
                    case 6:
                        pointList.add(new TreePoint("" + id, hospital_subsidiary[j - 1], "" + parentId2, "0", j));
                        break;
                    default:
                        break;
                }
                for (int k = 1; k <= appoint_date.length; k++) {
                    if (k == 1) {
                        parentId3 = id;
                    }
                    id++;
                    pointList.add(new TreePoint("" + id, appoint_date[k - 1], "" + parentId3, "1", k));
                }
            }
        }
        //        for (int i = 1; i <= bigDepartments.size(); i++) {
        //            id++;
        //            pointList.add(new TreePoint("" + id, bigDepartments.get(i - 1).getBig_depart_name(), "" + parentId, "0", i));
        //            for (int j = 1; j <= bigDepartments.get(i - 1).getDepartments().size(); j++) {
        //                if (j == 1) {
        //                    parentId2 = id;
        //                }
        //                id++;
        //                departments = bigDepartments.get(i - 1).getDepartments();
        //                pointList.add(new TreePoint("" + id, departments.get(j - 1).getDepart_name(), "" + parentId2, "0", j));
        //                for(int k=1;k<=appoint_date.length;k++){
        //                    if (k == 1) {
        //                        parentId3 = id;
        //                    }
        //                    id++;
        //                    pointList.add(new TreePoint("" + id, appoint_date[k-1], "" + parentId3, "1", k));
        //                                    }
        //            }
        //        }
        //        for(int i=1;i<5;i++){
        //            id++;
        //            pointList.add(new TreePoint(""+id,"分类"+i,"" + parentId,"0",i));
        //            for(int j=1;j<5;j++){
        //                if(j==1){
        //                    parentId2 = id;
        //                }
        //                id++;
        //                pointList.add(new TreePoint(""+id,"分类"+i+"_"+j,""+parentId2,"0",j));
        //                for(int k=1;k<5;k++){
        //                    if(k==1){
        //                        parentId3 = id;
        //                    }
        //                    id++;
        //                    pointList.add(new TreePoint(""+id,"分类"+i+"_"+j+"_"+k,""+parentId3,"1",k));
        //                }
        //            }
        //        }
        //打乱集合中的数据
        Collections.shuffle(pointList);
        //对集合中的数据重新排序
        updateData();
    }


    public void addListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.onItemClick(position);
            }
        });

        et_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchAdapter(s);
            }
        });
    }

    private void searchAdapter(Editable s) {
        adapter.setKeyword(s.toString());
    }

    //对数据排序 深度优先
    private void updateData() {
        for (TreePoint treePoint : pointList) {
            pointMap.put(treePoint.getID(), treePoint);
        }
        Collections.sort(pointList, new Comparator<TreePoint>() {
            @Override
            public int compare(TreePoint lhs, TreePoint rhs) {
                int llevel = TreeUtils.getLevel(lhs, pointMap);
                int rlevel = TreeUtils.getLevel(rhs, pointMap);
                if (llevel == rlevel) {
                    if (lhs.getPARENT_ID().equals(rhs.getPARENT_ID())) {  //左边小
                        return lhs.getDISPLAY_ORDER() > rhs.getDISPLAY_ORDER() ? 1 : -1;
                    } else {  //如果父辈id不相等
                        //同一级别，不同父辈
                        TreePoint ltreePoint = TreeUtils.getTreePoint(lhs.getPARENT_ID(), pointMap);
                        TreePoint rtreePoint = TreeUtils.getTreePoint(rhs.getPARENT_ID(), pointMap);
                        return compare(ltreePoint, rtreePoint);  //父辈
                    }
                } else {  //不同级别
                    if (llevel > rlevel) {   //左边级别大       左边小
                        if (lhs.getPARENT_ID().equals(rhs.getID())) {
                            return 1;
                        } else {
                            TreePoint lreasonTreePoint = TreeUtils.getTreePoint(lhs.getPARENT_ID(), pointMap);
                            return compare(lreasonTreePoint, rhs);
                        }
                    } else {   //右边级别大   右边小
                        if (rhs.getPARENT_ID().equals(lhs.getID())) {
                            return -1;
                        }
                        TreePoint rreasonTreePoint = TreeUtils.getTreePoint(rhs.getPARENT_ID(), pointMap);
                        return compare(lhs, rreasonTreePoint);
                    }
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, String contentInfo, int progress) {
        Notification notification;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        Intent intent = new Intent(context, BottomNavigationBarActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentInfo(contentInfo);

        builder.setDefaults(~0);
        //                builder.set
        builder.setPriority(Notification.PRIORITY_MAX);
        //        builder.setContent(remoteViews);//设置自定义布局
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);

        //        builder.setTicker("悬浮通知");
        notification = builder.build();
        return notification;
    }
}
