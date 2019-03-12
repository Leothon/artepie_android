package com.leothon.cogito.Mvp;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.DataBase.DaoSession;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.FitUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/*
 * created by leothon on 2018.7.22
 * */

public abstract class BaseActivity extends AppCompatActivity {
    private int REQUEST_CODE_PERMISSION = 0x00099;

    private BaseApplication baseApplication;
//    private BaseActivity context;
    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    protected final String TAG = this.getClass().getSimpleName();

    ZLoadingDialog dialog = new ZLoadingDialog(this);

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubTitle;
    public SharedPreferencesUtils activitysharedPreferencesUtils;


    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FitUtils.autoFit(this,false);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        setContentView(initLayout());
        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplicationContext();
        }
        //context = this;
        activitysharedPreferencesUtils = new SharedPreferencesUtils(this,"saveToken");
        //addActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtils.setStatusBarColor(this,R.color.white);
        }
        requestPermission(permissions,1);
        initToolBar();

        ButterKnife.bind(this);

        if (!activitysharedPreferencesUtils.getParams("token","").toString().equals("")){
            JPushInterface.setAlias(this,0,tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString()).getUid());
        }

        initData();
        initView();


    }


//    public void addActivity(){
//        baseApplication.addActivity(context);
//    }
//
//    public void removeAllActivity(){
//        baseApplication.finishActivity();
//    }

    public DaoSession getDAOSession(){
        return baseApplication.getDaoSession();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (null != getToolbar() && isShowBacking()){
            showBack();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public abstract int initLayout();

    public abstract void initView();

    public abstract void initData();

    /**
     * 封装状态栏
     */
    public void initToolBar(){
        if (mToolbar != null){
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null){
            mToolbarTitle.setText(getTitle());
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public TextView getToolbarTitle(){
        return mToolbarTitle;
    }

    public TextView getToolbarSubTitle(){
        return mToolbarSubTitle;
    }

    public void setToolbarTitle(CharSequence title){
        if (mToolbarTitle != null){
            mToolbarTitle.setText(title);
        }else {
            getToolbar().setTitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    public void setToolbarSubTitle(CharSequence title) {
        if (mToolbarSubTitle != null){
            mToolbarSubTitle.setText(title);
        }else {
            getToolbar().setSubtitle(title);
            setSupportActionBar(getToolbar());
        }
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }

    public void showBack(){
        getToolbar().setNavigationIcon(R.drawable.baseline_arrow_back_24);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 默认显示返回按钮
     * @return
     */
    protected boolean isShowBacking(){
        return true;
    }





    public void requestPermission(String[] permissions,int requestCode){
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)){
            permissionSuccess(REQUEST_CODE_PERMISSION);
        }else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this,needPermissions.toArray(new String[needPermissions.size()]),REQUEST_CODE_PERMISSION);

        }
    }

    /**
     * 检测是否获取到所有的权限
     */
    private boolean checkPermissions(String[] permissions){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }

        for(String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }

        return true;
    }


    private List<String> getDeniedPermissions(String[] permissions){
        List<String> needRequestPermissionsList = new ArrayList<>();
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                needRequestPermissionsList.add(permission);
            }
        }
        return needRequestPermissionsList;
    }

    /**
     * 系统请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION){
            if (verifyPermissions(grantResults)){
                permissionSuccess(REQUEST_CODE_PERMISSION);
            }else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Integer i){
        finish();
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    public void permissionSuccess(int requestCode) {
        Log.d(TAG, "获取权限成功=" + requestCode);

    }

    /**
     * 权限获取失败
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
        Log.d(TAG, "获取权限失败=" + requestCode);
    }


    public void showLoadingAnim(){
        dialog.setLoadingBuilder(Z_TYPE.SEARCH_PATH)
                .setLoadingColor(Color.GRAY)
                .setHintText("请稍后...")
                .setHintTextSize(16)
                .setHintTextColor(Color.GRAY)
                .setDurationTime(0.5)
                .setDialogBackgroundColor(Color.parseColor("#ffffff")) // 设置背景色，默认白色
                .show();
    }

    public void hideLoadingAnim(){
        dialog.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseApplication = null;
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
