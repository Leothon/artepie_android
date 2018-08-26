package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.Bean.Download;
import com.leothon.cogito.Bean.Fav;
import com.leothon.cogito.Mvp.View.Activity.DownloadActivity.DownloadDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
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
public class DownloadAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Download> downloads;
    private Animation operatingAnim;
    private LinearInterpolator linearInterpolator;
    private boolean isdownloading = true;
    public DownloadAdapter(Context context, ArrayList<Download> downloads){
        this.context = context;
        this.downloads = downloads;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownloadViewHolder(LayoutInflater.from(context).inflate(R.layout.download_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        loadAnim();
        Download download = downloads.get(position);
        final DownloadViewHolder downloadViewHolder = (DownloadViewHolder)holder;



        ImageLoader.loadImageViewThumbnailwitherror(context,download.getDownloadurl(),downloadViewHolder.downloadImg,R.drawable.defalutimg);
        downloadViewHolder.downloadTitle.setText(download.getDownloadtitle());
        downloadViewHolder.downloadStatus.setText("下载中，点击查看详情");
        if (operatingAnim != null){
            downloadViewHolder.remoteImg.startAnimation(operatingAnim);
        }

        downloadViewHolder.remoteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isdownloading){
                    downloadViewHolder.remoteImg.clearAnimation();
                    downloadViewHolder.downloadStatus.setText("下载完成");
                    isdownloading = false;
                }else {
                    downloadViewHolder.remoteImg.startAnimation(operatingAnim);
                    downloadViewHolder.downloadStatus.setText("下载中，点击查看详情");
                    isdownloading = true;
                }

            }
        });


        downloadViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转相关页面
//                Bundle bundle = new Bundle();
//                bundle.putString("url",downloads.get(position).getFavurl());
//                bundle.putString("title",downloads.get(position).getTitle());
//                bundle.putString("author",downloads.get(position).getAuthor());
                IntentUtils.getInstence().intent(context, DownloadDetailActivity.class);
            }
        });


    }



    public void loadAnim(){
        operatingAnim = AnimationUtils.loadAnimation(context,R.anim.remote_img);
        linearInterpolator = new LinearInterpolator();
        operatingAnim.setInterpolator(linearInterpolator);
    }

    @Override
    public int getItemCount() {
        return downloads.size();
    }


    public  class DownloadViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.download_page_img)
        RoundedImageView downloadImg;
        @BindView(R.id.download_title)
        TextView downloadTitle;
        @BindView(R.id.download_status)
        TextView downloadStatus;
        @BindView(R.id.remote_img)
        ImageView remoteImg;




        public DownloadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
