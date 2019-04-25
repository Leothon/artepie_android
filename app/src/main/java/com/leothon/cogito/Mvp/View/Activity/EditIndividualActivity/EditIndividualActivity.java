package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;

import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.MD5Utils;
import com.leothon.cogito.Utils.PhotoUtils;
import com.leothon.cogito.Utils.UriPathUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.ActionSheetDialog;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    private int REQUEST_CODE_PERMISSION = 0x00099;
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
    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA,
    };
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


        icon = url;

        hideLoadingAnim();
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
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void updateSuccess() {
        MyToast.getInstance(this).show("资料修改成功",Toast.LENGTH_SHORT);
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
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
        isEdit = true;
    }

    @Override
    public void bindPhoneNumberFailed(String msg) {
        hideLoadingAnim();
        isEdit = false;
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
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
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
        isEdit = true;
    }

    @Override
    public void setPasswordFailed(String msg) {
        hideLoadingAnim();
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
        inputOldPassword.setText("");
        inputPassword.setText("");
    }

    @OnClick(R.id.edit_icon)
    public void editIcon(View view){

        requestCameraPermission(permissions,205);

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
                        if (inputPassword.getText().toString().length() < 6){
                            MyToast.getInstance(EditIndividualActivity.this).show("密码长度不够",Toast.LENGTH_SHORT);
                        }else {
                            String passwordEncrypt = MD5Utils.encrypt(inputPassword.getText().toString());
                            editInfoPresenter.setPassword(activitysharedPreferencesUtils.getParams("token","").toString(),passwordEncrypt);
                            nowPassword = passwordEncrypt;
                            showLoadingAnim();
                        }


                    }else {
                        MyToast.getInstance(EditIndividualActivity.this).show("请输入密码",Toast.LENGTH_SHORT);
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
                        MyToast.getInstance(EditIndividualActivity.this).show("请输入原密码和将设置的密码",Toast.LENGTH_SHORT);
                    }else {
                        if (inputPassword.getText().toString().length() < 6){
                            MyToast.getInstance(EditIndividualActivity.this).show("密码长度不够",Toast.LENGTH_SHORT);
                        }else {
                            String oldPasswordEncrypt = MD5Utils.encrypt(inputOldPassword.getText().toString());
                            String newPasswordEncrypt = MD5Utils.encrypt(inputPassword.getText().toString());
                            editInfoPresenter.changePassword(activitysharedPreferencesUtils.getParams("token","").toString(),oldPasswordEncrypt,newPasswordEncrypt);
                            nowPassword = newPasswordEncrypt;
                            showLoadingAnim();
                        }

                    }
                }
            });
        }


        alertDialogBuilder
                .setCancelable(false)

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
                                    MyToast.getInstance(EditIndividualActivity.this).show("请输入完整信息",Toast.LENGTH_SHORT);
                                }else {
                                    if (CommonUtils.isPhoneNumber(phoneNumberBind.getText().toString())){
                                        userNumber.setText(phoneNumberBind.getText().toString());
                                        editInfoPresenter.bindPhone(phoneNumberBind.getText().toString(),activitysharedPreferencesUtils.getParams("token","").toString());
                                        showLoadingAnim();
                                        dialog.cancel();
                                    }else {
                                        MyToast.getInstance(EditIndividualActivity.this).show("手机号码不合法",Toast.LENGTH_SHORT);
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

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_settext, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

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


        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == PhotoUtils.NONE)
            return;
        if (requestCode == PhotoUtils.PHOTOGRAPH) {
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

        if (requestCode == PhotoUtils.PHOTOZOOM) {

            path = PhotoUtils.getPath(this);// 生成一个地址用于存放剪辑后的图片
            if (TextUtils.isEmpty(path)) {
                Log.e(TAG, "随机生成的用于存放剪辑后的图片的地址失败");
                return;
            }
            Uri imageUri = UriPathUtils.getUri(this, path);
            PhotoUtils.startPhotoZoom(EditIndividualActivity.this, data.getData(), PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH, imageUri);
        }
        if (requestCode == PhotoUtils.PHOTORESOULT) {

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

    public void requestCameraPermission(String[] permissions,int requestCode){
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)){
            permissionCameraSuccess(REQUEST_CODE_PERMISSION);
        }else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this,needPermissions.toArray(new String[needPermissions.size()]),REQUEST_CODE_PERMISSION);

        }
    }

    /**
     * 检测是否获取到所有的权限
     */
    private boolean checkPermissions(String[] permissions){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }

        for(String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }

        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     */

    private List<String> getDeniedPermissions(String[] permissions){
        List<String> needRequestPermissionsList = new ArrayList<>();
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED || ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                needRequestPermissionsList.add(permission);
            }
        }
        return needRequestPermissionsList;
    }

    /**
     * 系统请求权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION){
            if (verifyPermissions(grantResults)){
                permissionCameraSuccess(REQUEST_CODE_PERMISSION);
            }else {
                permissionCameraFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }


    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }
    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    private void permissionCameraSuccess(int requestCode) {
        options();

    }

    /**
     * 权限获取失败
     * @param requestCode
     */
    private void permissionCameraFail(int requestCode) {

    }
}
