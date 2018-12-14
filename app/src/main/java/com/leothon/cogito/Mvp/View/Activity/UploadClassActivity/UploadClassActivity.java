package com.leothon.cogito.Mvp.View.Activity.UploadClassActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.UploadClassAdapter;
import com.leothon.cogito.Bean.ChooseClass;
import com.leothon.cogito.Bean.SaveUploadData;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.UploadSave;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Message.MessageEvent;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.OnClick;

public class UploadClassActivity extends BaseActivity {

    @BindView(R.id.title_class_upload)
    MaterialEditText titleClassUpload;
    @BindView(R.id.desc_class_upload)
    MaterialEditText descClassUpload;
    @BindView(R.id.rv_add_class)
    RecyclerView rvAddClass;
    @BindView(R.id.add_class_upload)
    TextView addClassUpload;
    @BindView(R.id.send_icon)
    RoundedImageView sendIcon;

    private UploadClassAdapter uploadClassAdapter;
    private ArrayList<ChooseClass> titles;


    private ArrayList<UploadSave> uploadSaves;
    @Override
    public int initLayout() {
        return R.layout.activity_upload_class;
    }

    @Override
    public void initView() {
        titles = new ArrayList<>();
        uploadSaves = new ArrayList<>();
        loadConstantsData();
        setToolbarTitle("添加课程");
        setToolbarSubTitle("");
        initAdapter();
        EventBus.getDefault().register(this);
        sendIcon.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadConstantsData();
    }

    private void loadConstantsData(){
        if (Constants.uploadSaves == null){
            Constants.uploadSaves = new ArrayList<>();
        }
        if (Constants.classTitle == null){
            Constants.classTitle = "";
        }
        if (Constants.classDesc == null) {
            Constants.classDesc = "";
        }
        if (Constants.tags == null) {
            Constants.tags = new ArrayList<>();

        }

        titleClassUpload.setText(Constants.classTitle);
        descClassUpload.setText(Constants.classDesc);

        if (uploadSaves.size() != 0){
            titles.clear();
            Log.e(TAG, "loadConstantsData: "+ uploadSaves.size());
            for (int i=0;i<uploadSaves.size();i++){
                ChooseClass chooseClass = new ChooseClass();
                chooseClass.setName(uploadSaves.get(i).getTitle());
                titles.add(chooseClass);

            }
        }
    }

    @OnClick(R.id.send_icon)
    public void sendClass(View view){
        if (titleClassUpload.getText().toString().equals("") || descClassUpload.getText().toString().equals("") || uploadSaves.size() == 0){
            CommonUtils.makeText(this,"请填写完整内容");
        }else {
            //TODO 上传课程
            CommonUtils.makeText(this,"课程上传成功");
            Constants.classTitle = "";
            Constants.classDesc = "";
            Constants.uploadSaves.clear();
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (titleClassUpload.getText().toString().equals("") && descClassUpload.getText().toString().equals("") && uploadSaves.size() == 0){
            super.onBackPressed();
        }else {
            loadDialog();
        }


    }

    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("是否保存草稿？")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("保存")
                .setNegtive("不保存")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        Constants.classTitle = titleClassUpload.getText().toString();
                        Constants.classDesc = descClassUpload.getText().toString();
                        Constants.uploadSaves = uploadSaves;
                        UploadClassActivity.super.onBackPressed();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        Constants.classTitle = "";
                        Constants.classDesc = "";
                        Constants.uploadSaves.clear();
                        uploadSaves.clear();
                        UploadClassActivity.super.onBackPressed();
                    }

                })
                .show();
    }
    private void initAdapter(){
        uploadClassAdapter = new UploadClassAdapter(this,titles);
        rvAddClass.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvAddClass.setAdapter(uploadClassAdapter);
        uploadClassAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                //TODO 跳转重新编辑
                Constants.classTitle = titleClassUpload.getText().toString();
                Constants.classDesc = descClassUpload.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putInt("mark",position + 1);
                Constants.uploadSaves = uploadSaves;
                IntentUtils.getInstence().intent(UploadClassActivity.this,UploadClassDetailActivity.class,bundle);
            }
        });
        uploadClassAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UploadSave uploadSave) {
        if (uploadSave.getMark() != 0){
            uploadSaves.remove(uploadSave.getMark() - 1);
            uploadSaves.add(uploadSave.getMark() - 1,uploadSave);
        }else {
            uploadSaves.add(uploadSave);
        }

        uploadClassAdapter.notifyDataSetChanged();

    }


    @OnClick(R.id.add_class_upload)
    public void addClassUpload(View view){
        //TODO 添加课程
        Constants.classTitle = titleClassUpload.getText().toString();
        Constants.classDesc = descClassUpload.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putInt("mark",0);
        IntentUtils.getInstence().intent(this,UploadClassDetailActivity.class,bundle);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
