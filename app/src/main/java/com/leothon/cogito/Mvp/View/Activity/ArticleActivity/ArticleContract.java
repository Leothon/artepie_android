package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import com.leothon.cogito.Bean.Article;

public class ArticleContract {

    public interface IArticleModel{

        void getArticleInfo(String articleId,String token,OnArticleFinishedListener listener);
        void deleteArticle(String token,String articleId,OnArticleFinishedListener listener);
    }

    public interface IArticleView{



        void loadArticleData(Article article);
        void showInfo(String msg);
        void deleteSuccess(String msg);
    }

    public interface OnArticleFinishedListener {

        void loadArticleData(Article article);
        void showInfo(String msg);
        void deleteSuccess(String msg);

    }

    public interface IArticlePresenter{
        void loadArticle(String articleId,String token);
        void onDestroy();
        void deleteArticle(String token,String articleId);

    }
}
