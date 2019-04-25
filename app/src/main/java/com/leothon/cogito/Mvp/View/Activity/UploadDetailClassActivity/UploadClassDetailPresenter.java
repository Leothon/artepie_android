package com.leothon.cogito.Mvp.View.Activity.UploadDetailClassActivity;

import com.leothon.cogito.Bean.ClassDetailList;

import java.io.File;

public class UploadClassDetailPresenter implements UploadClassDetailContract.IUploadClassDetailPresenter,UploadClassDetailContract.OnUploadClassDetailFinishedListener {
    private UploadClassDetailContract.IUploadClassDetailModel iUploadClassDetailModel;
    private UploadClassDetailContract.IUploadClassDetailView iUploadClassDetailView;

    public UploadClassDetailPresenter(UploadClassDetailContract.IUploadClassDetailView iUploadClassDetailView){
        this.iUploadClassDetailModel = new UploadClassDetailModel();
        this.iUploadClassDetailView = iUploadClassDetailView;
    }

    @Override
    public void showProgress(long nowSize, long totalSize) {
        if (iUploadClassDetailView != null){
            iUploadClassDetailView.showProgress(nowSize,totalSize);
        }
    }

    @Override
    public void uploadVideoSuccess(String path) {
        if (iUploadClassDetailView != null){
            iUploadClassDetailView.uploadVideoSuccess(path);
        }
    }

    @Override
    public void sendClassDetailSuccess(String msg) {
        if (iUploadClassDetailView != null){
            iUploadClassDetailView.sendClassDetailSuccess(msg);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iUploadClassDetailView != null){
            iUploadClassDetailView.showInfo(msg);
        }
    }

    @Override
    public void uploadImgSuccesss(String path) {
        if (iUploadClassDetailView != null){
            iUploadClassDetailView.uploadImgSuccesss(path);
        }
    }

    @Override
    public void onDestroy() {
        iUploadClassDetailView = null;
        iUploadClassDetailModel = null;
    }

    @Override
    public void uploadVideo(String path) {
        iUploadClassDetailModel.uploadVideo(path,this);
    }

    @Override
    public void sendClassDetail(ClassDetailList classDetailList) {
        iUploadClassDetailModel.sendClassDetail(classDetailList,this);
    }

    @Override
    public void uploadImg(String name,byte[] img) {
        iUploadClassDetailModel.uploadImg(name, img,this);
    }
}
