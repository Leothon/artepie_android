package com.leothon.cogito.Mvp.View.Activity.UploadActivity;

import com.leothon.cogito.DTO.QAData;

import java.util.ArrayList;

public class UploadContract {

    public interface IUploadModel{
        //TODO 执行相关数据操作

        void getAskData(String token,OnUploadFinishedListener listener);
        void getAskMoreData(int currentPage,String token,OnUploadFinishedListener listener);

        void addLike(String token,String qaId,OnUploadFinishedListener listener);
        void removeLike(String token,String qaId,OnUploadFinishedListener listener);
    }

    public interface IUploadView{
        //TODO　执行前端操作

        void loadAskData(ArrayList<QAData> qaData);
        void loadAskMoreData(ArrayList<QAData> qaData);
        void showInfo(String msg);
    }

    public interface OnUploadFinishedListener {
        void loadAskData(ArrayList<QAData> qaData);
        void loadAskMoreData(ArrayList<QAData> qaData);
        void showInfo(String msg);

    }

    public interface IUploadPresenter{
        void onDestroy();
        void getAskData(String token);
        void getAskMoreData(int currentPage,String token);

        void addLiked(String token,String qaId);
        void removeLiked(String token,String qaId);
    }
}
