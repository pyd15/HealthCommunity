package com.example.health_community.model;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/19
 * desc:
 */

public class AppointMent {

    private String user_id;
    private String hospital_id;
    private String hospital_department;
    private String appoint_date;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getHospital_department() {
        return hospital_department;
    }

    public void setHospital_department(String hospital_department) {
        this.hospital_department = hospital_department;
    }

    public String getAppoint_date() {
        return appoint_date;
    }

    public void setAppoint_date(String appoint_date) {
        this.appoint_date = appoint_date;
    }
}
