package com.leothon.cogito.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.WeChatData;
import com.leothon.cogito.Bean.WeChatUserInfo;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.WechatUtils;
import com.leothon.cogito.View.MyToast;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;


import okhttp3.Response;

import static com.leothon.cogito.Utils.ImageUtils.getBytesByBitmap;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private BaseResp resp = null;
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    private String WX_APP_SECRET = "46601a2aa172c3884927f6cbcc502ec3";



    private String accessToken = "";
    private ClassDetail classDetail;
    private Article article;
    private IWXAPI wx_api;
    private String openId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wx_api = WXAPIFactory.createWXAPI(this,Constants.WeChat_APP_ID,true);
        wx_api.handleIntent(getIntent(), this);
        wx_api.registerApp(Constants.WeChat_APP_ID);
        if (getIntent().hasExtra("flag")){
            flag = getIntent().getStringExtra("flag");
            if (getIntent().hasExtra("data")){
                classDetail = (ClassDetail)getIntent().getSerializableExtra("data");
                Log.e("TGA",flag+"-----------------flag-----------");
                if("1".equals(flag)){

                    Glide.with(this).asBitmap().load(classDetail.getTeaClasss().getSelectbackimg()).into(new SimpleTarget<Bitmap>() {
                        /**
                         * 成功的回调
                         */
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {


                            shareWXSceneTimeline(classDetail,bitmap);

                        }

                        /**
                         * 失败的回调
                         */
                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            MyToast.getInstance(WXEntryActivity.this).show("分享失败，请重试",Toast.LENGTH_SHORT);
                            finish();

                        }
                    });

                }else if("2".equals(flag)){

                    Glide.with(this).asBitmap().load(classDetail.getTeaClasss().getSelectbackimg()).into(new SimpleTarget<Bitmap>() {
                        /**
                         * 成功的回调
                         */
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {


                            shareWXSceneSession(classDetail,bitmap);
                        }

                        /**
                         * 失败的回调
                         */
                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            MyToast.getInstance(WXEntryActivity.this).show("分享失败，请重试",Toast.LENGTH_SHORT);
                            finish();

                        }
                    });

                }else{
                    finish();
                }
            }else {
                article = (Article)getIntent().getSerializableExtra("article");
                if("3".equals(flag)){

                    Glide.with(this).asBitmap().load(article.getArticleImg()).into(new SimpleTarget<Bitmap>() {
                        /**
                         * 成功的回调
                         */
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {


                            shareArticleWXSceneTimeline(article,bitmap);
                        }

                        /**
                         * 失败的回调
                         */
                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            MyToast.getInstance(WXEntryActivity.this).show("分享失败，请重试",Toast.LENGTH_SHORT);
                            finish();

                        }
                    });

                }else if("4".equals(flag)){
                    Glide.with(this).asBitmap().load(article.getArticleImg()).into(new SimpleTarget<Bitmap>() {
                        /**
                         * 成功的回调
                         */
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {


                            shareArticleWXSceneSession(article,bitmap);
                        }

                        /**
                         * 失败的回调
                         */
                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            MyToast.getInstance(WXEntryActivity.this).show("分享失败，请重试",Toast.LENGTH_SHORT);
                            finish();

                        }
                    });

                }else{
                    finish();
                }
            }

        }else if (getIntent().hasExtra("share")){
            String share = getIntent().getStringExtra("share");

            if ("1".equals(share)){

                QAData qaData = (QAData)getIntent().getSerializableExtra("qa");
                Glide.with(this).asBitmap().load(qaData.getQa_video_cover()).into(new SimpleTarget<Bitmap>() {
                    /**
                     * 成功的回调
                     */
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {


                        shareQaWXSceneTimeline(qaData,bitmap);
                    }

                    /**
                     * 失败的回调
                     */
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        MyToast.getInstance(WXEntryActivity.this).show("分享失败，请重试",Toast.LENGTH_SHORT);
                        finish();

                    }
                });

            }else if ("2".equals(share)){

                //showLoadingAnim();
                QAData qaData = (QAData)getIntent().getSerializableExtra("qa");
                Glide.with(this).asBitmap().load(qaData.getQa_video_cover()).into(new SimpleTarget<Bitmap>() {
                    /**
                     * 成功的回调
                     */
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

                        shareQaWXSceneSession(qaData,bitmap);
                    }

                    /**
                     * 失败的回调
                     */
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        MyToast.getInstance(WXEntryActivity.this).show("分享失败，请重试",Toast.LENGTH_SHORT);
                        finish();

                    }
                });

            }else if ("3".equals(share)){
                //showLoadingAnim();
                QADataDetail qaDataDetail = (QADataDetail)getIntent().getSerializableExtra("qad");
                Glide.with(this).asBitmap().load(qaDataDetail.getQaData().getQa_video_cover()).into(new SimpleTarget<Bitmap>() {
                    /**
                     * 成功的回调
                     */
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

                        //hideLoadingAnim();

                        shareQaDetailWXSceneTimeline(qaDataDetail,bitmap);
                    }

                    /**
                     * 失败的回调
                     */
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        MyToast.getInstance(WXEntryActivity.this).show("分享失败，请重试",Toast.LENGTH_SHORT);
                        finish();

                    }
                });

            }else if ("4".equals(share)){
                //showLoadingAnim();
                QADataDetail qaDataDetail = (QADataDetail)getIntent().getSerializableExtra("qad");
                Glide.with(this).asBitmap().load(qaDataDetail.getQaData().getQa_video_cover()).into(new SimpleTarget<Bitmap>() {
                    /**
                     * 成功的回调
                     */
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {

                        //hideLoadingAnim();

                        shareQaDetailWXSceneSession(qaDataDetail,bitmap);
                    }

                    /**
                     * 失败的回调
                     */
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        MyToast.getInstance(WXEntryActivity.this).show("分享失败，请重试",Toast.LENGTH_SHORT);
                        finish();

                    }
                });

            }else {
                finish();
            }
        }

    }



    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp != null) {
            this.resp = resp;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "成功";
                MyToast.getInstance(this).show(result,Toast.LENGTH_SHORT);
                String code = ((SendAuth.Resp) resp).code;
                Log.i("TGA", code + "------------");
                /*
                 * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
                 */
                String get_access_token = getCodeRequest(code);
                Map<String, String> reqBody = new ConcurrentSkipListMap<>();
                WechatUtils netUtils = WechatUtils.getInstance();
                netUtils.postDataAsynToNet(get_access_token, reqBody,
                        new WechatUtils.MyNetCall() {
                            @Override
                            public void success(okhttp3.Call call, Response response) throws IOException {
                                String responseData = response.body().string();
                                Log.e( "success: ", responseData);
                                parseJSONWithGSON(responseData);
                            }

                            @Override
                            public void failed(okhttp3.Call call, IOException e) {

                            }
                        });

                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消";
                MyToast.getInstance(this).show(result,Toast.LENGTH_SHORT);
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "被拒绝";
                MyToast.getInstance(this).show(result,Toast.LENGTH_SHORT);
                finish();
                break;
            default:
                result = "返回";
                MyToast.getInstance(this).show(result,Toast.LENGTH_SHORT);
                finish();
                break;
        }
    }

    /**
     * 通过拼接的用户信息url获取用户信息
     *
     * @param user_info_url
     */
    private void getUserInfo(String user_info_url) {

        Map<String, String> reqBody = new ConcurrentSkipListMap<>();
        WechatUtils netUtils = WechatUtils.getInstance();
        netUtils.postDataAsynToNet(user_info_url, reqBody,
                new WechatUtils.MyNetCall() {
                    @Override
                    public void success(okhttp3.Call call, Response response) throws IOException {
                        String str = response.body().string();
                        Log.e( "success: ", "用户信息" + str);
                        parseJSONUser(str);
                    }

                    @Override
                    public void failed(okhttp3.Call call, IOException e) {

                    }
                });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        wx_api.handleIntent(intent, this);
        //finish();
    }



    /**
     * 获取access_token的URL（微信）
     *
     * @param code
     *            授权时，微信回调给的
     * @return URL
     */
    private String getCodeRequest(String code) {
        String result = null;
        GetCodeRequest = GetCodeRequest.replace("APPID",
                urlEnodeUTF8(Constants.WeChat_APP_ID));
        GetCodeRequest = GetCodeRequest.replace("SECRET",
                urlEnodeUTF8(WX_APP_SECRET));
        GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
        result = GetCodeRequest;
        return result;
    }

    /**
     * 获取用户个人信息的URL（微信）
     *
     * @param access_token
     *            获取access_token时给的
     * @param openid
     *            获取access_token时给的
     * @return URL
     */
    private String getUserInfo(String access_token, String openid) {
        String result = null;
        GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
                urlEnodeUTF8(access_token));
        GetUserInfo = GetUserInfo.replace("OPENID", urlEnodeUTF8(openid));
        result = GetUserInfo;
        return result;
    }

    private String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 解析返回的数据
    private void parseJSONWithGSON(String jsonData) {

        Gson gson = new Gson();

        WeChatData weChatData = gson.fromJson(jsonData,WeChatData.class);
        accessToken = weChatData.getAccess_token();
        openId = weChatData.getOpenid();
        String str = getUserInfo(weChatData.getAccess_token(), weChatData.getOpenid());
        getUserInfo(str);
    }

    // 解析用户信息
    private void parseJSONUser(String jsonData) {
        Gson gson = new Gson();

        WeChatUserInfo weChatUserInfo = gson.fromJson(jsonData,WeChatUserInfo.class);
        //TODO
        weChatUserInfo.setAccesstoken(openId);
        Bundle bundle = new Bundle();
        bundle.putSerializable("wechatinfo",weChatUserInfo);
        IntentUtils.getInstence().intent(WXEntryActivity.this,LoginActivity.class,bundle);
    }


    public  enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}
    public  String flag;


    /**
     * @param qaData
     */
    public void shareQaWXSceneSession(QAData qaData,Bitmap bitmap) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.artepie.cn"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_e3995a920cc1";     // 小程序原始id
        miniProgramObj.path = "pages/video/video?classid=" + qaData.getQa_id();            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = "艺派视频：" + qaData.getQa_content();                    // 小程序消息title
        msg.description = qaData.getQa_content();               // 小程序消息desc
        //msg.thumbData = getThumb(qaData.getQa_video_cover());小程序消息封面图片，小于128k 这个字节数组不能为空 否则无法调起微信页面 调试的时候可以先随便赋值一个new byte[n]
        msg.setThumbImage(bitmap);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        wx_api.sendReq(req);
        finish();
    }

    public void shareQaWXSceneTimeline(QAData qaData,Bitmap bitmap) {


        WXVideoObject videoObject = new WXVideoObject();
//        2、设置视频url
        videoObject.videoUrl = qaData.getQa_video().replace("artepie.oss-cn-zhangjiakou.aliyuncs.com","www.artepie.cn");

//        3、创建WXMediaMessage对象，
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = videoObject;
        msg.title = qaData.getQa_content();
        msg.description = qaData.getQa_content();
        msg.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = buildTransaction("video");
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        wx_api.sendReq(req);

//        WXWebpageObject webpageObject = new WXWebpageObject();
//        webpageObject.webpageUrl = "http://www.artepie.cn";
//        WXMediaMessage msg = new WXMediaMessage(webpageObject);
//        msg.title = "艺派视频：" + qaData.getQa_content();
////        BitmapDrawable bmpDraw = (BitmapDrawable) getResources().getDrawable(
////                R.drawable.icon);
////        Bitmap thumb = bmpDraw.getBitmap();    // 小程序消息title
////        msg.description = qaData.getQa_content();
////        msg.setThumbImage(thumb);
//        try {
//            msg.setThumbImage(Glide.with(CommonUtils.getContext())
//                    .asBitmap()
//                    .load(qaData.getQaData().getQa_video_cover())
//                    .submit(300,300).get());
//        }catch (Exception e){
//            e.fillInStackTrace();
//        }
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        wx_api.sendReq(req);
        finish();
    }


    public void shareQaDetailWXSceneSession(QADataDetail qaDataDetail,Bitmap bitmap) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.artepie.cn"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_e3995a920cc1";     // 小程序原始id
        miniProgramObj.path = "pages/video/video?classid=" + qaDataDetail.getQaData().getQa_id();            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = "发布者：" + qaDataDetail.getQaData().getUser_name();                    // 小程序消息title
        msg.description = qaDataDetail.getQaData().getQa_content();               // 小程序消息desc
//        msg.thumbData = getThumb();                      // 小程序消息封面图片，小于128k 这个字节数组不能为空 否则无法调起微信页面 调试的时候可以先随便赋值一个new byte[n]
        msg.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        wx_api.sendReq(req);
        finish();
    }

    public void shareQaDetailWXSceneTimeline(QADataDetail qaDataDetail,Bitmap bitmap) {

        WXVideoObject videoObject = new WXVideoObject();
//        2、设置视频url
        videoObject.videoUrl = qaDataDetail.getQaData().getQa_video().replace("artepie.oss-cn-zhangjiakou.aliyuncs.com","www.artepie.cn");
//        3、创建WXMediaMessage对象，
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = videoObject;
        msg.title = qaDataDetail.getQaData().getQa_content();
        msg.description = qaDataDetail.getQaData().getQa_content();
        msg.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = buildTransaction("video");
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        wx_api.sendReq(req);
//        WXWebpageObject webpageObject = new WXWebpageObject();
//        webpageObject.webpageUrl = "http://www.artepie.cn";
//        WXMediaMessage msg = new WXMediaMessage(webpageObject);
//        msg.title = "发布者：" + qaDataDetail.getQaData().getUser_name();
////        BitmapDrawable bmpDraw = (BitmapDrawable) getResources().getDrawable(
////                R.drawable.icon);
////        Bitmap thumb = bmpDraw.getBitmap();    // 小程序消息title
//        msg.description = qaDataDetail.getQaData().getQa_content();
//        try{
//            msg.setThumbImage(Glide.with(CommonUtils.getContext())
//                    .asBitmap()
//                    .load(qaDataDetail.getQaData().getQa_video_cover())
//                    .submit(300,300).get());
//        }catch (Exception e){
//            e.fillInStackTrace();
//        }
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        wx_api.sendReq(req);
        finish();
    }


    public void shareWXSceneSession(ClassDetail classDetail,Bitmap bitmap) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.artepie.cn"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_e3995a920cc1";     // 小程序原始id
        miniProgramObj.path = "pages/index/index?classid=" + classDetail.getTeaClasss().getSelectId();            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = classDetail.getTeaClasss().getSelectlisttitle();                    // 小程序消息title
        msg.description = classDetail.getTeaClasss().getSelectdesc();               // 小程序消息desc
//        msg.thumbData = getThumb();                      // 小程序消息封面图片，小于128k 这个字节数组不能为空 否则无法调起微信页面 调试的时候可以先随便赋值一个new byte[n]
        msg.setThumbImage(bitmap);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        wx_api.sendReq(req);
        finish();
    }

    public void shareWXSceneTimeline(ClassDetail classDetail,Bitmap bitmap) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "http://www.artepie.cn";
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = classDetail.getTeaClasss().getSelectlisttitle();
        msg.description = classDetail.getTeaClasss().getSelectdesc();
        msg.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        wx_api.sendReq(req);
        finish();
    }



    public void shareArticleWXSceneTimeline(Article article,Bitmap bitmap) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "https://www.artepie.com/image/" + article.getArticleId() + ".html";
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = article.getArticleTitle();
        msg.description = article.getArticleAuthorName();
        msg.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        wx_api.sendReq(req);
        finish();
    }


    public void shareArticleWXSceneSession(Article article,Bitmap bitmap) {

        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.artepie.cn"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2

        miniProgramObj.userName = "gh_e3995a920cc1";     // 小程序原始id
        miniProgramObj.path = "pages/article/article?articleid=" + article.getArticleId();            //小程序页面路径
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = article.getArticleTitle();
        msg.description = article.getArticleAuthorName();
        //msg.thumbData = getThumb(article.getArticleImg());                      // 小程序消息封面图片，小于128k 这个字节数组不能为空 否则无法调起微信页面 调试的时候可以先随便赋值一个new byte[n]
        msg.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        wx_api.sendReq(req);
        finish();

    }









    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
