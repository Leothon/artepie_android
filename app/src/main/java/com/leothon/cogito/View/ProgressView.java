package com.leothon.cogito.View;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.leothon.cogito.R;

public class ProgressView extends View {
    public static final float MAX = 100f;
    public static final int RADIUS = 15;     // 圆角矩形半径
    private RectF rectFBg;
    private RectF rectFProgress;
    private Paint mPaint;
    private int mWidth;
    private float progressPercent;
    private int bgColor, progressColor;
    private int mHeight;
    private int radius;
    private int startColor, endColor;
    private LinearGradient gradient;
    private boolean isGradient;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UploadProgressDialog);
        bgColor = typedArray.getColor(R.styleable.UploadProgressDialog_barBgColor, getResources().getColor(R.color.contentColor));
        progressColor = typedArray.getColor(R.styleable.UploadProgressDialog_barProgressColor, getResources().getColor(R.color.colorPrimary));
        mHeight = typedArray.getDimensionPixelSize(R.styleable.UploadProgressDialog_barHeight, context.getResources().getDimensionPixelSize(R.dimen.progress_height));
        isGradient = typedArray.getBoolean(R.styleable.UploadProgressDialog_barIsGradient, false);
        startColor = typedArray.getColor(R.styleable.UploadProgressDialog_barStartColor, getResources().getColor(R.color.black));
        endColor = typedArray.getColor(R.styleable.UploadProgressDialog_barEndColor, getResources().getColor(R.color.black));
        radius = typedArray.getInt(R.styleable.UploadProgressDialog_barRadius, RADIUS);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = heightSpecSize;
        } else {
            mHeight = getContext().getResources().getDimensionPixelSize(R.dimen.progress_height);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        gradient = new LinearGradient(0, 0, getWidth(), mHeight, startColor, endColor, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1、背景
        mPaint.setShader(null);
        mPaint.setColor(bgColor);
        rectFBg.right = mWidth; //宽度
        rectFBg.bottom = mHeight; //高度
        canvas.drawRoundRect(rectFBg, radius, radius, mPaint);
        //2、进度条
        rectFProgress.right = mWidth * progressPercent;
        rectFProgress.bottom = mHeight;
        //3、是否绘制渐变色
        if (isGradient) {
            mPaint.setShader(gradient);//设置线性渐变
        } else {
            mPaint.setColor(progressColor);
        }
        if (progressPercent > 0 && rectFProgress.right < radius)//进度值小于半径时，设置大于半径的最小值，防止绘制不出圆弧矩形
            rectFProgress.right = radius + 10;
        canvas.drawRoundRect(rectFProgress, radius, radius, mPaint);//进度}
    }

    @Keep
    public void setPercentage(float percentage) {

        if (percentage / MAX >= 1) {
            this.progressPercent = 1;
        } else {
            this.progressPercent = percentage / MAX;
        }
        invalidate();
    }

    private void init() {
        rectFBg = new RectF(0, 0, 0, mHeight);
        rectFProgress = new RectF(0, 0, 0, mHeight);
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
        invalidate();
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public void setGradient(boolean gradient) {
        isGradient = gradient;
    }


}
