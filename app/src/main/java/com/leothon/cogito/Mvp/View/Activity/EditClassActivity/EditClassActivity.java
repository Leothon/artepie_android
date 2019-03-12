package com.leothon.cogito.Mvp.View.Activity.EditClassActivity;


import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.EditClassAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Message.UploadMessage;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

public class EditClassActivity extends BaseActivity implements EditClassContract.IEditClassView,SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.swp_edit_class)
    SwipeRefreshLayout swpEdit;
    @BindView(R.id.rv_edit_class)
    RecyclerView rvEdit;


    private EditClassAdapter editClassAdapter;
    private EditClassPresenter editClassPresenter;

    private ArrayList<SelectClass> selectClasses;

    private Bundle bundle;
    private Intent intent;
    @Override
    public int initLayout() {
        return R.layout.activity_edit_class;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        if (bundle.get("type").equals("other")){
            setToolbarTitle("他制作的课程");
        }else {
            setToolbarTitle("编辑我的课程");
        }
        swpEdit.setRefreshing(true);
        editClassPresenter.getSelectClassInfo(bundle.getString("userId"));
    }

    @Override
    public void initData() {
        editClassPresenter = new EditClassPresenter(this);
        swpEdit.setProgressViewOffset (false,100,300);
        swpEdit.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        intent = getIntent();
        bundle = intent.getExtras();
    }

    private void initAdapter(){
        swpEdit.setOnRefreshListener(this);
        editClassAdapter = new EditClassAdapter(this,selectClasses);
        rvEdit.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvEdit.setAdapter(editClassAdapter);


        editClassAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (bundle.get("type").equals("other")){
                    if (selectClasses.get(position).isIsbuy() || selectClasses.get(position).getSelectprice().equals("0.00")){
                        Bundle bundleto = new Bundle();
                        bundleto.putString("classId",selectClasses.get(position).getSelectId());
                        IntentUtils.getInstence().intent(EditClassActivity.this, SelectClassActivity.class,bundleto);
                    }else {
                        loadDialog(selectClasses.get(position).getSelectId());
                    }
                }else {
                    Bundle bundleto = new Bundle();
                    bundleto.putString("classId",selectClasses.get(position).getSelectId());
                    IntentUtils.getInstence().intent(EditClassActivity.this,SelectClassActivity.class,bundleto);
                }
            }
        });

        editClassAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {
                if (!bundle.get("type").equals("other")){
                    loadDeleteDialog(selectClasses.get(position).getSelectId());
                }else {
                    MyToast.getInstance(EditClassActivity.this).show("轻点进入该课程",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void loadDialog(final String classId){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("该课程是付费课程，您尚未订阅")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("试看前几节")
                .setNegtive("返回")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",classId);
                        IntentUtils.getInstence().intent(EditClassActivity.this, SelectClassActivity.class,bundle);
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }
    private void loadDeleteDialog(final String classId){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("是否删除本课程，删除后不可恢复")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("取消")
                .setNegtive("删除")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        editClassPresenter.deleteClass(activitysharedPreferencesUtils.getParams("token","").toString(),classId);

                    }

                })
                .show();
    }
    @Override
    public void loadClassSuccess(ArrayList<SelectClass> selectClasses) {
        if (swpEdit.isRefreshing()){
            swpEdit.setRefreshing(false);
        }
        this.selectClasses = selectClasses;
        initAdapter();
    }

    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(EditClassActivity.this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void deleteClassSuccess(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
        editClassPresenter.getSelectClassInfo(bundle.getString("userId"));
        EventBus.getDefault().post(new UploadMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editClassPresenter.onDestroy();
    }

    @Override
    public void onRefresh() {
        editClassPresenter.getSelectClassInfo(bundle.getString("userId"));
    }
}
