package com.leothon.cogito.Mvp.View.Activity.FavActivity;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.FavAdapter;
import com.leothon.cogito.Bean.Fav;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * created by leothon on 2018.8.11
 */
public class FavActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.swp_fav)
    SwipeRefreshLayout swpFav;
    @BindView(R.id.rv_fav)
    RecyclerView rvFav;

    private ArrayList<Fav> favs;
    private FavAdapter favAdapter;
    @Override
    public int initLayout() {
        return R.layout.activity_fav;
    }

    @Override
    public void initView() {
        setToolbarTitle("我的收藏");
        setToolbarSubTitle("");
        loadFalseData();
        initAdapter();
    }

    private void initAdapter(){
        swpFav.setOnRefreshListener(this);
        favAdapter = new FavAdapter(FavActivity.this,favs);
        rvFav.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvFav.setAdapter(favAdapter);


    }
    private void loadFalseData(){

        favs = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            Fav fav = new Fav();
            fav.setFavurl("");
            fav.setTitle("收藏的课程");
            fav.setDescription("收藏课程的描述");
            fav.setAuthor("古巨基");
            favs.add(fav);
        }

    }

    @Override
    public void onRefresh() {
        swpFav.setRefreshing(false);
    }

    @Override
    public void initData() {

    }
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }


}
