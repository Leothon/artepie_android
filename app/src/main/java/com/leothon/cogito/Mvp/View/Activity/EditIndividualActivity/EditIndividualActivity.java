package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.danikula.videocache.file.Md5FileNameGenerator;
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
import com.leothon.cogito.Utils.MD5Utils;
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
    public int T = 60;
    private String phone = "绑定手机号";
    private String signatrue = "输入签名，展示自己";
    private String address = "填写地址，发现同城好友";
    private String[] sexArray = new String[]{"暂不填写","女","男"};
    private boolean isEdit = false;
    private User userSend;
    private String icon;

    private Handler mHandler = new Handler();

    private UserEntity userInsert;
    private EditInfoPresenter editInfoPresenter;
    private UserEntity userEntity;
    private MaterialEditText phoneNumberBind;
    private Button getVerifyCodeBtn;
    private MaterialEditText verifyCodeBind;

    private TextView passwordTitle;
    private MaterialEditText inputOldPassword;
    private MaterialEditText inputPassword;
    private Button sendPassword;

    private String nowPassword;
    private String password = "";
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

        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);

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

        if (!password.equals("")){
            userSend.setUser_password(password);
            userInsert.setUser_password(password);
        }else {
            userSend.setUser_password("");
            userInsert.setUser_password("");
        }

        userSend.setUser_role(userEntity.getUser_role());
        userInsert.setUser_role(userEntity.getUser_role());

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

        getDAOSession().update(userInsert);

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

    @Override
    public void checkNumberResult(String msg) {
        hideLoadingAnim();
        if (msg.equals("yes")){
            phoneNumberBind.setFloatingLabelText("该号码已被其他账号绑定，如果您确定绑定，则会导致那个账号信息丢失");
        }else {
            phoneNumberBind.setFloatingLabelText("该号码可以被绑定");
        }
    }

    @Override
    public void bindPhoneNumberSuccess(String msg) {
        hideLoadingAnim();
        CommonUtils.makeText(this,msg);
        isEdit = true;
    }

    @Override
    public void bindPhoneNumberFailed(String msg) {
        hideLoadingAnim();
        isEdit = false;
        CommonUtils.makeText(this,msg);
        phoneNumberBind.setText("");
        verifyCodeBind.setText("");
    }

    @Override
    public void verifyCodeSuccess(String msg) {
        hideLoadingAnim();
        verifyCodeBind.setText(msg);
    }

    @Override
    public void setPasswordSuccess(String msg) {
        hideLoadingAnim();
        password = nowPassword;
        CommonUtils.makeText(this,msg);
        isEdit = true;
    }

    @Override
    public void setPasswordFailed(String msg) {
        hideLoadingAnim();
        CommonUtils.makeText(this,msg);
        inputOldPassword.setText("");
        inputPassword.setText("");
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
        onPhoneEditDialog();
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
        onPasswordEditDialog();
    }

    @OnClick(R.id.edit_person_info)
    public void sendEdit(View view){
        sendEdit();
    }

    public void sendEdit(){
        if (isEdit){

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

    private void onPasswordEditDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_password_bind, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);
        passwordTitle = (TextView)nameView.findViewById(R.id.password_bind_title);
        inputOldPassword = (MaterialEditText) nameView.findViewById(R.id.input_old_password);
        inputPassword = (MaterialEditText)nameView.findViewById(R.id.input_password);
        sendPassword = (Button)nameView.findViewById(R.id.send_password);


        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String checkResult = CommonUtils.checkPassword(editable.toString());
                if (checkResult.equals("弱")){
                    inputPassword.setFloatingLabelText("密码强度弱，不安全");
                    inputPassword.setFloatingLabelTextColor(Color.parseColor("#db4437"));
                }else if (checkResult.equals("中")){
                    inputPassword.setFloatingLabelText("密码强度中");
                    inputPassword.setFloatingLabelTextColor(Color.parseColor("#FFFFCC00"));
                }else if (checkResult.equals("强")){
                    inputPassword.setFloatingLabelText("密码强度强");
                    inputPassword.setFloatingLabelTextColor(Color.parseColor("#FF02C602"));
                }else {
                    inputPassword.setFloatingLabelText("测试");
                }
            }
        });
        if (userEntity.getUser_password().equals("")){
            passwordTitle.setText("设置密码");
            inputOldPassword.setVisibility(View.GONE);
            sendPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!inputPassword.getText().toString().equals("")){
                        String passwordEncrypt = MD5Utils.encrypt(inputPassword.getText().toString());
                        editInfoPresenter.setPassword(activitysharedPreferencesUtils.getParams("token","").toString(),passwordEncrypt);
                        nowPassword = passwordEncrypt;
                        showLoadingAnim();

                    }else {
                        CommonUtils.makeText(EditIndividualActivity.this,"请输入密码");
                    }


                }
            });
        }else {
            passwordTitle.setText("修改密码");
            inputOldPassword.setVisibility(View.VISIBLE);
            sendPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputPassword.getText().toString().equals("") || inputOldPassword.getText().toString().equals("")){
                        CommonUtils.makeText(EditIndividualActivity.this,"请输入原密码和将设置的密码");
                    }else {
                        String oldPasswordEncrypt = MD5Utils.encrypt(inputOldPassword.getText().toString());
                        String newPasswordEncrypt = MD5Utils.encrypt(inputPassword.getText().toString());
                        editInfoPresenter.changePassword(activitysharedPreferencesUtils.getParams("token","").toString(),oldPasswordEncrypt,newPasswordEncrypt);
                        nowPassword = newPasswordEncrypt;
                        showLoadingAnim();
                    }
                }
            });
        }


        alertDialogBuilder
                .setCancelable(false)
//                .setPositiveButton("确认绑定",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // 获取edittext的内容,显示到textview
//
//                                if (phoneNumberBind.getText().toString().equals("") || verifyCodeBind.getText().toString().equals("")){
//                                    CommonUtils.makeText(EditIndividualActivity.this,"请输入完整信息");
//                                }else {
//                                    if (CommonUtils.isPhoneNumber(phoneNumberBind.getText().toString())){
//                                        userNumber.setText(phoneNumberBind.getText().toString());
//                                        editInfoPresenter.bindPhone(phoneNumberBind.getText().toString(),activitysharedPreferencesUtils.getParams("token","").toString());
//                                        showLoadingAnim();
//                                        dialog.cancel();
//                                    }else {
//                                        CommonUtils.makeText(EditIndividualActivity.this,"手机号码不合法");
//                                    }
//
//                                }
//
//                            }
//                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private void onPhoneEditDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_phone_bind, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        phoneNumberBind = (MaterialEditText) nameView.findViewById(R.id.bind_phone_number);
        verifyCodeBind = (MaterialEditText)nameView.findViewById(R.id.verify_code_bind);
        getVerifyCodeBtn = (Button)nameView.findViewById(R.id.get_verify_code_bind);

        phoneNumberBind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 11){
                    getVerifyCodeBtn.setEnabled(true);
                    getVerifyCodeBtn.setBackground(getResources().getDrawable(R.drawable.btnbackground));
                    editInfoPresenter.checkPhoneNumberIsExits(phoneNumberBind.getText().toString());
                    showLoadingAnim();
                }else {
                    getVerifyCodeBtn.setEnabled(false);
                    getVerifyCodeBtn.setBackground(getResources().getDrawable(R.drawable.btnenableback));
                }
            }
        });

        getVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MyCountDownTimer()).start();
                editInfoPresenter.verifyPhoneNumber(phoneNumberBind.getText().toString());
                showLoadingAnim();
            }
        });
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("确认绑定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview

                                if (phoneNumberBind.getText().toString().equals("") || verifyCodeBind.getText().toString().equals("")){
                                    CommonUtils.makeText(EditIndividualActivity.this,"请输入完整信息");
                                }else {
                                    if (CommonUtils.isPhoneNumber(phoneNumberBind.getText().toString())){
                                        userNumber.setText(phoneNumberBind.getText().toString());
                                        editInfoPresenter.bindPhone(phoneNumberBind.getText().toString(),activitysharedPreferencesUtils.getParams("token","").toString());
                                        showLoadingAnim();
                                        dialog.cancel();
                                    }else {
                                        CommonUtils.makeText(EditIndividualActivity.this,"手机号码不合法");
                                    }

                                }

                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
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
//            case PHONE:
//                userInput.setFloatingLabelText("修改绑定号码");
//                userInput.setText(userEntity.getUser_phone() + "");
//                userInput.setHint("修改绑定号码");
//                // 设置Dialog按钮
//                alertDialogBuilder
//                        .setCancelable(false)
//                        .setPositiveButton("确认修改",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        // 获取edittext的内容,显示到textview
//                                        if (!userInput.getText().toString().equals("")){
//                                            phone = userInput.getText().toString();
//                                            userNumber.setText(phone);
//                                            isEdit = true;
//                                        }
//
//                                    }
//                                })
//                        .setNegativeButton("取消",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//                break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editInfoPresenter.onDestroy();
    }

    class MyCountDownTimer implements Runnable {

        @Override
        public void run() {

            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getVerifyCodeBtn.setClickable(false);
                        getVerifyCodeBtn.setText(T + "秒后重新获取");
                        getVerifyCodeBtn.setBackground(getResources().getDrawable(R.drawable.btnenableback));
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (phoneNumberBind.getEditableText().toString().length() == 11){
                        getVerifyCodeBtn.setClickable(true);
                        getVerifyCodeBtn.setText("获取验证码");
                        getVerifyCodeBtn.setBackground(getResources().getDrawable(R.drawable.btnbackground));
                    }else {
                        getVerifyCodeBtn.setClickable(false);
                        getVerifyCodeBtn.setText("获取验证码");
                        getVerifyCodeBtn.setBackground(getResources().getDrawable(R.drawable.btnenableback));
                    }

                }
            });
            T = 60; //最后再恢复倒计时时长
        }
    }
}
