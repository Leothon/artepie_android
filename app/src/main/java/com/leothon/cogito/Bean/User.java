package com.leothon.cogito.Bean;

/**
 * created by leothon on 2018.8.5
 * 登录用户名和密码
 */
public class User {

    private String u_id;
    private String u_phone;
    private String u_password;
    private String u_name;
    private String u_idCard;//身份证
    private String u_addTime;
    private String u_lastLoginTime;
    private String u_headerImage;
    private String u_nlineTime;//累计登录时间
    private String token;
    private String verify_code;

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_password() {
        return u_password;
    }

    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_idCard() {
        return u_idCard;
    }

    public void setU_idCard(String u_idCard) {
        this.u_idCard = u_idCard;
    }

    public String getU_addTime() {
        return u_addTime;
    }

    public void setU_addTime(String u_addTime) {
        this.u_addTime = u_addTime;
    }

    public String getU_lastLoginTime() {
        return u_lastLoginTime;
    }

    public void setU_lastLoginTime(String u_lastLoginTime) {
        this.u_lastLoginTime = u_lastLoginTime;
    }

    public String getU_headerImage() {
        return u_headerImage;
    }

    public void setU_headerImage(String u_headerImage) {
        this.u_headerImage = u_headerImage;
    }

    public String getU_nlineTime() {
        return u_nlineTime;
    }

    public void setU_nlineTime(String u_nlineTime) {
        this.u_nlineTime = u_nlineTime;
    }


}
