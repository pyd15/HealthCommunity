package com.example.health_community.model;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/19
 * desc:
 */

public class AppointMent extends LitePalSupport implements Serializable {
    private int appoint_id;
//    private BigInteger appoint_id;
    private String user_account;
    private String hos_name;
    private String big_depart_name;
    private String depart_name;
    private String appoint_date;

    public AppointMent() {}

    public AppointMent(int appoint_id, String user_account, String hos_name, String big_depart_name,
//public AppointMent(BigInteger appoint_id, String user_account, String hos_name, String big_depart_name,
                       String depart_name, String appoint_date) {
        this.appoint_id = appoint_id;
        this.user_account=user_account;
        this.hos_name = hos_name;
        this.big_depart_name = big_depart_name;
        this.depart_name = depart_name;
        this.appoint_date = appoint_date;
    }

//    public BigInteger getAppoint_id() {
//        return appoint_id;
//    }

//    public void setAppoint_id(BigInteger appoint_id) {
//        this.appoint_id = appoint_id;
//    }

        public int getAppoint_id() {
        return appoint_id;
    }

    public void setAppoint_id(int appoint_id) {
        this.appoint_id = appoint_id;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getHos_name() {
        return hos_name;
    }

    public void setHos_name(String hos_name) {
        this.hos_name = hos_name;
    }

    public String getBig_depart_name() {
        return big_depart_name;
    }

    public void setBig_depart_name(String big_depart_name) {
        this.big_depart_name = big_depart_name;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public String getAppoint_date() {
        return appoint_date;
    }

    public void setAppoint_date(String appoint_date) {
        this.appoint_date = appoint_date;
    }

    @Override
    public String toString() {
        return appoint_id+"-"+user_account + "-" + hos_name + "-" + big_depart_name + "-" + depart_name + "-" + appoint_date;
    }
}
