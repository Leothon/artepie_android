package com.leothon.cogito.Mvp.View.Activity.PayInfoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;
import com.leothon.cogito.Weight.MDCheckBox;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.8.13
 */
public class PayInfoActivity extends BaseActivity implements PayContract.IPayView {

    @BindView(R.id.pay_info_img)
    RoundedImageView payInfoImg;//收费页面的图片
    @BindView(R.id.pay_info_title)
    TextView titlePayInfo;//收费业务的标题
    @BindView(R.id.pay_info_des)
    TextView desPayInfo;//收费业务的描述
    @BindView(R.id.pay_info_show_price)
    Button showPrice;//收费的价格

    @BindView(R.id.art_coin_use_rebate)
    TextView artCoinUseRebate;//艺币数量

    @BindView(R.id.pay_info_rebate)
    TextView payInfoRebate;//收费的折扣
    @BindView(R.id.pay_info_price)
    TextView payInfoPrice;//收费的最后价格

    @BindView(R.id.wechat_pay)
    RelativeLayout weChatPay;
    @BindView(R.id.wechat_pay_check)
    MDCheckBox weChatPayCheck;

    @BindView(R.id.ali_pay)
    RelativeLayout aliPay;
    @BindView(R.id.ali_pay_check)
    MDCheckBox aliPayCheck;


    @BindView(R.id.pay_type_ll)
    LinearLayout payTypeLL;

    @BindView(R.id.pay_info_btn_ensure)
    Button payInfoBtnEnsure;

    @BindView(R.id.pay_info_btn)
    Button payInfoBtn;

    private PayPresenter payPresenter;

    private Intent intent;
    private Bundle bundle;

    private String uuid;
    private UserEntity userEntity;
    private SelectClass selectClass;
    @Override
    public int initLayout() {
        return R.layout.activity_pay_info;
    }

    @Override
    public void initData() {
        payPresenter = new PayPresenter(this);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();

        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);

    }
    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        payInfoBtn.setVisibility(View.GONE);
        payTypeLL.setVisibility(View.GONE);
        setToolbarTitle("确认支付信息");
        setToolbarSubTitle("");
        showLoadingAnim();
        payPresenter.getClassPayInfo(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));

    }


    @Override
    public void getClassInfo(SelectClass selectClass) {
        this.selectClass = selectClass;

        hideLoadingAnim();
        ImageLoader.loadImageViewThumbnailwitherror(this,selectClass.getSelectbackimg(),payInfoImg,R.drawable.defalutimg);
        titlePayInfo.setText(selectClass.getSelectlisttitle());
        desPayInfo.setText(selectClass.getSelectdesc());
        showPrice.setText("￥" + selectClass.getSelectprice());
        if (userEntity.getUser_art_coin() == null){
            artCoinUseRebate.setText("0");
        }else {
            artCoinUseRebate.setText(userEntity.getUser_art_coin());
        }

        payInfoRebate.setText("￥" + CommonUtils.stringto(artCoinUseRebate.getText().toString()));
        payInfoPrice.setText("￥" + calculater(selectClass.getSelectprice(),artCoinUseRebate.getText().toString()));

    }

    private void loadDialog(String artcoincount){
        final CommonDialog dialog = new CommonDialog(this);

        if (!artcoincount.equals("0")){
            dialog.setMessage("您拥有" + artcoincount + "个艺币，可折扣" + CommonUtils.stringto(artcoincount) + "元")
                    .setTitle("艺币折扣")
                    .setSingle(true)
                    .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                        @Override
                        public void onPositiveClick() {
                            dialog.dismiss();


                        }

                        @Override
                        public void onNegativeClick() {
                            dialog.dismiss();

                        }

                    })
                    .show();
        }else {
            dialog.setMessage("您没有艺币可用。可以通过分享课程和推荐朋友使用来获取艺币")
                    .setTitle("艺币折扣")
                    .setSingle(true)
                    .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                        @Override
                        public void onPositiveClick() {
                            dialog.dismiss();

                        }

                        @Override
                        public void onNegativeClick() {
                            dialog.dismiss();

                        }

                    })
                    .show();
        }


    }

    /**
     * 计算折扣
     * @param price
     * @param rebate
     * @return
     */
    public String calculater(String price,String rebate){
        float priceCount = Float.valueOf(price);
        float rebateCount = Float.valueOf(rebate)/100;
        float finalPrice = priceCount - rebateCount;

        return String.valueOf(finalPrice);
    }
    @OnClick(R.id.art_coin_rebate)
    public void showartdetail(View view){
        //TODO 展示艺币的作用和余额this
        loadDialog(artCoinUseRebate.getText().toString());
    }



    @OnClick(R.id.pay_info_btn_ensure)
    public void sendTransInfo(View view){

        //TODO 发送信息给后台，创建订单，成功后显示付款
        MyToast.getInstance(this).show("订单已生成，请选择支付方式并付款",Toast.LENGTH_LONG);
        payTypeLL.setVisibility(View.VISIBLE);
        payInfoBtn.setVisibility(View.VISIBLE);
        payInfoBtnEnsure.setVisibility(View.GONE);
    }
    @OnClick(R.id.pay_info_btn)
    public void payInfoUp(View view){
        //TODO 提交数据,通过支付宝接口
        if (aliPayCheck.isChecked()){
            startPay(1);
        }else if (weChatPayCheck.isChecked()){
            startPay(2);
        }else {
            MyToast.getInstance(this).show("请选择一种支付方式进行支付",Toast.LENGTH_SHORT);
        }
        //TODO payPresenter.sendTransactionInfo(activitysharedPreferencesUtils.getParams("token","").toString(),selectClass.getSelectId());
//        if (bundle.getString("type").equals("class")){
//            Bundle bundleto = new Bundle();
//            bundleto.putString("title",bundle.getString("title"));
//            bundleto.putString("price",lastPrice);
//            bundleto.putString("type","class");
//            bundleto.putString("url",bundle.getString("imgurl"));
//            IntentUtils.getInstence().intent(PayInfoActivity.this, PayActivity.class,bundleto);
//            finish();
//        }else {
//
//            Bundle bundleto = new Bundle();
//            bundleto.putString("title",bundle.getString("title"));
//            bundleto.putString("price",lastPrice);
//            bundleto.putString("type","activity");
//            bundleto.putString("url","");
//            IntentUtils.getInstence().intent(PayInfoActivity.this, PayActivity.class,bundleto);
//            finish();
//        }
    }


    @OnClick(R.id.wechat_pay)
    public void wechatPay(View view){
        if (weChatPayCheck.isChecked()){
            weChatPayCheck.setChecked(false);
            aliPayCheck.setChecked(false);
        }else if (!weChatPayCheck.isChecked()){
            weChatPayCheck.setChecked(true);
            aliPayCheck.setChecked(false);
        }
    }

    @OnClick(R.id.ali_pay)
    public void aliPay(View view){
        if (aliPayCheck.isChecked()){
            aliPayCheck.setChecked(false);
            weChatPayCheck.setChecked(false);

        }else if (!aliPayCheck.isChecked()){
            aliPayCheck.setChecked(true);
            weChatPayCheck.setChecked(false);
        }
    }



    private void startPay(int payMethod){
        switch (payMethod){
            case 1:
                //TODO 支付宝支付
                MyToast.getInstance(this).show("支付宝支付" + selectClass.getSelectlisttitle(),Toast.LENGTH_SHORT);
                Log.e(TAG, "startPay: " + "支付宝支付");
                break;
            case 2:
                //TODO 微信支付
                MyToast.getInstance(this).show("微信支付" + selectClass.getSelectlisttitle(),Toast.LENGTH_SHORT);
                Log.e(TAG, "startPay: " + "微信支付");
                break;
            default:
                break;
        }
    }


    private void loadExitDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("您确定退出支付吗？精彩不等人~")
                .setTitle("提醒")
                .setSingle(false)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
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
    protected void onDestroy() {
        super.onDestroy();
        payPresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        loadExitDialog();
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }
}
