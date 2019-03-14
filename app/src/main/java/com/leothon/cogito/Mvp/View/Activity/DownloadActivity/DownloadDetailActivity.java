package com.leothon.cogito.Mvp.View.Activity.DownloadActivity;



import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;

import butterknife.BindView;

public class DownloadDetailActivity extends BaseActivity {


    @BindView(R.id.rv_detail_download)
    RecyclerView rvDetail;

    @Override
    public int initLayout() {
        return R.layout.activity_download_detail;
    }

    @Override
    public void initView() {

        loadFalseData();
        initAdapter();
    }

    @Override
    public void initData() {

    }

    public void initAdapter(){

    }
    public void loadFalseData(){

    }


}
