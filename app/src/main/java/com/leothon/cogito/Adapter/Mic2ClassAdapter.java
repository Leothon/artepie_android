package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.ActivityAndVideo;
import com.leothon.cogito.Bean.MicClass;
import com.leothon.cogito.R;

import java.util.ArrayList;

/**
 * created by leothon on 9/9/2018.
 */
public class Mic2ClassAdapter extends BaseAdapter {
    public Mic2ClassAdapter(Context context, ArrayList<MicClass> micClasses){
        super(context, R.layout.mic2_item,micClasses);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean) {
        MicClass micClass = (MicClass)bean;
        holder.setImageUrls(R.id.mic2_img,micClass.getImgurl());
        holder.setText(R.id.mic2_author,micClass.getAuthor());
        holder.setText(R.id.mic2_time,micClass.getTime());
        holder.setText(R.id.mic2_title,micClass.getTitle());
        holder.setText(R.id.mic2_class_count,micClass.getClassCount());
        holder.setText(R.id.mic2_class_price,micClass.getPrice());
    }
}
