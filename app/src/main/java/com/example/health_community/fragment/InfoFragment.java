package com.example.health_community.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.health_community.R;
import com.example.health_community.activity.BottomNavigationBarActivity;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/08
 * desc:
 */
public class InfoFragment extends Fragment {

    //    @BindView(R.id.main_toolbar)
    //    android.support.v7.widget.Toolbar mToolbar;
    //    @BindView(R.id.search_view_main)
    //    MaterialSearchView searchView;


    private View view;
    //    @BindView(R.id.view_pager_main)
    private ViewPager mVpContent;
    BottomNavigationBarActivity bottomNavigationActivity;

    //    private List<Book> allBooks;
    //    List<Book> filteredModelList;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_frag, null);
        //        TextView textView = new TextView(getActivity());
        //        textView.setGravity(Gravity.CENTER);
        //        textView.setText(R.string.hello_blank_fragment);
        //        return textView;
        //        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        //        allBooks = LitePal.findAll(Book.class);
        initView();
        return view;
    }

    public void initView() {
        //        initSearchView();
        mVpContent =  view.findViewById(R.id.view_pager_info_frag);
        //    @BindView(R.id.tab_layout_main)
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_info_frag);
        MainTabAdapter adapter = new MainTabAdapter(bottomNavigationActivity.getSupportFragmentManager());
        //bottomNavigationActivity.getSupportFragmentManager()
        adapter.setContext(getActivity());
        mVpContent.setAdapter(adapter);
        mVpContent.setOffscreenPageLimit(8);
        tabLayout.setupWithViewPager(mVpContent);
        //设置tab指示器下划线的颜色
        //        tabLayout.setSelectedTabIndicatorColor(getContext().getResources().getColor(R.color.white));
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //                ((NestedScrollView) view.findViewById(R.id.nestedScrollView)).scrollTo(0, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setSmoothScrollingEnabled(true);
    }

    class MainTabAdapter extends FragmentStatePagerAdapter {

        private Context context;
        private final String[] titles;

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
            this.titles = getResources().getStringArray(R.array.info_tab_type);
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            RecycleViewFragment fragment = null;
            if (0 == position) {
                fragment = new RecycleViewFragment();
                fragment.setCategory("文学");
                fragment.setContext(context);
            } else if (1 == position) {
                fragment = new RecycleViewFragment();
                fragment.setCategory("教育");
                fragment.setContext(context);
            } else if (2 == position) {
                fragment = new RecycleViewFragment();
                fragment.setCategory("艺术");
                fragment.setContext(context);
            } else if (3 == position) {
                fragment = new RecycleViewFragment();
                fragment.setCategory("政治");
                fragment.setContext(context);
            } else if (4 == position) {
                fragment = new RecycleViewFragment();
                fragment.setCategory("科学");
                fragment.setContext(context);
            } else if (5 == position) {
                fragment = new RecycleViewFragment();
                fragment.setCategory("军事");
                fragment.setContext(context);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    //    @Override
    //    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    //        //fragment中调用此方法会只显示fragment自己的toolbar菜单栏
    //        Log.e("UserBlank", "onCreateOptionsMenu()");
    //        menu.clear();
    //        super.onCreateOptionsMenu(menu, inflater);
    //        inflater.inflate(R.menu.toolbar, menu);
    //        MenuItem item = menu.findItem(R.id.action_search_main_tool_bar);
    //        searchView.setMenuItem(item);
    //        //        inflater.inflate(R.menu.toolbar, menu);
    //    }

    //    /**
    //     * 未解之谜？？fragment中的onOptionsItemSelected无效
    //     * @param item
    //     * @return
    //     */
    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        switch (item.getItemId()) {
    //            case R.id.settings:
    //                Intent intent = new Intent(Intent.ACTION_SEND);
    //                intent.setType("image/*");
    //                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
    //                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
    //                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //                startActivity(Intent.createChooser(intent, bottomNavigationActivity.getTitle()));
    //                return true;
    //            default:
    //                break;
    //        }
    //        return true;
    //    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof BottomNavigationBarActivity) {
            bottomNavigationActivity = (BottomNavigationBarActivity) activity;
        }
    }

}

