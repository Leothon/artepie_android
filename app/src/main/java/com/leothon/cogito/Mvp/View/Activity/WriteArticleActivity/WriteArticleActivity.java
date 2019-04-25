package com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.FontStyle;
import com.leothon.cogito.Utils.ImageUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.FontStyleMenu;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.View.RichEditTextView;
import com.leothon.cogito.Weight.CommonDialog;
import com.leothon.cogito.handle.CustomHtml;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteArticleActivity extends BaseActivity implements FontStyleMenu.OnFontPanelListener,RichEditTextView.OnSelectChangeListener,WriteArticleContract.IWriteArticleView {



    @BindView(R.id.write_article_title)
    MaterialEditText writeArticleTitle;
    @BindView(R.id.write_article_content)
    RichEditTextView writeArticleContent;
    @BindView(R.id.write_article_img)
    ImageView writeArticleImg;
    @BindView(R.id.span_style_set)
    ImageView spanStyleSet;

    @BindView(R.id.style_menu)
    CardView styleMenu;

    @BindView(R.id.font_style_menu)
    FontStyleMenu fontStyleMenu;

    private String filePath;

    private boolean styleMenuShow = false;
    private UserEntity userEntity;


    @BindView(R.id.send_icon)
    RoundedImageView sendIcon;
    private WriteArticlePresenter writeArticlePresenter;
    @Override
    public int initLayout() {
        return R.layout.activity_write_article;
    }

    @Override
    public void initData() {

        writeArticlePresenter = new WriteArticlePresenter(this);
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString()).getUid()).get(0);
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("写文章");

        fontStyleMenu.setOnFontPanelListener(this);
        writeArticleContent.setOnSelectChangeListener(this);
        sendIcon.setVisibility(View.VISIBLE);

    }

    @Override
    public void showBack() {
        getToolbar().setNavigationIcon(R.drawable.baseline_clear_black_24);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!writeArticleTitle.getText().toString().equals("") || !writeArticleContent.getText().toString().equals("")){
                    loadDialog();
                }else {
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.write_article_img)
    public void addImgToArticle(View view){
        PictureSelector.create(WriteArticleActivity.this)
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
                //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
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


                if (selectList.get(0).isCompressed()){
                    filePath = selectList.get(0).getCompressPath();
                }else {
                    filePath = selectList.get(0).getPath();
                }

                //writeArticleContent.setImg(filePath);

                Bitmap bitmap = ImageUtils.drawTextToRightBottom(WriteArticleActivity.this,ImageUtils.getImageBitmap(filePath),"艺派 @" + userEntity.getUser_name(),10,Color.WHITE,10,10);

                writeArticlePresenter.uploadSelectImg(CommonUtils.compressImage(bitmap));

                showLoadingAnim();
                filePath = null;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (!writeArticleTitle.getText().toString().equals("") || !writeArticleContent.getText().toString().equals("")){
            loadDialog();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void isUploadSuccess(String info) {
        hideLoadingAnim();
        MyToast.getInstance(this).show("文章发布成功",Toast.LENGTH_SHORT);
        PictureFileUtils.deleteCacheDirFile(WriteArticleActivity.this);
        Article article = new Article();
        EventBus.getDefault().post(article);
        super.onBackPressed();
    }

    @Override
    public void getUploadImgUrl(String url) {
        String trueUrl = Api.ComUrl + "image/" + url;
        writeArticleContent.setImg(trueUrl);
        hideLoadingAnim();
    }

    @OnClick(R.id.span_style_set)
    public void setSpanStyle(View view){
        if (styleMenuShow){
            styleMenuShow = false;
            spanStyleSet.setColorFilter(Color.parseColor("#808080"));
            styleMenu.setVisibility(View.GONE);
        }else {
            styleMenuShow = true;
            spanStyleSet.setColorFilter(Color.parseColor("#f26402"));
            styleMenu.setVisibility(View.VISIBLE);
        }

    }
    @OnClick(R.id.send_icon)
    public void sendArticle(View view){
        //TODO 发送文章

        Article article = new Article();
        if (!writeArticleContent.getText().toString().equals("") && !writeArticleTitle.getText().toString().equals("")){

            String articleImg = CommonUtils.getImgStr(CustomHtml.toHtml(writeArticleContent.getEditableText(),CustomHtml.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE)).get(0);
            if (!articleImg.equals("")){
                article.setArticleImg(articleImg);
            }


            article.setArticleTitle(writeArticleTitle.getText().toString());
            article.setArticleContent(CustomHtml.toHtml(writeArticleContent.getEditableText(),CustomHtml.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE));
            TokenValid tokenValid  = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
            String uuid = tokenValid.getUid();
            article.setArticleAuthorId(uuid);
            writeArticlePresenter.uploadArticleInfo(article);
            showLoadingAnim();
        }else {
            MyToast.getInstance(this).show("请完成文章所有内容后提交！",Toast.LENGTH_SHORT);
        }

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
    protected void onDestroy() {
        super.onDestroy();
        writeArticlePresenter.onDestroy();
    }



    @Override
    public void setBold(boolean isBold) {
        writeArticleContent.setBold(isBold);
    }

    @Override
    public void setItalic(boolean isItalic) {
        writeArticleContent.setItalic(isItalic);
    }

    @Override
    public void setUnderline(boolean isUnderline) {
        writeArticleContent.setUnderline(isUnderline);
    }

    @Override
    public void setStreak(boolean isStreak) {
        writeArticleContent.setStreak(isStreak);
    }

    @Override
    public void setFontSize(int size) {
        writeArticleContent.setFontSize(size);
    }


    @Override
    public void onFontStyleChang(FontStyle fontStyle) {
        fontStyleMenu.initFontStyle(fontStyle);
    }

    @Override
    public void onSelect(int start, int end) {

    }



    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

}
