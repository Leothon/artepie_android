package com.leothon.cogito.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.leothon.cogito.Mvp.View.Activity.WalletActivity.WalletActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.View.RichEditTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImagePlate {
    private Context mContext;
    private RequestManager glideRequests;
    private RichEditTextView view;
    public ImagePlate(RichEditTextView view,Context context){
        this.view =view;
        this.mContext=context;
        glideRequests = Glide.with(context);
    }

    /**
     * 图片加载
     * @param path
     */
    public void image(final String path) {
        //final Uri uri = Uri.parse(path);
        Log.e( "setImg: ",path);
        final int maxWidth = view.getMeasuredWidth() -view. getPaddingLeft() - view.getPaddingRight();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.defalutimg)
                .error(R.drawable.defalutimg);
        glideRequests.asBitmap()
                //.load(new File(path))
                .load(path)
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Bitmap bitmap = zoomBitmapToFixWidth(resource, maxWidth);
                        image(path, bitmap);
                    }
                });

    }

    public void image(String url, Bitmap pic) {
        String img_str="img";
        int start = view.getSelectionStart();

        SpannableString ss = new SpannableString("\njav \n");
        RichImageSpan myImgSpan = new RichImageSpan(mContext, pic, url);
        ss.setSpan(myImgSpan, 1, img_str.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.getEditableText().insert(start, ss);// 设置ss要添加的位置

        //设置点击事件
        setClick(start+1,start+ss.length()-2,url);

        view.requestLayout();
        view.requestFocus();
    }

    public void setClick(int start,int end ,final String path){
        view.setMovementMethod(LinkMovementMethod.getInstance());
        ClickableSpan click_span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
//                view.clearFocus(); //跳转时候弹键盘
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                Toast.makeText(mContext,path,Toast.LENGTH_SHORT).show();
            }
        };
        view.getEditableText().setSpan(click_span,start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static Bitmap zoomBitmapToFixWidth(Bitmap bitmap, int maxWidth) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int newHight = maxWidth * h / w;
        return zoomBitmap(bitmap, maxWidth, newHight);
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }
}
