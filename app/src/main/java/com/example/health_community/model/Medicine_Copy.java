package com.example.health_community.model;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.io.Serializable;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/17
 * desc:
 */
public class Medicine_Copy extends BaseIndexPinyinBean implements Serializable {

    private int med_id;
    private String med_name;
    private String med_en_name;
    private String med_desc;
    private String med_pre_img;
    private String med_desc_img;
    private String med_company;
    private String med_type;
    private String med_rates;
    private String med_pre_tag;//列表标签
    private String med_desc_tag;//详情标签
    private String med_related_tips;//相关标签
    private String med_component;//成分
    private String med_useage;//用法
    private String med_avoid;//禁忌
    private String med_attention;//注意事项
    private String med_adverse;//不良反应
    private String med_purpose;//功能主治
    private String med_href;
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的

    public int getMed_id() {
        return med_id;
    }

    public void setMed_id(int med_id) {
        this.med_id = med_id;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getMed_en_name() {
        return med_en_name;
    }

    public void setMed_en_name(String med_en_name) {
        this.med_en_name = med_en_name;
    }

    public String getMed_desc() {
        return med_desc;
    }

    public void setMed_desc(String med_desc) {
        this.med_desc = med_desc;
    }

    public String getMed_pre_img() {
        return med_pre_img;
    }

    public void setMed_pre_img(String med_pre_img) {
        this.med_pre_img = med_pre_img;
    }

    public String getMed_desc_img() {
        return med_desc_img;
    }

    public void setMed_desc_img(String med_desc_img) {
        this.med_desc_img = med_desc_img;
    }

    public String getMed_company() {
        return med_company;
    }

    public void setMed_company(String med_company) {
        this.med_company = med_company;
    }

    public String getMed_type() {
        return med_type;
    }

    public void setMed_type(String med_type) {
        this.med_type = med_type;
    }

    public String getMed_rates() {
        return med_rates;
    }

    public void setMed_rates(String med_rates) {
        this.med_rates = med_rates;
    }

    public String getMed_pre_tag() {
        return med_pre_tag;
    }

    public void setMed_pre_tag(String med_pre_tag) {
        this.med_pre_tag = med_pre_tag;
    }

    public String getMed_desc_tag() {
        return med_desc_tag;
    }

    public void setMed_desc_tag(String med_desc_tag) {
        this.med_desc_tag = med_desc_tag;
    }

    public String getMed_related_tips() {
        return med_related_tips;
    }

    public void setMed_related_tips(String med_related_tips) {
        this.med_related_tips = med_related_tips;
    }

    public String getMed_component() {
        return med_component;
    }

    public void setMed_component(String med_component) {
        this.med_component = med_component;
    }

    public String getMed_useage() {
        return med_useage;
    }

    public void setMed_useage(String med_useage) {
        this.med_useage = med_useage;
    }

    public String getMed_avoid() {
        return med_avoid;
    }

    public void setMed_avoid(String med_avoid) {
        this.med_avoid = med_avoid;
    }

    public String getMed_attention() {
        return med_attention;
    }

    public void setMed_attention(String med_attention) {
        this.med_attention = med_attention;
    }

    public String getMed_adverse() {
        return med_adverse;
    }

    public void setMed_adverse(String med_adverse) {
        this.med_adverse = med_adverse;
    }

    public String getMed_purpose() {
        return med_purpose;
    }

    public void setMed_purpose(String med_purpose) {
        this.med_purpose = med_purpose;
    }

    public String getMed_href() {
        return med_href;
    }

    public void setMed_href(String med_href) {
        this.med_href = med_href;
    }

    @Override
    public String getTarget() {
        return null;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }

    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }
}
