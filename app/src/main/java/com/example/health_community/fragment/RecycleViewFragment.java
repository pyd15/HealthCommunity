package com.example.health_community.fragment;


import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.health_community.R;
import com.example.health_community.view.interf.NewsSeparated;
import com.example.health_community.adapter.RecyclerViewAdapter;
import com.example.health_community.model.News;
import com.example.health_community.util.Constant;
import com.example.health_community.util.HttpAction;
import com.example.health_community.util.SPUtils;
import com.example.health_community.view.ItemTouchHelperCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecycleViewFragment extends Fragment implements NewsSeparated {


    private View view;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    Context context;

    private int color = 0;
    private List<String> data;
    private List<News> news;
    private List<News> allNews;
    private News insertData;
    private boolean loading,isNetWork;
    private int loadTimes;
    private int count;
    private String category, adminName;

    public RecycleViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
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
//        news = LitePal.where("category like ?", "%" + category + "%").find(Book.class);
        //        allNews = LitePal.findAll(Book.class);
        String strJson = SPUtils.getPrefString("news", null);
        news=new Gson().fromJson(strJson, new TypeToken<List<News>>() {
        }.getType());
        if (news==null)
            Constant.isNetWork = false;
        else
            Constant.isNetWork = true;
        Log.e("dasfasdfdasfasdf", news.size() + "");
//        insertData =news.get(0);
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
        //        fab =view.findViewById(R.id.fab_recycler_view_frag);
        recyclerView = view.findViewById(R.id.recycler_view_recycler_view);

        //       initSearchView();
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

        adapter = new RecyclerViewAdapter(getActivity());
        adapter.setItems(news);
        adapter.addFooter();
        recyclerView.setAdapter(adapter);
        SPUtils.setPrefInt(Constant.RECYCLER_LIST, 1);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        //        Log.e("sp_key", sp.getString(Constant.USER_NAME, null));
//        adminName = SPUtils.getPrefString(Constant.ADMIN, null);
//        if (adminName != null && adminName.equals("admin")) {
//            mItemTouchHelper.attachToRecyclerView(recyclerView);
//        }
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_recycler_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
//            if (color > 4) {
//                color = 0;
//            }
//            adapter.setColor(++color);
            try {
                HttpAction.parseJSONWithJSONObject(context,"https://pydwp.xyz/JSoupDemo/News/getJsonNews.do?news_type=1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.clearItems();
            loading = false;
            String strJson = SPUtils.getPrefString("news", null);
            news=new Gson().fromJson(strJson, new TypeToken<List<News>>() {}.getType());
            adapter.addItems(news);
            adapter.addFooter();
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }, 2000));

        recyclerView.addOnScrollListener(scrollListener);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        //        Toast.makeText(context, "onResume", Toast.LENGTH_SHORT).show();
//        news.clear();
//        //        allNews.clear();
////        if (SPUtils.getPrefInt(Constant.RECYCLER_LIST, 1) > 1)
////            adapter.clear();
//        //        new Handler().postDelayed(new Runnable() {
//        //            @Override
//        //            public void run() {
//        initData();
//        adapter.setItems(news);
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
                try {
                    HttpAction.parseJSONWithJSONObject(context,"https://pydwp.xyz/JSoupDemo/News/getJsonNews.do?news_type=1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (loadTimes <= 2) {
                            adapter.removeFooter();
                            loading = false;
                            String strJson = SPUtils.getPrefString("news", null);
                            news=new Gson().fromJson(strJson, new TypeToken<List<News>>() {}.getType());
                            adapter.addItems(news);
                            adapter.addFooter();
                            loadTimes++;
                        } else {
                            adapter.removeFooter();
                            Snackbar.make(recyclerView, getString(R.string.no_more_data), Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    super.onDismissed(transientBottomBar, event);
                                    loading = false;
                                    adapter.addFooter();
                                }
                            }).show();
                        }
                    }
                }, 1500);

                loading = true;
            }
//            if (!loading && count == (linearLayoutManager.findLastVisibleItemPosition() + 1)) {
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("count", String.valueOf(count));
//                        if (loadTimes <= 2) {
//                            adapter.removeFooter();
//                            loading = false;
//                            String strJson = SPUtils.getPrefString("news", null);
//                            news=new Gson().fromJson(strJson, new TypeToken<List<News>>() {}.getType());
//                            adapter.addItems(news);
//                            adapter.addFooter();
//                            count++;
//                            loadTimes++;
//                        } else {
//                            adapter.removeFooter();
//                            Snackbar.make(recyclerView, getString(R.string.no_more_data), Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
//                                @Override
//                                public void onDismissed(Snackbar transientBottomBar, int event) {
//                                    super.onDismissed(transientBottomBar, event);
//                                    loading = false;
//                                    adapter.addFooter();
//                                }
//                            }).show();
//                        }
//                    }
//                }, 1500);
//
//                loading = true;
//            }
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

}
//        adapter.setRecyclerViewOnItemClickListener(new RecyclerViewAdapter.RecyclerViewOnItemClickListener() {
//            @Override
//            public void onItemClickListener(View view, int position) {
//                //点击事件
//                //设置选中的项
//                adapter.setSelectItem(position);
//            }
//
//            @Override
//            public boolean onItemLongClickListener(View view, int position) {
//                //长按事件
//                adapter.setShowBox();
//                //设置选中的项
//                adapter.setSelectItem(position);
//                adapter.getMap();
//                adapter.notifyDataSetChanged();
//                return true;
//            }
//        });
