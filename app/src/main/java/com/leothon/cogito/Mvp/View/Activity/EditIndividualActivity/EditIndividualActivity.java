package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;

import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

public class EditIndividualActivity extends BaseActivity {


    @BindView(R.id.edit_user_icon)
    RoundedImageView userIcon;
    @BindView(R.id.edit_user_name)
    TextView userName;
    @BindView(R.id.edit_user_sex)
    TextView userSex;
    @BindView(R.id.edit_user_birth)
    TextView userBirth;


    @Override
    public int initLayout() {
        return R.layout.activity_edit_individual;
    }

    @Override
    public void initview() {
        setToolbarSubTitle("");
        setToolbarTitle("编辑个人信息");
        userName.setText("陈独秀");
        userSex.setText("男");
        userBirth.setText("1998-8-8");
        userIcon.setImageResource(R.drawable.wanghongwei);
    }

    @OnClick(R.id.edit_icon)
    public void editIcon(View view){
        //TODO 编辑用户头像
    }
    @OnClick(R.id.edit_name)
    public void editName(View view){
        //TODO 编辑用户名字
    }
    @OnClick(R.id.edit_sex)
    public void editSex(View view){
        //TODO 编辑用户性别
    }
    @OnClick(R.id.edit_birth)
    public void editBirth(View view){
        //TODO 编辑用户生日
    }
    @OnClick(R.id.edit_phone)
    public void editPhone(View view){
        //TODO 编辑用户电话
    }
    @OnClick(R.id.edit_signal)
    public void editSignal(View view){
        //TODO 修改签名
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
