package com.leothon.cogito.Mvp.View.Activity.CustomActivity;

public class CustomContract {

    public interface ICustomModel{
        void uploadInfo(String token,String info,OnCustomFinishedListener listener);

    }

    public interface ICustomView{


        void uploadSuccess(String info);
        void showMsg(String msg);


    }

    public interface OnCustomFinishedListener {

        void uploadSuccess(String info);

        void showMsg(String msg);


    }

    public interface ICustomPresenter{

        void uploadInfo(String token,String info);
        void onDestroy();

    }
}
