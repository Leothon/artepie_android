package com.leothon.cogito.View;

import android.content.Context;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.leothon.cogito.R;
import com.leothon.cogito.Utils.FontStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FontStyleMenu extends LinearLayout {
    private Context mContext;
    private OnFontPanelListener onFontPanelListener;

    //字体 加粗 斜杠 删除线 下划线 设置
    @BindView(R.id.font_style_select)
    FontSelectStyleView cusView_fontStyle;
    //字体 大小
    @BindView(R.id.font_size_select)
    FontSelectSizeView cusView_fontSize;


    private FontStyle fontStyle;
    public FontStyleMenu(Context context) {
        super(context);
        initView(context);
    }

    public FontStyleMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FontStyleMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.font_style_menu,this);
        ButterKnife.bind(this);
        fontStyle = new FontStyle();
        //字体样式
        cusView_fontStyle.setOnFontStyleSelectListener(onFontStyleSelectListener);
        cusView_fontStyle.setFontStyle(fontStyle);
        //字体大小
        cusView_fontSize.setOnFontSizeChangeListener(onFontSizeChangeListener);
        cusView_fontSize.setFontStyle(fontStyle);
    }

//    //拍照
//    @OnClick(R.id.btn_img)
//    protected void btn_img_onClick(){
//        if(onFontPanelListener!=null) {
//            onFontPanelListener.insertImg();
//        }
//    }

    /**
     * 字体样式 设置
     */
    private FontSelectStyleView.OnFontStyleSelectListener onFontStyleSelectListener = new FontSelectStyleView.OnFontStyleSelectListener() {
        @Override
        public void setBold(boolean isBold) {
            if(onFontPanelListener!=null) {
                onFontPanelListener.setBold(isBold);
            }
        }
        @Override
        public void setItalic(boolean isItalic) {
            if(onFontPanelListener!=null) {
                onFontPanelListener.setItalic(isItalic);
            }
        }
        @Override
        public void setUnderline(boolean isUnderline) {
            if(onFontPanelListener!=null) {
                onFontPanelListener.setUnderline(isUnderline);
            }
        }
        @Override
        public void setStreak(boolean isStreak) {
            if(onFontPanelListener!=null) {
                onFontPanelListener.setStreak(isStreak);
            }
        }
    };

    /**
     * 字体 大小
     */
    private FontSelectSizeView.OnFontSizeChangeListener onFontSizeChangeListener = new FontSelectSizeView.OnFontSizeChangeListener() {
        @Override
        public void onFontSizeSelect(int size) {
            if(onFontPanelListener!=null){
                onFontPanelListener.setFontSize(size);
            }
        }
    };
//    /**
//     * 字体 颜色
//     */
//    private FontsColorSelectView.OnColorSelectListener onColorSelectListener = new FontsColorSelectView.OnColorSelectListener() {
//        @Override
//        public void onColorSelect(int color) {
//            if(onFontPanelListener!=null){
//                onFontPanelListener.setFontColor(color);
//            }
//        }
//    };

    public interface OnFontPanelListener{
        void setBold(boolean isBold);
        void setItalic(boolean isItalic);
        void setUnderline(boolean isUnderline);
        void setStreak(boolean isStreak);
        //void insertImg();
        void setFontSize(int size);
        //void setFontColor(int color);
    }


    public void initFontStyle(FontStyle fontStyle){
        this.fontStyle =fontStyle;
        cusView_fontStyle.initFontStyle(fontStyle);
        cusView_fontSize.initFontStyle(fontStyle);
        //cusView_fontColor.initFontStyle(fontStyle);
    }

    public void setOnFontPanelListener(OnFontPanelListener onFontPanelListener) {
        this.onFontPanelListener = onFontPanelListener;
    }
}
