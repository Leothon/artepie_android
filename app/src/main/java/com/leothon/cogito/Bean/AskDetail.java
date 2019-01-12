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
    private String coverurl;
    private String videourl;
    private String audiourl;
    private String like;
    private String comment;
    private ArrayList<Comment> userComments;


    public String getAudiourl() {
        return audiourl;
    }

    public void setAudiourl(String audiourl) {
        this.audiourl = audiourl;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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

    public ArrayList<Comment> getUserComments() {
        return userComments;
    }

    public void setUserComments(ArrayList<Comment> userComments) {
        this.userComments = userComments;
    }
}

