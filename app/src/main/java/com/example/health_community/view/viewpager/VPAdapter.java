package com.example.health_community.view.viewpager;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.health_community.Medicine;
import com.example.health_community.R;
import com.example.health_community.fragment.ImageFragment;
import com.example.health_community.util.Constant;

import java.util.ArrayList;

/**
 * Created by Dr.P on 2017/11/6.
 * VPAdapter类作为自定义的ViewPage的适配器，用于生成ViewPage要显示的图片列表
 */
public class VPAdapter extends PagerAdapter {
    private Context context;
    private Activity activity;
    private FragmentManager fm;
    private ArrayList<String> imgs;
    private Medicine medicine;
    private int img_position;

    private static final String DIALOG_IMAGE = "image";

    public VPAdapter(Context context, FragmentManager fm, ArrayList<String> imageList, Medicine medicine) {
        this.context = context;
        this.fm=fm;
        imgs = new ArrayList<>();
        this.medicine = medicine;
        for (int i = 0; i < imageList.size(); i++) {
            imgs.add(imageList.get(i));
        }
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.vf_vp_layout_item, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_item);
        RequestOptions options = new RequestOptions().fitCenter().
                placeholder(R.drawable.loading).error(R.drawable.fail_diy);
        //        iv.setImageResource(imgs.get(position % 3));
        //为选中的当前页面加载对应图片
        if (imgs.size() > 1) {
                            img_position=position;
//                            Glide.with(context).load(imgs.get(position % imgs.size())).into(iv);
            Glide.with(context).load(Constant._URL+imgs.get(position % imgs.size())).apply(options).into(iv);
        } else if (imgs.size() == 1) {
            Glide.with(context).load(Constant._URL+imgs.get(0)).apply(options).into(iv);
//            Glide.with(context).load(imgs.get(0)).apply(options).into(iv);
        } else {
            Glide.with(context).load(context.getFilesDir().getAbsolutePath()+"/btf/zanwu.jpg").into(iv);
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgs.size()>1)
                ImageFragment.newInstance(context,imgs.get((img_position-1) % (imgs.size())),imgs, medicine).show(fm,DIALOG_IMAGE);
                else
                    ImageFragment.newInstance(context,context.getFilesDir().getAbsolutePath()+"/btf/zanwu.jpg",imgs, medicine).show(fm,DIALOG_IMAGE);

            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
