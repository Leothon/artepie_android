package com.leothon.cogito.Mvp.View.Activity.LoginActivity;

import com.leothon.cogito.Bean.User;

/**
 * created by leothon on 2018.8.5
 * 实现接口
 */
public class LoginPresenter implements LoginContract.ILoginPresenter,LoginContract.OnLoginFinishedListener {

    private LoginContract.ILoginView iLoginView;
    private LoginContract.ILoginModel iLoginModel;

    public LoginPresenter(LoginContract.ILoginView iLoginView){
        this.iLoginView = iLoginView;
        this.iLoginModel = new LoginModel();
    }



    @Override
    public void registerORloginSuccess(User  user) {
        if (iLoginView != null){
            iLoginView.registerORloginSuccess(user);
        }
    }



    @Override
    public void showFailInfo(String err) {
        if (iLoginView != null){
            iLoginView.showFailInfo(err);
        }
    }


    @Override
    public void isQQRegisterResult(String msg) {
        if (iLoginView != null){
            iLoginView.isQQRegisterResult(msg);
        }
    }

    @Override
    public void qqUserRegisterSuccess(User user) {
        if (iLoginView != null){
            iLoginView.qqUserRegisterSuccess(user);
        }
    }

    @Override
    public void weChatUserRegisterSuccess(User user) {
        if (iLoginView != null){
            iLoginView.weChatUserRegisterSuccess(user);
        }
    }

    @Override
    public void isWeChatRegisterResult(String msg) {
        if (iLoginView != null){
            iLoginView.isWeChatRegisterResult(msg);
        }
    }


    @Override
    public void login(String phoneNumber, String password) {
        iLoginModel.login(phoneNumber,password,this);
    }

    @Override
    public void registerInfo(String phonenumber) {
        iLoginModel.register(phonenumber,this);
    }


    @Override
    public void onDestroy() {
        iLoginView = null;
        iLoginModel = null;
    }



    @Override
    public void isQQRegister(String accessToken) {
        iLoginModel.isQQRegister(accessToken,this);
    }

    @Override
    public void isWechatRegister(String accessToken) {
        iLoginModel.isWechatRegister(accessToken,this);
    }

    @Override
    public void qqUserRegister(User user) {

        iLoginModel.qqUserRegister(user,this);
    }

    @Override
    public void weChatUserRegister(User user) {
        iLoginModel.weChatUserRegister(user,this);
    }

    @Override
    public void loginByQQ(String accessToken) {
        iLoginModel.loginByQQ(accessToken,this);
    }

    @Override
    public void loginByWeChat(String accessToken) {
        iLoginModel.loginByWeChat(accessToken,this);
    }

}
