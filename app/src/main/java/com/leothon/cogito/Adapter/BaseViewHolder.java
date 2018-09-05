package com.leothon.cogito.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

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



    //常用设置方法封装
    public void setText(int id, String text) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setText(text);
    }

    public void setImageResource(int id, int resouceId) {
        ImageView img= (ImageView) convertView.findViewById(id);
        img.setImageResource(resouceId);
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


}
