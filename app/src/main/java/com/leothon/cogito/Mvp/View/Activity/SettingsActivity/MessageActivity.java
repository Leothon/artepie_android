package com.leothon.cogito.Mvp.View.Activity.SettingsActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.View.MyToast;
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
    public void initView() {
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
                    MyToast.getInstance(MessageActivity.this).show("未选中",Toast.LENGTH_SHORT);
                }else {
                    soundNotice.setChecked(true);
                    //TODO 选中
                    MyToast.getInstance(MessageActivity.this).show("选中",Toast.LENGTH_SHORT);
                }


            }
        });
       askNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (askNotice.isChecked()){
                   askNotice.setChecked(false);
                   //TODO 未选中
                   MyToast.getInstance(MessageActivity.this).show("未选中",Toast.LENGTH_SHORT);
               }else {
                   askNotice.setChecked(true);
                   //TODO 选中
                   MyToast.getInstance(MessageActivity.this).show("选中",Toast.LENGTH_SHORT);
               }
           }
       });

       commentNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (commentNotice.isChecked()){
                   commentNotice.setChecked(false);
                   //TODO 未选中
                   MyToast.getInstance(MessageActivity.this).show("未选中",Toast.LENGTH_SHORT);
               }else {
                   commentNotice.setChecked(true);
                   //TODO 选中
                   MyToast.getInstance(MessageActivity.this).show("选中",Toast.LENGTH_SHORT);
               }
           }
       });
       classNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (classNotice.isChecked()){
                   classNotice.setChecked(false);
                   //TODO 未选中
                   MyToast.getInstance(MessageActivity.this).show("未选中",Toast.LENGTH_SHORT);
               }else {
                   classNotice.setChecked(true);
                   //TODO 选中
                   MyToast.getInstance(MessageActivity.this).show("选中",Toast.LENGTH_SHORT);
               }
           }
       });

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
