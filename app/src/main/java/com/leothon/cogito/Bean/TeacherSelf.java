package com.leothon.cogito.Bean;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.4
 * 教师主页数据类
 */
public class TeacherSelf {

    private String teaiconurl;
    private String teaname;
    private String teadescription;
    private ArrayList<ClassItem> classItems;
    private int teaicon;

    public int getTeaicon() {
        return teaicon;
    }

    public void setTeaicon(int teaicon) {
        this.teaicon = teaicon;
    }

    public ArrayList<ClassItem> getClassItems() {
        return classItems;
    }

    public void setClassItems(ArrayList<ClassItem> classItems) {
        this.classItems = classItems;
    }

    public String getTeadescription() {
        return teadescription;
    }

    public void setTeadescription(String teadescription) {
        this.teadescription = teadescription;
    }

    public String getTeaiconurl() {
        return teaiconurl;
    }

    public void setTeaiconurl(String teaiconurl) {
        this.teaiconurl = teaiconurl;
    }

    public String getTeaname() {
        return teaname;
    }

    public void setTeaname(String teaname) {
        this.teaname = teaname;
    }
}
