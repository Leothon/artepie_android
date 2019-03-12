package com.leothon.cogito.Mvp.View.Activity.SettingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Constants;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.View.MyToast;
import com.tencent.tauth.Tencent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.8.9
 *
 */
public class SettingsActivity extends BaseActivity {



    @BindView(R.id.cache_show)
    TextView cacheShow;
    @BindView(R.id.update_show)
    TextView updateShow;

    @BindView(R.id.log_out_settings)
    Button logout;

    private Intent intent;
    private Bundle bundle;

    private Tencent mTencent;

    private BaseApplication baseApplication;

    @Override
    public int initLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarTitle("设置");
        setToolbarSubTitle("");
        if (!bundle.getBoolean("loginstatus")){
            logout.setVisibility(View.GONE);
        }


        cacheShow.setText(CommonUtils.getTotalCacheSize(getApplicationContext()));
    }



    @OnClick(R.id.message_settings)
    public void messageSettings(View view){
        //TODO 消息设置
        IntentUtils.getInstence().intent(SettingsActivity.this,MessageActivity.class);
    }
    @OnClick(R.id.advice_settings)
    public void advice(View view){
        //TODO 跳转意见
        IntentUtils.getInstence().intent(SettingsActivity.this,AdviceActivity.class);
    }
    @OnClick(R.id.clearcache_settings)
    public void clearCache(View view){
        //TODO 清除缓存
        MyToast.getInstance(this).show("已清除" + cacheShow.getText() + "缓存",Toast.LENGTH_SHORT);
        CommonUtils.clearAllCache(getApplicationContext());
        cacheShow.setText("0KB");


    }
    @OnClick(R.id.check_update_settings)
    public void update(View view){
        //TODO 检查更新
        MyToast.getInstance(this).show("已是最新版本",Toast.LENGTH_SHORT);

    }


    @OnClick(R.id.log_out_settings)
    public void logout(View view){
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,"saveToken");
        sharedPreferencesUtils.clear();
        getDAOSession().deleteAll(UserEntity.class);
        Bundle bundleto = new Bundle();
        bundleto.putString("wechat","");
        IntentUtils.getInstence().intent(SettingsActivity.this, LoginActivity.class,bundleto);
        mTencent.logout(this);

        finish();
    }
    @Override
    public void initData() {
        mTencent = Tencent.createInstance(Constants.APP_ID,SettingsActivity.this.getApplicationContext());

        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }
    }


}
