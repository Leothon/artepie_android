package com.leothon.cogito.Mvp.View.Activity.VSureActivity;

import com.leothon.cogito.Bean.AuthInfo;
import com.leothon.cogito.Bean.User;

import java.io.File;
import java.util.ArrayList;

public class VSureContract {
    public interface IVSureModel{


        void sendAuthInfo(String token,String img,String content,OnVSureFinishedListener listener);
        void getAuthInfo(String token,OnVSureFinishedListener listener);
        void uploadAuthImg(String path, OnVSureFinishedListener listener);

        void getUserInfo(String token,OnVSureFinishedListener listener);
    }

    public interface IVSureView{

        void sendSuccess(String msg);
        void uploadImgSuccess(String msg);

        void getUserInfoSuccess(User user);
        void getInfoSuccess(ArrayList<AuthInfo> authInfos);
        void showInfo(String msg);
    }

    public interface OnVSureFinishedListener {

        void sendSuccess(String msg);
        void uploadImgSuccess(String msg);
        void getUserInfoSuccess(User user);
        void getInfoSuccess(ArrayList<AuthInfo> authInfos);
        void showInfo(String msg);

    }

    public interface IVSurePresenter{
        void onDestroy();
        void sendAuthInfo(String token,String img,String content);
        void getAuthInfo(String token);
        void uploadAuthImg(String path);

        void getUserInfo(String token);
    }
}
