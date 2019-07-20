package com.leothon.cogito.Mvp.View.Fragment.AboutPage;

import android.graphics.Color;
import android.os.Bundle;


import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.Update;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Message.NoticeMessage;
import com.leothon.cogito.Message.UpdateMessage;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.AboutusActivity.AboutusActivity;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavActivity;
import com.leothon.cogito.Mvp.View.Activity.HistoryActivity.HistoryActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.NoticeActivity.NoticeActivity;
import com.leothon.cogito.Mvp.View.Activity.QAHisActivity.QAHisActivity;
import com.leothon.cogito.Mvp.View.Activity.SearchActivity.SearchActivity;
import com.leothon.cogito.Mvp.View.Activity.SettingsActivity.SettingsActivity;
import com.leothon.cogito.Mvp.View.Activity.WalletActivity.WalletActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.ArcImageView;
import com.leothon.cogito.View.AuthView;
import com.leothon.cogito.View.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;


import static com.leothon.cogito.Base.BaseApplication.getApplication;

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

    @BindView(R.id.notice_bot_about)
    View noticeBot;

    @BindView(R.id.notice_bot_update)
    View noticeBotUpdate;

//    @BindView(R.id.check_in)
//    TextView checkIn;

    @BindView(R.id.auth_mark_about)
    AuthView authMark;
//    private boolean isCheck = false;

    private AboutPresenter aboutPresenter;
    private UserEntity userEntity;
    private String uuid;

    private BaseApplication baseApplication;
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

        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }
        if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            userEntity = baseApplication.getDaoSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
            ImageLoader.loadImageViewThumbnailwitherrorandbulr(getMContext(),userEntity.getUser_icon(),backImg);
        }

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
        //ImageLoader.loadImageViewThumbnailwitherrorandbulr(getMContext(),userEntity.getUser_icon(),backImg);
        ViewGroup.LayoutParams layoutParams = positionBar.getLayoutParams();
        layoutParams.height = CommonUtils.getStatusBarHeight(getMContext()) - CommonUtils.dip2px(getMContext(),3);
        positionBar.setLayoutParams(layoutParams);
        positionBar.setVisibility(View.INVISIBLE);
        search.setBackgroundColor(getResources().getColor(R.color.alpha));
        searchTitle.setText("搜索相关内容");
        //TODO 根据登录信息来设定用户的各种信息
        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            userName.setText("未登录");
            userIcon.setImageResource(R.drawable.defaulticon);
            signature.setText("");
        }else {

            userName.setText(userEntity.getUser_name());
            ImageLoader.loadImageViewThumbnailwitherror(getMContext(),userEntity.getUser_icon(),userIcon,R.drawable.defaulticon);
            Log.e("initView: ",userEntity.getUser_role() );
            int role = CommonUtils.isVIP(userEntity.getUser_role());
            if (role != 2){
                authMark.setVisibility(View.VISIBLE);
                if (role == 0){
                    authMark.setColor(Color.parseColor("#f26402"));
                }else if (role == 1){
                    authMark.setColor(Color.parseColor("#2298EF"));
                }else {
                    authMark.setVisibility(View.GONE);
                    signature.setText(userEntity.getUser_signal());
                }
                signature.setText("认证：" + userEntity.getUser_role().substring(1));

            }else {
                authMark.setVisibility(View.GONE);
                signature.setText(userEntity.getUser_signal());
            }

        }
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        HostActivity hostActivity = (HostActivity)getActivity();
        String botStatus = hostActivity.getBotStatus();
        if (botStatus != null){
            if (botStatus.equals("notice")){
                noticeBot.setVisibility(View.VISIBLE);
            }else {
                noticeBot.setVisibility(View.GONE);
            }
        }

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(User user){
        UserEntity userEntityRe = baseApplication.getDaoSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        ImageLoader.loadImageViewThumbnailwitherror(getMContext(),userEntityRe.getUser_icon(),userIcon,R.drawable.defaulticon);
        userName.setText(userEntityRe.getUser_name());
        int role = CommonUtils.isVIP(userEntityRe.getUser_role());
        if (role != 2){
            authMark.setVisibility(View.VISIBLE);
            if (role == 0){
                authMark.setColor(Color.parseColor("#f26402"));
            }else if (role == 1){
                authMark.setColor(Color.parseColor("#2298EF"));
            }else {
                authMark.setVisibility(View.GONE);
                signature.setText(userEntityRe.getUser_signal());
            }
            signature.setText("认证：" + userEntityRe.getUser_role().substring(1));

        }else {
            authMark.setVisibility(View.GONE);
            signature.setText(userEntityRe.getUser_signal());
        }
    }
//    @OnClick(R.id.check_in)
//    public void checkIn(View view){
//        //TODO 签到
//        if (isCheck){
//            MyToast.getInstance(getMContext()).show("本日已签到",Toast.LENGTH_SHORT);
//
//        }else {
//            checkIn.setBackgroundResource(R.drawable.checkback);
//            checkIn.setText("已签到");
//            MyToast.getInstance(getMContext()).show("签到成功，赠送艺币10",Toast.LENGTH_SHORT);
//            isCheck = true;
//        }
//
//    }
    @OnClick(R.id.search)
    public void searchAbout(View view){
        IntentUtils.getInstence().intent(getMContext(), SearchActivity.class);
    }

//    @OnClick(R.id.backimg_about)
//    public void backImgClick(View v){
//        LayoutInflater inflater = LayoutInflater.from(getMContext());
//        View imgEntryView = inflater.inflate(R.layout.image, null); // 加载自定义的布局文件
//        final AlertDialog dialog = new AlertDialog.Builder(getMContext()).create();
//        ImageView img = (ImageView)imgEntryView.findViewById(R.id.image_big);
//
//
//        //ImageView imageView = new ImageView(context);
//        //img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(img);
//        ImageView target = imageViewWeakReference.get();
//        if (target != null) {
//            img.setImageResource(R.drawable.aboutbackground);
//            //ImageLoader.loadImageViewThumbnailwitherror(context, ask.getQa_video_cover(), target, R.drawable.defalutimg);
//        }
//
//        dialog.setView(imgEntryView); // 自定义dialog
//        dialog.show();
//        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);
//        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
//        imgEntryView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View paramView) {
//                dialog.cancel();
//            }
//        });
//    }
    @OnClick(R.id.username_about)
    public void userNameClick(View v){

        toPersonPage();
    }
    @OnClick(R.id.usericon_about)
    public void userIconClick(View v){

        toPersonPage();
    }
    @OnClick(R.id.signature_about)
    public void signatureClick(View v){

        toPersonPage();
    }
    @OnClick(R.id.fav_about)
    public void favClick(View v){
        toFavPage();
    }
    @OnClick(R.id.order_about)
    public void orderClick(View v){
       toOrderPage();
    }
    @OnClick(R.id.my_upload)
    public void uploadClick(View v){
        toUploadPage();
    }

    /**
     * 跳转订阅
     * @param v
     */
    @OnClick(R.id.histroy_about)
    public void historyClick(View v){
        toBuyPage();
    }
    @OnClick(R.id.wallet_about)
    public void walletClick(View v){
        MyToast.getInstance(getMContext()).show("暂未开放，敬请期待",Toast.LENGTH_SHORT);
        //toWalletPage();
    }
    @OnClick(R.id.message_about)
    public void messageClick(View v){
        toNoticePage();
    }
    @OnClick(R.id.settings_about)
    public void settingsClick(View v){
        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            Bundle bundleto = new Bundle();
            bundleto.putBoolean("loginstatus",false);
            IntentUtils.getInstence().intent(getMContext(), SettingsActivity.class,bundleto);
        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            Bundle bundleto = new Bundle();
            bundleto.putBoolean("loginstatus",true);
            IntentUtils.getInstence().intent(getMContext(), SettingsActivity.class,bundleto);
        }

    }
    @OnClick(R.id.about_about)
    public void aboutClick(View v){
        IntentUtils.getInstence().intent(getMContext(), AboutusActivity.class);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(NoticeMessage message) {
        if (message.getMessage().equals("show")){
            noticeBot.setVisibility(View.VISIBLE);
        }else {
            noticeBot.setVisibility(View.GONE);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UpdateMessage updateMessage) {

        if (updateMessage.getMessage().equals("show")){
            noticeBotUpdate.setVisibility(View.VISIBLE);
        }else {
            noticeBotUpdate.setVisibility(View.GONE);
        }


    }

    private void toPersonPage(){
        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            CommonUtils.loadinglogin(getMContext());
        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            Bundle bundleto = new Bundle();
            bundleto.putString("type","individual");
            IntentUtils.getInstence().intent(getMContext(), IndividualActivity.class,bundleto);
        }
    }

    private void toFavPage(){
        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            CommonUtils.loadinglogin(getMContext());
        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            IntentUtils.getInstence().intent(getMContext(), FavActivity.class);
        }
    }

    /**
     *
     */
    private void toOrderPage(){

        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            CommonUtils.loadinglogin(getMContext());
        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            MyToast.getInstance(getMContext()).show("暂时订单",Toast.LENGTH_SHORT);
            //IntentUtils.getInstence().intent(getMContext(), DownloadActivity.class);
        }
    }

    private void toUploadPage(){
        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            CommonUtils.loadinglogin(getMContext());
        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            Bundle bundle = new Bundle();
            bundle.putString("userId",userEntity.getUser_id());
            IntentUtils.getInstence().intent(getMContext(), QAHisActivity.class,bundle);
        }
    }
    private void toBuyPage(){
        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            CommonUtils.loadinglogin(getMContext());
        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            //TODO 跳转订阅的课程页面
            MyToast.getInstance(getMContext()).show("暂时订阅",Toast.LENGTH_SHORT);
            //IntentUtils.getInstence().intent(getMContext(), HistoryActivity.class);
        }
    }
    private void toWalletPage(){
        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            CommonUtils.loadinglogin(getMContext());
        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            IntentUtils.getInstence().intent(getMContext(), WalletActivity.class);
        }
    }
    private void toNoticePage(){
        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            CommonUtils.loadinglogin(getMContext());
        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            IntentUtils.getInstence().intent(getMContext(), NoticeActivity.class);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //aboutPresenter.onDestroy();
        baseApplication = null;
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(getMContext()).show(msg,Toast.LENGTH_SHORT);
    }


}
