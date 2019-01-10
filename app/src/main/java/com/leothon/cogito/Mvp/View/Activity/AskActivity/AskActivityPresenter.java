package com.leothon.cogito.Mvp.View.Activity.AskActivity;

import com.leothon.cogito.DTO.SendQAData;

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
    public void onDestory() {
        iAskActivityView = null;
    }

    @Override
    public void uploadFile(String path) {
        iAskActivityModel.uploadFile(path,this);
    }

    @Override
    public void sendData(SendQAData sendQAData) {

        iAskActivityModel.sendQaData(sendQAData,this);
    }
}
