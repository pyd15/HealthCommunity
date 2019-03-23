package com.example.health_community.view.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/20
 * desc:
 */
public class DIYBottomSheetDialog extends BottomSheetDialog {
    public DIYBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public DIYBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected DIYBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void show() {
        super.show();

    }
}
