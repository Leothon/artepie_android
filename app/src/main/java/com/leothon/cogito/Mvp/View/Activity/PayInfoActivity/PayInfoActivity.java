package com.leothon.cogito.Mvp.View.Activity.PayInfoActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.PayActivity.PayActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
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
//    @BindView(R.id.activity_layout_pay_info)
//    LinearLayout activityLayoutPayInfo;//是否显示活动信息
//    @BindView(R.id.activity_time_pay_info)
//    TextView activityTimePayInfo;//活动的时间
//    @BindView(R.id.activity_address_pay_info)
//    TextView activityAddressPayInfo;//活动的地点
//    @BindView(R.id.activity_person_count_pay_info)
//    TextView activityPersonCountPayInfo;//活动的人数
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

        setToolbarTitle("确认支付信息");
        setToolbarSubTitle("");

        payPresenter.getClassPayInfo(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));

    }


    @Override
    public void getClassInfo(SelectClass selectClass) {
        this.selectClass = selectClass;

        ImageLoader.loadImageViewThumbnailwitherror(this,selectClass.getSelectbackimg(),payInfoImg,R.drawable.defalutimg);
        titlePayInfo.setText(selectClass.getSelectlisttitle());
        desPayInfo.setText(selectClass.getSelectdesc());
        showPrice.setText("￥" + selectClass.getSelectprice());
        if (userEntity.getUser_art_coin() == null){
            artCoinUseRebate.setText("0");
        }else {
            artCoinUseRebate.setText(userEntity.getUser_art_coin());
        }

//        if (bundle.get("type").equals("class")){
//            activityLayoutPayInfo.setVisibility(View.GONE);
//        }else {
//            activityLayoutPayInfo.setVisibility(View.VISIBLE);
//            activityTimePayInfo.setText(bundle.getString("time"));
//            activityAddressPayInfo.setText(bundle.getString("address"));
//            activityPersonCountPayInfo.setText(bundle.getString("count"));
//        }
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

    @OnClick(R.id.pay_info_btn)
    public void payInfoUp(View view){
        //TODO 提交数据,通过支付宝接口
        if (aliPayCheck.isChecked()){
            startPay(1);
        }else if (weChatPayCheck.isChecked()){
            startPay(2);
        }else {
            CommonUtils.makeText(this,"请选择一种支付方式进行支付");
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
                CommonUtils.makeText(this,"支付宝支付" + selectClass.getSelectlisttitle());
                Log.e(TAG, "startPay: " + "支付宝支付");
                break;
            case 2:
                //TODO 微信支付
                CommonUtils.makeText(this,"微信支付" + selectClass.getSelectlisttitle());
                Log.e(TAG, "startPay: " + "微信支付");
                break;
            default:
                break;
        }
    }
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

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
        CommonUtils.makeText(this,msg);
    }
}
