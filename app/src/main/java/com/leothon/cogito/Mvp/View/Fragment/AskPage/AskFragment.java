package com.leothon.cogito.Mvp.View.Fragment.AskPage;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.Mvp.View.Activity.AskActivity.AskActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.EPieVideoPlayer;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.wxapi.WXEntryActivity;
import com.shuyu.gsyvideoplayer.GSYVideoBaseManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


import static com.leothon.cogito.Base.BaseApplication.getApplication;

/**
 * created by leothon on 2018.7.29
 * 问答页面的fragment
 */
public class AskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,AskFragmentContract.IAskView{



    @BindView(R.id.toolbar)
    Toolbar askBar;
    @BindView(R.id.swp_ask)
    SwipeRefreshLayout swpAsk;
    @BindView(R.id.rv_ask)
    RecyclerView rvAsk;

    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;
//    @BindView(R.id.float_btn)
//    FloatingActionButton floatBtn;

    private AskAdapter askAdapter;
    private ArrayList<QAData> asks;

    private AskPresenter askPresenter;

    private static int THRESHOLD_OFFSET = 10;
    private HostActivity hostActivity;
    private Animation viewShowAnim;
    private Animation viewHideAnim;
    private BaseApplication baseApplication;

    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2;
    private boolean isLogin;

    private TextView deleteContent;
    private TextView copyContent;
    private RelativeLayout dismiss;
    private View popview;
    private PopupWindow popupWindow;
    //private QQShareListener qqShareListener;
    private String informText;
    String uuid;
    //private Tencent mTencent;

    private class QQShareListener implements IUiListener {
        @Override
        public void onCancel() {

        }

        @Override public void onError(UiError uiError) {
        }

        @Override public void onComplete(Object o) {

            MyToast.getInstance(getMContext()).show("分享成功！",Toast.LENGTH_SHORT);
        }
    }
    public AskFragment() {}

    /**
     * 构造方法
     * @return
     */
    public static AskFragment newInstance() {
        AskFragment fragment = new AskFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_ask;
    }

    @Override
    protected void initData() {
        //mTencent = Tencent.createInstance(Constants.APP_ID,getMContext().getApplicationContext());
        //qqShareListener = new QQShareListener();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        askPresenter = new AskPresenter(this);
        initMorePopupWindow();
        TokenValid tokenValid = tokenUtils.ValidToken(fragmentsharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();
        swpAsk.setProgressViewOffset (false,100,300);
        swpAsk.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }

    }
    @Override
    protected void initView() {
        ViewGroup.LayoutParams layoutParams = askBar.getLayoutParams();
        layoutParams.height = CommonUtils.getStatusBarHeight(getMContext()) + CommonUtils.dip2px(getMContext(),45);
        askBar.setLayoutParams(layoutParams);
        askBar.setPadding(0,CommonUtils.getStatusBarHeight(getMContext()),0,0);


        title.setText("秀吧视频");
        subtitle.setText("");
        asks = new ArrayList<>();
        askPresenter.getAskData(fragmentsharedPreferencesUtils.getParams("token","").toString());
        swpAsk.setRefreshing(true);
        hostActivity = (HostActivity)getActivity();
        viewShowAnim = AnimationUtils.loadAnimation(getMContext(),R.anim.view_scale_show);
        viewHideAnim = AnimationUtils.loadAnimation(getMContext(),R.anim.view_scale_hide);

        showShareDialog();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String msg){
        askPresenter.getAskData(fragmentsharedPreferencesUtils.getParams("token","").toString());
        MyToast.getInstance(getMContext()).show(msg,Toast.LENGTH_SHORT);
    }
    @Override
    public void loadAskData(ArrayList<QAData> qaData) {


        asks = qaData;

        askPresenter.getInform(fragmentsharedPreferencesUtils.getParams("token","").toString());

    }

    @Override
    public void loadAskMoreData(ArrayList<QAData> qaData) {
        if (swpAsk.isRefreshing()){
            swpAsk.setRefreshing(false);
        }
        for (int i = 0;i < qaData.size(); i++){
            asks.add(qaData.get(i));

        }
        askAdapter.notifyDataSetChanged();
    }

    public void initMorePopupWindow(){
        popview = LayoutInflater.from(getMContext()).inflate(R.layout.qa_more_pop,null,false);
        popupWindow = new PopupWindow(popview,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupQAWindow_anim_style);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dismiss = (RelativeLayout) popview.findViewById(R.id.miss_pop_more);
        deleteContent = (TextView)popview.findViewById(R.id.delete_content_this);
        copyContent = (TextView)popview.findViewById(R.id.copy_content_this);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });
    }
    public void showPopWindow(){
        View rootView = LayoutInflater.from(getMContext()).inflate(R.layout.fragment_ask,null);
        popupWindow.showAtLocation(rootView, Gravity.CENTER,0,0);

    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(getMContext()).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void deleteSuccess(String msg) {
        MyToast.getInstance(getMContext()).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void getInformSuccess(String text) {

        if (swpAsk.isRefreshing()){
            swpAsk.setRefreshing(false);
        }
        informText = text;

        initAdapter();
    }

    public void initAdapter(){
        swpAsk.setOnRefreshListener(this);
        if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            isLogin = true;
        }else {
            isLogin = false;
        }
        askAdapter = new AskAdapter(getMContext(),asks,informText,isLogin);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false);
        rvAsk.setLayoutManager(mlinearLayoutManager);
        rvAsk.setAdapter(askAdapter);
        rvAsk.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean controlVisible = true;
            int scrollDistance = 0;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                        //当屏幕停止滚动，加载图片
                        try {
                            if (getMContext() != null) Glide.with(getMContext()).resumeRequests();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                        //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                        try {
                            if (getMContext() != null) Glide.with(getMContext()).pauseRequests();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under outside control.
                        //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                        try {
                            if (getMContext() != null) Glide.with(getMContext()).pauseRequests();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = mlinearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = mlinearLayoutManager.findFirstVisibleItemPosition();
                if (GSYVideoManager.instance().getPlayPosition() >= 0){
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if ( position < firstVisibleItem || position > lastVisibleItem){
                        if (GSYVideoManager.isFullState(getActivity())){
                            return;
                        }
                        GSYVideoManager.releaseAllVideos();
                        askAdapter.notifyDataSetChanged();
                    }
                }

                if (controlVisible && scrollDistance > THRESHOLD_OFFSET){//手指上滑即Scroll向下滚动的时候，dy为正
                    //animationHide();
                    controlVisible = false;
                    scrollDistance = 0;
                }else if (!controlVisible && scrollDistance < -THRESHOLD_OFFSET){//手指下滑即Scroll向上滚动的时候，dy为负
                    //animationShow();
                    controlVisible = true;
                    scrollDistance = 0;
                }

                //当scrollDistance累计到隐藏（显示)ToolBar之后，如果Scroll向下（向上）滚动，则停止对scrollDistance的累加
                //直到Scroll开始往反方向滚动，再次启动scrollDistance的累加
                if ((controlVisible && dy > 0) || (!controlVisible && dy < 0)){
                    scrollDistance += dy;
                }

            }
        });
        askAdapter.setOnClickaddLike(new AskAdapter.addLikeOnClickListener() {
            @Override
            public void addLikeClickListener(boolean isLike,String qaId) {
                if (isLike){
                    askPresenter.removeLiked(fragmentsharedPreferencesUtils.getParams("token","").toString(),qaId);
                }else {
                    askPresenter.addLiked(fragmentsharedPreferencesUtils.getParams("token","").toString(),qaId);
                }
            }
        });

        askAdapter.setDeleteQaOnClickListener(new AskAdapter.DeleteQaOnClickListener() {
            @Override
            public void deleteQaClickListener(final String qaId, String qaUserId,final String content,final int position) {
                showPopWindow();
                if (qaUserId.equals(uuid)){
                    deleteContent.setVisibility(View.VISIBLE);
                }else {
                    deleteContent.setVisibility(View.GONE);
                }

                deleteContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askPresenter.deleteQa(fragmentsharedPreferencesUtils.getParams("token","").toString(),qaId);
                        asks.remove(position);
                        askAdapter.notifyItemRemoved(position);
                        //askAdapter.notifyItemChanged(position);
                        //askAdapter.notifyItemRangeChanged(position, asks.size());
                        //askAdapter.notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });

                copyContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getMContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        MyToast.getInstance(getMContext()).show("内容已复制",Toast.LENGTH_SHORT);
                        popupWindow.dismiss();
                    }
                });
            }
        });

        rvAsk.addOnScrollListener(new loadMoreDataListener(mlinearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpAsk.setRefreshing(true);
                askPresenter.getAskMoreData(currentPage * 15,fragmentsharedPreferencesUtils.getParams("token","").toString());
            }
        });

        askAdapter.setOnClickaddQaView(new AskAdapter.addQaViewOnClickListener() {
            @Override
            public void addQaViewClickListener(String qaId) {
                askPresenter.addView(fragmentsharedPreferencesUtils.getParams("token","").toString(),qaId);
            }
        });


        askAdapter.setOnShareListener(new AskAdapter.addShareOnClickListener() {
            @Override
            public void addShareClickListener(int position) {

                shareDialog.show();
                setShare(position);
            }
        });
    }

//    private void animationHide(){
//        hostActivity.hideBottomBtn();
//        floatBtn.hide();
//        floatBtn.startAnimation(viewHideAnim);
//
//    }
//
//    private void animationShow(){
//        hostActivity.showBottomBtn();
//        floatBtn.show();
//        floatBtn.startAnimation(viewShowAnim);
//
//    }
//    @OnClick(R.id.float_btn)
//    public void addcontent(View view){
//
//        if (!(boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
//            CommonUtils.loadinglogin(getMContext());
//        }else if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
//            Bundle bundle = new Bundle();
//            bundle.putString("type","write");
//            IntentUtils.getInstence().intent(getMContext(), AskActivity.class,bundle);
//        }
//
//
//    }


    private BottomSheetDialog shareDialog;
    private BottomSheetBehavior shareDialogBehavior;
    private SwipeRefreshLayout swpArticleComment;
    private RelativeLayout shareToQQ;
    private RelativeLayout shareToFriendCircle;
    private RelativeLayout shareToWeChat;
    private RelativeLayout shareToMore;




    private void showShareDialog() {

        View view = View.inflate(getMContext(), R.layout.popup_share, null);
        shareToQQ = (RelativeLayout)view.findViewById(R.id.share_class_to_qq);
        shareToFriendCircle = (RelativeLayout)view.findViewById(R.id.share_class_to_circle);
        shareToWeChat = (RelativeLayout)view.findViewById(R.id.share_class_to_wechat);
        shareToMore = (RelativeLayout)view.findViewById(R.id.share_class_to_more);
        shareDialog = new BottomSheetDialog(getMContext(), R.style.dialog);
        shareDialog.setContentView(view);
        shareDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        shareDialogBehavior.setPeekHeight(getWindowHeight());
        shareDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    shareDialog.dismiss();
                    shareDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });


        Window window = shareDialog.getWindow();
        window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
    }

    private void setShare(int position){
        shareToQQ.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                MyToast.getInstance(getMContext()).show("暂不支持QQ分享",Toast.LENGTH_SHORT);
                //shareToQQClass(asks.get(position));
                shareDialog.dismiss();

            }
        });
        shareToFriendCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (asks.get(position).getQa_video() == null){

                    MyToast.getInstance(getMContext()).show("非视频不能分享到朋友圈",Toast.LENGTH_SHORT);
                    return;
                }
                Bundle bundleto = new Bundle();
                bundleto.putString("share","1");
                bundleto.putSerializable("qa",asks.get(position));
                IntentUtils.getInstence().intent(getMContext(), WXEntryActivity.class,bundleto);
                shareDialog.dismiss();

            }
        });
        shareToWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleto = new Bundle();
                bundleto.putString("share","2");
                bundleto.putSerializable("qa",asks.get(position));
                IntentUtils.getInstence().intent(getMContext(), WXEntryActivity.class,bundleto);
                shareDialog.dismiss();

            }
        });
        shareToMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.getInstance(getMContext()).show("暂不支持分享更多",Toast.LENGTH_SHORT);
                //shareToMoreInfo(asks.get(position));
                shareDialog.dismiss();

            }
        });
    }

//    private void shareToQQClass(QAData qaData)
//    {
//        Bundle bundle = new Bundle();
//        //这条分享消息被好友点击后的跳转URL。
//        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "https://www.artepie.com/image/" + qaData.getArticleId() + ".html");
//        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
//        bundle.putString(QQShare.SHARE_TO_QQ_TITLE,article.getArticleTitle());
//        //分享的图片URL
//        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, article.getArticleImg());
//        //分享的消息摘要，最长50个字
//        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, article.getArticleAuthorName());
//        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
//        //bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "??我在测试");
//        //标识该消息的来源应用，值为应用名称+AppId。
//        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "艺派" + Constants.APP_ID);
//        mTencent.shareToQQ(this, bundle , qqShareListener);
//    }

//    private void shareToMoreInfo(QAData qaData) {
//        Intent share_intent = new Intent();
//        share_intent.setAction(Intent.ACTION_SEND);
//        share_intent.setType("text/plain");
//        share_intent.putExtra(Intent.EXTRA_SUBJECT, "艺派");
//        share_intent.putExtra(Intent.EXTRA_TEXT, "我分享了文章" + article.getArticleTitle() + "\n戳我查看：https://www.artepie.com/image/" + article.getArticleId() + ".html");
//        share_intent = Intent.createChooser(share_intent, "分享");
//        startActivity(share_intent);
//    }

    private int getWindowHeight() {
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
    @Override
    public void onRefresh() {

        askPresenter.getAskData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        askPresenter.onDestroy();

        baseApplication = null;
    }


}
