package com.example.health_community.model;

/**
 * author:PYD
 * email:pyd2657@qq.com
 * time:2019/03/18
 * desc:
 */
public class User {
    private String user_id;
    private String user_name;
    private String user_account;
    private String user_pass;
    private String user_register_date;

    public User() {}

    public User(String user_id, String user_name, String user_account, String user_pass, String user_register_date) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_account = user_account;
        this.user_pass = user_pass;
        this.user_register_date = user_register_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String  getUser_register_date() {
        return user_register_date;
    }

    public void setUser_register_date(String  user_register_date) {
        this.user_register_date = user_register_date;
    }

}
