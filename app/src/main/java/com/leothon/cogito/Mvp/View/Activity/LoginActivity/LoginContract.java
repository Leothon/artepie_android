package com.leothon.cogito.Mvp.View.Activity.LoginActivity;

import com.leothon.cogito.Bean.User;

/**
 * created by leothon on 2018.8.5
 */
public class LoginContract {

    public interface ILoginModel{

        void login(String phoneNumber, String password,OnLoginFinishedListener Listener);


        void register(String phonenumber,OnLoginFinishedListener Listener);




        void isQQRegister(String accessToken,OnLoginFinishedListener listener);
        void isWechatRegister(String accessToken,OnLoginFinishedListener listener);

        void qqUserRegister(User user,OnLoginFinishedListener listener);
        void weChatUserRegister(User user,OnLoginFinishedListener listener);

        void loginByQQ(String accessToken,OnLoginFinishedListener listener);
        void loginByWeChat(String accessToken,OnLoginFinishedListener listener);


        void checkPhoneNumberIsExits(String number,OnLoginFinishedListener listener);
    }

    public interface ILoginView{
        void addverifycode(String code);

        void showFailInfo(String err);

        void registerORloginSuccess(User user);

        void isQQRegisterResult(String msg);
        void isWeChatRegisterResult(String msg);

        void qqUserRegisterSuccess(User user);
        void weChatUserRegisterSuccess(User user);

        void checkNumberResult(String msg);

    }

    public interface OnLoginFinishedListener {



        void registerORloginSuccess(User user);

        void showFailInfo(String err);
        void isQQRegisterResult(String msg);
        void qqUserRegisterSuccess(User user);
        void weChatUserRegisterSuccess(User user);
        void isWeChatRegisterResult(String msg);

        void checkNumberResult(String msg);
    }

    public interface ILoginPresenter{
        void login(String phoneNumber, String password);
        void registerInfo(String phonenumber);
        void onDestroy();
        void isQQRegister(String accessToken);
        void isWechatRegister(String accessToken);
        void qqUserRegister(User user);
        void weChatUserRegister(User user);
        void loginByQQ(String accessToken);
        void loginByWeChat(String accessToken);

        void checkPhoneNumberIsExits(String number);
    }
}
