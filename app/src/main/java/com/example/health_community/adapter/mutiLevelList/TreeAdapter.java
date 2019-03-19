package com.example.health_community.adapter.mutiLevelList;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.health_community.R;
import com.example.health_community.util.DensityUtil;
import com.example.health_community.util.TreeUtils;

import java.util.HashMap;
import java.util.List;


/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/08
 * desc:
 */
public class TreeAdapter extends BaseAdapter {
    private Context mcontext;
    private Activity mActivity;
    private List<TreePoint> pointList;
    private String keyword = "";
    private HashMap<String, TreePoint> pointMap = new HashMap<>();

    private int operateMode = ModeSelect;
    //两种操作模式  点击 或者 选择
    private static final int ModeClick = 1;
    private static final int ModeSelect = 2;

    @IntDef({ModeClick,ModeSelect})
    public @interface Mode{

    }

    //设置操作模式
    public void setOperateMode(@Mode int operateMode){
        this.operateMode = operateMode;
    }

    public TreeAdapter(final Context mcontext, List<TreePoint> pointList, HashMap<String, TreePoint> pointMap) {
        this.mcontext = mcontext;
        this.mActivity = (Activity) mcontext;
        this.pointList = pointList;
        this.pointMap = pointMap;
    }

    /**
     * 搜索的时候，先关闭所有的条目，然后，按照条件，找到含有关键字的数据
     * 如果是叶子节点，
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
        for (TreePoint treePoint : pointList) {
            treePoint.setExpand(false);
        }
        if (!"".equals(keyword)) {
            for (TreePoint tempTreePoint : pointList) {
                if (tempTreePoint.getN_NAME().contains(keyword)) {
                    if ("0".equals(tempTreePoint.getIS_LEAF())) {   //非叶子节点
                        tempTreePoint.setExpand(true);
                    }
                    //展开从最顶层到该点的所有节点
                    openExpand(tempTreePoint);
                }
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 从TreePoint开始一直展开到顶部
     * @param treePoint
     */
    private void openExpand(TreePoint treePoint) {
        if ("0".equals(treePoint.getPARENT_ID())) {
            treePoint.setExpand(true);
        } else {
            pointMap.get(treePoint.getPARENT_ID()).setExpand(true);
            openExpand(pointMap.get(treePoint.getPARENT_ID()));
        }
    }


    //第一要准确计算数量
    @Override
    public int getCount() {
        int count = 0;
        for (TreePoint tempPoint : pointList) {
            if ("0".equals(tempPoint.getPARENT_ID())) {
                count++;
            } else {
                if (getItemIsExpand(tempPoint.getPARENT_ID())) {
                    count++;
                }
            }
        }
        return count;
    }

    //判断当前Id的tempPoint是否展开了
    private boolean getItemIsExpand(String ID) {
        for (TreePoint tempPoint : pointList) {
            if (ID.equals(tempPoint.getID())) {
                return tempPoint.isExpand();
            }
        }
        return false;
    }

    @Override
    public Object getItem(int position) {
        return pointList.get(convertPostion(position));
    }

    private int convertPostion(int position) {
        int count = 0;
        for (int i = 0; i < pointList.size(); i++) {
            TreePoint treePoint = pointList.get(i);
            if ("0".equals(treePoint.getPARENT_ID())) {
                count++;
            } else {
                if (getItemIsExpand(treePoint.getPARENT_ID())) {
                    count++;
                }
            }
            if (position == (count - 1)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.adapter_treeview, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.ib_select = (ImageButton) convertView.findViewById(R.id.ib_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TreePoint tempPoint = (TreePoint) getItem(position);
        int level = TreeUtils.getLevel(tempPoint,pointMap);
        holder.icon.setPadding(25 * level, holder.icon.getPaddingTop(), 0, holder.icon.getPaddingBottom());
        if ("0".equals(tempPoint.getIS_LEAF())) {  //如果为父节点
            if (!tempPoint.isExpand()) {    //不展开显示加号
                holder.icon.setVisibility(View.VISIBLE);
                holder.icon.setImageResource(R.drawable.outline_list_collapse);
            } else {                        //展开显示减号
                holder.icon.setVisibility(View.VISIBLE);
                holder.icon.setImageResource(R.drawable.outline_list_expand);
            }
        } else {   //如果叶子节点，不占位显示
            holder.icon.setVisibility(View.INVISIBLE);
        }
        if(operateMode == ModeSelect){
            holder.ib_select.setVisibility(View.VISIBLE);
            holder.ib_select.setSelected(tempPoint.isSelected());
            holder.ib_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onModeSelect(tempPoint);
                }
            });
        }else{
            holder.ib_select.setVisibility(View.GONE);
        }
        //如果存在搜索关键字
        if (keyword != null && !"".equals(keyword) && tempPoint.getN_NAME().contains(keyword)) {
            int index = tempPoint.getN_NAME().indexOf(keyword);
            int len = keyword.length();
            Spanned temp = Html.fromHtml(tempPoint.getN_NAME().substring(0, index)
                    + "<font color=#FF0000>"
                    + tempPoint.getN_NAME().substring(index, index + len) + "</font>"
                    + tempPoint.getN_NAME().substring(index + len, tempPoint.getN_NAME().length()));

            holder.text.setText(temp);
        } else {
            holder.text.setText(tempPoint.getN_NAME());
        }
        holder.text.setCompoundDrawablePadding(DensityUtil.dip2px(mcontext, 10));
        return convertView;
    }



    public void onItemClick(int position) {
        TreePoint treePoint = (TreePoint) getItem(position);
        if ("1".equals(treePoint.getIS_LEAF())) {   //点击叶子节点
            //处理回填
            Toast.makeText(mcontext, getSubmitResult(treePoint), Toast.LENGTH_SHORT).show();
        } else {  //如果点击的是父类
            if (treePoint.isExpand()) {
                for (TreePoint tempPoint : pointList) {
                    if (tempPoint.getPARENT_ID().equals(treePoint.getID())) {
                        if ("0".equals(treePoint.getIS_LEAF())) {
                            tempPoint.setExpand(false);
                        }
                    }
                }
                treePoint.setExpand(false);
            } else {
                treePoint.setExpand(true);
            }
        }
        this.notifyDataSetChanged();
    }


    //选择操作
    private void onModeSelect(TreePoint treePoint){
        if ("1".equals(treePoint.getIS_LEAF())) {   //选择叶子节点
            //处理回填
            treePoint.setSelected(!treePoint.isSelected());
        } else {                                   //选择父节点
            int position = pointList.indexOf(treePoint);
            boolean isSelect = treePoint.isSelected();
            treePoint.setSelected(!isSelect);
            if(position == -1){
                return ;
            }
            if(position == pointList.size()-1){
                return;
            }
            position++;
            for(;position < pointList.size();position++){
                TreePoint tempPoint =  pointList.get(position);
                if(tempPoint.getPARENT_ID().equals(treePoint.getPARENT_ID())){    //如果找到和自己同级的数据就返回
                    break;
                }
                tempPoint.setSelected(!isSelect);
            }
        }
        this.notifyDataSetChanged();
    }

    //选中所有的point
//    private void selectPoint(TreePoint treePoint) {
//        if(){
//
//        }
//    }




    private String getSubmitResult(TreePoint treePoint) {
        StringBuilder sb = new StringBuilder();
        addResult(treePoint, sb);
        String result = sb.toString();
        if (result.endsWith("-")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    private void addResult(TreePoint treePoint, StringBuilder sb) {
        if (treePoint != null && sb != null) {
            sb.insert(0, treePoint.getN_NAME() + "-");
            if (!"0".equals(treePoint.getPARENT_ID())) {
                addResult(pointMap.get(treePoint.getPARENT_ID()), sb);
            }
        }
    }


    class ViewHolder {
        TextView text;
        ImageView icon;
        ImageButton ib_select;
    }

}
