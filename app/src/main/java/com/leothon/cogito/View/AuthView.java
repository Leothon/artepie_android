package com.leothon.cogito.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.leothon.cogito.R;


public class AuthView extends View {


    private Paint outPaint;
    private Paint circlePaint;
    private Paint textPaint;

    private int viewColor;
    private float mR, mCx, mCy;

    public AuthView(Context context) {
        super(context,null);
        initPaint();
    }
    public AuthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        initPaint();
    }

    public AuthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

    }

    public void initPaint(){
        viewColor = Color.parseColor("#f26402");
        outPaint = new Paint();
        circlePaint = new Paint();
        textPaint = new Paint();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float mW = getMeasuredWidth();
        float mH = getMeasuredHeight();

        mCx = mW / 2;
        mCy = mH / 2;
        mR = Math.min(mCx, mCy);
    }

    public void setColor(int color){
        viewColor = color;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        outPaint.setColor(Color.parseColor("#ffffff"));
        outPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        outPaint.setAntiAlias(true);


        circlePaint.setColor(viewColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#ffffff"));
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(35f);
        textPaint.setTextSkewX(-0.1f);

        float stringWidth = textPaint.measureText("V");
        float x = (getWidth() - stringWidth) / 2;

        float stringHeight = textPaint.measureText("V");
        float y = (getHeight() + stringHeight) / 2;

        canvas.drawCircle(mCx,mCy,mR,outPaint);
        canvas.drawCircle(mCx,mCy,mR - 3,circlePaint);
        canvas.save();

        canvas.drawText("V",x,y,textPaint);
        canvas.save();

    }

}
