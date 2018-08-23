package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.10
 */
public class Ask extends BaseResponse {

    private String usericonurl;
    private String username;
    private String userdes;
    private String content;
    private String commentcount;
    private String likecount;

    private String coverurl;
    private String videourl;


    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getUsericonurl() {
        return usericonurl;
    }

    public void setUsericonurl(String usericonurl) {
        this.usericonurl = usericonurl;
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

    public String getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(String commentcount) {
        this.commentcount = commentcount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getLikecount() {
        return likecount;
    }

    public void setLikecount(String likecount) {
        this.likecount = likecount;
    }

}
