package com.leothon.cogito.Mvp.View.Activity.DownloadActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.DownloadAdapter;
import com.leothon.cogito.Bean.Download;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.View.MyToast;

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
    public void initView() {
        setToolbarSubTitle("编辑");
        setToolbarTitle("下载");
        loadFalseData();
        initAdapter();
        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.getInstance(DownloadActivity.this).show("点击编辑下载任务",Toast.LENGTH_SHORT);
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
    public void initData() {

    }


}
