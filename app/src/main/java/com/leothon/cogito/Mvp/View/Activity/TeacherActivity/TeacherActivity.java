package com.leothon.cogito.Mvp.View.Activity.TeacherActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.TeacherSelfAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.TeaClass;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.View.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;

public class TeacherActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,TeacherContract.ITeacherView {


    private TeacherSelfAdapter teacherSelfAdapter;

    @BindView(R.id.swp_tea)
    SwipeRefreshLayout swpTea;
    @BindView(R.id.rv_tea)
    RecyclerView rvTea;
    @BindView(R.id.tea_bar)
    CardView teaBar;
    @BindView(R.id.teacher_icon)
    RoundedImageView teacherIcon;
    private Intent intent;
    private Bundle bundle;

    private boolean isLogin;
    private TeaClass teaClass;
    private TeacherPresenter teacherPresenter;



    private LinearLayoutManager linearLayoutManager;

    private ArrayList<SelectClass> selectClasses;
    @Override
    public int initLayout() {
        return R.layout.activity_teacher;
    }
    @Override
    public void initData() {
        teacherPresenter = new TeacherPresenter(this);
        swpTea.setProgressViewOffset (false,100,300);
        swpTea.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
    }

    @Override
    public void initView() {
        StatusBarUtils.transparencyBar(this);
        selectClasses = new ArrayList<>();
        intent = getIntent();
        bundle = intent.getExtras();
        teacherPresenter.loadTeaClass(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("teacherId"));
        swpTea.setRefreshing(true);
        setToolbarTitle("");


    }

    public void initAdapter(){
        swpTea.setOnRefreshListener(this);
        if ((boolean)activitysharedPreferencesUtils.getParams("login",false)){
            isLogin = true;
        }else {
            isLogin = false;
        }
        teacherSelfAdapter = new TeacherSelfAdapter(teaClass,this,isLogin);
        initHeadView(teacherSelfAdapter);
        initDesView(teacherSelfAdapter);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvTea.setLayoutManager(linearLayoutManager);
        rvTea.setAdapter(teacherSelfAdapter);

        rvTea.addOnScrollListener(new loadMoreDataListener(linearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpTea.setRefreshing(true);
                teacherPresenter.loadMoreClassByTeacher(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("teacherId"),currentPage * 20);
            }
        });
        rvTea.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManager != null){
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    if (position > 0){
                        teaBar.setVisibility(View.VISIBLE);
                        teaBar.setTranslationY(CommonUtils.getStatusBarHeight(TeacherActivity.this) - CommonUtils.dip2px(TeacherActivity.this,3));
                        teacherIcon.setVisibility(View.VISIBLE);
                        ImageLoader.loadImageViewThumbnailwitherror(TeacherActivity.this,teaClass.getTeacher().getUser_icon(),teacherIcon,R.drawable.defaulticon);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                            StatusBarUtils.setStatusBarColor(TeacherActivity.this,R.color.white);
                        }

                    }else {
                        teaBar.setVisibility(View.GONE);
                        StatusBarUtils.transparencyBar(TeacherActivity.this);

                    }
                }
            }
        });

    }

    @Override
    public void getTeacherClass(TeaClass teaClass) {
        setToolbarSubTitle(teaClass.getTeacher().getUser_name());
        if (swpTea.isRefreshing()){
            swpTea.setRefreshing(false);
        }
        this.teaClass = teaClass;
        this.selectClasses = teaClass.getTeaClassses();
        initAdapter();
    }

    @Override
    public void getMoreTeacherClass(ArrayList<SelectClass> selectClasses) {
        if (swpTea.isRefreshing()){
            swpTea.setRefreshing(false);
        }
        for (int i = 0;i < selectClasses.size(); i++){
            this.selectClasses.add(selectClasses.get(i));

        }
        teacherSelfAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }


    public void initHeadView(TeacherSelfAdapter teacherSelfAdapter){
        View headView = View.inflate(this,R.layout.teacher_background,null);
        teacherSelfAdapter.addHeadView(headView);
    }

    public void initDesView(TeacherSelfAdapter teacherSelfAdapter){
        View desView = View.inflate(this,R.layout.teacher_background_description,null);
        teacherSelfAdapter.addDesView(desView);
    }





    @Override
    public void onRefresh() {
        teacherPresenter.loadTeaClass(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("teacherId"));
    }

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
