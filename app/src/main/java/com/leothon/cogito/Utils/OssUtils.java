package com.leothon.cogito.Utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.leothon.cogito.Http.Api;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class OssUtils {

    private static OssUtils instance;

    private final String P_ENDPOINT ="http://oss-cn-zhangjiakou.aliyuncs.com";


    private final String P_STSSERVER = "http://www.artepie.com:7080";

    private final String P_BUCKETNAME ="artepie";

    private static OSS oss;

    private SimpleDateFormat simpleDateFormat;

    public OssUtils() {

    }


    private static volatile OssUtils ossUtils;

    public static OssUtils getInstance() {
        if (ossUtils == null) {
            synchronized (OssUtils.class) {
                if (ossUtils == null) {
                    ossUtils = new OssUtils();
                }
            }
        }
        return ossUtils;
    }


    public void getOSs(Context context) {

        //推荐使用OSSAuthCredentialsProvider。token过期可以及时更新

        OssAuthCredentialsUtils ossAuthCredentialsUtils = new OssAuthCredentialsUtils(P_STSSERVER);

        //OSSStsTokenCredentialProvider ossStsTokenCredentialProvider = new OSSStsTokenCredentialProvider("STS.NJw2EHwQRFpbtQcGiFMUNGmwn","6jBfgG1gLq2fxyHznswBfigrHX6eByfUozNfYo44LjPC","CAISkwJ1q6Ft5B2yfSjIr4nCef/8mo5z8bKJdnfSo2kTQdpiqKjcjDz2IHBNdHhsBOAWtvg2mGtR7/4TlqB6T55OSAmcNZIoLD7uV8/kMeT7oMWQweEuuv/MQBquaXPS2MvVfJ+OLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/m6/Ngdc9FHHP7D1x8CcxROxFppeIDKHLVLozNCBPxhXfKB0ca3WgZgGhku6Ok2Z/euFiMzn+Ck7RM+tuseMP7N5g8YcklCu3YhrImKvDztwdL8AVP+atMi6hJxCzKpNn1ASMKs0zWYraJoo03cFYlPfFjQ/9e3/H4lOxlvOvIjJjwyBtLMuxTXj7WWIe62szAFfNU6wOUqks4URqAAayr9KCMxPhjG1NZUCDx9k9Jp0EzQij1/+vA+rG7r6aMKiJq4CmrjgJuz9suRhTq/b2gAbA5Y9IbL/Dn3YYspNxVEkXLK8TmBjcsxw6S7iDVkWXd3pLrotmU5Z/ZrvOU0oDu/mB7OjwIJ53RvAkeShj2qMArLCa7gJxBF5h2ANFu");
        //该配置类如果不设置，会有默认配置，具体可看该类

        ClientConfiguration conf = new ClientConfiguration();

        conf.setConnectionTimeout(15 *1000);// 连接超时，默认15秒

        conf.setSocketTimeout(15 *1000);// socket超时，默认15秒

        conf.setMaxConcurrentRequest(5);// 最大并发请求数，默认5个

        conf.setMaxErrorRetry(2);// 失败后最大重试次数，默认2次

        oss = new OSSClient(context, P_ENDPOINT, ossAuthCredentialsUtils,conf);




    }

    /**

     * 上传图片 上传文件

     *

     * @param context application上下文对象

     * @param ossUpCallback 成功的回调

     * @param img_name 上传到oss后的文件名称，图片要记得带后缀 如：.jpg

     * @param imgPath 图片的本地路径

     */

    public void upImage(Context context, OssUpCallback ossUpCallback, final String img_name, String imgPath) {

        //getOSs(context);

        if (simpleDateFormat==null){

            simpleDateFormat =new SimpleDateFormat("yyyyMMdd");

        }



        final Date data = new Date();

        data.setTime(System.currentTimeMillis());

        PutObjectRequest putObjectRequest = new PutObjectRequest(P_BUCKETNAME,"file/" + simpleDateFormat.format(data)+"/" +  img_name, imgPath);

        putObjectRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {

            @Override

            public void onProgress(PutObjectRequest request,long currentSize,long totalSize) {

                ossUpCallback.inProgress(currentSize, totalSize);

            }

        });

        oss.asyncPutObject(putObjectRequest,new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override

            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                Log.e("MyOSSUtils","------getRequestId:" + result.getRequestId());



                ossUpCallback.successImg(oss.presignPublicObjectURL(P_BUCKETNAME,"file/" + simpleDateFormat.format(data) + "/" + img_name));


            }

            @Override

            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {

                ossUpCallback.successImg("错误原因" + clientException.getMessage() + " " + serviceException.getRawMessage());

            }

        });

    }

    /**

     * 上传图片 上传流

     *

     * @param context application上下文对象

     * @param ossUpCallback 成功的回调

     * @param img_name 上传到oss后的文件名称，图片要记得带后缀 如：.jpg

     * @param imgbyte 图片的byte数组

     */

    public void upImage(Context context,OssUpCallback ossUpCallback,final String img_name,byte[] imgbyte) {

        //getOSs(context);

        if (simpleDateFormat==null){

            simpleDateFormat =new SimpleDateFormat("yyyyMMdd");

        }

        final Date data =new Date();

        data.setTime(System.currentTimeMillis());

        PutObjectRequest putObjectRequest =new PutObjectRequest(P_BUCKETNAME,"file/" + simpleDateFormat.format(data) + "/" + img_name, imgbyte);

        putObjectRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {


            @Override

            public void onProgress(PutObjectRequest request,long currentSize,long totalSize) {

                ossUpCallback.inProgress(currentSize, totalSize);

            }

        });

        oss.asyncPutObject(putObjectRequest, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {


            /**
             * @param request
             * @param result
             */
            @Override

            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                Log.e("MyOSSUtils","------getRequestId:" + result.getRequestId());


                ossUpCallback.successImg(oss.presignPublicObjectURL(P_BUCKETNAME,"file/" + simpleDateFormat.format(data) + "/" + img_name));

            }

            @Override

            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {

                ossUpCallback.successImg("错误原因" + clientException.getMessage() + " " + serviceException.getRawMessage());

            }

        });

    }

    /**

     * 上传视频

     *

     * @param context application上下文对象

     * @param ossUpCallback 成功的回调

     * @param video_name 上传到oss后的文件名称，视频要记得带后缀 如：.mp4

     * @param video_path 视频的本地路径

     */

    public void upVideo(Context context,OssUpCallback ossUpCallback,final String video_name, String video_path) {

        //getOSs(context);

        if (simpleDateFormat==null){

            simpleDateFormat =new SimpleDateFormat("yyyyMMdd");

        }
        final Date data =new Date();

        data.setTime(System.currentTimeMillis());

        PutObjectRequest putObjectRequest =new PutObjectRequest(P_BUCKETNAME,"file/" + simpleDateFormat.format(data)+"/"+ video_name, video_path);

        putObjectRequest.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {

            @Override

            public void onProgress(PutObjectRequest request,long currentSize,long totalSize) {

                ossUpCallback.inProgress(currentSize, totalSize);

            }

        });

        oss.asyncPutObject(putObjectRequest,new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {

            @Override

            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                //Log.e("MyOSSUtils","是执行到这里的" + oss.presignPublicObjectURL(P_BUCKETNAME,"file/" + simpleDateFormat.format(data) + "/" + video_name));

                ossUpCallback.successVideo(oss.presignPublicObjectURL(P_BUCKETNAME,"file/" + simpleDateFormat.format(data)+"/"+video_name));

            }

            @Override

            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {

                ossUpCallback.successVideo("错误原因" + clientException.getMessage() + " " + serviceException.getRawMessage());

            }

        });

    }

    public interface OssUpCallback {

        void successImg(String img_url);

        void successVideo(String video_url);

        /**
         * @param progress
         * @param allsi
         */
        void inProgress(long progress,long allsi);

    }
}
