package com.leothon.cogito.Mvp.View.Activity.UploadDetailClassActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Bean.UploadSave;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.View.MyToast;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class UploadClassDetailActivity extends BaseActivity{


    @BindView(R.id.title_sub_class_detail)
    MaterialEditText titleClassDetail;
    @BindView(R.id.content_class_detail)
    MaterialEditText contentClassDetail;
    @BindView(R.id.video_audio_layout)
    RelativeLayout videoAudioLayout;
    @BindView(R.id.upload_add_video)
    ImageView uploadVideo;
    @BindView(R.id.upload_add_sound)
    ImageView uploadSound;
    @BindView(R.id.add_btn_class)
    TextView addBtn;
    private Intent intent;
    private Bundle bundle;
    private UploadSave uploadSave;
    private ArrayList<UploadSave> uploadSaves;
    @Override
    public int initLayout() {
        return R.layout.activity_upload_class_detail;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        uploadSave = new UploadSave();
        uploadSaves = new ArrayList<>();
        setToolbarSubTitle("");
        setToolbarTitle("添加单节课程");

    }



    @OnClick(R.id.add_btn_class)
    public void addClass(View view){
        if (contentClassDetail.getText().toString().equals("")){
            MyToast.getInstance(this).show("请输入内容再提交",Toast.LENGTH_SHORT);
        }else {
            String title = titleClassDetail.getText().toString();
            String content = contentClassDetail.getText().toString();
            uploadSave.setTitle(title);
            uploadSave.setContent(content);


            //点击修改已有课程
            if (bundle.getInt("mark") != 0){

                uploadSave.setMark(bundle.getInt("mark"));

//                Constants.uploadSaves.remove(bundle.getInt("mark") - 1);
//
//                Constants.uploadSaves.add(bundle.getInt("mark") - 1,uploadSave);
            }else {//点击添加新课程


                uploadSave.setMark(0);


            }

            EventBus.getDefault().post(uploadSave);
            onBackPressed();

        }

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
