package com.leothon.cogito.Mvp.View.Activity.UploadClassActivity;

import com.leothon.cogito.Bean.SelectClass;

public class UploadClassPresenter implements UploadClassContract.OnUploadClassFinishedListener,UploadClassContract.IUploadClassPresenter {

    private UploadClassContract.IUploadClassModel iUploadClassModel;
    private UploadClassContract.IUploadClassView iUploadClassView;

    public UploadClassPresenter(UploadClassContract.IUploadClassView iUploadClassView){
        this.iUploadClassModel = new UploadClassModel();
        this.iUploadClassView = iUploadClassView;
    }
    @Override
    public void createSuccess(String msg) {
        if (iUploadClassView != null){
            iUploadClassView.createSuccess(msg);
        }
    }

    @Override
    public void imgSendSuccess(String msg) {
        if (iUploadClassView != null){
            iUploadClassView.imgSendSuccess(msg);
        }
    }

    @Override
    public void getClassSuccess(SelectClass selectClass) {
        if (iUploadClassView != null){
            iUploadClassView.getClassSuccess(selectClass);
        }
    }

    @Override
    public void editClassSuccess(String msg) {
        if (iUploadClassView != null){
            iUploadClassView.editClassSuccess(msg);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iUploadClassView != null){
            iUploadClassView.showInfo(msg);
        }

    }

    @Override
    public void onDestroy() {

        iUploadClassView = null;
        iUploadClassModel = null;
    }

    @Override
    public void createClass(SelectClass selectClass) {
        iUploadClassModel.createClass(selectClass,this);
    }

    @Override
    public void uploadImg(String path) {

        iUploadClassModel.uploadImg(path,this);
    }

    @Override
    public void getClassInfo(String classId) {
        iUploadClassModel.getClassInfo(classId,this);
    }

    @Override
    public void editClassInfo(SelectClass selectClass) {
        iUploadClassModel.editClassInfo(selectClass,this);
    }
}
