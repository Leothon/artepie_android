package com.leothon.cogito.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import io.reactivex.annotations.NonNull;


/**
 * created by leothon on 8/25/2018.
 * 今日头条的屏幕适配方法
 * 重新编写density值，使所有屏幕适应
 */
public class FitUtils {

//    private static float width = 750;
//    private static int dpi = 375;
//    private static float nativeWidth = 0;
//
//    /**
//     * 在Activity的onCreate中调用,修改该Activity的density,即可完成适配,使用宽高直接使用设计图上px相等的dp值
//     *
//     * @param activity     需要改变的Activity
//     * @param isPxEqualsDp 是否需要设置为设计图上的px直接在xml上写dp值(意思就是不需要自己计算dp值,直接写设计图上的px值,并改单位为dp),但开启后可能需要手动去设置ToolBar的大小,如果不用可以忽略
//     */
//    public static void autoFit(AppCompatActivity activity, boolean isPxEqualsDp){
//        if (nativeWidth == 0){
//            nativeWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
//        }
//        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
//        displayMetrics.density = isPxEqualsDp ? nativeWidth / dpi / (width / dpi) : nativeWidth / dpi;
//        displayMetrics.densityDpi = (int)(displayMetrics.density * 160);
//    }


    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static void serCustomDensity(@NonNull Activity activity, @NonNull final Application application){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0){
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration configuration) {
                    if (configuration != null && configuration.fontScale > 0){
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        final int targetDensityDpi = (int)(160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
