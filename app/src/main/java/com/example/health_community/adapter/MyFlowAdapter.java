package com.example.health_community.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.health_community.R;
import com.example.health_community.view.BorderTextView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/17
 * desc:
 */
public class MyFlowAdapter extends TagAdapter<String> {

    private Context context;
    private List<Integer> colors;

    public MyFlowAdapter(Context context, List<String> datas,List<Integer> colors) {
        super(datas);
        this.context=context;
        this.colors=colors;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        LayoutInflater inflater = LayoutInflater.from(context);
        BorderTextView tv = (BorderTextView) inflater.inflate(R.layout.flow_item, parent, false);
//        if (position<colors.size()) {
            tv.setText(s);
            //边框颜色
//            Log.e("color", position + "-" + colors.get(position));
        Log.e("color", position+"");
//        if (colors.get(position)!=null)
        try {
            tv.setBorderColor(colors.get(position));
            tv.setTextColor(colors.get(position));
        } catch (IndexOutOfBoundsException e) {
            tv.setBorderColor(colors.get(position-1));
            tv.setTextColor(colors.get(position-1));
        }
//        else
//            tv.setBorderColor(colors.get(position-1));
            //字体颜色
//            tv.setTextColor(colors.get(position));
            tv.setTextSize(13);
//        }
//        tv.setBackgroundColor(context.getResources().getColor(R.color.recycler_color1));
//        view.setBackgroundColor(R.color.colorAccent);
        return tv;
    }

    /**
     * 选中时的回调方法
     * @param position
     * @param view
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onSelected(int position, View view) {
        super.onSelected(position, view);
//        view.setBackgroundColor(R.color.colorAccent);
    }

    /**
     * 默认的回调方法
     * @param position
     * @param view
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void unSelected(int position, View view) {
        super.unSelected(position, view);
        view.setBackgroundColor(R.color.colorPrimaryDark);
    }
}
