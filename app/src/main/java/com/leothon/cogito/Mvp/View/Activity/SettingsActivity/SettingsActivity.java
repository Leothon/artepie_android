package com.leothon.cogito.Mvp.View.Activity.SettingsActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.8.9
 *
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.switchnet_settings)
    Switch switchNet;

    @BindView(R.id.cache_show)
    TextView cacheShow;
    @BindView(R.id.update_show)
    TextView updateShow;

    @BindView(R.id.log_out_settings)
    Button logout;

    private Intent intent;
    private Bundle bundle;
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
        switchNet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    Constants.isMobilenet = true;
                }else {
                    Constants.isMobilenet = false;
                }
            }
        });

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
        CommonUtils.makeText(this,"已清除" + cacheShow.getText() + "缓存");
        CommonUtils.clearAllCache(getApplicationContext());
        cacheShow.setText("0KB");


    }
    @OnClick(R.id.check_update_settings)
    public void update(View view){
        //TODO 检查更新
        CommonUtils.makeText(this,"已是最新版本");

    }


    @OnClick(R.id.log_out_settings)
    public void logout(View view){
        Constants.loginStatus = 0;
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,"saveToken");
        sharedPreferencesUtils.clear();
        Constants.user = null;
        Constants.icon = "";
        IntentUtils.getInstence().intent(SettingsActivity.this, LoginActivity.class);
        finish();
    }
    @Override
    public void initData() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
