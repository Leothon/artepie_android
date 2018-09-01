package com.leothon.cogito.Mvp.View.Activity.UploadActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;

public class UploadActivity extends BaseActivity {


    @BindView(R.id.rv_upload)
    RecyclerView rvUpload;

    private AskAdapter uploadAdapter;
    private ArrayList<Ask> askArrayList;

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
    public void initview() {
        setToolbarSubTitle("");
        setToolbarTitle("我发布的内容");
        loadFalseData();
        initAdapter();
    }


    private void loadFalseData(){
        askArrayList = new ArrayList<>();
        for (int i = 0 ;i < 10;i++){
            Ask ask = new Ask();
            ask.setUsericonurl("http://bpic.588ku.com/element_origin_min_pic/16/10/27/a83c050d95559070f6dea688be356b5c.jpg");
            ask.setUsername("张自忠");
            ask.setUserdes("抗日豪杰");
            ask.setContent("生当作人杰，死亦为鬼雄");
            ask.setLikecount("122");
            ask.setCommentcount("56");
            ask.setVideourl("http://121.196.199.171:8080/myweb/cogito001.mp4");
            ask.setCoverurl("http://bpic.588ku.com/element_origin_min_pic/16/10/27/a83c050d95559070f6dea688be356b5c.jpg");

            askArrayList.add(ask);
        }
    }
    private void initAdapter(){
        uploadAdapter = new AskAdapter(this,askArrayList);
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
    public void initdata() {

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
