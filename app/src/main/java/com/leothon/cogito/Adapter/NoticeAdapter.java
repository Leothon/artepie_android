package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.Notice;
import com.leothon.cogito.R;

import java.util.ArrayList;

/**
 * created by leothon on 8/12/2018.
 */
public class NoticeAdapter extends BaseAdapter {

    public NoticeAdapter(Context context, ArrayList<Notice> notices){
        super(context, R.layout.notice_item,notices);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean) {
        Notice notice = (Notice)bean;
        holder.setImageUrlswiththumb(R.id.notice_icon,notice.getUsericon());
        holder.setText(R.id.notice_name,notice.getNoticeuser());
        holder.setText(R.id.notice_content,notice.getNoticecontent());
    }
}
