package com.example.health_community.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * Created by pyd on 2019/2/26.
 */
public class SPUtils {

    static Context context;

    private SPUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(BaseApplication.getApplication());
//        return PreferenceManager.getDefaultSharedPreferences(LitePalApplication.getApplication());
    }

    private static SharedPreferences getPrivateSharedPreferences() {
        return UIUtils.getApplication().getSharedPreferences(Constant.BTF_DATA, Context.MODE_PRIVATE);
        //        return PreferenceManager.getDefaultSharedPreferences(LitePalApplication.getApplication());
    }

    public static SharedPreferences.Editor getPrivateSPEditor() {
        return getPrivateSharedPreferences().edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getPrefString(String key, final String defaultValue) {
        return getPrivateSharedPreferences().getString(key, defaultValue);
    }

    public static void setPrefString(final String key, final String value) {
        getPrivateSPEditor().putString(key, value).apply();
    }

    public static boolean getPrefBoolean(final String key, final boolean defaultValue) {
        return getPrivateSharedPreferences().getBoolean(key, defaultValue);
    }

    public static void setPrefBoolean(final String key, final boolean value) {
        getPrivateSPEditor().putBoolean(key, value).apply();
    }

    public static int getPrefInt(final String key, final int defaultValue) {
        return getPrivateSharedPreferences().getInt(key, defaultValue);
    }

    public static void setPrefInt(final String key, final int value) {
        getPrivateSPEditor().putInt(key, value).apply();
    }

    public static float getPrefFloat(final String key, final float defaultValue) {
        return getPrivateSharedPreferences().getFloat(key, defaultValue);
    }

    public static void setPrefFloat(final String key, final float value) {
        getPrivateSPEditor().putFloat(key, value).apply();
    }

    public static long getPrefLong(final String key, final long defaultValue) {
        return getPrivateSharedPreferences().getLong(key, defaultValue);
    }

    public static void setPrefLong(final String key, final long value) {
        getPrivateSPEditor().putLong(key, value).apply();

    }

    public static <T> void setPreList(final String key, List<T> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        setPrefString(key, new Gson().toJson(list));
    }

    public static <News> List<News> getPreList(final String key,News t) {
        List<News> list;
        String strJson = getPrefString(key, null);
//        if (null == strJson) {
//            return list;
//        }
        list =new Gson().fromJson(strJson, new TypeToken<List<News>>() {
        }.getType());
        Log.e("sdfsdf", list.get(0).getClass().toString());
        return list;
    }

    public static void removeData(final String key) {
        getPrivateSPEditor().remove(key).apply();
    }

    public static boolean hasKey(final String key) {
        return getPrivateSharedPreferences().contains(key);
    }

    public static void clearPreference() {
        final SharedPreferences.Editor editor = getPrivateSPEditor();
        editor.clear();
        editor.apply();
    }
}
