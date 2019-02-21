package com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity;

import com.leothon.cogito.DTO.VideoDetail;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerContract;

public class WriteArticleContract {
    public interface IWriteArticleModel{

        void uploadImg(String path,OnWriteArticleFinishedListener listener);
    }

    public interface IWriteArticleView{



        void getUploadImgUrl(String url);
        void showInfo(String msg);

    }

    public interface OnWriteArticleFinishedListener {

        void getUploadImgUrl(String url);
        void showInfo(String msg);

    }

    public interface IWriteArticlePresenter{

        void onDestroy();
        void uploadSelectImg(String path);

    }
}
