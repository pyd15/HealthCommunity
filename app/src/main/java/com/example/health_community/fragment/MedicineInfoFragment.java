package com.example.health_community.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.health_community.R;
import com.example.health_community.activity.MedInfoActivity;
import com.example.health_community.activity.SearchMedicineActivity;
import com.example.health_community.model.Medicine_Copy;
import com.example.health_community.util.Constant;
import com.example.health_community.util.UIUtils;
import com.example.health_community.view.viewpager.GuardViewPager;
import com.example.health_community.view.viewpager.VPAdapter;
import com.example.health_community.view.viewpager.ViewPagerIndicator;
import com.github.hymanme.tagflowlayout.OnTagClickListener;
import com.github.hymanme.tagflowlayout.TagAdapter;
import com.github.hymanme.tagflowlayout.TagFlowLayout;
import com.github.hymanme.tagflowlayout.bean.TagBean;
import com.github.hymanme.tagflowlayout.tags.ColorfulTagView;
import com.github.hymanme.tagflowlayout.tags.DefaultTagView;
import com.hymane.expandtextview.ExpandTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedicineInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] rates,related_tips;
    private StringBuilder med_info = new StringBuilder();
    private View view;
    private Context context;

    //    FloatingActionButton fab_med_content;

    TextView med_name, med_company, med_en_name, featuretext, areatext,
            protecttext, raretext, uniqueToChinatext;
    ExpandTextView med_brief;
    AppCompatRatingBar ratingBar_effect;
    AppCompatRatingBar ratingBar_side_effect;
    AppCompatRatingBar ratingBar_price;
    TagFlowLayout tagFlowLayout;
    private GuardViewPager vp;
    private VPAdapter vpAdapter;
    private LinearLayout ll;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Medicine_Copy medicine;
    private FloatingActionButton fab_med_content;
    private FragmentManager fm;

    public MedicineInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter abc.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicineInfoFragment newInstance(String param1, String param2) {
        MedicineInfoFragment fragment = new MedicineInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.med_info_fragment, container, false);
        medicine = (Medicine_Copy) getActivity().getIntent().getSerializableExtra(Constant.MEDINCINE_INFO);
        //        Log.e("name", medicine.getMed_name() + "-" + medicine.getMed_desc_img() + "-"
        //                + medicine.getMed_attention() + "-" + medicine.getMed_component() + "-" + medicine.getMed_company());
        return view;
    }

    public void refresh(final Context context, Medicine_Copy medicine, String[] imageList) {
        this.context = context;
        initView(context, imageList);
        med_name.setText(medicine.getMed_name());
        if (medicine.getMed_en_name() == null||medicine.getMed_en_name().equals("")) {
            med_en_name.setText("英文名称：暂无");
        }else med_en_name.setText(clearText(medicine.getMed_en_name()));
        med_company.setText(medicine.getMed_company());
        //        med_en_name = view.findViewById(R.id.type);
        //        featuretext = view.findViewById(R.id.feature);
        //        areatext = view.findViewById(R.id.area);
        //        protecttext = view.findViewById(R.id.protect);
        //        raretext = view.findViewById(R.id.rare);
        //        uniqueToChinatext = view.findViewById(R.id.uniqueToChina);
        rates = medicine.getMed_rates().split(",");
        if (rates.length > 0) {
            ratingBar_effect.setRating(Float.valueOf(rates[0].substring(rates[0].length()-1,rates[0].length())));
            ratingBar_side_effect.setRating(Float.valueOf(rates[1].substring(rates[1].length()-1,rates[1].length())));
            ratingBar_price.setRating(Float.valueOf(rates[2].substring(rates[2].length()-1,rates[2].length())));
        }
//        clearText(medicine);
        med_info.append("【成份】" + "\n");
        if (medicine.getMed_component().equals("") || medicine.getMed_component() == null) {
            med_info.append("尚不明确" + "\n\n");
        } else
            med_info.append(medicine.getMed_component() + "\n\n");
        med_info.append("【功能主治】" + "\n");
        if (medicine.getMed_purpose().equals("") || medicine.getMed_purpose() == null) {
            med_info.append("尚不明确" + "\n\n");
        } else
            med_info.append(medicine.getMed_purpose() + "\n\n");
        med_info.append("【用法用量】" + "\n");
        if (medicine.getMed_useage().equals("") || medicine.getMed_useage() == null) {
            med_info.append("尚不明确" + "\n\n");
        } else
            med_info.append(medicine.getMed_useage() + "\n\n");
        med_info.append("【不良反应】" + "\n");
        if (medicine.getMed_adverse().equals("") || medicine.getMed_adverse() == null) {
            med_info.append("尚不明确" + "\n\n");
        } else
            med_info.append(medicine.getMed_adverse() + "\n\n");
        med_info.append("【禁忌】" + "\n");
        if (medicine.getMed_avoid().equals("") || medicine.getMed_avoid() == null) {
            med_info.append("尚不明确" + "\n\n");
        } else
            med_info.append(medicine.getMed_avoid() + "\n\n");
        med_info.append("【注意事项】" + "\n");
        if (medicine.getMed_attention().equals("") || medicine.getMed_attention() == null) {
            med_info.append("尚不明确" + "\n\n");
//            String[] strings=medicine.getMed_attention().split(" ");
//            for (String s:strings) {
//                med_info.append(s + "\n");
//            }
//            med_info.append("\n");
        } else
            med_info.append(medicine.getMed_attention() + "\n\n");
        med_brief.setContent(med_info.toString());


        if (imageList.length - 1 > 1) {
            vp.setOnPageChangeListener(new ViewPagerIndicator(context, vp, ll, imageList.length - 1));
        } else {
            vp.toggleSlide(false);//若该类药品只有一张图片则不进行切换显示
        }
    }

    private String clearText(String s) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        return m.replaceAll("");
    }

    private void initView(Context context, String[] imageList) {
        toolbar = view.findViewById(R.id.toolbar_info);
        collapsingToolbarLayout = view.findViewById(R.id.image_collapsing_toolbar);
        //        FloatingActionButton fab_med_content = (FloatingActionButton) findViewById(R.id.fab_med_content);
        vp = view.findViewById(R.id.vp);
        fm = getActivity().getSupportFragmentManager();
        vpAdapter = new VPAdapter(context, fm, imageList,toolbar);
        vp.setAdapter(vpAdapter);
        ll = view.findViewById(R.id.ll);
        //        fab_med_content = (FloatingActionButton) view.findViewById(R.id.fab_med_content);
        ratingBar_effect = view.findViewById(R.id.ratingBar_effect);
        ratingBar_side_effect = view.findViewById(R.id.ratingBar_side_effect);
        ratingBar_price = view.findViewById(R.id.ratingBar_price);
        med_company = view.findViewById(R.id.med_company);
        med_name = view.findViewById(R.id.med_name);
        med_en_name = view.findViewById(R.id.med_en_name);
        med_brief = view.findViewById(R.id.med_brief);
        tagFlowLayout = view.findViewById(R.id.tag_med_info_flow_layout);
        related_tips = medicine.getMed_related_tips().split(" ");
        if (related_tips.length>1) {
        initTagFlowLayout();
        }else tagFlowLayout.setVisibility(View.GONE);
    }

    private void initTagFlowLayout() {
            List<TagBean> tagBeans = null;
            if (related_tips.length > 0) {
                tagBeans = new ArrayList<>();
                for (int i = 0; i < related_tips.length; i++) {
                    tagBeans.add(new TagBean(i, related_tips[i]));
                }

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
                    Toast.makeText(UIUtils.getContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLongClick(com.github.hymanme.tagflowlayout.TagFlowLayout parent, View view, int position) {
                    Toast.makeText(UIUtils.getContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                }

            });
            tagFlowLayout.setTitle("");
            tagFlowLayout.setMaxVisibleHeight(30);
            tagFlowLayout.setTitleTextSize(0);
            tagFlowLayout.setHintTextSize(10);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SearchMedicineActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MedInfoActivity) getActivity()).setToolbar(toolbar, collapsingToolbarLayout, medicine.getMed_name());
        //        mToolbar.inflateMenu(R.menu.toolbar);
        //        searchView.setMenuItem(mToolbar.getMenu().findItem(R.id.action_search_main_tool_bar));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //                finish();
                //                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
