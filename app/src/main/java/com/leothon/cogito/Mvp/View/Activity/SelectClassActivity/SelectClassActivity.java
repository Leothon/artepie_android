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
import android.widget.ImageView;

import com.leothon.cogito.Adapter.SelectClassAdapter;
import com.leothon.cogito.Adapter.TeacherSelfAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.VideoClass;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.TeacherActivity.TeacherActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.StatusBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.8.4
 */
public class SelectClassActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,SelectClassContract.ISelectClassView {


    @BindView(R.id.swp_select_class)
    SwipeRefreshLayout swpSelect;
    @BindView(R.id.rv_select_class)
    RecyclerView rvSelect;

    @BindView(R.id.select_bar)
    CardView selectBar;


    private ClassDetail classDetail;
    private LinearLayoutManager linearLayoutManager;
    private Intent intent;
    private Bundle bundle;
    private SelectClassAdapter selectClassAdapter;
    private SelectClassPresenter selectClassPresenter;
    private static int THRESHOLD_OFFSET = 10;
    @Override
    public int initLayout() {
        return R.layout.activity_select_class;
    }

    @Override
    public void initData() {
        selectClassPresenter = new SelectClassPresenter(this);
        swpSelect.setProgressViewOffset (false,100,300);
        swpSelect.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

    }
    @Override
    public void initView() {
        StatusBarUtils.transparencyBar(this);
        intent = getIntent();
        bundle = intent.getExtras();
        //loadfalsedata();
        selectClassPresenter.getClassDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));
        swpSelect.setRefreshing(true);
        //initAdapter();
        //setToolbarTitle(bundle.getString("title"));
        setToolbarSubTitle("");

    }

    @Override
    public void getClassDetailInfo(ClassDetail classDetail) {
        if (swpSelect.isRefreshing()){
            swpSelect.setRefreshing(false);
        }
        setToolbarTitle(classDetail.getTeaClasss().getSelectlisttitle());
        this.classDetail = classDetail;
        initAdapter();
    }

    @Override
    public void showInfo(String msg) {
        CommonUtils.makeText(this,msg);
    }

//    public void loadfalsedata(){
//        selectClass = new SelectClass();
//        selectClass.setSelectbackimg(bundle.getString("url"));
//        selectClass.setSelectlisttitle(bundle.getString("title"));
//        selectClass.setSelectauthor(bundle.getString("author"));
//        selectClass.setSelectprice(bundle.getString("price"));
//        selectClass.setSelectdesc(bundle.getString("desc"));
//        ArrayList<VideoClass> classes = new ArrayList<>();
//        for (int i = 0;i < 20;i++){
//            VideoClass videoClass = new VideoClass();
//            videoClass.setVideoTitle("单节课程的标题");
//            classes.add(videoClass);
//        }
//        selectClass.setVideoClasses(classes);
//    }
    public void initAdapter(){
        swpSelect.setOnRefreshListener(this);
        selectClassAdapter = new SelectClassAdapter(classDetail,this);
        initHeadView(selectClassAdapter);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvSelect.setLayoutManager(linearLayoutManager);
        rvSelect.setAdapter(selectClassAdapter);
        rvSelect.addOnScrollListener(new RecyclerView.OnScrollListener() {


            boolean controlVisible = true;
            int scrollDistance = 0;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (controlVisible && scrollDistance > THRESHOLD_OFFSET){//手指上滑即Scroll向下滚动的时候，dy为正
                    selectBar.setVisibility(View.VISIBLE);
                    selectBar.setTranslationY(CommonUtils.getStatusBarHeight(SelectClassActivity.this) - CommonUtils.dip2px(SelectClassActivity.this,3));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        StatusBarUtils.setStatusBarColor(SelectClassActivity.this,R.color.white);
                    }
                    selectBar.setVisibility(View.VISIBLE);
                    controlVisible = false;
                    scrollDistance = 0;
                }else if (!controlVisible && scrollDistance < -THRESHOLD_OFFSET){//手指下滑即Scroll向上滚动的时候，dy为负
                    selectBar.setVisibility(View.GONE);
                    StatusBarUtils.transparencyBar(SelectClassActivity.this);
                    controlVisible = true;
                    scrollDistance = 0;
                }

                //当scrollDistance累计到隐藏（显示)ToolBar之后，如果Scroll向下（向上）滚动，则停止对scrollDistance的累加
                //直到Scroll开始往反方向滚动，再次启动scrollDistance的累加
                if ((controlVisible && dy > 0) || (!controlVisible && dy < 0)){
                    scrollDistance += dy;
                }

            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

        });
        selectClassAdapter.setFavOnClickListener(new SelectClassAdapter.favOnClickListener() {
            @Override
            public void favClickListener(boolean isFaved,String classId) {
                if (isFaved){
                    selectClassPresenter.setunFavClass(activitysharedPreferencesUtils.getParams("token","").toString(),classId);
                }else {
                    selectClassPresenter.setfavClass(activitysharedPreferencesUtils.getParams("token","").toString(),classId);
                }
            }
        });

    }

    public void initHeadView(SelectClassAdapter selectClassAdapter){
        View headView = View.inflate(this,R.layout.select_class_background,null);
        selectClassAdapter.addHeadView(headView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        selectClassPresenter.onDestroy();
    }

    @Override
    public void onRefresh() {
        selectClassPresenter.getClassDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));
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
