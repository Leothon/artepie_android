package com.leothon.cogito.Mvp.View.Activity.PayActivity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.SuccessActivity.SuccessActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.leothon.cogito.Weight.MDCheckBox;

import butterknife.BindView;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {

    @BindView(R.id.pay_title)
    TextView payTitle;
    @BindView(R.id.pay_price)
    TextView payPrice;

    @BindView(R.id.pay_to_money)
    Button payToMoney;
    private Intent intent;
    private Bundle bundle;

    @BindView(R.id.account_pay_check)
    MDCheckBox accountCheck;
    @BindView(R.id.wechat_pay_check)
    MDCheckBox wechatCheck;
    @BindView(R.id.ali_pay_check)
    MDCheckBox aliCheck;
    private Handler handler;
    @Override
    public int initLayout() {
        return R.layout.activity_pay;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        setToolbarTitle("确认支付");
        payTitle.setText(bundle.getString("title"));
        payPrice.setText("￥" + bundle.getString("price"));
        payToMoney.setText("确认支付 ￥" + bundle.getString("price"));
        accountCheck.setChecked(false);
        wechatCheck.setChecked(false);
        aliCheck.setChecked(false);
        handler = new Handler();
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialog();
            }
        });
    }

    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("您确定退出支付吗？精彩不等人~")
                .setTitle("提醒")
                .setSingle(false)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        //TODO 删除该收藏
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

    @Override
    public void onBackPressed() {
        loadDialog();
    }

    @OnClick(R.id.account_pay)
    public void accountPay(View view){
        //TODO 选择账户余额付款
        if (accountCheck.isChecked()){
            accountCheck.setChecked(false);
            wechatCheck.setChecked(false);
            aliCheck.setChecked(false);
        }else if (!accountCheck.isChecked()){
            accountCheck.setChecked(true);
            wechatCheck.setChecked(false);
            aliCheck.setChecked(false);
        }
    }

    @OnClick(R.id.wechat_pay)
    public void wechatPay(View view){
        //TODO 选择微信付款
        if (wechatCheck.isChecked()){
            wechatCheck.setChecked(false);
            accountCheck.setChecked(false);
            aliCheck.setChecked(false);
        }else if (!wechatCheck.isChecked()){
            wechatCheck.setChecked(true);
            accountCheck.setChecked(false);
            aliCheck.setChecked(false);
        }
    }

    @OnClick(R.id.ali_pay)
    public void aliPay(View view){
        //TODO 选择支付宝付款
        if (aliCheck.isChecked()){
            aliCheck.setChecked(false);
            wechatCheck.setChecked(false);
            accountCheck.setChecked(false);
        }else if (!aliCheck.isChecked()){
            aliCheck.setChecked(true);
            wechatCheck.setChecked(false);
            accountCheck.setChecked(false);
        }
    }

    @OnClick(R.id.pay_to_money)
    public void payToMoney(View view){
        //TODO 支付
        if (accountCheck.isChecked()){
            CommonUtils.makeText(this,"使用账户余额支付");
            startPay(0);
        }else if (aliCheck.isChecked()){
            CommonUtils.makeText(this,"使用支付宝支付");
            startPay(1);
        }else if (wechatCheck.isChecked()){
            CommonUtils.makeText(this,"使用微信支付");
            startPay(2);
        }else {
            CommonUtils.makeText(this,"请选择一种支付方式进行支付");
        }
    }

    public void startPay(final int payType){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (payType == 0){
                    CommonUtils.makeText(PayActivity.this,"使用账户支付");
                    pay();
                }else if (payType == 1){
                    CommonUtils.makeText(PayActivity.this,"使用支付宝支付");
                    pay();
                }else if (payType == 2){
                    CommonUtils.makeText(PayActivity.this,"使用微信支付");
                    pay();
                }

            }
        },5000);
    }

    public void pay(){
        Bundle bundleto = new Bundle();
        bundleto.putString("title",bundle.getString("title"));
        bundleto.putString("url",bundle.getString("url"));
        bundleto.putString("type",bundle.getString("type"));
        IntentUtils.getInstence().intent(PayActivity.this, SuccessActivity.class,bundleto);
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
