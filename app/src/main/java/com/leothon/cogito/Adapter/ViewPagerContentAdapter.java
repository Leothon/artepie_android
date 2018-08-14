package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.ActivityAndVideo;
import com.leothon.cogito.R;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.6
 */
public class ViewPagerContentAdapter extends BaseAdapter {

    public ViewPagerContentAdapter(Context context, ArrayList<ActivityAndVideo> activityAndVideos){
        super(context, R.layout.videoforyou_home,activityAndVideos);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean) {
        ActivityAndVideo activityAndVideo = (ActivityAndVideo) bean;
        holder.setText(R.id.foryou_tv,activityAndVideo.getAvtitle());
        holder.setImageUrls(R.id.foryou_iv,activityAndVideo.getAvurl());
        if (activityAndVideo.getAvmark() == 0){
            holder.setViewVisiable(R.id.play_mark,1);
        }else if (activityAndVideo.getAvmark() == 1){
            holder.setViewVisiable(R.id.play_mark,0);
        }

    }
}
