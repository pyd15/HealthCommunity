package com.example.health_community.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.health_community.Medicine;
import com.example.health_community.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dr.P on 2017/11/10.
 */
public class SearchButterflyInfoHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.butterfly_image)
    ImageView med_pre_img;

    @BindView(R.id.butterfly_name)
    TextView med_name;

    @BindView(R.id.butterfly_latinName)
    TextView med_desc;

    public View butterflyView;
    Context context;

    public SearchButterflyInfoHolder(Context context, View itemView) {
        super(itemView);
        butterflyView = itemView;
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public void bind(Medicine medicine) throws IOException {

        String imagePath = medicine.getMed_desc_img();
        med_name.setText(medicine.getMed_name());

        String[] images = imagePath.split(",");
        String[] img_urls = medicine.getMed_desc_img().split(",");
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.fail)
                .dontTransform();
//            if (images.length > 1)
//                            Glide.with(butterflyView.getApplication()).load(images[abc]).into(med_pre_img);
                Glide.with(butterflyView.getContext()).load(medicine.getMed_pre_img().trim()).apply(options).into(med_pre_img);
//            else
//                Glide.with(butterflyView.getContext()).load(butterflyView.getContext().getFilesDir().getAbsolutePath() + "/btf/zanwu.jpg").into(med_pre_img);
//        med_desc.setText(butterflyInfo.getLatinName());
    }
}
