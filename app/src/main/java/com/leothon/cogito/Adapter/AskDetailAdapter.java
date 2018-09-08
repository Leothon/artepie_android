package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.leothon.cogito.Bean.AskDetail;
import com.leothon.cogito.Bean.BagBuy;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 8/15/2018.
 */
public class AskDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private AskDetail askdetails;


    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;

    private boolean islike = false;

    private OrientationUtils orientationUtils;


    public AskDetailAdapter(AskDetail askdetails, Context context){
        this.askdetails = askdetails;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {
            return new DetailViewHolder(LayoutInflater.from(context).inflate(R.layout.ask_detail_head,parent,false));
        }else {
            return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD0 ) {
            final DetailViewHolder detailViewHolder = (DetailViewHolder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,askdetails.getUsericon(),detailViewHolder.userIcon,R.drawable.defalutimg);
            detailViewHolder.userName.setText(askdetails.getUsername());
            detailViewHolder.userDes.setText(askdetails.getUserdes());
            detailViewHolder.contentDetail.setText(askdetails.getContent());
            if (askdetails.getLike().equals("0")){
                detailViewHolder.likeDetail.setText("喜欢");
            }else {
                detailViewHolder.likeDetail.setText(askdetails.getLike());
            }
            if (askdetails.getComment().equals("0")){
                detailViewHolder.commentDetail.setText("评论");
            }else {
                detailViewHolder.commentDetail.setText(askdetails.getComment());
            }

            detailViewHolder.moreAskDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonUtils.makeText(context,"查看更多");
                }
            });
            detailViewHolder.contentDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 回调接口，实现点击滑到评论
                }
            });
//            detailViewHolder.likeDetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (!islike){
//
//                        Drawable drawableLeft = context.getResources().getDrawable(
//                                R.drawable.baseline_favorite_black_18);
//
//                        detailViewHolder.likeDetail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
//                                null, null, null);
//                        String like = detailViewHolder.likeDetail.getText().toString();
//                        if (like.equals("喜欢")){
//                            int likeint = 1;
//                            detailViewHolder.likeDetail.setText(Integer.toString(likeint));
//                        }else {
//                            int likeint = Integer.parseInt(like) + 1;
//                            detailViewHolder.likeDetail.setText(Integer.toString(likeint));
//                        }
//                        islike = true;
//                        //TODO 加载点赞
//                    }else {
//                        Drawable drawableLeft = context.getResources().getDrawable(
//                                R.drawable.baseline_favorite_border_black_18);
//
//                        detailViewHolder.likeDetail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
//                                null, null, null);
//                        String like = detailViewHolder.likeDetail.getText().toString();
//
//                        int likeint = Integer.parseInt(like) - 1;
//                        if (likeint == 0){
//                            detailViewHolder.likeDetail.setText("喜欢");
//                        }else {
//                            detailViewHolder.likeDetail.setText(Integer.toString(likeint));
//                        }
//                        islike = false;
//                        //TODO 取消点赞
//                    }
//                }
//            });
            if (!askdetails.getVideourl().equals("")){
                detailViewHolder.VideoPlayer.setVisibility(View.VISIBLE);


                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.loadImageViewwithError(context,askdetails.getCoverurl(),imageView,R.drawable.defalutimg);

                detailViewHolder.VideoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


                GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
                gsyVideoOption.setThumbImageView(imageView)
                        .setIsTouchWiget(true)
                        .setRotateViewAuto(true)
                        .setLockLand(false)
                        .setAutoFullWithSize(true)
                        .setShowFullAnimation(false)
                        .setNeedLockFull(true)
                        .setUrl(askdetails.getVideourl())
                        .setCacheWithPlay(false)
                        .setVideoTitle("")
                .build(detailViewHolder.VideoPlayer);

                detailViewHolder.VideoPlayer.getBackButton().setVisibility(View.GONE);
                detailViewHolder.VideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //orientationUtils.resolveByClick();
                        /**
                         *  bug描述：在本页中，不显示状态栏，但是全屏后，再返回会出现状态栏，根据本方法可知，传入两个参数，是否有状态栏和标题栏
                         *  默认传入两者都有，则程序执行时，会再退出全屏后重新生成状态栏，将此处两者设为没有（false)，则不会重新生成状态栏
                         */

                        detailViewHolder.VideoPlayer.startWindowFullscreen(context,false,true);
                    }
                });


            }else {
                detailViewHolder.VideoPlayer.setVisibility(View.GONE);
            }

            detailViewHolder.userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundleto = new Bundle();
                    bundleto.putString("type","other");
                    bundleto.putString("icon",askdetails.getUsericon());
                    bundleto.putString("name",askdetails.getUsername());
                    bundleto.putString("desc",askdetails.getUserdes());
                    IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                }
            });

        }else if(viewType == HEAD1){
            int position1 = position - 1;

            CommentViewHolder commentViewHolder = (CommentViewHolder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,askdetails.getUserComments().get(position1).getUsericon(),commentViewHolder.userIconComment,R.drawable.defalutimg);
            commentViewHolder.userNameComment.setText(askdetails.getUserComments().get(position1).getUsername());
            commentViewHolder.userComment.setText(askdetails.getUserComments().get(position1).getUsercomment());
            commentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 点击回复评论
                    CommonUtils.makeText(context,"暂不支持回复评论功能");
                }
            });
        }


    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0 ) {
            return HEAD0;
        }else {
            return HEAD1;
        }
    }


    @Override
    public int getItemCount() {
        return askdetails.getUserComments().size() + 1;
    }

    @Override
    public void onClick(View view) {

    }



    class DetailViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_icon_ask_detail)
        RoundedImageView userIcon;
        @BindView(R.id.user_name_ask_detail)
        TextView userName;
        @BindView(R.id.user_des_ask_detail)
        TextView userDes;
        @BindView(R.id.content_ask_detail)
        TextView contentDetail;
        @BindView(R.id.detail_video_player)
        StandardGSYVideoPlayer VideoPlayer;
        @BindView(R.id.more_ask_detail)
        ImageView moreAskDetail;
        @BindView(R.id.like_ask_detail)
        TextView likeDetail;
        @BindView(R.id.comment_ask_detail)
        TextView commentDetail;

        public DetailViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class CommentViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_icon_video)
        RoundedImageView userIconComment;
        @BindView(R.id.user_name_video)
        TextView userNameComment;
        @BindView(R.id.user_comment_video)
        TextView userComment;

        public CommentViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }




    public interface OnItemClickListener{
        void onItemClick(View v,int postion);
        void onItemLongClick(View v,int postion);
    }
    /**自定义条目点击监听*/
    private OnItemClickListener mOnItemClickLitener;

    public void setmOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
