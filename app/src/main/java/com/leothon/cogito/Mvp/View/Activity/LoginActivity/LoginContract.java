package com.leothon.cogito.Mvp.View.Activity.LoginActivity;

import com.leothon.cogito.Bean.User;

/**
 * created by leothon on 2018.8.5
 */
public class LoginContract {

    public interface ILoginModel{

        void login(String phoneNumber, String password,OnLoginFinishedListener Listener);


        void register(User user,OnLoginFinishedListener Listener);


        void verifyPhonenumber(String phoneNumber,OnLoginFinishedListener listener);


        void isQQRegister(String accessToken,OnLoginFinishedListener listener);
        void isWechatRegister(String accessToken,OnLoginFinishedListener listener);

        void qqUserRegister(User user,OnLoginFinishedListener listener);
        void weChatUserRegister(User user,OnLoginFinishedListener listener);

        void loginByQQ(String accessToken,OnLoginFinishedListener listener);
        void loginByWeChat(String accessToken,OnLoginFinishedListener listener);
    }

    public interface ILoginView{
        void addverifycode(String code);

        void showFailInfo(String err);

        void registerORloginSuccess(User user);

        void isQQRegisterResult(String msg);
        void isWeChatRegisterResult(String msg);

        void qqUserRegisterSuccess(User user);
        void weChatUserRegisterSuccess(User user);

    }

    public interface OnLoginFinishedListener {



        void registerORloginSuccess(User user);

        void showFailInfo(String err);
        void verifysuccess(String code);
        void isQQRegisterResult(String msg);
        void qqUserRegisterSuccess(User user);
        void weChatUserRegisterSuccess(User user);
        void isWeChatRegisterResult(String msg);
    }

    public interface ILoginPresenter{
        void login(String phoneNumber, String password);
        void registerInfo(User user);
        void onDestroy();
        void verifyphone(String phoneNumber);
        void isQQRegister(String accessToken);
        void isWechatRegister(String accessToken);
        void qqUserRegister(User user);
        void weChatUserRegister(User user);
        void loginByQQ(String accessToken);
        void loginByWeChat(String accessToken);
    }
}
