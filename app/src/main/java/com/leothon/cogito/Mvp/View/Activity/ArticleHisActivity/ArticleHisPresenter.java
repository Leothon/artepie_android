package com.leothon.cogito.Mvp.View.Activity.ArticleHisActivity;

import com.leothon.cogito.Bean.Article;

import java.util.ArrayList;

public class ArticleHisPresenter implements ArticleHisContract.OnArticleHisFinishedListener,ArticleHisContract.IArticleHisPresenter {

    private ArticleHisContract.IArticleHisModel iArticleHisModel;
    private ArticleHisContract.IArticleHisView iArticleHisView;

    public ArticleHisPresenter(ArticleHisContract.IArticleHisView iArticleHisView){
        this.iArticleHisView = iArticleHisView;
        this.iArticleHisModel = new ArticleHisModel();
    }
    @Override
    public void loadArticleHisData(ArrayList<Article> articles) {
        if (iArticleHisView != null){
            iArticleHisView.loadArticleHisData(articles);
        }
    }

    @Override
    public void loadArticleHisMoreData(ArrayList<Article> articles) {
        if (iArticleHisView != null){
            iArticleHisView.loadArticleHisMoreData(articles);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iArticleHisView != null){
            iArticleHisView.showInfo(msg);
        }
    }

    @Override
    public void onDestroy() {
        iArticleHisView = null;
        iArticleHisModel = null;
    }

    @Override
    public void getArticleHisData(String userId) {
        iArticleHisModel.getArticleHisData(userId,this);
    }

    @Override
    public void getArticleHisMoreData(int currentPage, String userId) {
        iArticleHisModel.getArticleHisMoreData(currentPage,userId,this);
    }
}
