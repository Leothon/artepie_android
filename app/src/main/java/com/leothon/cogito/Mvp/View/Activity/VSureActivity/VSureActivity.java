package com.leothon.cogito.Mvp.View.Activity.VSureActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.AuthInfo;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Constants;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class VSureActivity extends BaseActivity implements VSureContract.IVSureView {


    @BindView(R.id.v_sure_icon)
    RoundedImageView vSureIcon;
    @BindView(R.id.v_sure_name)
    TextView vSureName;
    @BindView(R.id.v_sure_signal)
    TextView vSureSignal;
    @BindView(R.id.v_sure_des)
    TextView vSureDes;
    @BindView(R.id.ensure_img_v_sure)
    ImageView ensureImg;
    @BindView(R.id.delete_ensure_img)
    RoundedImageView deleteEnsureImg;
    @BindView(R.id.v_sure_info)
    MaterialEditText vSureInfo;
    @BindView(R.id.send_v_sure)
    TextView sendVSure;

    @BindView(R.id.upload_img_v_sure)
    RelativeLayout uploadImgRL;
    @BindView(R.id.auth_schedule)
    RelativeLayout authSchedule;

    @BindView(R.id.auth_info_name)
    TextView authInfoName;
    @BindView(R.id.auth_info_user_id)
    TextView authInfoUserId;
    @BindView(R.id.auth_info_time)
    TextView authInfoTime;
    @BindView(R.id.auth_info_content)
    TextView authInfoContent;
    @BindView(R.id.auth_info_schedule)
    TextView authInfoSchedule;
    @BindView(R.id.auth_info_schedule_mark)
    ImageView authInfoMark;
    @BindView(R.id.auth_info_advice)
    TextView authAdvice;

    @BindView(R.id.re_auth_btn)
    Button reAuthBtn;



    private VSurePresenter vSurePresenter;

    private AuthInfo authInfo;



    private UserEntity userEntity;
    private boolean isImgShow = false;

    private String url;
    ZLoadingDialog dialog = new ZLoadingDialog(VSureActivity.this);
    private String path;
    @Override
    public int initLayout() {
        return R.layout.activity_vsure;
    }

    @Override
    public void initData() {
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();
        vSurePresenter = new VSurePresenter(this);
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);

    }
    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("认证身份");
        ImageLoader.loadImageViewThumbnailwitherror(this, userEntity.getUser_icon(),vSureIcon,R.drawable.defalutimg);
        vSureName.setText(userEntity.getUser_name());

        vSureSignal.setText(userEntity.getUser_signal());
        showLoadingAnim();
        vSurePresenter.getAuthInfo(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    @OnClick(R.id.upload_img_v_sure)
    public void choosefile(View view){
        //TODO 选择图片
        chooseImg();
    }

    @Override
    public void sendSuccess(String msg) {
        CommonUtils.makeText(this,msg);
        vSurePresenter.getAuthInfo(activitysharedPreferencesUtils.getParams("token","").toString());
        vSurePresenter.getUserInfo(activitysharedPreferencesUtils.getParams("token","").toString());

    }

    @Override
    public void uploadImgSuccess(String msg) {
        hideLoadingAnim();
        url = Api.ComUrl + "resource/" + msg;
    }

    @Override
    public void getUserInfoSuccess(User user) {
        hideLoadingAnim();

        UserEntity userEntity = new UserEntity(user.getUser_id(),user.getUser_name(),user.getUser_icon(),user.getUser_birth(),user.getUser_sex(),user.getUser_signal(),user.getUser_address(),user.getUser_password(),user.getUser_token(),user.getUser_status(),user.getUser_register_time(),user.getUser_register_ip(),user.getUser_lastlogin_time(),user.getUser_phone(),user.getUser_role(),user.getUser_balance(),user.getUser_art_coin());
        getDAOSession().update(userEntity);
        EventBus.getDefault().post(user);

    }

    @Override
    public void getInfoSuccess(ArrayList<AuthInfo> authInfos) {
        hideLoadingAnim();

        authInfo = new AuthInfo();
        this.authInfo = authInfos.get(0);
        if (authInfo == null){
            uploadImgRL.setVisibility(View.VISIBLE);
            authSchedule.setVisibility(View.GONE);
            vSureInfo.setVisibility(View.VISIBLE);
        }else {
            if (authInfo.getAuthStatus() == 2){
                uploadImgRL.setVisibility(View.GONE);
                authSchedule.setVisibility(View.VISIBLE);
                reAuthBtn.setVisibility(View.VISIBLE);
                vSureInfo.setVisibility(View.GONE);
                authInfoName.setText("认证人 ： " + userEntity.getUser_name());
                authInfoUserId.setText("认证人ID : " + authInfo.getAuthUserId());
                authInfoTime.setText("认证提交时间 ： " + authInfo.getAuthTime());
                authInfoContent.setText("认证内容 ： " + authInfo.getAuthContent());
                authInfoSchedule.setText("认证进度 : 认证失败！");
                authInfoMark.setImageResource(R.drawable.unpass);
                authAdvice.setText("认证建议 : " + authInfo.getAuthAdvice());
                reAuthBtn.setVisibility(View.VISIBLE);
            }else if (authInfo.getAuthStatus() == 1){
                vSurePresenter.getUserInfo(activitysharedPreferencesUtils.getParams("token","").toString());
                uploadImgRL.setVisibility(View.GONE);
                authSchedule.setVisibility(View.VISIBLE);
                reAuthBtn.setVisibility(View.VISIBLE);

                authInfoName.setText("认证人 ： " + userEntity.getUser_name());
                authInfoUserId.setText("认证人ID : " + authInfo.getAuthUserId());
                authInfoTime.setText("认证提交时间 ： " + authInfo.getAuthTime());
                authInfoContent.setText("认证内容 ： " + authInfo.getAuthContent());
                authInfoSchedule.setText("认证进度 : 认证成功！");
                authInfoMark.setImageResource(R.drawable.pass);
                authAdvice.setText("认证建议 : " + authInfo.getAuthAdvice());
                vSureInfo.setVisibility(View.GONE);
            }else {
                uploadImgRL.setVisibility(View.GONE);
                authSchedule.setVisibility(View.VISIBLE);
                vSureInfo.setVisibility(View.GONE);
                authInfoName.setText("认证人 ： " + userEntity.getUser_name());
                authInfoUserId.setText("认证人ID : " + authInfo.getAuthUserId());
                authInfoTime.setText("认证提交时间 ： " + authInfo.getAuthTime());
                authInfoContent.setText("认证内容 ： " + authInfo.getAuthContent());
                authInfoSchedule.setText("认证进度 : 待审核！");
                authInfoMark.setImageResource(R.drawable.unpass);
                authAdvice.setText("认证建议 : " + authInfo.getAuthAdvice());
                reAuthBtn.setVisibility(View.GONE);
            }

        }


    }


    @OnClick(R.id.re_auth_btn)
    public void reAuth(View view){
        uploadImgRL.setVisibility(View.VISIBLE);
        authSchedule.setVisibility(View.GONE);
        vSureInfo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInfo(String msg) {
        CommonUtils.makeText(this,msg);
    }

    @OnClick(R.id.send_v_sure)
    public void sendVSure(View view){

        if (!vSureInfo.getText().toString().equals("") && isImgShow){
            if (url != null){
                showLoadingAnim();
                vSurePresenter.sendAuthInfo(activitysharedPreferencesUtils.getParams("token","").toString(),url,vSureInfo.getText().toString());
            }else {
                CommonUtils.makeText(this,"图片未上传请重试");
            }

        }else {
            CommonUtils.makeText(this,"请上传图片，填写信息");
        }

    }

    @OnClick(R.id.delete_ensure_img)
    public void deleteEnsureImg(View view){
        ensureImg.setVisibility(View.GONE);
        deleteEnsureImg.setVisibility(View.GONE);
        vSureDes.setVisibility(View.VISIBLE);
        isImgShow = false;
        sendVSure.setBackgroundColor(getResources().getColor(R.color.contentColor));
        sendVSure.setTextColor(getResources().getColor(R.color.white));
    }

    protected void chooseImg() {
        ActionSheetDialog mDialog = new ActionSheetDialog(this).builder();
        mDialog.setTitle("选择");
        mDialog.setCancelable(false);
        mDialog.addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                PhotoUtils.photograph(VSureActivity.this);
            }
        }).addSheetItem("从相册选取", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                PhotoUtils.selectPictureFromAlbum(VSureActivity.this);
            }
        }).show();
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
                    picture = new File(VSureActivity.this.getFilesDir() + PhotoUtils.imageName);
                }
            }

            path = PhotoUtils.getPath(this);// 生成一个地址用于存放剪辑后的图片
            if (TextUtils.isEmpty(path)) {
                Log.e(TAG, "随机生成的用于存放剪辑后的图片的地址失败");
                return;
            }
            Uri imageUri = UriPathUtils.getUri(this, path);
            PhotoUtils.startPhotoZoom(VSureActivity.this, Uri.fromFile(picture), PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH, imageUri);
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
            PhotoUtils.startPhotoZoom(VSureActivity.this, data.getData(), PhotoUtils.PICTURE_HEIGHT, PhotoUtils.PICTURE_WIDTH, imageUri);
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
                ensureImg.setVisibility(View.VISIBLE);
                deleteEnsureImg.setVisibility(View.VISIBLE);
                sendVSure.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ensureImg.setImageBitmap(bitmap);
                vSureDes.setVisibility(View.GONE);
                isImgShow = true;
            }
            File file = new File(path);
            showLoadingAnim();
            vSurePresenter.uploadAuthImg(file);

        }
        super.onActivityResult(requestCode, resultCode, data);
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
        vSurePresenter.onDestroy();
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
