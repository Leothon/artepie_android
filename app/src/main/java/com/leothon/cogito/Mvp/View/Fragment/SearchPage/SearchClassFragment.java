package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.R;



public class SearchClassFragment extends BaseFragment {


    public SearchClassFragment() {

    }


    public static SearchClassFragment newInstance() {
        SearchClassFragment fragment = new SearchClassFragment();
        return fragment;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_search_class;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

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
