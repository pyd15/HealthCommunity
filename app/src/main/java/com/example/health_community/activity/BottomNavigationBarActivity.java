package com.example.health_community.activity;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.health_community.R;
import com.example.health_community.adapter.BaseAdapter;
import com.example.health_community.adapter.RecyclerViewAdapter;
import com.example.health_community.fragment.AppointFragment;
import com.example.health_community.fragment.BlankFragment;
import com.example.health_community.fragment.HomeFragment;
import com.example.health_community.fragment.InfoFragment;
import com.example.health_community.model.AppointMent;
import com.example.health_community.model.News;
import com.example.health_community.util.ActivityCollector;
import com.example.health_community.util.Constant;
import com.example.health_community.util.NormalUtil;
import com.example.health_community.util.SPUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationBarActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    //    private BottomNavigationView bottomNavigationView;
    private BottomNavigationBar bottomNavigationView;
    private List<View> viewList;
    private ViewPager mVpContent;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private Toolbar toolbar;
    private Button button;
    private View headerView;
    private LinearLayout nav_header;
    private ActionBarDrawerToggle toggle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private RecyclerViewAdapter adapter;
    private TextView nav_header_text;
    private int color = 0;
    //    private List<String> data;
    private List<News> news;
    private News insertData;
    private boolean loading;
    private int loadTimes;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private BottomNavigationItem bottomNavigationItem1;
    private BottomNavigationItem bottomNavigationItem2;
    private BottomNavigationItem bottomNavigationItem3;
    private BottomNavigationItem bottomNavigationItem4;
    private TextBadgeItem textBadgeItem;
    private Intent intent;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_bar);
        ActivityCollector.AddActivity(this);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.argb(33, 0, 0, 0));
        initView();
        //        poiInfos = Objects.requireNonNull(getIntent().getExtras()).getParcelableArrayList(Constant.NEARBY_HOSPITAL);
        //        poiInfos = savedInstanceState.getParcelableArrayList(Constant.NEARBY_HOSPITAL);
        //        if (!Constant.isNetWork)
        //        Toast.makeText(this, "无法连接至服务器", Toast.LENGTH_LONG).show();
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
        //        try {
        //            HttpAction.parseJSONWithJSONObject(this, "https://pydwp.xyz/JSoupDemo/News/getJsonNews.do?news_type=1");
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        nav_header = headerView.findViewById(R.id.nav_header);
        nav_header_text = headerView.findViewById(R.id.text_nav_header);
        nav_header.setOnClickListener(this);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (SPUtils.getPrefBoolean(Constant.LOGIN_SUCCESS, false))
            nav_header_text.setText("欢迎回来，" + SPUtils.getPrefString(Constant.USER_NAME, "张三"));
        mVpContent = findViewById(R.id.view_pager_bottom_navigation);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        //值得一提，模式跟背景的设置都要在添加tab前面，不然不会有效果。
        bottomNavigationView
                .setActiveColor(R.color.colorPrimary)//选中颜色 图标和文字
                .setInActiveColor("#8e8e8e")//默认未选择颜色
                .setBarBackgroundColor("#ECECEC");//默认背景色
        bottomNavigationItem1 = new BottomNavigationItem(R.drawable.ic_home_black_24dp, "首页");
        bottomNavigationItem2 = new BottomNavigationItem(R.drawable.ic_import_contacts_black_24dp, "资讯");
        bottomNavigationItem3 = new BottomNavigationItem(R.drawable.ic_email_black_24dp, "消息");
        bottomNavigationItem4 = new BottomNavigationItem(R.drawable.ic_perm_identity_black_24dp, "我的");
        bottomNavigationView
                .addItem(bottomNavigationItem1)
                .addItem(bottomNavigationItem2)
                .addItem(bottomNavigationItem3)
                .addItem(bottomNavigationItem4)
                .setFirstSelectedPosition(0)//设置默认选择的按钮
                .initialise();//所有的设置需在调用该方法前完成
        //设置lab点击事件
        bottomNavigationView.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        mVpContent.setCurrentItem(0);
                        break;
                    case 1:
                        mVpContent.setCurrentItem(1);
                        break;
                    case 2:
                        mVpContent.setCurrentItem(2);
                        break;
                    case 3:
                        mVpContent.setCurrentItem(3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
        bottomNavigationView.setAutoHideEnabled(true);
        HomeFragment mainFragment = new HomeFragment();
        //        InfoFragment mainFragment = new InfoFragment();
        InfoFragment mainFragment1 = new InfoFragment();
        AppointFragment mainFragment2 = new AppointFragment();
        mainFragment2.setContext(this);
//        BlankFragment mainFragment2 = new BlankFragment();
        BlankFragment mainFragment3 = new BlankFragment();
        mFragmentList.add(mainFragment);
        mFragmentList.add(mainFragment1);
        mFragmentList.add(mainFragment2);
        mFragmentList.add(mainFragment3);

        mVpContent.setAdapter(new BaseAdapter(getSupportFragmentManager(), mFragmentList));
        mVpContent.setOffscreenPageLimit(12);
        mVpContent.setCurrentItem(0);
        List<String> permissionList = new ArrayList<>();
        mVpContent.addOnPageChangeListener(pageChangeListener);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            requestLocation();
        }

        IntentFilter filter = new IntentFilter(PoiSearchActivity.action);
        registerReceiver(broadcastReceiver, filter);
    }

    private void requestLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
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
                    bottomNavigationView.selectTab(0);
                    break;
                case 1:
                    bottomNavigationView.selectTab(1);
                    break;
                case 2:
                    bottomNavigationView.selectTab(2);
                    break;
                case 3:
                    bottomNavigationView.selectTab(3);
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
//                    textBadgeItem = new TextBadgeItem();
//                    bottomNavigationItem3.setBadgeItem(textBadgeItem);
                    textBadgeItem.setText("");
                    mVpContent.setCurrentItem(2);
                    //                    bottomNavigationItem3.setBadgeItem(new TextBadgeItem());
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
        switch (v.getId()) {
            case R.id.btn_medicines:
                intent = new Intent(this, WebNewsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_header:
                if (!SPUtils.getPrefBoolean(Constant.LOGIN_SUCCESS, false)) {
                    intent = new Intent(BottomNavigationBarActivity.this, LoginActivity.class);
                    startActivity(intent);
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    new AlertDialog.Builder(this).setTitle("重新登录？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SPUtils.setPrefBoolean(Constant.LOGIN_SUCCESS, false);
                                    nav_header_text.setText("点击进入登录界面");
                                    intent = new Intent(BottomNavigationBarActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                                    drawer.closeDrawer(GravityCompat.START);
                                }
                            })
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }

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
                Intent intent = new Intent(this, BottomNavigationActivity.class);
                startActivity(intent);
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

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuilder currentPosition = new StringBuilder();
            SPUtils.setPrefLong("latitude", (long) location.getLatitude());
            SPUtils.setPrefLong("longtitude", (long) location.getLongitude());
            SPUtils.setPrefString("province", location.getProvince());
            SPUtils.setPrefString("city", location.getCity());
            SPUtils.setPrefString("district", location.getDistrict());
            SPUtils.setPrefString("street", location.getStreet());
            //            Log.e("sdfafasdf", currentPosition.toString());
        }
    }

    @Override
    public void onBackPressed() {
        NormalUtil.showExitDialog(this,"确认退出吗？");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Toast.makeText(this, "main_start", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Toast.makeText(this, "main_resume", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "main_destory", Toast.LENGTH_SHORT).show();
        unregisterReceiver(broadcastReceiver);
        ActivityCollector.RemoveActivity(this);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            int i = SPUtils.getPrefInt(Constant.APPOINT_NUM, 2);
            Toast.makeText(BottomNavigationBarActivity.this, String.valueOf(intent.getExtras().getInt(Constant.APPOINT_NUM)), Toast.LENGTH_SHORT).show();
            if (i != 0) {
                textBadgeItem = new TextBadgeItem()
                        .setBorderWidth(4)
                        .setAnimationDuration(200)
                        .setHideOnSelect(true)
                        .setGravity(Gravity.RIGHT | Gravity.TOP) // 默认为右上角
                        .setText(String.valueOf(intent.getExtras().getInt(Constant.APPOINT_NUM)));
                bottomNavigationItem3.setBadgeItem(textBadgeItem);
                bottomNavigationView.initialise();
                AppointMent appointMent = LitePal.findFirst(AppointMent.class);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(appointMent.getHos_name()).append("\n").append(appointMent.getBig_depart_name()).append("-").append(appointMent.getDepart_name()).append("\n")
                        .append(appointMent.getAppoint_date());
                Toast.makeText(BottomNavigationBarActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                getNotificationManager().notify(1, getNotification("预约成功!",stringBuilder.toString(), -1));
            }
        }
    };

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, String contentInfo, int progress) {
        Notification notification;
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        Intent intent = new Intent(this, BottomNavigationBarActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(contentInfo);

//        builder.setDefaults(~0);
//        builder.setTicker("悬浮通知");
        builder.setPriority(Notification.PRIORITY_MAX);
        //        builder.setContent(remoteViews);//设置自定义布局
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentIntent(pendingIntent);
        builder.setFullScreenIntent(pendingIntent, true);
        notification = builder.build();
        return notification;
    }
}

