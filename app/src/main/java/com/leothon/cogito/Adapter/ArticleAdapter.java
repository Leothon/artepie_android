package com.leothon.cogito.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.Banner;
import com.leothon.cogito.DTO.ArticleData;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleAdapter extends BaseAdapter {

    public ArticleAdapter(Context context, ArrayList<Article> articles){
        super(context, R.layout.article_item,articles);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {

        Article article = (Article)bean;
        holder.setImageUrls(R.id.article_img,article.getArticleImg());
        holder.setImageUrls(R.id.article_author_icon,article.getArticleAuthorIcon());
        holder.setText(R.id.article_author,article.getArticleAuthorName());
        holder.setText(R.id.article_time,CommonUtils.getTimeRange(article.getArticleTime()));
        holder.setText(R.id.article_title,article.getArticleTitle());
        int role = CommonUtils.isVIP(article.getAuthorRole());
        if (role != 2){
            holder.setAuthorVisible(R.id.auth_mark_article_list,role,1);
        }else {
            holder.setAuthorVisible(R.id.auth_mark_article_list,role,0);
        }

    }

}
