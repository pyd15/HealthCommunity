package com.example.health_community.view.viewpager;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.health_community.model.Medicine;
import com.example.health_community.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Created by Dr.P on 2018/3/18.
 * runas /user:Dr.P "cmd /k"
 */

public class DFAdapter extends PagerAdapter {

    private Context context;
    private Activity activity;
    private Dialog dialog;
    private FragmentManager fm;
    private String[] imgs;
    private Medicine medicine;
    private int img_position;

    public DFAdapter(Context context, Activity activity, Dialog dialog, FragmentManager fm, String[] imageList) {
        this.context = context;
        this.activity=activity;
        this.dialog=dialog;
        this.fm = fm;
        imgs = new String[imageList.length];
//        this.medicine = med;
        for (int i = 0; i < imageList.length; i++) {
            imgs[i]=imageList[i];
        }
    }

    @Override
    public int getCount() {
//        return Integer.MAX_VALUE;
        return imgs!=null?imgs.length:0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(final ViewGroup container, int position) {
//        final View view = LayoutInflater.from(context).inflate(R.layout.df_vp_layout_item, null);
        PhotoView iv = new PhotoView(context);
        Log.e("position", position + "");
        RequestOptions options = new RequestOptions().fitCenter().
                placeholder(R.drawable.loading).error(R.drawable.fail_diy);
        if (imgs.length > 1) {
            img_position = position;
            Glide.with(context).load(imgs[position%imgs.length]).apply(options).into(iv);
//            Glide.with(context).load(imgs.get(position % imgs.size())).apply(options).into(iv);
        } else if (imgs.length == 1) {
            Glide.with(context).load(imgs[0]).apply(options).into(iv);
//            Glide.with(context).load(imgs.get(0)).apply(options).into(iv);
        } else {
            Glide.with(context).load(context.getFilesDir().getAbsolutePath() + "/files/zanwu.jpg").into(iv);
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
