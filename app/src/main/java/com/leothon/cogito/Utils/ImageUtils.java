package com.leothon.cogito.Utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;

import com.leothon.cogito.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * created by leothon on 8/15/2018.
 */
public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();


    /**
     * 根据Uri获取路径
     * @param contentUri
     * @return
     */
    public static String getRealPathByURI(Uri contentUri, Context context) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri,
                proj, null, null, null);
        if (cursor.moveToFirst()) {
            ;
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    public static Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        String status = Environment.getExternalStorageState();
        SimpleDateFormat timeFormatter = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));
        // ContentValues是我们希望这条记录被创建时包含的数据信息
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        Log.i("", "生成的照片输出路径：" + imageFilePath.toString());
        return imageFilePath;
    }

    /**
     * 图片压缩
     *
     * @param
     * @param file
     */
    public static void compressBmpToFile(File file,int height,int width) {
        Bitmap bmp = decodeSampledBitmapFromFile(file.getPath(), height, width);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		/*while (baos.toByteArray().length / 1024 > 30) {
			baos.reset();
			if (options - 10 > 0) {
				options = options - 10;
				bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
			}
			if (options - 10 <= 0) {
				break;
			}
		}*/
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将图片变成bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getImageBitmap(String path) {
        Bitmap bitmap = null;
        File file = new File(path);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }
        return null;
    }


//=================================图片压缩方法===============================================

    /**
     * 质量压缩
     * @author ping 2015-1-5 下午1:29:58
     * @param image
     * @param maxkb
     * @return
     */
    public static Bitmap compressBitmap(Bitmap image,int maxkb) {
        //L.showlog(压缩图片);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        int options = 100;
        // 循环判断如果压缩后图片是否大于(maxkb)50kb,大于继续压缩
        while (baos.toByteArray().length / 1024 > maxkb) {
            // 重置baos即清空baos
            baos.reset();
            if(options-10>0){
                // 每次都减少10
                options -= 10;
            }
            // 这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    /**
     *
     * @param res
     * @param resId
     * @param reqWidth
     *            所需图片压缩尺寸最小宽度
     * @param reqHeight
     *            所需图片压缩尺寸最小高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     *
     * @param filepath
     * 			 图片路径
     * @param reqWidth
     *			所需图片压缩尺寸最小宽度
     * @param reqHeight
     *          所需图片压缩尺寸最小高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String filepath,int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }

    /**
     *
     * @param bitmap
     * @param reqWidth
     * 			所需图片压缩尺寸最小宽度
     * @param reqHeight
     * 			所需图片压缩尺寸最小高度
     * @return
     */
    public static Bitmap decodeSampledBitmapFromBitmap(Bitmap bitmap,
                                                       int reqWidth, int reqHeight) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] data = baos.toByteArray();

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /**
     * 计算压缩比例值(改进版 by touch_ping)
     *
     * 原版2>4>8...倍压缩
     * 当前2>3>4...倍压缩
     *
     * @param options
     *            解析图片的配置信息
     * @param reqWidth
     *            所需图片压缩尺寸最小宽度O
     * @param reqHeight
     *            所需图片压缩尺寸最小高度
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int picheight = options.outHeight;
        final int picwidth = options.outWidth;

        int targetheight = picheight;
        int targetwidth = picwidth;
        int inSampleSize = 1;

        if (targetheight > reqHeight || targetwidth > reqWidth) {
            while (targetheight  >= reqHeight
                    && targetwidth>= reqWidth) {
                inSampleSize += 1;
                targetheight = picheight/inSampleSize;
                targetwidth = picwidth/inSampleSize;
            }
        }

        Log.i("===","最终压缩比例:" +inSampleSize + "倍");
        Log.i("===", "新尺寸:" +  targetwidth + "*" +targetheight);
        return inSampleSize;
    }

    // 读取图像的旋转度
    public static int readBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     *
     * @param mBitmap
     * @param fileName
     */
    public static void saveBitmapToLocal(Bitmap mBitmap,String fileName) {
        if(mBitmap != null){
            FileOutputStream fos = null;
            try {
                File file = new File(fileName);
                if(file.exists()){
                    file.delete();
                }
                file.createNewFile();
                fos = new FileOutputStream(file);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{

                try {
                    if(fos != null){
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 将下载下来的图片保存到SD卡或者本地.并返回图片的路径(包括文件命和扩展名)
     * @param context
     * @param bitName
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context,String bitName, Bitmap mBitmap) {
        String path = null;
        File f;
        if(mBitmap != null){
            if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
                f = new File(Environment.getExternalStorageDirectory() + File.separator +"images/");
                String fileName = Environment.getExternalStorageDirectory() + File.separator +"images/"+ bitName + ".png";
                path = fileName;
                FileOutputStream fos = null;
                try {
                    if(!f.exists()){
                        f.mkdirs();
                    }
                    File file = new File(fileName);
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{

                    try {
                        if(fos != null){
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }else{
                //本地存储路径
                f = new File(context.getFilesDir() + File.separator +"images/");
                Log.i(TAG, "本地存储路径:"+context.getFilesDir() + File.separator +"images/"+ bitName + ".png");
                path = context.getFilesDir() + File.separator +"images/"+ bitName + ".png";
                FileOutputStream fos = null;
                try {
                    if(!f.exists()){
                        f.mkdirs();
                    }
                    File file = new File(path);
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        if(fos != null){
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        return path;
    }

    /**
     * 删除图片
     * @param context
     * @param bitName
     */
    public void deleteFile(Context context,String bitName) {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File dirFile = new File(Environment.getExternalStorageDirectory() + File.separator + "images/"+ bitName + ".png");
            if (!dirFile.exists()) {
                return;
            }

            dirFile.delete();
        } else {
            File f = new File(context.getFilesDir() + File.separator
                    + "images/" + bitName + ".png");
            if(!f.exists()){
                return;
            }
            f.delete();
        }
    }


    /**
     * 绘制文字到右下角
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text, int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(CommonUtils.dip2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - CommonUtils.dip2px(context, paddingRight + text.length()),
                bitmap.getHeight() - CommonUtils.dip2px(context, paddingBottom));
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text, Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }



    public  static  byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }



    /**
     * 截取屏幕(全屏)并保存
     *
     * @param isSaveStatusBar 是否保留状态栏
     */
    public static Bitmap saveScreenAsImage(Activity mActivity, boolean isSaveStatusBar) {
        View pView = mActivity.getWindow().getDecorView();
        pView.setDrawingCacheEnabled(true);
        pView.buildDrawingCache();
        Bitmap srcBitmap = pView.getDrawingCache();
        // 图片大小
        Point point = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(point);
        int width = pView.getWidth();//point.x;
        int height = pView.getHeight();//point.y;
        // 标题栏高度
        int statusBarHeight = 0;
        if (isSaveStatusBar == false) {
            Rect frame = new Rect();
            mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
            height = height - statusBarHeight;
        }
        // 创建存储的bitmap
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, statusBarHeight, width, height);

        // 清理掉缓存图片,防止内存溢出
        pView.destroyDrawingCache();
        // 保存图片

        return bitmap;
    }

    /**
     * 图片缩放比例
     */
    private static final float BITMAP_SCALE = 0.2f;
    /**
     * 最大模糊度(在0.0到25.0之间)
     */
    private static final float BLUR_RADIUS = 25f;

    private static final int BLACK = R.color.black;

    public static Bitmap blur(Context context, Bitmap image) {
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        // 将缩小后的图片做为预渲染的图片。
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片。
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(BLUR_RADIUS);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

}
