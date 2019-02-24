package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.CommentDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.3
 * 视频下用户评论Adapter
 */
public class VideoCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    private ArrayList<Comment> comments;


    private Context context;

    private OrientationUtils orientationUtils;

    private ImageView imageView;

    private static int COMPLETED = 1;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private String userId;

    private boolean isLogin;


    public AddVideoLikeCommentOnClickListener addVideoLikeCommentOnClickListener;

    public void setOnClickAddVideoLikeComment(AddVideoLikeCommentOnClickListener addVideoLikeCommentOnClickListener) {
        this.addVideoLikeCommentOnClickListener = addVideoLikeCommentOnClickListener;
    }
    public AddVideoLikeReplyOnClickListener addVideoLikeReplyOnClickListener;

    public void setVideoOnClickAddLikeReply(AddVideoLikeReplyOnClickListener addVideoLikeReplyOnClickListener) {
        this.addVideoLikeReplyOnClickListener = addVideoLikeReplyOnClickListener;
    }
    public SendVideoReplyOnClickListener sendVideoReplyOnClickListener;

    public void setVideoSendReplyOnClickListener(SendVideoReplyOnClickListener sendVideoReplyOnClickListener) {
        this.sendVideoReplyOnClickListener = sendVideoReplyOnClickListener;
    }

    public DeleteVideoCommentOnClickListener deleteVideoCommentOnClickListener;

    public void setVideoDeleteCommentOnClickListener(DeleteVideoCommentOnClickListener deleteVideoCommentOnClickListener) {
        this.deleteVideoCommentOnClickListener = deleteVideoCommentOnClickListener;
    }

    public DeleteVideoReplyOnClickListener deleteVideoReplyOnClickListener;

    public void setVideoDeleteReplyOnClickListener(DeleteVideoReplyOnClickListener deleteVideoReplyOnClickListener) {
        this.deleteVideoReplyOnClickListener = deleteVideoReplyOnClickListener;
    }
    public VideoCommentAdapter(ArrayList<Comment> comments, Context context,boolean isLogin){
        this.comments = comments;
        this.context = context;
        this.isLogin = isLogin;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        sharedPreferencesUtils = new SharedPreferencesUtils(context,"saveToken");
        userId = tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid();

        if (comments.size() != 0){
            final CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.commentTimeQa.setText(CommonUtils.getTimeRange(comments.get(position).getComment_q_time()));


            if (comments.get(position).getComment_q_like() == null && comments.get(position).getComment_q_like().equals("0")){
                commentViewHolder.commentLikeQa.setText("喜欢");
            }else {
                if (comments.get(position).isComment_liked()){
                    commentViewHolder.likeImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
                }
                commentViewHolder.commentLikeQa.setText(comments.get(position).getComment_q_like());
            }

            commentViewHolder.likeImgQa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (isLogin){
                        addVideoLikeCommentOnClickListener.addVideoLikeCommentClickListener(comments.get(position).isComment_liked(),comments.get(position).getComment_q_id());
                        if (!comments.get(position).isComment_liked()){
                            commentViewHolder.likeImgQa.setImageResource(R.drawable.baseline_favorite_black_18);

                            String like = commentViewHolder.commentLikeQa.getText().toString();
                            if (like.equals("喜欢")){
                                int likeint = 1;
                                commentViewHolder.commentLikeQa.setText(Integer.toString(likeint));
                            }else {
                                int likeint = Integer.parseInt(like) + 1;
                                commentViewHolder.commentLikeQa.setText(Integer.toString(likeint));
                            }
                            comments.get(position).setComment_liked(true);
                        }else {
                            commentViewHolder.likeImgQa.setImageResource(R.drawable.baseline_favorite_border_black_18);
                            String like = commentViewHolder.commentLikeQa.getText().toString();

                            int likeint = Integer.parseInt(like) - 1;
                            if (likeint == 0){
                                commentViewHolder.commentLikeQa.setText("喜欢");
                            }else {
                                commentViewHolder.commentLikeQa.setText(Integer.toString(likeint));
                            }
                            comments.get(position).setComment_liked(false);
                        }
                    }else {
                        CommonUtils.loadinglogin(context);
                    }
                }

            });

            commentViewHolder.commentMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLogin){
                        deleteVideoCommentOnClickListener.deleteVideoCommentClickListener(comments.get(position).getComment_q_id(),comments.get(position).getComment_q_user_id(),comments.get(position).getComment_q_content(),position);

                    }else {
                        CommonUtils.loadinglogin(context);
                    }
                }
            });
            if (comments.get(position).getReplies() != null && comments.get(position).getReplies().size() != 0){
                int replyCount = comments.get(position).getReplies().size();

                commentViewHolder.firstReply.setVisibility(View.VISIBLE);
                if (comments.get(position).getReplies().get(0).getReply_like() == null && comments.get(position).getReplies().get(0).getReply_like().equals("0")){
                    commentViewHolder.comment1LikeQa.setText("喜欢");
                }else {
                    if (comments.get(position).getReplies().get(0).isReply_liked()){
                        commentViewHolder.like1ImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
                    }
                    commentViewHolder.comment1LikeQa.setText(comments.get(position).getReplies().get(0).getReply_like());
                }
                ImageLoader.loadImageViewThumbnailwitherror(context,comments.get(position).getReplies().get(0).getUser_icon(),commentViewHolder.replyUserIcon1,R.drawable.defaulticon);
                commentViewHolder.comment1TimeQa.setText(CommonUtils.getTimeRange(comments.get(position).getReplies().get(0).getReply_time()));
                commentViewHolder.replyUserName1.setText(comments.get(position).getReplies().get(0).getUser_name());
                commentViewHolder.replyToUserName1.setText(comments.get(position).getReplies().get(0).getTo_user_name());
                commentViewHolder.replyComment1.setText(comments.get(position).getReplies().get(0).getReply_comment());

                commentViewHolder.like1ImgQa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isLogin){
                            addVideoLikeReplyOnClickListener.addVideoLikeReplyClickListener(comments.get(position).getReplies().get(0).isReply_liked(),comments.get(position).getReplies().get(0).getReply_id());
                            if (!comments.get(position).getReplies().get(0).isReply_liked()){
                                commentViewHolder.like1ImgQa.setImageResource(R.drawable.baseline_favorite_black_18);

                                String like = commentViewHolder.comment1LikeQa.getText().toString();
                                if (like.equals("喜欢")){
                                    int likeint = 1;
                                    commentViewHolder.comment1LikeQa.setText(Integer.toString(likeint));
                                }else {
                                    int likeint = Integer.parseInt(like) + 1;
                                    commentViewHolder.comment1LikeQa.setText(Integer.toString(likeint));
                                }
                                comments.get(position).getReplies().get(0).setReply_liked(true);
                            }else {
                                commentViewHolder.like1ImgQa.setImageResource(R.drawable.baseline_favorite_border_black_18);
                                String like = commentViewHolder.comment1LikeQa.getText().toString();

                                int likeint = Integer.parseInt(like) - 1;
                                if (likeint == 0){
                                    commentViewHolder.comment1LikeQa.setText("喜欢");
                                }else {
                                    commentViewHolder.comment1LikeQa.setText(Integer.toString(likeint));
                                }
                                comments.get(position).getReplies().get(0).setReply_liked(false);
                            }
                        }else {
                            CommonUtils.loadinglogin(context);
                        }
                    }
                });

                commentViewHolder.replyUserIcon1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundleto = new Bundle();
                        if (userId.equals(comments.get(position).getReplies().get(0).getReply_user_id())){
                            bundleto.putString("type","individual");
                            IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                        }else {
                            bundleto.putString("type","other");
                            bundleto.putString("userId",comments.get(position).getReplies().get(0).getReply_user_id());
                            IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                        }
                    }
                });

                commentViewHolder.reply1More.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isLogin){
                            deleteVideoReplyOnClickListener.deleteVideoReplyClickListener(comments.get(position).getReplies().get(0).getReply_id(),comments.get(position).getReplies().get(0).getReply_user_id(),comments.get(position).getReplies().get(0).getReply_comment(),position,0);

                        }else {
                            CommonUtils.loadinglogin(context);
                        }
                    }
                });

                commentViewHolder.firstReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isLogin){
                            commentViewHolder.moreComment.setVisibility(View.VISIBLE);
                            sendVideoReplyOnClickListener.sendVideoReplyClickListener(comments.get(position).getComment_q_id(),comments.get(position).getReplies().get(0).getReply_user_id(),comments.get(position).getReplies().get(0).getUser_name());

                        }else {
                            CommonUtils.loadinglogin(context);
                        }
                    }
                });
//                if (replyCount >= 2){
//                    commentViewHolder.firstReply.setVisibility(View.VISIBLE);
//                    commentViewHolder.secondReply.setVisibility(View.VISIBLE);
//                    commentViewHolder.moreComment.setVisibility(View.GONE);
//                    if (comments.get(position).getReplies().get(1).getReply_like() == null && comments.get(position).getReplies().get(1).getReply_like().equals("0")){
//                        commentViewHolder.comment2LikeQa.setText("喜欢");
//                    }else {
//                        if (comments.get(position).getReplies().get(1).isReply_liked()){
//                            commentViewHolder.like2ImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
//                        }
//                        commentViewHolder.comment2LikeQa.setText(comments.get(position).getReplies().get(1).getReply_like());
//                    }
//                    commentViewHolder.comment2TimeQa.setText(CommonUtils.getTimeRange(comments.get(position).getReplies().get(1).getReply_time()));
//                    commentViewHolder.comment2LikeQa.setText(comments.get(position).getReplies().get(1).getReply_like());
//                    ImageLoader.loadImageViewThumbnailwitherror(context,comments.get(position).getReplies().get(1).getUser_icon(),commentViewHolder.replyUserIcon1,R.drawable.defaulticon);
//                    commentViewHolder.replyUserName2.setText(comments.get(position).getReplies().get(1).getUser_name());
//                    commentViewHolder.replyToUserName2.setText(comments.get(position).getReplies().get(1).getTo_user_name());
//                    commentViewHolder.replyComment2.setText(comments.get(position).getReplies().get(1).getReply_comment());
//                    commentViewHolder.like2ImgQa.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (isLogin){
//                                addVideoLikeReplyOnClickListener.addVideoLikeReplyClickListener(comments.get(position).getReplies().get(1).isReply_liked(),comments.get(position).getReplies().get(1).getReply_id());
//                                if (!comments.get(position).getReplies().get(1).isReply_liked()){
//                                    commentViewHolder.like2ImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
//
//                                    String like = commentViewHolder.comment2LikeQa.getText().toString();
//                                    if (like.equals("喜欢")){
//                                        int likeint = 1;
//                                        commentViewHolder.comment2LikeQa.setText(Integer.toString(likeint));
//                                    }else {
//                                        int likeint = Integer.parseInt(like) + 1;
//                                        commentViewHolder.comment2LikeQa.setText(Integer.toString(likeint));
//                                    }
//                                    comments.get(position).getReplies().get(1).setReply_liked(true);
//                                }else {
//                                    commentViewHolder.like2ImgQa.setImageResource(R.drawable.baseline_favorite_border_black_18);
//                                    String like = commentViewHolder.comment2LikeQa.getText().toString();
//
//                                    int likeint = Integer.parseInt(like) - 1;
//                                    if (likeint == 0){
//                                        commentViewHolder.comment2LikeQa.setText("喜欢");
//                                    }else {
//                                        commentViewHolder.comment2LikeQa.setText(Integer.toString(likeint));
//                                    }
//                                    comments.get(position).getReplies().get(1).setReply_liked(false);
//                                }
//                            }else {
//                                CommonUtils.loadinglogin(context);
//                            }
//
//                        }
//                    });
//
//                    commentViewHolder.secondReply.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (isLogin){
//                                commentViewHolder.moreComment.setVisibility(View.VISIBLE);
//                                sendVideoReplyOnClickListener.sendVideoReplyClickListener(comments.get(position).getComment_q_id(),comments.get(position).getReplies().get(1).getReply_user_id(),comments.get(position).getReplies().get(1).getUser_name());
//
//                            }else {
//                                CommonUtils.loadinglogin(context);
//                            }
//                        }
//                    });
//
//                    commentViewHolder.reply2More.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (isLogin){
//                                deleteVideoReplyOnClickListener.deleteVideoReplyClickListener(comments.get(position).getReplies().get(1).getReply_id(),comments.get(position).getReplies().get(1).getReply_user_id(),comments.get(position).getReplies().get(1).getReply_comment(),position,1);
//
//                            }else {
//                                CommonUtils.loadinglogin(context);
//                            }
//                        }
//                    });
//
//                    commentViewHolder.replyUserIcon2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Bundle bundleto = new Bundle();
//                            if (userId.equals(comments.get(position).getReplies().get(0).getReply_user_id())){
//                                bundleto.putString("type","individual");
//                                IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
//                            }else {
//                                bundleto.putString("type","other");
//                                bundleto.putString("userId",comments.get(position).getReplies().get(0).getReply_user_id());
//                                IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
//                            }
//                        }
//                    });
                    if (replyCount > 1){
                        commentViewHolder.firstReply.setVisibility(View.VISIBLE);
                        //commentViewHolder.secondReply.setVisibility(View.VISIBLE);
                        commentViewHolder.moreComment.setVisibility(View.VISIBLE);
                    }



                commentViewHolder.moreComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isLogin){
                            Bundle bundle = new Bundle();
                            bundle.putString("commentId",comments.get(position).getComment_q_id());
                            IntentUtils.getInstence().intent(context,CommentDetailActivity.class,bundle);
                        }else {
                            CommonUtils.loadinglogin(context);
                        }

                    }
                });






            }

            ImageLoader.loadImageViewThumbnailwitherror(context, comments.get(position).getUser_icon(), commentViewHolder.userIconComment, R.drawable.defaulticon);
            commentViewHolder.userNameComment.setText(comments.get(position).getUser_name());
            commentViewHolder.userComment.setText(comments.get(position).getComment_q_content());
            commentViewHolder.commentTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLogin){
                        sendVideoReplyOnClickListener.sendVideoReplyClickListener(comments.get(position).getComment_q_id(),comments.get(position).getComment_q_user_id(),comments.get(position).getUser_name());
                        commentViewHolder.moreComment.setVisibility(View.VISIBLE);
                    }else {
                        CommonUtils.loadinglogin(context);
                    }

                }
            });
            commentViewHolder.userIconComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundleto = new Bundle();
                    if (userId.equals(comments.get(position).getComment_q_user_id())){
                        bundleto.putString("type","individual");
                        IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                    }else {
                        bundleto.putString("type","other");
                        bundleto.putString("userId",comments.get(position).getComment_q_user_id());
                        IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                    }
                }
            });
        }





    }




    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {

        return comments.size();


    }

    @Override
    public void onClick(View view) {

    }





    class CommentViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.user_icon_video)
        RoundedImageView userIconComment;
        @BindView(R.id.user_name_video)
        TextView userNameComment;
        @BindView(R.id.user_comment_video)
        TextView userComment;
        @BindView(R.id.more_comment)
        RelativeLayout moreComment;
        @BindView(R.id.reply_comment2)
        TextView replyComment2;
        @BindView(R.id.reply_to_user_name2)
        TextView replyToUserName2;
        @BindView(R.id.reply_user_name2)
        TextView replyUserName2;
        @BindView(R.id.reply_comment1)
        TextView replyComment1;
        @BindView(R.id.reply_to_user_name1)
        TextView replyToUserName1;
        @BindView(R.id.reply_user_name1)
        TextView replyUserName1;
        @BindView(R.id.reply_user_icon2)
        RoundedImageView replyUserIcon2;
        @BindView(R.id.reply_user_icon1)
        RoundedImageView replyUserIcon1;
        @BindView(R.id.first_reply)
        RelativeLayout firstReply;
        @BindView(R.id.second_reply)
        RelativeLayout secondReply;

        @BindView(R.id.comment_time_qa)
        TextView commentTimeQa;
        @BindView(R.id.comment1_time_qa)
        TextView comment1TimeQa;
        @BindView(R.id.comment2_time_qa)
        TextView comment2TimeQa;
        @BindView(R.id.like_img_qa)
        ImageView likeImgQa;
        @BindView(R.id.like1_img_qa)
        ImageView like1ImgQa;
        @BindView(R.id.like2_img_qa)
        ImageView like2ImgQa;
        @BindView(R.id.comment_like_qa)
        TextView commentLikeQa;
        @BindView(R.id.comment1_like_qa)
        TextView comment1LikeQa;
        @BindView(R.id.comment2_like_qa)
        TextView comment2LikeQa;

        @BindView(R.id.comment_more)
        ImageView commentMore;
        @BindView(R.id.reply1_more)
        ImageView reply1More;
        @BindView(R.id.reply2_more)
        ImageView reply2More;

        @BindView(R.id.comment_to)
        RelativeLayout commentTo;

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


    public interface AddVideoLikeCommentOnClickListener{
        void addVideoLikeCommentClickListener(boolean isLike,String commentId);
    }

    public interface AddVideoLikeReplyOnClickListener{
        void addVideoLikeReplyClickListener(boolean isLike,String replyId);
    }

    public interface SendVideoReplyOnClickListener{
        void sendVideoReplyClickListener(String commentId,String toUserId,String toUsername);
    }

    public interface DeleteVideoCommentOnClickListener{
        void deleteVideoCommentClickListener(String commentId,String commentUserId,String content,int position);
    }
    public interface DeleteVideoReplyOnClickListener{
        void deleteVideoReplyClickListener(String replyId,String replyUserId,String content,int commentPosition,int replyPosition);
    }

}
