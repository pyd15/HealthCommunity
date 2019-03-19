package com.example.health_community.activity;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.health_community.R;
import com.example.health_community.fragment.MedicineInfoFragment;
import com.example.health_community.model.Medicine_Copy;
import com.example.health_community.util.ActivityCollector;
import com.example.health_community.util.Constant;
import com.example.health_community.view.AppBarStateChangeListener;
import com.example.health_community.view.viewpager.GuardViewPager;
import com.example.health_community.view.viewpager.VPAdapter;

/**
 * Created by Dr.P on 2017/10/10.
 */

public class MedInfoActivity extends AppCompatActivity {

    ImageView butterflypicture;
    TextView nametext;
    TextView latinNametext;
    TextView typetext;
    TextView featuretext;
    ;
    TextView areatext;
    TextView protecttext;
    TextView raretext;
    TextView uniqueToChinatext;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private GuardViewPager vp;
    private VPAdapter vpAdapter;
    private LinearLayout ll;
    private String[] imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        ActivityCollector.AddActivity(this);
        Medicine_Copy medicine = (Medicine_Copy) getIntent().getSerializableExtra(Constant.MEDINCINE_INFO);
//        final List<Medicine> nameList = LitePal.where("med_name=? and med_company=?", medicine.getMed_name(), medicine.getMed_company()).find(Medicine.class);
        if (medicine.getMed_desc_img() != null) {
            imageList = medicine.getMed_desc_img().split(",");
        }else imageList=null;

        //        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info);
        //        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.image_collapsing_toolbar);
        ////        FloatingActionButton fab_fruit_content = (FloatingActionButton) findViewById(R.id.fab_fruit_content);
        //        setSupportActionBar(toolbar);
        //        ActionBar actionBar = getSupportActionBar();
        //        if (actionBar != null) {
        //            actionBar.setDisplayHomeAsUpEnabled(true);
        //        }
        //        AppBarLayout appBarLayout = findViewById(R.id.appBar);
        //        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
        //            @Override
        //            public void onStateChanged(AppBarLayout appBarLayout, State state) {
        //                Log.d("STATE", state.name());
        //                if (state == State.EXPANDED) {
        //                    collapsingToolbarLayout.setTitle("");//fruitName
        //                    //展开状态
        //
        //                } else if (state == State.COLLAPSED) {
        //                    collapsingToolbarLayout.setTitle(nameList.get(0).getMed_name());//fruitName
        //                    //折叠状态
        //
        //                } else {
        //                    //中间状态
        //                    collapsingToolbarLayout.setTitle("");
        //                }
        //            }
        //        });
        MedicineInfoFragment medicineInfoFragment = (MedicineInfoFragment) getSupportFragmentManager().findFragmentById(R.id.info_fragment);
        medicineInfoFragment.refresh(getApplicationContext(), medicine, imageList);
        //        vp = (GuardViewPager) findViewById(R.id.vp);
        //        vpAdapter = new VPAdapter(this, imageList, nameList.get(0));
        //        vp.setAdapter(vpAdapter);
        //        ll = (LinearLayout) findViewById(R.id.ll);
        //
        //        nametext = (TextView) findViewById(R.id.name);
        //        latinNametext = (TextView) findViewById(R.id.latinName);
        //        typetext = (TextView) findViewById(R.id.type);
        //        featuretext = (TextView) findViewById(R.id.feature);
        //        areatext = (TextView) findViewById(R.id.area);
        //        protecttext = (TextView) findViewById(R.id.protect);
        //        raretext = (TextView) findViewById(R.id.rare);
        //        uniqueToChinatext = (TextView) findViewById(R.id.uniqueToChina);
        //
        //        if (nameList.get(0).getName()!=null)
        //        nametext.setText("中文学名:" + nameList.get(0).getName());
        //        else nametext.setText("中文学名:暂无" );
        //        if (nameList.get(0).getLatinName() != null)
        //        latinNametext.setText("拉丁学名:" + nameList.get(0).getLatinName());
        //        else latinNametext.setText("拉丁学名:暂无");
        //        if(nameList.get(0).getType()!=null)
        //        typetext.setText("科属:" + nameList.get(0).getType());
        //        else typetext.setText("科属:暂无");
        //        if(nameList.get(0).getFeature()!=null)
        //        featuretext.setText("识别特征:\n" + nameList.get(0).getFeature());
        //        else featuretext.setText("识别特征:\n暂无");
        //        if (nameList.get(0).getArea()!=null)
        //        areatext.setText("地理分布:\n" + nameList.get(0).getArea());
        //        else areatext.setText("地理分布:\n暂无");
        //        if (nameList.get(0).getProtect() == 0) {
        //            protecttext.setText("保护级别:非保护品种");
        //        } else {
        //            protecttext.setText("保护级别:保护品种");
        //        }
        //        if (nameList.get(0).getRare() == 0) {
        //            raretext.setText("稀有级别:较常见");
        //        } else {
        //            raretext.setText("稀有级别:较稀有");
        //        }
        //        if (nameList.get(0).getUniqueToChina() == 0) {
        //            uniqueToChinatext.setText("中国特有:分布广泛");
        //        } else {
        //            uniqueToChinatext.setText("中国特有:分布不广泛");
        //        }

        //        fab_fruit_content.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Intent intent = new Intent(MedInfoActivity.this, MainActivity.class);
        //                startActivity(intent);
        //                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        //            }
        //        });

        //        if (imageList.size() > abc) {
        //            vp.setOnPageChangeListener(new ViewPagerIndicator(this, vp, ll, imageList.size()));
        //        } else {
        //            vp.toggleSlide(false);//若该类药品只有一张图片则不进行切换显示
        //        }
    }

    public void setToolbar(Toolbar toolbar, CollapsingToolbarLayout collapsingToolbarLayout, String title) {
        if (toolbar != null) {
            this.toolbar = toolbar;
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setTitle("");
            AppBarLayout appBarLayout = findViewById(R.id.appBar);
            appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                @Override
                public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                    Log.d("STATE", state.name());
                    //展开状态
                    if (state == State.EXPANDED) {
                        collapsingToolbarLayout.setTitle("");//fruitName
                        //                        toolbar.setBackgroundColor(Color.WHITE);
                        collapsingToolbarLayout.setBackgroundColor(Color.WHITE);
                        Drawable upArrow = ContextCompat.getDrawable(MedInfoActivity.this, R.drawable.abc_ic_ab_back_material);
                        upArrow.setColorFilter(getResources().getColor(R.color.gray_light), PorterDuff.Mode.SRC_ATOP);
                        getSupportActionBar().setHomeAsUpIndicator(upArrow);

                        //折叠状态
                    } else if (state == State.COLLAPSED) {
                        collapsingToolbarLayout.setTitle(title);//fruitName
                        Drawable upArrow = ContextCompat.getDrawable(MedInfoActivity.this, R.drawable.abc_ic_ab_back_material);
                        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                        getSupportActionBar().setHomeAsUpIndicator(upArrow);

                        //中间状态
                    } else {
                        collapsingToolbarLayout.setTitle("");
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.RemoveActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
