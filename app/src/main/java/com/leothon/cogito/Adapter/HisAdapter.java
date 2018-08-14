package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.Fav;
import com.leothon.cogito.Bean.VideoClass;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * created by leothon on 2018.8.10
 */
public class HisAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<VideoClass> videoClasses;
    public HisAdapter(Context context, ArrayList<VideoClass> videoClasses){
        this.context = context;
        this.videoClasses = videoClasses;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HisViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VideoClass videoClass = videoClasses.get(position);
        HisViewHolder hisViewHolder = (HisViewHolder) holder;


        ImageLoader.loadImageViewThumbnailwitherror(context,videoClass.getVideoUrl(),hisViewHolder.favImg,R.drawable.defalutimg);
        hisViewHolder.favTitle.setText(videoClass.getVideoTitle());
        hisViewHolder.favDes.setText(videoClass.getVideoDescription());
        hisViewHolder.favClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 删除该条浏览历史

                videoClasses.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,videoClasses.size());
            }
        });

        hisViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转相关页面
                Bundle bundle = new Bundle();
                bundle.putString("imgUrls",videoClasses.get(position).getVideoUrl());
                bundle.putString("imgTitle",videoClasses.get(position).getVideoTitle());
                IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
            }
        });


    }



    @Override
    public int getItemCount() {
        return videoClasses.size();
    }


    public  class HisViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fav_title_fav)
        TextView favTitle;
        @BindView(R.id.fav_img_fav)
        RoundedImageView favImg;
        @BindView(R.id.fav_description_fav)
        TextView favDes;

        @BindView(R.id.fav_clear)
        FrameLayout favClear;


        public HisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
