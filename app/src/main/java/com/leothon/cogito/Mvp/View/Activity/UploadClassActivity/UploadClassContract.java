package com.leothon.cogito.Mvp.View.Activity.UploadClassActivity;

import com.leothon.cogito.Bean.SelectClass;

public class UploadClassContract {
    public interface IUploadClassModel{

        void createClass(SelectClass selectClass,OnUploadClassFinishedListener listener);

        void uploadImg(String path,OnUploadClassFinishedListener listener);

        void getClassInfo(String classId,OnUploadClassFinishedListener listener);
        void editClassInfo(SelectClass selectClass,OnUploadClassFinishedListener listener);
    }

    public interface IUploadClassView{

        void imgSendSuccess(String msg);
        void createSuccess(String msg);

        void getClassSuccess(SelectClass selectClass);
        void editClassSuccess(String msg);
        void showInfo(String msg);
    }

    public interface OnUploadClassFinishedListener {

        void createSuccess(String msg);
        void imgSendSuccess(String msg);

        void getClassSuccess(SelectClass selectClass);
        void editClassSuccess(String msg);
        void showInfo(String msg);

    }

    public interface IUploadClassPresenter{
        void onDestroy();
        void createClass(SelectClass selectClass);
        void uploadImg(String path);

        void getClassInfo(String classId);
        void editClassInfo(SelectClass selectClass);

    }
}
