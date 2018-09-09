package com.leothon.cogito.Adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
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
    private boolean islike = false;
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


        ImageLoader.loadImageViewThumbnailwitherror(context,ask.getUsericonurl(),askViewHolder.userIcon,R.drawable.defalutimg);
        askViewHolder.userName.setText(ask.getUsername());
        askViewHolder.userDes.setText(ask.getUserdes());
        askViewHolder.contentAsk.setText(ask.getContent());
        if (ask.getLikecount().equals("0")){
            askViewHolder.likeAsk.setText("喜欢");
        }else {
            askViewHolder.likeAsk.setText(ask.getLikecount());
        }
        if (ask.getCommentcount().equals("0")){
            askViewHolder.commentAsk.setText("评论");
        }else {
            askViewHolder.commentAsk.setText(ask.getCommentcount());
        }
        if (!ask.getVideourl().equals("")) {
            askViewHolder.gsyVideoPlayer.setVisibility(View.VISIBLE);
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setImageResource(R.drawable.activityback);
            ImageLoader.loadImageViewThumbnailwitherror(context, ask.getCoverurl(), imageView, R.drawable.defalutimg);


            GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
            gsyVideoOption.setThumbImageView(imageView)
                    .setIsTouchWiget(true)
                    .setRotateViewAuto(true)
                    .setLockLand(false)
                    .setAutoFullWithSize(true)
                    .setShowFullAnimation(false)
                    .setNeedLockFull(true)
                    .setUrl(ask.getVideourl())
                    .setCacheWithPlay(false)
                    .setVideoTitle("")
            .build(askViewHolder.gsyVideoPlayer);
            askViewHolder.gsyVideoPlayer.getFullscreenButton().setVisibility(View.GONE);
            askViewHolder.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);

        }else {
            askViewHolder.gsyVideoPlayer.setVisibility(View.GONE);
        }

        askViewHolder.moreAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.makeText(context,"显示更多信息");
            }
        });
        askViewHolder.commentAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleto = new Bundle();
                bundleto.putString("icon",ask.getUsericonurl());
                bundleto.putString("name",ask.getUsername());
                bundleto.putString("desc",ask.getUserdes());
                bundleto.putString("content",ask.getContent());
                bundleto.putString("video",ask.getVideourl());
                bundleto.putString("cover",ask.getCoverurl());
                bundleto.putString("like",ask.getLikecount());
                bundleto.putString("comment",ask.getCommentcount());
                IntentUtils.getInstence().intent(context, AskDetailActivity.class,bundleto);
            }
        });

        askViewHolder.likeAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!islike){

                    Drawable drawableLeft = context.getResources().getDrawable(
                            R.drawable.baseline_favorite_black_18);

                    askViewHolder.likeAsk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    String like = askViewHolder.likeAsk.getText().toString();
                    if (like.equals("喜欢")){
                        int likeint = 1;
                        askViewHolder.likeAsk.setText(Integer.toString(likeint));
                    }else {
                        int likeint = Integer.parseInt(like) + 1;
                        askViewHolder.likeAsk.setText(Integer.toString(likeint));
                    }
                    islike = true;
                    //TODO 加载点赞
                }else {
                    Drawable drawableLeft = context.getResources().getDrawable(
                            R.drawable.baseline_favorite_border_black_18);

                    askViewHolder.likeAsk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    String like = askViewHolder.likeAsk.getText().toString();

                    int likeint = Integer.parseInt(like) - 1;
                    if (likeint == 0){
                        askViewHolder.likeAsk.setText("喜欢");
                    }else {
                        askViewHolder.likeAsk.setText(Integer.toString(likeint));
                    }
                    islike = false;
                    //TODO 取消点赞
                }
            }
        });
        askViewHolder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                Bundle bundleto = new Bundle();
                bundleto.putString("type","other");
                bundleto.putString("icon",ask.getUsericonurl());
                bundleto.putString("name",ask.getUsername());
                bundleto.putString("desc",ask.getUserdes());
                IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
            }
        });
        askViewHolder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                Bundle bundleto = new Bundle();
                bundleto.putString("type","other");
                bundleto.putString("icon",ask.getUsericonurl());
                bundleto.putString("name",ask.getUsername());
                bundleto.putString("desc",ask.getUserdes());
                IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
            }
        });
        askViewHolder.userDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                Bundle bundleto = new Bundle();
                bundleto.putString("type","other");
                bundleto.putString("icon",ask.getUsericonurl());
                bundleto.putString("name",ask.getUsername());
                bundleto.putString("desc",ask.getUserdes());
                IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
            }
        });


        askViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleto = new Bundle();
                bundleto.putString("icon",ask.getUsericonurl());
                bundleto.putString("name",ask.getUsername());
                bundleto.putString("desc",ask.getUserdes());
                bundleto.putString("content",ask.getContent());
                bundleto.putString("video",ask.getVideourl());
                bundleto.putString("cover",ask.getCoverurl());
                bundleto.putString("like",ask.getLikecount());
                bundleto.putString("comment",ask.getCommentcount());
                IntentUtils.getInstence().intent(context, AskDetailActivity.class,bundleto);

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
        @BindView(R.id.like_ask)
        TextView likeAsk;
        @BindView(R.id.comment_ask)
        TextView commentAsk;
        @BindView(R.id.more_ask)
        ImageView moreAsk;



        @BindView(R.id.video_item_player)
        StandardGSYVideoPlayer gsyVideoPlayer;
        public AskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
