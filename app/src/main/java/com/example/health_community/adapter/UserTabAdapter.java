package com.example.health_community.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.health_community.R;
import com.example.health_community.fragment.RecycleViewFragment;

/**
 * Created by kongqw on 2016/3/7.
 */
public class UserTabAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private final String[] titles;

    public UserTabAdapter(FragmentManager fm) {
        super(fm);
        this.titles = context.getResources().getStringArray(R.array.main_tab_type);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        RecycleViewFragment fragment = null;
        //        Fragment fragment=null;

        if (0 == position) {
            //            fragment = new RecycleViewFragment();
            fragment = new RecycleViewFragment();
            fragment.setCategory("文学");
            fragment.setContext(context);
        } else if (1 == position) {
            //            fragment = new CardsFragment();
            fragment = new RecycleViewFragment();
            fragment.setCategory("教育");
            fragment.setContext(context);
        } else if (2 == position) {
            //            fragment = new CardsFragment();
            fragment = new RecycleViewFragment();
            fragment.setCategory("艺术");
            fragment.setContext(context);
        } else if (3 == position) {
            //            fragment = new CardsFragment();
            fragment = new RecycleViewFragment();
            fragment.setCategory("政治");
            fragment.setContext(context);
        } else if (4 == position) {
            //            fragment = new CardsFragment();
            fragment = new RecycleViewFragment();
            fragment.setCategory("科学");
            fragment.setContext(context);
        } else if (5 == position) {
            //            fragment = new CardsFragment();
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
                switch (position) {
                    case 0:
                        return "文学";
                    case 1:
                        return "教育";
                    case 2:
                        return "艺术";
                    case 3:
                        return "政治";
                    case 4:
                        return "科学";
                    case 5:
                        return "军事";
                }
                return null;
    }
}