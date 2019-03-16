package com.example.health_community.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.example.health_community.R;
import com.example.health_community.interf.OnTouchOutsideViewListener;

/**
 * @说明:点击marker弹出的自定义view
 * @作者:zry
 * @时间:2016/8/26
 */
public class InfoView extends LinearLayout{

    private RelativeLayout relativeLayout;
    private TextView tv1,tv2,tv3;
    public Button close_btn;
    private View mTouchOutsideView;
    private OnTouchOutsideViewListener mOnTouchOutsideViewListener;
    private BaiduMap map;

    public InfoView(Context context) {
        this(context,null);
    }

    public InfoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public InfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context){
        View.inflate(context, R.layout.view_info,this);
        relativeLayout = findViewById(R.id.info_view);
        tv1= findViewById(R.id.textView);
        tv2= findViewById(R.id.textView2);
        tv3= findViewById(R.id.textView3);
        close_btn = findViewById(R.id.close_btn);
        close_btn.setOnClickListener(view1 -> {
            map.hideInfoWindow();
        });
    }

    public void setTv1(String text, float size, int color){
        tv1.setText(text);
        tv1.setTextSize(size);
        tv1.setTextColor(color);
    }

    public void setTv2(String text, float size, int color){
        tv2.setText("地址："+text);
        tv2.setTextSize(size);
        tv2.setTextColor(color);
    }

    public void setTv3(String text, float size, int color){
        if (text==null||text.equals(""))
        tv3.setText("电话：暂无");
        else
            tv3.setText("电话："+text);
        tv3.setTextSize(size);
        tv3.setTextColor(color);
    }

    public void setBaiduMap(BaiduMap map) {
        this.map = map;
    }

//    public void finish() {
//        relativeLayout.setVisibility(GONE);
//    }
//
//    public void setOnTouchOutsideViewListener(View view, OnTouchOutsideViewListener onTouchOutsideViewListener) {
//        mTouchOutsideView = view;
//        mOnTouchOutsideViewListener = onTouchOutsideViewListener;
//    }
//
//    public OnTouchOutsideViewListener getOnTouchOutsideViewListener() {
//        return mOnTouchOutsideViewListener;
//    }
//
//    @Override
//    public boolean dispatchTouchEvent(final MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            // Notify touch outside listener if user tapped outside a given view
//            if (mOnTouchOutsideViewListener != null && mTouchOutsideView != null
//                    && mTouchOutsideView.getVisibility() == View.VISIBLE) {
//                Rect viewRect = new Rect();
//                mTouchOutsideView.getGlobalVisibleRect(viewRect);
//                if (!viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
//                    mOnTouchOutsideViewListener.onTouchOutside(mTouchOutsideView, ev);
//                }
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    public void onTouchOutside(View view, MotionEvent event) {
//        view.setVisibility(GONE);
//    }
}
