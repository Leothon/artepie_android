package com.leothon.cogito.Mvp.View.Activity.QAHisActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.ArticleHisActivity.ArticleHisActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;

public class QAHisActivity extends BaseActivity implements QAHisContract.IQAHisView,SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_upload)
    RecyclerView rvUpload;
    @BindView(R.id.swp_upload)
    SwipeRefreshLayout swpUpload;
    private AskAdapter uploadAdapter;
    private ArrayList<QAData> askArrayList;

    private UserEntity userEntity;

    private Intent intent;
    private Bundle bundle;

    private QAHisPresenter qaHisPresenter;

    @Override
    public int initLayout() {
        return R.layout.activity_upload;
    }
    @Override
    public void initData() {
        swpUpload.setProgressViewOffset (false,100,300);
        swpUpload.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();
        qaHisPresenter = new QAHisPresenter(this);
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        intent = getIntent();
        bundle = intent.getExtras();
    }
    @Override
    public void initView() {
        getToolbarSubTitle().setTextColor(Color.parseColor("#f26402"));
        if (bundle.get("userId").equals(userEntity.getUser_id())){
            setToolbarTitle("我发布的内容");
            setToolbarSubTitle("我的文章");

        }else {
            setToolbarTitle("他发布的内容");
            setToolbarSubTitle("他的文章");
        }

        swpUpload.setRefreshing(true);
        qaHisPresenter.getAskData(bundle.getString("userId"));


        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleto = new Bundle();
                bundleto.putString("userId",bundle.getString("userId"));
                IntentUtils.getInstence().intent(QAHisActivity.this,ArticleHisActivity.class,bundleto);
            }
        });
    }

    @Override
    public void loadAskData(ArrayList<QAData> qaData) {
        askArrayList = qaData;
        if (swpUpload.isRefreshing()){
            swpUpload.setRefreshing(false);
        }
        initAdapter();
    }

    @Override
    public void onRefresh() {
        qaHisPresenter.getAskData(bundle.getString("userId"));
    }

    @Override
    public void loadAskMoreData(ArrayList<QAData> qaData) {
        if (swpUpload.isRefreshing()){
            swpUpload.setRefreshing(false);
        }
        for (int i = 0;i < qaData.size(); i++){
            askArrayList.add(qaData.get(i));

        }
        uploadAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    private void initAdapter(){
        swpUpload.setOnRefreshListener(this);
        uploadAdapter = new AskAdapter(this,askArrayList,true);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvUpload.setLayoutManager(mlinearLayoutManager);
        rvUpload.setAdapter(uploadAdapter);
        rvUpload.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = mlinearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = mlinearLayoutManager.findFirstVisibleItemPosition();
                if (GSYVideoManager.instance().getPlayPosition() >= 0){
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if (GSYVideoManager.instance().getPlayTag().equals(AskAdapter.TAG) && (position < firstVisibleItem || position > lastVisibleItem)){
                        if (GSYVideoManager.isFullState(QAHisActivity.this)){
                            return;
                        }

                        GSYVideoManager.releaseAllVideos();
                        uploadAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        uploadAdapter.setOnClickaddLike(new AskAdapter.addLikeOnClickListener() {
            @Override
            public void addLikeClickListener(boolean isLike,String qaId) {
                if (isLike){
                    qaHisPresenter.removeLiked(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                }else {
                    qaHisPresenter.addLiked(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                }
            }
        });

        rvUpload.addOnScrollListener(new loadMoreDataListener(mlinearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpUpload.setRefreshing(true);
                qaHisPresenter.getAskMoreData(currentPage * 15,bundle.getString("userId"));
            }
        });
    }



    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qaHisPresenter.onDestroy();
        GSYVideoManager.releaseAllVideos();

    }
}
