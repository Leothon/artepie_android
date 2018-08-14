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
    public void onUsernameORPassWordEmpty() {
        if (iLoginView != null){
            iLoginView.setUsernameORPassWordEmpty();
        }
    }

    @Override
    public void onSuccess() {
        if (iLoginView != null){
            iLoginView.showSuccess();
        }
    }

    @Override
    public void onFail() {
        if (iLoginView != null){
            iLoginView.showFail();
        }
    }

    @Override
    public void onSomeEmpty() {
        if (iLoginView != null){
            iLoginView.setSomeEmpty();
        }
    }

    @Override
    public void onRegisterSuccess() {
        if (iLoginView != null){
            iLoginView.showRegisterSuccess();
        }
    }

    @Override
    public void onRegisterFail() {
        if (iLoginView != null){
            iLoginView.showRegisterFail();
        }
    }

    @Override
    public void onPhoneIllegal() {
        if (iLoginView != null){
            iLoginView.showphoneIllegal();
        }
    }

    /**
     * 在presenter中调用model中的登录
     * @param user
     */
    @Override
    public void validateCrendentials(User user) {

        iLoginModel.login(user,this);

    }

    @Override
    public void registerInfo(User user) {
        iLoginModel.register(user,this);
    }

    @Override
    public void onDestory() {
        iLoginView = null;
    }

}
