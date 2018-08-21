package com.leothon.cogito.Base;
/*
 * created by leothon on 2018.7.22
 * */
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.leothon.cogito.Constants;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application {
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }




    public static Context getApplication(){
        return application;
    }
    /*
    * 加入所有的activity至数组中，实现一键退出
    * */
    private ArrayList<Activity> activityArrayList = new ArrayList<>();

    public void addActivity(Activity activity){
        activityArrayList.add(activity);
    }


    public void finishActivity(){
        for(Activity activity:activityArrayList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        //下面代码带更改

        activityArrayList.clear();
    }

    public int getCount(){
        return activityArrayList.size();
    }


}
