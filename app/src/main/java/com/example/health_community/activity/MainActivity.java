package com.example.health_community.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.health_community.News;
import com.example.health_community.R;
import com.example.health_community.adapter.RecyclerViewAdapter;
import com.example.health_community.util.HttpAction;
import com.example.health_community.util.SPUtils;
import com.example.health_community.view.ItemTouchHelperCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mVpContent;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private RecyclerViewAdapter adapter;
    private int color = 0;
//    private List<String> data;
    private List<News> news;
    private News insertData;
    private boolean loading;
    private int loadTimes;
    private Toolbar toolbar;
    private Button button;
    private View headerView;
    private LinearLayout nav_header;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SqlScoutServer.create(this, getPackageName());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        floatingActionButton = findViewById(R.id.fab_web);
        button = findViewById(R.id.btn_medicines);
        button.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //此处用于处理点击任意菜单项后的逻辑
                drawerLayout.closeDrawers();
                return true;
            }
        });
        headerView = navigationView.getHeaderView(0);
        nav_header = headerView.findViewById(R.id.nav_header);
        nav_header.setOnClickListener(this);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
//                //添加新按钮
//                SnackBarUtil.SnackbarAddView(snackbar, R.layout.snackbarbtn, 1);
//                //设置新添加的按钮监听器
//                SnackBarUtil.SetAction(snackbar, R.id.cancel_btn, "取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        snackbar.dismiss();
//                    }
//                });
//                //设置snackbar自带的按钮监听器
//                snackbar.setAction("确定", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, "删除联系人成功", Toast.LENGTH_SHORT).show();
//                    }
//
//                });
//                snackbar.show();
//            }
//        });
        initData();
        initView();
    }

    private void initData() {
//        data = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            data.add(i + "");
//        }
//        news = SPUtils.getPreList("news", new News());
        //        if (null == strJson) {
        //            return list;
        //        }
        String strJson = SPUtils.getPrefString("news", null);
        news=new Gson().fromJson(strJson, new TypeToken<List<News>>() {
        }.getType());
        insertData =news.get(0);
        loadTimes = 0;
    }

    private void initView() {
        fab = findViewById(R.id.fab_web);
        mRecyclerView = findViewById(R.id.recycler_view_recycler_view);


        if (getScreenWidthDp() >= 1200) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else if (getScreenWidthDp() >= 800) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(linearLayoutManager);
        }

        adapter = new RecyclerViewAdapter(this);
        mRecyclerView.setAdapter(adapter);
        adapter.setItems(news);
        adapter.addFooter();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                adapter.addItem(linearLayoutManager.findFirstVisibleItemPosition() + 1, insertData);
            }
        });

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_recycler_view);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (color > 4) {
                            color = 0;
                        }
                        adapter.setColor(++color);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);

            }
        });

        mRecyclerView.addOnScrollListener(scrollListener);
    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (!loading && linearLayoutManager.getItemCount() == (linearLayoutManager.findLastVisibleItemPosition() + 1)) {
                try {
                    HttpAction.parseJSONWithJSONObject(MainActivity.this,"https://pydwp.xyz/JSoupDemo/News/getJsonNews.do?news_type=1");
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
                            Snackbar.make(mRecyclerView, getString(R.string.no_more_data), Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
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
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载toolbar菜单文件
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "Backup", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.delete:
//                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.settings:
                // intent.setType("text/plain"); //纯文本
                /*
                 * 图片分享 it.setType("image/png"); 　//添加图片 File f = new
                 * File(Environment.getExternalStorageDirectory()+"/name.png");
                 * Uri uri = Uri.fromFile(f); intent.putExtra(Intent.EXTRA_STREAM,
                 * uri);
                 */
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully shar e my message through my app");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_medicines:
                intent = new Intent(this, WebNewsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_header:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
        }
    }

    private int getScreenWidthDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

    public void tryJsoup() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 192; i++) {
                    try {
                        Document document = Jsoup.connect("http://jiankang.163.com/special/zhutierji/?type=5").get();
                        Elements els = document.select("div.zhuti_box > ul > *");
                        Log.e("els.size", String.valueOf(els.size()) + "dasdf");
                        for (int j = 0; j < els.size(); j++) {
                            Elements el = els.get(j).select("a");
                            String href = el.attr("href");
                            //拿到这条段子的详情链接
                            Log.e("链接", href);
                            Log.e("text", el.text());
                            Log.e("origin", document.toString() + "1");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }


}
