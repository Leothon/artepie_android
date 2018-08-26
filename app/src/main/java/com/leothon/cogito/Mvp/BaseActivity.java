package com.leothon.cogito.Mvp;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Constants;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.FitUtils;
import com.leothon.cogito.Utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 * created by leothon on 2018.7.22
 * */

public abstract class BaseActivity<P extends BasePresenter,V extends BaseContract.BaseIView> extends AppCompatActivity implements BaseContract.BaseIView {
    private int REQUEST_CODE_PERMISSION = 0x00099;
    /*
    * 封装常用的方法
    * 1、setcontentview方法
    * 2、绑定bindview了的使用
    * 3、生命周期
    * 4、权限请求
    * */
    private BaseApplication baseApplication;
    private BaseActivity context;
    public P basePresenter;
    private String[] permissions = {Manifest.permission.READ_CONTACTS,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CALL_PHONE,
                                    Manifest.permission.CAMERA};
    protected final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubTitle;


    private static long startTime = 0;
    private static long endTime = 0;
    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FitUtils.autoFit(this,false);
        setContentView(initLayout());
        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }
        context = this;
        addActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtils.setStatusBarColor(this,R.color.white);
        }
        requestPermission(permissions,1);
        initToolBar();
        basePresenter = initPresenter();
        if (basePresenter != null){
            basePresenter.attachWindow(initModel(),this);
        }
        ButterKnife.bind(this);
        initdata();
        initview();

    }

    public void addActivity(){
        baseApplication.addActivity(context);
    }

    public void removeAllActivity(){
        baseApplication.finishActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != getToolbar() && isShowBacking()){
            showBack();
        }
        if (!CommonUtils.netIsConnected(this)){
            CommonUtils.makeText(this,"当前网络不可用");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTime = CommonUtils.getSystemTime();
    }

    @Override
    protected void onStop() {
        super.onStop();
        endTime = CommonUtils.getSystemTime();
        Constants.onlineTime = Constants.onlineTime + (endTime - startTime);
    }

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
    /**
     * 初始化presenter操作
     * @return
     */
    public abstract P initPresenter();

    /**
     * 初始化model操作
     * @return
     */
    public abstract BaseModel initModel();

    @Override
    protected void onDestroy() {


        super.onDestroy();
        if (baseApplication.getCount() == 0){
            Log.e(TAG, "在线时长(全退出)"+CommonUtils.msTomin(Constants.onlineTime));
        }
//        if (unbinder != null && unbinder != Unbinder.EMPTY){
//            unbinder.unbind();
//        }
//        this.unbinder = null;

//        basePresenter.detachWindow();



    }

    public abstract int initLayout();

    public abstract void initview();

    public abstract void initdata();


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

    /**
     * 获取权限集中需要申请权限的列表
     */

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

    public void getData(){
//        RetrofitServiceManager.getInstance().create(HttpService.class)
//                .login("112","11")
//                .compose(ThreadTransformer.<T>switchSchedulers())
//                .subscribe(new BaseObserver<T>() {
//                    @Override
//                    public void onNext(T t) {
//                        super.onNext(t);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                    }
//                })


    }
}
