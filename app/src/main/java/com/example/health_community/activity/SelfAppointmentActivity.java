package com.example.health_community.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.health_community.R;
import com.example.health_community.adapter.mutiLevelList.TreeAdapter;
import com.example.health_community.adapter.mutiLevelList.TreePoint;
import com.example.health_community.model.BigDepartment;
import com.example.health_community.model.Department;
import com.example.health_community.util.HttpAction;
import com.example.health_community.util.HttpUtil;
import com.example.health_community.util.TreeUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;

public class SelfAppointmentActivity extends AppCompatActivity {

    private TreeAdapter adapter;
    private ListView listView;
    private EditText et_filter;
    private String[] hospital_big_department, hospital_internal, hospital_obstetrics_gynecology, hospital_orthopedics, hospital_oncology, hospital_surgery, hospital_subsidiary;
    private List<TreePoint> pointList = new ArrayList<>();
    private HashMap<String, TreePoint> pointMap = new HashMap<>();
    private List<BigDepartment> bigDepartmentList;
    private List<BigDepartment> bigDepartments;
    private List<Department> departments;
    private static final int REQUEST_COMPELETE = 1;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_COMPELETE:
                    initData(bigDepartmentList);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_appointment);
        init();
        addListener();
    }

    public void init() {
        adapter = new TreeAdapter(this, pointList, pointMap);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        et_filter = findViewById(R.id.et_filter);
        HttpUtil.sendOkHttpGetRequest("https://www.pydwp.xyz/JSoupDemo/hospital/getHospitalAndDepartments.do?hos_name=广州协佳医院", new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    bigDepartmentList = HttpAction.parseJSONWithGSON(responseData);
//                    bigDepartments.addAll(bigDepartmentList);
                    Message message = new Message();
                    message.what = REQUEST_COMPELETE;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
//        initData(bigDepartments);
    }

    //初始化数据
    //数据特点：TreePoint 之间的关系特点   id是任意唯一的。    如果为根节点 PARENTID  为"0"   如果没有子节点，也就是本身是叶子节点的时候ISLEAF = "1"
    //  DISPLAY_ORDER 是同一级中 显示的顺序
    //如果需要做选中 单选或者多选，只需要给TreePoint增加一个选中的属性，在ReasonAdapter中处理就好了
    private void initData(List<BigDepartment> bigDepartmentList) {

        hospital_big_department = getResources().getStringArray(R.array.hospital_big_department);
        hospital_internal = getResources().getStringArray(R.array.hospital_internal);
        hospital_obstetrics_gynecology = getResources().getStringArray(R.array.hospital_obstetrics_gynecology);
        hospital_orthopedics = getResources().getStringArray(R.array.hospital_orthopedics);
        hospital_oncology = getResources().getStringArray(R.array.hospital_oncology);
        hospital_surgery = getResources().getStringArray(R.array.hospital_surgery);
        hospital_subsidiary = getResources().getStringArray(R.array.hospital_subsidiary);
        int[] departs_num = {hospital_internal.length, hospital_obstetrics_gynecology.length,
                hospital_orthopedics.length, hospital_oncology.length, hospital_surgery.length, hospital_subsidiary.length};
        pointList.clear();
        int id = 1000;
        int parentId = 0;
        int parentId2 = 0;
        for (int i = 1; i <= bigDepartmentList.size(); i++) {
            id++;
            pointList.add(new TreePoint("" + id, bigDepartmentList.get(i - 1).getBig_depart_name(), "" + parentId, "0", i));
            for (int j = 1; j <= bigDepartmentList.get(i - 1).getDepartments().size(); j++) {
                if (j == 1) {
                    parentId2 = id;
                }
                id++;
                departments = bigDepartmentList.get(i - 1).getDepartments();
                pointList.add(new TreePoint("" + id, departments.get(j - 1).getDepart_name(), "" + parentId2, "1", j));
            }
        }
        //        for (int i = 1; i <= hospital_big_department.length; i++) {
        //            id++;
        //            pointList.add(new TreePoint("" + id, hospital_big_department[i - 1], "" + parentId, "0", i));
        //            for (int j = 1; j <= departs_num[i - 1]; j++) {
        //                if (j == 1) {
        //                    parentId2 = id;
        //                }
        //                id++;
        //                switch (i) {
        //                    case 1:
        //                        pointList.add(new TreePoint("" + id, hospital_internal[j - 1], "" + parentId2, "1", j));
        //                        break;
        //                    case 2:
        //                        pointList.add(new TreePoint("" + id, hospital_obstetrics_gynecology[j - 1], "" + parentId2, "1", j));
        //                        break;
        //                    case 3:
        //                        pointList.add(new TreePoint("" + id, hospital_orthopedics[j - 1], "" + parentId2, "1", j));
        //                        break;
        //                    case 4:
        //                        pointList.add(new TreePoint("" + id, hospital_oncology[j - 1], "" + parentId2, "1", j));
        //                        break;
        //                    case 5:
        //                        pointList.add(new TreePoint("" + id, hospital_surgery[j - 1], "" + parentId2, "1", j));
        //                        break;
        //                    case 6:
        //                        pointList.add(new TreePoint("" + id, hospital_subsidiary[j - 1], "" + parentId2, "1", j));
        //                        break;
        //                    default:
        //                        break;
        //                }
        //                //                pointList.add(new TreePoint(""+id,"分类"+i+"_"+j,""+parentId2,"0",j));
        //            }
        //        }

        //        int parentId3 = 0;
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
}
