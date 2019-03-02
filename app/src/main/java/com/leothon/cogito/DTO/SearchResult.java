package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.User;

import java.util.ArrayList;

public class SearchResult {
    private ArrayList<SelectClass> selectClasses;
    private ArrayList<QAData> qaData;
    private ArrayList<User> users;
    private ArrayList<Article> articles;

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<QAData> getQaData() {
        return qaData;
    }

    public void setQaData(ArrayList<QAData> qaData) {
        this.qaData = qaData;
    }

    public ArrayList<SelectClass> getSelectClasses() {
        return selectClasses;
    }

    public void setSelectClasses(ArrayList<SelectClass> selectClasses) {
        this.selectClasses = selectClasses;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
