package com.leothon.cogito.Mvp.View.Fragment.ArticleListPage;

import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leothon.cogito.Adapter.ArticleAdapter;
import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.DTO.ArticleData;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Listener.loadMoreDataArticleListener;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.VSureActivity.VSureActivity;
import com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity.WriteArticleActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.Banner;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.leothon.cogito.Base.BaseApplication.getApplication;

/**
 * created by leothon on 2018.7.29
 * 专栏页fragment
 */
public class ArticleListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,ArticleListContract.IArticleListView {






    @BindView(R.id.toolbar)
    Toolbar articleBar;

    @BindView(R.id.article_rv)
    RecyclerView articleRv;

    @BindView(R.id.swp_article)
    SwipeRefreshLayout swpArticle;

//    @BindView(R.id.float_btn_article)
//    FloatingActionButton writeArticle;

    private ArticleData articleData;

    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;


    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2;

    private ArticleAdapter articleAdapter;

    private Animation showAnimation;
    private Animation hideAnimation;

    private BaseApplication baseApplication;
    private ArticleListPresenter articleListPresenter;

    private static int THRESHOLD_OFFSET = 10;
    private ArrayList<Article> articles;


    private UserEntity userEntity;


    private HostActivity hostActivity;
    public ArticleListFragment() { }

    /**
     * fragment的构造方法
     * @return
     */
    public static ArticleListFragment newInstance() {
        ArticleListFragment fragment = new ArticleListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_article_list;
    }



    @Override
    protected void initData() {
        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }
        swpArticle.setProgressViewOffset (false,100,300);
        swpArticle.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        articleListPresenter = new ArticleListPresenter(this);
        ViewGroup.LayoutParams layoutParams = articleBar.getLayoutParams();
        layoutParams.height = CommonUtils.getStatusBarHeight(getMContext()) + CommonUtils.dip2px(getMContext(),45);
        articleBar.setLayoutParams(layoutParams);
        articleBar.setPadding(0,CommonUtils.getStatusBarHeight(getMContext()),0,0);
        if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)) {
            userEntity = baseApplication.getDaoSession().queryRaw(UserEntity.class, "where user_id = ?", tokenUtils.ValidToken(fragmentsharedPreferencesUtils.getParams("token", "").toString()).getUid()).get(0);
        }
    }

    @Override
    protected void initView() {
        title.setText("艺条");
        subtitle.setText("");
        hostActivity = (HostActivity)getActivity();

        showAnimation = AnimationUtils.loadAnimation(getMContext(),R.anim.top_view_in);
        hideAnimation = AnimationUtils.loadAnimation(getMContext(),R.anim.top_view_out);
        articleListPresenter.loadArticleData(fragmentsharedPreferencesUtils.getParams("token","").toString());
        swpArticle.setRefreshing(true);
    }

    private void initAdapter(){


        swpArticle.setOnRefreshListener(this);
        articleAdapter = new ArticleAdapter(articleData,getMContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getMContext(),2,LinearLayout.VERTICAL,false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                if(position >= 1){
                    return 1;
                }else{
                    return 2;
                }
            }
        });

        articleRv.setLayoutManager(gridLayoutManager);
        articleRv.setAdapter(articleAdapter);

        articleRv.addOnScrollListener(new loadMoreDataArticleListener(gridLayoutManager) {
            @Override
            public void onLoadMoreArticleData(int currentPage) {
                swpArticle.setRefreshing(true);
                articleListPresenter.loadArticleData(fragmentsharedPreferencesUtils.getParams("token","").toString(),currentPage * 15);
            }
        });
        articleRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

            /**
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (gridLayoutManager != null){

                    int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                    int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

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

                    if (firstVisibleItem > 0) {

                        title.setText("文章列表");
                    }else {
                        title.setText("艺条");
                    }
                }
            }
        });
    }
//
//    private void animationHide(){
//        //hostActivity.hideBottomBtn();
//        writeArticle.hide();
//        writeArticle.startAnimation(hideAnimation);
//
//    }
//
//    private void animationShow(){
//        //hostActivity.showBottomBtn();
//        writeArticle.show();
//        writeArticle.startAnimation(showAnimation);
//
//    }

    @Override
    public void loadArticlePageData(final ArticleData articleData) {
        this.articleData = articleData;
        if (swpArticle.isRefreshing()){
            swpArticle.setRefreshing(false);
        }
        articles = articleData.getArticles();
        if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            this.articleData.setUser_icon(userEntity.getUser_icon());
        }else {
            this.articleData.setUser_icon("");
        }

        initAdapter();

    }

//    @OnClick(R.id.float_btn_article)
//    public void writeArticle(View view){
//        if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
//            if (CommonUtils.isVIP(userEntity.getUser_role()) == 1){
//                toWriteArticle();
//            }else {
//                dialogLoading();
//            }
//        }else {
//            CommonUtils.loadinglogin(getContext());
//        }
//
//
//    }

//    private  void dialogLoading(){
//        final CommonDialog dialog = new CommonDialog(getContext());
//
//
//        dialog.setMessage("您未认证讲师，暂不可发表文章")
//                .setTitle("提示")
//                .setSingle(false)
//                .setNegtive("取消")
//                .setPositive("去认证")
//                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
//                    @Override
//                    public void onPositiveClick() {
//                        dialog.dismiss();
//                        IntentUtils.getInstence().intent(getContext(), VSureActivity.class);
//                    }
//
//                    @Override
//                    public void onNegativeClick() {
//                        dialog.dismiss();
//                    }
//
//                })
//                .show();
//
//    }

    @Override
    public void loadMoreArticlePageData(ArrayList<Article> articles) {
        if (swpArticle.isRefreshing()){
            swpArticle.setRefreshing(false);
        }
        for (int i = 0;i < articles.size();i ++){
            this.articles.add(articles.get(i));
        }
        articleAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Article article) {
        articleListPresenter.loadArticleData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }


    private void toWriteArticle(){
        if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            IntentUtils.getInstence().intent(getMContext(),WriteArticleActivity.class);
        }else {
            CommonUtils.loadinglogin(getMContext());
        }

    }
    @Override
    public void onRefresh() {
        articleListPresenter.loadArticleData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }




    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(getMContext()).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        baseApplication = null;
        articleListPresenter.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
