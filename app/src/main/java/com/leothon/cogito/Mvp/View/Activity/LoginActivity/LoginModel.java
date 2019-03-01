package com.leothon.cogito.Mvp.View.Activity.LoginActivity;

import android.util.Log;

import com.leothon.cogito.Bean.TokenInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Bean.verify;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.MD5Utils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;

import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.8.5
 * 此处进行数据的获取和加载
 */
public class LoginModel implements LoginContract.ILoginModel {


    SharedPreferencesUtils sharedPreferencesUtils;


    @Override
    public void login(String phoneNumber, String password, final LoginContract.OnLoginFinishedListener Listener) {
        sharedPreferencesUtils = new SharedPreferencesUtils(CommonUtils.getContext(),"saveToken");
        String passwordEncrypt = MD5Utils.encrypt(password);
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .usePasswordLogin(phoneNumber,passwordEncrypt)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        Listener.showFailInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            User user = (User)baseResponse.getData();
                            sharedPreferencesUtils.setParams("token",user.getUser_token());
                            Listener.registerORloginSuccess(user);
                        }else {
                            Listener.showFailInfo(baseResponse.getError());
                        }

                    }
                });
    }

    @Override
    public void register(User user, final LoginContract.OnLoginFinishedListener Listener) {
        String phonenumber = user.getUser_phone();
        String verifyCode = user.getVerifyCode();
            //TODO 使用retrofit进行注册
        sharedPreferencesUtils = new SharedPreferencesUtils(CommonUtils.getContext(),"saveToken");
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .usePhoneLogin(phonenumber,verifyCode)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        Listener.showFailInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {
                        Log.e("返回", "完成");
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            User user = (User)baseResponse.getData();
                            sharedPreferencesUtils.setParams("token",user.getUser_token());
                            Listener.registerORloginSuccess(user);
                        }else {
                            //显示错误信息
                            Listener.showFailInfo(baseResponse.getError());
                        }

                    }
                });


    }

    @Override
    public void verifyPhonenumber(String phoneNumber, final LoginContract.OnLoginFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .verifyphone(phoneNumber)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showFailInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        verify verify = (verify)baseResponse.getData();
                        listener.verifysuccess(verify.getCode());
                    }
        });
    }

    @Override
    public void isQQRegister(String accessToken, final LoginContract.OnLoginFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .isQQRegister(accessToken)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showFailInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        String info = baseResponse.getError();
                        listener.isQQRegisterResult(info);
                    }
                });
    }

    @Override
    public void qqUserRegister(User user, final LoginContract.OnLoginFinishedListener listener) {
        sharedPreferencesUtils = new SharedPreferencesUtils(CommonUtils.getContext(),"saveToken");
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .qqUserRegister(user)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showFailInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        User user = (User)baseResponse.getData();
                        sharedPreferencesUtils.setParams("token",user.getUser_token());
                        listener.qqUserRegisterSuccess(user);
                    }
                });
    }

    @Override
    public void loginByQQ(String accessToken, final LoginContract.OnLoginFinishedListener listener) {
        sharedPreferencesUtils = new SharedPreferencesUtils(CommonUtils.getContext(),"saveToken");
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getUserInfoByQQ(accessToken)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showFailInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        User user = (User)baseResponse.getData();
                        sharedPreferencesUtils.setParams("token",user.getUser_token());
                        listener.qqUserRegisterSuccess(user);
                    }
                });
    }
}
