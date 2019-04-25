package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.Mvp.View.Activity.AskActivity.AskActivity;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.CommentDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.AuthView;
import com.leothon.cogito.View.EPieVideoPlayer;
import com.leothon.cogito.View.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 8/15/2018.
 */
public class AskDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private QADataDetail qaDataDetail;

    private final int EMPTY_VIEW = 2;

    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;


    private SharedPreferencesUtils sharedPreferencesUtils;
    private String userId;

    public AddLikeDetailOnClickListener addLikeDetailOnClickListener;
    public void setOnClickAddLikeDetail(AddLikeDetailOnClickListener addLikeDetailOnClickListener) {
        this.addLikeDetailOnClickListener = addLikeDetailOnClickListener;
    }

    public AddLikeCommentOnClickListener addLikeCommentOnClickListener;

    public void setOnClickAddLikeComment(AddLikeCommentOnClickListener addLikeCommentOnClickListener) {
        this.addLikeCommentOnClickListener = addLikeCommentOnClickListener;
    }
    public AddLikeReplyOnClickListener addLikeReplyOnClickListener;

    public void setOnClickAddLikeReply(AddLikeReplyOnClickListener addLikeReplyOnClickListener) {
        this.addLikeReplyOnClickListener = addLikeReplyOnClickListener;
    }
    public SendReplyOnClickListener sendReplyOnClickListener;

    public void setSendReplyOnClickListener(SendReplyOnClickListener sendReplyOnClickListener) {
        this.sendReplyOnClickListener = sendReplyOnClickListener;
    }

    public DeleteCommentOnClickListener deleteCommentOnClickListener;

    public void setDeleteCommentOnClickListener(DeleteCommentOnClickListener deleteCommentOnClickListener) {
        this.deleteCommentOnClickListener = deleteCommentOnClickListener;
    }

    public DeleteReplyOnClickListener deleteReplyOnClickListener;

    public void setDeleteReplyOnClickListener(DeleteReplyOnClickListener deleteReplyOnClickListener) {
        this.deleteReplyOnClickListener = deleteReplyOnClickListener;
    }

    public DeleteQaDetailOnClickListener deleteQaDetailOnClickListener;

    public void setDeleteQaDetailOnClickListener(DeleteQaDetailOnClickListener deleteQaDetailOnClickListener) {
        this.deleteQaDetailOnClickListener = deleteQaDetailOnClickListener;
    }
    public AskDetailAdapter(QADataDetail  qaDataDetail, Context context){
        this.qaDataDetail = qaDataDetail;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        if (viewType == EMPTY_VIEW){
//
//            return new EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.empty_view, parent, false));
//
//        }
        if (viewType == HEAD0) {
            return new DetailViewHolder(LayoutInflater.from(context).inflate(R.layout.ask_detail_head,parent,false));
        }else {

            return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        sharedPreferencesUtils = new SharedPreferencesUtils(context, "saveToken");
        userId = tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token", "").toString()).getUid();
        if (viewType == HEAD0) {
            final DetailViewHolder detailViewHolder = (DetailViewHolder) holder;
            ImageLoader.loadImageViewThumbnailwitherror(context, qaDataDetail.getQaData().getUser_icon(), detailViewHolder.userIcon, R.drawable.defaulticon);
            detailViewHolder.userName.setText(qaDataDetail.getQaData().getUser_name());
            int role = CommonUtils.isVIP(qaDataDetail.getQaData().getUser_role());
            if (role != 2){
                detailViewHolder.authMark.setVisibility(View.VISIBLE);
                if (role == 0){
                    detailViewHolder.authMark.setColor(Color.parseColor("#f26402"));
                }else if (role == 1){
                    detailViewHolder.authMark.setColor(Color.parseColor("#2298EF"));
                }else {
                    detailViewHolder.authMark.setVisibility(View.GONE);
                    detailViewHolder.userDes.setText(qaDataDetail.getQaData().getUser_signal());
                }
                detailViewHolder.userDes.setText("认证：" + qaDataDetail.getQaData().getUser_role().substring(1));

            }else {
                detailViewHolder.authMark.setVisibility(View.GONE);
                detailViewHolder.userDes.setText(qaDataDetail.getQaData().getUser_signal());
            }

            detailViewHolder.qaDetailTime.setText(CommonUtils.getTimeRange(qaDataDetail.getQaData().getQa_time()));
            String re = qaDataDetail.getQaData().getQa_content();
            SpannableString spannableString = new SpannableString(re);
//            if (re.contains("@") && re.contains(":")){
//
//                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#2298EF"));
//                spannableString.setSpan(colorSpan, re.indexOf("@"),re.indexOf(":"), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//            }




            detailViewHolder.contentDetail.setText(spannableString);



            if (qaDataDetail.getQaData().getQa_like() == null && qaDataDetail.getQaData().getQa_like().equals("0")) {
                detailViewHolder.likeDetail.setText("喜欢");
            } else {
                if (qaDataDetail.getQaData().isLiked()) {
                    Drawable drawableLeft = context.getResources().getDrawable(
                            R.drawable.baseline_favorite_black_18);
                    drawableLeft.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    detailViewHolder.likeDetail.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    detailViewHolder.likeDetail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                }
                detailViewHolder.likeDetail.setText(qaDataDetail.getQaData().getQa_like());
            }

            if (qaDataDetail.getQaData().getQa_comment() == null && qaDataDetail.getQaData().getQa_comment().equals("0")) {
                detailViewHolder.commentDetail.setText("评论");
            } else {
                detailViewHolder.commentDetail.setText(qaDataDetail.getQaData().getQa_comment());
            }

            detailViewHolder.moreAskDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteQaDetailOnClickListener.deleteQaDetailClickListener(qaDataDetail.getQaData().getQa_id(),qaDataDetail.getQaData().getQa_user_id(),qaDataDetail.getQaData().getQa_content());
                }
            });
            detailViewHolder.contentDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            detailViewHolder.likeDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addLikeDetailOnClickListener.addLikeDetailClickListener(qaDataDetail.getQaData().isLiked(), qaDataDetail.getQaData().getQa_id());
                    if (!qaDataDetail.getQaData().isLiked()) {

                        Drawable drawableLeft = context.getResources().getDrawable(
                                R.drawable.baseline_favorite_black_18);
                        drawableLeft.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                        detailViewHolder.likeDetail.setTextColor(context.getResources().getColor(R.color.colorAccent));
                        detailViewHolder.likeDetail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                null, null, null);
                        String like = detailViewHolder.likeDetail.getText().toString();
                        if (like.equals("喜欢")) {
                            int likeint = 1;
                            detailViewHolder.likeDetail.setText(Integer.toString(likeint));
                        } else {
                            int likeint = Integer.parseInt(like) + 1;
                            detailViewHolder.likeDetail.setText(Integer.toString(likeint));
                        }
                        qaDataDetail.getQaData().setLiked(true);
                    } else {
                        Drawable drawableLeft = context.getResources().getDrawable(
                                R.drawable.baseline_favorite_border_black_18);
                        detailViewHolder.likeDetail.setTextColor(context.getResources().getColor(R.color.fontColor));
                        detailViewHolder.likeDetail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                null, null, null);
                        String like = detailViewHolder.likeDetail.getText().toString();

                        int likeint = Integer.parseInt(like) - 1;
                        if (likeint == 0) {
                            detailViewHolder.likeDetail.setText("喜欢");
                        } else {
                            detailViewHolder.likeDetail.setText(Integer.toString(likeint));
                        }
                        qaDataDetail.getQaData().setLiked(false);
                    }
                }
            });
            if (qaDataDetail.getQaData().getQa_video() != null) {
                detailViewHolder.VideoPlayer.setVisibility(View.VISIBLE);

                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
                ImageView target = imageViewWeakReference.get();
                if (target != null) {
                    ImageLoader.loadImageViewThumbnailwitherror(context, qaDataDetail.getQaData().getQa_video_cover(), target, R.drawable.defalutimg);
                }






                detailViewHolder.VideoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


                GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();

                if (CommonUtils.isHaveChar(qaDataDetail.getQaData().getQa_video())){
                    try{
                        String url = URLEncoder.encode(qaDataDetail.getQaData().getQa_video(),"utf-8").replaceAll("\\+", "%20");
                        url = url.replaceAll("%3A", ":").replaceAll("%2F", "/");
                        gsyVideoOption.setUrl(url);
                    }catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                }else {
                    gsyVideoOption.setUrl(qaDataDetail.getQaData().getQa_video());
                }

                gsyVideoOption
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

                        detailViewHolder.VideoPlayer.startWindowFullscreen(context, false, true);
                        GSYVideoManager.instance().setNeedMute(false);
                    }
                });


            } else {
                detailViewHolder.VideoPlayer.setVisibility(View.GONE);
            }


            detailViewHolder.userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundleto = new Bundle();
                    if (userId.equals(qaDataDetail.getQaData().getQa_user_id())) {
                        bundleto.putString("type", "individual");
                        IntentUtils.getInstence().intent(context, IndividualActivity.class, bundleto);
                    } else {
                        bundleto.putString("type", "other");
                        bundleto.putString("userId", qaDataDetail.getQaData().getQa_user_id());
                        IntentUtils.getInstence().intent(context, IndividualActivity.class, bundleto);
                    }

                }
            });
            if (qaDataDetail.getQaData().getQaData() != null){
                detailViewHolder.reContentLL.setVisibility(View.VISIBLE);
                final QAData reShowQA = qaDataDetail.getQaData().getQaData();
                detailViewHolder.reUserName.setText("@" + reShowQA.getUser_name());
                detailViewHolder.reContent.setText(reShowQA.getQa_content());
                if (reShowQA.getQa_like().equals("") && reShowQA.getQa_like() == null){
                    detailViewHolder.reLike.setText("喜欢：0");
                }else {
                    detailViewHolder.reLike.setText("喜欢：" + reShowQA.getQa_like());
                }
                if (reShowQA.getQa_comment().equals("") && reShowQA.getQa_comment() == null){
                    detailViewHolder.reComment.setText("评论：0");
                }else {
                    detailViewHolder.reComment.setText("评论：" + reShowQA.getQa_like());
                }
                if (reShowQA.getQa_video() != null) {
                    detailViewHolder.reVideo.setVisibility(View.VISIBLE);

                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
                    ImageView target = imageViewWeakReference.get();
                    if (target != null) {
                        ImageLoader.loadImageViewThumbnailwitherror(context, reShowQA.getQa_video_cover(), target, R.drawable.defalutimg);
                    }


                    GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();



                    if (CommonUtils.isHaveChar(reShowQA.getQa_video())){
                        try{
                            String url = URLEncoder.encode(reShowQA.getQa_video(),"utf-8").replaceAll("\\+", "%20");
                            url = url.replaceAll("%3A", ":").replaceAll("%2F", "/");
                            gsyVideoOption.setUrl(url);
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                    }else {
                        gsyVideoOption.setUrl(reShowQA.getQa_video());
                    }
                    gsyVideoOption
                            .setThumbImageView(target)
                            .setIsTouchWiget(true)
                            .setRotateViewAuto(true)
                            .setLockLand(false)
                            .setAutoFullWithSize(true)
                            .setShowFullAnimation(false)
                            .setNeedLockFull(true)
                            .setUrl(reShowQA.getQa_video())
                            .setCacheWithPlay(false)
                            .setVideoTitle("")
                            .setThumbPlay(true)
                            .build(detailViewHolder.reVideo);
                    //GSYVideoManager.instance().setNeedMute(true);
                    //detailViewHolder.reVideo.getFullscreenButton().setVisibility(View.GONE);
                    detailViewHolder.reVideo.getBackButton().setVisibility(View.GONE);

                    detailViewHolder.reVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //orientationUtils.resolveByClick();
                            /**
                             *  bug描述：在本页中，不显示状态栏，但是全屏后，再返回会出现状态栏，根据本方法可知，传入两个参数，是否有状态栏和标题栏
                             *  默认传入两者都有，则程序执行时，会再退出全屏后重新生成状态栏，将此处两者设为没有（false)，则不会重新生成状态栏
                             */


                            detailViewHolder.reVideo.startWindowFullscreen(context, false, true);
                            GSYVideoManager.instance().setNeedMute(false);
                        }
                    });
                }else {
                    detailViewHolder.reVideo.setVisibility(View.GONE);
                }

                detailViewHolder.reContentLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundleto = new Bundle();

                        if (reShowQA.getQa_user_id() == null){
                            MyToast.getInstance(context).show("内容已被删除",Toast.LENGTH_SHORT);
                        }else {
                            bundleto.putString("qaId",reShowQA.getQa_id());
                            IntentUtils.getInstence().intent(context, AskDetailActivity.class,bundleto);
                        }

                    }
                });
            }else {
                detailViewHolder.reContentLL.setVisibility(View.GONE);
            }

            detailViewHolder.reBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (qaDataDetail.getQaData().getQaData() != null){
                        if (qaDataDetail.getQaData().getQaData().getQa_user_id() != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("type","re");
                            if (qaDataDetail.getQaData().getQaData() != null){
                                bundle.putString("qaId",qaDataDetail.getQaData().getQaData().getQa_id());

                            }else {
                                bundle.putString("qaId","");
                            }

                            bundle.putString("id",qaDataDetail.getQaData().getQa_id());
                            IntentUtils.getInstence().intent(context,AskActivity.class,bundle);
                        }else {
                            MyToast.getInstance(context).show("原问题已被删除，不可转发",Toast.LENGTH_SHORT);
                        }
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("type","re");
                        if (qaDataDetail.getQaData().getQaData() != null){
                            bundle.putString("qaId",qaDataDetail.getQaData().getQaData().getQa_id());

                        }else {
                            bundle.putString("qaId","");
                        }

                        bundle.putString("id",qaDataDetail.getQaData().getQa_id());
                        IntentUtils.getInstence().intent(context,AskActivity.class,bundle);
                    }


                }
            });
        } else if (viewType == HEAD1) {


            final int position1 = position - 1;

            final CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.commentTimeQa.setText(CommonUtils.getTimeRange(qaDataDetail.getComments().get(position1).getComment_q_time()));


            if (qaDataDetail.getComments().get(position1).getComment_q_like() == null && qaDataDetail.getComments().get(position1).getComment_q_like().equals("0")) {
                commentViewHolder.commentLikeQa.setText("喜欢");
                Drawable drawableLeft = context.getResources().getDrawable(
                        R.drawable.baseline_favorite_border_black_18);
                drawableLeft.setColorFilter(context.getResources().getColor(R.color.fontColor), PorterDuff.Mode.SRC_IN);
                commentViewHolder.commentLikeQa.setTextColor(context.getResources().getColor(R.color.fontColor));
                commentViewHolder.commentLikeQa.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
            } else {
                if (qaDataDetail.getComments().get(position1).isComment_liked()) {
                    commentViewHolder.likeImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
                    commentViewHolder.likeImgQa.setColorFilter(context.getResources().getColor(R.color.colorAccent));
                    commentViewHolder.commentLikeQa.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }
                commentViewHolder.commentLikeQa.setText(qaDataDetail.getComments().get(position1).getComment_q_like());
            }

            commentViewHolder.likeImgQa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addLikeCommentOnClickListener.addLikeCommentClickListener(qaDataDetail.getComments().get(position1).isComment_liked(), qaDataDetail.getComments().get(position1).getComment_q_id());
                    if (!qaDataDetail.getComments().get(position1).isComment_liked()) {
                        commentViewHolder.likeImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
                        commentViewHolder.likeImgQa.setColorFilter(context.getResources().getColor(R.color.colorAccent));
                        commentViewHolder.commentLikeQa.setTextColor(context.getResources().getColor(R.color.colorAccent));
                        String like = commentViewHolder.commentLikeQa.getText().toString();
                        if (like.equals("喜欢")) {
                            int likeint = 1;
                            commentViewHolder.commentLikeQa.setText(Integer.toString(likeint));
                        } else {
                            int likeint = Integer.parseInt(like) + 1;
                            commentViewHolder.commentLikeQa.setText(Integer.toString(likeint));
                        }
                        qaDataDetail.getComments().get(position1).setComment_liked(true);
                    } else {
                        commentViewHolder.likeImgQa.setImageResource(R.drawable.baseline_favorite_border_black_18);
                        String like = commentViewHolder.commentLikeQa.getText().toString();
                        commentViewHolder.commentLikeQa.setTextColor(context.getResources().getColor(R.color.fontColor));
                        int likeint = Integer.parseInt(like) - 1;
                        if (likeint == 0) {
                            commentViewHolder.commentLikeQa.setText("喜欢");
                        } else {
                            commentViewHolder.commentLikeQa.setText(Integer.toString(likeint));
                        }
                        qaDataDetail.getComments().get(position1).setComment_liked(false);
                    }
                }
            });

            commentViewHolder.commentMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    deleteCommentOnClickListener.deleteCommentClickListener(qaDataDetail.getComments().get(position1).getComment_q_id(), qaDataDetail.getComments().get(position1).getComment_q_user_id(), qaDataDetail.getComments().get(position1).getComment_q_content(), position1);
                }
            });
            if (qaDataDetail.getComments().get(position1).getReplies() != null && qaDataDetail.getComments().get(position1).getReplies().size() != 0) {
                int replyCount = qaDataDetail.getComments().get(position1).getReplies().size();

                commentViewHolder.firstReply.setVisibility(View.VISIBLE);
                if (qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_like() == null && qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_like().equals("0")) {
                    commentViewHolder.comment1LikeQa.setText("喜欢");
                    Drawable drawableLeft = context.getResources().getDrawable(
                            R.drawable.baseline_favorite_border_black_18);
                    drawableLeft.setColorFilter(context.getResources().getColor(R.color.fontColor), PorterDuff.Mode.SRC_IN);
                    commentViewHolder.comment1LikeQa.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    commentViewHolder.comment1LikeQa.setTextColor(context.getResources().getColor(R.color.fontColor));
                } else {
                    if (qaDataDetail.getComments().get(position1).getReplies().get(0).isReply_liked()) {
                        commentViewHolder.like1ImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
                        commentViewHolder.like1ImgQa.setColorFilter(context.getResources().getColor(R.color.colorAccent));
                        commentViewHolder.comment1LikeQa.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    }
                    commentViewHolder.comment1LikeQa.setText(qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_like());
                }
                ImageLoader.loadImageViewThumbnailwitherror(context, qaDataDetail.getComments().get(position1).getReplies().get(0).getUser_icon(), commentViewHolder.replyUserIcon1, R.drawable.defaulticon);
                commentViewHolder.comment1TimeQa.setText(CommonUtils.getTimeRange(qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_time()));
                if (qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_user_id().equals(qaDataDetail.getQaData().getQa_user_id())){
                    commentViewHolder.replyUserName1.setText(qaDataDetail.getComments().get(position1).getReplies().get(0).getUser_name() + "[作者]");
                }else {
                    commentViewHolder.replyUserName1.setText(qaDataDetail.getComments().get(position1).getReplies().get(0).getUser_name());
                }

                commentViewHolder.replyToUserName1.setText(qaDataDetail.getComments().get(position1).getReplies().get(0).getTo_user_name());
                commentViewHolder.replyComment1.setText(qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_comment());

                commentViewHolder.like1ImgQa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addLikeReplyOnClickListener.addLikeReplyClickListener(qaDataDetail.getComments().get(position1).getReplies().get(0).isReply_liked(), qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_id());
                        if (!qaDataDetail.getComments().get(position1).getReplies().get(0).isReply_liked()) {
                            commentViewHolder.like1ImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
                            commentViewHolder.like1ImgQa.setColorFilter(context.getResources().getColor(R.color.colorAccent));
                            String like = commentViewHolder.comment1LikeQa.getText().toString();
                            commentViewHolder.comment1LikeQa.setTextColor(context.getResources().getColor(R.color.colorAccent));
                            if (like.equals("喜欢")) {
                                int likeint = 1;
                                commentViewHolder.comment1LikeQa.setText(Integer.toString(likeint));
                            } else {
                                int likeint = Integer.parseInt(like) + 1;
                                commentViewHolder.comment1LikeQa.setText(Integer.toString(likeint));
                            }
                            qaDataDetail.getComments().get(position1).getReplies().get(0).setReply_liked(true);
                        } else {
                            commentViewHolder.like1ImgQa.setImageResource(R.drawable.baseline_favorite_border_black_18);
                            String like = commentViewHolder.comment1LikeQa.getText().toString();
                            commentViewHolder.comment1LikeQa.setTextColor(context.getResources().getColor(R.color.fontColor));
                            int likeint = Integer.parseInt(like) - 1;
                            if (likeint == 0) {
                                commentViewHolder.comment1LikeQa.setText("喜欢");
                            } else {
                                commentViewHolder.comment1LikeQa.setText(Integer.toString(likeint));
                            }
                            qaDataDetail.getComments().get(position1).getReplies().get(0).setReply_liked(false);
                        }
                    }
                });

                commentViewHolder.replyUserIcon1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundleto = new Bundle();
                        if (userId.equals(qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_user_id())) {
                            bundleto.putString("type", "individual");
                            IntentUtils.getInstence().intent(context, IndividualActivity.class, bundleto);
                        } else {
                            bundleto.putString("type", "other");
                            bundleto.putString("userId", qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_user_id());
                            IntentUtils.getInstence().intent(context, IndividualActivity.class, bundleto);
                        }
                    }
                });

                commentViewHolder.reply1More.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteReplyOnClickListener.deleteReplyClickListener(qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_id(), qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_user_id(), qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_comment(), position1, 0);
                    }
                });

                commentViewHolder.firstReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        commentViewHolder.moreComment.setVisibility(View.VISIBLE);
                        sendReplyOnClickListener.sendReplyClickListener(qaDataDetail.getComments().get(position1).getComment_q_id(), qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_user_id(), qaDataDetail.getComments().get(position1).getReplies().get(0).getUser_name());
                    }
                });
//                    if (replyCount >= 2){
//                        commentViewHolder.firstReply.setVisibility(View.VISIBLE);
//                        commentViewHolder.secondReply.setVisibility(View.VISIBLE);
//                        commentViewHolder.moreComment.setVisibility(View.GONE);
//                        if (qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_like() == null && qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_like().equals("0")){
//                            commentViewHolder.comment2LikeQa.setText("喜欢");
//                        }else {
//                            if (qaDataDetail.getComments().get(position1).getReplies().get(1).isReply_liked()){
//                                commentViewHolder.like2ImgQa.setImageResource(R.drawable.baseline_favorite_black_18);
//                            }
//                            commentViewHolder.comment2LikeQa.setText(qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_like());
//                        }
//                        commentViewHolder.comment2TimeQa.setText(CommonUtils.getTimeRange(qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_time()));
//                        commentViewHolder.comment2LikeQa.setText(qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_like());
//                        ImageLoader.loadImageViewThumbnailwitherror(context,qaDataDetail.getComments().get(position1).getReplies().get(1).getUser_icon(),commentViewHolder.replyUserIcon1,R.drawable.defaulticon);
//                        commentViewHolder.replyUserName2.setText(qaDataDetail.getComments().get(position1).getReplies().get(1).getUser_name());
//                        commentViewHolder.replyToUserName2.setText(qaDataDetail.getComments().get(position1).getReplies().get(1).getTo_user_name());
//                        commentViewHolder.replyComment2.setText(qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_comment());
//                        commentViewHolder.like2ImgQa.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                addLikeReplyOnClickListener.addLikeReplyClickListener(qaDataDetail.getComments().get(position1).getReplies().get(1).isReply_liked(),qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_id());
//                                if (!qaDataDetail.getComments().get(position1).getReplies().get(1).isReply_liked()){
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
//                                    qaDataDetail.getComments().get(position1).getReplies().get(1).setReply_liked(true);
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
//                                    qaDataDetail.getComments().get(position1).getReplies().get(1).setReply_liked(false);
//                                }
//                            }
//                        });
//
//                commentViewHolder.secondReply.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        commentViewHolder.moreComment.setVisibility(View.VISIBLE);
//                        sendReplyOnClickListener.sendReplyClickListener(qaDataDetail.getComments().get(position1).getComment_q_id(), qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_user_id(), qaDataDetail.getComments().get(position1).getReplies().get(1).getUser_name());
//                    }
//                });
//
//                commentViewHolder.reply2More.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleteReplyOnClickListener.deleteReplyClickListener(qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_id(), qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_user_id(), qaDataDetail.getComments().get(position1).getReplies().get(1).getReply_comment(), position1, 1);
//                    }
//                });
//
//                commentViewHolder.replyUserIcon2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Bundle bundleto = new Bundle();
//                        if (userId.equals(qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_user_id())) {
//                            bundleto.putString("type", "individual");
//                            IntentUtils.getInstence().intent(context, IndividualActivity.class, bundleto);
//                        } else {
//                            bundleto.putString("type", "other");
//                            bundleto.putString("userId", qaDataDetail.getComments().get(position1).getReplies().get(0).getReply_user_id());
//                            IntentUtils.getInstence().intent(context, IndividualActivity.class, bundleto);
//                        }
//                    }
//                });
                if (replyCount > 1) {
                    commentViewHolder.firstReply.setVisibility(View.VISIBLE);
                    //commentViewHolder.secondReply.setVisibility(View.VISIBLE);
                    commentViewHolder.moreComment.setVisibility(View.VISIBLE);
                }
            }


            commentViewHolder.moreComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("commentId", qaDataDetail.getComments().get(position1).getComment_q_id());
                    IntentUtils.getInstence().intent(context, CommentDetailActivity.class, bundle);
                }
            });





                ImageLoader.loadImageViewThumbnailwitherror(context, qaDataDetail.getComments().get(position1).getUser_icon(), commentViewHolder.userIconComment, R.drawable.defaulticon);
                if (qaDataDetail.getComments().get(position1).getComment_q_user_id().equals(qaDataDetail.getQaData().getQa_user_id())){
                    commentViewHolder.userNameComment.setText(qaDataDetail.getComments().get(position1).getUser_name() + "[作者]");
                }else {
                    commentViewHolder.userNameComment.setText(qaDataDetail.getComments().get(position1).getUser_name());
                }

                commentViewHolder.userComment.setText(qaDataDetail.getComments().get(position1).getComment_q_content());
                commentViewHolder.commentTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendReplyOnClickListener.sendReplyClickListener(qaDataDetail.getComments().get(position1).getComment_q_id(),qaDataDetail.getComments().get(position1).getComment_q_user_id(),qaDataDetail.getComments().get(position1).getUser_name());
                        commentViewHolder.moreComment.setVisibility(View.VISIBLE);
                    }
                });
                commentViewHolder.userIconComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundleto = new Bundle();
                        if (userId.equals(qaDataDetail.getComments().get(position1).getComment_q_user_id())){
                            bundleto.putString("type","individual");
                            IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
                        }else {
                            bundleto.putString("type","other");
                            bundleto.putString("userId",qaDataDetail.getComments().get(position1).getComment_q_user_id());
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
        if (qaDataDetail.getComments() != null){
            return qaDataDetail.getComments().size() + 1;
        }else {
            return 2;
        }

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
        EPieVideoPlayer VideoPlayer;
        @BindView(R.id.more_ask_detail)
        ImageView moreAskDetail;
        @BindView(R.id.like_ask_detail)
        TextView likeDetail;
        @BindView(R.id.comment_ask_detail)
        TextView commentDetail;

        @BindView(R.id.re_btn_detail)
        ImageView reBtn;
        @BindView(R.id.re_detail_ll)
        RelativeLayout reContentLL;

        @BindView(R.id.re_detail_user_name)
        TextView reUserName;

        @BindView(R.id.re_detail_content)
        TextView reContent;

        @BindView(R.id.re_detail_like)
        TextView reLike;

        @BindView(R.id.qa_detail_time)
        TextView qaDetailTime;
        @BindView(R.id.re_detail_comment)
        TextView reComment;

        @BindView(R.id.re_detail_video_player)
        EPieVideoPlayer reVideo;

        @BindView(R.id.auth_mark_ask)
        AuthView authMark;
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





    public interface AddLikeDetailOnClickListener{
        void addLikeDetailClickListener(boolean isLike,String qaId);
    }

    public interface AddLikeCommentOnClickListener{
        void addLikeCommentClickListener(boolean isLike,String commentId);
    }

    public interface AddLikeReplyOnClickListener{
        void addLikeReplyClickListener(boolean isLike,String replyId);
    }

    public interface SendReplyOnClickListener{
        void sendReplyClickListener(String commentId,String toUserId,String toUsername);
    }

    public interface DeleteCommentOnClickListener{
        void deleteCommentClickListener(String commentId,String commentUserId,String content,int position);
    }
    public interface DeleteReplyOnClickListener{
        void deleteReplyClickListener(String replyId,String replyUserId,String content,int commentPosition,int replyPosition);
    }

    public interface DeleteQaDetailOnClickListener{
        void deleteQaDetailClickListener(String qaId,String qaUserId,String content);
    }
}
