package com.leothon.cogito.Mvp.View.Activity.AskActivity;

import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.SendQAData;

import java.io.File;

public class AskActivityPresenter implements AskActivityContract.IAskActivityPresenter,AskActivityContract.OnAskActivityFinishedListener {

    private AskActivityContract.IAskActivityView iAskActivityView;
    private AskActivityContract.IAskActivityModel iAskActivityModel;

    public AskActivityPresenter(AskActivityContract.IAskActivityView iAskActivityView){
        this.iAskActivityView = iAskActivityView;
        this.iAskActivityModel = new AskActivityModel();
    }
    @Override
    public void getUploadUrl(String url) {
        if (iAskActivityView != null){
            iAskActivityView.getUploadUrl(url);
        }
    }

    @Override
    public void sendSuccess(String msg) {
        if (iAskActivityView != null){
            iAskActivityView.sendSuccess(msg);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iAskActivityView != null){
            iAskActivityView.showInfo(msg);
        }
    }

    @Override
    public void getReInfo(QAData qaData) {
        if (iAskActivityView != null){
            iAskActivityView.getReInfo(qaData);
        }
    }

    @Override
    public void reSuccess(String msg) {
        if (iAskActivityView != null){
            iAskActivityView.reSuccess(msg);
        }
    }

    @Override
    public void getImgUrl(String url) {
        if (iAskActivityView != null){
            iAskActivityView.getImgUrl(url);
        }
    }

    @Override
    public void onDestroy() {
        iAskActivityView = null;
        iAskActivityModel = null;
    }

    @Override
    public void uploadFile(String path) {
        iAskActivityModel.uploadFile(path,this);
    }

    @Override
    public void sendData(SendQAData sendQAData) {

        iAskActivityModel.sendQaData(sendQAData,this);
    }

    @Override
    public void getReInfo(String qaId, String token) {
        iAskActivityModel.getReInfo(qaId,token,this);
    }

    @Override
    public void reContent(String token, String content, String qaId) {
        iAskActivityModel.reContent(token,content,qaId,this);
    }

    @Override
    public void uploadVideoImg(File file) {
        iAskActivityModel.uploadVideoImg(file,this);
    }
}
