package com.leothon.cogito.Mvp.View.Activity.UploadActivity;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;

public class UploadActivity extends BaseActivity implements UploadContract.IUploadView,SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_upload)
    RecyclerView rvUpload;
    @BindView(R.id.swp_upload)
    SwipeRefreshLayout swpUpload;
    private AskAdapter uploadAdapter;
    private ArrayList<QAData> askArrayList;

    //private UserEntity userEntity;

    private UploadPresenter uploadPresenter;
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
    }

    @Override
    public int initLayout() {
        return R.layout.activity_upload;
    }
    @Override
    public void initData() {
        swpUpload.setProgressViewOffset (false,100,300);
        swpUpload.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
//        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
//        String uuid = tokenValid.getUid();
        uploadPresenter = new UploadPresenter(this);
        //userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
    }
    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("我发布的内容");
        swpUpload.setRefreshing(true);
        uploadPresenter.getAskData(activitysharedPreferencesUtils.getParams("token","").toString());
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
        uploadPresenter.getAskData(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    @Override
    public void loadAskMoreData(ArrayList<QAData> qaData) {
        for (int i = 0;i < qaData.size(); i++){
            askArrayList.add(qaData.get(i));
            uploadAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showInfo(String msg) {
        CommonUtils.makeText(this,msg);
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
                        if (GSYVideoManager.isFullState(UploadActivity.this)){
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
                    uploadPresenter.removeLiked(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                }else {
                    uploadPresenter.addLiked(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                }
            }
        });

        rvUpload.addOnScrollListener(new loadMoreDataListener(mlinearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                uploadPresenter.getAskMoreData(currentPage * 15,activitysharedPreferencesUtils.getParams("token","").toString());
            }
        });
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        uploadPresenter.onDestroy();
    }
}
