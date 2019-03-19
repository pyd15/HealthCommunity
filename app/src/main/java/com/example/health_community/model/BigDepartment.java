package com.example.health_community.model;

import java.util.List;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/19
 * desc:
 */

public class BigDepartment {
    private String big_depart_id;
    private String big_depart_name;
    private String hos_id;
    private String hos_name;
    private List<Department> departments;

    public BigDepartment() {
    }

    public BigDepartment(String big_depart_id, String big_depart_name, String hos_id,String hos_name) {
        this.big_depart_id = big_depart_id;
        this.big_depart_name = big_depart_name;
        this.hos_id = hos_id;
        this.hos_name = hos_name;
    }

    public String getBig_depart_id() {
        return big_depart_id;
    }

    public void setBig_depart_id(String big_depart_id) {
        this.big_depart_id = big_depart_id;
    }

    public String getBig_depart_name() {
        return big_depart_name;
    }

    public void setBig_depart_name(String big_depart_name) {
        this.big_depart_name = big_depart_name;
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return hos_id + "-" + hos_name + "-" + big_depart_name + "-" + big_depart_id;
    }
}
