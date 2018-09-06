package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.makeramen.roundedimageview.RoundedImageView;
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

            if (!askdetails.getVideourl().equals("")){
                detailViewHolder.VideoPlayer.setVisibility(View.VISIBLE);
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //imageView.setImageResource(R.drawable.activityback);
                ImageLoader.loadImageViewThumbnailwitherror(context,askdetails.getCoverurl(),imageView,R.drawable.defalutimg);
                detailViewHolder.VideoPlayer.setThumbImageView(imageView);
                detailViewHolder.VideoPlayer.setUpLazy(askdetails.getVideourl(),true,null,null,"title");
                detailViewHolder.VideoPlayer.getTitleTextView().setVisibility(View.GONE);
                detailViewHolder.VideoPlayer.getBackButton().setVisibility(View.GONE);
                detailViewHolder.VideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        detailViewHolder.VideoPlayer.startWindowFullscreen(context,false,true);
                    }
                });
                detailViewHolder.VideoPlayer.setAutoFullWithSize(true);
                //音频焦点冲突时是否释放
                detailViewHolder.VideoPlayer.setReleaseWhenLossAudio(false);
                //全屏动画
                detailViewHolder.VideoPlayer.setShowFullAnimation(true);
                //小屏时不触摸滑动
                detailViewHolder.VideoPlayer.setIsTouchWiget(false);
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
