package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.ArticleComment;
import com.leothon.cogito.Mvp.View.Fragment.ArticleListPage.ArticleListPresenter;

import java.util.ArrayList;

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
    public void addLikeSuccess(String msg) {
        if (iArticleView != null){
            iArticleView.addLikeSuccess(msg);
        }
    }

    @Override
    public void removeLikeSuccess(String msg) {
        if (iArticleView != null){
            iArticleView.removeLikeSuccess(msg);
        }
    }

    @Override
    public void sendSuccess(String msg) {
        if (iArticleView != null){
            iArticleView.sendSuccess(msg);
        }
    }

    @Override
    public void getCommentSuccess(ArrayList<ArticleComment> articleComments) {
        if (iArticleView != null){
            iArticleView.getCommentSuccess(articleComments);
        }
    }

    @Override
    public void getMoreCommentSuccess(ArrayList<ArticleComment> articleComments) {
        if (iArticleView != null){
            iArticleView.getMoreCommentSuccess(articleComments);
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

    @Override
    public void addLikeArticle(String token, String articleId) {
        iArticleModel.addLikeArticle(token,articleId,this);
    }

    @Override
    public void removeLikeArticle(String token, String articleId) {
        iArticleModel.removeLikeArticle(token,articleId,this);
    }

    @Override
    public void sendComment(String token, String articleId, String articleComment) {
        iArticleModel.sendComment(token,articleId,articleComment,this);
    }

    @Override
    public void getComment(String articleId) {
        iArticleModel.getComment(articleId,this);
    }

    @Override
    public void getCommentMore(String articleId, int currentPage) {
        iArticleModel.getCommentMore(articleId,currentPage,this);
    }

    @Override
    public void replyArticleComment(String commentId, String token, String reply) {
        iArticleModel.replyArticleComment(commentId,token,reply,this);
    }

    @Override
    public void deleteComment(String commentId, String token) {
        iArticleModel.deleteComment(commentId,token,this);
    }


}
