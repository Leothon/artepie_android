package com.leothon.cogito.Mvp.View.Activity.VSureActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.PhotoUtils;
import com.leothon.cogito.Utils.UriPathUtils;
import com.leothon.cogito.Weight.ActionSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class VSureActivity extends BaseActivity {


    @BindView(R.id.v_sure_icon)
    RoundedImageView vSureIcon;
    @BindView(R.id.v_sure_name)
    TextView vSureName;
    @BindView(R.id.v_sure_signal)
    TextView vSureSignal;
    @BindView(R.id.upload_img_v_sure)
    RelativeLayout uploadImgVSure;
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

    private boolean isImgShow = false;

    private String path;
    @Override
    public int initLayout() {
        return R.layout.activity_vsure;
    }

    @Override
    public void initview() {
        setToolbarSubTitle("");
        setToolbarTitle("认证身份");
        ImageLoader.loadImageViewThumbnailwitherror(this, Constants.iconurl,vSureIcon,R.drawable.defalutimg);
        vSureName.setText("叶落知秋");
        vSureSignal.setText("如鱼饮水，冷暖自知");
    }

    @OnClick(R.id.upload_img_v_sure)
    public void choosefile(View view){
        //TODO 选择图片
        chooseImg();
    }

    @OnClick(R.id.send_v_sure)
    public void sendVSure(View view){

        if (!vSureInfo.getText().toString().equals("") && isImgShow){
            //TODO 上传相关证件
            Bundle bundleto = new Bundle();
            bundleto.putString("type","individual");
            IntentUtils.getInstence().intent(VSureActivity.this, IndividualActivity.class,bundleto);
            finish();
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

        }
        super.onActivityResult(requestCode, resultCode, data);
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
