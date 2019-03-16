package com.example.health_community.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.health_community.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Dr.P on 2017/11/10.
 * runas /user:Dr.P "cmd /k"
 */

public class HttpAction<T> {

    public static <T> List<T> parseJSONWithJSONObject(final Context context, String url, String setString) throws JSONException {
        List<T> results = new ArrayList<>();
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        //提取出family中的所有
                        JSONObject s1 = (JSONObject) data.get(i);
                    }
                    List<T> list = new Gson().fromJson(data.toString(), new TypeToken<List<T>>() {}.getType());
                    Log.e("size", list.size()+"");
                    SPUtils.setPreList("news", list);
                    results.addAll(list);
                    Log.e("test", SPUtils.getPrefString(setString,"?????"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return results;
    }

    public static void parseJSONWithJSONObject(final Context context, String url) throws JSONException {
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String responseData = response.body().string();
                System.out.println(responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
//                    Log.v("json", jsonObject.toString());
                    //                    int code = jsonObject.getInt("code");
                    //                    String message = jsonObject.getString("msg");
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        //提取出family中的所有
                        JSONObject s1 = (JSONObject) data.get(i);
//                        Log.v("fdfafa", s1.toString());

                    }
//                                        Log.e("json", data.toString());
//                    Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show();
                    List<News> news = new Gson().fromJson(data.toString(), new TypeToken<List<News>>() {}.getType());
                    for (News news1 : news) {
                        Log.e("img", news1.getNews_image());
//                        news1.saveOrUpdate();
                    }
                    Log.e("size", news.size()+"");
//                    if (SPUtils.getPrefString("news",null)!=null)
//                    SPUtils.removeData("news");
                    SPUtils.setPreList("news", news);
                    Log.e("test", SPUtils.getPrefString("news","?????"));
                    //                    String name = data.getString("name");
                    //                    String updateContent = data.getString("updateContent");
                    //                    SPUtils.setPrefInt("code", code);
                    //                    SPUtils.setPrefString("message", message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //    public static boolean parseJSONWithGSON(String jsonData) throws ExecutionException, InterruptedException {
    //        try {
    //            Boolean flag = false;
    //            String msg = jsonData.substring(jsonData.length() - 1, jsonData.length());
    //            Gson gson = new Gson();
    //            ButterflyInfo butterflyInfo1 = gson.fromJson(jsonData.substring(0, jsonData.length() - 2), new TypeToken<ButterflyInfo>() {
    //            }.getType());
    //            List<Medicine> butterflyInfoList = butterflyInfo1.MedicineList;
    //            if (msg.equals("+")) {
    //                flag = InsertSQL(butterflyInfoList);
    //            } else if (msg.equals("-")) {
    //                flag = DeleteSQL(butterflyInfoList);
    //            } else if (msg.equals("=")) {
    //                flag = UpdateSQL(butterflyInfoList);
    //            } else {
    //                Log.d("sqlerror", "No match!");
    //            }
    //            return flag;
    //        } catch (Exception e) {
    //            e.printStackTrace();//        }
    //        return false;
    //    }
    public static String handleMessage(String name, String updateContent) {
        String[] strings = updateContent.split("/n");
        String udContent = "";
        for (String s : strings) {
            udContent += s + "\n\n";
        }
        String splitLine = "------------------------------------------------------------\n\n";
        return "版本名:" + name + "\n\n" + splitLine + "更新内容：\n\n" + udContent + splitLine + "是否更新？";
    }

    //    public static String handleResponse(String oldData, String newData) {
    //        String head = newData.substring(0, 35);
    //        String oldInfo = oldData.substring(35, oldData.length() - 2);
    //        String newInfo = newData.substring(35, newData.length() - 2);
    //        String[] oldlist = oldInfo.split("(?<=\\}),(?=\\{)");
    //        String[] newlist = newInfo.split("(?<=\\}),(?=\\{)");
    //        String result = "";
    //        String finalResult = "";
    //        if (oldlist.length == newlist.length) {
    //            //            Log.e("=", "");
    //            for (int i = 0; i < newlist.length; i++) {
    //                if (oldlist[i].equals(newlist[i])) {
    //                    continue;
    //                }
    //                //                Log.e("olds", oldlist[i] + "\n");
    //                //                Log.e("news", newlist[i]+"\n");
    //                result = result + "," + newlist[i];
    //            }
    //            finalResult = head + result.substring(1, result.length()) + "]}" + "^=";
    //        } else if (oldlist.length < newlist.length) {
    //            //            Log.e("<", "");
    //            for (int i = oldlist.length; i < newlist.length; i++) {
    //                result = result + "," + newlist[i];
    //            }
    //            finalResult = head + result.substring(1, result.length()) + "]}" + "^+";
    //        } else {
    //            //            Log.e(">", "");
    //            for (int i = newlist.length; i < oldlist.length; i++) {
    //                result = result + "," + oldlist[i];
    //            }
    //            finalResult = head + result.substring(1, result.length()) + "]}" + "^-";
    //        }
    //        //        Log.e("final_result", finalResult);
    //        return finalResult;
    //    }
    //
    //    private static boolean InsertSQL(List<Medicine> butterflyInfoList) throws ExecutionException, InterruptedException {
    //        boolean flag = false;
    //        Connector.getDatabase();
    //        for (Medicine butterflyInfo : butterflyInfoList) {
    //            butterflyInfo.setImageUrl(butterflyInfo.getImageUrl());
    //            butterflyInfo.setImagePath((String) new DownImage(butterflyInfo).execute(butterflyInfo.getImageUrl()).get());
    //            flag = butterflyInfo.saveOrUpdate("b_id=?", String.valueOf(butterflyInfo.getId()));
    //        }
    ////        Log.e("Insert-flag", String.valueOf(flag));
    //        return flag;
    //    }
    //
    //    private static boolean DeleteSQL(List<Medicine> butterflyInfoList) {
    //        boolean flag = false;
    //        Connector.getDatabase();
    //        String images[];
    //        File file;
    //        for (Medicine butterflyInfo : butterflyInfoList) {
    //            List<Medicine> oneInfo = LitePal.where("name=?", butterflyInfo.getName()).find(Medicine.class);
    //            images = oneInfo.get(0).getImagePath().split(",");
    //            for (String imagePath : images) {
    //                file = new File(imagePath);
    //                if (file.exists()) {
    //                    file.delete();
    //                }
    //            }
    //            oneInfo.get(0).delete();
    //            flag = true;
    //        }
    ////        Log.e("Delete-flag", String.valueOf(flag));
    //        return flag;
    //    }
    //
    //    private static boolean UpdateSQL(List<Medicine> butterflyInfoList) throws ExecutionException, InterruptedException {
    //        boolean flag = false;
    //        List<Medicine> nameList = LitePal.findAll(Medicine.class);
    //        int count = 0;
    //        for (int i = 0, j = 0; i < nameList.size(); i++) {
    ////            Log.e("i-j", i + "-" + j);
    //            if (nameList.get(i).getId()==butterflyInfoList.get(j).getId()) {
    ////                Log.e("id", nameList.get(i).getId() + "-" + butterflyInfoList.get(j).getId());
    ////                Log.e("name_name", nameList.get(i).getName() + "-" + butterflyInfoList.get(j).getName());
    ////                                        Log.e("latin_latin", nameList.get(i).getLatinName() + "-" + butterflyInfoList.get(j).getLatinName());
    ////                                        Log.e("url_url", nameList.get(i).getImageUrl()+ "-" + butterflyInfoList.get(j).getImageUrl());
    ////                                        Log.e("path_path", nameList.get(i).getImagePath() + "-" + butterflyInfoList.get(j).getImagePath());
    ////                                        Log.e("type_type", nameList.get(i).getType() + "-" + butterflyInfoList.get(j).getType());
    ////                                        Log.e("area_area", nameList.get(i).getArea() + "-" + butterflyInfoList.get(j).getArea());
    ////                                        Log.e("protect_protect", nameList.get(i).getProtect() + "-" + butterflyInfoList.get(j).getProtect());
    ////                                        Log.e("unique_unique", nameList.get(i).getUniqueToChina() + "-" + butterflyInfoList.get(j).getUniqueToChina());
    ////                                        Log.e("feature_feature", nameList.get(i).getFeature() + "-" + butterflyInfoList.get(j).getFeature());
    //                nameList.get(i).setName(butterflyInfoList.get(j).getName());
    //                nameList.get(i).setImageUrl(butterflyInfoList.get(j).getImageUrl());
    //                nameList.get(i).setArea(butterflyInfoList.get(j).getArea());
    //                nameList.get(i).setFeature(butterflyInfoList.get(j).getFeature());
    //                nameList.get(i).setId(butterflyInfoList.get(j).getId());
    //                nameList.get(i).setLatinName(butterflyInfoList.get(j).getLatinName());
    //                nameList.get(i).setRare(butterflyInfoList.get(j).getRare());
    //                nameList.get(i).setType(butterflyInfoList.get(j).getType());
    //                nameList.get(i).setUniqueToChina(butterflyInfoList.get(j).getUniqueToChina());
    //                if (butterflyInfoList.get(j).getImagePath()!=null)
    //                nameList.get(i).setImagePath((String) new DownImage(butterflyInfoList.get(j)).execute(butterflyInfoList.get(j).getImageUrl()).get());
    //                 j++;
    //                if (nameList.get(i).save())
    //                    count++;
    //                if (j==butterflyInfoList.size())
    //                    break;
    //            }
    //        }
    ////        Log.e("count", String.valueOf(count));
    //        if (count == butterflyInfoList.size()) {
    //            flag = true;
    //        }
    //        return flag;
    //    }

    public static int getNetWorkStatus(Context context) {
        int netWorkType = Constant.NETWORK_CLASS_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = Constant.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }

        return netWorkType;
    }

    public static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return Constant.NETWORK_CLASS_2_G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return Constant.NETWORK_CLASS_3_G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return Constant.NETWORK_CLASS_4_G;

            default:
                return Constant.NETWORK_CLASS_UNKNOWN;
        }
    }
}