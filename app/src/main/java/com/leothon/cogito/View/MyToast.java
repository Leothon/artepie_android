package com.leothon.cogito.View;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.R;

import java.util.Timer;
import java.util.TimerTask;

public class MyToast{
    private static MyToast instance; //单例的
    private View mToastView;//自定义toast view
    private TextView mTextView;

    private Context context;
    public static Toast mToast;


    public synchronized static MyToast getInstance(Context context) {
        if (instance == null)
            instance = new MyToast(context.getApplicationContext());
        return instance;
    }

    public void show(String text, int mShowTime){
        Context context1 = context.getApplicationContext();
        if (mToast == null) {
            mToast = Toast.makeText(context1, text, mShowTime);
        }
        //mToast.setText(text);
        mToastView = LayoutInflater.from(context1).inflate(R.layout.common_toast, null);

        //用来提示的文字
        mTextView = ((TextView) mToastView.findViewById(R.id.toast_text));
        mTextView.setText(text);
        mToast.setView(mToastView);

        mToast.show();
    }


    private MyToast(Context context) {

        this.context = context;
    }

}
