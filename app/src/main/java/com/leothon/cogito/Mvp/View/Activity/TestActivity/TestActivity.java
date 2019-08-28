package com.leothon.cogito.Mvp.View.Activity.TestActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;


import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.leothon.cogito.Adapter.TestSelfAdapter;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.TypeClass;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.View.MyToast;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.5
 */
public class TestActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,TestContract.ITestView {

    private Intent intent;
    private Bundle bundle;

    @BindView(R.id.swp_test)
    SwipeRefreshLayout swpTest;
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    @BindView(R.id.test_bar)
    CardView testBar;

    private boolean isLogin;

    private TestSelfAdapter testSelfAdapter;
    private TypeClass typeClass;
    private LinearLayoutManager linearLayoutManager;
    private TestPresenter testPresenter;
    private ArrayList<SelectClass> selectClasses;
    @Override
    public int initLayout() {
        return R.layout.activity_test;
    }

    @Override
    public void initData() {
        testPresenter = new TestPresenter(this);
        swpTest.setProgressViewOffset (false,100,300);
        swpTest.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);


    }
    @Override
    public void initView() {
        StatusBarUtils.transparencyBar(this);
        selectClasses = new ArrayList<>();
        intent = getIntent();
        bundle = intent.getExtras();
        //LoadFalseData();
        //initAdapter();
        swpTest.setRefreshing(true);
        testPresenter.loadTypeClass(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("type"));
        setToolbarSubTitle("");
        setToolbarTitle(bundle.getString("type"));
    }


    @Override
    public void onRefresh() {
        testPresenter.loadTypeClass(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("type"));
    }

    @Override
    public void getTypeClass(TypeClass typeClass) {

        if (swpTest.isRefreshing()){
            swpTest.setRefreshing(false);
        }

        this.typeClass = typeClass;

        typeClass.setType(bundle.getString("type"));
        initAdapter();
    }

    @Override
    public void getMoreTypeClass(ArrayList<SelectClass> selectClasses) {
        if (swpTest.isRefreshing()){
            swpTest.setRefreshing(false);
        }
        for (int i = 0;i < selectClasses.size(); i++){
            this.selectClasses.add(selectClasses.get(i));

        }
        testSelfAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }
    public void initAdapter(){
            swpTest.setOnRefreshListener(this);
            if ((boolean)activitysharedPreferencesUtils.getParams("login",false)){
                isLogin = true;
            }else {
                isLogin = false;
            }
            testSelfAdapter = new TestSelfAdapter(typeClass,this,isLogin);
            initHeadView(testSelfAdapter);
            initDesView(testSelfAdapter);
            linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            rvTest.setLayoutManager(linearLayoutManager);
            rvTest.setAdapter(testSelfAdapter);
            rvTest.addOnScrollListener(new loadMoreDataListener(linearLayoutManager) {
                @Override
                public void onLoadMoreData(int currentPage) {
                    swpTest.setRefreshing(true);
                    testPresenter.loadMoreClassByType(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("type"),currentPage * 20);
                }
            });
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                                StatusBarUtils.setStatusBarColor(TestActivity.this,R.color.white);
                            }
                            testBar.setTranslationY(CommonUtils.getStatusBarHeight(TestActivity.this) - CommonUtils.dip2px(TestActivity.this,3));
                        }else {
                            testBar.setVisibility(View.GONE);
                            StatusBarUtils.transparencyBar(TestActivity.this);
                        }
                    }
                }
            });

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
    protected void onDestroy() {
        super.onDestroy();
        testPresenter.onDestroy();
    }

    /**
     *
     */
    class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;
        private int length;

        public SpaceItemDecoration(int space,int length) {
            this.space = space;
            this.length = length;

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) == (length - 1)) {
                outRect.bottom = space;
            }
        }


    }
}
