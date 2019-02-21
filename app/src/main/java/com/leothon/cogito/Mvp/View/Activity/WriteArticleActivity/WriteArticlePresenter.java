package com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity;

public class WriteArticlePresenter implements WriteArticleContract.IWriteArticlePresenter,WriteArticleContract.OnWriteArticleFinishedListener {
    private WriteArticleContract.IWriteArticleView iWriteArticleView;
    private WriteArticleContract.IWriteArticleModel iWriteArticleModel;

    public WriteArticlePresenter(WriteArticleContract.IWriteArticleView iWriteArticleView){
        this.iWriteArticleView = iWriteArticleView;
        this.iWriteArticleModel = new WriteArticleModel();
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
    public void onDestroy() {
        iWriteArticleView = null;
        iWriteArticleModel = null;
    }

    @Override
    public void uploadSelectImg(String path) {
        iWriteArticleModel.uploadImg(path,this);
    }
}
