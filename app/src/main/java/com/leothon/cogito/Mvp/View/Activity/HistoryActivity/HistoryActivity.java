package com.leothon.cogito.Mvp.View.Activity.HistoryActivity;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Adapter.FavAdapter;
import com.leothon.cogito.Adapter.HisAdapter;
import com.leothon.cogito.Bean.Fav;
import com.leothon.cogito.Bean.VideoClass;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavActivity;
import com.leothon.cogito.R;

import java.util.ArrayList;

import butterknife.BindView;

public class HistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.swp_his)
    SwipeRefreshLayout swpHis;
    @BindView(R.id.rv_his)
    RecyclerView rvHis;

    private ArrayList<VideoClass> videoClasses;
    private HisAdapter hisAdapter;


    @Override
    public int initLayout() {
        return R.layout.activity_history;
    }

    @Override
    public void initview() {
        setToolbarTitle("播放历史");
        setToolbarSubTitle("");
        loadFalseData();
        initAdapter();
    }
    private void initAdapter(){
        swpHis.setOnRefreshListener(this);
        hisAdapter = new HisAdapter(HistoryActivity.this,videoClasses);
        rvHis.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvHis.setAdapter(hisAdapter);


    }
    private void loadFalseData(){

        videoClasses = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            VideoClass videoClass = new VideoClass();
            videoClass.setVideoUrl("");
            videoClass.setVideoTitle("浏览过的视频");
            videoClass.setVideoDescription("浏览视频的描述");
            videoClass.setVideoAuthor("古巨基");
            videoClasses.add(videoClass);
        }

    }

    @Override
    public void onRefresh() {
        swpHis.setRefreshing(false);
    }



    @Override
    public void initdata() {

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
