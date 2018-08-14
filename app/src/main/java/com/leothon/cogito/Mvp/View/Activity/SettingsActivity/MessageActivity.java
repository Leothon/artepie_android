package com.leothon.cogito.Mvp.View.Activity.SettingsActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.google.android.exoplayer2.C;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Weight.MDCheckBox;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.9
 */
public class MessageActivity extends BaseActivity {

    @BindView(R.id.sound_notice)
    MDCheckBox soundNotice;
    @BindView(R.id.ask_notice)
    MDCheckBox askNotice;
    @BindView(R.id.comment_notice)
    MDCheckBox commentNotice;
    @BindView(R.id.class_notice)
    MDCheckBox classNotice;


    @Override
    public int initLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initview() {
        setToolbarSubTitle("");
        setToolbarTitle("消息设置");
        soundNotice.setChecked(true);
        askNotice.setChecked(true);
        commentNotice.setChecked(true);
        classNotice.setChecked(true);
        soundNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soundNotice.isChecked()){
                    soundNotice.setChecked(false);
                    //TODO 未选中
                    CommonUtils.makeText(CommonUtils.getContext(),"未选中");
                }else {
                    soundNotice.setChecked(true);
                    //TODO 选中
                    CommonUtils.makeText(CommonUtils.getContext(),"选中");
                }


            }
        });
       askNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (askNotice.isChecked()){
                   askNotice.setChecked(false);
                   //TODO 未选中
                   CommonUtils.makeText(CommonUtils.getContext(),"未选中");
               }else {
                   askNotice.setChecked(true);
                   //TODO 选中
                   CommonUtils.makeText(CommonUtils.getContext(),"选中");
               }
           }
       });

       commentNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (commentNotice.isChecked()){
                   commentNotice.setChecked(false);
                   //TODO 未选中
                   CommonUtils.makeText(CommonUtils.getContext(),"未选中");
               }else {
                   commentNotice.setChecked(true);
                   //TODO 选中
                   CommonUtils.makeText(CommonUtils.getContext(),"选中");
               }
           }
       });
       classNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (classNotice.isChecked()){
                   classNotice.setChecked(false);
                   //TODO 未选中
                   CommonUtils.makeText(CommonUtils.getContext(),"未选中");
               }else {
                   classNotice.setChecked(true);
                   //TODO 选中
                   CommonUtils.makeText(CommonUtils.getContext(),"选中");
               }
           }
       });

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
