package com.leothon.cogito.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.Weight.CommonDialog;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACVerifier;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import net.minidev.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* created by leothon on 2017.7.23
* 封装一些常用的工具类
* */
public class CommonUtils {
    public static Toast mToast;
    public static final int SHOW_SNACKBAR = 5001;
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;
    private static Boolean _hasCamera = null;

    static {
        GTE_ICS = Build.VERSION.SDK_INT >= 14;
        GTE_HC = Build.VERSION.SDK_INT >= 11;
        PRE_HC = Build.VERSION.SDK_INT < 11;
    }



    private CommonUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }


    /**
     * 设置hint大小
     *
     * @param size
     * @param v
     * @param res
     */
    public static void setViewHintSize(Context context, int size, TextView v, int res) {
        SpannableString ss = new SpannableString(getResources(context).getString(
                res));
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        v.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }

    /**
     * 获取视频第一帧作为封面（网络）
     * @param url
     * @return
     */

    public static Bitmap getVideoThumbnail(String url) {
        Bitmap bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(url, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public static List<String> getImgStr(String htmlStr) {
        List<String> pics = new ArrayList<String>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //recycleBitmap(bitmap);
        return file;
    }

    /**
     * 获取视频第一帧作为封面（本地）
     * @param path
     * @return
     */

    public  static Bitmap getVideoThumb(String path) {

        MediaMetadataRetriever media = new MediaMetadataRetriever();

        media.setDataSource(path);

        return media.getFrameAtTime();

    }

    public static String fileType(String filename){
        String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
        String audio[] = {  "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
                "m4a", "vqf" };
        String video[] = { "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm" };
        for (int i = 0; i < audio.length; i++) {
            if (audio[i].equals(fileType)) {
                return "audio";
            } else if (video[i].equals(fileType)) {
                return "video";
            }
        }

        return null;
    }


    public  static String stringto(String rebate){
        int rebateprice = Integer.parseInt(rebate);
        int price = rebateprice/100;
        return Integer.toString(price);
    }

    public static void loadinglogin(final Context context){
            final CommonDialog dialog = new CommonDialog(context);


            dialog.setMessage("您尚未登录，是否登录？")
                    .setTitle("登录提醒")
                    .setSingle(false)
                    .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                        @Override
                        public void onPositiveClick() {
                            dialog.dismiss();
                            IntentUtils.getInstence().intent(context, LoginActivity.class);
                        }

                        @Override
                        public void onNegativeClick() {
                            dialog.dismiss();

                        }

                    })
                    .show();

    }
    /**
     * 将int数值转化为汉字显示
     * @param index
     * @return
     */

    public static String toCharaNumber(int index){

        String[] num = {"零","一","二","三","四","五","六","七","八","九"};

        String[] unit = {"","十","百","千","万","十","百","千","亿","十","百","千","万亿"};

        String result = String.valueOf(index);

        char[] ch = result.toCharArray();

        String str = "";
        int length = ch.length;
        for (int i = 0; i < length; i++) {
            int c = (int)ch[i]-48;
            if(c != 0) {
                str += num[c]+unit[length-i-1];
            } else {
                if(i==length-1){}
                else{
                    if(ch[i+1]!='0'){
                        str += num[c];
                    }
                }
            }
        }

        String first = Character.toString(str.charAt(0));
        if (str.length() >= 2 && first.equals("一")){
            return str.substring(1);
        }

        return str;
    }


    /**
     * 验证是否是手机号码
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (number == null || number.length()==0 || number.length()!=11)
            return false;
        else
            return number.matches(telRegex);

    }
    /**
     * dip转pix
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 得到字符数组
     */
    public static String[] getStringArray(Context context, int id) {
        return getResources(context).getStringArray(id);
    }

    /**
     * pix转dip
     */
    public static int pix2dip(Context context, int pix) {
        final float densityDpi = getResources(context).getDisplayMetrics().density;
        return (int) (pix / densityDpi + 0.5f);
    }


    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param id
     * @return
     */
    public static int getDimens(Context context, int id) {
        return (int) getResources(context).getDimension(id);
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param dimenName
     * @return
     */
    public static float getDimens(Context context, String dimenName) {
        return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.getPackageName()));
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(Context context, int stringID) {
        return getResources(context).getString(stringID);
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(Context context, String strName) {
        return getString(context, getResources(context).getIdentifier(strName, "string", context.getPackageName()));
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, View view, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) view.findViewById(id);
        return v;
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, Activity activity, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        T v = (T) activity.findViewById(id);
        return v;
    }

    /**
     * 根据 layout 名字获得 id
     *
     * @param layoutName
     * @return
     */
    public static int findLayout(Context context, String layoutName) {
        int id = getResources(context).getIdentifier(layoutName, "layout", context.getPackageName());
        return id;
    }

    /**
     * 填充view
     *
     * @param detailScreen
     * @return
     */
    public static View inflate(Context context, int detailScreen) {
        return View.inflate(context, detailScreen, null);
    }

    /**
     * 单例 toast
     *
     * @param string
     */
    public static void makeText(Context context, String string) {

        Context context1 = context.getApplicationContext();
        if (mToast == null) {
            mToast = Toast.makeText(context1, string, Toast.LENGTH_SHORT);
        }
        mToast.setText(string);
        mToast.show();
    }


    /**
     * 跳转界面 3
     *
     * @param activity
     * @param homeActivityClass
     */
    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
        activity.startActivity(intent);
    }

    /**
     * 跳转界面 4
     *
     * @param
     */
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getResources(context).getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth(Context context) {
        return getResources(context).getDisplayMetrics().heightPixels;
    }


    /**
     * 获得颜色
     */
    public static int getColor(Context context, int rid) {
        return getResources(context).getColor(rid);
    }

    /**
     * 获得颜色
     */
    public static int getColor(Context context, String colorName) {
        return getColor(context, getResources(context).getIdentifier(colorName, "color", context.getPackageName()));
    }

    /**
     * 移除孩子
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }



    /**
     * 全屏,并且沉侵式状态栏
     *
     * @param activity
     */
    public static void statuInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     * @deprecated Use {@link #configRecyclerView(RecyclerView, RecyclerView.LayoutManager)} instead
     */
    @Deprecated
    public static void configRecycleView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecyclerView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前系统的时间，以毫秒数展示
     * */

    public static long getSystemTime(){
        Date dt = new Date();
        Long time = dt.getTime();

        return time;
    }

    public static long msTomin(long ms){
        return (ms/1000)/60;
    }
    /**
     * 判断是否存在sd卡
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (extraInfo != null && !extraInfo.isEmpty()) {
                if (extraInfo.equalsIgnoreCase("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    public static boolean netIsConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        //手机网络连接状态
        NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //WIFI连接状态
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            //当前无可用的网络
            return false;
        }
        return true;
    }

    /**
     * wifi是否开启
     *
     * @param context
     * @return
     */
    public static boolean isWifiOpen(Context context) {
        boolean isWifiConnect = false;
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // check the networkInfos numbers
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }
    /**
     * 当前是否有网
     *
     * @param context
     * @return
     */
    public static boolean hasInternet(Context context) {
        boolean flag;
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null && manager.getActiveNetworkInfo() != null)
            flag = true;
        else
            flag = false;
        return flag;
    }

    /**
     * 设备是否有相机
     *
     * @param context
     * @return
     */
    public static final boolean hasCamera(Context context) {
        if (_hasCamera == null) {
            PackageManager pckMgr = context
                    .getPackageManager();
            boolean flag = pckMgr
                    .hasSystemFeature("android.hardware.camera.front");
            boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
            boolean flag2;
            if (flag || flag1)
                flag2 = true;
            else
                flag2 = false;
            _hasCamera = Boolean.valueOf(flag2);
        }
        return _hasCamera.booleanValue();
    }


    /**
     * 获取上下文
     * @return
     * 慎用，该方法直接获取全局的context，如果大量被持有，则会导致持有的对象无法被回收而导致内存溢出
     */
    public static Context getContext(){
        return BaseApplication.getApplication();
    }


    /**
     * 获取缓存大小
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) {

        long cacheSize = 0;
        try{
            //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
            cacheSize = getFolderSize(context.getCacheDir());
            //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += getFolderSize(context.getExternalCacheDir());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return getFormatSize(cacheSize);
    }

    /**
     * 清除所有缓存
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
            //下面两句清理webview网页缓存.但是每次执行都报false,我用的是魅蓝5.1的系统，后来发现且/data/data/应用package目录下找不到database文///件夹 不知道是不是个别手机的问题，
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     * @param size
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 通过距离今天的差值来获取日期
     * @param sum
     * @return
     */
    public static String getTime(int sum){



        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,sum);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String dateString = formatter.format(date);

        return dateString;
    }

    public static void longLog(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.i(tag, msg);
    }

    public static  Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    //由出生日期获得年龄
    public static  int getAge(String  birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(parse(birthDay));

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }

    private static final int seconds_of_1minute = 60;

    private static final int seconds_of_30minutes = 30 * 60;

    private static final int seconds_of_1hour = 60 * 60;

    private static final int seconds_of_1day = 24 * 60 * 60;

    private static final int seconds_of_15days = seconds_of_1day * 15;

    private static final int seconds_of_30days = seconds_of_1day * 30;

    private static final int seconds_of_6months = seconds_of_30days * 6;

    private static final int seconds_of_1year = seconds_of_30days * 12;

    public static String getTimeRange(String mTime)
    {



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /**获取当前时间*/
        Date  curDate = new  Date(System.currentTimeMillis());
        String dataStrNew= sdf.format(curDate);
        Date startTime=null;
        try {
            /**将时间转化成Date*/
            curDate=sdf.parse(dataStrNew);
            startTime = sdf.parse(mTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /**除以1000是为了转换成秒*/
        long   between=(curDate.getTime()- startTime.getTime())/1000;
        int   elapsedTime= (int) (between);
        if (elapsedTime < seconds_of_1minute) {

            return "刚刚";
        }
        if (elapsedTime < seconds_of_30minutes) {

            return elapsedTime / seconds_of_1minute + "分钟前";
        }
        if (elapsedTime < seconds_of_1hour) {

            return "半小时前";
        }
        if (elapsedTime < seconds_of_1day) {

            return elapsedTime / seconds_of_1hour + "小时前";
        }
        if (elapsedTime < seconds_of_15days) {

            return elapsedTime / seconds_of_1day + "天前";
        }
        if (elapsedTime < seconds_of_30days) {

            return "半个月前";
        }
        if (elapsedTime < seconds_of_6months) {

            return elapsedTime / seconds_of_30days + "月前";
        }
        if (elapsedTime < seconds_of_1year) {

            return "半年前";
        }
        if (elapsedTime >= seconds_of_1year) {

            return elapsedTime / seconds_of_1year + "年前";
        }
        return "";
    }


    public static String getNowTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }


    public static String createUUID() {
        int machineId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
    }

    public static boolean isHaveChar(String str){
        String zhPattern = "[\u4e00-\u9fa5]+";
        Pattern p = Pattern.compile(zhPattern);
        Matcher m = p.matcher(str);
        if (m.find()){
            return true;
        }else {
            return false;
        }
    }


    public static int isVIP(String roleInfo){
        String role = roleInfo.substring(0,1);
        if (role.equals("0")){
            return 0;
        }else if(role.equals("1")){
            return 1;
        }else {
            return 2;
        }
    }

    /**
     * 密码强度
     *
     * @return Z = 字母 S = 数字 T = 特殊字符
     */ /*  一、假定密码字符数范围6-16，除英文数字和字母外的字符都视为特殊字符：
    弱：^[0-9A-Za-z]{6,16}$
    中：^(?=.{6,16})[0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z]*$
    强：^(?=.{6,16})([0-9A-Za-z]*[^0-9A-Za-z][0-9A-Za-z]*){2,}$
    二、假定密码字符数范围6-16，密码字符允许范围为ASCII码表字符：
    弱：^[0-9A-Za-z]{6,16}$
    中：^(?=.{6,16})[0-9A-Za-z]*[\x00-\x2f\x3A-\x40\x5B-\xFF][0-9A-Za-z]*$
    强：^(?=.{6,16})([0-9A-Za-z]*[\x00-\x2F\x3A-\x40\x5B-\xFF][0-9A-Za-z]*){2,}$*/
     public static String checkPassword(String passwordStr) {
         String regexZ = "\\d*";
         String regexS = "[a-zA-Z]+";
         String regexT = "\\W+$";
         String regexZT = "\\D*";
         String regexST = "[\\d\\W]*";
         String regexZS = "\\w*";
         String regexZST = "[\\w\\W]*";
         if (passwordStr.matches(regexZ)) {
             return "弱";
         }
         if (passwordStr.matches(regexS)) {
             return "弱";
         }
         if (passwordStr.matches(regexT)) {
             return "弱";
         }
         if (passwordStr.matches(regexZT)) {
             return "中";
         }
         if (passwordStr.matches(regexST)) {
             return "中";
         }
         if (passwordStr.matches(regexZS)) {
             return "中";
         }
         if (passwordStr.matches(regexZST)) {
             return "强";
         }
         return passwordStr;
     }


    /**
     * 返回版本号
     * 对应build.gradle中的versionCode
     *
     * @param context
     * @return
     */
    public static String getVersionCode(Context context) {
        String versionCode = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
//    public static String getDeviceId(Context context) {
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        String deviceId = tm.getDeviceId();
//        if (deviceId == null) {
//            return -;
//        } else {
//            return deviceId;
//        }
//    }
    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }
    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return
     */
    public static int getBuildLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }
    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     *
     * @return
     */
    public static String getBuildVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


}
