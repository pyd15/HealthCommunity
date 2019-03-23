package com.example.health_community.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.health_community.R;
import com.example.health_community.adapter.AppointAdapter;
import com.example.health_community.model.AppointMent;
import com.example.health_community.model.News;
import com.example.health_community.util.Constant;
import com.example.health_community.util.SPUtils;
import com.example.health_community.view.ItemTouchHelperCallback;
import com.example.health_community.view.interf.AppointType;

import org.litepal.LitePal;

import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/21
 * desc:
 */
public class AppointFragment extends Fragment implements AppointType {



    private View view;
    private RecyclerView recyclerView;
    private AppointAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    Context context;

    private int color = 0;
    private List<String> data;
    private List<AppointMent> appointMents;
    private List<AppointMent> allNews;
    private News insertData;
    private boolean loading, isNetWork;
    private int loadTimes;
    private int count;
    private String type, adminName;

    public AppointFragment() {
        // Required empty public constructor
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recyclerview_frag, container, false);
        //        sp = getActivity().getSharedPreferences(Constant.SP_KEY, Activity.MODE_PRIVATE);
        //        editor=getActivity().getSharedPreferences(Constant.SP_KEY, Activity.MODE_PRIVATE).edit();
        //        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //        ActionBar actionBar=((AppCompatActivity)getActivity()).getSupportActionBar();
        //        if (actionBar != null) {
        //            actionBar.setDisplayHomeAsUpEnabled(true);
        //            actionBar.setHomeAsUpIndicator(R.drawable.ic_format_list_bulleted_black_24dp);
        //        }
        //        setHasOptionsMenu(true);
        initData();
        if (Constant.isNetWork)
            initView();
        return view;
    }

    private void initData() {
        //        appointMents = LitePal.where("type like ?", "%" + type + "%").find(Book.class);
        //        allNews = LitePal.findAll(Book.class);
        //        String strJson = SPUtils.getPrefString("appointMents", null);
        //        appointMents=new Gson().fromJson(strJson, new TypeToken<List<News>>() {
        //        }.getType());
        //        try {
        //            HttpAction.parseJSONWithJSONObject(context, Constant.GET_NEWS_URL + type, type);
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }
        appointMents = LitePal.order("id desc").find(AppointMent.class);
        if (appointMents == null)
            Constant.isNetWork = false;
        else
            Constant.isNetWork = true;
        //        Log.e("dasfasdfdasfasdf", appointMents.size() + "");
        //        insertData =appointMents.get(0);
        loadTimes = 0;
    }

    //    @Override
    //    public void onAttach(Activity activity) {
    //        super.onAttach(activity);
    //        if (activity instanceof MainActivity) {
    //            MainActivity bottomNavigationActivity = (MainActivity) activity;
    //            toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
    //            toolbar.setTitle("图书列表");
    //        }
    //    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recycler_view_recycler_view);

        if (getScreenWidthDp() >= 1200) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else if (getScreenWidthDp() >= 800) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        adapter = new AppointAdapter(getActivity());
        adapter.setItems(appointMents);
        adapter.addFooter();
        recyclerView.setAdapter(adapter);
        SPUtils.setPrefInt(Constant.RECYCLER_LIST, 1);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter,context);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(recyclerView);
        //        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter,context);
        //        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_recycler_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
//            appointMents = LitePal.findAll(AppointMent.class);
            initData();
            adapter.clearItems();
            loading = false;
            adapter.addItems(appointMents);
            adapter.addFooter();
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }, 2000));

        recyclerView.addOnScrollListener(scrollListener);
//        IntentFilter filter = new IntentFilter(Constant.APPOINT_REFRESH);
//        context.registerReceiver(broadcastReceiver, filter);
    }

    //    @Override
    //    public void onResume() {
    //        super.onResume();
    //        //        Toast.makeText(context, "onResume", Toast.LENGTH_SHORT).show();
    //        appointMents.clear();
    //        //        allNews.clear();
    ////        if (SPUtils.getPrefInt(Constant.RECYCLER_LIST, 1) > 1)
    ////            adapter.clear();
    //        //        new Handler().postDelayed(new Runnable() {
    //        //            @Override
    //        //            public void run() {
    //        initData();
    //        adapter.setItems(appointMents);
    //        adapter.addFooter();
    //        adapter.notifyDataSetChanged();
    //        //            }
    //        //        }, 100);
    //        SPUtils.setPrefInt(Constant.RECYCLER_LIST, SPUtils.getPrefInt(Constant.RECYCLER_LIST, 1) + 1);
    //    }


    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            count = linearLayoutManager.getItemCount();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 1)) {
//                appointMents = LitePal.findAll(AppointMent.class);
                initData();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (loadTimes <= 2) {
                            adapter.removeFooter();
                            loading = false;
                            adapter.addItems(appointMents);
                            adapter.addFooter();
                            loadTimes++;
                        } else {
                            if (adapter.getItemCount()==appointMents.size())
                            adapter.removeFooter();
                            Snackbar.make(recyclerView, getString(R.string.no_more_data), Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    super.onDismissed(transientBottomBar, event);
                                    loading = false;
                                    adapter.addFooter();
                                }

                            }).show();
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, 1500);

                loading = true;
            }
        }
    };

    //    @Override
    //    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    //    fragment中调用此方法会只显示fragment自己的toolbar菜单栏
    //        menu.clear();
    //        super.onCreateOptionsMenu(menu, inflater);
    //        inflater.inflate(R.menu.menu1, menu);
    //        inflater.inflate(R.menu.toolbar, menu);
    //        MenuItem item = menu.findItem(R.id.action_search_main2);
    //        searchView.setMenuItem(item);
    //    }

    private int getScreenWidthDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    @Override
    public void onStart() {
        super.onStart();
        appointMents.clear();
        initData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(context, "appoint", Toast.LENGTH_SHORT).show();
        appointMents.clear();
        adapter.clearItems();
        initData();
        adapter.setItems(appointMents);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        context.unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            adapter.notifyDataSetChanged();
            }
    };
}
