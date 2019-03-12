package com.leothon.cogito.Bean;

import java.io.Serializable;

public class WeChatUserInfo implements Serializable {

    private String openid;
    private String nickname;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;

    private String unionid;

    private String errcode;
    private String errmsg;

    private String accesstoken;


    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void setProvince(String province) {
        this.province = province;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public int getSex() {
        return sex;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public String getNickname() {
        return nickname;
    }



    public String getProvince() {
        return province;
    }

    public String getUnionid() {
        return unionid;
    }

}
