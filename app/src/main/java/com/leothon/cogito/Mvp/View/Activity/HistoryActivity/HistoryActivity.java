package com.leothon.cogito.Mvp.View.Activity.HistoryActivity;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.HisAdapter;
import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.View.MyToast;

import java.util.ArrayList;

import butterknife.BindView;

public class HistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,HistoryContract.IHisView {


    @BindView(R.id.swp_his)
    SwipeRefreshLayout swpHis;
    @BindView(R.id.rv_his)
    RecyclerView rvHis;

    private HisAdapter hisAdapter;
    private HistoryPresenter historyPresenter;
    private ArrayList<ClassDetailList> classDetailLists;

    @Override
    public int initLayout() {
        return R.layout.activity_history;
    }


    @Override
    public void initData() {
        historyPresenter = new HistoryPresenter(this);
        swpHis.setProgressViewOffset (false,100,300);
        swpHis.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

    }
    @Override
    public void initView() {
        setToolbarTitle("播放历史");
        setToolbarSubTitle("");
        swpHis.setRefreshing(true);
        historyPresenter.getViewClass(activitysharedPreferencesUtils.getParams("token","").toString());
    }
    private void initAdapter(){
        swpHis.setOnRefreshListener(this);
        hisAdapter = new HisAdapter(HistoryActivity.this,classDetailLists);
        rvHis.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvHis.setAdapter(hisAdapter);

        hisAdapter.setRemoveViewClick(new HisAdapter.removeViewOnClickListener() {
            @Override
            public void removeViewClickListener(String classdId) {
                historyPresenter.removeViewClass(classdId,activitysharedPreferencesUtils.getParams("token","").toString());
            }
        });
    }

    @Override
    public void loadViewClass(ArrayList<ClassDetailList> classDetailLists) {
        this.classDetailLists = classDetailLists;
        if (swpHis.isRefreshing()){
            swpHis.setRefreshing(false);
        }

        initAdapter();
    }

    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }


    @Override
    public void onRefresh() {
        historyPresenter.getViewClass(activitysharedPreferencesUtils.getParams("token","").toString());
    }







}
