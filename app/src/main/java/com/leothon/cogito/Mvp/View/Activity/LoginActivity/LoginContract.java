package com.leothon.cogito.Mvp.View.Activity.LoginActivity;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Mvp.BaseContract;

/**
 * created by leothon on 2018.8.5
 */
public class LoginContract {

    public interface ILoginModel{
        /**
         * 登录
         * @param user
         * @param Listener
         */
        void login(User user,OnLoginFinishedListener Listener);

        /**
         * 注册
         * @param user
         * @param Listener
         */
        void register(User user,OnLoginFinishedListener Listener);

    }

    public interface ILoginView{
        /**
         * presenter对view的操作
         * 这个地方放上VIew中需要操作的方法
         */

        void setUsernameORPassWordEmpty();
        void showSuccess();
        void showFail();

        void setSomeEmpty();
        void showRegisterSuccess();
        void showRegisterFail();
        void showphoneIllegal();
    }

    public interface OnLoginFinishedListener {

        void onUsernameORPassWordEmpty();
        void onSuccess();
        void onFail();

        void onSomeEmpty();
        void onRegisterSuccess();
        void onRegisterFail();
        void onPhoneIllegal();
    }

    public interface ILoginPresenter{
        void validateCrendentials(User user);
        void registerInfo(User user);
        void onDestory();
    }
}
