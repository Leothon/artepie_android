package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Constants;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.R;

import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.PhotoUtils;
import com.leothon.cogito.Utils.UriPathUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.Weight.ActionSheetDialog;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class EditIndividualActivity extends BaseActivity implements EditInfoContract.IEditInfoView {


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

    @BindView(R.id.edit_user_address)
    TextView userAddress;
    @BindView(R.id.edit_user_password)
    TextView userPassword;


    private String path = "";

    private int mYear;
    private int mMonth;
    private int mDay;

    private static final int NAME = 0;
    private static final int PHONE = 1;
    private static final int SIGNATRUE = 2;
    private static final int ADDRESS = 3;

    private String phone = "绑定手机号";
    private String signatrue = "输入签名，展示自己";
    private String address = "填写地址，发现同城好友";
    private String[] sexArray = new String[]{"暂不填写","女","男"};
    private boolean isEdit = false;
    private User userSend;
    private String icon;

    private UserEntity userInsert;
    private EditInfoPresenter editInfoPresenter;
    private UserEntity userEntity;

    ZLoadingDialog dialog = new ZLoadingDialog(EditIndividualActivity.this);
    @Override
    public int initLayout() {
        return R.layout.activity_edit_individual;
    }

    @Override
    public void initData() {
        editInfoPresenter = new EditInfoPresenter(this);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();

        userEntity = BaseApplication.getInstances().getDaoSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);

    }
    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("编辑个人信息");

        userName.setText(userEntity.getUser_name());
        if (userEntity.getUser_sex() == 1){
            userSex.setText("男");
        }else if (userEntity.getUser_sex() == 2){
            userSex.setText("女");
        }

        if (userEntity.getUser_birth() != null){
            userBirth.setText(userEntity.getUser_birth());
        }
        if (userEntity.getUser_phone() != null){
            phone = userEntity.getUser_phone();
        }
        if (userEntity.getUser_signal() != null){
            signatrue = userEntity.getUser_signal();
        }
        if (userEntity.getUser_address() != null){
            userAddress.setText(userEntity.getUser_address());
        }

        userNumber.setText(phone);
        userSignal.setText(signatrue);
        ImageLoader.loadImageViewThumbnailwitherror(this,userEntity.getUser_icon(),userIcon,R.drawable.defalutimg);
        Calendar nowdate = Calendar.getInstance();
        mYear = nowdate.get(Calendar.YEAR);
        mMonth = nowdate.get(Calendar.MONTH);
        mDay = nowdate.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void getIconUrl(String url) {


        icon = Api.ComUrl + "resource/" + url;

        uploadAll();

    }


    private void uploadAll(){

        userSend = new User();
        userInsert = new UserEntity();
        userInsert.setUser_id(userEntity.getUser_id());
        userSend.setUser_name(userName.getText().toString());
        userInsert.setUser_name(userName.getText().toString());
        if (userSex.getText().toString().equals("男")){
            userSend.setUser_sex(1);
            userInsert.setUser_sex(1);
        }else if (userSex.getText().toString().equals("女")){
            userSend.setUser_sex(2);
            userInsert.setUser_sex(2);
        }else {
            userSend.setUser_sex(0);
            userInsert.setUser_sex(0);
        }

        userSend.setUser_birth(userBirth.getText().toString());
        userInsert.setUser_birth(userBirth.getText().toString());
        userSend.setUser_phone(userNumber.getText().toString());
        userInsert.setUser_phone(userNumber.getText().toString());
        userSend.setUser_signal(userSignal.getText().toString());
        userInsert.setUser_signal(userSignal.getText().toString());
        userSend.setUser_address(userAddress.getText().toString());
        userInsert.setUser_address(userAddress.getText().toString());
        userSend.setUser_token(activitysharedPreferencesUtils.getParams("token","").toString());
        userInsert.setUser_token(activitysharedPreferencesUtils.getParams("token","").toString());

        if (icon == null || icon.equals("")){
            userSend.setUser_icon(userEntity.getUser_icon());
            userInsert.setUser_icon(userEntity.getUser_icon());
        }else {
            userSend.setUser_icon(icon);
            userInsert.setUser_icon(icon);
        }

        BaseApplication.getInstances().getDaoSession().update(userInsert);

        EventBus.getDefault().post(userSend);
        editInfoPresenter.updateUserInfo(userSend);

    }

    @Override
    public void showMsg(String msg) {
        CommonUtils.makeText(this,msg);
    }

    @Override
    public void updateSuccess() {
        CommonUtils.makeText(this,"资料修改成功");
        hideLoadingAnim();
        super.onBackPressed();
    }

    @OnClick(R.id.edit_icon)
    public void editIcon(View view){

        options();
    }
    @OnClick(R.id.edit_name)
    public void editName(View view){
        onTextEditDialog(NAME);
    }
    @OnClick(R.id.edit_sex)
    public void editSex(View view){
        showSexChooseDialog();
    }
    @OnClick(R.id.edit_birth)
    public void editBirth(View view){
        new DatePickerDialog(EditIndividualActivity.this, onDateSetListener,mYear,mMonth,mDay).show();
    }
    @OnClick(R.id.edit_phone)
    public void editPhone(View view){
        onTextEditDialog(PHONE);
    }
    @OnClick(R.id.edit_signal)
    public void editSignal(View view){
        onTextEditDialog(SIGNATRUE);
    }

    @OnClick(R.id.edit_address)
    public void editAddress(View view){
        onTextEditDialog(ADDRESS);
    }

    @OnClick(R.id.edit_password)
    public void editPassword(View view){
    }

    @OnClick(R.id.edit_person_info)
    public void sendEdit(View view){
        sendEdit();
    }

    public void sendEdit(){
        if (isEdit){

            //TODO 增加进度条

            showLoadingAnim();
            if (path != null && !path.equals("")){
                editInfoPresenter.updateUserIcon(path);
            }else {
                uploadAll();
            }



        }

//        super.onBackPressed();

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
                    public void onNegativeClick() {
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
                userInput.setText(userEntity.getUser_name());
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
                userInput.setText(userEntity.getUser_phone() + "");
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
                userInput.setText(userEntity.getUser_signal() + "");
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
            case ADDRESS:
                userInput.setFloatingLabelText("修改地址");
                userInput.setText(userEntity.getUser_address() + "");
                userInput.setHint("修改地址");
                // 设置Dialog按钮
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("确认修改",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // 获取edittext的内容,显示到textview
                                        if (!userInput.getText().toString().equals("")){
                                            address = userInput.getText().toString();
                                            userAddress.setText(address);
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
            default:
                break;

        }


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
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

            File imgfile = new File(path);
            Log.e(TAG, "地址是什么" + path);
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
    private void showLoadingAnim(){
        dialog.setLoadingBuilder(Z_TYPE.SEARCH_PATH)
                .setLoadingColor(Color.GRAY)
                .setHintText("请稍后...")
                .setHintTextSize(16)
                .setHintTextColor(Color.GRAY)
                .setDurationTime(0.5)
                .setDialogBackgroundColor(Color.parseColor("#ffffff")) // 设置背景色，默认白色
                .show();
    }

    private void hideLoadingAnim(){
        dialog.cancel();
    }

}
