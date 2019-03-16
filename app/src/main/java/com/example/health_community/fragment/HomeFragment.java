package com.example.health_community.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.health_community.R;
import com.example.health_community.activity.BottomNavigationBarActivity;
import com.example.health_community.activity.PoiSearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    //    @BindView(R.id.main_toolbar)
    //    android.support.v7.widget.Toolbar mToolbar;
    //    @BindView(R.id.search_view_main)
    //    MaterialSearchView searchView;
    @BindView(R.id.btn_medicines)
    Button btn_medicines;
    @BindView(R.id.health_rate)
    Button btn_health_rate;
    @BindView(R.id.health_profile)
    Button btn_health_profile;
    @BindView(R.id.planned_immunize)
    Button btn_planned_immunize;
    @BindView(R.id.btn_hospital_map)
    Button btn_hospital_map;
    

    private View view;
    //    @BindView(R.id.view_pager_main)
    private ViewPager mVpContent;
    //    @BindView(R.id.tab_layout_main)
    private TabLayout tabLayout;
    BottomNavigationBarActivity bottomNavigationActivity;

    //    private List<Book> allBooks;
    //    List<Book> filteredModelList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_frag, null);
        //        TextView textView = new TextView(getActivity());
        //        textView.setGravity(Gravity.CENTER);
        //        textView.setText(R.string.hello_blank_fragment);
        //        return textView;
        ButterKnife.bind(this, view);
        btn_medicines.setOnClickListener(this);
        btn_hospital_map.setOnClickListener(this);
        setHasOptionsMenu(true);
        //        allBooks = LitePal.findAll(Book.class);
        initView();
        return view;
    }

    public void initView() {
        //        initSearchView();
        //        MainTabAdapter adapter = new MainTabAdapter(bottomNavigationActivity.getSupportFragmentManager());
        //        mVpContent =  view.findViewById(R.id.view_pager_info);
        //        tabLayout =  view.findViewById(R.id.tab_layout_info);
        //        adapter.setContext(getActivity());
        //        mVpContent.setAdapter(adapter);
        //        mVpContent.setOffscreenPageLimit(8);
        //        tabLayout.setupWithViewPager(mVpContent);
        //        //设置tab指示器下划线的颜色
        ////        tabLayout.setSelectedTabIndicatorColor(getContext().getResources().getColor(R.color.white));
        //        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        //            @Override
        //            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //
        //            }
        //
        //            @Override
        //            public void onPageSelected(int position) {
        //                //                ((NestedScrollView) view.findViewById(R.id.nestedScrollView)).scrollTo(0, 0);
        //            }
        //
        //            @Override
        //            public void onPageScrollStateChanged(int state) {
        //
        //            }
        //        });
        //        tabLayout.setSmoothScrollingEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_medicines:
                break;
            case R.id.btn_hospital_map:
                Intent intent = new Intent(getActivity(), PoiSearchActivity.class);
                startActivity(intent);
        }
    }

    class MainTabAdapter extends FragmentStatePagerAdapter {

        private Context context;
        private final String[] titles;

        public MainTabAdapter(FragmentManager fm) {
            super(fm);
            this.titles = getResources().getStringArray(R.array.main_tab_type);
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
    //
    //    @Override
    //    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    //        super.onActivityCreated(savedInstanceState);
    //        ((MainActivity) getActivity()).setToolbar(mToolbar);
    //        mToolbar.inflateMenu(R.menu.toolbar);
    //        searchView.setMenuItem(mToolbar.getMenu().findItem(R.id.action_search_main_tool_bar));
    //    }
    //
    //    /**
    //     * init SearchView
    //     */
    //    private void initSearchView() {
    //        //        searchView = (MaterialSearchView) findViewById(R.id.search_view_main);
    //        searchView.setHint(getResources().getString(R.string.search_hint));
    //        searchView.setVoiceSearch(false);
    //        searchView.setCursorDrawable(R.drawable.custom_cursor);
    //        searchView.setEllipsize(true);
    //        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
    //            @Override
    //            public boolean onQueryTextSubmit(String query) {
    //                filteredModelList = filter(allBooks, query);
    //                Intent intent = new Intent(getActivity(), RecyclerViewActivity.class);
    //                intent.putExtra(Constant.SEARCH_LIST, (Serializable)filteredModelList);
    //                startActivity(intent);
    //                return true;
    //            }
    //
    //            @Override
    //            public boolean onQueryTextChange(String newText) {
    //
    //                //                filteredModelList = filter(allBooks, newText);
    //                //                adapter.setFilter(filteredModelList);
    //                //                adapter.animateTo(filteredModelList);
    //                //                recyclerView.scrollToPosition(0);
    //                return true;
    //            }
    //        });
    //
    //        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
    //            @Override
    //            public void onSearchViewShown() {
    //            }
    //
    //            @Override
    //            public void onSearchViewClosed() {
    //                //                adapter.setFilter(books);
    //            }
    //        });
    //    }


    //    /**
    //     * 筛选逻辑
    //     *
    //     * @param books
    //     * @param query
    //     * @return
    //     */
    //    private List<Book> filter(List<Book> books, String query) {
    //
    //        final List<Book> filteredModelList = new ArrayList<>();
    //        for (Book book : books) {
    //            final String bName = book.getB_Name();
    //            final String author = book.getAuthor();
    //            final String publishment=book.getPublishment();
    //            if (bName.contains(query) || author.contains(query)||publishment.toLowerCase().contains(query)) {
    //                filteredModelList.add(book);
    //            }
    //        }
    //        //        filteredModelList.add(new Book(11111));
    //        return filteredModelList;
    //    }
    //
    //    /**
    //     * 筛选传递
    //     *
    //     * @param requestCode
    //     * @param resultCode
    //     * @param data
    //     */
    //    @Override
    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
    //            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
    //            if (matches != null && matches.size() > 0) {
    //                String searchWrd = matches.get(0);
    //                if (!TextUtils.isEmpty(searchWrd)) {
    //                    Log.e("query_onActvitiyResult", searchWrd);
    //                    searchView.setQuery(searchWrd, false);
    //                    Intent intent = new Intent(getActivity(), RecyclerViewActivity.class);
    //                    intent.putExtra(Constant.SEARCH_LIST, (Serializable)filteredModelList);
    //                    startActivity(intent);
    //                    //setQuery->onSubmitQuery()->onQueryTextSubmit()
    //                    //onTextChanged()->onQueryTextChange()
    //                }
    //            }
    //            return;
    //        }
    //        super.onActivityResult(requestCode, resultCode, data);
    //    }

}
