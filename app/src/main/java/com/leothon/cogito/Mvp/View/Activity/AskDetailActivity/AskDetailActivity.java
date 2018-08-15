package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;

public class AskDetailActivity extends BaseActivity {


    @Override
    public int initLayout() {
        return R.layout.activity_ask_detail;
    }

    @Override
    public void initview() {

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
