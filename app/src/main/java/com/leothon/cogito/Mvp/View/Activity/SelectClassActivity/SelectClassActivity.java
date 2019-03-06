package com.leothon.cogito.Mvp.View.Activity.SelectClassActivity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.SelectClassAdapter;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadClassActivity.UploadClassActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadDetailClassActivity.UploadClassDetailActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;

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


    @BindView(R.id.edit_class_btn)
    CardView editClassBtn;
    @BindView(R.id.edit_my_class)
    TextView editMyClass;
    @BindView(R.id.upload_my_class)
    TextView uploadMyClass;

    private ClassDetail classDetail;
    private LinearLayoutManager linearLayoutManager;
    private Intent intent;
    private Bundle bundle;
    private SelectClassAdapter selectClassAdapter;
    private SelectClassPresenter selectClassPresenter;
    private static int THRESHOLD_OFFSET = 10;
    private String userId;
    @Override
    public int initLayout() {
        return R.layout.activity_select_class;
    }

    @Override
    public void initData() {
        selectClassPresenter = new SelectClassPresenter(this);
        swpSelect.setProgressViewOffset (false,100,300);
        swpSelect.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        userId = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString()).getUid();

    }
    @Override
    public void initView() {
        StatusBarUtils.transparencyBar(this);
        intent = getIntent();
        bundle = intent.getExtras();
        selectClassPresenter.getClassDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));
        swpSelect.setRefreshing(true);

        setToolbarSubTitle("");

    }

    @Override
    public void getClassDetailInfo(ClassDetail classDetail) {
        if (swpSelect.isRefreshing()){
            swpSelect.setRefreshing(false);
        }
        if (classDetail.getTeaClasss().getSelectauthorid().equals(userId)){
            editClassBtn.setVisibility(View.VISIBLE);
        }
        setToolbarTitle(classDetail.getTeaClasss().getSelectlisttitle());
        this.classDetail = classDetail;
        initAdapter();
    }

    @OnClick(R.id.edit_my_class)
    public void editMyClass(View view){
        Bundle bundleto = new Bundle();
        bundleto.putString("type","edit");
        bundleto.putString("classId",classDetail.getTeaClasss().getSelectId());
        IntentUtils.getInstence().intent(SelectClassActivity.this, UploadClassActivity.class,bundleto);
    }

    @OnClick(R.id.upload_my_class)
    public void uploadMyClass(View view){
        Bundle bundleto = new Bundle();
        bundleto.putString("classId",classDetail.getTeaClasss().getSelectId());
        bundleto.putString("title",classDetail.getTeaClasss().getSelectlisttitle());
        IntentUtils.getInstence().intent(SelectClassActivity.this,UploadClassDetailActivity.class,bundleto);
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void deleteSuccess(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
        selectClassPresenter.getClassDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));
    }

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


        selectClassAdapter.setOnDeleteClickListener(new SelectClassAdapter.OnDeleteClickListener() {
            @Override
            public void deleteClickListener(String classdId) {
                loadDeleteDialog(classdId);
            }
        });
    }

    public void initHeadView(SelectClassAdapter selectClassAdapter){
        View headView = View.inflate(this,R.layout.select_class_background,null);
        selectClassAdapter.addHeadView(headView);
    }

    private void loadDeleteDialog(final String classdId){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("是否删除本节课程，删除后不可恢复")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("取消")
                .setNegtive("删除")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        selectClassPresenter.deleteClassDetail(classdId);

                    }

                })
                .show();
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




}
