package com.leothon.cogito.Mvp.View.Activity.SplashActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leothon.cogito.Bean.verifyCode;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;

import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.7.24
 * APP欢迎页
 * 设置两个东西，从sharedPrefrences中读取是否有登录的账号和密码，如果有，则直接登录到首页，否则进入登录页面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,"AccountPassword");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                                    if (sharedPreferencesUtils.contain("account") && sharedPreferencesUtils.contain("token")){
                                        String username = sharedPreferencesUtils.getParams("account","").toString();
                                        String password = sharedPreferencesUtils.getParams("token","").toString();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("type","home");
                                        IntentUtils.getInstence().intent(SplashActivity.this,HostActivity.class,bundle);
                                        Constants.loginStatus = 1;//表示登录成功
                                        finish();
                                        //使用账号密码进行登录操作
//                    RetrofitServiceManager.getInstance().create(HttpService.class)
//                            .login(username,password)
//                            .compose(ThreadTransformer.switchSchedulers())
//                            .subscribe(new BaseObserver() {
//                                @Override
//                                public void doOnSubscribe(Disposable d) { }
//                                @Override
//                                public void doOnError(String errorMsg) { }
//                                @Override
//                                public void doOnNext(BaseResponse baseResponse) {
//
//                                }
//                                @Override
//                                public void doOnCompleted() {
//                                    IntentUtils.getInstence().intent(SplashActivity.this,HostActivity.class);
//                                    Constants.loginStatus = 1;//表示登录成功
//                                    finish();
//                                }
//                                @Override
//                                public void onNext(Object o) { }
//                            });

                                    }else {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("mark","normal");
                                        IntentUtils.getInstence().intent(SplashActivity.this,LoginActivity.class,bundle);
                                        finish();
                                    }
//                                }else {
//                                    finish();
                                }







        },3000);
    }
}
