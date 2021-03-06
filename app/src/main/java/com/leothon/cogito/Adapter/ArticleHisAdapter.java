package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;

public class ArticleHisAdapter extends BaseAdapter {

    public ArticleHisAdapter(Context context, ArrayList<Article> articles){
        super(context, R.layout.article_item,articles);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {


        Article article = (Article)bean;
        holder.setImageUrls(R.id.article_img,article.getArticleImg());
        holder.setImageUrls(R.id.article_author_icon,article.getArticleAuthorIcon());
        holder.setText(R.id.article_author,article.getArticleAuthorName());
        holder.setText(R.id.article_title,article.getArticleTitle());
        holder.setText(R.id.article_time,CommonUtils.getTimeRange(article.getArticleTime()));
        holder.setText(R.id.article_vision_count,"阅读：" + CommonUtils.numToChar(article.getArticleVisionCount()));
        holder.setText(R.id.article_like_count,"推荐:" + CommonUtils.numToChar(article.getLikeCount()));
        int role = CommonUtils.isVIP(article.getAuthorRole());
        if (role != 2){

            holder.setAuthorVisible(R.id.auth_mark_article_list,role,1);

        }else {
            holder.setAuthorVisible(R.id.auth_mark_article_list,role,0);
        }
    }
}
