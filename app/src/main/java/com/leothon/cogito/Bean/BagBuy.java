package com.leothon.cogito.Bean;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.8
 *
 */
public class BagBuy {
    private String imgurl;
    private String title;
    private String description;
    private SelectClass selectclass;
    private String author;
    private String classCount;

    private String time;

    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private int classoractivity;

    public String getClassCount() {
        return classCount;
    }

    public void setClassCount(String classCount) {
        this.classCount = classCount;
    }

    public int getClassoractivity() {
        return classoractivity;
    }

    public void setClassoractivity(int classoractivity) {
        this.classoractivity = classoractivity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SelectClass getSelectclass() {
        return selectclass;
    }

    public void setSelectclass(SelectClass selectclass) {
        this.selectclass = selectclass;
    }
}
