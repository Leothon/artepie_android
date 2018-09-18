package com.leothon.cogito.Mvp.View.Activity.PayInfoActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.PayActivity.PayActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.8.13
 */
public class PayInfoActivity extends BaseActivity {

    @BindView(R.id.pay_info_img)
    RoundedImageView payInfoImg;//收费页面的图片
    @BindView(R.id.title_pay_info)
    TextView titlePayInfo;//收费业务的标题
    @BindView(R.id.des_pay_info)
    TextView desPayInfo;//收费业务的描述
    @BindView(R.id.show_price_pay_info)
    Button showPrice;//收费的价格

    @BindView(R.id.art_coin_use_rebate)
    TextView artCoinUseRebate;//艺币数量
    @BindView(R.id.activity_layout_pay_info)
    LinearLayout activityLayoutPayInfo;//是否显示活动信息
    @BindView(R.id.activity_time_pay_info)
    TextView activityTimePayInfo;//活动的时间
    @BindView(R.id.activity_address_pay_info)
    TextView activityAddressPayInfo;//活动的地点
    @BindView(R.id.activity_person_count_pay_info)
    TextView activityPersonCountPayInfo;//活动的人数
    @BindView(R.id.pay_info_rebate)
    TextView payInfoRebate;//收费的折扣
    @BindView(R.id.pay_info_price)
    TextView payInfoPrice;//收费的最后价格

    private String lastPrice;


    private Intent intent;
    private Bundle bundle;

    @Override
    public int initLayout() {
        return R.layout.activity_pay_info;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();

        setToolbarTitle("确认支付信息");
        setToolbarSubTitle("");
        ImageLoader.loadImageViewThumbnailwitherror(this,bundle.getString("imgurl"),payInfoImg,R.drawable.defalutimg);
        titlePayInfo.setText(bundle.getString("title"));
        desPayInfo.setText(bundle.getString("des"));
        showPrice.setText("￥" + bundle.getString("price"));
        artCoinUseRebate.setText("300");
        if (bundle.get("type").equals("class")){
            activityLayoutPayInfo.setVisibility(View.GONE);
        }else {
            activityLayoutPayInfo.setVisibility(View.VISIBLE);
            activityTimePayInfo.setText(bundle.getString("time"));
            activityAddressPayInfo.setText(bundle.getString("address"));
            activityPersonCountPayInfo.setText(bundle.getString("count"));
        }
        payInfoRebate.setText("￥" + CommonUtils.stringto(artCoinUseRebate.getText().toString()));
        lastPrice = calculater(bundle.getString("price"),artCoinUseRebate.getText().toString());
        payInfoPrice.setText("￥" + lastPrice);
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
        int priceCount = Integer.parseInt(price);
        int rebateCount = Integer.parseInt(rebate)/100;
        int finalPrice = priceCount - rebateCount;
        return Integer.toString(finalPrice);
    }
    @OnClick(R.id.art_coin_rebate)
    public void showartdetail(View view){
        //TODO 展示艺币的作用和余额this
        loadDialog(artCoinUseRebate.getText().toString());
    }

    @OnClick(R.id.pay_info_btn)
    public void payInfoUp(View view){
        //TODO 提交数据
        if (bundle.getString("type").equals("class")){
            Bundle bundleto = new Bundle();
            bundleto.putString("title",bundle.getString("title"));
            bundleto.putString("price",lastPrice);
            bundleto.putString("type","class");
            bundleto.putString("url",bundle.getString("imgurl"));
            IntentUtils.getInstence().intent(PayInfoActivity.this, PayActivity.class,bundleto);
            finish();
        }else {

            Bundle bundleto = new Bundle();
            bundleto.putString("title",bundle.getString("title"));
            bundleto.putString("price",lastPrice);
            bundleto.putString("type","activity");
            bundleto.putString("url","");
            IntentUtils.getInstence().intent(PayInfoActivity.this, PayActivity.class,bundleto);
            finish();
        }
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
