package com.leothon.cogito.Mvp.View.Activity.SettingsActivity;

import android.view.View;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Weight.MDCheckBox;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.9
 */
public class MessageActivity extends BaseActivity {

    @BindView(R.id.sound_notice)
    MDCheckBox soundNotice;
    @BindView(R.id.ask_notice)
    MDCheckBox askNotice;
    @BindView(R.id.class_notice)
    MDCheckBox classNotice;


    private SharedPreferencesUtils sharedPreferencesUtils;
    @Override
    public int initLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("消息设置");
        if ((boolean)sharedPreferencesUtils.getParams("soundNotice",false)){
            soundNotice.setChecked(true);
        }
        if ((boolean)sharedPreferencesUtils.getParams("qaNotice",false)){
            askNotice.setChecked(true);
        }
        if ((boolean)sharedPreferencesUtils.getParams("classNotice",false)){
            classNotice.setChecked(true);
        }
        soundNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soundNotice.isChecked()){
                    soundNotice.setChecked(false);
                    sharedPreferencesUtils.setParams("soundNotice",false);
                }else {
                    soundNotice.setChecked(true);
                    sharedPreferencesUtils.setParams("soundNotice",true);
                }


            }
        });
       askNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (askNotice.isChecked()){
                   askNotice.setChecked(false);
                   sharedPreferencesUtils.setParams("qaNotice",false);
               }else {
                   askNotice.setChecked(true);
                   sharedPreferencesUtils.setParams("qaNotice",true);
               }
           }
       });


       classNotice.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (classNotice.isChecked()){
                   classNotice.setChecked(false);

                   sharedPreferencesUtils.setParams("classNotice",false);
               }else {
                   classNotice.setChecked(true);
                   sharedPreferencesUtils.setParams("classNotice",true);
               }
           }
       });

    }

    @Override
    public void initData() {
        sharedPreferencesUtils = new SharedPreferencesUtils(this,"artSettings");
    }

}
