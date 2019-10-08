package com.leothon.cogito.Mvp.View.Activity.BannerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.4
 */
public class BannerActivity extends BaseActivity {

//    @BindView(R.id.content_banner)
//    ImageView contentBanner;

    @BindView(R.id.web)
    WebView webView;
    @BindView(R.id.banner_title)
    CardView bannerTitle;

    private Intent intent;
    private Bundle bundle;
    private String urls;
    private String title;
    @Override
    public int initLayout() {
        return R.layout.activity_banner;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        urls = bundle.getString("urls");
        title = bundle.getString("title");
        setToolbarTitle(title);
        setToolbarSubTitle("刷新");
        loadImg(urls);
        clickButton();
    }

    public void clickButton(){
        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImg(urls);
            }
        });
    }

    public void loadImg(String url){

        webView.getSettings().setSupportZoom(true);//缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);//不显示控制器
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(url);
//        ImageLoader.loadImageViewThumbnailwitherror(this,url,contentBanner,R.drawable.defalutimg);
    }

    @Override
    public void initData() {

    }

}
