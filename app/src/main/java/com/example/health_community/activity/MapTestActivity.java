package com.example.health_community.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.health_community.R;

public class MapTestActivity extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLoc = true;//定义第一次启动
    private MyLocationConfiguration.LocationMode mCurrentMode;//定义当前定位模式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());//初始化地图SDK
        setContentView(R.layout.activity_map_test);
        mapView = findViewById(R.id.map_view);
        baiduMap = mapView.getMap();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //设置每一秒获取一次location信息
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,//指定GPS定位提供者
                1000,
                1,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //GPS信息发生改变时，更新位置
                        locationUpdates(location);
                    }

                    //位置状态发生改变时触发
                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                    }

                    //定位提供者启动时触发
                    @Override
                    public void onProviderEnabled(String s) {
                    }

                    //定位提供者关闭时触发
                    @Override
                    public void onProviderDisabled(String s) {

                    }
                }

        );
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationUpdates(location);
    }

    //获取指定的查询信息
    private void locationUpdates(Location location) {
        if (location != null) {
            StringBuilder stringBuilder = new StringBuilder();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());//获取当前经纬度
            //若为第一次定位
            if (isFirstLoc) {
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);//更新坐标位置
                baiduMap.animateMapStatus(mapStatusUpdate);//设置地图位置
                isFirstLoc = true;//取消第一次定位
            }
            //构造定位数据
            MyLocationData locationData = new MyLocationData.Builder()
                    .accuracy(location.getAccuracy())//设置精度
                    .direction(100)//设置获取的方向信息，顺时针0-360
                    .latitude(location.getLatitude())//设置纬度坐标
                    .longitude(location.getLongitude())//设置经度坐标
                    .build();
            //设置定位数据
            baiduMap.setMyLocationData(locationData);
            //设置自定义定位图标
            BitmapDescriptor currentMarker = BitmapDescriptorFactory.fromResource(R.drawable.cardio_machine);
            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;//设置定位模式
            //设置位置构造方式，添加定位模式和定位图标
            MyLocationConfiguration configuration = new MyLocationConfiguration(mCurrentMode, true, currentMarker);
            //地图显示定位图标
            baiduMap.setMyLocationConfigeration(configuration);
        } else {
            Log.i("Location", "未获取到GPS信息！");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        baiduMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }
}
