package com.leothon.cogito.Mvp.View.Activity.AskActivity;

import com.leothon.cogito.DTO.SendQAData;

public class AskActivityContract {

    public interface IAskActivityModel{

        void uploadFile(String path,OnAskActivityFinishedListener listener);

        void sendQaData(SendQAData sendQAData,OnAskActivityFinishedListener listener);
    }

    public interface IAskActivityView{


        void getUploadUrl(String url);

        void sendSuccess(String msg);

        void showInfo(String msg);

    }

    public interface OnAskActivityFinishedListener {

        void getUploadUrl(String url);

        void sendSuccess(String msg);
        void showInfo(String msg);

    }

    public interface IAskActivityPresenter{
        void onDestory();
        void uploadFile(String path);
        void sendData(SendQAData sendQAData);

    }
}
