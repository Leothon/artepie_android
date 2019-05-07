package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.Banner;

import java.util.ArrayList;

public class ArticleData {

    private ArrayList<Banner> banners;
    private ArrayList<Article> articles;

    private String user_icon;




    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public ArrayList<Banner> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<Banner> banners) {
        this.banners = banners;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

}
