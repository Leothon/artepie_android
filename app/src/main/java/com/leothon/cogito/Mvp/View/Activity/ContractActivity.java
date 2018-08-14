package com.leothon.cogito.Mvp.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;


import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.8.13
 */
public class ContractActivity extends BaseActivity {


    private Intent intent;
    private Bundle bundle;

    @BindView(R.id.contract_content)
    TextView contractContent;

    @Override
    public int initLayout() {
        return R.layout.activity_contract;
    }

    @Override
    public void initview() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        if (bundle.getString("type").equals("recharge")){
            setToolbarTitle("用户充值协议");
            contractContent.setText(R.string.recharge_contract);
        }else {
            setToolbarTitle("注册协议");
            contractContent.setText(R.string.register_contract);
        }
    }

    @OnClick(R.id.btn_agree)
    public void setAgree(){
        onBackPressed();
    }

    @Override
    public void initdata() {

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
