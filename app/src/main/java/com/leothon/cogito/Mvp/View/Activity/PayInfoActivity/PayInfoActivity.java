package com.leothon.cogito.Mvp.View.Activity.PayInfoActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leothon.cogito.Bean.AlipayBean;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.Merchandise;
import com.leothon.cogito.DTO.Orders;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.SuccessActivity.SuccessActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;
import com.leothon.cogito.Weight.MDCheckBox;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.DecimalFormat;
import java.util.Map;

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

    private String realDiscount;
    private Orders successOrders;
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

        if (userEntity.getUser_phone() != null){
            if (!userEntity.getUser_phone().equals("")){
                payPresenter.getClassPayInfo(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));
            }else {
                bindPhoneDialog();
            }
        }else {
            bindPhoneDialog();
        }



    }
    private void bindPhoneDialog(){
        final CommonDialog dialog = new CommonDialog(this);

        dialog.setCancelable(false);

        dialog.setMessage("您尚未绑定手机号码或者绑定有误，请重新绑定")
                .setTitle("提示")
                .setSingle(false)
                .setNegtive("暂不绑定")
                .setPositive("前往绑定")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        IntentUtils.getInstence().intent(PayInfoActivity.this, EditIndividualActivity.class);
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        finish();

                    }

                })

                .show();
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

        realDiscount = calculaterDiscountArtCoin(selectClass.getSelectprice(),userEntity.getUser_art_coin());
        payInfoRebate.setText("￥" + realDiscount);
        payInfoPrice.setText("￥" + calculater(selectClass.getSelectprice(),realDiscount));

    }

    private String calculaterDiscountArtCoin(String classPrice,String artCoin){
        DecimalFormat df = new DecimalFormat("#.00");
        float priceCount = Float.valueOf(classPrice);
        float maxDiscount = Float.valueOf(df.format(priceCount * 0.048));
        float rebateCount = Float.valueOf(artCoin)/100;
        if (maxDiscount < rebateCount){
            return String.valueOf(maxDiscount);
        }else {
            return String.valueOf(rebateCount);
        }
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
        float rebateCount = Float.valueOf(rebate);
        float finalPrice = priceCount - rebateCount;

        return String.valueOf(finalPrice);
    }
    @OnClick(R.id.art_coin_rebate)
    public void showartdetail(View view){
        loadDialog(artCoinUseRebate.getText().toString());
    }



    @OnClick(R.id.pay_info_btn_ensure)
    public void sendTransInfo(View view){



        Orders orders = new Orders();

        orders.setOrder_user_id(userEntity.getUser_id());
        orders.setOrder_class_id(selectClass.getSelectId());
        orders.setOrder_class_price(selectClass.getSelectprice());
        orders.setOrder_discount(realDiscount);
        orders.setOrder_name(selectClass.getSelectlisttitle());
        orders.setOrder_description(selectClass.getSelectdesc());
        orders.setOrder_end_price(calculater(selectClass.getSelectprice(),realDiscount));
        payPresenter.createTransactionInfo(orders);

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

    }

    @Override
    public void createOrderSuccess(Orders orders) {
        this.successOrders = orders;
        Merchandise merchandise = new Merchandise();

        merchandise.setMe_Id(selectClass.getSelectId());
        merchandise.setMe_Author(selectClass.getSelectauthor());
        merchandise.setME_Des(selectClass.getSelectdesc());
        merchandise.setMe_Name(selectClass.getSelectlisttitle());
        merchandise.setME_Create_Time(selectClass.getSelecttime());

        successOrders.setLocal_ip(CommonUtils.getLocalIpAddress(this));
        successOrders.setMerchandise(merchandise);
        Log.e(TAG, "createOrderSuccess: " + successOrders.toString() );
        MyToast.getInstance(this).show("订单已生成，请选择支付方式并付款",Toast.LENGTH_LONG);
        payTypeLL.setVisibility(View.VISIBLE);
        payInfoBtn.setVisibility(View.VISIBLE);
        payInfoBtnEnsure.setVisibility(View.GONE);
    }

    /**
     * @param orders
     */
    @Override
    public void getTransactionSuccess(Orders orders) {
        wxTask = new WxTask(this,orders.getPayInfo());
        wxTask.execute();
        Log.e(TAG, "订单信息: " + orders.getPayInfo());

    }

    /**
     *
     * * @param result
     */
    @Override
    public void verifyResult(String result) {
        //TODO 验证结果,显示
        Log.e(TAG, "第二个地方: " + result );
        startAliPay(result);
//        IntentUtils.getInstence().intent(PayInfoActivity.this, SuccessActivity.class);
    }

    @Override
    public void endCheckSuccess(String msg) {
        MyToast.getInstance(PayInfoActivity.this).show("支付成功", Toast.LENGTH_SHORT);
        IntentUtils.getInstence().intent(PayInfoActivity.this, SuccessActivity.class);
    }

    @Override
    public void endCheckFail(String msg) {
        MyToast.getInstance(PayInfoActivity.this).show(msg, Toast.LENGTH_SHORT);

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
//                AlipayBean alipayBean = new AlipayBean();
//                alipayBean.setBody(successOrders.getOrder_description());
//                alipayBean.setSubject(successOrders.getOrder_name());
//                alipayBean.setOut_trade_no(successOrders.getOrder_number());
//                alipayBean.setTotal_amount(successOrders.getOrder_end_price());
//
////                "{product_code:QUICK_MSECURITY_PAY,total_amount:0.01,subject:1,body:我是测试数据,out_trade_no:}"
//                startAliPay(alipayBean.toString());

                AlipayBean alipayBean = new AlipayBean();
                alipayBean.setTotal_amount(successOrders.getOrder_end_price());
                alipayBean.setOut_trade_no(successOrders.getOrder_number());
                alipayBean.setSubject(successOrders.getOrder_name());
                alipayBean.setBody(successOrders.getOrder_description());
                payPresenter.verifyAlipayTransaction(alipayBean);
                break;
            case 2:

                payPresenter.getTransaction(successOrders);
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


    private WxTask wxTask;
    /**
     * 微信支付错误检测 提示语
     */
    private static final String WX_PAY_ERRMSG_1 = "您没有安装微信...";
    private static final String WX_PAY_ERRMSG_2 = "当前版本不支持支付功能...";
    private static final String WX_PAY_ERRMSG_3 = "微信支付失败...";

    private class WxTask extends AsyncTask<String, Integer, Boolean> {
        private IWXAPI mIWXAPI;
        private Context mContext;
        private String payInfo;
        private boolean canPay;

        public WxTask(Activity context, String payInfo) {
            this.mContext = context;
            this.payInfo = payInfo;
            mIWXAPI = WXAPIFactory.createWXAPI(mContext, Constants.WeChat_APP_ID);
            //PayUtils.getInstance().setPayResultCallback(payResultCallback);
        }

        @Override
        protected void onPreExecute() {
            canPay = true;
            if (!mIWXAPI.isWXAppInstalled()) {
                MyToast.getInstance(PayInfoActivity.this).show(WX_PAY_ERRMSG_1,Toast.LENGTH_SHORT);
                canPay = false;
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean sendReq = false;
            if (canPay) {

                JsonParser parse =new JsonParser();
                JsonObject json=(JsonObject) parse.parse(payInfo);
                PayReq req = new PayReq();
                req.appId = json.get("appid").getAsString();
                req.partnerId = json.get("partnerid").getAsString();
                req.prepayId = json.get("prepayid").getAsString();
                req.nonceStr = json.get("noncestr").getAsString();
                req.timeStamp = json.get("timestamp").getAsString();
                req.packageValue = json.get("package").getAsString();
                req.sign = json.get("sign").getAsString();
//                req.extData = "app data"; // optional
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                sendReq = mIWXAPI.sendReq(req);
            }
            return sendReq;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                MyToast.getInstance(PayInfoActivity.this).show(WX_PAY_ERRMSG_3,Toast.LENGTH_SHORT);
            }
        }
    }



    private static final int SDK_PAY_FLAG = 1001;


    private Handler mHandler = new Handler() {
        /**
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功

                    if (TextUtils.equals(resultStatus, "9000")) {

//                        JsonParser parse = new JsonParser();
//                        JsonObject pay_response = (JsonObject) parse.parse(resultInfo);
//                        String content = pay_response.get("alipay_trade_app_pay_response").toString();
//                        JsonObject resultObject = (JsonObject)parse.parse(content);
//
//                        AlipayBean alipayBean = new AlipayBean();
//                        alipayBean.setTotal_amount(resultObject.get("total_amount").toString());
//                        alipayBean.setOut_trade_no(resultObject.get("out_trade_no").toString());
//
                        payPresenter.checkAliPayOrder(resultInfo);



                    } else {
                        MyToast.getInstance(PayInfoActivity.this).show("支付失败", Toast.LENGTH_SHORT);
                        break;
                    }
            }
        }

    };



    private void startAliPay(String orderInfo){


        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(PayInfoActivity.this);
                //获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



}
