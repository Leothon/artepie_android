package com.leothon.cogito.Mvp.View.Activity.SplashActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.TokenValid;
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
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;

import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.7.24
 * APP欢迎页
 * 设置两个东西，从sharedPrefrences中读取是否有登录的账号和密码，如果有，则直接登录到首页，否则进入登录页面
 */
public class SplashActivity extends AppCompatActivity {

    TokenValid tokenValid = null;
    private BaseApplication baseApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,"saveToken");

        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }


        tokenValid = new TokenValid();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferencesUtils.contain("token")){
                    String token = sharedPreferencesUtils.getParams("token","").toString();
                    tokenValid = tokenUtils.ValidToken(token);
                    if (tokenValid.isExpired()){
                        //TODO token过期，需要重新登录
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("type","home");
                        IntentUtils.getInstence().intent(SplashActivity.this,HostActivity.class,bundle);
                        baseApplication.setLoginStatus(1);//表示登录成功
                        baseApplication = null;
                        finish();
                    }

                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("mark", "normal");
                    IntentUtils.getInstence().intent(SplashActivity.this, LoginActivity.class, bundle);
                    baseApplication = null;
                    finish();
                }
            }
        },3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseApplication = null;
    }
}
