package com.leothon.cogito.Mvp.View.Activity.UploadClassActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.leothon.cogito.Adapter.UploadClassAdapter;
import com.leothon.cogito.Adapter.WalletAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Message.UploadMessage;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadDetailClassActivity.UploadClassDetailActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.ImageUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zyao89.view.zloading.ZLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UploadClassActivity extends BaseActivity implements UploadClassContract.IUploadClassView {

    @BindView(R.id.title_class_upload)
    MaterialEditText titleClassUpload;
    @BindView(R.id.desc_class_upload)
    MaterialEditText descClassUpload;



    @BindView(R.id.cover_show)
    ImageView uploadCoverImg;
    @BindView(R.id.cover_pall_view)
    View coverPall;
    @BindView(R.id.upload_cover_btn)
    RelativeLayout uploadBtn;
    @BindView(R.id.upload_class_cover)
    RelativeLayout addCoverRl;
    @BindView(R.id.is_free)
    Switch isFree;
    @BindView(R.id.add_price_rl)
    RelativeLayout addPriceRl;
    @BindView(R.id.upload_class_price)
    MaterialEditText uploadClassPrice;
    @BindView(R.id.rv_type_upload_class)
    RecyclerView rvType;
    ZLoadingDialog dialog = new ZLoadingDialog(UploadClassActivity.this);

    private UploadClassAdapter walletAdapter;
    private ArrayList<String> list;

    private String chooseType = "";

    private String filePath = "";

    private UploadClassPresenter uploadClassPresenter;
    private UserEntity userEntity;


    private SelectClass editSelectClass;
    private Intent intent;
    private Bundle bundle;

    @Override
    public int initLayout() {
        return R.layout.activity_upload_class;
    }

    @Override
    public void initView() {
        if (bundle.get("type").equals("create")){
            setToolbarTitle("新建课程");
            setToolbarSubTitle("创建完成");


        }else {
            setToolbarTitle("编辑课程");
            setToolbarSubTitle("编辑完成");
            showLoadingAnim();
            uploadClassPresenter.getClassInfo(bundle.getString("classId"));
        }

        getToolbarSubTitle().setTextColor(Color.parseColor("#db4437"));
        loadPrice();
        initAdapter();


        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bundle.get("type").equals("create")){
                    if (!filePath.equals("")
                            && !titleClassUpload.getText().toString().equals("")
                            && !descClassUpload.getText().toString().equals("")
                            && !chooseType.equals("")){


                        showLoadingAnim();

                        uploadClassPresenter.uploadImg(filePath);
                    }else {
                        MyToast.getInstance(UploadClassActivity.this).show("请填写完整信息后上传",Toast.LENGTH_SHORT);
                    }
                }else {
                    if (!titleClassUpload.getText().toString().equals("")
                            && !descClassUpload.getText().toString().equals("")){


                        showLoadingAnim();

                        if (filePath.equals("")){
                            SelectClass selectClass = new SelectClass();
                            selectClass.setSelectId(editSelectClass.getSelectId());
                            selectClass.setSelectbackimg(editSelectClass.getSelectbackimg());
                            selectClass.setSelectdesc(descClassUpload.getText().toString());
                            selectClass.setSelectlisttitle(titleClassUpload.getText().toString());
                            selectClass.setSelectprice(uploadClassPrice.getText().toString());
                            if (chooseType.equals("")){
                                selectClass.setType(editSelectClass.getType());
                            }else {
                                selectClass.setType(chooseType);
                            }

                            uploadClassPresenter.editClassInfo(selectClass);
                        }else {
                            uploadClassPresenter.uploadImg(filePath);
                        }

                    }else {
                        MyToast.getInstance(UploadClassActivity.this).show("请填写完整信息后上传",Toast.LENGTH_SHORT);
                    }
                }
            }
        });

        isFree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    addPriceRl.setVisibility(View.VISIBLE);
                }else {
                    addPriceRl.setVisibility(View.GONE);
                    uploadClassPrice.setText("");
                }
            }
        });
    }




    @OnClick(R.id.upload_class_cover)
    public void uploadClassCover(View view){
        PictureSelector.create(UploadClassActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                //.maxSelectNum(1)// 最大图片选择数量 int
                //.minSelectNum()// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                //.previewVideo(true)// 是否可预览视频 true or false
                //.enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                //.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(4,3)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                //.circleDimmedLayer()// 是否圆形裁剪 true or false
                //.showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                //.showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                //.openClickSound()// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                //.previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                //.rotateEnabled() // 裁剪是否可旋转图片 true or false
                //.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
                //.videoQuality(1)// 视频录制质量 0 or 1 int
                //.videoMaxSecond(1800)// 显示多少秒以内的视频or音频也可适用 int
                //.videoMinSecond(1800)// 显示多少秒以内的视频or音频也可适用 int
                //.recordVideoSecond(1800)//视频秒数录制 默认60s int
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == PictureConfig.CHOOSE_REQUEST){
                // 图片、视频、音频选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                filePath = null;

                if (selectList.get(0).isCompressed()){
                    filePath = selectList.get(0).getCompressPath();
                }else {
                    filePath = selectList.get(0).getPath();
                }
                uploadCoverImg.setImageBitmap(ImageUtils.getImageBitmap(filePath));

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed() {

        loadDialog(bundle.get("type").toString());



    }

    private void loadDialog(String type){
        final CommonDialog dialog = new CommonDialog(this);

        if (type.equals("create")){
            dialog.setPositive("继续创建")
                    .setNegtive("不创建")
                    .setMessage("取消创建课程？");

        }else {
            dialog.setPositive("继续编辑")
                    .setNegtive("不编辑")
                    .setMessage("取消编辑课程？");
        }

        dialog.setTitle("提醒")
                .setSingle(false)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();


                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        UploadClassActivity.super.onBackPressed();
                    }

                })
                .show();
    }

    private void loadSuccessDialog(final String classId, final String title){
        final CommonDialog dialog = new CommonDialog(this);

        dialog.setCancelable(false);

        dialog.setMessage("是否立即上传课程内容？您可以在我的课程进行更新课程内容，编辑课程等操作")
                .setTitle("创建成功")
                .setSingle(false)
                .setNegtive("不上传")

                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();

                        Bundle bundleto = new Bundle();
                        bundleto.putString("classId",classId);
                        bundleto.putString("title",title);
                        IntentUtils.getInstence().intent(UploadClassActivity.this,UploadClassDetailActivity.class,bundleto);

                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        UploadClassActivity.super.onBackPressed();
                    }

                })

                .show();
    }



    @Override
    public void initData() {

        intent = getIntent();
        bundle = intent.getExtras();
        uploadClassPresenter = new UploadClassPresenter(this);
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?", tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString()).getUid()).get(0);

    }




    private void initAdapter(){
        walletAdapter = new UploadClassAdapter(list,this);
        rvType.setHasFixedSize(true);
        rvType.setLayoutManager(new GridLayoutManager(this,4,GridLayoutManager.VERTICAL,false));
        rvType.setAdapter(walletAdapter);
        walletAdapter.setClassTypeOnClickListener(new UploadClassAdapter.ClassTypeOnClickListener() {
            @Override
            public void classTypeClickListener(String type) {
                chooseType = type;
            }
        });

    }

    public void loadPrice() {
        list = new ArrayList<>();
        list.add("民族");
        list.add("美声");
        list.add("古典");
        list.add("戏曲");
        list.add("原生态");
        list.add("民谣");
        list.add("通俗");
        list.add("其他");
    }

    @Override
    public void imgSendSuccess(String msg) {
        String url =  msg;

        if (bundle.get("type").equals("create")){
            SelectClass selectClass = new SelectClass();
            selectClass.setSelectauthor(userEntity.getUser_name());
            selectClass.setSelectauthordes(userEntity.getUser_role().substring(1));
            selectClass.setSelectauthorid(userEntity.getUser_id());
            selectClass.setSelectbackimg(url);
            selectClass.setSelectdesc(descClassUpload.getText().toString());
            selectClass.setSelectlisttitle(titleClassUpload.getText().toString());
            selectClass.setSelectprice(uploadClassPrice.getText().toString());
            selectClass.setType(chooseType);
            uploadClassPresenter.createClass(selectClass);
        }else {
            SelectClass selectClass = new SelectClass();
            selectClass.setSelectbackimg(url);
            selectClass.setSelectId(editSelectClass.getSelectId());
            selectClass.setSelectdesc(descClassUpload.getText().toString());
            selectClass.setSelectlisttitle(titleClassUpload.getText().toString());
            selectClass.setSelectprice(uploadClassPrice.getText().toString());
            if (chooseType.equals("")){
                selectClass.setType(editSelectClass.getType());
            }else {
                selectClass.setType(chooseType);
            }

            uploadClassPresenter.editClassInfo(selectClass);
        }

    }

    @Override
    public void createSuccess(String msg) {
        hideLoadingAnim();
        loadSuccessDialog(msg,titleClassUpload.getText().toString());
        EventBus.getDefault().post(new UploadMessage());
    }

    @Override
    public void getClassSuccess(SelectClass selectClass) {
        this.editSelectClass = selectClass;
        hideLoadingAnim();
        ImageLoader.loadImageViewThumbnailwitherror(this,selectClass.getSelectbackimg(),uploadCoverImg,R.drawable.defalutimg);
        titleClassUpload.setText(selectClass.getSelectlisttitle());
        descClassUpload.setText(selectClass.getSelectdesc());
        if (selectClass.getSelectprice().equals("")){
            isFree.setChecked(false);
        }else {
            isFree.setChecked(true);
            addPriceRl.setVisibility(View.VISIBLE);
            uploadClassPrice.setText(selectClass.getSelectprice());
        }

    }

    @Override
    public void editClassSuccess(String msg) {

        hideLoadingAnim();
        loadSuccessDialog(msg,titleClassUpload.getText().toString());
    }

    @Override
    public void showInfo(String msg) {

        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uploadClassPresenter.onDestroy();
    }
}
