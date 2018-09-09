package com.leothon.cogito.Mvp.View.Activity.LoginActivity;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;

/**
 * created by leothon on 2018.8.5
 * 此处进行数据的获取和加载
 */
public class LoginModel implements LoginContract.ILoginModel {

    SharedPreferencesUtils sharedPreferencesUtils;
    @Override
    public void login(User user, LoginContract.OnLoginFinishedListener listener) {
        String username = user.getU_name();
        String password = user.getU_password();
        //登录操作

        sharedPreferencesUtils = new SharedPreferencesUtils(CommonUtils.getContext(),"AccountPassword");
        if (!username.equals("") && !password.equals("")){

            //TODO 使用Retrofit进行登录 成功登录，则调用 listener.onSuccess()，如果失败，则调用listener.onFail()

            if (username.equals("12345") && password.equals("12345")){
                listener.onSuccess();
            }else {
                listener.onFail();
            }
        }else {
            listener.onUsernameORPassWordEmpty();
        }
    }

    @Override
    public void register(User user, LoginContract.OnLoginFinishedListener listener) {
        String phonenumber = user.getU_phone();
        String username = user.getU_name();
        String password = user.getU_password();
        sharedPreferencesUtils = new SharedPreferencesUtils(CommonUtils.getContext(),"RegisterInfo");
        if (!phonenumber.equals("") && !username.equals("") && !password.equals("") && CommonUtils.isPhoneNumber(phonenumber)){

            //TODO 使用retrofit进行注册
            listener.onRegisterSuccess();

        }else if (!CommonUtils.isPhoneNumber(phonenumber)){
            listener.onPhoneIllegal();
        }else {
            listener.onSomeEmpty();
        }
    }
}
