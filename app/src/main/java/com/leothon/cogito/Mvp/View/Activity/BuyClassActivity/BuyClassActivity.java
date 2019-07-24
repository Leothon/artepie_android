package com.leothon.cogito.Mvp.View.Activity.BuyClassActivity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.BuyClassAdapter;
import com.leothon.cogito.Adapter.FavAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavActivity;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavPresenter;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;

import java.util.ArrayList;

import butterknife.BindView;

public class BuyClassActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BuyClassContract.IBuyClassView {


    @BindView(R.id.rv_buy_class)
    RecyclerView rvBuyClass;
    @BindView(R.id.swp_buy_class)
    SwipeRefreshLayout swpBuyClass;


    private ArrayList<SelectClass> selectClasses;
    private BuyClassAdapter buyClassAdapter;
    private BuyClassPresenter buyClassPresenter;
    @Override
    public int initLayout() {
        return R.layout.activity_buy_class;
    }

    @Override
    public void initView() {
        setToolbarTitle("我订阅的课程");
        setToolbarSubTitle("");

        swpBuyClass.setRefreshing(true);
        buyClassPresenter.getBuyClass(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    @Override
    public void initData() {
        buyClassPresenter = new BuyClassPresenter(this);
        swpBuyClass.setProgressViewOffset (false,100,300);
        swpBuyClass.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

    }

    @Override
    public void onRefresh() {
        buyClassPresenter.getBuyClass(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    private void initAdapter(){
        swpBuyClass.setOnRefreshListener(this);
        buyClassAdapter = new BuyClassAdapter(BuyClassActivity.this,selectClasses);
        rvBuyClass.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvBuyClass.setAdapter(buyClassAdapter);
        buyClassAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("classId",selectClasses.get(position).getSelectId());
                IntentUtils.getInstence().intent(BuyClassActivity.this, SelectClassActivity.class,bundle);
            }
        });

        buyClassAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

    }
    @Override
    public void loadBuyClass(ArrayList<SelectClass> selectClasses) {
        if (swpBuyClass.isRefreshing()){
            swpBuyClass.setRefreshing(false);
        }
        this.selectClasses = selectClasses;
        initAdapter();
    }

    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(this).show(msg, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        buyClassPresenter.onDestroy();
    }
}
