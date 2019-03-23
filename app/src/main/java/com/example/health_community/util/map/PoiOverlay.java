package com.example.health_community.util.map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.example.health_community.R;
import com.example.health_community.activity.LoginActivity;
import com.example.health_community.activity.PoiSearchActivity;
import com.example.health_community.model.BigDepartment;
import com.example.health_community.util.Constant;
import com.example.health_community.util.SPUtils;
import com.example.health_community.view.DIYDialog;
import com.example.health_community.view.InfoView;
import com.syd.oden.circleprogressdialog.core.CircleProgressDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 用于显示poi的overly
 */
public class PoiOverlay extends OverlayManager {

    private static final int MAX_POI_SIZE = 10;

    private PoiResult mPoiResult = null;
    Context context;
    private InfoWindow infoWindow;
    List<BigDepartment> bigDepartments = new ArrayList<>();
    CircleProgressDialog circleProgressDialog ;
    private static final int REQUEST_COMPELETE = 1;
    private PoiSearchActivity activity;
    private List<BigDepartment> bigDepartmentList;
    private List<PoiInfo> poiInfos = new ArrayList<>();
    private DIYDialog diyDialog = new DIYDialog();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_COMPELETE:
                    circleProgressDialog.dismiss();
//                    diyDialog.create(activity,context,bigDepartmentList);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 构造函数
     * 
     * @param baiduMap   该 PoiOverlay 引用的 BaiduMap 对象
     */
    public PoiOverlay(BaiduMap baiduMap) {
        super(baiduMap);
//        this.context = context;
    }

    public void setContext(Context context) {
        this.context=context;
        circleProgressDialog = new CircleProgressDialog(context);
    }
    /**
     * 设置POI数据
     * 
     * @param poiResult    设置POI数据
     */
    public void setData(PoiResult poiResult) {
        this.mPoiResult = poiResult;
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {
        if (mPoiResult == null || mPoiResult.getAllPoi() == null) {
            return null;
        }

        List<OverlayOptions> markerList = new ArrayList<>();
        int markerSize = 0;

        //在地图上添加检索结果的标记
        for (int i = 0; i < mPoiResult.getAllPoi().size() && markerSize < MAX_POI_SIZE; i++) {
            if (mPoiResult.getAllPoi().get(i).location == null) {
                continue;
            }
//            Log.e("address", mPoiResult.getAllPoi().get(i).address);
            markerSize++;
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            bundle.putParcelable(i+"",mPoiResult.getAllPoi().get(i));
            markerList.add(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + markerSize + ".png"))
                .extraInfo(bundle)
                .position(mPoiResult.getAllPoi().get(i).location));
            
        }

        return markerList;
    }

    /**
     * 获取该PoiOverlay的poi数据
     * 
     * @return     POI数据
     */
    public PoiResult getPoiResult() {
        return mPoiResult;
    }

    /**
     * 覆写此方法以改变默认点击行为
     * 
     * @param i    被点击的poi在
     *             {@link com.baidu.mapapi.search.poi.PoiResult#getAllPoi()} 中的索引
     * @return     true--事件已经处理，false--事件未处理
     */
    public boolean onPoiClick(int i) {
        if (mPoiResult.getAllPoi() != null
                && mPoiResult.getAllPoi().get(i) != null) {
            Toast.makeText(BMapManager.getContext(),
                    mPoiResult.getAllPoi().get(i).name, Toast.LENGTH_LONG)
                    .show();
        }
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        activity = (PoiSearchActivity) context;
        if (mOverlayList.contains(marker)) {
            Log.e("contain", marker.getExtraInfo().getInt("index")+"");
            final LatLng ll = marker.getPosition();
            int index=marker.getExtraInfo().getInt("index");
            PoiInfo result=marker.getExtraInfo().getParcelable(index+"");
            diyDialog.setHospital(result.name);
            //动态生成一个view对象，用户在地图中显示InfoWindow
            final InfoView infoView = new InfoView(context);
            infoView.setBackgroundResource(R.drawable.button);
            infoView.setTv1(result.name, 14, R.color.orange_primary_dark);
            infoView.setTv2(result.address, 10, Color.BLACK);
            infoView.setTv3(result.phoneNum, 10, Color.BLACK);
            infoView.setBackgroundResource(R.drawable.info_back);
            infoView.setBaiduMap(mBaiduMap);
            infoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (SPUtils.getPrefBoolean(Constant.LOGIN_SUCCESS, false)) {
                        diyDialog.create(activity,context,bigDepartmentList);
                    } else {
                        new android.support.v7.app.AlertDialog.Builder(context).setTitle("登录后才能预约挂号哟，现在去登录吧？")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                        //                        ActivityCollector.finishAll();
                                                Intent intent = new Intent(context, LoginActivity.class);
                                                context.startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                    }
//                    diyDialog.create(activity,context,bigDepartmentList);
//                    circleProgressDialog.showDialog();
//                    HttpUtil.sendOkHttpGetRequest("https://www.pydwp.xyz/JSoupDemo/hospital/getRandomHospitalAndDepartments.do", new Callback() {
////                            HttpUtil.sendOkHttpGetRequest("https://www.pydwp.xyz/JSoupDemo/hospital/getHospitalAndDepartments.do?hos_name=广州协佳医院", new Callback() {
//                                @Override
//                                public void onFailure(okhttp3.Call call, IOException e) {
//                                }
//
//                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                                @Override
//                                public void onResponse(okhttp3.Call call, Response response) throws IOException {
//                                    String responseData = response.body().string();
//                                    try {
//                                        bigDepartmentList = HttpAction.parseJSONWithGSON(responseData);
////                                        bigDepartments.addAll(bigDepartmentList);
//                                        if (bigDepartmentList.size() > 0) {
//                                            Message message = new Message();
//                                            message.what = REQUEST_COMPELETE;
//                                            handler.sendMessage(message);
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                            });
                }
            });
            //初始化infoWindow，最后那个参数表示显示的位置相对于覆盖物的竖直偏移量，这里也可以传入一个监听器
            infoWindow = new InfoWindow(infoView, ll, -100);
            mBaiduMap.showInfoWindow(infoWindow);//显示此infoWindow

            //让地图以被点击的覆盖物为中心
            MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
            //                mBaiduMap.setMapStatus(status);
            //以动画方式更新地图状态，动画耗时 500 ms
            mBaiduMap.animateMapStatus(status, 500);
//            return true;
            return false;//返回false则不继续
        }

//        if (marker.getExtraInfo() != null) {
//            Log.e("getExtraInfo", marker.getExtraInfo().toString());
//            return onPoiClick(marker.getExtraInfo().getInt("index"));
//
//        }

        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }

    public List<PoiInfo> sortPoi(List<PoiInfo> poiInfos) {
        Comparator<PoiInfo> comparator = new Comparator<PoiInfo>(){
            public int compare(PoiInfo s1, PoiInfo s2) {
                //先排年龄
                if(s1.distance!=s2.distance){
                    return s1.distance-s2.distance;
                }else return 1;
            }
        };
        Collections.sort(poiInfos,comparator);
        return poiInfos;
    }
}
