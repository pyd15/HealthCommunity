package com.example.health_community.view;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.health_community.R;
import com.example.health_community.adapter.mutiLevelList.TreeAdapter;
import com.example.health_community.adapter.mutiLevelList.TreePoint;
import com.example.health_community.util.TreeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/18
 * desc:
 */
public class DIYDialog {
    View dialogView;
    Context context;
    private TreeAdapter adapter;
    private ListView listView;
    private EditText et_filter;
    private String[] items;
    private List<TreePoint> pointList = new ArrayList<>();
    private HashMap<String, TreePoint> pointMap = new HashMap<>();

    public void create(Activity activity, Context context) {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
//        dialogView = activity.getLayoutInflater().inflate(R.layout.dialog_bottom_sheet, null);
        dialogView = activity.getLayoutInflater().inflate(R.layout.activity_self_appointment, null);
        this.context = context;
        init();
        addListener();
        Button btn_dialog_bottom_sheet_ok = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_ok);
        Button btn_dialog_bottom_sheet_cancel = dialogView.findViewById(R.id.btn_dialog_bottom_sheet_cancel);
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
        mBottomSheetDialog.show();
    }

    public void init() {
        adapter = new TreeAdapter(context, pointList, pointMap);
        listView =  dialogView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        et_filter =  dialogView.findViewById(R.id.et_filter);
        initData();
    }

    //初始化数据
    //数据特点：TreePoint 之间的关系特点   id是任意唯一的。    如果为根节点 PARENTID  为"0"   如果没有子节点，也就是本身是叶子节点的时候ISLEAF = "1"
    //  DISPLAY_ORDER 是同一级中 显示的顺序
    //如果需要做选中 单选或者多选，只需要给TreePoint增加一个选中的属性，在ReasonAdapter中处理就好了
    private void initData() {

        items = context.getResources().getStringArray(R.array.hospital_big_department);
        pointList.clear();
        int id =1000;
        int parentId = 0;
        int parentId2 = 0;
        int parentId3 = 0;
        for(int i=1;i<5;i++){
            id++;
            pointList.add(new TreePoint(""+id,"分类"+i,"" + parentId,"0",i));
            for(int j=1;j<5;j++){
                if(j==1){
                    parentId2 = id;
                }
                id++;
                pointList.add(new TreePoint(""+id,"分类"+i+"_"+j,""+parentId2,"0",j));
                for(int k=1;k<5;k++){
                    if(k==1){
                        parentId3 = id;
                    }
                    id++;
                    pointList.add(new TreePoint(""+id,"分类"+i+"_"+j+"_"+k,""+parentId3,"1",k));
                }
            }
        }
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
