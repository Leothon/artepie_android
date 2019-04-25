package com.leothon.cogito.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Mvp.View.Activity.AskActivity.AskActivity;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
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
import com.leothon.cogito.Weight.MarqueeTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.8
 */
public class AskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{


    public static final String TAG = "adapter";
    private Context context;
    private ArrayList<QAData> asks;
    private String text;

    private SharedPreferencesUtils sharedPreferencesUtils;
    public addLikeOnClickListener addLikeOnClickListener;

    public void setOnClickaddLike(addLikeOnClickListener onClickMyTextView) {
        this.addLikeOnClickListener = onClickMyTextView;
    }

    public DeleteQaOnClickListener deleteQaOnClickListener;

    public void setDeleteQaOnClickListener(DeleteQaOnClickListener deleteQaOnClickListener) {
        this.deleteQaOnClickListener = deleteQaOnClickListener;
    }

    private int HEAD0 = 0;
    private int HEAD1 = 1;
    private int HEAD2 = 2;

    private String userId;
    private boolean isLogin;
    public AskAdapter(Context context, ArrayList<QAData> asks,String text,boolean isLogin){
        this.context = context;
        this.asks = asks;
        this.isLogin = isLogin;
        this.text = text;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {

            return new InformHolder(LayoutInflater.from(context).inflate(R.layout.inform_item, parent, false));
        }else if (viewType == HEAD1){
            return new AskViewHolder(LayoutInflater.from(context).inflate(R.layout.askitem,parent,false));
        }else {
            return new BottomHolder(LayoutInflater.from(context).inflate(R.layout.bottom_show,parent,false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        sharedPreferencesUtils = new SharedPreferencesUtils(context,"saveToken");
        userId = tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid();
        int viewType = getItemViewType(position);

        if (viewType == HEAD0){
            InformHolder informHolder = (InformHolder) holder;
            informHolder.informText.setText(text);
        }else if (viewType == HEAD1 ) {
            int realPosition = position - 1;
            final QAData ask = asks.get(realPosition);
            final AskViewHolder askViewHolder = (AskViewHolder) holder;

            ImageLoader.loadImageViewThumbnailwitherror(context,ask.getUser_icon(),askViewHolder.userIcon,R.drawable.defaulticon);
            askViewHolder.userName.setText(ask.getUser_name());
            int role = CommonUtils.isVIP(ask.getUser_role());
            if (role != 2){
                askViewHolder.authMark.setVisibility(View.VISIBLE);
                if (role == 0){
                    askViewHolder.authMark.setColor(Color.parseColor("#f26402"));
                }else if (role == 1){
                    askViewHolder.authMark.setColor(Color.parseColor("#2298EF"));
                }else {
                    askViewHolder.authMark.setVisibility(View.GONE);
                    askViewHolder.userDes.setText(ask.getUser_signal());
                }
                askViewHolder.userDes.setText("认证：" + ask.getUser_role().substring(1));

            }else {
                askViewHolder.authMark.setVisibility(View.GONE);
                askViewHolder.userDes.setText(ask.getUser_signal());
            }




            askViewHolder.qaTime.setText(CommonUtils.getTimeRange(ask.getQa_time()));
            String re = ask.getQa_content();

            SpannableString spannableString = new SpannableString(re);
//            if (re.contains("@")){
//
//                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#2298EF"));
//                spannableString.setSpan(colorSpan, re.indexOf("@"),re.indexOf(ask.getUser_name()), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            }


            askViewHolder.contentAsk.setText(spannableString);

            if (ask.getQa_like() == null && ask.getQa_like().equals("0")){
                askViewHolder.likeAsk.setText("喜欢");
                Drawable drawableLeft = context.getResources().getDrawable(
                        R.drawable.baseline_favorite_border_black_18);
                drawableLeft.setColorFilter(context.getResources().getColor(R.color.fontColor), PorterDuff.Mode.SRC_IN);
                askViewHolder.likeAsk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
            }else {

                if (ask.isLiked()){
                    Drawable drawableLeft = context.getResources().getDrawable(
                            R.drawable.baseline_favorite_black_18);
                    drawableLeft.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    askViewHolder.likeAsk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    askViewHolder.likeAsk.setTextColor(context.getResources().getColor(R.color.colorAccent));
                }else {
                    Drawable drawableLeft = context.getResources().getDrawable(
                            R.drawable.baseline_favorite_border_black_18);
                    drawableLeft.setColorFilter(context.getResources().getColor(R.color.fontColor), PorterDuff.Mode.SRC_IN);
                    askViewHolder.likeAsk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    askViewHolder.likeAsk.setTextColor(context.getResources().getColor(R.color.fontColor));
                }
                askViewHolder.likeAsk.setText(ask.getQa_like());
            }


            if (ask.getQa_comment() == null && ask.getQa_comment().equals("0")){
                askViewHolder.commentAsk.setText("评论");
            }else {
                askViewHolder.commentAsk.setText(ask.getQa_comment());
            }
            if (ask.getQa_video() != null) {
                //GSYVideoManager.instance().setNeedMute(true);
                askViewHolder.gsyVideoPlayer.setVisibility(View.VISIBLE);
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
                ImageView target = imageViewWeakReference.get();
                if (target != null) {
                    ImageLoader.loadImageViewThumbnailwitherror(context, ask.getQa_video_cover(), target, R.drawable.defalutimg);
                }




                GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();

                if (CommonUtils.isHaveChar(ask.getQa_video())){
                    try{
                        String url = URLEncoder.encode(ask.getQa_video(),"utf-8").replaceAll("\\+", "%20");
                        url = url.replaceAll("%3A", ":").replaceAll("%2F", "/");
                        gsyVideoOption.setUrl(url);
                    }catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                }else {
                    gsyVideoOption.setUrl(ask.getQa_video());
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
                        .build(askViewHolder.gsyVideoPlayer);

                askViewHolder.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
                askViewHolder.gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // orientationUtils.resolveByClick();
                        /**
                         *  bug描述：在本页中，不显示状态栏，但是全屏后，再返回会出现状态栏，根据本方法可知，传入两个参数，是否有状态栏和标题栏
                         *  默认传入两者都有，则程序执行时，会再退出全屏后重新生成状态栏，将此处两者设为没有（false)，则不会重新生成状态栏
                         */

                        askViewHolder.gsyVideoPlayer.startWindowFullscreen(context, false, true);
                        GSYVideoManager.instance().setNeedMute(false);
                    }
                });

            }else {
                askViewHolder.gsyVideoPlayer.setVisibility(View.GONE);
            }

            askViewHolder.moreAsk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLogin){
                        deleteQaOnClickListener.deleteQaClickListener(ask.getQa_id(),ask.getQa_user_id(),ask.getQa_content(),position);
                    }else {
                        CommonUtils.loadinglogin(context);
                    }
                }
            });
            askViewHolder.commentAsk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundleto = new Bundle();
                    bundleto.putString("qaId",ask.getQa_id());
                    IntentUtils.getInstence().intent(context, AskDetailActivity.class,bundleto);
                }
            });

            askViewHolder.likeAsk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (isLogin){
                        addLikeOnClickListener.addLikeClickListener(ask.isLiked(),ask.getQa_id());

                        if (!ask.isLiked()) {

                            Drawable drawableLeft = context.getResources().getDrawable(
                                    R.drawable.baseline_favorite_black_18);
                            drawableLeft.setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                            askViewHolder.likeAsk.setTextColor(context.getResources().getColor(R.color.colorAccent));
                            askViewHolder.likeAsk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);
                            String like = askViewHolder.likeAsk.getText().toString();
                            if (like.equals("喜欢")) {
                                int likeint = 1;
                                askViewHolder.likeAsk.setText(Integer.toString(likeint));
                            } else {
                                int likeint = Integer.parseInt(like) + 1;
                                askViewHolder.likeAsk.setText(Integer.toString(likeint));
                            }
                            ask.setLiked(true);


                        } else {
                            Drawable drawableLeft = context.getResources().getDrawable(
                                    R.drawable.baseline_favorite_border_black_18);
                            drawableLeft.setColorFilter(context.getResources().getColor(R.color.fontColor), PorterDuff.Mode.SRC_IN);
                            askViewHolder.likeAsk.setTextColor(context.getResources().getColor(R.color.fontColor));
                            askViewHolder.likeAsk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                                    null, null, null);
                            String like = askViewHolder.likeAsk.getText().toString();

                            int likeint = Integer.parseInt(like) - 1;
                            if (likeint == 0) {
                                askViewHolder.likeAsk.setText("喜欢");
                            } else {
                                askViewHolder.likeAsk.setText(Integer.toString(likeint));
                            }
                            ask.setLiked(false);
                        }
                    }else {
                        CommonUtils.loadinglogin(context);
                    }

                }
            });

            askViewHolder.userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   intoIndividual(ask);
                }
            });
            askViewHolder.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intoIndividual(ask);
                }
            });
            askViewHolder.userDes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   intoIndividual(ask);
                }
            });


            askViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLogin){
                        Bundle bundleto = new Bundle();
                        bundleto.putString("qaId",ask.getQa_id());
                        IntentUtils.getInstence().intent(context, AskDetailActivity.class,bundleto);
                    }else {
                        CommonUtils.loadinglogin(context);
                    }


                }
            });


            if (ask.getQaData() != null){
                askViewHolder.reContentLL.setVisibility(View.VISIBLE);
                final QAData reShowQA = ask.getQaData();
                askViewHolder.reUserName.setText("@" + reShowQA.getUser_name());
                askViewHolder.reContent.setText(reShowQA.getQa_content());
                if (reShowQA.getQa_like().equals("") && reShowQA.getQa_like() == null){
                    askViewHolder.reLike.setText("喜欢：0");
                }else {
                    askViewHolder.reLike.setText("喜欢：" + reShowQA.getQa_like());
                }
                if (reShowQA.getQa_comment().equals("") && reShowQA.getQa_comment() == null){
                    askViewHolder.reComment.setText("评论：0");
                }else {
                    askViewHolder.reComment.setText("评论：" + reShowQA.getQa_like());
                }
                if (reShowQA.getQa_video() != null) {
//                    if (!GSYVideoManager.instance().isNeedMute()){
//                        GSYVideoManager.instance().setNeedMute(true);
//                    }

                    askViewHolder.reVideo.setVisibility(View.VISIBLE);
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
                            .setPlayPosition(position)
                            .setIsTouchWiget(true)
                            .setRotateViewAuto(true)
                            .setLockLand(false)
                            .setAutoFullWithSize(true)
                            .setShowFullAnimation(false)
                            .setNeedLockFull(true)
                            .setCacheWithPlay(false)
                            .setVideoTitle("")
                            .setThumbPlay(true)
                            .build(askViewHolder.reVideo);
//                    GSYVideoManager.instance().setNeedMute(true);
                    askViewHolder.reVideo.getBackButton().setVisibility(View.GONE);
                    askViewHolder.reVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //orientationUtils.resolveByClick();
                            /**
                             *  bug描述：在本页中，不显示状态栏，但是全屏后，再返回会出现状态栏，根据本方法可知，传入两个参数，是否有状态栏和标题栏
                             *  默认传入两者都有，则程序执行时，会再退出全屏后重新生成状态栏，将此处两者设为没有（false)，则不会重新生成状态栏
                             */


                            askViewHolder.reVideo.startWindowFullscreen(context, false, true);
                            GSYVideoManager.instance().setNeedMute(false);
                        }
                    });
                }else {
                    askViewHolder.reVideo.setVisibility(View.GONE);
                }

                askViewHolder.reContentLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isLogin){
                            Bundle bundleto = new Bundle();
                            if (reShowQA.getQa_user_id() == null){
                                MyToast.getInstance(context).show("内容已被删除",Toast.LENGTH_SHORT);
                            }else {
                                bundleto.putString("qaId",reShowQA.getQa_id());
                                IntentUtils.getInstence().intent(context, AskDetailActivity.class,bundleto);
                            }


                        }else {
                            CommonUtils.loadinglogin(context);
                        }

                    }
                });
            }else {
                askViewHolder.reContentLL.setVisibility(View.GONE);
            }


            askViewHolder.reBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLogin){

                        if (ask.getQaData() != null){
                            if (ask.getQaData().getQa_user_id() != null){
                                Bundle bundle = new Bundle();
                                bundle.putString("type","re");
                                if (ask.getQaData() != null){
                                    bundle.putString("qaId",ask.getQaData().getQa_id());
                                }else {
                                    bundle.putString("qaId","");
                                }

                                bundle.putString("id",ask.getQa_id());
                                IntentUtils.getInstence().intent(context,AskActivity.class,bundle);
                            }else {
                                MyToast.getInstance(context).show("原问题已被删除，不可转发",Toast.LENGTH_SHORT);
                            }
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putString("type","re");
                            if (ask.getQaData() != null){
                                bundle.putString("qaId",ask.getQaData().getQa_id());
                            }else {
                                bundle.putString("qaId","");
                            }

                            bundle.putString("id",ask.getQa_id());
                            IntentUtils.getInstence().intent(context,AskActivity.class,bundle);
                        }


                    }else {
                        CommonUtils.loadinglogin(context);
                    }
                }
            });


        }else if(viewType == HEAD2){
            return;
        }
    }



    private void intoIndividual(QAData ask){

        Bundle bundleto = new Bundle();
        if (userId.equals(ask.getQa_user_id())){
            bundleto.putString("type","individual");
            IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
        }else {
            bundleto.putString("type","other");
            bundleto.putString("userId",ask.getQa_user_id());
            IntentUtils.getInstence().intent(context, IndividualActivity.class,bundleto);
        }
    }




    @Override
    public int getItemCount() {
        return asks.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return HEAD0;
        }else if (position < asks.size() && position > 0) {
            return HEAD1;
        }else {
            return HEAD2;
        }
    }
    @Override
    public void onClick(View view) {

    }


    class InformHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.inform_text)
        TextView informText;


        public InformHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
    class AskViewHolder extends RecyclerView.ViewHolder {
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

        @BindView(R.id.auth_mark_ask_list)
        AuthView authMark;

        @BindView(R.id.re_btn)
        ImageView reBtn;

        @BindView(R.id.re_content_ll)
        RelativeLayout reContentLL;

        @BindView(R.id.re_user_name)
        TextView reUserName;

        @BindView(R.id.re_content)
        TextView reContent;

        @BindView(R.id.re_like)
        TextView reLike;

        @BindView(R.id.re_comment)
        TextView reComment;

        @BindView(R.id.re_video_player)
        EPieVideoPlayer reVideo;

        @BindView(R.id.qa_list_time)
        TextView qaTime;
        @BindView(R.id.video_item_player)
        EPieVideoPlayer gsyVideoPlayer;

        public AskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
    class BottomHolder extends RecyclerView.ViewHolder{



        public BottomHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public interface addLikeOnClickListener{
        void addLikeClickListener(boolean isLike,String qaId);
    }

    class MyClickableSpan extends ClickableSpan {

        private QAData qaData;

        public MyClickableSpan(QAData qaData) {
            this.qaData = qaData;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {

            intoIndividual(qaData);

        }
    }

    public interface DeleteQaOnClickListener{
        void deleteQaClickListener(String qaId,String qaUserId,String content,int position);
    }

}
