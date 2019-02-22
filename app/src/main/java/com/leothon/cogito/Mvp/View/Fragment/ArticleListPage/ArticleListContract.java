package com.leothon.cogito.Mvp.View.Fragment.ArticleListPage;

import com.leothon.cogito.DTO.ArticleData;

public class ArticleListContract {
    public interface IArticleListModel{
        //TODO 执行相关数据操作

        void getArticleData(String token,OnArticleListFinishedListener listener);

    }

    public interface IArticleListView{
        //TODO　执行前端操作
        void loadArticlePageData(ArticleData articleData);

        void showInfo(String msg);
    }

    public interface OnArticleListFinishedListener {

        void loadArticlePageData(ArticleData articleData);
        void showInfo(String msg);

    }

    public interface IArticleListPresenter{
        void onDestroy();

        void loadArticleData(String token);

    }
}
