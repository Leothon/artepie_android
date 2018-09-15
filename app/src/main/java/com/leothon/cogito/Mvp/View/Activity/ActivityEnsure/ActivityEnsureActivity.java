package com.leothon.cogito.Mvp.View.Activity.ActivityEnsure;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityEnsureActivity extends BaseActivity {

    @BindView(R.id.ensure_img)
    RoundedImageView ensureImg;
    @BindView(R.id.ensure_title)
    TextView ensureTitle;
    @BindView(R.id.ensure_name)
    MaterialEditText ensureName;
    @BindView(R.id.ensure_idcard)
    MaterialEditText ensureIdCard;
    @BindView(R.id.ensure_phone)
    MaterialEditText ensurePhone;
    @BindView(R.id.count_ensure)
    EditText countEnsure;

    @BindView(R.id.ensure_price)
    TextView ensurePrice;

    private int countAct = 1;//初始票数
    private Intent intent;
    private Bundle bundle;
    private String lastPrice;
    @Override
    public int initLayout() {
        return R.layout.activity_ensure;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        setToolbarTitle("填写购票信息");
        ImageLoader.loadImageViewThumbnailwitherror(this,bundle.getString("imgurl"),ensureImg,R.drawable.defalutimg);
        ensureTitle.setText(bundle.getString("title"));
        ensurePrice.setText("￥"+bundle.getString("price"));
        lastPrice = bundle.getString("price");
        ensureName.clearFocus();
        ensureIdCard.clearFocus();
        ensurePhone.clearFocus();
    }

    @OnClick(R.id.add_count_ensure)
    public void addCount(View view){
        //TODO 增加数量
        if (countAct >= 10){
            CommonUtils.makeText(this,"您最多只能预定十张");
        }else {
            countAct++;
            countEnsure.setText(countAct+"");
            lastPrice = Integer.toString((Integer.parseInt(bundle.getString("price")))*countAct);
            ensurePrice.setText("￥"+lastPrice);
        }

    }

    @OnClick(R.id.remove_count_ensure)
    public void removeCount(View view){
        //TODO 减少数量
        if (countAct == 1){
            CommonUtils.makeText(this,"最少请选择一张票");
        }else {
            countAct--;
            countEnsure.setText(countAct+"");
            lastPrice = Integer.toString((Integer.parseInt(bundle.getString("price")))*countAct);
            ensurePrice.setText("￥"+lastPrice);
        }

    }
    @OnClick(R.id.ensure_btn)
    public void ensureBtn(View view){
        //TODO 进入
        if (ensureName.getText().toString().equals("") && ensureIdCard.getText().toString().equals("") && ensurePhone.getText().toString().equals("")){
            CommonUtils.makeText(this,"请填写完整信息");
        }else {
            Bundle bundleintent = new Bundle();
            bundleintent.putString("imgurl",bundle.getString("imgurl"));
            bundleintent.putString("title",bundle.getString("title"));
            bundleintent.putString("des","");
            bundleintent.putString("price",lastPrice);
            bundleintent.putString("type","activity");
            bundleintent.putString("time",bundle.getString("time"));
            bundleintent.putString("address",bundle.getString("address"));
            bundleintent.putString("count",countAct+"");
            bundleintent.putString("name",ensureName.getText().toString());
            bundleintent.putString("idcard",ensureIdCard.getText().toString());
            bundleintent.putString("phone",ensurePhone.getText().toString());
            IntentUtils.getInstence().intent(ActivityEnsureActivity.this, PayInfoActivity.class,bundleintent);
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
