package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchQAAdapter extends BaseAdapter{
    public static final String TAG = "adapter";
    private Context context;
    public SearchQAAdapter(Context context, ArrayList<QAData> qaData){

        super(context, R.layout.askitem,qaData);
        this.context = context;
    }
    @Override
    public <T> void convert(final BaseViewHolder holder, T bean, int position) {
        QAData qaData = (QAData)bean;
        holder.setText(R.id.user_name_ask,qaData.getUser_name());
        holder.setImageUrls(R.id.user_icon_ask,qaData.getUser_icon());

        holder.setText(R.id.qa_list_time,CommonUtils.getTimeRange(qaData.getQa_time()));
        int role = CommonUtils.isVIP(qaData.getUser_role());
        if (role != 2){
            if (role == 0){
                holder.setAuthorVisible(R.id.auth_mark_ask_list,0,1);
            }else if (role == 1){
                holder.setAuthorVisible(R.id.auth_mark_ask_list,1,1);
            }else {
                holder.setAuthorVisible(R.id.auth_mark_ask_list,2,0);
                holder.setText(R.id.user_des_ask,qaData.getUser_signal());
            }
            holder.setText(R.id.user_des_ask,"认证：" + qaData.getUser_role().substring(1));
        }else {
            holder.setAuthorVisible(R.id.auth_mark_ask_list,2,0);
            holder.setText(R.id.user_des_ask,qaData.getUser_signal());
        }
        String re = qaData.getQa_content();

        SpannableString spannableString = new SpannableString(re);
//        if (re.contains("@") && re.contains(":")){
//
//            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#2298EF"));
//            spannableString.setSpan(colorSpan, re.indexOf("@"),re.indexOf(":"), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        }

        holder.setSpannableText(R.id.content_ask,spannableString);




        if (qaData.getQa_like() == null && qaData.getQa_like().equals("0")){
            holder.setText(R.id.like_ask,"喜欢");
        }else {

            if (qaData.isLiked()){
                Drawable drawableLeft = context.getResources().getDrawable(
                        R.drawable.baseline_favorite_black_18);
                holder.setTextIcon(R.id.like_ask,drawableLeft);
            }
            holder.setText(R.id.like_ask,qaData.getQa_like());
        }


        if (qaData.getQa_comment() == null && qaData.getQa_comment().equals("0")){
            holder.setText(R.id.comment_ask,"评论");
        }else {
            holder.setText(R.id.comment_ask,qaData.getQa_comment());
        }
        if (qaData.getQa_video() != null) {
            holder.setViewVisiable(R.id.video_item_player,1);
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.loadImageViewThumbnailwitherror(context, qaData.getQa_video_cover(), imageView, R.drawable.defalutimg);

            GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();



            if (CommonUtils.isHaveChar(qaData.getQa_video())){
                try{
                    String url = URLEncoder.encode(qaData.getQa_video(),"utf-8").replaceAll("\\+", "%20");
                    url = url.replaceAll("%3A", ":").replaceAll("%2F", "/");
                    gsyVideoOption.setUrl(url);
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }else {
                gsyVideoOption.setUrl(qaData.getQa_video());
            }
            gsyVideoOption
                    .setPlayTag(TAG)
                    .setPlayPosition(position)
                    .setThumbImageView(imageView)
                    .setIsTouchWiget(true)
                    .setRotateViewAuto(true)
                    .setLockLand(false)
                    .setAutoFullWithSize(true)
                    .setShowFullAnimation(false)
                    .setNeedLockFull(true)
                    .setCacheWithPlay(false)
                    .setVideoTitle("")
                    .build(holder.getVideoPlayer(R.id.video_item_player));
            holder.getVideoPlayer(R.id.video_item_player).getBackButton().setVisibility(View.GONE);
            holder.getVideoPlayer(R.id.video_item_player).getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //orientationUtils.resolveByClick();
                    /**
                     *  bug描述：在本页中，不显示状态栏，但是全屏后，再返回会出现状态栏，根据本方法可知，传入两个参数，是否有状态栏和标题栏
                     *  默认传入两者都有，则程序执行时，会再退出全屏后重新生成状态栏，将此处两者设为没有（false)，则不会重新生成状态栏
                     */

                    holder.getVideoPlayer(R.id.video_item_player).startWindowFullscreen(context, false, true);
                }
            });

        }else {
            holder.getVideoPlayer(R.id.video_item_player).setVisibility(View.GONE);
        }

        holder.setViewVisiable(R.id.more_ask,0);




        if (qaData.getQaData() != null){
            holder.setViewVisiable(R.id.re_content_ll,1);
            final QAData reShowQA = qaData.getQaData();
            holder.setText(R.id.re_user_name,"@" + reShowQA.getUser_name());
            holder.setText(R.id.re_content,reShowQA.getQa_content());
            if (reShowQA.getQa_like().equals("") && reShowQA.getQa_like() == null){
                holder.setText(R.id.re_like,"喜欢：0");
            }else {
                holder.setText(R.id.re_like,"喜欢：" + reShowQA.getQa_like());
            }
            if (reShowQA.getQa_comment().equals("") && reShowQA.getQa_comment() == null){
                holder.setText(R.id.re_comment,"评论：0");
            }else {
                holder.setText(R.id.re_comment,"评论：" + reShowQA.getQa_like());
            }
            if (reShowQA.getQa_video() != null) {

                holder.getVideoPlayer(R.id.re_video_player).setVisibility(View.VISIBLE);
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader.loadImageViewThumbnailwitherror(context, reShowQA.getQa_video_cover(), imageView, R.drawable.defalutimg);


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
                        .setPlayTag(TAG)
                        .setThumbImageView(imageView)
                        .setPlayPosition(position)
                        .setIsTouchWiget(true)
                        .setRotateViewAuto(true)
                        .setLockLand(false)
                        .setAutoFullWithSize(true)
                        .setShowFullAnimation(false)
                        .setNeedLockFull(true)
                        .setCacheWithPlay(false)
                        .setVideoTitle("")
                        .build(holder.getVideoPlayer(R.id.re_video_player));
                //askViewHolder.reVideo.getFullscreenButton().setVisibility(View.GONE);
                holder.getVideoPlayer(R.id.re_video_player).getBackButton().setVisibility(View.GONE);

                holder.getVideoPlayer(R.id.re_video_player).getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //orientationUtils.resolveByClick();
                        /**
                         *  bug描述：在本页中，不显示状态栏，但是全屏后，再返回会出现状态栏，根据本方法可知，传入两个参数，是否有状态栏和标题栏
                         *  默认传入两者都有，则程序执行时，会再退出全屏后重新生成状态栏，将此处两者设为没有（false)，则不会重新生成状态栏
                         */

                        holder.getVideoPlayer(R.id.re_video_player).startWindowFullscreen(context, false, true);
                    }
                });
            }else {
                holder.getVideoPlayer(R.id.re_video_player).setVisibility(View.GONE);
            }


        }else {

            holder.setViewVisiable(R.id.re_content_ll,0);
        }

        holder.setViewVisiable(R.id.re_btn,0);
    }
}
