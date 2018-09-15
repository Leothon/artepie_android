package com.leothon.cogito.Mvp.View.Activity.SearchActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {


    @BindView(R.id.rv_search)
    RecyclerView rvSearch;

    @BindView(R.id.search_content)
    MaterialEditText searchContent;



    @Override
    public int initLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {


    }

    @Override
    public void initData() {

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
