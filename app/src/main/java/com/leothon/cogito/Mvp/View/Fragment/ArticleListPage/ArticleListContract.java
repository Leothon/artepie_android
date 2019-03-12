package com.leothon.cogito.Mvp.View.Fragment.ArticleListPage;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.DTO.ArticleData;

import java.util.ArrayList;

public class ArticleListContract {
    public interface IArticleListModel{

        void getArticleData(String token,OnArticleListFinishedListener listener);
        void getMoreArticleData(String token,int currentPage,OnArticleListFinishedListener listener);

    }

    public interface IArticleListView{
        void loadArticlePageData(ArticleData articleData);
        void loadMoreArticlePageData(ArrayList<Article> articles);

        void showInfo(String msg);
    }

    public interface OnArticleListFinishedListener {

        void loadArticlePageData(ArticleData articleData);
        void loadMoreArticlePageData(ArrayList<Article> articles);
        void showInfo(String msg);

    }

    public interface IArticleListPresenter{
        void onDestroy();

        void loadArticleData(String token);
        void loadArticleData(String token,int currentPage);

    }
}
