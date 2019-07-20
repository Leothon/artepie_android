package com.leothon.cogito.Mvp.View.Activity.AskActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.SendQAData;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.ImageUtils;
import com.leothon.cogito.Utils.OssUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.View.ProgressView;
import com.leothon.cogito.Weight.CommonDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AskActivity extends BaseActivity implements AskActivityContract.IAskActivityView {

    @BindView(R.id.teacher_icon)
    RoundedImageView Icon;
    @BindView(R.id.send_icon)
    RoundedImageView send;
    @BindView(R.id.ask_add_img)
    ImageView askAddImg;
//    @BindView(R.id.ask_add_sound)
//    ImageView askAddSound;
    @BindView(R.id.ask_char_count)
    TextView askCharCound;
    @BindView(R.id.ask_content)
    MaterialEditText askContent;
    @BindView(R.id.ask_img_layout)
    RelativeLayout askImgLayout;
    @BindView(R.id.upload_cover_ll)
    RelativeLayout uploadCover;
    @BindView(R.id.upload_cover_img)
    RoundedImageView uploadImgCover;

    @BindView(R.id.write_re_user_name)
    TextView reUserName;
    @BindView(R.id.write_re_content)
    TextView reContent;
    @BindView(R.id.delete_video)
    ImageView deleteVideo;
//
//    @BindView(R.id.progress_show)
//    CardView progressShow;
//    @BindView(R.id.progress_bar)
//    ProgressView progressView;
//    @BindView(R.id.progress_content)
//    TextView progressContent;

    @BindView(R.id.progress_upload_video)
    NumberProgressBar progressBar;
    String filePath = "";

    private Bundle bundle;
    private Intent intent;
    private UserEntity userEntity;

    DecimalFormat df = new DecimalFormat("0.00");
    private String coverImg = "";



    private AskActivityPresenter askActivityPresenter;
    private SendQAData sendQAData;



    @Override
    public int initLayout() {
        return R.layout.activity_ask;
    }

    @Override
    public void initData() {
        askActivityPresenter = new AskActivityPresenter(this);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();

        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
    }
    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();

        if (bundle.getString("type").equals("re")){
            askAddImg.setVisibility(View.GONE);
            //askAddSound.setVisibility(View.GONE);
            setToolbarTitle("转发");

            askActivityPresenter.getReInfo(bundle.getString("id"),activitysharedPreferencesUtils.getParams("token","").toString());

        }else {
            askAddImg.setVisibility(View.VISIBLE);
            //askAddSound.setVisibility(View.VISIBLE);
            setToolbarTitle("");
        }
        setToolbarSubTitle("");

        Icon.setVisibility(View.VISIBLE);
        ImageLoader.loadImageViewThumbnailwitherror(this,userEntity.getUser_icon(),Icon,R.drawable.defaulticon);
        send.setVisibility(View.VISIBLE);


        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (askContent.getText().toString().equals("")){
                    finish();
                }else {
                    loadDialog();
                }
            }
        });
        askContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                askCharCound.setText("" + editable.toString().length());
                if (editable.toString().length() > 140){
                    askCharCound.setTextColor(Color.RED);
                }else {
                    askCharCound.setTextColor(getResources().getColor(R.color.fontColor));
                }
            }
        });
    }


    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("确认退出编辑？")
                .setTitle("提示")
                .setSingle(false)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }



    @Override
    public void onBackPressed() {
        if (askContent.getText().toString().equals("")){
            super.onBackPressed();
        }else {
            loadDialog();
        }

    }

    @OnClick(R.id.preview_video)
    public void previewVideo(View view){
        PictureSelector.create(AskActivity.this).externalPictureVideo(filePath);
    }

    @OnClick(R.id.delete_video)
    public void deleteVideo(View view){
        filePath = "";
        uploadCover.setVisibility(View.GONE);
    }

    @OnClick(R.id.send_icon)
    public void sendAsk(View view){

        if (bundle.getString("type").equals("re")){

            String content = askContent.getText().toString();


            if (bundle.get("qaId").equals("")){
                askActivityPresenter.reContent(activitysharedPreferencesUtils.getParams("token","").toString(),content,bundle.getString("id"));
            }else {
                askActivityPresenter.reContent(activitysharedPreferencesUtils.getParams("token","").toString(),content,bundle.getString("qaId"));
            }


            showLoadingAnim();
        }else {
            sendQAData = new SendQAData();
            sendQAData.setToken(activitysharedPreferencesUtils.getParams("token","").toString());

            sendQAData.setQa_video_cover(coverImg);
            sendQAData.setQa_content(askContent.getText().toString());
            if (askContent.getText().toString().equals("")){
                MyToast.getInstance(this).show("请输入问题描述",Toast.LENGTH_SHORT);
            }else if (askContent.getText().toString().length() > 140){
                MyToast.getInstance(this).show("最多字数限制为140字",Toast.LENGTH_SHORT);
            }else {

                if (filePath.equals("")){
                    showLoadingAnim();
                    askActivityPresenter.sendData(sendQAData);
                }else {
                    //progressShow.setVisibility(View.VISIBLE);
                    //showLoadingAnim();
                    progressBar.setVisibility(View.VISIBLE);
                    send.setEnabled(false);
                    uploadVideo(filePath);
                    //askActivityPresenter.uploadFile(filePath);

                }
            }
        }

    }

    private void uploadVideo(String path){
        File file = new File(path);
        OssUtils.getInstance().upVideo(CommonUtils.getContext(), new OssUtils.OssUpCallback() {
            @Override
            public void successImg(String img_url) {


            }

            /**
             * @param video_url
             */
            @Override
            public void successVideo(String video_url) {
                Log.e(TAG, "successVideo: " + video_url);
                sendQAData.setQa_video(video_url);
                askActivityPresenter.sendData(sendQAData);

            }

            @Override
            public void inProgress(long progress, long allsi) {
                Log.e(TAG, "inProgress: 运行" );
                progressBar.setMax(Integer.parseInt(String.valueOf(allsi)));
                progressBar.incrementProgressBy(1);
                progressBar.setProgress(Integer.parseInt(String.valueOf(progress)));

            }
        },file.getName(),path);
    }

//    @OnClick(R.id.ask_add_sound)
//    public void addSound(View view){
//        MyToast.getInstance(this).show("暂不支持音频",Toast.LENGTH_SHORT);
//    }

    @OnClick(R.id.ask_add_img)
    public void addVideo(View view){

        addVideoORAudio(PictureMimeType.ofVideo());
    }


    @Override
    public void showProgress(long nowSize, long totalSize) {



        progressBar.setMax(Integer.parseInt(String.valueOf(totalSize)));
        progressBar.incrementProgressBy(Integer.parseInt(String.valueOf(nowSize)));
//        progressContent.setText(nowSize / 1000 + "KB / " + totalSize / 1000000 + "MB");
//        float percent = (float)nowSize / (float)totalSize;
//
//        progressView.setPercentage(Float.parseFloat(df.format(percent * 100)));

    }

    @Override
    public void getUploadUrl(String url) {


        progressBar.setVisibility(View.INVISIBLE);

        //hideLoadingAnim();
        sendQAData.setQa_video(url);


        askActivityPresenter.sendData(sendQAData);

    }

    @Override
    public void sendSuccess(String msg) {
        EventBus.getDefault().post(msg);
        hideLoadingAnim();

        PictureFileUtils.deleteCacheDirFile(AskActivity.this);
        super.onBackPressed();
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void getReInfo(QAData qaData) {
        askImgLayout.setVisibility(View.VISIBLE);

        if (qaData.getQa_re_id() == null){
            reUserName.setText("@" + qaData.getUser_name());
            String re = qaData.getQa_content();
            SpannableString spannableString = new SpannableString(re);
            if (re.contains("@") && re.contains(":")){

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#2298EF"));
                spannableString.setSpan(colorSpan, re.indexOf("@"),re.indexOf(":"), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }
            reContent.setText(spannableString);
        }else {
            reUserName.setText("@" + qaData.getQaData().getUser_name());
            reContent.setText(qaData.getQaData().getQa_content());
            String content = "//@" + qaData.getUser_name() + ":" + qaData.getQa_content();
            SpannableString spannableString = new SpannableString(content);

            if (content.contains("@") && content.contains(":")){

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#2298EF"));
                spannableString.setSpan(colorSpan, content.indexOf("@"),content.indexOf(":"), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            }

            askContent.setText(spannableString);
            askContent.setSelection(0);
        }


    }

    @Override
    public void reSuccess(String msg) {
        hideLoadingAnim();
        EventBus.getDefault().post(msg);
        super.onBackPressed();
    }

    @Override
    public void getImgUrl(String url) {

        hideLoadingAnim();

        coverImg = url;
    }


    private void addVideoORAudio(int type){
        PictureSelector.create(AskActivity.this)
                .openGallery(type)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的


                    filePath = selectList.get(0).getPath();
                    if (!CommonUtils.isBeyondVideoSizeLimited(filePath)){
                        uploadCover.setVisibility(View.VISIBLE);
                        uploadImgCover.setImageBitmap(CommonUtils.getVideoThumb(filePath));
                        askActivityPresenter.uploadVideoImg(CommonUtils.compressImage(CommonUtils.getVideoThumb(filePath)).getName(),ImageUtils.getBytesByBitmap(CommonUtils.getVideoThumb(filePath)));
                        showLoadingAnim();
                    }else {
                        MyToast.getInstance(AskActivity.this).show("您所选视频大小超过500M，请重新选择",Toast.LENGTH_SHORT);
                    }

                    break;
            }
        }
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        askActivityPresenter.onDestroy();
    }


}
