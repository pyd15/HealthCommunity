package com.example.health_community.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.health_community.model.News;
import com.example.health_community.R;
import com.example.health_community.adapter.BaseAdapter;
import com.example.health_community.adapter.RecyclerViewAdapter;
import com.example.health_community.fragment.BlankFragment;
import com.example.health_community.fragment.HomeFragment;
import com.example.health_community.util.HttpAction;
import com.example.health_community.view.BottomNavigationViewHelper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/06
 * desc:
 */
public class BottomNavigationActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
        private BottomNavigationView bottomNavigationView;
    private List<View> viewList;
    private ViewPager mVpContent;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;

    private Toolbar toolbar;
    private Button button;
    private View headerView;
    private LinearLayout nav_header;
    private ActionBarDrawerToggle toggle;
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
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private List<Fragment> mFragmentList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.argb(33, 0, 0, 0));
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        floatingActionButton = findViewById(R.id.fab_web);
        //        button = findViewById(R.id.to_webview);
        //        button.setOnClickListener(this);
        try {
            HttpAction.parseJSONWithJSONObject(this, "https://pydwp.xyz/JSoupDemo/News/getJsonNews.do?news_type=1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        nav_header = headerView.findViewById(R.id.nav_header);
        nav_header.setOnClickListener(this);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mVpContent = findViewById(R.id.view_pager_bottom_navigation);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        HomeFragment mainFragment = new HomeFragment();
        BlankFragment mainFragment1 = new BlankFragment();
        BlankFragment mainFragment2 = new BlankFragment();
        BlankFragment mainFragment3 = new BlankFragment();
        mFragmentList.add(mainFragment);
        mFragmentList.add(mainFragment1);
        mFragmentList.add(mainFragment2);
        mFragmentList.add(mainFragment3);

        mVpContent.setAdapter(new BaseAdapter(getSupportFragmentManager(), mFragmentList));
        mVpContent.setOffscreenPageLimit(16);
        mVpContent.setCurrentItem(0);
                bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
                mVpContent.addOnPageChangeListener(pageChangeListener);


        // If BottomNavigationView has more than 3 items, using reflection to disable shift mode
                BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
    }

        private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //            ArgbEvaluator evaluator = new ArgbEvaluator();
                //            int evaluate = getResources().getColor(R.color.app_blue);
                //            if (position == 0) {
                //                evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.app_blue), getResources().getColor(R.color.app_green));
                //            } else if (position == 1) {
                //                evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.app_green), getResources().getColor(R.color.app_yellow));
                //            } else if (position == 2) {
                //                evaluate = (Integer) evaluator.evaluate(positionOffset, getResources().getColor(R.color.app_yellow), getResources().getColor(R.color.app_red));
                //            } else {
                //                evaluate = getResources().getColor(R.color.app_red);
                //            }
                //            ((View) viewPager.getParent()).setBackgroundColor(evaluate);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_blue);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_green);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_yellow);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_red);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_blue:
                    mVpContent.setCurrentItem(0);
                    return true;
                case R.id.bottom_navigation_green:
                    mVpContent.setCurrentItem(1);
                    return true;
                case R.id.bottom_navigation_yellow:
                    mVpContent.setCurrentItem(2);
                    return true;
                case R.id.bottom_navigation_red:
                    mVpContent.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
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
                intent = new Intent(BottomNavigationActivity.this, LoginActivity.class);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_call:
                Toast.makeText(this, "dfadfa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_friends:
                break;
            case R.id.nav_location:
                break;
            case R.id.nav_mail:
                break;
            case R.id.nav_task:
                break;
            default:
                return true;
        }
        drawerLayout.closeDrawers();
        return true;
    }
}
