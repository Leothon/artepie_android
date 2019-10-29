package com.leothon.cogito.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.textclassifier.TextClassifier;

import com.leothon.cogito.R;

public class LinearGradientTextView extends AppCompatTextView {

    private TextPaint paint;
    private LinearGradient linearGradient;

    private Matrix matrix;
    private float translateX;
    private float deltaX = 20;

    private int showTime;
    private int lineNumber;
    private int showStyle;
    public static final int UNIDIRECTION = 0;
    public static final int TWOWAY = 1;
    private int color;
    private boolean isGradient;

    public LinearGradientTextView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LinearGradientTextView);
        showTime = typedArray.getInteger(R.styleable.LinearGradientTextView_showTime,40);
        lineNumber = typedArray.getInteger(R.styleable.LinearGradientTextView_lineNumber,1);
        showStyle = typedArray.getInt(R.styleable.LinearGradientTextView_showStyle,UNIDIRECTION);
        color = typedArray.getColor(R.styleable.LinearGradientTextView_textColor, Color.blue(200));
        isGradient = false;
    }

    public void setGradient(boolean gradient){
        isGradient = gradient;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isGradient){
            paint = getPaint();
            String text = getText().toString();
            float textWidth = paint.measureText(text);
            //闪光的文字一直是三个文字的宽度
            int gradientSize = (int) (3 * textWidth / text.length());
            linearGradient = new LinearGradient(-gradientSize,
                    0,
                    gradientSize,
                    0,
                    new int[]{color - 0xAF000000, color, color - 0xAF000000},
                    new float[]{0, 0.5f, 1},
                    Shader.TileMode.CLAMP);
            paint.setShader(linearGradient);
            matrix = new Matrix();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isGradient){
            float textWidth = (getPaint().measureText(getText().toString())) / lineNumber;
            translateX += deltaX;
            switch (showStyle){
                case UNIDIRECTION:
                    if(translateX > textWidth + 1 || translateX < 1){
                        translateX = 0;
                        translateX += deltaX;
                    }
                    break;
                case TWOWAY:
                    if (translateX > textWidth + 1 || translateX < 1){
                        deltaX = -deltaX;
                    }
                    break;
            }

            matrix.setTranslate(translateX,0);
            linearGradient.setLocalMatrix(matrix);
            postInvalidateDelayed(showTime * lineNumber);
        }

    }
}
