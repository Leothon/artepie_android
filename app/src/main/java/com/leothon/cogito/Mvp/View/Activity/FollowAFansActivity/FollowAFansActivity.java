package com.leothon.cogito.Mvp.View.Activity.FollowAFansActivity;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;


import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;

import butterknife.BindView;

public class FollowAFansActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.swp_followafans)
    SwipeRefreshLayout swpFollowFans;
    @BindView(R.id.rv_followafans)
    RecyclerView rvFollowFans;



    private Intent intent;
    private Bundle bundle;
    @Override
    public int initLayout() {
        return R.layout.activity_follow_afans;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        if (bundle.getString("type").equals("follow")){
            //TODO 加载关注列表
            setToolbarTitle("关注列表");
        }else {
            //TODO 加载粉丝列表
            setToolbarTitle("粉丝列表");

        }


    }

    @Override
    public void initData() {

    }


    @Override
    public void onRefresh() {

    }
}
