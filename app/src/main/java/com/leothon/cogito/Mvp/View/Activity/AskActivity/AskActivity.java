package com.leothon.cogito.Mvp.View.Activity.AskActivity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.SendQAData;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuyu.gsyvideoplayer.utils.FileUtils.getPath;

public class AskActivity extends BaseActivity implements AskActivityContract.IAskActivityView {

    @BindView(R.id.teacher_icon)
    RoundedImageView Icon;
    @BindView(R.id.send_icon)
    RoundedImageView send;
    @BindView(R.id.ask_add_img)
    ImageView askAddImg;
    @BindView(R.id.ask_add_sound)
    ImageView askAddSound;
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
    String filePath = "";

    private Bundle bundle;
    private Intent intent;
    private UserEntity userEntity;

    private String coverImg = "";

    private static int VIDEO = PictureMimeType.ofVideo();
    private static int AUDIO = PictureMimeType.ofAudio();
    private static int ALL = PictureMimeType.ofAll();

    private AskActivityPresenter askActivityPresenter;
    ZLoadingDialog dialog = new ZLoadingDialog(AskActivity.this);
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
            askAddSound.setVisibility(View.GONE);
            setToolbarTitle("转发");

            askActivityPresenter.getReInfo(bundle.getString("id"),activitysharedPreferencesUtils.getParams("token","").toString());

        }else {
            askAddImg.setVisibility(View.VISIBLE);
            askAddSound.setVisibility(View.VISIBLE);
            setToolbarTitle("");
        }
        setToolbarSubTitle("");

        Icon.setVisibility(View.VISIBLE);
        ImageLoader.loadImageViewThumbnailwitherror(this,userEntity.getUser_icon(),Icon,R.drawable.defaulticon);
        send.setVisibility(View.VISIBLE);


        //getToolbar().setNavigationIcon(Constants.icon);
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
                CommonUtils.makeText(this,"请输入问题描述");
            }else if (askContent.getText().toString().length() > 140){
                CommonUtils.makeText(this,"最多字数限制为140字");
            }else {
                showLoadingAnim();
                if (filePath.equals("")){
                    askActivityPresenter.sendData(sendQAData);
                }else {
                    askActivityPresenter.uploadFile(filePath);
                }
            }
        }

    }

    @OnClick(R.id.ask_add_sound)
    public void addSound(View view){
        //TODO 增加声音

        addVideoORAudio(AUDIO);
    }

    @OnClick(R.id.ask_add_img)
    public void addVideo(View view){

        addVideoORAudio(VIDEO);
    }


    @Override
    public void getUploadUrl(String url) {
        String urlPath = Api.ComUrl + "resource/" + url;


        if (CommonUtils.fileType(url).equals("audio")){
            sendQAData.setQa_audio(urlPath);
        }else if (CommonUtils.fileType(url).equals("video")){
            sendQAData.setQa_video(urlPath);
        }
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
        CommonUtils.makeText(this,msg);
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

        coverImg = Api.ComUrl + "resource/" + url;
    }


    private void addVideoORAudio(int type){
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(AskActivity.this)
                .openGallery(type)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                //.maxSelectNum(1)// 最大图片选择数量 int
                //.minSelectNum()// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                //.previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                //.enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                //.glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                //.isGif()// 是否显示gif图片 true or false
                //.compressSavePath(getPath())//压缩图片保存地址
                //.freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
                //.circleDimmedLayer()// 是否圆形裁剪 true or false
                //.showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                //.showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                //.openClickSound()// 是否开启点击声音 true or false
                //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                //.previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(50)// 裁剪压缩质量 默认90 int
                //.minimumCompressSize(100)// 小于100kb的图片不压缩
                //.synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                //.rotateEnabled() // 裁剪是否可旋转图片 true or false
                //.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
                .videoMaxSecond(1800)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(1800)// 显示多少秒以内的视频or音频也可适用 int
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
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的


                    filePath = selectList.get(0).getPath();
                    uploadCover.setVisibility(View.VISIBLE);
                    uploadImgCover.setImageBitmap(CommonUtils.getVideoThumb(filePath));
                    askActivityPresenter.uploadVideoImg(CommonUtils.compressImage(CommonUtils.getVideoThumb(filePath)));
                    showLoadingAnim();
                    break;
            }
        }
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
        askActivityPresenter.onDestroy();
    }
}
