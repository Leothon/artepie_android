package com.leothon.cogito.Mvp.View.Activity.DownloadActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.exoplayer2.ui.DownloadNotificationUtil;
import com.leothon.cogito.Adapter.DownloadAdapter;
import com.leothon.cogito.Bean.Download;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class DownloadActivity extends BaseActivity {

    @BindView(R.id.rv_download)
    RecyclerView rvDownload;

    private DownloadAdapter downloadAdapter;
    private ArrayList<Download> downloads;
    @Override
    public int initLayout() {
        return R.layout.activity_download;
    }

    @Override
    public void initview() {
        setToolbarSubTitle("编辑");
        setToolbarTitle("下载");
        loadFalseData();
        initAdapter();
        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 点击进行编辑
                CommonUtils.makeText(CommonUtils.getContext(),"点击编辑下载任务");
            }
        });
    }

    private void loadFalseData(){
        downloads = new ArrayList<>();
        for (int i = 0;i < 15;i++){
            Download download = new Download();
            download.setDownloadurl("");
            download.setDownloadtitle("正在下载的课程");
            downloads.add(download);
        }

    }

    public void initAdapter(){
        downloadAdapter = new DownloadAdapter(this,downloads);
        rvDownload.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvDownload.setAdapter(downloadAdapter);
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
