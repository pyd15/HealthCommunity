package com.example.health_community.model;

import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/19
 * desc:
 */

public class Hospital {
    private String hos_id;
    private String hos_name;
    private String hos_phone;
    private String hos_address;
    private String hos_area;
    private String hos_city;
    private String hos_province;
    private List<BigDepartment> bigDepartments;

    public Hospital(String hos_id, String hos_name) {
        this.hos_id=hos_id;
        this.hos_name = hos_name;
    }

    public Hospital(String hos_id, String hos_name,String hos_phone,String hos_address,
                    String hos_area,String hos_city,String hos_province) {
        this.hos_id=hos_id;
        this.hos_name = hos_name;
        this.hos_phone = hos_phone;
        this.hos_address = hos_address;
        this.hos_area = hos_area;
        this.hos_city = hos_city;
        this.hos_province = hos_province;
    }

    public String getHos_id() {
        return hos_id;
    }

    public void setHos_id(String hos_id) {
        this.hos_id = hos_id;
    }

    public String getHos_name() {
        return hos_name;
    }

    public void setHos_name(String hos_name) {
        this.hos_name = hos_name;
    }

    public String getHos_phone() {
        return hos_phone;
    }

    public void setHos_phone(String hos_phone) {
        this.hos_phone = hos_phone;
    }

    public String getHos_address() {
        return hos_address;
    }

    public void setHos_address(String hos_address) {
        this.hos_address = hos_address;
    }

    public String getHos_area() {
        return hos_area;
    }

    public void setHos_area(String hos_area) {
        this.hos_area = hos_area;
    }

    public String getHos_city() {
        return hos_city;
    }

    public void setHos_city(String hos_city) {
        this.hos_city = hos_city;
    }

    public String getHos_province() {
        return hos_province;
    }

    public void setHos_province(String hos_province) {
        this.hos_province = hos_province;
    }

    public List<BigDepartment> getBigDepartments() {
        return bigDepartments;
    }

    public void setBigDepartments(List<BigDepartment> bigDepartments) {
        this.bigDepartments = bigDepartments;
    }
}

