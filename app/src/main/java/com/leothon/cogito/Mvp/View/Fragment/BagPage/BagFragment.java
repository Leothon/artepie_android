package com.leothon.cogito.Mvp.View.Fragment.BagPage;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BagAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.BagBuy;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.BagPageData;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Message.UploadMessage;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.View.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

import static com.leothon.cogito.Base.BaseApplication.getApplication;

/**
 * created by leothon on 2018.7.29
 */
public class BagFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,BagContract.IBagView {

    @BindView(R.id.toolbar)
    androidx.appcompat.widget.Toolbar bagBar;

    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;
    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.swp_bag)
    SwipeRefreshLayout swpBag;
    @BindView(R.id.rv_bag)
    RecyclerView rvBag;





    private ArrayList<BagBuy> bagBuys;
    private ArrayList<BagBuy> recomments;

    private BagAdapter bagAdapter;

    private BagPresenter bagPresenter;

    private BagPageData bagPageData;

    private BaseApplication baseApplication;
    private boolean isLogin;
    public BagFragment() {}

    /**
     * 构造方法
     * @return
     */
    public static BagFragment newInstance() {
        BagFragment fragment = new BagFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_bag;
    }

    @Override
    protected void initData() {
        bagPresenter = new BagPresenter(this);
        swpBag.setProgressViewOffset (false,100,300);
        swpBag.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }
    @Override
    protected void initView() {
        ViewGroup.LayoutParams layoutParams = bagBar.getLayoutParams();
        layoutParams.height = CommonUtils.getStatusBarHeight(getMContext()) + CommonUtils.dip2px(getMContext(),45);
        bagBar.setLayoutParams(layoutParams);
        bagBar.setPadding(0,CommonUtils.getStatusBarHeight(getMContext()),0,0);
        title.setText("小书包");
        subtitle.setText("");
        swpBag.setRefreshing(true);
        bagPresenter.getBagData(fragmentsharedPreferencesUtils.getParams("token","").toString());


    }
    @Override
    public void loadBagData(BagPageData bagPageData) {

        this.bagPageData = bagPageData;
        if (swpBag.isRefreshing()){
            swpBag.setRefreshing(false);
        }
        initAdapter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UploadMessage uploadMessage){
        bagPresenter.getBagData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }
    @Override
    public void loadFineClassMoreData(ArrayList<SelectClass> selectClasses) {
        for (int i = 0;i < selectClasses.size(); i++){
            bagPageData.getFineClasses().add(selectClasses.get(i));
            bagAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(getMContext()).show(msg,Toast.LENGTH_SHORT);
    }



    public void initAdapter(){
        swpBag.setOnRefreshListener(this);
        if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            isLogin = true;
        }else {
            isLogin = false;
        }
        bagAdapter = new BagAdapter(bagPageData,getMContext(),isLogin);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMContext());
        rvBag.setLayoutManager(linearLayoutManager);
        rvBag.setAdapter(bagAdapter);
        rvBag.addOnScrollListener(new loadMoreDataListener(linearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                bagPresenter.getMoreFineClassData(currentPage * 10,fragmentsharedPreferencesUtils.getParams("token","").toString());
            }
        });
    }

    @Override
    public void onRefresh() {
        bagPresenter.getBagData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        bagPresenter.onDestroy();
    }
}
