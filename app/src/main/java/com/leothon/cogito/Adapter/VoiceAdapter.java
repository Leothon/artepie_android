package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.Voice;
import com.leothon.cogito.R;

import java.util.ArrayList;
/**
 * created by leothon on 2018.8.10
 */
public class VoiceAdapter extends BaseAdapter {

    public VoiceAdapter(Context context, ArrayList<Voice> voices){
        super(context, R.layout.voiceitem,voices);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean) {
        Voice voice = (Voice)bean;
        holder.setImageUrls(R.id.voice_img,voice.getImgurl());
        holder.setText(R.id.voice_des,voice.getDescription());
        holder.setText(R.id.voice_title,voice.getTitle());
    }
}
