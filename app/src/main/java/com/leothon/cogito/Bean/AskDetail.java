package com.leothon.cogito.Bean;

import java.util.ArrayList;

/**
 * created by leothon on 8/15/2018.
 */
public class AskDetail  {
    private String usericon;
    private String username;
    private String userdes;
    private String content;
    private String imgurl;
    private ArrayList<UserComment> userComments;

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserdes() {
        return userdes;
    }

    public void setUserdes(String userdes) {
        this.userdes = userdes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public ArrayList<UserComment> getUserComments() {
        return userComments;
    }

    public void setUserComments(ArrayList<UserComment> userComments) {
        this.userComments = userComments;
    }
}

