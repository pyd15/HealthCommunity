package com.example.health_community.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.Random;

/**
 * Created by Dr.P on 2018/5/8.
 * runas /user:Dr.P "cmd /k"
 */

public class NormalUtil {

    private static Context context = UIUtils.getContext();
    //    private static Activity activity = (Activity)context;
    private static Activity activity;
    public static File file = new File(Constant.INSTALL_PATH + SPUtils.getPrefString("new_name", null));
//public static File file = new File(Constant.INSTALL_PATH + getVersionName(context));

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showInstallDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setMessage("已下载最新版安装包，现在安装吧？")
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        installAPK(Constant.INSTALL_PATH + SPUtils.getPrefString("new_name", null));
                    }
                })
                .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean a=false;
                        if (NormalUtil.file.exists()) {
                            a=NormalUtil.file.delete();
                        }
                        if (a) {
                            Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("过会再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    public static void showExitDialog(Context context) {
        new android.support.v7.app.AlertDialog.Builder(context).setTitle("确认退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

//    public static ArrayList<String> getImagePaths(Medicine Medicine) {
//        String[] images = Medicine.getImagePath().split(",");
//        String[] img_urls = Medicine.getImageUrl().split(",");
//        ArrayList<String> imageList = new ArrayList<String>();
//        //        for (int i = abc; i < images.length; i++) {
//        for (int i = 0; i < img_urls.length; i++) {
//            imageList.add(img_urls[i]);
//            //            imageList.add(images[i]);
//        }
//        return imageList;
//    }

    public static void installAPK(String filePath) {
        File file = new File(filePath);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 请求用户给予悬浮窗的权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void askForPermission() {
        if (!Settings.canDrawOverlays(activity)) {
            Toast.makeText(activity, "当前无悬浮通知权限，请授权！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, Constant.OVERLAY_PERMISSION_REQ_CODE);
        }
    }

    /**
     * get App versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        Log.e("????????????", getVersionName(context));
        return "btfrg_V"+versionName+".apk";
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
