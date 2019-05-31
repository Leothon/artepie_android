package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.ArticleComment;
import com.leothon.cogito.R;

import java.util.ArrayList;

public class ArticleCommentAdapter extends BaseAdapter {

    public ArticleCommentAdapter(Context context, ArrayList<ArticleComment> articleComments){
        super(context, R.layout.article_comment_item,articleComments);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {
        ArticleComment articleComment = (ArticleComment)bean;
        if (!articleComment.getArticleCommentReply().equals("")){
            holder.setViewVisiable(R.id.author_reply,1);
            holder.setText(R.id.author_reply_content,articleComment.getArticleCommentReply());
        }else {
            holder.setViewVisiable(R.id.author_reply,0);
        }
        holder.setImageUrls(R.id.user_icon_article,articleComment.getArticleCommentUserIcon());
        holder.setText(R.id.user_name_video,articleComment.getArticleCommentUserName());
        holder.setText(R.id.user_comment_video,articleComment.getArticleComment());



    }
}
