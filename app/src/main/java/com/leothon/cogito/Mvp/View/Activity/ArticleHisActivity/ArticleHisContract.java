package com.leothon.cogito.Mvp.View.Activity.ArticleHisActivity;

import com.leothon.cogito.Bean.Article;

import java.util.ArrayList;

public class ArticleHisContract {
    public interface IArticleHisModel{

        void getArticleHisData(String userId,OnArticleHisFinishedListener listener);
        void getArticleHisMoreData(int currentPage,String userId,OnArticleHisFinishedListener listener);
    }

    public interface IArticleHisView{

        void loadArticleHisData(ArrayList<Article> articles);
        void loadArticleHisMoreData(ArrayList<Article> articles);
        void showInfo(String msg);
    }

    public interface OnArticleHisFinishedListener {
        void loadArticleHisData(ArrayList<Article> articles);
        void loadArticleHisMoreData(ArrayList<Article> articles);
        void showInfo(String msg);

    }

    public interface IArticleHisPresenter{
        void onDestroy();
        void getArticleHisData(String userId);
        void getArticleHisMoreData(int currentPage,String userId);

    }
}
