package com.leothon.cogito.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;



/**
 * created by leothon on 2018.7.24
 */
public class IntentUtils {

    public static IntentUtils instence;

    public static IntentUtils getInstence() {
        if (null == instence) {
            instence = new IntentUtils();
        }
        return instence;
    }

    private IntentUtils() {

    }

    /**
     * 不带参数的跳转
     *
     * @param fromContext
     * @param cls
     *            泛型
     */
    public void intent(Context fromContext, Class<?> cls) {
        Intent intent = new Intent(fromContext, cls);
        fromContext.startActivity(intent);
    }

    /**
     * 带参数的跳转
     *
     * @param fromContext
     * @param cls
     */
    public void intent(Context fromContext, Class<?> cls,Bundle bb) {
        Intent intent = new Intent(fromContext, cls);
        intent.putExtras(bb);
        fromContext.startActivity(intent);
    }
    /**
     * 封装 startActivityForResult 无带参数
     * {@link #startActivityForResult(Activity, Class, int, Bundle)}
     *
     * @param fromClass
     * @param toClass
     * @param requestCode
     */
    public void startActivityForResult(Activity fromClass, Class<?> toClass, int requestCode) {
        startActivityForResult(fromClass, toClass, requestCode, null);
    }

    /**
     * 封装 startActivityForResult 带参数
     *
     * @param fromClass
     * @param toClass
     * @param requestCode
     * @param data
     */
    public void startActivityForResult(Activity fromClass, Class<?> toClass, int requestCode, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(fromClass, toClass);
        if (null != data) {
            intent.putExtras(data);
        }
        fromClass.startActivityForResult(intent, requestCode);
    }

    /**
     * 接收参数然后在返回数值
     * @param fromContext 当前的activity
     * @param bb
     * @param RESULT_OK
     */
    public void setResult(Activity fromContext, Bundle bb, int RESULT_OK){
        Intent intent=new Intent();
        intent.putExtras(bb);
        fromContext.setResult(RESULT_OK, intent);
        fromContext.finish();
    }

}
