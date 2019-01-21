package com.leothon.cogito.Mvp.View.Activity.UploadActivity;

import android.support.annotation.NonNull;
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
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.tokenUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;

public class UploadActivity extends BaseActivity {


    @BindView(R.id.rv_upload)
    RecyclerView rvUpload;

    private AskAdapter uploadAdapter;
    private ArrayList<QAData> askArrayList;

    private UserEntity userEntity;
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
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();

        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
    }
    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("我发布的内容");
        loadFalseData();
        initAdapter();
    }


    private void loadFalseData(){
        askArrayList = new ArrayList<>();
        for (int i = 0 ;i < 10;i++){
            QAData qaData = new QAData();

            qaData.setUser_icon(userEntity.getUser_icon());
            qaData.setUser_name(userEntity.getUser_name());
            qaData.setUser_signal(userEntity.getUser_signal());
            qaData.setQa_content("发布一条视频");
            qaData.setQa_like("45");
            qaData.setQa_comment("1");
            qaData.setQa_video("");
            qaData.setQa_video_cover(Api.ComUrl + "resource/home1.jpg");
            askArrayList.add(qaData);
        }
    }
    private void initAdapter(){

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
