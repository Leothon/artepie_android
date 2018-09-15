package com.leothon.cogito.Mvp.View.Activity.SettingsActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on  2018.8.9
 */
public class AdviceActivity extends BaseActivity {

    @BindView(R.id.edit_advice)
    MaterialEditText editAdvice;

    @Override
    public int initLayout() {
        return R.layout.activity_advice;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("反馈意见");
    }

    @OnClick(R.id.submit_advice)
    public void submitAdvice(View view){
        //TODO 提交用户的反馈
        String advice = editAdvice.getText().toString();
        CommonUtils.makeText(this,"已提交"+advice);
        editAdvice.setText("");
        //onBackPressed();
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
