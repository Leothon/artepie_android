package com.leothon.cogito.Mvp.View.Activity.UploadDetailClassActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.numberprogressbar.NumberProgressBar;
import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.OssUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.View.ProgressView;
import com.leothon.cogito.Weight.CommonDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class UploadClassDetailActivity extends BaseActivity implements UploadClassDetailContract.IUploadClassDetailView {


    @BindView(R.id.title_sub_class_detail)
    MaterialEditText titleClassDetail;
    @BindView(R.id.content_class_detail)
    MaterialEditText contentClassDetail;
    @BindView(R.id.video_audio_layout)
    RelativeLayout videoAudioLayout;
    @BindView(R.id.video_class_cover)
    ImageView uploadVideo;
    @BindView(R.id.add_class_video)
    ImageView addVideo;

    @BindView(R.id.progress_upload_class)
    NumberProgressBar progressBar;

    private Intent intent;
    private Bundle bundle;
    DecimalFormat df = new DecimalFormat("0.00");

    private String path = "";

    private String coverUrl = "";
    private boolean isHasVideo = false;
    private String duration = "0";
    @Override
    public int initLayout() {
        return R.layout.activity_upload_class_detail;
    }

    private UploadClassDetailPresenter uploadClassDetailPresenter;
    @Override
    public void initData() {
        uploadClassDetailPresenter = new UploadClassDetailPresenter(this);
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("上传该节课程");
        setToolbarTitle(bundle.getString("title"));
        getToolbarSubTitle().setTextColor(Color.parseColor("#db4437"));

        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 上传课程
                if (!titleClassDetail.getText().toString().equals("") && !contentClassDetail.getText().toString().equals("") && !path.equals("")){
                    //progressShow.setVisibility(View.VISIBLE);
                    //showLoadingAnim();
                    progressBar.setVisibility(View.VISIBLE);
                    uploadClassVideo(path);
                    //uploadClassDetailPresenter.uploadVideo(path);
                }else {
                    MyToast.getInstance(UploadClassDetailActivity.this).show("请完成所有内容后上传",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @OnClick(R.id.video_audio_layout)
    public void chooseClass(View view){
        if (isHasVideo){
            PictureSelector.create(UploadClassDetailActivity.this).externalPictureVideo(path);
        }else {
            PictureSelector.create(UploadClassDetailActivity.this)
                    .openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    //.theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量 int
                    .minSelectNum(1)// 最小选择数量 int
                    .imageSpanCount(4)// 每行显示个数 int
                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    //.previewImage(true)// 是否可预览图片 true or false
                    .previewVideo(true)// 是否可预览视频 true or false
                    .enablePreviewAudio(true) // 是否可播放音频 true or false
                    .isCamera(true)// 是否显示拍照按钮 true or false
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                    .enableCrop(true)// 是否裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                    //.glideOverride(160,160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    //.withAspectRatio(16,9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                    .isGif(true)// 是否显示gif图片 true or false
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    //.circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    //.showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    //.//showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .openClickSound(true)// 是否开启点击声音 true or false
                    //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                    //.previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    //.cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                    //.minimumCompressSize(100)// 小于100kb的图片不压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                    //.rotateEnabled() // 裁剪是否可旋转图片 true or false
                    //.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
                    .videoQuality(1)// 视频录制质量 0 or 1 int
                    //.videoMaxSecond(1800)// 显示多少秒以内的视频or音频也可适用 int
                    //.videoMinSecond(1800)// 显示多少秒以内的视频or音频也可适用 int
                    .recordVideoSecond(1800)//视频秒数录制 默认60s int
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的


                    path = selectList.get(0).getPath();
                    if (!CommonUtils.isBeyondVideoSizeLimited(path)){
                        addVideo.setImageResource(R.drawable.baseline_play_circle_outline_black_24);
                        uploadVideo.setImageBitmap(CommonUtils.getVideoThumb(path));
                        isHasVideo = true;
                        duration = String.valueOf(CommonUtils.getVideoDuration(path));
                        showLoadingAnim();
                        uploadClassDetailPresenter.uploadImg(CommonUtils.compressImage(CommonUtils.getVideoThumb(path)).getName(), ImageUtils.getBytesByBitmap(CommonUtils.getVideoThumb(path)));
                    }else {
                        MyToast.getInstance(UploadClassDetailActivity.this).show("您所选视频大小超过500M，请重新选择",Toast.LENGTH_SHORT);
                    }

                    break;
            }
        }
    }


    private void uploadClassVideo(String path){
        File file = new File(path);

        OssUtils.getInstance().upVideo(CommonUtils.getContext(), new OssUtils.OssUpCallback() {
            @Override
            public void successImg(String img_url) {

            }

            @Override
            public void successVideo(String video_url) {
                //showLoadingAnim();
                Log.e(TAG, "successVideo: 1" );
                ClassDetailList classDetailList = new ClassDetailList();
                classDetailList.setClass_classd_id(bundle.getString("classId"));
                classDetailList.setClassd_title(titleClassDetail.getText().toString());
                classDetailList.setClassd_des(contentClassDetail.getText().toString());
                classDetailList.setClassd_video(video_url);
                classDetailList.setClassd_duration(duration);
                classDetailList.setClassd_video_cover(coverUrl);
                Log.e(TAG, "successVideo: 2" );
                uploadClassDetailPresenter.sendClassDetail(classDetailList);
                Log.e(TAG, "successVideo: 3" );
            }

            @Override
            public void inProgress(long progress, long allsi) {
                progressBar.setMax(Integer.parseInt(String.valueOf(allsi)));
                progressBar.incrementProgressBy(1);
                progressBar.setProgress(Integer.parseInt(String.valueOf(progress)));

            }
        },file.getName(),path);
    }
    @Override
    public void showProgress(long nowSize, long totalSize) {

//        progressContent.setText(nowSize / 1000 + "KB / " + totalSize / 1000000 + "MB");
//        float percent = (float)nowSize / (float)totalSize;
//        progressView.setPercentage(Float.parseFloat(df.format(percent * 100)));
    }

    @Override
    public void uploadVideoSuccess(String path) {
        //progressShow.setVisibility(View.GONE);
        String url =  path;
        showLoadingAnim();
        ClassDetailList classDetailList = new ClassDetailList();
        classDetailList.setClass_classd_id(bundle.getString("classId"));
        classDetailList.setClassd_title(titleClassDetail.getText().toString());
        classDetailList.setClassd_des(contentClassDetail.getText().toString());
        classDetailList.setClassd_video(url);
        classDetailList.setClassd_duration(duration);
        classDetailList.setClassd_video_cover(coverUrl);
        uploadClassDetailPresenter.sendClassDetail(classDetailList);
        hideLoadingAnim();
    }

    @Override
    public void sendClassDetailSuccess(String msg) {
        //hideLoadingAnim();

        Log.e(TAG, "successVideo: 4" );
        loadSuccessDialog(bundle.getString("classId"),bundle.getString("title"));
    }

    @Override
    public void uploadImgSuccesss(String path) {
        hideLoadingAnim();

        coverUrl =  path;
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uploadClassDetailPresenter.onDestroy();
    }

    /**
     * @param classId
     * @param title
     */
    private void loadSuccessDialog(final String classId, final String title){
        final CommonDialog dialog = new CommonDialog(this);

        dialog.setCancelable(false);

        dialog.setMessage("是否继续上传下一节课程？之后可以在课程页面更新课程内容，编辑课程等操作")
                .setTitle("本节课上传成功！请等待审核")
                .setSingle(false)
                .setNegtive("继续上传")
                .setPositive("不再上传")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        Bundle bundleto = new Bundle();
                        bundleto.putString("classId",classId);
                        bundleto.putString("title",title);
                        IntentUtils.getInstence().intent(UploadClassDetailActivity.this,UploadClassDetailActivity.class,bundleto);
                        finish();
                    }

                })
                .show();
    }


}
