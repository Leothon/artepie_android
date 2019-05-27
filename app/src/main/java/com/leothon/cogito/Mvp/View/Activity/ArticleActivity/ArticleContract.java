package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.ArticleComment;

import java.util.ArrayList;

public class ArticleContract {

    public interface IArticleModel{

        void getArticleInfo(String articleId,String token,OnArticleFinishedListener listener);
        void deleteArticle(String token,String articleId,OnArticleFinishedListener listener);

        void addLikeArticle(String token,String articleId,OnArticleFinishedListener listener);
        void removeLikeArticle(String token,String articleId,OnArticleFinishedListener listener);

        void sendComment(String token,String articleId,String articleComment,OnArticleFinishedListener listener);
        void getComment(String articleId,OnArticleFinishedListener listener);
        void getCommentMore(String articleId,int currentPage,OnArticleFinishedListener listener);
    }

    public interface IArticleView{



        void loadArticleData(Article article);
        void showInfo(String msg);
        void deleteSuccess(String msg);

        void addLikeSuccess(String msg);
        void removeLikeSuccess(String msg);
        void sendSuccess(String msg);
        void getCommentSuccess(ArrayList<ArticleComment> articleComments);
        void getMoreCommentSuccess(ArrayList<ArticleComment> articleComments);
    }

    public interface OnArticleFinishedListener {

        void loadArticleData(Article article);
        void showInfo(String msg);
        void deleteSuccess(String msg);

        void addLikeSuccess(String msg);
        void removeLikeSuccess(String msg);
        void sendSuccess(String msg);
        void getCommentSuccess(ArrayList<ArticleComment> articleComments);
        void getMoreCommentSuccess(ArrayList<ArticleComment> articleComments);
    }

    public interface IArticlePresenter{
        void loadArticle(String articleId,String token);
        void onDestroy();
        void deleteArticle(String token,String articleId);


        void addLikeArticle(String token,String articleId);
        void removeLikeArticle(String token,String articleId);
        void sendComment(String token,String articleId,String articleComment);
        void getComment(String articleId);
        void getCommentMore(String articleId,int currentPage);
    }
}
