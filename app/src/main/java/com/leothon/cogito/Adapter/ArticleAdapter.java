package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.DTO.ArticleData;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.Mvp.View.Activity.AskActivity.AskActivity;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.CommentDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.AuthView;
import com.leothon.cogito.View.Banner;
import com.leothon.cogito.View.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private ArticleData articleData;


    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;


//    private SharedPreferencesUtils sharedPreferencesUtils;
//    private String userId;


    public ArticleAdapter(ArticleData articleData, Context context){
        this.articleData = articleData;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEAD0) {
            return new ArticleHeadViewHolder(LayoutInflater.from(context).inflate(R.layout.article_head,parent,false));
        }else {
            return new ArticleViewHolder(LayoutInflater.from(context).inflate(R.layout.article_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        int viewType = getItemViewType(position);
//        sharedPreferencesUtils = new SharedPreferencesUtils(context, "saveToken");
//        userId = tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token", "").toString()).getUid();
        if (viewType == HEAD0) {
            ArticleHeadViewHolder articleHeadViewHolder = (ArticleHeadViewHolder)holder;

            ArrayList<String> urls = new ArrayList<>();
            for (int i = 0;i < articleData.getBanners().size();i ++){
                urls.add(articleData.getBanners().get(i).getBanner_img());
            }
            ImageLoader.loadImageViewThumbnailwitherror(context,articleData.getUser_icon(),articleHeadViewHolder.articleTitleIcon,R.drawable.defaulticon);

            articleHeadViewHolder.articleBanner.setPages(urls, new MZHolderCreator() {
                @Override
                public MZViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });

            articleHeadViewHolder.articleBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
                @Override
                public void onPageClick(View view, int i) {
                    Bundle bundle = new Bundle();
                    bundle.putString("articleId",articleData.getBanners().get(i).getBanner_id());
                    IntentUtils.getInstence().intent(context,ArticleActivity.class,bundle);
                }
            });

            articleHeadViewHolder.articleBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                    articleHeadViewHolder.bannerTitle.setText("" + articleData.getBanners().get(i).getBanner_url());
                }

                @Override
                public void onPageSelected(int i) {

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
//            articleHeadViewHolder.articleBanner.setImageUrl(urls);
//            articleHeadViewHolder.articleBanner.setPointPosition(2);//位置点为右边
//
//            articleHeadViewHolder.articleBanner.setOnItemClickListener(new com.leothon.cogito.View.Banner.OnItemClickListener() {
//                @Override
//                public void onItemClick(int position) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("articleId",articleData.getBanners().get(position).getBanner_id());
//                    IntentUtils.getInstence().intent(context,ArticleActivity.class,bundle);
//                }
//            });
//            articleHeadViewHolder.articleBanner.setOnPositionListener(new com.leothon.cogito.View.Banner.OnPositionListener() {
//                @Override
//                public void onPositionChange(int position) {
//                    articleHeadViewHolder.bannerTitle.setText("" + articleData.getBanners().get(position).getBanner_url());
//                }
//            });
        }else if (viewType == HEAD1) {
            int pos = position - 1;
            ArticleViewHolder articleViewHolder = (ArticleViewHolder)holder;
            Article article = articleData.getArticles().get(pos);
            ImageLoader.loadImageViewThumbnailwitherror(context,article.getArticleImg(),articleViewHolder.articleImg,R.drawable.default_cover);
            ImageLoader.loadImageViewThumbnailwitherror(context,article.getArticleAuthorIcon(),articleViewHolder.articleAuthorIcon,R.drawable.defaulticon);
            articleViewHolder.articleTitle.setText(article.getArticleTitle());
            articleViewHolder.articleAuthor.setText(article.getArticleAuthorName());
            //articleViewHolder.articleTime.setText(CommonUtils.getTimeRange(article.getArticleTime()));
            articleViewHolder.articleTime.setVisibility(View.GONE);
            articleViewHolder.articleCount.setText("阅读 " + CommonUtils.numToChar(article.getArticleVisionCount()));
            articleViewHolder.articleLike.setText("推荐" + CommonUtils.numToChar(article.getLikeCount()));
            int role = CommonUtils.isVIP(article.getAuthorRole());
            if (role != 2){
                articleViewHolder.authorInfo.setVisibility(View.VISIBLE);
                if (role == 0){
                    articleViewHolder.authorInfo.setColor(Color.parseColor("#f26402"));
                }else {
                    articleViewHolder.authorInfo.setColor(Color.parseColor("#2298EF"));
                }

            }else {
                articleViewHolder.authorInfo.setVisibility(View.GONE);
            }

            articleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("articleId",article.getArticleId());
                    IntentUtils.getInstence().intent(context, ArticleActivity.class,bundle);
                }
            });
        }




    }


    @Override
    public int getItemViewType(int position) {


        if (position == 0 ) {
            return HEAD0;
        }else {

            return HEAD1;
        }


    }



    @Override
    public int getItemCount() {

        return articleData.getArticles().size() + 1;

    }




    class ArticleHeadViewHolder extends RecyclerView.ViewHolder{

//        @BindView(R.id.article_banner)
//        Banner articleBanner;

        @BindView(R.id.article_banner)
        MZBannerView articleBanner;
        @BindView(R.id.banner_title_article)
        TextView bannerTitle;
        @BindView(R.id.article_icon_title)
        RoundedImageView articleTitleIcon;
        public ArticleHeadViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class ArticleViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.article_vision_count)
        TextView articleCount;
        @BindView(R.id.article_like_count)
        TextView articleLike;
        @BindView(R.id.article_img)
        RoundedImageView articleImg;
        @BindView(R.id.article_author_icon)
        RoundedImageView articleAuthorIcon;
        @BindView(R.id.article_author)
        TextView articleAuthor;
        @BindView(R.id.article_title)
        TextView articleTitle;
        @BindView(R.id.article_time)
        TextView articleTime;
        @BindView(R.id.auth_mark_article_list)
        AuthView authorInfo;



        public ArticleViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }



    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        /**
         * @param context
         * @return
         */
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.mz_banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.mz_banner_img);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
//            mImageView.setImageResource(data);
            ImageLoader.loadImageViewThumbnailwitherror(context,data,mImageView,R.drawable.default_cover);
        }
    }




}
