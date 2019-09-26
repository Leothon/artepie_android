package com.leothon.cogito.Bean;

import java.io.Serializable;

public class ClassDetailList implements Serializable {

    private String classd_id;
    private String classd_title;
    private String class_classd_id;
    private String classd_des;
    private String classd_duration;
    private String classd_like;
    private String classd_view;
    private String classd_video;
    private String classd_create_time;
    private boolean liked;
    private int classdStatus;

    public int getClassdStatus() {
        return classdStatus;
    }

    public void setClassdStatus(int classdStatus) {
        this.classdStatus = classdStatus;
    }
    private String classd_video_cover;

    public String getClassd_video_cover() {
        return classd_video_cover;
    }

    public void setClassd_video_cover(String classd_video_cover) {
        this.classd_video_cover = classd_video_cover;
    }


    public boolean isLiked() {
        return liked;
    }

    public String getClass_classd_id() {
        return class_classd_id;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getClassd_create_time() {
        return classd_create_time;
    }

    public void setClass_classd_id(String class_classd_id) {
        this.class_classd_id = class_classd_id;
    }

    public String getClassd_des() {
        return classd_des;
    }

    public String getClassd_duration() {
        return classd_duration;
    }

    public String getClassd_id() {
        return classd_id;
    }

    public String getClassd_like() {
        return classd_like;
    }

    public String getClassd_title() {
        return classd_title;
    }

    public String getClassd_video() {
        return classd_video;
    }

    public String getClassd_view() {
        return classd_view;
    }

    public void setClassd_create_time(String classd_create_time) {
        this.classd_create_time = classd_create_time;
    }

    public void setClassd_des(String classd_des) {
        this.classd_des = classd_des;
    }

    public void setClassd_duration(String classd_duration) {
        this.classd_duration = classd_duration;
    }

    public void setClassd_id(String classd_id) {
        this.classd_id = classd_id;
    }

    public void setClassd_title(String classd_title) {
        this.classd_title = classd_title;
    }

    public void setClassd_like(String classd_like) {
        this.classd_like = classd_like;
    }

    public void setClassd_video(String classd_video) {
        this.classd_video = classd_video;
    }

    public void setClassd_view(String classd_view) {
        this.classd_view = classd_view;
    }
}
