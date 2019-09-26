package com.leothon.cogito.Mvp.View.Activity.CustomActivity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.CustomViewPagerAdapter;
import com.leothon.cogito.Adapter.UploadClassAdapter;
import com.leothon.cogito.Adapter.WalletAdapter;
import com.leothon.cogito.Bean.CustomEnterprise;
import com.leothon.cogito.Bean.CustomHearing;
import com.leothon.cogito.Bean.CustomInstrument;
import com.leothon.cogito.Bean.CustomMood;
import com.leothon.cogito.Bean.CustomStyle;
import com.leothon.cogito.Bean.CustomUse;
import com.leothon.cogito.DTO.CustomUploadInfo;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment.Custom1Fragment;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment.Custom2Fragment;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment.Custom3Fragment;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment.Custom4Fragment;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment.Custom5Fragment;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment.Custom6Fragment;
import com.leothon.cogito.R;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomActivity extends BaseActivity implements CustomContract.ICustomView {



    public CustomUploadInfo customUploadInfo;

    @BindView(R.id.custom_vp)
    ViewPager customVp;
    @BindView(R.id.custom_exit_btn)
    ImageView exitBtn;
    @BindView(R.id.last_btn)
    TextView lastBtn;
    @BindView(R.id.next_btn)
    TextView nextBtn;

    private ArrayList<Fragment> fragments;

    private Custom1Fragment custom1Fragment;
    private Custom2Fragment custom2Fragment;
    private Custom3Fragment custom3Fragment;
    private Custom4Fragment custom4Fragment;
    private Custom5Fragment custom5Fragment;
    private Custom6Fragment custom6Fragment;

    private CustomPresenter customPresenter;


    @Override
    public void initView() {

        customUploadInfo = new CustomUploadInfo();
        CustomEnterprise customEnterprise = new CustomEnterprise();
        CustomUse customUse = new CustomUse();
        CustomMood customMood = new CustomMood();
        CustomHearing customHearing = new CustomHearing();
        CustomStyle customStyle = new CustomStyle();
        CustomInstrument customInstrument = new CustomInstrument();
        customUploadInfo.setCustomEnterprise(customEnterprise);
        customUploadInfo.setCustomUse(customUse);
        customUploadInfo.setCustomMood(customMood);
        customUploadInfo.setCustomHearing(customHearing);
        customUploadInfo.setCustomStyle(customStyle);
        customUploadInfo.setCustomInstrument(customInstrument);
        exitBtn.setVisibility(View.VISIBLE);
        lastBtn.setVisibility(View.GONE);
        nextBtn.setText("下一页");
        setToolbarSubTitle("");
        setToolbarTitle("");
        custom1Fragment = Custom1Fragment.newInstance();
        custom2Fragment = Custom2Fragment.newInstance();
        custom3Fragment = Custom3Fragment.newInstance();
        custom4Fragment = Custom4Fragment.newInstance();
        custom5Fragment = Custom5Fragment.newInstance();
        custom6Fragment = Custom6Fragment.newInstance();
        fragments = new ArrayList<>();
        fragments.add(custom1Fragment);
        fragments.add(custom2Fragment);
        fragments.add(custom3Fragment);
        fragments.add(custom4Fragment);
        fragments.add(custom5Fragment);
        fragments.add(custom6Fragment);
        CustomViewPagerAdapter adapter=new CustomViewPagerAdapter(getSupportFragmentManager(),fragments);
        customVp.setAdapter(adapter);
        customVp.setCurrentItem(0);
        customVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    exitBtn.setVisibility(View.VISIBLE);
                    lastBtn.setVisibility(View.GONE);
                    nextBtn.setText("下一页");
                    nextBtn.setTextColor(getResources().getColor(R.color.fontColor));
                }else if (position == fragments.size() - 1){
                    exitBtn.setVisibility(View.GONE);
                    lastBtn.setVisibility(View.VISIBLE);
                    nextBtn.setText("完成");
                    nextBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
                }else {
                    exitBtn.setVisibility(View.GONE);
                    lastBtn.setVisibility(View.VISIBLE);
                    nextBtn.setText("下一页");
                    nextBtn.setTextColor(getResources().getColor(R.color.fontColor));
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomActivity.super.onBackPressed();
            }
        });

        lastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customVp.setCurrentItem(customVp.getCurrentItem() - 1);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customVp.getCurrentItem() == fragments.size() - 1){

                    if (customUploadInfo.getCustomEnterprise().getEnterpriseName() == null || customUploadInfo.getCustomEnterprise().getEnterpriseName().equals("")){
                        MyToast.getInstance(CustomActivity.this).show("请填写完整",Toast.LENGTH_SHORT);
                    }else {
                        customPresenter.uploadInfo(activitysharedPreferencesUtils.getParams("token","").toString(),customUploadInfo.toString());
                    }


                }else {
                    customVp.setCurrentItem(customVp.getCurrentItem() + 1);
                }
            }
        });

    }



    public void setCustomEnterprise(CustomEnterprise customEnterprise) {
        this.customUploadInfo.setCustomEnterprise(customEnterprise);
    }
    public void setCustomUse(CustomUse customUse) {
        this.customUploadInfo.setCustomUse(customUse);
    }
    public void setCustomMood(CustomMood customMood) {
        this.customUploadInfo.setCustomMood(customMood);
    }
    public void setCustomHearing(CustomHearing customHearing) {
        this.customUploadInfo.setCustomHearing(customHearing);
    }
    public void setCustomStyle(CustomStyle customStyle) {
        this.customUploadInfo.setCustomStyle(customStyle);
    }
    public void setCustomInstrument(CustomInstrument customInstrument) {
        this.customUploadInfo.setCustomInstrument(customInstrument);
    }

    @Override
    public void onBackPressed() {

            final CommonDialog dialog = new CommonDialog(this);


            dialog.setMessage("您尚未提交，是否退出")
                    .setTitle("退出提示")
                    .setSingle(false)
                    .setNegtive("继续定制")
                    .setPositive("退出")
                    .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                        @Override
                        public void onPositiveClick() {
                            dialog.dismiss();
                            CustomActivity.super.onBackPressed();
                        }

                        @Override
                        public void onNegativeClick() {
                            dialog.dismiss();
                        }

                    })
                    .show();


    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    public void initData() {

        customUploadInfo = new CustomUploadInfo();
        customPresenter = new CustomPresenter(this);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_custom;
    }


    @Override
    public void uploadSuccess(String info) {
        MyToast.getInstance(this).show("提交完成，请等待我们与您联系",Toast.LENGTH_SHORT);
        super.onBackPressed();
    }

    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(this).show(msg, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customPresenter.onDestroy();
    }


}
