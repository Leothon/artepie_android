package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.R;

import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.PhotoUtils;
import com.leothon.cogito.Utils.UriPathUtils;
import com.leothon.cogito.Weight.ActionSheetDialog;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;
import java.util.Calendar;

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
    @BindView(R.id.edit_user_signal)
    TextView userSignal;
    @BindView(R.id.edit_user_number)
    TextView userNumber;


    private String path;

    private int mYear;
    private int mMonth;
    private int mDay;

    private static final int NAME = 0;
    private static final int PHONE = 1;
    private static final int SIGNATRUE = 2;

    private String phone = "";
    private String signatrue = "";
    private String[] sexArray = new String[]{"暂不填写","女","男"};
    private boolean isEdit = false;

    @Override
    public int initLayout() {
        return R.layout.activity_edit_individual;
    }

    @Override
    public void initview() {
        setToolbarSubTitle("");
        setToolbarTitle("编辑个人信息");
        userName.setText("叶落知秋");
        userSex.setText("男");
        userBirth.setText("1998-8-8");
        phone = "13500713926";
        signatrue = "如鱼饮水，冷暖自知";
        userNumber.setText(phone);
        userSignal.setText(signatrue);
        ImageLoader.loadImageViewThumbnailwitherror(this,Constants.iconurl,userIcon,R.drawable.defalutimg);
        Calendar nowdate = Calendar.getInstance();
        mYear = nowdate.get(Calendar.YEAR);
        mMonth = nowdate.get(Calendar.MONTH);
        mDay = nowdate.get(Calendar.DAY_OF_MONTH);
    }

    @OnClick(R.id.edit_icon)
    public void editIcon(View view){
        //TODO 编辑用户头像

        options();
    }
    @OnClick(R.id.edit_name)
    public void editName(View view){
        //TODO 编辑用户名字
        onTextEditDialog(NAME);
    }
    @OnClick(R.id.edit_sex)
    public void editSex(View view){
        //TODO 编辑用户性别
        showSexChooseDialog();
    }
    @OnClick(R.id.edit_birth)
    public void editBirth(View view){
        //TODO 编辑用户生日
        new DatePickerDialog(EditIndividualActivity.this, onDateSetListener,mYear,mMonth,mDay).show();
    }
    @OnClick(R.id.edit_phone)
    public void editPhone(View view){
        //TODO 编辑用户电话
        onTextEditDialog(PHONE);
    }
    @OnClick(R.id.edit_signal)
    public void editSignal(View view){
        //TODO 修改签名
        onTextEditDialog(SIGNATRUE);
    }

    @OnClick(R.id.edit_person_info)
    public void sendEdit(View view){
        //TODO 发送信息修改
        sendEdit();
    }

    public void sendEdit(){
        if (isEdit){
            //TODO 网络请求发送信息
            Constants.isRefreshtrueaboutFragment = true;
            Log.e(TAG, "sendEdit: 修改了");
        }

        Bundle bundle = new Bundle();
        bundle.putString("type","about");
        IntentUtils.getInstence().intent(EditIndividualActivity.this, IndividualActivity.class,bundle);
        finish();
    }


    @Override
    public void onBackPressed() {
        if (isEdit){
            dialogLoading();
        }else {
            super.onBackPressed();
        }
    }

    private  void dialogLoading(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("个人信息已经修改，请点击完成进行保存")
                .setTitle("修改提示")
                .setSingle(false)
                .setNegtive("不保存")
                .setPositive("保存")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        sendEdit();
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();
                        finish();
                    }

                })
                .show();

    }
    /**
     * 显示日期对话框
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            String days;
            days = new StringBuffer().append(mYear).append("-").append(mMonth+1).append("-").append(mDay).toString();
            userBirth.setText(days);
            isEdit = true;
        }
    };

    /**
     * 显示性别对话框
     */
    private void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);// 自定义对话框
        builder3.setSingleChoiceItems(sexArray, 0, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                userSex.setText(sexArray[which]);
                isEdit = true;
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }

    /**
     * 文本编辑
     */
    private void onTextEditDialog(int type) {
        // 使用LayoutInflater来加载dialog_setname.xml布局


        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_settext, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        final MaterialEditText userInput = (MaterialEditText) nameView.findViewById(R.id.text_edit);


        switch (type){
            case NAME:
                userInput.setFloatingLabelText("修改姓名");
                userInput.setText("陈独秀");
                userInput.setHint("修改姓名");
                // 设置Dialog按钮
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("确认修改",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 获取edittext的内容,显示到textview
                                        if (!userInput.getText().toString().equals("")){
                                            userName.setText(userInput.getText());
                                            isEdit = true;
                                        }

                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                break;
            case PHONE:
                userInput.setFloatingLabelText("修改绑定号码");
                userInput.setText("13500713926");
                userInput.setHint("修改绑定号码");
                // 设置Dialog按钮
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("确认修改",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 获取edittext的内容,显示到textview
                                        if (!userInput.getText().toString().equals("")){
                                            phone = userInput.getText().toString();
                                            userNumber.setText(phone);
                                            isEdit = true;
                                        }

                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                break;
            case SIGNATRUE:
                userInput.setFloatingLabelText("修改签名");
                userInput.setText("我来自于神之殿堂");
                userInput.setHint("修改签名");
                // 设置Dialog按钮
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("确认修改",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 获取edittext的内容,显示到textview
                                        if (!userInput.getText().toString().equals("")){
                                            signatrue = userInput.getText().toString();
                                            userSignal.setText(signatrue);
                                            isEdit = true;
                                        }

                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                break;
            default:
                break;

        }


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void initdata() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == PhotoUtils.NONE)
            return;
        // 拍照
        if (requestCode == PhotoUtils.PHOTOGRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File picture = null;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                picture = new File(Environment.getExternalStorageDirectory() + PhotoUtils.imageName);
                if (!picture.exists()) {
                    picture = new File(Environment.getExternalStorageDirectory() + PhotoUtils.imageName);
                }
            } else {
                picture = new File(this.getFilesDir() + PhotoUtils.imageName);
                if (!picture.exists()) {
                    picture = new File(EditIndividualActivity.this.getFilesDir() + PhotoUtils.imageName);
                }
            }

            path = PhotoUtils.getPath(this);// 生成一个地址用于存放剪辑后的图片
            if (TextUtils.isEmpty(path)) {
                Log.e(TAG, "随机生成的用于存放剪辑后的图片的地址失败");
                return;
            }
            Uri imageUri = UriPathUtils.getUri(this, path);
            PhotoUtils.startPhotoZoom(EditIndividualActivity.this, Uri.fromFile(picture), PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH, imageUri);
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PhotoUtils.PHOTOZOOM) {

            path = PhotoUtils.getPath(this);// 生成一个地址用于存放剪辑后的图片
            if (TextUtils.isEmpty(path)) {
                Log.e(TAG, "随机生成的用于存放剪辑后的图片的地址失败");
                return;
            }
            Uri imageUri = UriPathUtils.getUri(this, path);
            PhotoUtils.startPhotoZoom(EditIndividualActivity.this, data.getData(), PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH, imageUri);
        }
        // 处理结果
        if (requestCode == PhotoUtils.PHOTORESOULT) {
            /**
             * 在这里处理剪辑结果，可以获取缩略图，获取剪辑图片的地址。得到这些信息可以选则用于上传图片等等操作
             * */

            /**
             * 如，根据path获取剪辑后的图片
             */
            Bitmap bitmap = PhotoUtils.convertToBitmap(path,PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH);
            if(bitmap != null){

                userIcon.setImageBitmap(bitmap);
                isEdit = true;

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void options() {
        ActionSheetDialog mDialog = new ActionSheetDialog(this).builder();
        mDialog.setTitle("选择");
        mDialog.setCancelable(false);
        mDialog.addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                PhotoUtils.photograph(EditIndividualActivity.this);
            }
        }).addSheetItem("从相册选取", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                PhotoUtils.selectPictureFromAlbum(EditIndividualActivity.this);
            }
        }).show();
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
