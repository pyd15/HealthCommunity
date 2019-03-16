package com.example.health_community.activity;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.health_community.News;
import com.example.health_community.R;
import com.example.health_community.snackbar.SnackBarUtil;

public class WebNewsActivity extends AppCompatActivity {
    //    @BindView(R.id.toolbar_web_view)
    Toolbar toolbar;
    //    @BindView(R.id.web_view)
    WebView webView;
    //    @BindView(R.id.progressBar_web_view)
    ProgressBar progressBar;

    FloatingActionButton floatingActionButton;
    News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        //        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar_web_view);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progressBar_web_view);
        floatingActionButton = findViewById(R.id.fab_web);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        // Let the logic of click navigation arrow the same as back key , or has not the animation
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
                //添加新按钮
                SnackBarUtil.SnackbarAddView(snackbar, R.layout.snackbarbtn, 1);
                //设置新添加的按钮监听器
                SnackBarUtil.SetAction(snackbar, R.id.cancel_btn, "取消收藏", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                //设置snackbar自带的按钮监听器
                snackbar.setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(WebNewsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        news.saveOrUpdate();
                        floatingActionButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                    }

                });
                snackbar.show();
            }
        });
        news = (News) getIntent().getSerializableExtra("id");
        //        List<News> news= LitePal.where("news_id=?",String.valueOf(id)).find(News.class);
        //        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress != 100) {
                    progressBar.setProgress(newProgress);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //                toolbar.setSubtitle("健康新知");
                toolbar.setTitle("健康新知");
            }
        });
        webView.loadData(news.getNews_contents(), "text/html; charset=UTF-8", null);
        //        webView.setWebChromeClient(new WebChromeClient());
        //        webView.loadUrl("http://m.39.net/news/a_4640424.html");
        //        Toast.makeText(this, news.getNews_contents(), Toast.LENGTH_LONG).show();
        //        webView.loadData(news.get(0).getNews_contents(), "text/html; charset=UTF-8",null);
        //        webView.loadData(news.get(0).getNews_contents(), "text/html; charset=UTF-8",null);
        //        webView.loadUrl("file:///android_asset/test.html");

    }
    /*
        webView = (WebView) findViewById(R.id.bbs_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        special_content = special_content.replaceAll("&", "");
        special_content = special_content.replaceAll(""", "\"");
        special_content = special_content.replaceAll("<", "<");
        special_content = special_content.replaceAll(">", ">");
        special_content = special_content.replaceAll("\\n", "<br>");//换行
        special_content = special_content.replaceAll("<img", "<img width=\"100%\"");//图片不超出屏幕
        webView.loadDataWithBaseURL(null, special_content, "text/html", "utf-8", null);
         */
}
