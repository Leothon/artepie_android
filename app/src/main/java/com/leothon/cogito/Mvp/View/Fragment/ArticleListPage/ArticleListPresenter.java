package com.leothon.cogito.Mvp.View.Fragment.ArticleListPage;

import com.leothon.cogito.DTO.ArticleData;

public class ArticleListPresenter implements ArticleListContract.IArticleListPresenter,ArticleListContract.OnArticleListFinishedListener {

    private ArticleListContract.IArticleListModel iArticleListModel;
    private ArticleListContract.IArticleListView iArticleListView;

    public ArticleListPresenter(ArticleListContract.IArticleListView iArticleListView){
        this.iArticleListView = iArticleListView;
        this.iArticleListModel = new ArticleListModel();
    }
    @Override
    public void loadArticlePageData(ArticleData articleData) {
        if (iArticleListView != null){
            iArticleListView.loadArticlePageData(articleData);
        }
    }

    @Override
    public void showInfo(String msg) {

        if (iArticleListView != null){
            iArticleListView.showInfo(msg);
        }

    }

    @Override
    public void onDestroy() {
        iArticleListView = null;
        iArticleListModel = null;
    }

    @Override
    public void loadArticleData(String token) {
        iArticleListModel.getArticleData(token,this);
    }
}
