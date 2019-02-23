package com.leothon.cogito.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Bean.TokenValid;
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
public class AskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{


    public static final String TAG = "adapter";
    private Context context;
    private ArrayList<QAData> asks;

    private StandardGSYVideoPlayer curPlayer;
    protected OrientationUtils orientationUtils;

    protected boolean isPlay;
    private LayoutInflater inflater;
    protected boolean isFull;

    private ImageView imageView;



    private static int COMPLETED = 1;
    private Bitmap bitmap;

    private int isLiked;
    private SharedPreferencesUtils sharedPreferencesUtils;
    public addLikeOnClickListener addLikeOnClickListener;

    public void setOnClickaddLike(addLikeOnClickListener onClickMyTextView) {
        this.addLikeOnClickListener = onClickMyTextView;
    }

    private int HEAD0 = 0;
    private int HEAD1 = 1;

    private String userId;
    private boolean isLogin;
    public AskAdapter(Context context, ArrayList<QAData> asks,boolean isLogin){
        this.context = context;
        this.asks = asks;
        this.isLogin = isLogin;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {
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
        if (viewType == HEAD0 ) {
            final QAData ask = asks.get(position);
            final AskViewHolder askViewHolder = (AskViewHolder) holder;

            ImageLoader.loadImageViewThumbnailwitherror(context,ask.getUser_icon(),askViewHolder.userIcon,R.drawable.defaulticon);
            askViewHolder.userName.setText(ask.getUser_name());
            askViewHolder.userDes.setText(ask.getUser_signal());

            if (ask.getReQA().size() > 1){

                String re = ask.getQa_content();
                for (int i = 0;i < ask.getReQA().size() - 1;i ++){
                    re = re + " //@" + ask.getReQA().get(i).getUser_name() + ": " + ask.getReQA().get(i).getQa_content();

                }
                SpannableString spannableString = new SpannableString(re);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#2298EF"));
                spannableString.setSpan(colorSpan, re.indexOf("@"),re.indexOf(":"), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

//                for (int i = 0;i < ask.getReQA().size() - 1;i ++){
//
////                    MyClickableSpan clickableSpan = new MyClickableSpan(ask.getReQA().get(i));
////                    spannableString.setSpan(clickableSpan, re.indexOf("//"), re.indexOf(":"), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                }

                askViewHolder.contentAsk.setText(spannableString);

            }else {
                askViewHolder.contentAsk.setText(ask.getQa_content());
            }



            if (ask.getQa_like() == null && ask.getQa_like().equals("0")){
                askViewHolder.likeAsk.setText("喜欢");
            }else {

                if (ask.isLiked()){
                    Drawable drawableLeft = context.getResources().getDrawable(
                            R.drawable.baseline_favorite_black_18);
                    askViewHolder.likeAsk.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                }
                askViewHolder.likeAsk.setText(ask.getQa_like());
            }


            if (ask.getQa_comment() == null && ask.getQa_comment().equals("0")){
                askViewHolder.commentAsk.setText("评论");
            }else {
                askViewHolder.commentAsk.setText(ask.getQa_comment());
            }
            if (ask.getQa_video() != null) {
                askViewHolder.gsyVideoPlayer.setVisibility(View.VISIBLE);
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.loadImageViewThumbnailwitherror(context, ask.getQa_video_cover(), imageView, R.drawable.defalutimg);
                imageView.setTag(ask.getQa_video());



                GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
                if (imageView.getTag().equals(ask.getQa_video())){
                    gsyVideoOption.setThumbImageView(imageView);
                }
                gsyVideoOption
                        .setIsTouchWiget(true)
                        .setRotateViewAuto(true)
                        .setLockLand(false)
                        .setAutoFullWithSize(true)
                        .setShowFullAnimation(false)
                        .setNeedLockFull(true)
                        .setUrl(ask.getQa_video())
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


            if (ask.getReQA().size() != 0){
                askViewHolder.reContentLL.setVisibility(View.VISIBLE);
                final QAData reShowQA = ask.getReQA().get(ask.getReQA().size() - 1);
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
                    askViewHolder.reVideo.setVisibility(View.VISIBLE);
                    imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ImageLoader.loadImageViewThumbnailwitherror(context, reShowQA.getQa_video_cover(), imageView, R.drawable.defalutimg);
                    imageView.setTag(reShowQA.getQa_video());



                    GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
                if (imageView.getTag().equals(reShowQA.getQa_video())){
                    gsyVideoOption.setThumbImageView(imageView);
                }
                    gsyVideoOption
                            .setIsTouchWiget(true)
                            .setRotateViewAuto(true)
                            .setLockLand(false)
                            .setAutoFullWithSize(true)
                            .setShowFullAnimation(false)
                            .setNeedLockFull(true)
                            .setUrl(ask.getQa_video())
                            .setCacheWithPlay(false)
                            .setVideoTitle("")
                            .build(askViewHolder.reVideo);
                    askViewHolder.reVideo.getFullscreenButton().setVisibility(View.GONE);
                    askViewHolder.reVideo.getBackButton().setVisibility(View.GONE);

                }else {
                    askViewHolder.reVideo.setVisibility(View.GONE);
                }

                askViewHolder.reContentLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isLogin){
                            Bundle bundleto = new Bundle();
                            bundleto.putString("qaId",reShowQA.getQa_id());
                            IntentUtils.getInstence().intent(context, AskDetailActivity.class,bundleto);
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
                        Bundle bundle = new Bundle();
                        bundle.putString("type","re");
                        bundle.putString("qaId",ask.getQa_id());
                        IntentUtils.getInstence().intent(context,AskActivity.class,bundle);
                    }else {
                        CommonUtils.loadinglogin(context);
                    }
                }
            });
        }else if(viewType == HEAD1){
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
        return asks.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < asks.size()) {
            return HEAD0;
        }else {
            return HEAD1;
        }
    }

    @Override
    public void onClick(View view) {



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
        StandardGSYVideoPlayer reVideo;

        @BindView(R.id.video_item_player)
        StandardGSYVideoPlayer gsyVideoPlayer;
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
    public interface OnItemClickListener{
        void onItemClick(View v,int postion);
        void onItemLongClick(View v,int postion);
    }
    /**自定义条目点击监听*/
    private BagAdapter.OnItemClickListener mOnItemClickLitener;

    public void setmOnItemClickLitener(BagAdapter.OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
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
}
