package com.leothon.cogito.Base;
/*
 * created by leothon on 2018.7.22
 * */
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application {
    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

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
    }

}
