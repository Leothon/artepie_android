package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;



import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.AuthView;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.View.RichEditTextView;
import com.leothon.cogito.handle.CustomHtml;
import com.leothon.cogito.handle.RichEditImageGetter;
import com.leothon.cogito.wxapi.WXEntryActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
public class ArticleActivity extends BaseActivity implements ArticleContract.IArticleView,SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.article_content_show)
    RichEditTextView articleContent;

    @BindView(R.id.article_img_detail)
    RoundedImageView articleImg;

    @BindView(R.id.article_title_detail)
    TextView articleTitle;
    @BindView(R.id.article_author_icon_detail)
    RoundedImageView articleAuthorIcon;
    @BindView(R.id.article_author_detail)
    TextView articleAuthor;

    @BindView(R.id.article_right)
    TextView articleRight;
    @BindView(R.id.article_time_show)
    TextView articleTime;

    @BindView(R.id.swp_article_detail)
    SwipeRefreshLayout swpArticle;

    @BindView(R.id.article_app_bar)
    AppBarLayout articleAppBar;

    @BindView(R.id.title_article_detail)
    TextView titleArticleDetail;

    @BindView(R.id.more_about_article)
    ImageView moreAboutArticle;

    @BindView(R.id.auth_mark_article)
    AuthView authMark;

    @BindView(R.id.share_article)
    ImageView shareArticle;

    private TextView deleteContent;
    private TextView copyContent;
    private RelativeLayout dismissArt;
    private View popviewArt;
    private PopupWindow popupWindowArt;

    private ArticlePresenter articlePresenter;
    private QQShareListener qqShareListener;
    private Tencent mTencent;
    private Bundle bundle;
    private Intent intent;

    @BindView(R.id.article_detail_bar)
    Toolbar toolbar;

    String uuid;

    private Article article;

    private CollapsingToolbarLayoutState state;

    private View dismissShare;
    private RelativeLayout shareToQQ;
    private RelativeLayout shareToFriendCircle;
    private RelativeLayout shareToWeChat;
    private RelativeLayout shareToMore;

    private PopupWindow sharePopup;

    private View popUPView;


    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }
    @Override
    public int initLayout() {
        return R.layout.activity_article;
    }
    @Override
    public void initData() {
        swpArticle.setProgressViewOffset (false,100,300);
        swpArticle.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        articlePresenter = new ArticlePresenter(this);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }
        initSharePopupWindow();
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        titleArticleDetail.setText("");
        setToolbarSubTitle("");
        setToolbarTitle("");
        swpArticle.setRefreshing(true);
        swpArticle.setOnRefreshListener(this);
        articlePresenter.loadArticle(bundle.getString("articleId",""),activitysharedPreferencesUtils.getParams("token","").toString());
        initMoreArtPopupWindow();

    }

    @Override
    public void loadArticleData(final Article article) {

        this.article = article;
        ImageLoader.loadImageViewThumbnailwitherror(this,article.getArticleImg(),articleImg,R.drawable.defalutimg);
        ImageLoader.loadImageViewThumbnailwitherror(this,article.getArticleAuthorIcon(),articleAuthorIcon,R.drawable.defalutimg);
        articleTitle.setText(article.getArticleTitle());
        articleAuthor.setText(article.getArticleAuthorName());
        articleRight.setText("本文著作权归作者 @" + article.getArticleAuthorName() + " 所有，转载请联系作者");
        articleTime.setText("发布于" + article.getArticleTime());

        int role = CommonUtils.isVIP(article.getAuthorRole());
        if (role != 2){
            authMark.setVisibility(View.VISIBLE);
            if (role == 0){
                authMark.setColor(Color.parseColor("#f26402"));
            }else if (role == 1){
                authMark.setColor(Color.parseColor("#2298EF"));
            }else {
                authMark.setVisibility(View.GONE);
            }
        }else {
            authMark.setVisibility(View.GONE);
        }
        Spanned spanned = CustomHtml.fromHtml(article.getArticleContent(),CustomHtml.FROM_HTML_MODE_LEGACY,new RichEditImageGetter(this,articleContent),null);
        articleContent.setText(spanned);
        articleContent.setFocusable(false);
        articleContent.setFocusableInTouchMode(false);
        if (swpArticle.isRefreshing()){
            swpArticle.setRefreshing(false);
        }

        articleAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {



                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;
                        //修改状态标记为展开
                        //toolbarLayout.setTitle("EXPANDED");
                        //设置title为EXPANDED
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        titleArticleDetail.setText(article.getArticleTitle());
                        state = CollapsingToolbarLayoutState.COLLAPSED;

                    }

                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            titleArticleDetail.setText("");
                            //由折叠变为中间状态时隐藏播放按钮
                        }
                        //toolbarLayout.setTitle("INTERNEDIATE");
                        //设置title为INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;
                        //修改状态标记为中间
                    }
                }



            }

        });


    }

    @OnClick(R.id.share_article)
    public void ShareArticle(View view){

        MyToast.getInstance(this).show("暂不支持分享",Toast.LENGTH_SHORT);
        //showShareWindow();
    }


    @OnClick(R.id.more_about_article)
    public void MoreAboutArticle(View view){
        showQAPopWindow();
        if (article.getArticleAuthorId().equals(uuid)){
            copyContent.setVisibility(View.GONE);
            deleteContent.setVisibility(View.VISIBLE);
            deleteContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    articlePresenter.deleteArticle(activitysharedPreferencesUtils.getParams("token","").toString(),article.getArticleId());
                }
            });
        }else {
            copyContent.setVisibility(View.VISIBLE);
            copyContent.setText("联系作者");
            deleteContent.setVisibility(View.GONE);
        }
    }
    public void initMoreArtPopupWindow(){
        popviewArt = LayoutInflater.from(this).inflate(R.layout.qa_more_pop,null,false);
        popupWindowArt = new PopupWindow(popviewArt,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindowArt.setBackgroundDrawable(new BitmapDrawable());
        popupWindowArt.setTouchable(true);
        popupWindowArt.setAnimationStyle(R.style.popupQAWindow_anim_style);
        popupWindowArt.setFocusable(true);
        popupWindowArt.setOutsideTouchable(true);
        popupWindowArt.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dismissArt = (RelativeLayout) popviewArt.findViewById(R.id.miss_pop_more);
        deleteContent = (TextView)popviewArt.findViewById(R.id.delete_content_this);
        copyContent = (TextView)popviewArt.findViewById(R.id.copy_content_this);

        dismissArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowArt.dismiss();

            }
        });
    }
    public void showQAPopWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_article,null);
        popupWindowArt.showAtLocation(rootView, Gravity.CENTER,0,0);

    }
    @Override
    public void onRefresh() {
        articlePresenter.loadArticle(bundle.getString("articleId",""),activitysharedPreferencesUtils.getParams("token","").toString());
    }



    @OnClick(R.id.article_author_icon_detail)
    public void toUserZone(View view){
        Bundle bundleto = new Bundle();
        if (article.getArticleAuthorId().equals(uuid)){
            bundleto.putString("type","individual");
        }else {
            bundleto.putString("type","other");
            bundleto.putString("userId",article.getArticleAuthorId());
        }

        IntentUtils.getInstence().intent(ArticleActivity.this, IndividualActivity.class,bundleto);

    }


    @Override
    public void showInfo(String msg) {

        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void deleteSuccess(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
        Article article = new Article();
        EventBus.getDefault().post(article);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        articlePresenter.onDestroy();
    }

    private void initSharePopupWindow(){
        popUPView = LayoutInflater.from(this).inflate(R.layout.popup_share,null,false);
        sharePopup = new PopupWindow(popUPView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        sharePopup.setBackgroundDrawable(new BitmapDrawable());
        sharePopup.setTouchable(true);
        sharePopup.setAnimationStyle(R.style.popupWindow_anim_style);
        sharePopup.setFocusable(true);
        sharePopup.setOutsideTouchable(true);
        sharePopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dismissShare = (View) popUPView.findViewById(R.id.dismiss_share);
        shareToQQ = (RelativeLayout)popUPView.findViewById(R.id.share_class_to_qq);
        shareToFriendCircle = (RelativeLayout)popUPView.findViewById(R.id.share_class_to_circle);
        shareToWeChat = (RelativeLayout)popUPView.findViewById(R.id.share_class_to_wechat);
        shareToMore = (RelativeLayout)popUPView.findViewById(R.id.share_class_to_more);

        dismissShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePopup.dismiss();
                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
            }
        });

        shareToQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareToQQClass(article);
                sharePopup.dismiss();
                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
            }
        });
        shareToFriendCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleto = new Bundle();
                bundleto.putString("flag","3");
                bundleto.putSerializable("article",article);
                IntentUtils.getInstence().intent(ArticleActivity.this, WXEntryActivity.class,bundleto);
                sharePopup.dismiss();
                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
            }
        });
        shareToWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleto = new Bundle();
                bundleto.putString("flag","4");
                bundleto.putSerializable("article",article);
                IntentUtils.getInstence().intent(ArticleActivity.this, WXEntryActivity.class,bundleto);
                sharePopup.dismiss();
                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
            }
        });
        shareToMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareToMoreInfo(article);
                sharePopup.dismiss();
                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
            }
        });
    }
    public void showShareWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_select_class,null);
        sharePopup.showAtLocation(rootView, Gravity.BOTTOM,0,0);
        dismissShare.setBackgroundColor(Color.parseColor("#20b3b3b3"));
    }

    private void shareToMoreInfo(Article article) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "艺派");
        //share_intent.putExtra(Intent.EXTRA_TEXT, "我正在艺派APP学习课程" + classDetail.getTeaClasss().getSelectlisttitle() + "\n戳我查看：http://www.artepie.cn");
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
    }

    private void shareToQQClass(Article article)
    {
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.artepie.cn");
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE,article.getArticleTitle());
        //分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, article.getArticleImg());
        //分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, article.getArticleAuthorName());
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        //bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "??我在测试");
        //标识该消息的来源应用，值为应用名称+AppId。
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "艺派" + Constants.APP_ID);
        mTencent.shareToQQ(this, bundle , qqShareListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);

        if (null != mTencent)
            mTencent.onActivityResult(requestCode,resultCode,data);
    }


    private class QQShareListener implements IUiListener {
        @Override
        public void onCancel() {

        }

        @Override public void onError(UiError uiError) {
        }

        @Override public void onComplete(Object o) {

            MyToast.getInstance(ArticleActivity.this).show("分享成功！",Toast.LENGTH_SHORT);
        }
    }
}
