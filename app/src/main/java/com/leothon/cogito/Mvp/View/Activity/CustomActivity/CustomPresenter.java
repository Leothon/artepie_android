package com.leothon.cogito.Mvp.View.Activity.CustomActivity;

public class CustomPresenter implements CustomContract.ICustomPresenter, CustomContract.OnCustomFinishedListener{

    private CustomContract.ICustomView iCustomView;
    private CustomContract.ICustomModel iCustomModel;

    public CustomPresenter(CustomContract.ICustomView iCustomView){
        this.iCustomView = iCustomView;
        this.iCustomModel = new CustomModel();
    }

    @Override
    public void uploadSuccess(String info) {
        if (iCustomView != null){
            iCustomView.uploadSuccess(info);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iCustomView != null){
            iCustomView.showMsg(msg);
        }
    }

    @Override
    public void uploadInfo(String token, String info) {
        iCustomModel.uploadInfo(token,info,this);
    }

    @Override
    public void onDestroy() {
        iCustomView = null;
        iCustomModel = null;
    }
}
