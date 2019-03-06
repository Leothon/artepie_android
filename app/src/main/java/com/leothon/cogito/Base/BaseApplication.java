package com.leothon.cogito.Base;
/*
 * created by leothon on 2018.7.22
 * */
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.view.ViewManager;
import android.view.WindowManager;

import com.leothon.cogito.DataBase.DaoMaster;
import com.leothon.cogito.DataBase.DaoSession;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends Application {
    private static BaseApplication application;


//    private int loginStatus;

    public static WindowManager mWdm;

//    public int getLoginStatus() {
//        return loginStatus;
//    }
//
//    public void setLoginStatus(int loginStatus) {
//        this.loginStatus = loginStatus;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        MultiDex.install(this);
        //instances = this;
        mWdm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        setDatabase();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//        setLoginStatus(0);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);



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


    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    //静态单例
//    public static BaseApplication instances;
//
//    public static BaseApplication getInstances(){
//        return instances;
//    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        // 此处sport-db表示数据库名称 可以任意填写
        mHelper = new DaoMaster.DevOpenHelper(this, "sport-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }


}
