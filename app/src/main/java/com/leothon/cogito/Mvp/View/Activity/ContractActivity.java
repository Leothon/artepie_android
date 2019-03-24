package com.leothon.cogito.Mvp.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.TeaClass;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Message.MessageEvent;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.View.RichEditTextView;
import com.leothon.cogito.handle.CustomHtml;
import com.leothon.cogito.handle.RichEditImageGetter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.8.13
 */
public class ContractActivity extends BaseActivity {



    @BindView(R.id.contract_content)
    RichEditTextView contractContent;

    @BindView(R.id.contract_swp)
    SwipeRefreshLayout swpContract;

    @Override
    public int initLayout() {
        return R.layout.activity_contract;
    }

    @Override
    public void initView() {

        setToolbarSubTitle("");

        swpContract.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swpContract.setRefreshing(false);
            }
        });
        setToolbarTitle("用户服务协议");
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


        swpContract.setRefreshing(true);
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getArticleDetail(Constants.visitor_token,"contractregister")
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        MyToast.getInstance(ContractActivity.this).show(errorMsg, Toast.LENGTH_LONG);

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (swpContract.isRefreshing()){
                            swpContract.setRefreshing(false);
                        }
                        Article article = (Article)baseResponse.getData();
                        Spanned spanned = CustomHtml.fromHtml(article.getArticleContent(),CustomHtml.FROM_HTML_MODE_LEGACY,new RichEditImageGetter(ContractActivity.this,contractContent),null);
                        contractContent.setText(spanned);
                        contractContent.setFocusableInTouchMode(false);
                        contractContent.setCursorVisible(false);
                        contractContent.setFocusable(false);

                    }
                });

    }


}
