package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private CommentDetail  commentDetail;


    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;

    private boolean islike = false;




    private SharedPreferencesUtils sharedPreferencesUtils;
    private String userId;

    public CommentDetailAdapter(CommentDetail  commentDetail, Context context){
        this.commentDetail = commentDetail;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {
            return new CommentDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_detail_item,parent,false));
        }else {
            return new CommentDetailRvViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_detail_rv_item, parent, false));


        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        sharedPreferencesUtils = new SharedPreferencesUtils(context,"saveToken");
        userId = tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid();
        if (viewType == HEAD0 ) {
            CommentDetailViewHolder commentDetailViewHolder = (CommentDetailViewHolder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,commentDetail.getComment().getUser_icon(),commentDetailViewHolder.userIconCommentDetail,R.drawable.defaulticon);

            commentDetailViewHolder.userNameCommentDetail.setText(commentDetail.getComment().getUser_name());
            commentDetailViewHolder.userCommentCommentDetail.setText(commentDetail.getComment().getComment_q_content());
            commentDetailViewHolder.commentTimeCommentDetail.setText(CommonUtils.getTimeRange(commentDetail.getComment().getComment_q_time()));
            commentDetailViewHolder.commentLikeCommentDetail.setText(commentDetail.getComment().getComment_q_like());
            //commentDetailViewHolder.likeImgCommentDetail;
            commentDetailViewHolder.replyCountCommentDetail.setText(commentDetail.getReplies().size() + "条回复");

        }else if(viewType == HEAD1) {


            int position1 = position - 1;

            CommentDetailRvViewHolder commentDetailRvViewHolder = (CommentDetailRvViewHolder) holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,commentDetail.getReplies().get(position1).getUser_icon(),commentDetailRvViewHolder.replyUserIconCommentDetail,R.drawable.defaulticon);

            commentDetailRvViewHolder.replyUserNameCommentDetail.setText(commentDetail.getReplies().get(position1).getUser_name());
            commentDetailRvViewHolder.replyToUserNameCommentDetail.setText(commentDetail.getReplies().get(position1).getTo_user_name());
            commentDetailRvViewHolder.replyCommentCommentDetail.setText(commentDetail.getReplies().get(position1).getReply_comment());
            commentDetailRvViewHolder.commentTimeCommentDetail.setText(CommonUtils.getTimeRange(commentDetail.getReplies().get(position1).getReply_time()));
            commentDetailRvViewHolder.commentLikeCommentDetail.setText(commentDetail.getReplies().get(position1).getReply_like());
            //commentDetailRvViewHolder.LikeImgCommentDetail;

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

        return commentDetail.getReplies().size() + 1;


    }

    @Override
    public void onClick(View view) {

    }



    class CommentDetailViewHolder extends RecyclerView.ViewHolder{

       @BindView(R.id.user_icon_video_comment_detail)
       RoundedImageView userIconCommentDetail;
       @BindView(R.id.user_name_video_comment_detail)
       TextView userNameCommentDetail;
       @BindView(R.id.user_comment_video_comment_detail)
       TextView userCommentCommentDetail;
       @BindView(R.id.comment_time_qa_comment_detail)
       TextView commentTimeCommentDetail;
       @BindView(R.id.comment_like_qa_comment_detail)
       TextView commentLikeCommentDetail;
       @BindView(R.id.like_img_qa_comment_detail)
       ImageView likeImgCommentDetail;
       @BindView(R.id.reply_count_comment_detail)
       TextView replyCountCommentDetail;

        public CommentDetailViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class CommentDetailRvViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.reply_user_icon_comment_detail_rv)
        RoundedImageView replyUserIconCommentDetail;
        @BindView(R.id.reply_user_name_comment_detail_rv)
        TextView replyUserNameCommentDetail;
        @BindView(R.id.reply_to_user_name_comment_detail_rv)
        TextView replyToUserNameCommentDetail;
        @BindView(R.id.reply_comment_comment_detail_rv)
        TextView replyCommentCommentDetail;
        @BindView(R.id.comment_time_qa_comment_detail_rv)
        TextView commentTimeCommentDetail;
        @BindView(R.id.comment_like_qa_comment_detail_rv)
        TextView commentLikeCommentDetail;
        @BindView(R.id.like_img_qa_comment_detail_rv)
        ImageView LikeImgCommentDetail;


        public CommentDetailRvViewHolder(View itemView){
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
