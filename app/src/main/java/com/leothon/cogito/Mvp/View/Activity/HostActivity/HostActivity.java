package com.leothon.cogito.Mvp.View.Activity.HostActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.DTO.Update;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Message.NoticeMessage;
import com.leothon.cogito.Message.UpdateMessage;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.AskActivity.AskActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadClassActivity.UploadClassActivity;
import com.leothon.cogito.Mvp.View.Activity.VSureActivity.VSureActivity;
import com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity.WriteArticleActivity;
import com.leothon.cogito.Mvp.View.Fragment.AboutPage.AboutFragment;
import com.leothon.cogito.Mvp.View.Fragment.AskPage.AskFragment;
import com.leothon.cogito.Mvp.View.Fragment.HomePage.HomeFragment;
import com.leothon.cogito.Mvp.View.Fragment.ArticleListPage.ArticleListFragment;
import com.leothon.cogito.R;

import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.ImageUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.BottomButton;
import com.leothon.cogito.Weight.CommonDialog;
import com.leothon.cogito.Weight.UpdateDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.blur_view)
    FrameLayout BlurView;

    @BindView(R.id.bottom_btn_home)
    BottomButton bottombtnHome;
    @BindView(R.id.bottom_btn_article)
    BottomButton bottombtnArticle;
    @BindView(R.id.bottom_btn_ask)
    BottomButton bottombtnAsk;
//    @BindView(R.id.bottom_btn_add)
//    BottomButton bottombtnAdd;


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

    private HomeFragment homePage;
    private ArticleListFragment articlePage;
    private AskFragment askPage;
    //private BagFragment bagPage;
    private AboutFragment aboutPage;

    private static final String HOMEPAGE = "homePage";
    private static final String ARTICLEPAGE = "articlePage";
    private static final String ASKPAGE = "askPage";
    //private static final String ADD = "add";
    //private static final String BAGPAGE = "bagPage";
    private static final String ABOUTPAGE = "aboutPage";

    private FragmentTransaction transaction;

    private Intent intent;
    private Bundle bundle;

    private Animation mShowAction;
    private Animation mHiddenAction;

    private long exitTime = 0;
    private String info;
    private UserEntity userEntity;

//    private DownloadService.DownloadBinder downloadBinder;
//
//    private ServiceConnection connection = new ServiceConnection() {
//
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.e(TAG, "执行了");
//            downloadBinder = (DownloadService.DownloadBinder) service;
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//    };



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
        disableBack();
        StatusBarUtils.transparencyBar(this);
        showAddDialog();
        mShowAction = AnimationUtils.loadAnimation(this, R.anim.view_in);
        mHiddenAction = AnimationUtils.loadAnimation(this, R.anim.view_out);
        intent = getIntent();
        bundle = intent.getExtras();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

//        BGAUpgradeUtil.getDownloadProgressEventObservable()
//                .compose(this.<BGADownloadProgressEvent>bindToLifecycle())
//                .subscribe(new Action1<BGADownloadProgressEvent>() {
//                    @Override
//                    public void call(BGADownloadProgressEvent downloadProgressEvent) {
//                        if (mDownloadingDialog != null && mDownloadingDialog.isShowing() && downloadProgressEvent.isNotDownloadFinished()) {
//                            mDownloadingDialog.setProgress(downloadProgressEvent.getProgress(), downloadProgressEvent.getTotal());
//                        }
//                    }
//                });


//        Intent intentService = new Intent(this, DownloadService.class);
//        startService(intentService);
//        bindService(intentService, connection, BIND_AUTO_CREATE);



//        homePage = HomeFragment.newInstance();
//        articlePage = ArticleListFragment.newInstance();
//        askPage = AskFragment.newInstance();
//        bagPage = BagFragment.newInstance();
        //aboutPage = AboutFragment.newInstance();

        switch (bundle.getString("type")){
            case "home":
                focusOnHome();
                switchFragment(HOMEPAGE);
                break;
            case "article":
                focusOnMic();
                switchFragment(ARTICLEPAGE);
                break;
            case "ask":
                focusOnAsk();
                switchFragment(ASKPAGE);
                break;
//            case "bag":
//                focusOnBag();
//                switchFragment(BAGPAGE);
//                break;
            case "about":
                focusOnAbout();
                switchFragment(ABOUTPAGE);
                break;
            default:
                focusOnHome();
                switchFragment(HOMEPAGE);
                break;
        }
        //dialogLoading("1","2");

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getUpdate(activitysharedPreferencesUtils.getParams("token","").toString())
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
                        Update update = (Update)baseResponse.getData();
                        String updateVersion = update.getUpdateVersion();
                        int updateCode = update.getUpdateCode();

                        if (updateCode > CommonUtils.getVersionCode(HostActivity.this)){
                            dialogLoading(CommonUtils.getVerName(HostActivity.this),updateVersion,update.getUpdateContent());
                            UpdateMessage updateMessage = new UpdateMessage();
                            updateMessage.setMessage("show");
                            EventBus.getDefault().post(updateMessage);
                        }

                    }
                });
    }



    /**
     * 初始化底部按钮
     */
    public void initBottomButton(){
        bottombtnHome.setTvAndIv("首页",R.drawable.homeicon);
        bottombtnArticle.setTvAndIv("艺条",R.drawable.articleicon);
        bottombtnAsk.setTvAndIv("秀吧",R.drawable.videoicon);
//        bottombtnAdd.setTvAndIv("",R.drawable.add);

        bottombtnAbout.setTvAndIv("我的",R.drawable.individualicon);
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

    private void disableBack(){
        ParallaxHelper.getInstance().disableParallaxBack(this);
    }

    @Override
    public void initData() {

        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();

        if ((boolean)activitysharedPreferencesUtils.getParams("login",false)){
            userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        }else {
            userEntity = new UserEntity();
        }

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


    /**
     * @param oldVersion
     * @param newVersion
     */
    private  void dialogLoading(String oldVersion,String newVersion,String updateContent){
        final UpdateDialog dialog = new UpdateDialog(this);


        dialog.setMessage("当前版本：V" + oldVersion + "\n更新版本：V" + newVersion + "\n更新内容:\n" + updateContent)
                .setOnClickBottomListener(new UpdateDialog.OnClickBottomListener() {
                    @Override
                    public void onMarketClick() {
                        dialog.dismiss();
                        CommonUtils.toMarket(HostActivity.this);
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

    public String getBotStatus(){
        return info;
    }

//    private BottomSheetDialog addDialog;
//    private BottomSheetBehavior addDialogBehavior;


    private View view;
    private PopupWindow popupWindow;
    private RoundedImageView addIcon;
    private TextView addInfo;
    private FrameLayout addClass;
    private FrameLayout addArticle;
    private FrameLayout addQa;
    private FrameLayout addLive;
    private ImageView closeView;


    private void showAddDialog() {

        view =  LayoutInflater.from(this).inflate(R.layout.addpage, null,false);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        view.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        closeView = (ImageView)view.findViewById(R.id.close_add);
        addIcon = (RoundedImageView)view.findViewById(R.id.add_icon);
        addInfo = (TextView)view.findViewById(R.id.add_info);
        addClass = (FrameLayout)view.findViewById(R.id.class_btn);
        addArticle = (FrameLayout)view.findViewById(R.id.article_btn);
        addQa = (FrameLayout)view.findViewById(R.id.qa_btn);
        addLive = (FrameLayout)view.findViewById(R.id.live_btn);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    BlurView.setForeground(null);
                    BlurView.setBackground(null);
                    return true;
                }
                return false;
            }
        });

//        addDialog = new BottomSheetDialog(this, R.style.dialog);
//        addDialog.setContentView(view);
//        addDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
//        addDialogBehavior.setPeekHeight(getWindowHeight());
//        addDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    popupWindow.dismiss();
//                    BlurView.setBackground(null);
//                    addDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//            }
//        });

        addLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.getInstance(HostActivity.this).show("直播功能暂缓开通，敬请期待",Toast.LENGTH_SHORT);
            }
        });
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((boolean)activitysharedPreferencesUtils.getParams("login",false)) {


                    if (userEntity.getUser_role().substring(0, 1).equals("1")) {


                        createDialog();

                    } else {
                        MyToast.getInstance(HostActivity.this).show("您尚未认证讲师，请先认证。\n本平台只有认证成为讲师方可制作上传课程", Toast.LENGTH_LONG);
                    }
                }else {
                    CommonUtils.loadinglogin(HostActivity.this);
                }
                popupWindow.dismiss();
                BlurView.setBackground(null);
                BlurView.setForeground(null);
            }
        });

        addArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((boolean)activitysharedPreferencesUtils.getParams("login",false)){
                    if (CommonUtils.isVIP(userEntity.getUser_role()) == 1){
                        toWriteArticle();
                    }else {
                        dialogLoading();
                    }
                }else {
                    CommonUtils.loadinglogin(HostActivity.this);
                }
                popupWindow.dismiss();
                BlurView.setBackground(null);
                BlurView.setForeground(null);
            }
        });

        addQa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(boolean)activitysharedPreferencesUtils.getParams("login",false)){
                    CommonUtils.loadinglogin(HostActivity.this);
                }else if ((boolean)activitysharedPreferencesUtils.getParams("login",false)){
                    Bundle bundle = new Bundle();
                    bundle.putString("type","write");
                    IntentUtils.getInstence().intent(HostActivity.this, AskActivity.class,bundle);
                }
                popupWindow.dismiss();
                BlurView.setBackground(null);
                BlurView.setForeground(null);
            }
        });
        ImageLoader.loadImageViewThumbnailwitherror(this,userEntity.getUser_icon(),addIcon,R.drawable.defaulticon);
        if (userEntity.getUser_register_time() == null){
            addInfo.setText("请重新登录以获取您加入艺派的时间");
        }else {
            addInfo.setText("今天是您加入艺派的第" + CommonUtils.compareDays(CommonUtils.getNowTime(),userEntity.getUser_register_time()) + "天，祝您学习愉快");
        }

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                BlurView.setBackground(null);
                BlurView.setForeground(null);
            }
        });

//        Window window = addDialog.getWindow();
//        window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void showPopWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_host,null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }


    private void toWriteArticle(){
        if ((boolean)activitysharedPreferencesUtils.getParams("login",false)){
            IntentUtils.getInstence().intent(HostActivity.this, WriteArticleActivity.class);
        }else {
            CommonUtils.loadinglogin(this);
        }

    }
//    private int getWindowHeight() {
//        Resources res = this.getResources();
//        DisplayMetrics displayMetrics = res.getDisplayMetrics();
//        return displayMetrics.heightPixels;
//    }


    private  void dialogLoading(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("您未认证讲师，暂不可发表文章")
                .setTitle("提示")
                .setSingle(false)
                .setNegtive("取消")
                .setPositive("去认证")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        IntentUtils.getInstence().intent(HostActivity.this, VSureActivity.class);
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                    }

                })
                .show();

    }




    private void createDialog(){
        final CommonDialog dialog = new CommonDialog(this);



        dialog.setMessage("该选择会创建新的课程\n若已成功创建，请在个人主页上传内容或者编辑")
                .setTitle("提示")
                .setSingle(false)
                .setNegtive("取消")
                .setPositive("我知道，继续创建")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        Bundle bundleto = new Bundle();
                        bundleto.putString("type","create");
                        IntentUtils.getInstence().intent(HostActivity.this, UploadClassActivity.class,bundleto);

                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })

                .show();
    }
    /**
     * 切换相应的fragment
     * @param pageName
     */
    public void switchFragment(String pageName){
        transaction = getSupportFragmentManager().beginTransaction();
        HideAllFragment(transaction);
        switch (pageName){
            case HOMEPAGE:
                StatusBarUtils.transparencyBar(this);
                if (homePage == null){
                    homePage = HomeFragment.newInstance();
                    transaction.add(R.id.container_home,homePage);
                }else{
                    transaction.show(homePage);

                }
                break;
            case ARTICLEPAGE:
                if (articlePage == null){
                    articlePage = ArticleListFragment.newInstance();
                    transaction.add(R.id.container_home,articlePage);
                }else{
                    transaction.show(articlePage);
                }
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                StatusBarUtils.setStatusBarColor(this,R.color.white);
                break;
            case ASKPAGE:
                if (askPage == null){
                    askPage = AskFragment.newInstance();
                    transaction.add(R.id.container_home,askPage);
                }else{
                    transaction.show(askPage);
                }
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                StatusBarUtils.setStatusBarColor(this,R.color.white);
                break;
//            case ADD:
//                //TODO 打开四个接口
//
////                if (bagPage == null){
////                    bagPage = BagFragment.newInstance();
////                    transaction.add(R.id.container_home,bagPage);
////                }else{
////                    transaction.show(bagPage);
////                }
////                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
////                StatusBarUtils.setStatusBarColor(this,R.color.white);
//                break;
            case ABOUTPAGE:
                StatusBarUtils.transparencyBar(this);
                if (aboutPage == null){
                    aboutPage = AboutFragment.newInstance();
                    transaction.add(R.id.container_home,aboutPage);
                }else{
                    transaction.show(aboutPage);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有的fragment
     * @param transaction
     */
    public void HideAllFragment(FragmentTransaction transaction){
        if (homePage != null){
            transaction.hide(homePage);
        }
        if (articlePage != null){
            transaction.hide(articlePage);
        }
        if (askPage != null){
            transaction.hide(askPage);
        }
//        if (bagPage != null){
//            transaction.hide(bagPage);
//        }
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


    @OnClick(R.id.bottom_btn_article)
    public void showType(View v){
        //跳转文章
        focusOnMic();
        switchFragment(ARTICLEPAGE);
    }

    @OnClick(R.id.bottom_btn_ask)
    public void showAsk(View v){
        //跳转问答
        focusOnAsk();
        switchFragment(ASKPAGE);
    }


    @OnClick(R.id.bottom_btn_add)
    public void addContent(View v){


        BlurView.setForeground(new ColorDrawable(Color.parseColor("#90FFFFFF")));

        BlurView.setBackground(new BitmapDrawable(getResources(),ImageUtils.blur(this,ImageUtils.saveScreenAsImage(this,true))));
//        addDialog.show();
        showPopWindow();
    }
//    @OnClick(R.id.bottom_btn_bag)
//    public void showBag(View v){
//        //跳转小书包
//        focusOnBag();
//        switchFragment(BAGPAGE);
//    }

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
        bottombtnArticle.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnArticle.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnMicClass.resetButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAsk.resetButton();
//        bottombtnBag.setTvColor(getResources().getColor(R.color.fontColor));
//        bottombtnBag.setIvColor(getResources().getColor(R.color.fontColor));
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
        bottombtnArticle.setTvColor(getResources().getColor(R.color.colorPrimary));
        bottombtnArticle.setIvColor(getResources().getColor(R.color.colorPrimary));
        //bottombtnMicClass.focusOnButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAsk.resetButton();
//        bottombtnBag.setTvColor(getResources().getColor(R.color.fontColor));
//        bottombtnBag.setIvColor(getResources().getColor(R.color.fontColor));
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
        bottombtnArticle.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnArticle.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnMicClass.resetButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.colorPrimary));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.colorPrimary));
        //bottombtnAsk.focusOnButton();
//        bottombtnBag.setTvColor(getResources().getColor(R.color.fontColor));
//        bottombtnBag.setIvColor(getResources().getColor(R.color.fontColor));
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
        bottombtnArticle.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnArticle.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnMicClass.resetButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAsk.resetButton();
//        bottombtnBag.setTvColor(getResources().getColor(R.color.colorPrimary));
//        bottombtnBag.setIvColor(getResources().getColor(R.color.colorPrimary));
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
        bottombtnArticle.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnArticle.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnMicClass.resetButton();
        bottombtnAsk.setTvColor(getResources().getColor(R.color.fontColor));
        bottombtnAsk.setIvColor(getResources().getColor(R.color.fontColor));
        //bottombtnAsk.resetButton();
//        bottombtnBag.setTvColor(getResources().getColor(R.color.fontColor));
//        bottombtnBag.setIvColor(getResources().getColor(R.color.fontColor));
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

        if (!popupWindow.isShowing()) {


            if (GSYVideoManager.isFullState(this)) {
                GSYVideoManager.backFromWindowFull(this);
                //GSYVideoManager.instance().setNeedMute(true);
            } else {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    // 判断间隔时间 大于2秒就退出应用
                    if ((System.currentTimeMillis() - exitTime) > 2000) {

                        MyToast.getInstance(this).show("再按一次退出艺派", Toast.LENGTH_SHORT);
                        exitTime = System.currentTimeMillis();
                    } else {
                        GSYVideoManager.releaseAllVideos();
                        Intent home = new Intent(Intent.ACTION_MAIN);
                        home.addCategory(Intent.CATEGORY_HOME);
                        startActivity(home);
                    }
                    return true;
                }
                return super.onKeyDown(keyCode, event);
            }

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UpdateMessage updateMessage) {

        if (updateMessage.getMessage().equals("show")){
            noticeBot.setVisibility(View.VISIBLE);
        }else {
            noticeBot.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause: ");
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
