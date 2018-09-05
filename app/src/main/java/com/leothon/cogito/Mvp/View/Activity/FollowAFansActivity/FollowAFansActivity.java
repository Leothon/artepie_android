package com.leothon.cogito.Mvp.View.Activity.FollowAFansActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.FollowAFansAdapter;
import com.leothon.cogito.Bean.Voice;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class FollowAFansActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.swp_followafans)
    SwipeRefreshLayout swpFollowFans;
    @BindView(R.id.rv_followafans)
    RecyclerView rvFollowFans;

    private ArrayList<Voice> voices;
    private FollowAFansAdapter followAFansAdapter;

    private Intent intent;
    private Bundle bundle;
    @Override
    public int initLayout() {
        return R.layout.activity_follow_afans;
    }

    @Override
    public void initview() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        if (bundle.getString("type").equals("follow")){
            //TODO 加载关注列表
            setToolbarTitle("关注列表");
            loadFalseData1();
        }else {
            //TODO 加载粉丝列表
            setToolbarTitle("粉丝列表");
            loadFalseData2();
        }

        initAdapter();
    }

    private void loadFalseData1(){
        voices = new ArrayList<>();
        for (int i = 0; i < 30;i++){
            Voice voice = new Voice();
            voice.setTitle("陈江流");
            voice.setDescription("得道高僧陈玄奘");
            voice.setImgurl("https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1536147071841%26di%3D816cee85d35f312482da4fe06d425be3%26imgtype%3D0%26src%3Dhttp%253A%252F%252Ftravel.taiwan.cn%252Flist%252F201410%252FW020141017498044176219.jpg&thumburl=https%3A%2F%2Fss1.bdstatic.com%2F70cFuXSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D2704465701%2C1268881022%26fm%3D26%26gp%3D0.jpg");
            voices.add(voice);
        }
    }
    private void loadFalseData2(){
        voices = new ArrayList<>();
        for (int i = 0; i < 30;i++){
            Voice voice = new Voice();
            voice.setTitle("陈到底");
            voice.setDescription("无根悟净");
            voice.setImgurl("https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1536147071841%26di%3D816cee85d35f312482da4fe06d425be3%26imgtype%3D0%26src%3Dhttp%253A%252F%252Ftravel.taiwan.cn%252Flist%252F201410%252FW020141017498044176219.jpg&thumburl=https%3A%2F%2Fss1.bdstatic.com%2F70cFuXSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D2704465701%2C1268881022%26fm%3D26%26gp%3D0.jpg");
            voices.add(voice);
        }
    }

    private void initAdapter(){
        followAFansAdapter = new FollowAFansAdapter(this,voices);
        rvFollowFans.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rvFollowFans.setAdapter(followAFansAdapter);
        followAFansAdapter.setOnItemClickListner(new BaseAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                Bundle bundleto = new Bundle();
                bundleto.putString("type","other");
                IntentUtils.getInstence().intent(FollowAFansActivity.this, IndividualActivity.class,bundleto);
            }
        });
        followAFansAdapter.setOnItemLongClickListner(new BaseAdapter.OnItemLongClickListner() {
            @Override
            public void onItemLongClickListner(View v, int position) {

            }
        });
    }
    @Override
    public void initdata() {

    }

    @Override
    public void onRefresh() {
        //TODO 重新加载
        swpFollowFans.setRefreshing(false);
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
