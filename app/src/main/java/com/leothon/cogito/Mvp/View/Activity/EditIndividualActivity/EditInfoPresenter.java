package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import com.leothon.cogito.Bean.User;

public class EditInfoPresenter implements EditInfoContract.IEditInfoPresenter,EditInfoContract.OnEditInfoFinishedListener {

    private EditInfoContract.IEditInfoView iEditInfoView;
    private EditInfoContract.IEditInfoModel iEditInfoModel;

    public EditInfoPresenter(EditInfoContract.IEditInfoView iEditInfoView){
        this.iEditInfoView = iEditInfoView;
        this.iEditInfoModel = new EditInfoModel();
    }
    @Override
    public void getIconUrl(String url) {
        if (iEditInfoView != null){
            iEditInfoView.getIconUrl(url);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iEditInfoView != null){
            iEditInfoView.showMsg(msg);
        }
    }

    @Override
    public void updateSucess() {
        if (iEditInfoView != null){
            iEditInfoView.updateSuccess();
        }
    }

    @Override
    public void checkNumberResult(String msg) {
        if (iEditInfoView != null){
            iEditInfoView.checkNumberResult(msg);
        }
    }

    @Override
    public void bindPhoneNumberSuccess(String msg) {
        if (iEditInfoView != null){
            iEditInfoView.bindPhoneNumberSuccess(msg);
        }
    }

    @Override
    public void verifyCodeSuccess(String msg) {
        if (iEditInfoView != null){
            iEditInfoView.verifyCodeSuccess(msg);
        }
    }

    @Override
    public void setPasswordSuccess(String msg) {
        if (iEditInfoView != null){
            iEditInfoView.setPasswordSuccess(msg);
        }
    }

    @Override
    public void setPasswordFailed(String msg) {
        if (iEditInfoView != null){
            iEditInfoView.setPasswordFailed(msg);
        }
    }

    @Override
    public void bindPhoneNumberFailed(String msg) {
        if (iEditInfoView != null){
            iEditInfoView.bindPhoneNumberFailed(msg);
        }
    }

    @Override
    public void onDestroy() {
        iEditInfoView = null;
        iEditInfoModel = null;
    }

    @Override
    public void updateUserIcon(String path) {
        iEditInfoModel.uploadIcon(path,this);
    }

    @Override
    public void updateUserInfo(User user) {
        iEditInfoModel.uploadAllInfo(user,this);
    }

    @Override
    public void checkPhoneNumberIsExits(String number) {
        iEditInfoModel.checkPhoneNumberIsExits(number,this);
    }

    @Override
    public void bindPhone(String number, String token) {
        iEditInfoModel.bindPhone(number,token,this);
    }

    @Override
    public void verifyPhoneNumber(String phoneNumber) {
        iEditInfoModel.verifyPhoneNumber(phoneNumber,this);
    }

    @Override
    public void setPassword(String token, String password) {
        iEditInfoModel.setPassword(token,password,this);
    }

    @Override
    public void changePassword(String token, String oldPassword, String password) {
        iEditInfoModel.changePassword(token,oldPassword,password,this);
    }
}
