package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.leothon.cogito.Bean.Notice;
import com.leothon.cogito.Bean.NoticeInfo;
import com.leothon.cogito.R;

import java.util.ArrayList;

/**
 * created by leothon on 8/12/2018.
 */
public class NoticeAdapter extends BaseAdapter {

    public NoticeOnClickListener noticeOnClickListener;

    public void setNoticeOnClickListener(NoticeOnClickListener noticeOnClickListener) {
        this.noticeOnClickListener = noticeOnClickListener;
    }

    public NoticeAdapter(Context context, ArrayList<NoticeInfo> notices){
        super(context, R.layout.notice_item,notices);
    }
    @Override
    public <T> void convert(final BaseViewHolder holder, T bean, final int position) {
        final NoticeInfo noticeInfo = (NoticeInfo)bean;
        holder.setImageUrlswiththumb(R.id.notice_icon,noticeInfo.getUserIcon());
        holder.setText(R.id.notice_name,noticeInfo.getUserName());
        if (noticeInfo.getNoticeStatus() == 0){
            holder.setViewBackColor(R.id.notice_ll,Color.parseColor("#f1f1f1"));
            holder.setViewVisiable(R.id.show_notice_bot,1);
        }else {
            holder.setViewBackColor(R.id.notice_ll,Color.parseColor("#ffffff"));
            holder.setViewVisiable(R.id.show_notice_bot,0);
        }
        switch (noticeInfo.getNoticeType()){
            case "qalike":
                holder.setText(R.id.like_or_comment,"点赞了您的问答");
                holder.setText(R.id.notice_content,"点击查看我的问答");
                break;
            case "commentlike":
                holder.setText(R.id.like_or_comment,"点赞了您的评论");
                holder.setText(R.id.notice_content,"点击查看我的评论");
                break;
            case "replylike":
                holder.setText(R.id.like_or_comment,"点赞了您的回复");
                holder.setText(R.id.notice_content,"点击查看我的回复");
                break;
            case "qacomment":
                holder.setText(R.id.like_or_comment,"评论了您：");
                holder.setText(R.id.notice_content,noticeInfo.getNoticeContent());
                break;
            case "replycomment":
                holder.setText(R.id.like_or_comment,"评论了您");
                holder.setText(R.id.notice_content,noticeInfo.getNoticeContent());
                break;
            default:
                break;
        }

        holder.getView(R.id.notice_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.setViewBackColor(R.id.notice_ll,Color.parseColor("#ffffff"));
                holder.setViewVisiable(R.id.show_notice_bot,0);
                noticeOnClickListener.noticeClickListener(noticeInfo.getNoticeId(),position,noticeInfo.getNoticeTargetId());
            }
        });
    }

    public interface NoticeOnClickListener{
        void noticeClickListener(String noticeId,int position,String targetId);
    }

}
