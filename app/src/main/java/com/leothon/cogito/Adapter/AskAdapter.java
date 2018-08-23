package com.leothon.cogito.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.HomeActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Fragment.AskPage.AskFragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.8
 */
public class AskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final String TAG = "adapter";
    private Context context;
    private ArrayList<Ask> asks;

    private StandardGSYVideoPlayer curPlayer;
    protected OrientationUtils orientationUtils;

    protected boolean isPlay;
    private LayoutInflater inflater;
    protected boolean isFull;

    public AskAdapter(Context context, ArrayList<Ask> asks){
        this.context = context;
        this.asks = asks;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AskViewHolder(LayoutInflater.from(context).inflate(R.layout.askitem,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Ask ask = asks.get(position);
        final AskViewHolder askViewHolder = (AskViewHolder) holder;

        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.loadImageViewwithError(context,ask.getCoverurl(),imageView,R.drawable.defalutimg);
        ImageLoader.loadImageViewThumbnailwitherror(context,ask.getUsericonurl(),askViewHolder.userIcon,R.drawable.defalutimg);
        askViewHolder.userName.setText(ask.getUsername());
        askViewHolder.userDes.setText(ask.getUserdes());
        askViewHolder.contentAsk.setText(ask.getContent());


        askViewHolder.gsyVideoPlayer.setThumbImageView(imageView);
        askViewHolder.gsyVideoPlayer.setUpLazy(ask.getVideourl(),true,null,null,"标题");
        askViewHolder.gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        askViewHolder.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
        askViewHolder.gsyVideoPlayer.getFullscreenButton().setVisibility(View.GONE);
        askViewHolder.gsyVideoPlayer.setPlayTag(TAG);
        askViewHolder.gsyVideoPlayer.setPlayPosition(position);
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        askViewHolder.gsyVideoPlayer.setAutoFullWithSize(true);
        //音频焦点冲突时是否释放
        askViewHolder.gsyVideoPlayer.setReleaseWhenLossAudio(false);
        //全屏动画
        askViewHolder.gsyVideoPlayer.setShowFullAnimation(true);
        //小屏时不触摸滑动
        askViewHolder.gsyVideoPlayer.setIsTouchWiget(false);

        askViewHolder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                CommonUtils.makeText(context,"个人主页");
            }
        });
        askViewHolder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                CommonUtils.makeText(context,"个人主页");
            }
        });
        askViewHolder.userDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                CommonUtils.makeText(context,"个人主页");
            }
        });


        askViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.getInstence().intent(context, AskDetailActivity.class);
            }
        });


    }



    public ImageView addImageView(){
        ImageView imageView = new ImageView(context);
        return imageView;
    }
    @Override
    public int getItemCount() {
        return asks.size();
    }


    public  class AskViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_icon_ask)
        RoundedImageView userIcon;
        @BindView(R.id.user_name_ask)
        TextView userName;
        @BindView(R.id.user_des_ask)
        TextView userDes;
        @BindView(R.id.content_ask)
        TextView contentAsk;



        @BindView(R.id.video_item_player)
        StandardGSYVideoPlayer gsyVideoPlayer;
        public AskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
