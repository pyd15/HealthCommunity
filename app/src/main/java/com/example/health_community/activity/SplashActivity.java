package com.example.health_community.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.example.health_community.R;
import com.example.health_community.util.Constant;
import com.example.health_community.util.HttpAction;

import org.json.JSONException;

import me.wangyuwei.particleview.ParticleView;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/9/8
 * Description:
 */

public class SplashActivity extends AppCompatActivity {
    private ParticleView pv_logo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        try {
            HttpAction.parseJSONWithJSONObject(this, Constant.GET_NEWS_URL+"39_ys");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pv_logo = findViewById(R.id.pv_logo);
        pv_logo.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                Intent intent = new Intent(SplashActivity.this, BottomNavigationBarActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        });
        pv_logo.startAnim();
    }
}
