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
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
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

    private SharedPreferencesUtils sharedPreferencesUtilsSettings;
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
        final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,"saveToken");
        String img = "https://www.artepie.com/image/splash.jpg";
        ImageLoader.loadImageViewThumbnailwitherror(this,img,SplashImg,R.drawable.defalutimg);


        BaseApplication baseApplication = (BaseApplication)getApplication();

        tokenValid = new TokenValid();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferencesUtils.contain("login")){
                    if ((boolean)sharedPreferencesUtils.getParams("login",false)){
                        if (sharedPreferencesUtils.contain("token")){
                            String token = sharedPreferencesUtils.getParams("token","").toString();
                            tokenValid = tokenUtils.ValidToken(token);
                            if (tokenValid.isExpired()){
                                MyToast.getInstance(SplashActivity.this).show("您的身份信息已过期，请重新登录", Toast.LENGTH_LONG);
                                sharedPreferencesUtils.clear();
                                baseApplication.getDaoSession().deleteAll(UserEntity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("mark", "normal");
                                IntentUtils.getInstence().intent(SplashActivity.this, LoginActivity.class, bundle);
                                finish();

                            }else {

                                Bundle bundle = new Bundle();
                                bundle.putString("type","home");
                                IntentUtils.getInstence().intent(SplashActivity.this,HostActivity.class,bundle);
                                sharedPreferencesUtils.setParams("login",true);
                                finish();

                            }

                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putString("mark", "normal");
                            IntentUtils.getInstence().intent(SplashActivity.this, LoginActivity.class, bundle);
                            finish();
                        }
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("mark", "normal");
                        IntentUtils.getInstence().intent(SplashActivity.this, LoginActivity.class, bundle);
                        finish();
                    }
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("mark", "normal");
                    IntentUtils.getInstence().intent(SplashActivity.this, LoginActivity.class, bundle);
                    finish();
                }

            }
        },2500);

        skipToHost.setVisibility(View.VISIBLE);
//        skipToHost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }


}
