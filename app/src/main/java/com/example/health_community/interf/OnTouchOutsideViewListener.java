package com.example.health_community.interf;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/11
 * desc:
 */

import android.view.MotionEvent;
import android.view.View;

/**
 * Interface definition for a callback to be invoked when a touch event has occurred outside a formerly specified
 * view. See {@link #setOnTouchOutsideViewListener(View, OnTouchOutsideViewListener).}
 */
public interface OnTouchOutsideViewListener {

    /**
     * Called when a touch event has occurred outside a given view.
     *
     * @param view  The view that has not been touched.
     * @param event The MotionEvent object containing full information about the event.
     */
    public void onTouchOutside(View view, MotionEvent event);
}