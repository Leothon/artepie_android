package com.leothon.cogito.Mvp.View.Fragment.AboutPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Constants;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.AboutusActivity.AboutusActivity;
import com.leothon.cogito.Mvp.View.Activity.DownloadActivity.DownloadActivity;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavActivity;
import com.leothon.cogito.Mvp.View.Activity.HistoryActivity.HistoryActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.Mvp.View.Activity.NoticeActivity.NoticeActivity;
import com.leothon.cogito.Mvp.View.Activity.SettingsActivity.MessageActivity;
import com.leothon.cogito.Mvp.View.Activity.SettingsActivity.SettingsActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadActivity.UploadActivity;
import com.leothon.cogito.Mvp.View.Activity.WalletActivity.WalletActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.ArcImageView;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.7.29
 * 我的页面fragment
 */
public class AboutFragment extends BaseFragment implements AboutFragmentContract.IAboutView {


    @BindView(R.id.position_bar_about)
    LinearLayout positionBar;
    @BindView(R.id.search_about)
    CardView search;

    @BindView(R.id.search)
    RelativeLayout searchAbout;

    @BindView(R.id.search_title)
    TextView searchTitle;

    @BindView(R.id.backimg_about)
    ArcImageView backImg;
    @BindView(R.id.usericon_about)
    RoundedImageView userIcon;
    @BindView(R.id.username_about)
    TextView userName;
    @BindView(R.id.signature_about)
    TextView signature;

    @BindView(R.id.check_in)
    TextView checkIn;

    private boolean isCheck = false;

    private AboutPresenter aboutPresenter;
    private UserEntity userEntity;
    private String uuid;

    public AboutFragment() {}


    /**
     * 构造方法
     * @return
     */
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initData() {
        //aboutPresenter = new AboutPresenter(this);
        //aboutPresenter.loadUserAll(fragmentsharedPreferencesUtils.getParams("token","").toString());
        TokenValid tokenValid = tokenUtils.ValidToken(fragmentsharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();

        userEntity = BaseApplication.getInstances().getDaoSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
    }




    @Override
    public void getUserInfo(User user) {
//        userName.setText(user.getUser_name());
//        ImageLoader.loadImageViewThumbnailwitherror(getMContext(),user.getUser_icon(),userIcon,R.drawable.defaulticon);
//        signature.setText(user.getUser_signal());
//        UserEntity userEntity = new UserEntity(user.getUser_id(),user.getUser_name(),user.getUser_icon(),user.getUser_birth(),user.getUser_sex(),user.getUser_signal(),user.getUser_address(),user.getUser_password(),user.getUser_token(),user.getUser_status(),user.getUser_register_time(),user.getUser_register_ip(),user.getUser_lastlogin_time(),user.getUser_phone(),user.getUser_role(),user.getUser_balance());
//
//        BaseApplication.getInstances().getDaoSession().insert(userEntity);
//        Constants.user = user;
//        Constants.icon = user.getUser_icon();
    }
    @Override
    protected void initView() {
        ViewGroup.LayoutParams layoutParams = positionBar.getLayoutParams();
        layoutParams.height = CommonUtils.getStatusBarHeight(getMContext()) - CommonUtils.dip2px(getMContext(),3);
        positionBar.setLayoutParams(layoutParams);
        positionBar.setVisibility(View.INVISIBLE);
        search.setBackgroundColor(getResources().getColor(R.color.alpha));
        searchTitle.setText("搜索相关内容");
        //TODO 根据登录信息来设定用户的各种信息
        Log.e("登录状态", " "+Constants.loginStatus);
        if (Constants.loginStatus == 0){
            userName.setText("未登录");
            userIcon.setImageResource(R.drawable.defaulticon);
            signature.setText("");
        }else {

            userName.setText(userEntity.getUser_name());
            ImageLoader.loadImageViewThumbnailwitherror(getMContext(),userEntity.getUser_icon(),userIcon,R.drawable.defaulticon);
            signature.setText(userEntity.getUser_signal());
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(User user){
        UserEntity userEntityRe = BaseApplication.getInstances().getDaoSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        ImageLoader.loadImageViewThumbnailwitherror(getMContext(),userEntityRe.getUser_icon(),userIcon,R.drawable.defaulticon);
        userName.setText(userEntityRe.getUser_name());
        signature.setText(userEntityRe.getUser_signal());
    }
    @OnClick(R.id.check_in)
    public void checkIn(View view){
        //TODO 签到
        if (isCheck){
            CommonUtils.makeText(getMContext(),"本日已签到");

        }else {
            checkIn.setBackgroundResource(R.drawable.checkback);
            checkIn.setText("已签到");
            CommonUtils.makeText(getMContext(),"签到成功，赠送艺币10");
            isCheck = true;
        }

    }
    @OnClick(R.id.search)
    public void searchAbout(View view){
        CommonUtils.makeText(getMContext(),"点击了搜索");
    }

    @OnClick(R.id.backimg_about)
    public void backImgClick(View v){
        LayoutInflater inflater = LayoutInflater.from(getMContext());
        View imgEntryView = inflater.inflate(R.layout.image, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(getMContext()).create();
        ImageView img = (ImageView)imgEntryView.findViewById(R.id.image_big);
        img.setImageResource(R.drawable.aboutbackground);
        dialog.setView(imgEntryView); // 自定义dialog
        dialog.show();
        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                dialog.cancel();
            }
        });
    }
    @OnClick(R.id.username_about)
    public void userNameClick(View v){

        toPersonPage();
        //TODO 个人主页
    }
    @OnClick(R.id.usericon_about)
    public void userIconClick(View v){

        toPersonPage();
        //TODO 个人主页
    }
    @OnClick(R.id.signature_about)
    public void signatureClick(View v){

        toPersonPage();
        //TODO 个人主页
    }
    @OnClick(R.id.fav_about)
    public void favClick(View v){
        toFavPage();
        //TODO 收藏
    }
    @OnClick(R.id.download_about)
    public void downloadClick(View v){
       toDownloadPage();
        //TODO 下载
    }
    @OnClick(R.id.my_upload)
    public void uploadClick(View v){
        toUploadPage();
        //TODO 上传
    }
    @OnClick(R.id.histroy_about)
    public void historyClick(View v){
        toHistoryPage();
        //TODO 历史
    }
    @OnClick(R.id.wallet_about)
    public void walletClick(View v){
        toWalletPage();
        //TODO 钱包
    }
    @OnClick(R.id.message_about)
    public void messageClick(View v){
        toNoticePage();
        //TODO 信息
    }
    @OnClick(R.id.settings_about)
    public void settingsClick(View v){
        //TODO 设置
        if (Constants.loginStatus == 0){
            Bundle bundleto = new Bundle();
            bundleto.putBoolean("loginstatus",false);
            IntentUtils.getInstence().intent(getMContext(), SettingsActivity.class,bundleto);
        }else if (Constants.loginStatus == 1){
            Bundle bundleto = new Bundle();
            bundleto.putBoolean("loginstatus",true);
            IntentUtils.getInstence().intent(getMContext(), SettingsActivity.class,bundleto);
        }

    }
    @OnClick(R.id.about_about)
    public void aboutClick(View v){
        IntentUtils.getInstence().intent(getMContext(), AboutusActivity.class);
        //TODO 关于
    }



    private void toPersonPage(){
        if (Constants.loginStatus == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (Constants.loginStatus ==1){
            Bundle bundleto = new Bundle();
            bundleto.putString("type","individual");
            IntentUtils.getInstence().intent(getMContext(), IndividualActivity.class,bundleto);
        }
    }

    private void toFavPage(){
        if (Constants.loginStatus == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (Constants.loginStatus ==1){
            IntentUtils.getInstence().intent(getMContext(), FavActivity.class);
        }
    }

    private void toDownloadPage(){
        if (Constants.loginStatus == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (Constants.loginStatus ==1){
            IntentUtils.getInstence().intent(getMContext(), DownloadActivity.class);
        }
    }

    private void toUploadPage(){
        if (Constants.loginStatus == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (Constants.loginStatus ==1){
            //TODO 进入我的发布页面，显示我发布过的内容

            IntentUtils.getInstence().intent(getMContext(), UploadActivity.class);
        }
    }
    private void toHistoryPage(){
        if (Constants.loginStatus == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (Constants.loginStatus ==1){
            IntentUtils.getInstence().intent(getMContext(), HistoryActivity.class);
        }
    }
    private void toWalletPage(){
        if (Constants.loginStatus == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (Constants.loginStatus ==1){
            IntentUtils.getInstence().intent(getMContext(), WalletActivity.class);
        }
    }
    private void toNoticePage(){
        if (Constants.loginStatus == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (Constants.loginStatus ==1){
            IntentUtils.getInstence().intent(getMContext(), NoticeActivity.class);
        }
    }


    @Override
    public void hideLoading() {}

    @Override
    public void showMessage(@NonNull String message) {}

    @Override
    public void showLoading() {}


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void showInfo(String msg) {
        CommonUtils.makeText(getMContext(),msg);
    }


}
