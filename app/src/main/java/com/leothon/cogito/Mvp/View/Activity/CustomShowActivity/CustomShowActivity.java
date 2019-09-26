package com.leothon.cogito.Mvp.View.Activity.CustomShowActivity;

import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.CustomShowAdapter;
import com.leothon.cogito.Adapter.FavAdapter;
import com.leothon.cogito.Bean.CustomShow;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomActivity;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomContract;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CustomShowActivity extends BaseActivity implements CustomShowContract.ICustomShowView,SwipeRefreshLayout.OnRefreshListener {


    private CustomShowPresenter customShowPresenter;

    @BindView(R.id.custom_show_rv)
    RecyclerView customShowRv;
    @BindView(R.id.swp_custom_show)
    SwipeRefreshLayout swpCustomShow;

    private CustomShowAdapter customShowAdapter;
    private ArrayList<CustomShow> customShows;
    @Override
    public int initLayout() {
        return R.layout.activity_custom_show;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("什么是AIS?");
        getToolbarSubTitle().setTextColor(getResources().getColor(R.color.colorAccent));
        getToolbarSubTitle().setTextSize(12f);
        setToolbarTitle("AIS系统案例展示");
        getToolbarTitle().setTextColor(getResources().getColor(R.color.black));
        swpCustomShow.setRefreshing(true);
        customShowPresenter.getCustomShow();

        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialog();
            }
        });
    }

    @Override
    public void initData() {
        customShowPresenter = new CustomShowPresenter(this);
        swpCustomShow.setProgressViewOffset (false,100,300);
        swpCustomShow.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

    }


    private void initAdapter(){
        swpCustomShow.setOnRefreshListener(this);
        customShowAdapter = new CustomShowAdapter(this,customShows);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        customShowRv.setLayoutManager(linearLayoutManager);
        customShowRv.setAdapter(customShowAdapter);
        customShowRv.addOnScrollListener(new loadMoreDataListener(linearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpCustomShow.setRefreshing(true);
                customShowPresenter.getMoreCustomShow(currentPage * 15);
            }
        });

        customShowAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

            }
        });

        customShowAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });
    }


    @Override
    public void onRefresh() {
        customShowPresenter.getCustomShow();
    }

    @Override
    public void loadCustomShow(ArrayList<CustomShow> customShows) {
        if (swpCustomShow.isRefreshing()){
            swpCustomShow.setRefreshing(false);
        }
        this.customShows = customShows;
        initAdapter();
    }

    @Override
    public void loadMoreCustomShow(ArrayList<CustomShow> customShows) {
        if (swpCustomShow.isRefreshing()){
            swpCustomShow.setRefreshing(false);
        }
        for (int i = 0;i < customShows.size(); i++){
            this.customShows.add(customShows.get(i));

        }
        customShowAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(this).show(msg, Toast.LENGTH_SHORT);
    }


    @OnClick(R.id.to_custom_page)
    public void toCustomPage(View view){

        if ((boolean)activitysharedPreferencesUtils.getParams("login",false)) {
            IntentUtils.getInstence().intent(this, CustomActivity.class);
        }else {
            CommonUtils.loadinglogin(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        customShowPresenter.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }


    @Override
    protected void onPause() {
        Log.e(TAG, "onPause: ");
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }


    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setCanceledOnTouchOutside(true);
        dialog.setMessage(
                "Q:什么是AIS系统？\n" +
                        "A:AIS系统全称企业听觉识别系统。以特有的语音，音乐，视频，自然音响及其特殊音效等声音建立的听觉识别系统。主要包括商业定制音乐、企业定制歌曲、企业注册的特殊声音、企业特别发言人的声音等内容，从而建立企业核心竞争力，创建企业品牌。" )
                .setTitle("")
                .setSingle(true)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();

                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

}
