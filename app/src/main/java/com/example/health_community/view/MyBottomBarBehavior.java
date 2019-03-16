package com.example.health_community.view;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/06
 * desc:
 */

//逻辑并不复杂，我们通过重写Behavior中关于嵌套滑动的两个回调完成了FloatingActionButton的隐藏和显示判断及操作。
//        单独出场的底栏也可以利用上面一样的方法来设置隐藏或显示，我的底栏是和AppBarLayout一起出场，所以我就让底栏从属于AppBarLayout活动。代码如下：
public class MyBottomBarBehavior extends CoordinatorLayout.Behavior<View> {

    public MyBottomBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //确定所提供的子视图是否有另一个特定的同级视图作为布局从属。
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        //这个方法是说明这个子控件是依赖AppBarLayout的
        return dependency instanceof AppBarLayout;
    }

    //用于响应从属布局的变化
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        float translationY = Math.abs(dependency.getTop());//获取更随布局的顶部位置

        child.setTranslationY(translationY);
        return true;
    }

}
