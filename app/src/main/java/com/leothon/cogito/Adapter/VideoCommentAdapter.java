package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.R;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.3
 * 视频下用户评论Adapter
 */
public class VideoCommentAdapter extends BaseAdapter {

    public VideoCommentAdapter(Context context, ArrayList<Comment> userComments){
        super(context,R.layout.comment_item,userComments);
    }

    @Override
    public <T> void convert(BaseViewHolder holder, T bean,int position) {
        Comment userComment = (Comment)bean;
        holder.setText(R.id.user_name_video,userComment.getUser_name());
        holder.setText(R.id.user_comment_video,userComment.getComment_q_content());
        holder.setImageUrls(R.id.user_icon_video,userComment.getUser_icon());
    }

}
