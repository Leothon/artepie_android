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

    private int classoractivity;

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
