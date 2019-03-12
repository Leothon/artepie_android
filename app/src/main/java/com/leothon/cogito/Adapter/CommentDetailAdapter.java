package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private CommentDetail  commentDetail;


    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private String userId;

    public AddLikeCommentDetailOnClickListener addLikeCommentDetailOnClickListener;

    public void setOnClickAddLikeCommentDetail(AddLikeCommentDetailOnClickListener addLikeCommentDetailOnClickListener) {
        this.addLikeCommentDetailOnClickListener = addLikeCommentDetailOnClickListener;
    }
    public AddLikeCommentReplyOnClickListener addLikeCommentReplyOnClickListener;

    public void setOnClickAddLikeCommentReply(AddLikeCommentReplyOnClickListener addLikeCommentReplyOnClickListener) {
        this.addLikeCommentReplyOnClickListener = addLikeCommentReplyOnClickListener;
    }

    public SendDetailReplyOnClickListener sendDetailReplyOnClickListener;

    public void setSendDetailReplyOnClickListener(SendDetailReplyOnClickListener sendDetailReplyOnClickListener) {
        this.sendDetailReplyOnClickListener = sendDetailReplyOnClickListener;
    }

    public DeleteDetailCommentOnClickListener deleteDetailCommentOnClickListener;

    public void setDeleteDetailCommentOnClickListener(DeleteDetailCommentOnClickListener deleteDetailCommentOnClickListener) {
        this.deleteDetailCommentOnClickListener = deleteDetailCommentOnClickListener;
    }

    public DeleteDetailReplyOnClickListener deleteDetailReplyOnClickListener;

    public void setDeleteDetailReplyOnClickListener(DeleteDetailReplyOnClickListener deleteDetailReplyOnClickListener) {
        this.deleteDetailReplyOnClickListener = deleteDetailReplyOnClickListener;
    }
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
            final CommentDetailViewHolder commentDetailViewHolder = (CommentDetailViewHolder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,commentDetail.getComment().getUser_icon(),commentDetailViewHolder.userIconCommentDetail,R.drawable.defaulticon);

            commentDetailViewHolder.userNameCommentDetail.setText(commentDetail.getComment().getUser_name());
            commentDetailViewHolder.userCommentCommentDetail.setText(commentDetail.getComment().getComment_q_content());
            commentDetailViewHolder.commentTimeCommentDetail.setText(CommonUtils.getTimeRange(commentDetail.getComment().getComment_q_time()));
            if (commentDetail.getComment().getComment_q_like() == null ){
                commentDetailViewHolder.commentLikeCommentDetail.setText("喜欢");
            }else {
                if (commentDetail.getComment().isComment_liked()){
                    commentDetailViewHolder.likeImgCommentDetail.setImageResource(R.drawable.baseline_favorite_black_18);
                }
                commentDetailViewHolder.commentLikeCommentDetail.setText(commentDetail.getComment().getComment_q_like());
            }
            commentDetailViewHolder.replyCountCommentDetail.setText(commentDetail.getReplies().size() + "条回复");

            commentDetailViewHolder.likeImgCommentDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addLikeCommentDetailOnClickListener.addLikeCommentDetailClickListener(commentDetail.getComment().isComment_liked(),commentDetail.getComment().getComment_q_id());
                    if (!commentDetail.getComment().isComment_liked()){
                        commentDetailViewHolder.likeImgCommentDetail.setImageResource(R.drawable.baseline_favorite_black_18);

                        String like = commentDetailViewHolder.commentLikeCommentDetail.getText().toString();
                        if (like.equals("喜欢")){
                            int likeint = 1;
                            commentDetailViewHolder.commentLikeCommentDetail.setText(Integer.toString(likeint));
                        }else {
                            int likeint = Integer.parseInt(like) + 1;
                            commentDetailViewHolder.commentLikeCommentDetail.setText(Integer.toString(likeint));
                        }
                        commentDetail.getComment().setComment_liked(true);
                    }else {
                        commentDetailViewHolder.likeImgCommentDetail.setImageResource(R.drawable.baseline_favorite_border_black_18);
                        String like =commentDetailViewHolder.commentLikeCommentDetail.getText().toString();

                        int likeint = Integer.parseInt(like) - 1;
                        if (likeint == 0){
                            commentDetailViewHolder.commentLikeCommentDetail.setText("喜欢");
                        }else {
                            commentDetailViewHolder.commentLikeCommentDetail.setText(Integer.toString(likeint));
                        }
                        commentDetail.getComment().setComment_liked(false);
                    }
                }
            });

            commentDetailViewHolder.userIconCommentDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundleto = new Bundle();
                    if (userId.equals(commentDetail.getComment().getComment_q_user_id())){
                        bundleto.putString("type","individual");
                        IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                    }else {
                        bundleto.putString("type","other");
                        bundleto.putString("userId",commentDetail.getComment().getComment_q_user_id());
                        IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                    }
                }
            });

            commentDetailViewHolder.detailCommentMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDetailCommentOnClickListener.deleteDetailCommentClickListener(commentDetail.getComment().getComment_q_id(),commentDetail.getComment().getComment_q_user_id(),commentDetail.getComment().getComment_q_content());
                }
            });
        }else if(viewType == HEAD1) {


            final int position1 = position - 1;

            final CommentDetailRvViewHolder commentDetailRvViewHolder = (CommentDetailRvViewHolder) holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,commentDetail.getReplies().get(position1).getUser_icon(),commentDetailRvViewHolder.replyUserIconCommentDetail,R.drawable.defaulticon);

            commentDetailRvViewHolder.replyUserNameCommentDetail.setText(commentDetail.getReplies().get(position1).getUser_name());
            commentDetailRvViewHolder.replyToUserNameCommentDetail.setText(commentDetail.getReplies().get(position1).getTo_user_name());
            commentDetailRvViewHolder.replyCommentCommentDetail.setText(commentDetail.getReplies().get(position1).getReply_comment());
            commentDetailRvViewHolder.commentTimeCommentDetail.setText(CommonUtils.getTimeRange(commentDetail.getReplies().get(position1).getReply_time()));
            commentDetailRvViewHolder.commentLikeCommentDetail.setText(commentDetail.getReplies().get(position1).getReply_like());

            if (commentDetail.getReplies().get(position1).getReply_like() == null){
                commentDetailRvViewHolder.commentLikeCommentDetail.setText("喜欢");
            }else {
                if (commentDetail.getReplies().get(position1).isReply_liked()){
                    commentDetailRvViewHolder.LikeImgCommentDetail.setImageResource(R.drawable.baseline_favorite_black_18);
                }
                commentDetailRvViewHolder.commentLikeCommentDetail.setText(commentDetail.getReplies().get(position1).getReply_like());
            }

            commentDetailRvViewHolder.LikeImgCommentDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addLikeCommentReplyOnClickListener.addLikeCommentReplyClickListener(commentDetail.getReplies().get(position1).isReply_liked(), commentDetail.getReplies().get(position1).getReply_id());
                    if (!commentDetail.getReplies().get(position1).isReply_liked()) {
                        commentDetailRvViewHolder.LikeImgCommentDetail.setImageResource(R.drawable.baseline_favorite_black_18);

                        String like = commentDetailRvViewHolder.commentLikeCommentDetail.getText().toString();
                        if (like.equals("喜欢")) {
                            int likeint = 1;
                            commentDetailRvViewHolder.commentLikeCommentDetail.setText(Integer.toString(likeint));
                        } else {
                            int likeint = Integer.parseInt(like) + 1;
                            commentDetailRvViewHolder.commentLikeCommentDetail.setText(Integer.toString(likeint));
                        }
                        commentDetail.getReplies().get(position1).setReply_liked(true);
                    } else {
                        commentDetailRvViewHolder.LikeImgCommentDetail.setImageResource(R.drawable.baseline_favorite_border_black_18);
                        String like = commentDetailRvViewHolder.commentLikeCommentDetail.getText().toString();

                        int likeint = Integer.parseInt(like) - 1;
                        if (likeint == 0) {
                            commentDetailRvViewHolder.commentLikeCommentDetail.setText("喜欢");
                        } else {
                            commentDetailRvViewHolder.commentLikeCommentDetail.setText(Integer.toString(likeint));
                        }
                        commentDetail.getReplies().get(position1).setReply_liked(false);
                    }

                }
            });
            commentDetailRvViewHolder.detailReplyMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDetailReplyOnClickListener.deleteDetailReplyClickListener(commentDetail.getReplies().get(position1).getReply_id(),commentDetail.getReplies().get(position1).getReply_user_id(),commentDetail.getReplies().get(position1).getReply_comment(),position1);
                }
            });

            commentDetailRvViewHolder.replyCommentRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendDetailReplyOnClickListener.sendDetailReplyClickListener(commentDetail.getComment().getComment_q_id(),commentDetail.getReplies().get(position1).getReply_user_id(),commentDetail.getReplies().get(position1).getUser_name());
                }
            });

            commentDetailRvViewHolder.replyUserIconCommentDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundleto = new Bundle();
                    if (userId.equals(commentDetail.getReplies().get(position1).getReply_user_id())){
                        bundleto.putString("type","individual");
                        IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                    }else {
                        bundleto.putString("type","other");
                        bundleto.putString("userId",commentDetail.getReplies().get(position1).getReply_user_id());
                        IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                    }
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

       @BindView(R.id.detail_comment_more)
       ImageView detailCommentMore;
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

        @BindView(R.id.detail_reply_more)
        ImageView detailReplyMore;
        @BindView(R.id.reply_comment_detail_rv)
        RelativeLayout replyCommentRl;

        public CommentDetailRvViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface AddLikeCommentDetailOnClickListener{
        void addLikeCommentDetailClickListener(boolean isLike,String commentId);
    }
    public interface AddLikeCommentReplyOnClickListener{
        void addLikeCommentReplyClickListener(boolean isLike,String replyId);
    }

    public interface SendDetailReplyOnClickListener{
        void sendDetailReplyClickListener(String commentId,String toUserId,String toUsername);
    }

    public interface DeleteDetailCommentOnClickListener{
        void deleteDetailCommentClickListener(String commentId,String commentUserId,String content);
    }
    public interface DeleteDetailReplyOnClickListener{
        void deleteDetailReplyClickListener(String replyId,String replyUserId,String content,int replyPosition);
    }
}
