package com.leothon.cogito.Mvp.View.Activity.ActivityListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.ListCommonAdapter;
import com.leothon.cogito.Bean.Activityed;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.ActivityActivity.ActivityActivity;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.5
 */
public class ActivityListActivity extends BaseActivity {


    private ListCommonAdapter listCommonAdapter;
    private ArrayList<Activityed> activityeds;
    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private Intent intent;
    private Bundle bundle;

    @Override
    public int initLayout() {
        return R.layout.activity_list;
    }

    @Override
    public void initview() {
        intent = getIntent();
        bundle = intent.getExtras();
        loadFalseData();
        initAdapter();
        setToolbarTitle(bundle.getString("title"));
        setToolbarSubTitle("");
    }

    public void initAdapter(){
        listCommonAdapter = new ListCommonAdapter(this,activityeds);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setAdapter(listCommonAdapter);
        listCommonAdapter.setOnItemClickListner(new BaseAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                if (bundle.getString("title").equals("更多演出活动")){
                    //TODO 更多演出活动
                    Bundle bundle = new Bundle();
                    bundle.putString("title",activityeds.get(position).getClasstitle());
                    bundle.putString("url",activityeds.get(position).getClassurl());
                    IntentUtils.getInstence().intent(ActivityListActivity.this, ActivityActivity.class,bundle);
                }else if (bundle.getString("title").equals("更多精彩课时")){
                    //TODO 更多精彩课时
                    Bundle bundle = new Bundle();
                    bundle.putString("imgTitle",activityeds.get(position).getClasstitle());
                    bundle.putString("imgUrls",activityeds.get(position).getClassurl());
                    IntentUtils.getInstence().intent(ActivityListActivity.this, PlayerActivity.class,bundle);
                }

            }
        });

        listCommonAdapter.setOnItemLongClickListner(new BaseAdapter.OnItemLongClickListner() {
            @Override
            public void onItemLongClickListner(View v, int position) {

            }
        });
    }

    private void loadFalseData(){
        activityeds = new ArrayList<>();
        Activityed activityed = new Activityed();
        activityed.setClasstitle("演出活动的标题");
        activityed.setClassdescription("某位大艺术家的演出");
        activityed.setClassurl("https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1533488899582%26di%3D424be038e04bc4790b2b8f1da0fd682f%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fhzimg.guwanch.com%252Fcoms.zhangyan.com%252Fcapture%252F0c265870d82548529e0c8e56c52fdb92.jpg&thumburl=https%3A%2F%2Fss1.bdstatic.com%2F70cFuXSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D1320269086%2C3763628903%26fm%3D27%26gp%3D0.jpg");
        activityed.setClassprice("￥245");
        for (int i = 0;i < 30;i++){

            activityeds.add(activityed);
        }
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
