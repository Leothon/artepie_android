package com.leothon.cogito.Mvp.View.Activity.TestActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.leothon.cogito.Adapter.TestSelfAdapter;
import com.leothon.cogito.Bean.ClassItem;

import com.leothon.cogito.Bean.TestSelf;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.StatusBarUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.5
 */
public class TestActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Intent intent;
    private Bundle bundle;

    @BindView(R.id.swp_test)
    SwipeRefreshLayout swpTest;
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    @BindView(R.id.test_bar)
    CardView testBar;

    private TestSelfAdapter testSelfAdapter;
    private TestSelf testSelf;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public int initLayout() {
        return R.layout.activity_test;
    }

    @Override
    public void initview() {
        StatusBarUtils.transparencyBar(this);
        intent = getIntent();
        bundle = intent.getExtras();
        LoadFalseData();
        initAdapter();
        setToolbarTitle(testSelf.getTesttitle());
        setToolbarSubTitle("");
    }


    public void initAdapter(){
            swpTest.setOnRefreshListener(this);
            testSelfAdapter = new TestSelfAdapter(testSelf,this);
            initHeadView(testSelfAdapter);
            initDesView(testSelfAdapter);
            linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            rvTest.setLayoutManager(linearLayoutManager);
            rvTest.setAdapter(testSelfAdapter);
            rvTest.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);


                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (linearLayoutManager != null){
                        int position = linearLayoutManager.findFirstVisibleItemPosition();
                        if (position > 0){
                            testBar.setVisibility(View.VISIBLE);
                            testBar.setTranslationY(CommonUtils.getStatusBarHeight(TestActivity.this));
                        }else {
                            testBar.setVisibility(View.GONE);
                        }
                    }
                }
            });

    }


    private void LoadFalseData(){
        ArrayList<ClassItem> classitems = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            ClassItem classItem = new ClassItem();
            classItem.setClasstitle("民族唱法");
            classItem.setClassdescription("这个是艺考的描述");
            classItem.setClassprice("0");
            classItem.setAuthorname("陈独秀");
            classitems.add(classItem);
        }
        testSelf = new TestSelf();
        testSelf.setTesttitle(bundle.getString("title"));
        testSelf.setTestdescription(bundle.getString("description"));
        testSelf.setTestclasses(classitems);
    }
    public void initHeadView(TestSelfAdapter testSelfAdapter){
        View headView = View.inflate(this,R.layout.test_background,null);
        testSelfAdapter.addHeadView(headView);
    }

    public void initDesView(TestSelfAdapter testSelfAdapter){
        View desView = View.inflate(this,R.layout.test_background_description,null);
        testSelfAdapter.addDesView(desView);
    }

    @Override
    public void initdata() {

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

    @Override
    public void onRefresh() {
        swpTest.setRefreshing(false);
    }
}
