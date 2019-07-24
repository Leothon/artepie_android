package com.leothon.cogito.Mvp.View.Activity.FavActivity;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.FavAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.View.MyToast;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.11
 */
public class FavActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,FavContract.IFavView {

    @BindView(R.id.swp_fav)
    SwipeRefreshLayout swpFav;
    @BindView(R.id.rv_fav)
    RecyclerView rvFav;

    private ArrayList<SelectClass> selectClasses;
    private FavAdapter favAdapter;
    private FavPresenter favPresenter;
    @Override
    public int initLayout() {
        return R.layout.activity_fav;
    }

    @Override
    public void initData() {
        favPresenter = new FavPresenter(this);
        swpFav.setProgressViewOffset (false,100,300);
        swpFav.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);


    }

    @Override
    public void initView() {
        setToolbarTitle("我的收藏");
        setToolbarSubTitle("");
        swpFav.setRefreshing(true);
        favPresenter.getFavClass(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    private void initAdapter(){
        swpFav.setOnRefreshListener(this);
        favAdapter = new FavAdapter(FavActivity.this,selectClasses);
        rvFav.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvFav.setAdapter(favAdapter);

        favAdapter.setRemoveFavClick(new FavAdapter.removeFavOnClickListener() {
            @Override
            public void removeFavClickListener(String classId) {
                favPresenter.removeFavClass(classId,activitysharedPreferencesUtils.getParams("token","").toString());
            }
        });

    }

    @Override
    public void loadFavClass(ArrayList<SelectClass> selectClasses) {
        if (swpFav.isRefreshing()){
            swpFav.setRefreshing(false);
        }
        this.selectClasses = selectClasses;
        initAdapter();
    }

    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }


    @Override
    public void onRefresh() {
        favPresenter.getFavClass(activitysharedPreferencesUtils.getParams("token","").toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        favPresenter.onDestroy();
    }
}
