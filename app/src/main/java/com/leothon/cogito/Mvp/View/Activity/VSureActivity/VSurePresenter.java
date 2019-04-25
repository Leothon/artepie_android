package com.leothon.cogito.Mvp.View.Activity.VSureActivity;

import com.leothon.cogito.Bean.AuthInfo;
import com.leothon.cogito.Bean.User;

import java.io.File;
import java.util.ArrayList;

public class VSurePresenter implements VSureContract.IVSurePresenter,VSureContract.OnVSureFinishedListener {

    private VSureContract.IVSureModel ivSureModel;
    private VSureContract.IVSureView ivSureView;

    public VSurePresenter(VSureContract.IVSureView ivSureView){
        this.ivSureView = ivSureView;
        this.ivSureModel = new VSureModel();
    }
    @Override
    public void sendSuccess(String msg) {
        if (ivSureView != null){
            ivSureView.sendSuccess(msg);
        }
    }

    @Override
    public void uploadImgSuccess(String msg) {
        if (ivSureView != null){
            ivSureView.uploadImgSuccess(msg);
        }
    }

    @Override
    public void getUserInfoSuccess(User user) {
        if (ivSureView != null){
            ivSureView.getUserInfoSuccess(user);
        }
    }

    @Override
    public void getInfoSuccess(ArrayList<AuthInfo> authInfos) {
        if (ivSureView != null){
            ivSureView.getInfoSuccess(authInfos);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (ivSureView != null){
            ivSureView.showInfo(msg);
        }
    }

    @Override
    public void onDestroy() {
        ivSureView = null;
        ivSureModel = null;
    }

    @Override
    public void sendAuthInfo(String token, String img, String content) {
        ivSureModel.sendAuthInfo(token,img,content,this);
    }

    @Override
    public void getAuthInfo(String token) {
        ivSureModel.getAuthInfo(token,this);
    }

    @Override
    public void uploadAuthImg(String path) {
        ivSureModel.uploadAuthImg(path,this);
    }

    @Override
    public void getUserInfo(String token) {

        ivSureModel.getUserInfo(token,this);
    }
}
