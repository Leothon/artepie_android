package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.ArticleComment;
import com.leothon.cogito.R;

import java.util.ArrayList;

public class ArticleCommentAdapter extends BaseAdapter {

    public ArticleCommentAdapter(Context context, ArrayList<ArticleComment> articleComments){
        super(context, R.layout.article_comment_layout,articleComments);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {
        ArticleComment articleComment = (ArticleComment)bean;

    }
}
