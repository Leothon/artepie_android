package com.leothon.cogito.Mvp.View.Activity.SuccessActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;

import butterknife.BindView;


public class SuccessActivity extends BaseActivity {



    @BindView(R.id.success_img)
    ImageView successImg;


    @BindView(R.id.success_to_class)
    TextView successToAction;

    @Override
    public int initLayout() {
        return R.layout.activity_success;
    }

    @Override
    public void initView() {


        setToolbarSubTitle("");
        setToolbarTitle("支付成功");



        successToAction.setText("立即学习");
        successToAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 前往我的订阅
                //IntentUtils.getInstence().intent(SuccessActivity.this, SelectClassActivity.class);
                //finish();
            }
        });


    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }



    @Override
    public void onBackPressed() {
        //TODO eventbus实现
        Bundle bundleto = new Bundle();
        bundleto.putString("type","about");
        IntentUtils.getInstence().intent(SuccessActivity.this, HostActivity.class,bundleto);
        finish();
    }

    @Override
    public void initData() {

    }



}
