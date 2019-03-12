package com.leothon.cogito.Mvp.View.Activity.HostActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Message.NoticeMessage;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Fragment.AboutPage.AboutFragment;
import com.leothon.cogito.Mvp.View.Fragment.AskPage.AskFragment;
import com.leothon.cogito.Mvp.View.Fragment.BagPage.BagFragment;
import com.leothon.cogito.Mvp.View.Fragment.HomePage.HomeFragment;
import com.leothon.cogito.Mvp.View.Fragment.ArticleListPage.ArticleListFragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.BottomButton;
import com.shuyu.gsyvideoplayer.GSYVideoManager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.7.24
 * 主页，主页只显示底部栏，使用fragmentmanager进行四个页面的切换
 */
public class HostActivity extends BaseActivity  {

    @BindView(R.id.container_all)
    RelativeLayout containerAll;

    @BindView(R.id.bottom_btn_home)
    BottomButton bottombtnHome;
    @BindView(R.id.bottom_btn_mic_class)
    BottomButton bottombtnMicClass;
    @BindView(R.id.bottom_btn_ask)
    BottomButton bottombtnAsk;
    @BindView(R.id.bottom_btn_bag)
    BottomButton bottombtnBag;
    @BindView(R.id.bottom_btn_about)
    BottomButton bottombtnAbout;


    @BindView(R.id.container_home)
    FrameLayout container;
    @BindView(R.id.host_bottom)
    CardView hostBottom;

    @BindView(R.id.notice_bot_host)
    View noticeBot;

    @BindView(R.id.bar_host)
    CardView barHost;

    Fragment homePage = HomeFragment.newInstance();
    Fragment micClassPage = ArticleListFragment.newInstance();
    Fragment askPage = AskFragment.newInstance();
    Fragment bagPage = BagFragment.newInstance();
    Fragment aboutPage = AboutFragment.newInstance();

    private static final String HOMEPAGE = "homePage";
    private static final String MICCLASSPAGE = "micClassPage";
    private static final String ASKPAGE = "askPage";
    private static final String BAGPAGE = "bagPage";
    private static final String ABOUTPAGE = "aboutPage";

    //private FragmentTransaction transaction;

    private Intent intent;
    private Bundle bundle;

    private Animation mShowAction;
    private Animation mHiddenAction;

    private long exitTime = 0;
    private String info;


    @Override
    public int initLayout() {
        return R.layout.activity_host;
    }

    @Override
    public void initView() {
        if (CommonUtils.netIsConnected(this) && CommonUtils.getNetworkType(this) != 1){
            MyToast.getInstance(this).show("注意，你现在使用的是4G网络。",Toast.LENGTH_SHORT);
        }else if (!CommonUtils.netIsConnected(this)){
            MyToast.getInstance(this).show("无网络连接",Toast.LENGTH_SHORT);
        }
        initBottomButton();
        StatusBarUtils.transparencyBar(this);
        mShowAction = AnimationUtils.loadAnimation(this, R.anim.view_in);
        mHiddenAction = AnimationUtils.loadAnimation(this, R.anim.view_out);
        intent = getIntent();
        bundle = intent.getExtras();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        switch (bundle.getString("type")){
            case "home":
                focusOnHome();
                switchFragment(HOMEPAGE);
                break;
            case "voice":
                focusOnMic();
                switchFragment(MICCLASSPAGE);
                break;
            case "ask":
                focusOnAsk();
                switchFragment(ASKPAGE);
                break;
            case "bag":
                focusOnBag();
                switchFragment(BAGPAGE);
                break;
            case "about":
                focusOnAbout();
                switchFragment(ABOUTPAGE);
                break;
            default:
                focusOnHome();
                switchFragment(HOMEPAGE);
                break;
        }
    }



    /**
     * 初始化底部按钮
     */
    public void initBottomButton(){
        bottombtnHome.setTvAndIv("首页",R.drawable.baseline_music_note_black_24);
        bottombtnMicClass.setTvAndIv("艺条",R.drawable.baseline_queue_music_black_24);
        bottombtnAsk.setTvAndIv("互动",R.drawable.baseline_question_answer_black_24);
        bottombtnBag.setTvAndIv("小书包",R.drawable.baseline_class_black_24);
        bottombtnAbout.setTvAndIv("我的",R.drawable.baseline_perm_identity_black_24);
        focusOnHome();
    }




    public void hideBottomBtn(){
        hostBottom.setVisibility(View.GONE);
        hostBottom.startAnimation(mHiddenAction);

    }

    public void showBottomBtn(){
        hostBottom.setVisibility(View.VISIBLE);
        hostBottom.startAnimation(mShowAction);

    }

    @Override
    public void initData() {

//        homePage = HomeFragment.newInstance();
//        micClassPage = ArticleListFragment.newInstance();
//        askPage = AskFragment.newInstance();
//        bagPage = BagFragment.newInstance();
//        aboutPage = AboutFragment.newInstance();
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .isHasNotice(activitysharedPreferencesUtils.getParams("token","").toString())
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        MyToast.getInstance(HostActivity.this).show(errorMsg,Toast.LENGTH_SHORT);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        info = baseResponse.getMsg();
                        if (info.equals("notice")){
                            NoticeMessage noticeMessage = new NoticeMessage();
                            noticeMessage.setMessage("show");
                            EventBus.getDefault().post(noticeMessage);
                        }else{
                            noticeBot.setVisibility(View.GONE);
                        }
                    }
                });
    }



    public String getBotStatus(){
        return info;
    }


    private Fragment currentFragment = new Fragment();

    private FragmentTransaction switchtoFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.container_home, targetFragment,targetFragment.getClass().getName());
        } else {
            transaction.hide(currentFragment).show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }


    /**
     * 切换相应的fragment
     * @param pageName
     */
    public void switchFragment(String pageName){

        //transaction = getSupportFragmentManager().beginTransaction();
        //HideAllFragment(transaction);
        switch (pageName){
            case HOMEPAGE:
                focusOnHome();
                StatusBarUtils.transparencyBar(this);
//                if (homePage == null){
//                    homePage = HomeFragment.newInstance();
//                    transaction.add(R.id.container_home,homePage,HOMEPAGE);
//                }else{
//                    transaction.show(homePage);
//
//                }
                switchtoFragment(homePage).commit();
                break;
            case MICCLASSPAGE:
                focusOnMic();
//                if (micClassPage == null){
//                    micClassPage = ArticleListFragment.newInstance();
//                    transaction.add(R.id.container_home,micClassPage,MICCLASSPAGE);
//                }else{
//                    transaction.show(micClassPage);
//                }
                switchtoFragment(micClassPage).commit();
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                StatusBarUtils.setStatusBarColor(this,R.color.white);
                break;
            case ASKPAGE:
                focusOnAsk();
//                if (askPage == null){
//                    askPage = AskFragment.newInstance();
//                    transaction.add(R.id.container_home,askPage,ASKPAGE);
//                }else{
//                    transaction.show(askPage);
//                }
                switchtoFragment(askPage).commit();
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                StatusBarUtils.setStatusBarColor(this,R.color.white);
                break;
            case BAGPAGE:
                focusOnBag();
//                if (bagPage == null){
//                    bagPage = BagFragment.newInstance();
//                    transaction.add(R.id.container_home,bagPage,BAGPAGE);
//                }else{
//                    transaction.show(bagPage);
//                }
                switchtoFragment(bagPage).commit();
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                StatusBarUtils.setStatusBarColor(this,R.color.white);
                break;
            case ABOUTPAGE:
                focusOnAbout();
//                if (aboutPage == null){
//                    aboutPage = AboutFragment.newInstance();
//                    transaction.add(R.id.container_home,aboutPage,ABOUTPAGE);
//                }else{
//                    transaction.show(aboutPage);
//                }
                switchtoFragment(aboutPage).commit();
                StatusBarUtils.transparencyBar(this);
                break;
        }
        //transaction.commit();
    }

    /**
     * 隐藏所有的fragment
     * @param transaction
     */
    public void HideAllFragment(FragmentTransaction transaction){
        if (homePage != null){
            transaction.hide(homePage);
        }
        if (micClassPage != null){
            transaction.hide(micClassPage);
        }
        if (askPage != null){
            transaction.hide(askPage);
        }
        if (bagPage != null){
            transaction.hide(bagPage);
        }
        if (aboutPage != null){
            transaction.hide(aboutPage);
        }
    }
    @OnClick(R.id.bottom_btn_home)
    public void showHome(View v){
        //跳转主页
        focusOnHome();
        switchFragment(HOMEPAGE);
    }

    @OnClick(R.id.bottom_btn_mic_class)
    public void showType(View v){
        //跳转一条
        focusOnMic();
        switchFragment(MICCLASSPAGE);
    }

    @OnClick(R.id.bottom_btn_ask)
    public void showAsk(View v){
        //跳转问答
        focusOnAsk();
        switchFragment(ASKPAGE);
    }

    @OnClick(R.id.bottom_btn_bag)
    public void showBag(View v){
        //跳转小书包
        focusOnBag();
        switchFragment(BAGPAGE);
    }

    @OnClick(R.id.bottom_btn_about)
    public void showAbout(View v){
        //跳转我的
        focusOnAbout();
        switchFragment(ABOUTPAGE);
    }

    /**
     * 以下五个方法是用户点击所在的按钮，将底部色彩进行更新的操作
     */
    public void focusOnHome(){
        GSYVideoManager.releaseAllVideos();
        bottombtnHome.setTvColor(getResources().getColor(R.color.colorPrimary));
        bottombtnHome.setIvColor(getResources().getColor(R.color.colorPrimary));
        //bottombtnHome.focusOnButton();
        bottombtnMicClass.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnMicClass.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnMicClass.resetButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAsk.resetButton();
        bottombtnBag.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnBag.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnBag.resetButton();
        bottombtnAbout.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAbout.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAbout.resetButton();
    }

    public void focusOnMic(){
        GSYVideoManager.releaseAllVideos();
        bottombtnHome.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnHome.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnHome.resetButton();
        bottombtnMicClass.setTvColor(getResources().getColor(R.color.colorPrimary));
        bottombtnMicClass.setIvColor(getResources().getColor(R.color.colorPrimary));
        //bottombtnMicClass.focusOnButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAsk.resetButton();
        bottombtnBag.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnBag.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnBag.resetButton();
        bottombtnAbout.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAbout.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAbout.resetButton();
    }

    public void focusOnAsk(){
        GSYVideoManager.releaseAllVideos();
        bottombtnHome.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnHome.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnHome.resetButton();
        bottombtnMicClass.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnMicClass.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnMicClass.resetButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.colorPrimary));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.colorPrimary));
        //bottombtnAsk.focusOnButton();
        bottombtnBag.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnBag.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnBag.resetButton();
        bottombtnAbout.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAbout.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAbout.resetButton();
    }

    public void focusOnBag(){
        GSYVideoManager.releaseAllVideos();
        bottombtnHome.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnHome.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnHome.resetButton();
        bottombtnMicClass.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnMicClass.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnMicClass.resetButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAsk.resetButton();
        bottombtnBag.setTvColor(getResources().getColor(R.color.colorPrimary));
        bottombtnBag.setIvColor(getResources().getColor(R.color.colorPrimary));
        //bottombtnBag.focusOnButton();
        bottombtnAbout.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAbout.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAbout.resetButton();
    }

    public void focusOnAbout(){
        GSYVideoManager.releaseAllVideos();
        bottombtnHome.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnHome.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnHome.resetButton();
        bottombtnMicClass.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnMicClass.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnMicClass.resetButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAsk.resetButton();
        bottombtnBag.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnBag.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnBag.resetButton();
        bottombtnAbout.setTvColor(getResources().getColor(R.color.colorPrimary));
        bottombtnAbout.setIvColor(getResources().getColor(R.color.colorPrimary));
        //bottombtnAbout.focusOnButton();
    }


//    @Override
//    public void onBackPressed() {
//
//        if (GSYVideoManager.backFromWindowFull(this)) {
//            return;
//        }
//        removeAllActivity();
//
//    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (GSYVideoManager.isFullState(this)){
            GSYVideoManager.backFromWindowFull(this);
            GSYVideoManager.instance().setNeedMute(true);
        }else {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                // 判断间隔时间 大于2秒就退出应用
                if ((System.currentTimeMillis() - exitTime) > 2000) {

                    MyToast.getInstance(this).show("再按一次退出艺派",Toast.LENGTH_SHORT);
                    exitTime = System.currentTimeMillis();
                } else {
                    Intent home = new Intent(Intent.ACTION_MAIN);
                    home.addCategory(Intent.CATEGORY_HOME);
                    startActivity(home);
                }
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

        return true;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(NoticeMessage message) {
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,"artSettings");
        if (message.getMessage().equals("show") && (boolean)sharedPreferencesUtils.getParams("qaNotice",false)){
            noticeBot.setVisibility(View.VISIBLE);
        }else {
            noticeBot.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();

        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

}
