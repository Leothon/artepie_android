package com.leothon.cogito.GreenDao;



import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserEntity {

    @Id
    private String user_id;
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
    private String user_art_coin;
    @Generated(hash = 723318749)
    public UserEntity(String user_id, String user_name, String user_icon,
            String user_birth, int user_sex, String user_signal,
            String user_address, String user_password, String user_token,
            String user_status, String user_register_time, String user_register_ip,
            String user_lastlogin_time, String user_phone, String user_role,
            String user_balance, String user_art_coin) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_icon = user_icon;
        this.user_birth = user_birth;
        this.user_sex = user_sex;
        this.user_signal = user_signal;
        this.user_address = user_address;
        this.user_password = user_password;
        this.user_token = user_token;
        this.user_status = user_status;
        this.user_register_time = user_register_time;
        this.user_register_ip = user_register_ip;
        this.user_lastlogin_time = user_lastlogin_time;
        this.user_phone = user_phone;
        this.user_role = user_role;
        this.user_balance = user_balance;
        this.user_art_coin = user_art_coin;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }
    public String getUser_id() {
        return this.user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_name() {
        return this.user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_icon() {
        return this.user_icon;
    }
    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }
    public String getUser_birth() {
        return this.user_birth;
    }
    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }
    public int getUser_sex() {
        return this.user_sex;
    }
    public void setUser_sex(int user_sex) {
        this.user_sex = user_sex;
    }
    public String getUser_signal() {
        return this.user_signal;
    }
    public void setUser_signal(String user_signal) {
        this.user_signal = user_signal;
    }
    public String getUser_address() {
        return this.user_address;
    }
    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }
    public String getUser_password() {
        return this.user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public String getUser_token() {
        return this.user_token;
    }
    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
    public String getUser_status() {
        return this.user_status;
    }
    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }
    public String getUser_register_time() {
        return this.user_register_time;
    }
    public void setUser_register_time(String user_register_time) {
        this.user_register_time = user_register_time;
    }
    public String getUser_register_ip() {
        return this.user_register_ip;
    }
    public void setUser_register_ip(String user_register_ip) {
        this.user_register_ip = user_register_ip;
    }
    public String getUser_lastlogin_time() {
        return this.user_lastlogin_time;
    }
    public void setUser_lastlogin_time(String user_lastlogin_time) {
        this.user_lastlogin_time = user_lastlogin_time;
    }
    public String getUser_phone() {
        return this.user_phone;
    }
    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
    public String getUser_role() {
        return this.user_role;
    }
    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
    public String getUser_balance() {
        return this.user_balance;
    }
    public void setUser_balance(String user_balance) {
        this.user_balance = user_balance;
    }
    public String getUser_art_coin() {
        return this.user_art_coin;
    }
    public void setUser_art_coin(String user_art_coin) {
        this.user_art_coin = user_art_coin;
    }
    
}
