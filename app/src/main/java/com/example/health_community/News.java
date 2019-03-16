package com.example.health_community;


import com.google.gson.annotations.SerializedName;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/02/18
 * desc:新闻资讯实体类
 */

public class News extends LitePalSupport implements Serializable {
    @SerializedName("news_id")
    private int news_id;
    @Column(unique = true)
    @SerializedName("news_title")
    private String news_title;
    @SerializedName("news_desc")
    private String news_desc;
    @SerializedName("news_contents")
    private String news_contents;
    @SerializedName("news_href")
    private String news_href;
    @SerializedName("news_image")
    private String news_image;
    @SerializedName("news_type")
    private String news_type;
    @SerializedName("news_date")
    private String news_date;

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_desc() {
        return news_desc;
    }

    public void setNews_desc(String news_desc) {
        this.news_desc = news_desc;
    }

    public String getNews_href() {
        return news_href;
    }

    public void setNews_href(String news_href) {
        this.news_href = news_href;
    }

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public String getNews_contents() {
        return news_contents;
    }

    public void setNews_contents(String news_contents) {
        this.news_contents = news_contents;
    }

    public String getNews_type() {
        return news_type;
    }

    public void setNews_type(String news_type) {
        this.news_type = news_type;
    }

    public String getNews_date() {
        return news_date;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }

    //    public String getNews_date() {
    //        return news_date;
    //    }
    //
    //    public void setNews_date(String news_date) {
    //        this.news_date = news_date;
    //    }
}
