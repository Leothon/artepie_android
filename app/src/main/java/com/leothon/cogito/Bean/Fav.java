package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;
/**
 * created by leothon on 2018.8.11
 */
public class Fav extends BaseResponse{

    private String favurl;
    private String title;
    private String description;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFavurl() {
        return favurl;
    }

    public void setFavurl(String favurl) {
        this.favurl = favurl;
    }
}
