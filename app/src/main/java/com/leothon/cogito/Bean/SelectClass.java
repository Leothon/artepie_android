package com.leothon.cogito.Bean;

import java.util.ArrayList;
/**
 * created by leothon on 2018.8.7
 */
public class SelectClass  {

    private String selectbackimg;
    private String selectlisttitle;
    private String selectstucount;
    private String selecttime;
    private String selectauthor;
    private boolean isfav;

    private ArrayList<VideoClass> videoClasses;

    public ArrayList<VideoClass> getVideoClasses() {
        return videoClasses;
    }

    public void setVideoClasses(ArrayList<VideoClass> videoClasses) {
        this.videoClasses = videoClasses;
    }

    public String getSelectauthor() {
        return selectauthor;
    }

    public void setSelectauthor(String selectauthor) {
        this.selectauthor = selectauthor;
    }

    public String getSelectbackimg() {
        return selectbackimg;
    }

    public void setSelectbackimg(String selectbackimg) {
        this.selectbackimg = selectbackimg;
    }

    public String getSelectlisttitle() {
        return selectlisttitle;
    }

    public void setSelectlisttitle(String selectlisttitle) {
        this.selectlisttitle = selectlisttitle;
    }

    public String getSelectstucount() {
        return selectstucount;
    }

    public void setSelectstucount(String selectstucount) {
        this.selectstucount = selectstucount;
    }

    public String getSelecttime() {
        return selecttime;
    }

    public void setSelecttime(String selecttime) {
        this.selecttime = selecttime;
    }

    public Boolean getIsfav(){
        return isfav;
    }
    public void setIsfav(boolean isfav) {
        this.isfav = isfav;
    }


}

