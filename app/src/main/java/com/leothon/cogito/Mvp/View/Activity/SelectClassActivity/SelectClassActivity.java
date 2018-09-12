package com.leothon.cogito.Mvp.View.Activity.SelectClassActivity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leothon.cogito.Adapter.SelectClassAdapter;
import com.leothon.cogito.Adapter.TeacherSelfAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.VideoClass;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.TeacherActivity.TeacherActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.StatusBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
/**
 * created by leothon on 2018.8.4
 */
public class SelectClassActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.swp_select_class)
    SwipeRefreshLayout swpSelect;
    @BindView(R.id.rv_select_class)
    RecyclerView rvSelect;

    @BindView(R.id.select_bar)
    CardView selectBar;

    private SelectClass selectClass;
    private LinearLayoutManager linearLayoutManager;
    private Intent intent;
    private Bundle bundle;
    @Override
    public int initLayout() {
        return R.layout.activity_select_class;
    }

    @Override
    public void initview() {
        StatusBarUtils.transparencyBar(this);
        intent = getIntent();
        bundle = intent.getExtras();
        loadfalsedata();
        initAdapter();
        setToolbarTitle(bundle.getString("title"));
        setToolbarSubTitle("");
    }

    public void loadfalsedata(){
        selectClass = new SelectClass();
        selectClass.setSelectbackimg(bundle.getString("url"));
        selectClass.setSelectlisttitle(bundle.getString("title"));
        selectClass.setSelectauthor(bundle.getString("author"));
        selectClass.setSelectprice(bundle.getString("price"));
        selectClass.setSelectdesc(bundle.getString("desc"));
        ArrayList<VideoClass> classes = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            VideoClass videoClass = new VideoClass();
            videoClass.setVideoTitle("单节课程的标题");
            classes.add(videoClass);
        }
        selectClass.setVideoClasses(classes);
    }
    public void initAdapter(){
        swpSelect.setOnRefreshListener(this);
        SelectClassAdapter selectClassAdapter = new SelectClassAdapter(selectClass,this);
        initHeadView(selectClassAdapter);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvSelect.setLayoutManager(linearLayoutManager);
        rvSelect.setAdapter(selectClassAdapter);
        rvSelect.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManager != null){
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    if (position > 0){
                        selectBar.setVisibility(View.VISIBLE);
                        selectBar.setTranslationY(CommonUtils.getStatusBarHeight(SelectClassActivity.this) - 5);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                            StatusBarUtils.setStatusBarColor(SelectClassActivity.this,R.color.white);
                        }
                        selectBar.setVisibility(View.VISIBLE);
                    }else {
                        selectBar.setVisibility(View.GONE);
                        StatusBarUtils.transparencyBar(SelectClassActivity.this);
                    }
                }
            }
        });

    }

    public void initHeadView(SelectClassAdapter selectClassAdapter){
        View headView = View.inflate(this,R.layout.select_class_background,null);
        selectClassAdapter.addHeadView(headView);
    }

    @Override
    public void onRefresh() {
        swpSelect.setRefreshing(false);
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


}
