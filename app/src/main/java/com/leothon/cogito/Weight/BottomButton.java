package com.leothon.cogito.Weight;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;



/**
 * created by leothon on 2018.7.26
 */
public class BottomButton extends LinearLayout {


    private ImageView iv;
    private TextView tv;

    public BottomButton(Context context){
        super(context);
        init(context);
    }


    public BottomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setOrientation(VERTICAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);
        setPadding(CommonUtils.dip2px(getContext(),5),CommonUtils.dip2px(getContext(),5),CommonUtils.dip2px(getContext(),5),CommonUtils.dip2px(getContext(),5));

        View bottomBtnView = LayoutInflater.from(context).inflate(R.layout.bottom_button_view,this,true);
        iv = (ImageView)bottomBtnView.findViewById(R.id.iv);
        tv = (TextView)bottomBtnView.findViewById(R.id.tv);
    }

    public void setTvAndIv(String tvString,int ivImage){
        tv.setText(tvString);
        iv.setImageResource(ivImage);
    }

    public void setTvColor(int color){
        tv.setTextColor(color);
    }

    public void setIvColor(int color){
        iv.setColorFilter(color);
    }

    public void focusOnButton(){
        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
        layoutParams.width = 70;
        layoutParams.height = 70;
        iv.setLayoutParams(layoutParams);
    }
    public void resetButton(){
        ViewGroup.LayoutParams layoutParams = iv.getLayoutParams();
        layoutParams.width = 55;
        layoutParams.height = 55;
        iv.setLayoutParams(layoutParams);
    }
}
