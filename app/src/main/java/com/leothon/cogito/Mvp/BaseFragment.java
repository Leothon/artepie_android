package com.leothon.cogito.Mvp;
/*
* created by leothon on 2018.7.22
* */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leothon.cogito.Utils.SharedPreferencesUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    /*
    * 封装基类fragment
    * */

    private View mContentView;
    private Context mContext;
    private Unbinder unbinder;
    public SharedPreferencesUtils fragmentsharedPreferencesUtils;
    @Nullable
    @Override
    public View onCreateView(@android.support.annotation.NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
        unbinder = ButterKnife.bind(this,mContentView);
        mContext = getContext();
        fragmentsharedPreferencesUtils = new SharedPreferencesUtils(getMContext(),"saveToken");
        init();
        initData();
        initView();
        return mContentView;
    }

    protected abstract int setLayoutResourceID();

    protected  void init(){}

    protected abstract void initView();

    protected abstract void initData();

    public View getContentView(){
        return mContentView;
    }

    public Context getMContext(){
        return mContext;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
