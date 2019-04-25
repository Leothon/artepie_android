package com.leothon.cogito.Mvp.View.Activity.UploadDetailClassActivity;

import com.leothon.cogito.Bean.ClassDetailList;

import java.io.File;

public class UploadClassDetailContract {
    public interface IUploadClassDetailModel{

        void uploadVideo(String path,OnUploadClassDetailFinishedListener listener);
        void uploadImg(String name,byte[] img, OnUploadClassDetailFinishedListener listener);
        void sendClassDetail(ClassDetailList classDetailList,OnUploadClassDetailFinishedListener listener);
    }

    public interface IUploadClassDetailView{


        void showProgress(long nowSize,long totalSize);
        void uploadVideoSuccess(String path);
        void sendClassDetailSuccess(String msg);
        void uploadImgSuccesss(String path);
        void showInfo(String msg);
    }

    public interface OnUploadClassDetailFinishedListener {
        void showProgress(long nowSize,long totalSize);
        void uploadVideoSuccess(String path);
        void sendClassDetailSuccess(String msg);
        void showInfo(String msg);
        void uploadImgSuccesss(String path);
    }

    public interface IUploadClassDetailPresenter{
        void onDestroy();
        void uploadVideo(String path);
        void sendClassDetail(ClassDetailList classDetailList);
        void uploadImg(String name,byte[] img);

    }
}
