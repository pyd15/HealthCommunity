package com.example.health_community.adapter.searchMedicines;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.health_community.R;
import com.example.health_community.adapter.MyFlowAdapter;
import com.example.health_community.model.Medicine_Copy;
import com.example.health_community.util.UIUtils;
import com.github.hymanme.tagflowlayout.OnTagClickListener;
import com.github.hymanme.tagflowlayout.TagAdapter;
import com.github.hymanme.tagflowlayout.bean.TagBean;
import com.github.hymanme.tagflowlayout.tags.ColorfulTagView;
import com.github.hymanme.tagflowlayout.tags.DefaultTagView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dr.P on 2017/11/10.
 */
public class SearchMedicineInfoHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.butterfly_image)
    ImageView med_pre_img;

    @BindView(R.id.med_name)
    TextView med_name;

    @BindView(R.id.med_desc)
    TextView med_desc;

    @BindView(R.id.med_desc_tag)
    TextView med_desc_tag;

    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlow;

//    @BindView(R.id.tag_pre_flow_layout)
    com.github.hymanme.tagflowlayout.TagFlowLayout tagFlowLayout;

    List<String> list = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();


    public View butterflyView;
    Context context;

    public SearchMedicineInfoHolder(Context context, View itemView) {
        super(itemView);
        butterflyView = itemView;
        this.context = context;
        ButterKnife.bind(this, itemView);
//        initTagFlowLayout();
    }

    public void bind(Medicine_Copy medicine) throws IOException {
        med_name.setText(medicine.getMed_name());
        String[] pre_tag = medicine.getMed_pre_tag().split(",");
        String[] pre_tag_copy = medicine.getMed_pre_tag().split(",");
        for (int i = 0; i < pre_tag.length; i++) {
            switch (pre_tag[i]) {
                case "icon1":
                    pre_tag[i] = "OTC";
                    pre_tag_copy[i] = "ROTC";
                    colors.add(context.getResources().getColor(R.color.red_primary));
                    break;
                case "icon2":
                    pre_tag[i] = "OTC";
                    pre_tag_copy[i] = "GOTC";
                    colors.add(context.getResources().getColor(R.color.google_green));
                    break;
                case "icon3":
                    continue;
                case "icon4":
                    pre_tag[i] = "基";
                    pre_tag_copy[i] = "基";
                    colors.add(context.getResources().getColor(R.color.google_blue));
                    break;
                case "icon5":
                    pre_tag[i] = "保";
                    pre_tag_copy[i] = "保";
                    colors.add(context.getResources().getColor(R.color.google_green));
                    break;
                case "icon6":
                    pre_tag[i] = "ZB";
                    pre_tag_copy[i] = "ZB";
                    colors.add(context.getResources().getColor(R.color.brown_primary));
                    break;
                case "icon8":
                    pre_tag[i] = "销";
                    pre_tag_copy[i] = "销";
                    colors.add(context.getResources().getColor(R.color.grey_primary));
                    break;
                case "icon9":
                    pre_tag[i] = "外";
                    pre_tag_copy[i] = "外";
                    colors.add(context.getResources().getColor(R.color.app_red));
                    break;
                case "icon13":
                    pre_tag[i] = "GS1";
                    pre_tag_copy[i] = "GS1";
                    colors.add(context.getResources().getColor(R.color.blue_grey_primary));
                    break;
                //                default:
                //                    pre_tag[i] = "销";
                //                    colors.add(context.getResources().getColor(R.color.grey_primary));

            }
            //            Log.e("colors", colors.get(i) + "");
            list.add(pre_tag[i]);
        }
        //        colors.add(context.getResources().getColor(R.color.black));
        MyFlowAdapter adapter = new MyFlowAdapter(context, list, colors);
        mFlow.setAdapter(adapter);
        //点击时的回调方法
        mFlow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                switch (pre_tag_copy[position]) {
                    case "ROTC":
                        Toast.makeText(context, "甲类非处方药。不须医生处方就可以购买和出售，但必须在药店出售，并在药师指导下使用。", Toast.LENGTH_SHORT).show();
                        break;
                    case "GOTC":
                        Toast.makeText(context, "乙类非处方药。有着长期安全使用的记录，可以像普通商品一样在超市、杂货店直接出售。", Toast.LENGTH_SHORT).show();
                        break;
                    case "基":
                        Toast.makeText(context, "国家基本药物是指由政府制定的《国家基本药物目录》中的药品。国家基本药物的遴选原则为：临床必需、安全有效、价格合理、使用方便、中西药并重。", Toast.LENGTH_LONG).show();
                        break;
                    case "保":
                        Toast.makeText(context, "医保药品乙类", Toast.LENGTH_SHORT).show();
                        break;
                    case "ZB":
                        Toast.makeText(context, "中药保护品种二级", Toast.LENGTH_SHORT).show();
                        break;
                    case "外":
                        Toast.makeText(context, "该产品为外用，请勿内服！", Toast.LENGTH_SHORT).show();
                        break;
                    case "GS1":
                        Toast.makeText(context, "条码已在中国物品编码中心注册", Toast.LENGTH_SHORT).show();
                        break;
                    case "销":
                        Toast.makeText(context, "该产品批准文号已被注销", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "该产品批准文号已被注销", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        //点击时回调所选中的集合
        mFlow.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

            }
        });
        //预先设置选中
        //        adapter.setSelectedList(1, 3, 5, 7, 8, 9);
        //选中的下标集合
        //        Set<Integer> selectedList = mFlow.getSelectedList();
        list.clear();
        colors.clear();
        //        String[] images = imagePath.split(",");
        //        String[] img_urls = medicine.getMed_desc_img().split(",");
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.fail)
                .dontTransform();
        //            if (images.length > 1)
        //                            Glide.with(butterflyView.getApplication()).load(images[abc]).into(med_pre_img);
        Glide.with(butterflyView.getContext()).load(medicine.getMed_pre_img().trim()).apply(options).into(med_pre_img);
        //            else
        //                Glide.with(butterflyView.getContext()).load(butterflyView.getContext().getFilesDir().getAbsolutePath() + "/btf/zanwu.jpg").into(med_pre_img);
        if (medicine.getMed_desc().length() <=15) {
            med_desc.setText(medicine.getMed_desc());
        }
        else {
            med_desc.setText(medicine.getMed_desc().substring(0, 14)+"...");
        }
        if (medicine.getMed_desc_tag().length()>0) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(medicine.getMed_desc_tag());
            Log.e("desc-tag", m.replaceAll(""));
            p = Pattern.compile("\\t*|\r|\n");
            m = p.matcher(medicine.getMed_desc_tag());
            Log.e("desc-tag", m.replaceAll(""));
            med_desc_tag.setText(m.replaceAll(""));
        }else med_desc_tag.setVisibility(View.GONE);
    }

    //        //给adapter绑定数据
    private void initTagFlowLayout() {
        List<TagBean> tagBeans = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            tagBeans.add(new TagBean(i, "tags" + i));
        }
        TagAdapter<TagBean> tagAdapter = new TagAdapter<TagBean>() {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                //定制tag的样式，包括背景颜色，点击时背景颜色，背景形状等
                DefaultTagView textView = new ColorfulTagView(UIUtils.getContext());
                textView.setText(((TagBean) getItem(position)).getName());
                return textView;
            }
        };
        tagAdapter.addAllTags(tagBeans);
        tagFlowLayout.setTagAdapter(tagAdapter);
        tagFlowLayout.setTagListener(new OnTagClickListener() {
            @Override
            public void onClick(com.github.hymanme.tagflowlayout.TagFlowLayout parent, View view, int position) {
                Toast.makeText(UIUtils.getContext(), "click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(com.github.hymanme.tagflowlayout.TagFlowLayout parent, View view, int position) {
                Toast.makeText(UIUtils.getContext(), "long click==" + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }

        });
        tagFlowLayout.setTitle("");
        tagFlowLayout.setMaxVisibleHeight(30);
        tagFlowLayout.setTitleTextSize(0);
        tagFlowLayout.setHintTextSize(10);
    }
}
