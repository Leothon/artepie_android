package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;


import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.View.AuthView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.ButterKnife;

/**
 * created by leothon on 2018.7.30
 */
public  class BaseViewHolder extends RecyclerView.ViewHolder {

    View convertView;
    Context context;

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.convertView = itemView;
        this.context = context;
    }


    public View getView(int id){

        View view = (View)convertView.findViewById(id);
        return view;
    }

    //常用设置方法封装
    public void setText(int id, String text) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setText(text);
    }


    public StandardGSYVideoPlayer getVideoPlayer(int id){
        StandardGSYVideoPlayer standardGSYVideoPlayer = (StandardGSYVideoPlayer)convertView.findViewById(id);
        return standardGSYVideoPlayer;
    }

    public void setSpannableText(int id, SpannableString text) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setText(text);
    }

    public void setImageResource(int id, int resouceId) {
        ImageView img= (ImageView) convertView.findViewById(id);
        img.setImageResource(resouceId);
    }

    public void setTextIcon(int id, Drawable drawable) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setCompoundDrawablesWithIntrinsicBounds(drawable,
                null, null, null);

    }

    /**
     * 设置网络图片
     * @param id
     * @param urls
     */
    public void setImageUrls(int id,String urls){
        ImageView img = (ImageView) convertView.findViewById(id);
        ImageLoader.loadImageViewThumbnailwitherror(context,urls,img, R.drawable.defalutimg);
    }

    public void setOvilImage(int id,String urls){
        RoundedImageView img = (RoundedImageView)convertView.findViewById(id);
        ImageLoader.loadImageViewThumbnailwitherror(context,urls,img,R.drawable.defalutimg);
    }

    public void setText(int id,String text,int color,int background){
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setText(text);
        tx.setTextColor(color);
        tx.setBackgroundResource(background);
    }

    /**
     * 直接通过加入
     * @param view
     * @param url
     */
    public void setViewImagebyUrl(ImageView view,String url){
        ImageLoader.loadImageViewThumbnailwitherror(context,url,view, R.drawable.defalutimg);
    }


    public void setImageUrlswiththumb(int id,String url){
        ImageView img = (ImageView) convertView.findViewById(id);
        ImageLoader.loadImageViewThumbnailwitherror(context,url,img, R.drawable.defalutimg);
    }

    public void setViewBackColor(int id,int color){
        View view = (View)convertView.findViewById(id);
        view.setBackgroundColor(color);
    }
    public void setViewText(TextView tv, String text) {
        tv.setText(text);
    }

    public void setViewImageResource(ImageView img, int resouceId) {
        img.setImageResource(resouceId);
    }
    /**
     * 设置View是否可见，0表示消失，1表示可见，2表示不可见
     * @param id
     * @param isVisiable
     */
    public void setViewVisiable(int id,int isVisiable){
        View view = (View)convertView.findViewById(id);
        switch (isVisiable){
            case 0:
                view.setVisibility(View.GONE);
                break;
            case 1:
                view.setVisibility(View.VISIBLE);
                break;
            case 2:
                view.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }

    }



    public void setAuthorVisible(int id,int type,int visivble){
        AuthView authView = (AuthView)convertView.findViewById(id);

        switch (visivble){
            case 0:
                authView.setVisibility(View.GONE);
                break;
            case 1:
                authView.setVisibility(View.VISIBLE);
                if (type == 0){
                    authView.setColor(Color.parseColor("#f26402"));
                }else if (type == 1){
                    authView.setColor(Color.parseColor("#2298EF"));
                }else {
                    authView.setVisibility(View.GONE);
                }
                break;
            case 2:
                authView.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }


}
