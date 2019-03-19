package com.example.health_community.model;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/19
 * desc:
 */

public class Department {
    private String depart_name;
    private String big_depart_id;
    private String hos_name;

    public Department() {
    }

    public Department(String depart_name, String big_depart_id,String hos_name) {
        this.depart_name = depart_name;
        this.big_depart_id = big_depart_id;
        this.hos_name = hos_name;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public String getBig_depart_id() {
        return big_depart_id;
    }

    public void setBig_depart_id(String big_depart_id) {
        this.big_depart_id = big_depart_id;
    }

    public String getHos_name() {
        return hos_name;
    }

    public void setHos_name(String hos_name) {
        this.hos_name = hos_name;
    }

    @Override
    public String toString() {
        return hos_name + "-" + big_depart_id + "-" + depart_name + "-";
    }
}
