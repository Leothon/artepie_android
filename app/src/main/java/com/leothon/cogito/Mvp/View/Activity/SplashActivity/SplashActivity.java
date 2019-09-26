package com.leothon.cogito.Mvp.View.Activity.SplashActivity;

import android.os.Bundle;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.DTO.SplashInfo;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.7.24
 * APP欢迎页
 * 设置两个东西，从sharedPrefrences中读取是否有登录的账号和密码，如果有，则直接登录到首页，否则进入登录页面
 */
public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_ad)
    ImageView SplashImg;
    @BindView(R.id.skip_host)
    TextView skipToHost;
    TokenValid tokenValid = null;

    boolean login;

    private SharedPreferencesUtils sharedPreferencesUtilsSettings;
    private SharedPreferencesUtils sharedPreferencesUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        sharedPreferencesUtilsSettings = new SharedPreferencesUtils(this,"artSettings");
        if (!sharedPreferencesUtilsSettings.contain("soundNotice")){
            sharedPreferencesUtilsSettings.setParams("soundNotice",true);
        }
        if (!sharedPreferencesUtilsSettings.contain("qaNotice")){
            sharedPreferencesUtilsSettings.setParams("qaNotice",true);
        }
        if (!sharedPreferencesUtilsSettings.contain("classNotice")){
            sharedPreferencesUtilsSettings.setParams("classNotice",true);
        }
        sharedPreferencesUtils = new SharedPreferencesUtils(this,"saveToken");
        //String img = "https://artepie.oss-cn-zhangjiakou.aliyuncs.com/img/splash.jpg";


        BaseApplication baseApplication = (BaseApplication)getApplication();

        tokenValid = new TokenValid();


        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getSplash("")
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        MyToast.getInstance(SplashActivity.this).show("出错",Toast.LENGTH_LONG);

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        SplashInfo splashInfo = (SplashInfo)baseResponse.getData();
                        ImageLoader.loadImageViewThumbnailSplashwitherror(SplashActivity.this,splashInfo.getSplashUrl(),SplashImg,R.drawable.splash_img);

                        if (sharedPreferencesUtils.contain("login")){
                            if ((boolean)sharedPreferencesUtils.getParams("login",false)){
                                if (sharedPreferencesUtils.contain("token")){
                                    String token = sharedPreferencesUtils.getParams("token","").toString();
                                    tokenValid = tokenUtils.ValidToken(token);
                                    if (tokenValid.isExpired()){
                                        MyToast.getInstance(SplashActivity.this).show("您的身份信息已过期，请重新登录", Toast.LENGTH_LONG);
                                        sharedPreferencesUtils.clear();
                                        baseApplication.getDaoSession().deleteAll(UserEntity.class);
                                        login = false;

                                    }else {

                                       login = true;

                                    }

                                }else {
                                    login = false;
                                }
                            }else {
                                login = false;
                            }
                        }else {
                            login = false;
                        }

                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (login){
                                    toMainPage();
                                }else {
                                    toLogin();
                                }
                            }
                        };
                        handler.postDelayed(runnable,2500);

                        skipToHost.setVisibility(View.VISIBLE);
                        skipToHost.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                handler.removeCallbacks(runnable);
                                if (login){

                                    toMainPage();
                                }else {
                                    toLogin();
                                }
                            }
                        });
                    }
                });


    }

    private void toLogin(){
        Bundle bundle = new Bundle();
        bundle.putString("mark", "normal");
        IntentUtils.getInstence().intent(SplashActivity.this, LoginActivity.class, bundle);
        finish();
    }

    private void toMainPage(){
        Bundle bundle = new Bundle();
        bundle.putString("type","home");
        IntentUtils.getInstence().intent(SplashActivity.this,HostActivity.class,bundle);
        sharedPreferencesUtils.setParams("login",true);
        finish();
    }


}
