package com.leothon.cogito.Base;
/*
 * created by leothon on 2018.7.22
 * */
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.leothon.cogito.DataBase.DaoMaster;
import com.leothon.cogito.DataBase.DaoSession;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.OssUtils;
import com.leothon.cogito.View.MyToast;
import com.squareup.leakcanary.LeakCanary;
import com.wanjian.cockroach.App;
import com.wanjian.cockroach.Cockroach;


import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.sms.SMSSDK;

public class BaseApplication extends Application {
    private static BaseApplication application;
    public static WindowManager mWdm;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        MultiDex.install(this);
        //instances = this;
        mWdm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        setDatabase();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        SMSSDK.getInstance().initSdk(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                OssUtils.getInstance().getOSs(BaseApplication.this);


            }
        }).start();

//
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
//    private ArrayList<Activity> activityArrayList = new ArrayList<>();
//
//    public void addActivity(Activity activity){
//        activityArrayList.add(activity);
//    }
//
//
//    public void finishActivity() {
//        for (Activity activity : activityArrayList) {
//            if (!activity.isFinishing()) {
//                activity.finish();
//            }
//        }
//        //下面代码带更改
//
//        activityArrayList.clear();
//
//    }

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

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

//    private void install() {
//        Cockroach.install(new Cockroach.ExceptionHandler() {
//            @Override
//            public void handlerException(Thread thread, Throwable throwable) {
//                @Override
//                public void run() {
//                    try {
//                        Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",throwable);
//                        Toast.makeText(App.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
////                        throw new RuntimeException("..."+(i++));
//                    } catch (Throwable e) {
//
//                    }
//                }
//            }
//
//            @Override
//            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
//                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", throwable);
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        MyToast.getInstance(BaseApplication.this).show("危险操作",Toast.LENGTH_SHORT);
//                    }
//                });
//            }
//
//            @Override
//            protected void onBandageExceptionHappened(Throwable throwable) {
//                throwable.printStackTrace();//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
//                toast.setText("Cockroach Worked");
//                toast.show();
//            }
//
//            @Override
//            protected void onEnterSafeMode() {
//
//                MyToast.getInstance(BaseApplication.this).show("安全模式",Toast.LENGTH_LONG);
//
//            }
//
//            @Override
//            protected void onMayBeBlackScreen(Throwable e) {
//                Thread thread = Looper.getMainLooper().getThread();
//                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", e);
//                //黑屏时建议直接杀死app
//                sysExcepHandler.uncaughtException(thread, new RuntimeException("black screen"));
//            }
//
//        });
//
//    }
}












//
//            _____                    _             _   ____
//          / //_/_|                  | |           | | |  _ \
//         | |      _ __  ___   __ _ | |_  ___   __| | | |_) | _   _
//         | |     | '__|/ _ \ / _` || __|/ _ \ / _` | |  _ < | | | |
//         | |____ | |  |  __/| (_| || |_|  __/| (_| | | |_) || |_| |
//         \_____||_|   \___| \__,_| \__|\___| \__,_| |____/  \__, |
//                                                            __/ |
//                                                           |___/
//          _                   _    _
//         | |                 | |  | |
//         | |      ___   ___  | |_ | |__    ___   _ __
//         | |     / _ \ / _ \ | __|| '_ \  / _ \ | '_ \
//         | |____|  __/| (_) || |_ | | | || (_) || | | |
//         |______|\___| \___/  \__||_| |_| \___/ |_| |_|
//



