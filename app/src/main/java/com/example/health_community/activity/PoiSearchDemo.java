package com.example.health_community.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiDetailInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.health_community.R;
import com.example.health_community.util.map.PoiOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示poi搜索功能
 */
public class PoiSearchDemo extends FragmentActivity implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private BaiduMap mBaiduMap = null;
    /* 搜索关键字输入窗口 */
    private EditText editCity = null;
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int loadIndex = 0;

    private LatLng center = new LatLng(39.92235, 116.380338);
    private int radius = 100;
    private LatLng southwest = new LatLng( 39.92235, 116.380338 );
    private LatLng northeast = new LatLng( 39.947246, 116.414977);
    private LatLngBounds searchBound = new LatLngBounds.Builder().include(southwest).include(northeast).build();

    private int searchType = 0;  // 搜索的类型，在显示时区分

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisearch);
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        editCity = (EditText) findViewById(R.id.city);
        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
        sugAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.setThreshold(1);
        mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map))).getBaiduMap();

        /* 当输入关键字变化时，动态更新建议列表 */
        keyWorldsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length() <= 0) {
                    return;
                }

                /* 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新 */
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                    .keyword(cs.toString())
                    .city(editCity.getText().toString()));
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 响应城市内搜索按钮点击事件
     *
     * @param v    检索Button
     */
    public void searchButtonProcess(View v) {
        searchType = 1;

        String citystr = editCity.getText().toString();
        String keystr = keyWorldsView.getText().toString();

        mPoiSearch.searchInCity((new PoiCitySearchOption())
            .city(citystr)
            .keyword(keystr)
            .pageNum(loadIndex)
            .scope(1));
    }

    /**
     * 响应周边搜索按钮点击事件
     *
     * @param v    检索Button
     */
    public void  searchNearbyProcess(View v) {
        searchType = 2;
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
            .keyword(keyWorldsView.getText().toString())
            .sortType(PoiSortType.distance_from_near_to_far)
            .location(center)
            .radius(radius)
            .pageNum(loadIndex)
            .scope(1);

        mPoiSearch.searchNearby(nearbySearchOption);
    }

    public void goToNextPage(View v) {
        loadIndex++;
        searchButtonProcess(null);
    }

    /**
     * 响应区域搜索按钮点击事件
     *
     * @param v    检索Button
     */
    public void searchBoundProcess(View v) {
        searchType = 3;
        mPoiSearch.searchInBound(new PoiBoundSearchOption()
            .bound(searchBound)
            .keyword(keyWorldsView.getText().toString())
            .scope(1));

    }


    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     *
     * @param result    Poi检索结果，包括城市检索，周边检索，区域检索
     */
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(PoiSearchDemo.this, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();

            switch( searchType ) {
                case 2:
                    showNearbyArea(center, radius);
                    break;
//                case 3:
//                    showBound(searchBound);
//                    break;
                default:
                    break;
            }

            return;
        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";

            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }

            strInfo += "找到结果";
            Toast.makeText(PoiSearchDemo.this, strInfo, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
     * V5.2.0版本之后，还方法废弃，使用{@link #onGetPoiDetailResult(PoiDetailSearchResult)}代替
     * @param result    POI详情检索结果
     */
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(PoiSearchDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PoiSearchDemo.this,
                result.getName() + ": " + result.getAddress(),
                Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
        if (poiDetailSearchResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(PoiSearchDemo.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
            List<PoiDetailInfo> poiDetailInfoList = poiDetailSearchResult.getPoiDetailInfoList();
            if (null == poiDetailInfoList || poiDetailInfoList.isEmpty()) {
                Toast.makeText(PoiSearchDemo.this, "抱歉，检索结果为空", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < poiDetailInfoList.size(); i++) {
                PoiDetailInfo poiDetailInfo = poiDetailInfoList.get(i);
                if (null != poiDetailInfo) {
                    Toast.makeText(PoiSearchDemo.this,
                        poiDetailInfo.getName() + ": " + poiDetailInfo.getAddress(),
                        Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    /**
     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
     *
     * @param res    Sug检索结果
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }

        List<String> suggest = new ArrayList<>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }

        sugAdapter = new ArrayAdapter<>(PoiSearchDemo.this, android.R.layout.simple_dropdown_item_1line,
            suggest);
        keyWorldsView.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();
    }

    private class MyPoiOverlay extends PoiOverlay {
        MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
            // }
            return true;
        }
    }

    /**
     * 对周边检索的范围进行绘制
     *
     * @param center    周边检索中心点坐标
     * @param radius    周边检索半径，单位米
     */
    public void showNearbyArea(LatLng center, int radius) {
        BitmapDescriptor centerBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        MarkerOptions ooMarker = new MarkerOptions().position(center).icon(centerBitmap);
        mBaiduMap.addOverlay(ooMarker);

        OverlayOptions ooCircle = new CircleOptions().fillColor( 0xCCCCCC00 )
            .center(center)
            .stroke(new Stroke(5, 0xFFFF00FF ))
            .radius(radius);

        mBaiduMap.addOverlay(ooCircle);
    }

//    /**
//     * 对区域检索的范围进行绘制
//     *
//     * @param bounds     区域检索指定区域
//     */
//    public void showBound( LatLngBounds bounds) {
//        BitmapDescriptor bdGround = BitmapDescriptorFactory.fromResource(R.drawable.ground_overlay);
//
//        OverlayOptions ooGround = new GroundOverlayOptions()
//            .positionFromBounds(bounds)
//            .image(bdGround)
//            .transparency(0.8f)
//            .zIndex(1);
//
//        mBaiduMap.addOverlay(ooGround);
//
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(bounds.getCenter());
//        mBaiduMap.setMapStatus(u);
//
//        bdGround.recycle();
//    }
}
