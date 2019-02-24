package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Mvp.View.Fragment.ArticleListPage.ArticleListPresenter;

public class ArticlePresenter implements ArticleContract.IArticlePresenter,ArticleContract.OnArticleFinishedListener {

    private ArticleContract.IArticleModel iArticleModel;
    private ArticleContract.IArticleView iArticleView;

    public ArticlePresenter(ArticleContract.IArticleView iArticleView){
        this.iArticleView = iArticleView;
        this.iArticleModel = new ArticleModel();
    }
    @Override
    public void loadArticleData(Article article) {
        if (iArticleView != null){
            iArticleView.loadArticleData(article);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iArticleView != null){
            iArticleView.showInfo(msg);
        }
    }

    @Override
    public void deleteSuccess(String msg) {
        if (iArticleView != null){
            iArticleView.deleteSuccess(msg);
        }
    }

    @Override
    public void loadArticle(String articleId,String token) {
        iArticleModel.getArticleInfo(articleId,token,this);
    }

    @Override
    public void onDestroy() {
        iArticleModel = null;
        iArticleView = null;
    }

    @Override
    public void deleteArticle(String token, String articleId) {
        iArticleModel.deleteArticle(token,articleId,this);
    }
}
