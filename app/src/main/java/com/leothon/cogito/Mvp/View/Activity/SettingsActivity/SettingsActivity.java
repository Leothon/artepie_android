package com.leothon.cogito.Mvp.View.Activity.SettingsActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.Update;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Message.UpdateMessage;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Service.DownloadService;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.UpdateDialog;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.8.9
 *
 */
public class SettingsActivity extends BaseActivity {



    @BindView(R.id.cache_show)
    TextView cacheShow;
    @BindView(R.id.update_show)
    TextView updateShow;

    @BindView(R.id.log_out_settings)
    Button logout;

    @BindView(R.id.notice_bot_update_settings)
    View noticeBot;

    private Intent intent;
    private Bundle bundle;

    private Tencent mTencent;

    private boolean isNewVersion = true;

    private BaseApplication baseApplication;

    private String updateVersion;

    private String updateContent;
    @Override
    public int initLayout() {
        return R.layout.activity_settings;
    }


    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection connection = new ServiceConnection() {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "执行了");
            downloadBinder = (DownloadService.DownloadBinder) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarTitle("设置");
        setToolbarSubTitle("");
        if (!bundle.getBoolean("loginstatus")){
            logout.setVisibility(View.GONE);
        }


        cacheShow.setText(CommonUtils.getTotalCacheSize(getApplicationContext()));
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getUpdate(activitysharedPreferencesUtils.getParams("token","").toString())
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        MyToast.getInstance(SettingsActivity.this).show(errorMsg,Toast.LENGTH_SHORT);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {

                        Update update = (Update)baseResponse.getData();
                        updateVersion = update.getUpdateVersion();
                        int updateCode = update.getUpdateCode();

                        if (updateCode > CommonUtils.getVersionCode(SettingsActivity.this)){
                            updateShow.setText("有更新，请点击下载");
                            isNewVersion = false;
                            noticeBot.setVisibility(View.VISIBLE);
                            updateContent = update.getUpdateContent();
                        }else {
                            updateShow.setText("已是最新版本");
                            isNewVersion = true;
                        }


                    }
                });

    }



    @OnClick(R.id.message_settings)
    public void messageSettings(View view){
        //TODO 消息设置
        IntentUtils.getInstence().intent(SettingsActivity.this,MessageActivity.class);
    }
    @OnClick(R.id.advice_settings)
    public void advice(View view){
        //TODO 跳转意见
        IntentUtils.getInstence().intent(SettingsActivity.this,AdviceActivity.class);
    }
    @OnClick(R.id.clearcache_settings)
    public void clearCache(View view){
        //TODO 清除缓存
        MyToast.getInstance(this).show("已清除" + cacheShow.getText() + "缓存",Toast.LENGTH_SHORT);
        CommonUtils.clearAllCache(getApplicationContext());
        cacheShow.setText("0KB");


    }

    @OnClick(R.id.check_update_settings)
    public void update(View view){
        //TODO 检查更新
        if (isNewVersion){
            MyToast.getInstance(this).show("已是最新版本",Toast.LENGTH_SHORT);
        }else {
            noticeBot.setVisibility(View.GONE);
            UpdateMessage updateMessage = new UpdateMessage();
            updateMessage.setMessage("hide");
            EventBus.getDefault().post(updateMessage);
//            Intent intent = new Intent();
//
//            intent.setData(Uri.parse("http://www.artepie.cn/apk/artparty.apk"));//Url 就是你要打开的网址
//            intent.setAction(Intent.ACTION_VIEW);
//            startActivity(intent); //启动浏览器

            dialogLoading(CommonUtils.getVerName(SettingsActivity.this),updateVersion,updateContent);


            //downloadBinder.startDownload();
        }


    }


    private  void dialogLoading(String oldVersion,String newVersion,String updateContent){
        final UpdateDialog dialog = new UpdateDialog(this);


        dialog.setMessage("当前版本：V" + oldVersion + "\n更新版本：V" + newVersion + "\n更新内容:\n" + updateContent)
                .setOnClickBottomListener(new UpdateDialog.OnClickBottomListener() {
                    @Override
                    public void onMarketClick() {
                        dialog.dismiss();
                        CommonUtils.toMarket(SettingsActivity.this);
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                    @Override
                    public void onLinkClick() {
                        dialog.dismiss();
                        Intent intent = new Intent();

                        intent.setData(Uri.parse("http://www.artepie.cn/apk/artparty.apk"));
                        intent.setAction(Intent.ACTION_VIEW);
                        startActivity(intent); //启动浏览器
                    }
                }).show();

    }
    @OnClick(R.id.log_out_settings)
    public void logout(View view){
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,"saveToken");
        sharedPreferencesUtils.clear();
        getDAOSession().deleteAll(UserEntity.class);
        Bundle bundleto = new Bundle();
        bundleto.putString("wechat","");
        IntentUtils.getInstence().intent(SettingsActivity.this, LoginActivity.class,bundleto);
        mTencent.logout(this);

        finish();
    }



    @Override
    public void initData() {

        Intent intentService = new Intent(this, DownloadService.class);
        startService(intentService);
        bindService(intentService, connection, BIND_AUTO_CREATE);
        mTencent = Tencent.createInstance(Constants.APP_ID,SettingsActivity.this.getApplicationContext());

        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }
    }


}
