package com.leothon.cogito.Mvp.View.Fragment.AskPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.AskActivity.AskActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.TeacherActivity.TeacherActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
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
 * 问答页面的fragment
 */
public class AskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,AskFragmentContract.IAskView{



    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar askBar;
    @BindView(R.id.swp_ask)
    SwipeRefreshLayout swpAsk;
    @BindView(R.id.rv_ask)
    RecyclerView rvAsk;

    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;
    @BindView(R.id.float_btn)
    FloatingActionButton floatBtn;

    private AskAdapter askAdapter;
    private ArrayList<QAData> asks;

    private AskPresenter askPresenter;

    private static int THRESHOLD_OFFSET = 10;
    private HostActivity hostActivity;
    private Animation viewShowAnim;
    private Animation viewHideAnim;
    private BaseApplication baseApplication;

    private boolean isLogin;
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
        EventBus.getDefault().register(this);
        askPresenter = new AskPresenter(this);
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

        title.setText("互动论坛");
        subtitle.setText("");
        asks = new ArrayList<>();
        askPresenter.getAskData(fragmentsharedPreferencesUtils.getParams("token","").toString());
        swpAsk.setRefreshing(true);
        //initAdapter();
        hostActivity = (HostActivity)getActivity();
        viewShowAnim = AnimationUtils.loadAnimation(getMContext(),R.anim.view_scale_show);
        viewHideAnim = AnimationUtils.loadAnimation(getMContext(),R.anim.view_scale_hide);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            //Fragment隐藏时调用
            return;
        }else {
            //Fragment显示时调用
//            askPresenter.getAskData(fragmentsharedPreferencesUtils.getParams("token","").toString());
//            swpAsk.setRefreshing(true);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(String msg){
        askPresenter.getAskData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }
    @Override
    public void loadAskData(ArrayList<QAData> qaData) {

        if (swpAsk.isRefreshing()){
            swpAsk.setRefreshing(false);
        }
        asks = qaData;
        initAdapter();
    }

    @Override
    public void loadAskMoreData(ArrayList<QAData> qaData) {
        for (int i = 0;i < qaData.size(); i++){
            asks.add(qaData.get(i));
            askAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void showInfo(String msg) {
        CommonUtils.makeText(getMContext(),msg);
    }
    public void initAdapter(){
        swpAsk.setOnRefreshListener(this);
        if (baseApplication.getLoginStatus() == 1){
            isLogin = true;
        }else {
            isLogin = false;
        }
        askAdapter = new AskAdapter(getMContext(),asks,isLogin);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false);
        rvAsk.setLayoutManager(mlinearLayoutManager);
        rvAsk.setAdapter(askAdapter);
        rvAsk.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean controlVisible = true;
            int scrollDistance = 0;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = mlinearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = mlinearLayoutManager.findFirstVisibleItemPosition();
                if (GSYVideoManager.instance().getPlayPosition() >= 0){
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if (GSYVideoManager.instance().getPlayTag().equals(AskAdapter.TAG) && (position < firstVisibleItem || position > lastVisibleItem)){
                        if (GSYVideoManager.isFullState(getActivity())){
                            return;
                        }

                        GSYVideoManager.releaseAllVideos();
                        askAdapter.notifyDataSetChanged();
                    }
                }

                if (controlVisible && scrollDistance > THRESHOLD_OFFSET){//手指上滑即Scroll向下滚动的时候，dy为正
                    animationHide();
                    controlVisible = false;
                    scrollDistance = 0;
                }else if (!controlVisible && scrollDistance < -THRESHOLD_OFFSET){//手指下滑即Scroll向上滚动的时候，dy为负
                    animationShow();
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

        rvAsk.addOnScrollListener(new loadMoreDataListener(mlinearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                askPresenter.getAskMoreData(currentPage * 15,fragmentsharedPreferencesUtils.getParams("token","").toString());
            }
        });
    }

    private void animationHide(){
        hostActivity.hideBottomBtn();
        floatBtn.hide();
        floatBtn.startAnimation(viewHideAnim);

    }

    private void animationShow(){
        hostActivity.showBottomBtn();
        floatBtn.show();
        floatBtn.startAnimation(viewShowAnim);

    }
    @OnClick(R.id.float_btn)
    public void addcontent(View view){

        if (baseApplication.getLoginStatus() == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (baseApplication.getLoginStatus() == 1){
            IntentUtils.getInstence().intent(getMContext(), AskActivity.class);
        }


    }






    @Override
    public void onRefresh() {

        askPresenter.getAskData(fragmentsharedPreferencesUtils.getParams("token","").toString());
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
        askPresenter.onDestroy();

        baseApplication = null;
    }


}
