package com.leothon.cogito.Adapter;

import android.content.Context;


import com.leothon.cogito.Bean.MicClass;
import com.leothon.cogito.R;

import java.util.ArrayList;

/**
 * created by leothon on 9/5/2018.
 */
public class FollowAFansAdapter extends BaseAdapter {

    public FollowAFansAdapter(Context context, ArrayList<MicClass> voices){
        super(context, R.layout.followfans_item,voices);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean,int position) {
        MicClass voice = (MicClass)bean;
        holder.setText(R.id.faf_name,voice.getTitle());
        holder.setText(R.id.faf_content,voice.getAuthor());
        //holder.setOvilImage(R.id.faf_icon,voice.getImgurl());
    }
}
