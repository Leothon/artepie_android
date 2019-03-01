package com.leothon.cogito.Mvp.View.Activity.LoginActivity;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Mvp.BaseContract;

/**
 * created by leothon on 2018.8.5
 */
public class LoginContract {

    public interface ILoginModel{

        void login(String phoneNumber, String password,OnLoginFinishedListener Listener);


        void register(User user,OnLoginFinishedListener Listener);


        void verifyPhonenumber(String phoneNumber,OnLoginFinishedListener listener);


        void isQQRegister(String accessToken,OnLoginFinishedListener listener);

        void qqUserRegister(User user,OnLoginFinishedListener listener);

        void loginByQQ(String accessToken,OnLoginFinishedListener listener);
    }

    public interface ILoginView{
        /**
         * presenter对view的操作
         * 这个地方放上VIew中需要操作的方法
         */



        void addverifycode(String code);

        void showFailInfo(String err);

        void registerORloginSuccess(User user);

        void isQQRegisterResult(String msg);

        void qqUserRegisterSuccess(User user);

    }

    public interface OnLoginFinishedListener {



        void registerORloginSuccess(User user);

        void showFailInfo(String err);
        void verifysuccess(String code);
        void isQQRegisterResult(String msg);
        void qqUserRegisterSuccess(User user);
    }

    public interface ILoginPresenter{
        void login(String phoneNumber, String password);
        void registerInfo(User user);
        void onDestroy();
        void verifyphone(String phoneNumber);
        void isQQRegister(String accessToken);
        void qqUserRegister(User user);
        void loginByQQ(String accessToken);
    }
}
