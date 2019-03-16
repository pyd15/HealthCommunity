package com.example.health_community.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by hymanme on 2016/abc/5 0005.
 * Application
 */
public class BaseApplication extends LitePalApplication {
    public final static String TAG = "BaseApplication";
    public final static boolean DEBUG = true;
    private static BaseApplication application;
    private static Context context;
    private static int mainTid;
    private boolean readDBFlag = false;
    /**
     * Activity集合，来管理所有的Activity
     */
    private static List<Activity> activities;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        activities = new LinkedList<>();
        application = this;
        context=getApplicationContext();
        mainTid = android.os.Process.myTid();
        LitePal.initialize(this);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
//        init();
//        if (!readDBFlag) {
//            readDBFlag = SQLUtil.createDatabase(getApplicationContext());
//            SPUtils.setPrefBoolean(Constant.DB_EXIST, readDBFlag);
//        }
    }

//    private void init(){
//        Stetho.initializeWithDefaults(this);
//        new OkHttpClient.Builder()
//                .addNetworkInterceptor(new StethoInterceptor())
//                .build();
//    }

    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication() {
        return application;
    }

    /**
     * 获取context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static int getMainTid() {
        return mainTid;
    }

    /**
     * 添加一个Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束一个Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 结束当前所有Activity
     */
    public static void clearActivities() {
        ListIterator<Activity> iterator = activities.listIterator();
        Activity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应运程序
     */
    public static void quiteApplication() {
        clearActivities();
        System.exit(0);
    }
}
