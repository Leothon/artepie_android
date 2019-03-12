package com.leothon.cogito.Mvp.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.leothon.cogito.Message.MessageEvent;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    public void initView() {
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

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        finish();
    }



    @OnClick(R.id.btn_agree)
    public void setAgree(){
        onBackPressed();
    }

    @Override
    public void initData() {

    }


}
