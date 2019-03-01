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
    private Boolean mIsShow;//记录状态 是否在显示
    private Timer mTimer;//定时器
    private WindowManager.LayoutParams mParams;
    private Context context;
    public static Toast mToast;


    public synchronized static MyToast getInstance(Context context) {
        if (instance == null)
            instance = new MyToast(context);
        return instance;
    }

    public void show(String text, int mShowTime){
        Context context1 = context.getApplicationContext();
        if (mToast == null) {
            mToast = Toast.makeText(context1, text, mShowTime);
        }
        //mToast.setText(text);
        mToastView = LayoutInflater.from(context).inflate(R.layout.common_toast, null);

        //用来提示的文字
        mTextView = ((TextView) mToastView.findViewById(R.id.toast_text));
        mTextView.setText(text);
        mToast.setView(mToastView);

        mToast.show();
    }


    private MyToast(Context context) {
        //mIsShow = false;// 记录当前Toast的内容是否已经在显示

    //这里初始化toast view
       // mToastView = LayoutInflater.from(context).inflate(R.layout.common_toast, null);

    //用来提示的文字
        //mTextView = ((TextView) mToastView.findViewById(R.id.toast_text));

    //初始化计数器
        //mTimer = new Timer();
        // 设置布局参数
        //setParams();

        this.context = context;
    }

//    private void setParams() {
//        mParams = new WindowManager.LayoutParams();//初始化
//        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;  //高
//        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;   //宽
//        mParams.format = PixelFormat.TRANSLUCENT;
//        mParams.windowAnimations = R.style.custom_animation_toast;// 设置进入退出动画效果
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        } else {
//            mParams.type = WindowManager.LayoutParams.TYPE_TOAST; }
//
//        //mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
//        mParams.gravity = Gravity.BOTTOM;        //对其方式
//        mParams.y = 65;      //下间距
//    }

//    public void show(String text, int mShowTime) {
//        if (mIsShow) {// 如果Toast已经在显示 就先给隐藏了
//            if (BaseApplication.mWdm != null && mToastView != null)
//                BaseApplication.mWdm.removeView(mToastView);
//            // 取消计时器
//            if (mTimer != null) {
//                mTimer.cancel();
//                mTimer = new Timer();
//            }
//        }
//        //设置显示内容
//        mTextView.setText(text);
//        //设置显示状态
//        mIsShow = true;
//        // 将其加载到windowManager上
//        BaseApplication.mWdm.addView(mToastView, mParams);
//
//        //设置计时器
//        mTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                BaseApplication.mWdm.removeView(mToastView);
//                mIsShow = false;
//            }
//        }, (long) (mShowTime == Toast.LENGTH_LONG ? 2200 : 1200));
//    }


}
