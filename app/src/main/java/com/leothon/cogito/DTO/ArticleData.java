package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.Banner;

import java.util.ArrayList;

public class ArticleData {

    private ArrayList<Banner> banners;
    private ArrayList<Article> articles;

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
