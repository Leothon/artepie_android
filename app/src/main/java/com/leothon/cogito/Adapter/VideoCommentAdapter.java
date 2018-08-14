package com.leothon.cogito.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.UserComment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.3
 * 视频下用户评论Adapter
 */
public class VideoCommentAdapter extends BaseAdapter {

    public VideoCommentAdapter(Context context, ArrayList<UserComment> userComments){
        super(context,R.layout.comment_item,userComments);
    }

    @Override
    public <T> void convert(BaseViewHolder holder, T bean) {
        UserComment userComment = (UserComment)bean;
        holder.setText(R.id.user_name_video,userComment.getUsername());
        holder.setText(R.id.user_comment_video,userComment.getUsercomment());
        holder.setImageResource(R.id.user_icon_video,userComment.getUserIcon());
    }

}
