package com.leothon.cogito.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.CustomShow;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;

public class CustomShowAdapter extends BaseAdapter {

    private Context context;
    public CustomShowAdapter(Context context, ArrayList<CustomShow> customShows){
        super(context, R.layout.custom_show_item,customShows);
        this.context = context;
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {
        CustomShow customShow = (CustomShow)bean;

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
        ImageView target = imageViewWeakReference.get();
        if (target != null) {
            ImageLoader.loadImageViewThumbnailwitherror(context, customShow.getVideoCoverUrl(), target, R.drawable.loading);
        }
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();

        if (CommonUtils.isHaveChar(customShow.getVideoUrl())){
            try{
                String url = URLEncoder.encode(customShow.getVideoUrl(),"utf-8").replaceAll("\\+", "%20");
                url = url.replaceAll("%3A", ":").replaceAll("%2F", "/");
                gsyVideoOption.setUrl(url);
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }else {
            gsyVideoOption.setUrl(customShow.getVideoUrl());
        }
        gsyVideoOption
                .setPlayPosition(position)
                .setThumbImageView(target)
                .setIsTouchWiget(true)
                .setRotateViewAuto(true)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setCacheWithPlay(false)
                .setVideoTitle("")
                .setThumbPlay(true)
                .build(holder.getVideoPlayer(R.id.custom_player));

        holder.getVideoPlayer(R.id.custom_player).getBackButton().setVisibility(View.GONE);
        holder.getVideoPlayer(R.id.custom_player).getFullscreenButton().setVisibility(View.GONE);
        holder.setText(R.id.custom_video_title,customShow.getVideoTitle());
    }
}
