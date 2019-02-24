package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.RichEditTextView;
import com.leothon.cogito.handle.CustomHtml;
import com.leothon.cogito.handle.RichEditImageGetter;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

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

    private TextView deleteContent;
    private TextView copyContent;
    private RelativeLayout dismissArt;
    private View popviewArt;
    private PopupWindow popupWindowArt;

    private ArticlePresenter articlePresenter;

    private Bundle bundle;
    private Intent intent;

    @BindView(R.id.article_detail_bar)
    Toolbar toolbar;

    String uuid;

    private Article article;

    private CollapsingToolbarLayoutState state;




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
    public void showInfo(String msg) {

        CommonUtils.makeText(this,msg);
    }

    @Override
    public void deleteSuccess(String msg) {
        CommonUtils.makeText(this,msg);
        Article article = new Article();
        EventBus.getDefault().post(article);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        articlePresenter.onDestroy();
    }
}
