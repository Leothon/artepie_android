package com.leothon.cogito.Utils;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;



/**
 * created by leothon on 8/25/2018.
 * 今日头条的屏幕适配方法
 * 重新编写density值，使所有屏幕适应
 */
public class FitUtils {

    private static float width = 750;
    private static int dpi = 375;
    private static float nativeWidth = 0;

    /**
     * 在Activity的onCreate中调用,修改该Activity的density,即可完成适配,使用宽高直接使用设计图上px相等的dp值
     *
     * @param activity     需要改变的Activity
     * @param isPxEqualsDp 是否需要设置为设计图上的px直接在xml上写dp值(意思就是不需要自己计算dp值,直接写设计图上的px值,并改单位为dp),但开启后可能需要手动去设置ToolBar的大小,如果不用可以忽略
     */
    public static void autoFit(AppCompatActivity activity, boolean isPxEqualsDp){
        if (nativeWidth == 0){
            nativeWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        }
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        displayMetrics.density = isPxEqualsDp ? nativeWidth / dpi / (width / dpi) : nativeWidth / dpi;
        displayMetrics.densityDpi = (int)(displayMetrics.density * 160);
    }
}
