package com.leothon.cogito.Mvp.View.Activity.AskActivity;

import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.SendQAData;
import com.leothon.cogito.Mvp.View.Fragment.AskPage.AskFragmentContract;

import java.io.File;

public class AskActivityContract {

    public interface IAskActivityModel{

        void uploadFile(String path,OnAskActivityFinishedListener listener);

        void sendQaData(SendQAData sendQAData,OnAskActivityFinishedListener listener);

        void getReInfo(String qaId, String token, OnAskActivityFinishedListener listener);

        void reContent(String token,String content,String qaId,OnAskActivityFinishedListener listener);

        void uploadVideoImg(File file,OnAskActivityFinishedListener listener);
    }

    public interface IAskActivityView{


        void getUploadUrl(String url);

        void sendSuccess(String msg);

        void showInfo(String msg);

        void getReInfo(QAData qaData);

        void reSuccess(String msg);


        void getImgUrl(String url);
    }

    public interface OnAskActivityFinishedListener {

        void getUploadUrl(String url);

        void sendSuccess(String msg);
        void showInfo(String msg);
        void getReInfo(QAData qaData);
        void reSuccess(String msg);
        void getImgUrl(String url);

    }

    public interface IAskActivityPresenter{
        void onDestroy();
        void uploadFile(String path);
        void sendData(SendQAData sendQAData);
        void getReInfo(String qaId, String token);
        void reContent(String token,String content,String qaId);
        void uploadVideoImg(File file);
    }
}
