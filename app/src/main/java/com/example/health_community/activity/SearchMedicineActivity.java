package com.example.health_community.activity;


import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.health_community.R;
import com.example.health_community.adapter.HeaderAdapter;
import com.example.health_community.adapter.searchMedicines.SearchMedicineAdapter;
import com.example.health_community.model.Medicine;
import com.example.health_community.model.Medicine_Copy;
import com.example.health_community.util.ActivityCollector;
import com.example.health_community.util.Constant;
import com.example.health_community.util.HttpUtil;
import com.example.health_community.util.ImageUtil;
import com.example.health_community.view.RecyclerOnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchMedicineActivity extends AppCompatActivity implements RecyclerOnItemClickListener.OnItemClickListener {

    @BindView(R.id.toolbar_med_recycler_view)
    Toolbar toolbar;
    @BindView(R.id.medicine_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_med_view)
    MaterialSearchView searchView;
    @BindView(R.id.swipsh_med_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private SuspensionDecoration medicineDecoration;
    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    /**
     * 顶部首字母
     */
    private HeaderAdapter adapter;
    LinearLayoutManager layoutManager;
    private SearchMedicineAdapter searchMedicineAdapter;
    private IntentFilter intentFilter;
    private NetworkChangeReciver networkChangeReciver;
    String address = Constant.INFO_URL;
    List<Medicine_Copy> medicineList = new ArrayList<>();

    List<Medicine> medicines = new ArrayList<>();

    private static boolean readDBFlag = false;
    private static boolean dataFlag = true;
    private static boolean netFlag = true;
    public static String[] images = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_recycler_view);
        ActivityCollector.AddActivity(this);
        ButterKnife.bind(this);
        //初始化资源文件
        ImageUtil.saveImageToLocal(this);
        try {
            initView();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReciver = new NetworkChangeReciver();
        registerReceiver(networkChangeReciver, intentFilter);
    }

    private void initView() throws JSONException {
        initToolBar();
        initRecyclerView();
        initSearchView();
        initSwipeRefreshLayout();
    }

    /**
     * init Toolbar
     */
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    private void initMedicines() {
        LitePal.initialize(getApplicationContext());
//        medicineList.clear();
        medicines.clear();
        medicines = LitePal.findAll(Medicine.class);
        Log.e("medicine", medicines.size() + "");
        Medicine_Copy medicine_copy;
        for (Medicine medicine : medicines) {
            medicine_copy = new Medicine_Copy();
            medicine_copy.setMed_id(medicine.getMed_id());
            medicine_copy.setMed_name(medicine.getMed_name()+"");
            medicine_copy.setMed_en_name(medicine.getMed_en_name()+"");
            medicine_copy.setMed_pre_img(medicine.getMed_pre_img()+"");
            medicine_copy.setMed_desc_img(medicine.getMed_desc_img()+"");
            if (medicine_copy.getMed_name().contains("腌")) {
                medicine_copy.setBaseIndexPinyin("y");
                medicine_copy.setBaseIndexTag("Y");
            }
            medicine_copy.setMed_desc(medicine.getMed_desc()+"");
            medicine_copy.setMed_company(medicine.getMed_company()+"");
            medicine_copy.setMed_type(medicine.getMed_type()+"");
            medicine_copy.setMed_rates(medicine.getMed_rates()+"");
            medicine_copy.setMed_pre_tag(medicine.getMed_pre_tag()+"");
            medicine_copy.setMed_desc_tag(medicine.getMed_desc_tag()+"");
            medicine_copy.setMed_related_tips(medicine.getMed_related_tips()+"");
            medicine_copy.setMed_component(medicine.getMed_component()+"");
            medicine_copy.setMed_useage(medicine.getMed_useage()+"");
            medicine_copy.setMed_avoid(medicine.getMed_avoid()+"");
            medicine_copy.setMed_attention(medicine.getMed_attention()+"");
            medicine_copy.setMed_adverse(medicine.getMed_adverse()+"");
            medicine_copy.setMed_purpose(medicine.getMed_purpose()+"");
            medicine_copy.setMed_href(medicine.getMed_href()+"");
            medicineList.add(medicine_copy);
            //                    medicines.add(medicine_copy);
        }
    }

    /**
     * init RecyclerView
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void initRecyclerView() throws JSONException {
        initMedicines();

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        //        medicines = HttpAction.parseJSONWithJSONObject(this, Constant.SEARCH_MEDICINE_URL + "感冒", "meds");

        searchMedicineAdapter = new SearchMedicineAdapter(medicineList);
        recyclerView.setAdapter(searchMedicineAdapter);
        adapter = new HeaderAdapter(searchMedicineAdapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(medicineDecoration = new SuspensionDecoration(this, medicineList));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//LinearLayoutManager.VERTICAL
        //使用indexBar
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar
        //indexbar初始化
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(layoutManager);//设置RecyclerView的LayoutManager
//        mIndexBar.setmSourceDatas(medicineList)//设置数据
//                .invalidate();
        searchMedicineAdapter.setDatas(medicineList);
        //        if (medicines.size() < 5) {
        //            mIndexBar.setVisibility(View.INVISIBLE);//若列表数目小于5则不显示索引栏
        //        }
//        medicineDecoration.setmDatas(medicineList);
    }

    /**
     * init SearchView
     */
    private void initSearchView() {
        searchView.setHint("请输入药品相关信息");
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //                final List<Medicine> filteredModelList = filter(medicines, query);
                //                SearchMedicineActivity.this.runOnUiThread(new Runnable() {
                //                    @Override
                //                    public void run() {
                //                        searchMedicineAdapter.setFilter(filteredModelList);
                //                        searchMedicineAdapter.setDatas(filteredModelList);
                ////                        searchMedicineAdapter.animateTo(filteredModelList);
                //                        searchMedicineAdapter.notifyDataSetChanged();
                //                        //                searchMedicineAdapter.notifyDataSetChanged();
                //                        Toast.makeText(SearchMedicineActivity.this, "fuck", Toast.LENGTH_SHORT).show();
                //                        recyclerView.scrollToPosition(0);
                //                    }
                //                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Medicine_Copy> filteredModelList = filter(medicineList, newText);
                searchMedicineAdapter.setFilter(filteredModelList);
                //                        searchMedicineAdapter.setDatas(filteredModelList);
                searchMedicineAdapter.animateTo(filteredModelList);
                //                        searchMedicineAdapter.notifyDataSetChanged();
                //                        //                searchMedicineAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
                //                medicines.clear();
                //                medicines= filter(medicines, newText);
                //                SearchMedicineActivity.this.runOnUiThread(new Runnable() {
                //                    @Override
                //                    public void run() {
                ////                        searchMedicineAdapter.setFilter(filteredModelList);
                //                        searchMedicineAdapter.setDatas(medicines);
                ////                        searchMedicineAdapter.setDatas(filteredModelList);
                //                        //                        searchMedicineAdapter.animateTo(filteredModelList);
                //                        searchMedicineAdapter.notifyDataSetChanged();
                //                        //                searchMedicineAdapter.notifyDataSetChanged();
                //                        Toast.makeText(SearchMedicineActivity.this, "fuck", Toast.LENGTH_SHORT).show();
                ////                        recyclerView.scrollToPosition(0);
                //                    }
                //                });
                //                final List<Medicine> filteredModelList = filter(medicines, newText);
                //                //reset
                //                searchMedicineAdapter.setFilter(filteredModelList);
                //                searchMedicineAdapter.animateTo(filteredModelList);
                ////                searchMedicineAdapter.notifyDataSetChanged();
                //                recyclerView.scrollToPosition(0);
                //                final List<Medicine> filteredModelList = filter(medicines, newText);
                //                //reset
                //                searchMedicineAdapter.setFilter(filteredModelList);
                //                searchMedicineAdapter.animateTo(filteredModelList);
                //                recyclerView.scrollToPosition(0);
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                //                searchMedicineAdapter.setFilter(medicines);
            }
        });
    }

    //    /**
    //     * init IndexBar
    //     */
    //    private void initIndexBar() {
    //        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
    //                .setNeedRealIndex(true)//设置需要真实的索引
    //                .setmLayoutManager(layoutManager);//设置RecyclerView的LayoutManager
    //        mIndexBar.setmSourceDatas(medicines)//设置数据
    //                .invalidate();
    //        searchMedicineAdapter.setDatas(medicines);
    //        medicineDecoration.setmDatas(medicines);
    //    }

    /**
     * init SwiperRefreshLayout
     */
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            swipeRefreshLayout.setElevation((float) 0.0);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    refresh();
                } else {
                    Toast.makeText(SearchMedicineActivity.this, "无法刷新！", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    /**
     * 读取assets下的txt文件，返回utf-8 String
     *
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName + ".txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer).trim();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

    private void refresh() {
        if (netFlag) {
            //            HttpUtil.sendOkHttpGetRequest(address, new Callback() {
            //                @Override
            //                public void onResponse(Call call, Response response) throws IOException {
            //                    final String responeData = response.body().string();
            //                    String oldData = SPUtils.getPrefString(Constants.OLD_DATA, null).trim();
            //                    if (!responeData.equals(oldData)) {
            //                        String handlerespone = HttpAction.handleResponse(oldData, responeData);
            //                        boolean result = false;
            //                        try {
            //                            result = HttpAction.parseJSONWithGSON(handlerespone);
            //                        } catch (ExecutionException e) {
            //                            e.printStackTrace();
            //                        } catch (InterruptedException e) {
            //                            e.printStackTrace();
            //                        }
            //                        if (result) {
            //                            SearchMedicineActivity.this.runOnUiThread(new Runnable() {
            //                                @Override
            //                                public void run() {
            //                                    SPUtils.removeData(Constants.OLD_DATA);
            //                                    SPUtils.setPrefString(Constants.OLD_DATA, responeData);
            //                                    initMedicines();
            //                                    searchMedicineAdapter.notifyDataSetChanged();
            //                                    initIndexBar();
            //                                    swipeRefreshLayout.setRefreshing(false);
            //                                    Toast.makeText(SearchMedicineActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
            //                                }
            //                            });
            //                        } else {
            //                            SearchMedicineActivity.this.runOnUiThread(new Runnable() {
            //                                @Override
            //                                public void run() {
            //                                    swipeRefreshLayout.setRefreshing(false);
            //                                    Toast.makeText(SearchMedicineActivity.this, "无更新！", Toast.LENGTH_SHORT).show();
            //                                }
            //                            });
            //                        }
            //                    } else {
            //                        SearchMedicineActivity.this.runOnUiThread(new Runnable() {
            //                            @Override
            //                            public void run() {
            //                                swipeRefreshLayout.setRefreshing(false);
            //                                Toast.makeText(SearchMedicineActivity.this, "没有新数据！", Toast.LENGTH_SHORT).show();
            //                            }
            //                        });
            //                    }
            //                }

            //                @Override
            //                public void onFailure(Call call, IOException e) {
            //                    e.printStackTrace();
            //                    SearchMedicineActivity.this.runOnUiThread(new Runnable() {
            //                        @Override
            //                        public void run() {
            //                            swipeRefreshLayout.setRefreshing(false);
            //                            Toast.makeText(SearchMedicineActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
            //                        }
            //                    });
            //                }
            //            });
            HttpUtil.sendOkHttpGetRequest(Constant.SEARCH_MEDICINE_URL + "感冒", new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(SearchMedicineActivity.this, "刷新失败!", Toast.LENGTH_SHORT).show();
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
                        List<Medicine> list = new Gson().fromJson(data.toString(), new TypeToken<List<Medicine>>() {
                        }.getType());
                        medicines.clear();
                        medicines.addAll(list);
                        //                    SPUtils.setPreList("news", list);
                        //                    Log.e("test", SPUtils.getPrefString(setString,"?????"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SearchMedicineActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            searchMedicineAdapter.setDatas(medicineList);
                            searchMedicineAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                            Toast.makeText(SearchMedicineActivity.this, "刷新成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            Toast.makeText(SearchMedicineActivity.this, "网络无连接!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReciver);
    }

    /**
     * 筛选逻辑
     *
     * @param medicineList
     * @param query
     * @return
     */
    private List<Medicine_Copy> filter(List<Medicine_Copy> medicineList, String query) {

        //        "category like ?", "%" + category + "%"
        //LitePal.where("category like ?", "%" + category + "%").find(Book.class);
        final List<Medicine_Copy> filteredModelList = new ArrayList<>();
        //        final List<Medicine> filteredModelList = LitePal.where("med_desc like ?" + "%" + query + "%"
        //                + "or med_name like ? " + "%" + query + "%"
        //                + "or med_related_tips like ? " + "%" + query + "%" +
        //                "or med_useage like ?" + "%" + query + "%" + "or med_component like ?" + "%" + query + "%"
        //        ).find(Medicine.class);
        //         "%"+searchContent+"%" or med_name like "%"+searchContent+"%"
        //        or med_related_tips like "%"+searchContent+"%" or med_useage like "%"+searchContent+"%"
        //        List<Medicine> filteredModelList;
        for (Medicine_Copy medicine : medicineList) {
            if (medicine.getMed_name().contains(query) || medicine.getMed_desc().contains(query)
                    || medicine.getMed_related_tips().contains(query) || medicine.getMed_component().contains(query)
                    || medicine.getMed_purpose().contains(query) || medicine.getMed_attention().contains(query)) {
                filteredModelList.add(medicine);
            }
        }
        Log.e("filter", filteredModelList.size() + "");

        //        HttpUtil.sendOkHttpGetRequest(Constant.SEARCH_MEDICINE_URL + query, new Callback() {
        //            @Override
        //            public void onFailure(okhttp3.Call call, IOException e) {
        //            }
        //
        //            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        //            @Override
        //            public void onResponse(okhttp3.Call call, Response response) throws IOException {
        //                String responseData = response.body().string();
        //                try {
        //                    JSONObject jsonObject = new JSONObject(responseData);
        //                    JSONArray data = jsonObject.getJSONArray("data");
        //                    List<Medicine> list = new Gson().fromJson(data.toString(), new TypeToken<List<Medicine>>() {
        //                    }.getType());
        //                    filteredModelList.addAll(list);
        //                    Log.e("size", list.size() + "");
        //                    //                    Log.e("fasdfs", list.get(0).getMed_attention());
        //                    //                    SPUtils.setPreList("news", list);
        //                    //                    Log.e("test", SPUtils.getPrefString(setString,"?????"));
        //
        //                } catch (JSONException e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //        });
        return filteredModelList;
    }

    /**
     * 搜索按钮
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 返回按钮处理
     */
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();

        } else {
            super.onBackPressed();
            finish();
            //实现淡入淡出的切换效果
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    /**
     * 筛选传递
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class NetworkChangeReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                netFlag = true;
            } else {
                netFlag = false;
                Toast.makeText(context, "无法连接至服务器，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Medicine_Copy medicine = medicineList.get(position);
        Intent intent = new Intent(this, MedInfoActivity.class);
        //        intent.putExtra("butterflyName", butterflyInfo.getName());
        intent.putExtra(Constant.MEDINCINE_INFO, medicine);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}