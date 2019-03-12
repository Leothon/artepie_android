package com.leothon.cogito.View;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.leothon.cogito.R;
import com.leothon.cogito.Utils.FontStyle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FontSelectStyleView extends LinearLayout {
    private Context mContext;
    private FontStyle fontStyle;
    private OnFontStyleSelectListener onFontStyleSelectListener;

    @BindView(R.id.btn_bold)
    ImageView btn_bold;
    @BindView(R.id.btn_italic)
    ImageView btn_italic;
    @BindView(R.id.btn_underline)
    ImageView btn_underline;
    @BindView(R.id.btn_streak)
    ImageView btn_streak;

    public FontSelectStyleView(Context context) {
        super(context);
        initView(context);
    }

    public FontSelectStyleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FontSelectStyleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.font_style,this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_bold)
    protected void btn_bold_click(View view){
        setFontStyle(view);
    }
    @OnClick(R.id.btn_italic)
    protected void btn_italic_click(View view){
        setFontStyle(view);
    }
    @OnClick(R.id.btn_underline)
    protected void btn_underline_click(View view){
        setFontStyle(view);
    }
    @OnClick(R.id.btn_streak)
    protected void btn_streak_click(View view){
        setFontStyle(view);
    }

    private void setFontStyle(View view){
        ImageView imageView = (ImageView) view;
        imageView.setColorFilter(Color.parseColor("#808080"));
        boolean flag= false;
        if(onFontStyleSelectListener!=null&&fontStyle!=null){
            switch (view.getId()){
                case R.id.btn_bold:
                    fontStyle.isBold =! fontStyle.isBold;
                    flag=fontStyle.isBold;
                    onFontStyleSelectListener.setBold(fontStyle.isBold);
                    break;
                case R.id.btn_italic:
                    fontStyle.isItalic =! fontStyle.isItalic;
                    flag = fontStyle.isItalic;
                    onFontStyleSelectListener.setItalic(fontStyle.isItalic);
                    break;
                case R.id.btn_underline:
                    fontStyle.isUnderline =! fontStyle.isUnderline;
                    flag = fontStyle.isUnderline;
                    onFontStyleSelectListener.setUnderline(fontStyle.isUnderline);
                    break;
                case R.id.btn_streak:
                    fontStyle.isStreak =! fontStyle.isStreak;
                    flag = fontStyle.isStreak;
                    onFontStyleSelectListener.setStreak(fontStyle.isStreak);
                    break;
            }
            if(flag){
                imageView.setColorFilter(Color.parseColor("#f26402"));
            }
        }
    }

    public void initFontStyle(FontStyle fontStyle){
        this.fontStyle = fontStyle;
        initDefaultStyle();
        if(fontStyle.isBold){
            btn_bold.setColorFilter(Color.parseColor("#f26402"));
        }
        if(fontStyle.isItalic){
            btn_italic.setColorFilter(Color.parseColor("#f26402"));
        }
        if(fontStyle.isUnderline){
            btn_underline.setColorFilter(Color.parseColor("#f26402"));
        }
        if(fontStyle.isStreak){
            btn_streak.setColorFilter(Color.parseColor("#f26402"));
        }
    }

    private void initDefaultStyle(){
        btn_bold.setColorFilter(Color.parseColor("#808080"));
        btn_italic.setColorFilter(Color.parseColor("#808080"));
        btn_underline.setColorFilter(Color.parseColor("#808080"));
        btn_streak.setColorFilter(Color.parseColor("#808080"));
    }

    public interface OnFontStyleSelectListener{
        void setBold(boolean isBold);
        void setItalic(boolean isItalic);
        void setUnderline(boolean isUnderline);
        void setStreak(boolean isStreak);
    }

    public void setOnFontStyleSelectListener(OnFontStyleSelectListener onFontStyleSelectListener) {
        this.onFontStyleSelectListener = onFontStyleSelectListener;
    }

    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }
}
