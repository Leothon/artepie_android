package com.leothon.cogito.Adapter;

import android.content.Context;


import com.leothon.cogito.Bean.Voice;
import com.leothon.cogito.R;

import java.util.ArrayList;

/**
 * created by leothon on 9/5/2018.
 */
public class FollowAFansAdapter extends BaseAdapter {

    public FollowAFansAdapter(Context context, ArrayList<Voice> voices){
        super(context, R.layout.followfans_item,voices);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean) {
        Voice voice = (Voice)bean;
        holder.setText(R.id.faf_name,voice.getTitle());
        holder.setText(R.id.faf_content,voice.getDescription());
        //holder.setOvilImage(R.id.faf_icon,voice.getImgurl());
    }
}
