package com.leothon.cogito.Bean;

/**
 * created by leothon on 2018.8.5
 * 登录用户名和密码
 */
public class User {

    private int user_id;
    private String user_name;
    private String user_icon;
    private String user_birth;
    private int user_sex;
    private String user_signal;
    private String user_address;
    private String user_password;
    private String user_token;
    private String user_status;
    private String user_register_time;
    private String user_register_ip;
    private String user_lastlogin_time;
    private String user_phone;
    private String user_role;
    private String user_balance;
    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }

    public int getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_signal() {
        return user_signal;
    }

    public void setUser_signal(String user_signal) {
        this.user_signal = user_signal;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_register_time() {
        return user_register_time;
    }

    public void setUser_register_time(String user_register_time) {
        this.user_register_time = user_register_time;
    }

    public String getUser_register_ip() {
        return user_register_ip;
    }

    public void setUser_register_ip(String user_register_ip) {
        this.user_register_ip = user_register_ip;
    }

    public String getUser_lastlogin_time() {
        return user_lastlogin_time;
    }

    public void setUser_lastlogin_time(String user_lastlogin_time) {
        this.user_lastlogin_time = user_lastlogin_time;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(String user_balance) {
        this.user_balance = user_balance;
    }

    @Override
    public String toString() {
        return "用户信息"
                + "{ 用户ID：" + user_id
                + "用户姓名" + user_name
                + "用户头像地址" + user_icon
                + "用户生日" + user_birth
                + "用户性别" + user_sex
                + "用户签名" + user_signal
                + "用户地址" + user_address
                + "用户密码（密文）" + user_password
                + "用户token" + user_token
                + "用户状态" + user_status
                + "用户注册时间" + user_register_time
                + "用户注册IP" + user_register_ip
                + "用户上次登录时间" + user_lastlogin_time
                + "用户手机号码" + user_phone
                + "用户角色" + user_role
                + "用户余额" + user_balance
                + "}";
    }
}
