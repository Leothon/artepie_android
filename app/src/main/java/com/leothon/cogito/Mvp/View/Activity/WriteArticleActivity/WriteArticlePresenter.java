package com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity;

import com.leothon.cogito.Bean.Article;

import java.io.File;

public class WriteArticlePresenter implements WriteArticleContract.IWriteArticlePresenter,WriteArticleContract.OnWriteArticleFinishedListener {
    private WriteArticleContract.IWriteArticleView iWriteArticleView;
    private WriteArticleContract.IWriteArticleModel iWriteArticleModel;

    public WriteArticlePresenter(WriteArticleContract.IWriteArticleView iWriteArticleView){
        this.iWriteArticleView = iWriteArticleView;
        this.iWriteArticleModel = new WriteArticleModel();
    }

    @Override
    public void isUploadSuccess(String info) {
        if (iWriteArticleView != null){
            iWriteArticleView.isUploadSuccess(info);
        }
    }

    @Override
    public void getUploadImgUrl(String url) {
        if (iWriteArticleView != null){
            iWriteArticleView.getUploadImgUrl(url);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iWriteArticleView != null){
            iWriteArticleView.showInfo(msg);
        }
    }

    @Override
    public void uploadArticleInfo(Article article) {
        iWriteArticleModel.uploadArticle(article,this);
    }

    @Override
    public void onDestroy() {
        iWriteArticleView = null;
        iWriteArticleModel = null;
    }

    @Override
    public void uploadSelectImg(File file) {
        iWriteArticleModel.uploadImg(file,this);
    }
}
