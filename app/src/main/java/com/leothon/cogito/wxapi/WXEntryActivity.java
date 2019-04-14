package com.leothon.cogito.wxapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leothon.cogito.Bean.WeChatData;
import com.leothon.cogito.Bean.WeChatUserInfo;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.Mvp.View.Activity.LoginActivity.LoginActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.WechatUtils;
import com.leothon.cogito.View.MyToast;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;


import okhttp3.Response;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private BaseResp resp = null;
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    private String WX_APP_SECRET = "46601a2aa172c3884927f6cbcc502ec3";

    private String accessToken = "";

    private ClassDetail classDetail;
    private IWXAPI wx_api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wx_api = WXAPIFactory.createWXAPI(this,Constants.WeChat_APP_ID,true);
        wx_api.handleIntent(getIntent(), this);
        wx_api.registerApp(Constants.WeChat_APP_ID);
        if (getIntent().hasExtra("flag")){
            flag = getIntent().getStringExtra("flag");
            classDetail = (ClassDetail)getIntent().getSerializableExtra("data");
            Log.e("TGA",flag+"-----------------flag-----------");
            if("1".equals(flag)){
                shareWXSceneTimeline(classDetail);
            }else if("2".equals(flag)){
                shareWXSceneSession(classDetail);
            }else{
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
        finish();
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
        String str = getUserInfo(weChatData.getAccess_token(), weChatData.getOpenid());
        getUserInfo(str);
    }

    // 解析用户信息
    private void parseJSONUser(String jsonData) {
        Gson gson = new Gson();
        Log.e( "parseJSONUser: ", jsonData);
        WeChatUserInfo weChatUserInfo = gson.fromJson(jsonData,WeChatUserInfo.class);

        weChatUserInfo.setAccesstoken(accessToken);
        Bundle bundle = new Bundle();
        bundle.putSerializable("wechatinfo",weChatUserInfo);
        IntentUtils.getInstence().intent(WXEntryActivity.this,LoginActivity.class,bundle);
    }


    public  enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}
    public  String flag;



    public void shareWXSceneSession(ClassDetail classDetail) {
        share(SHARE_TYPE.Type_WXSceneSession,classDetail);
    }

    public void shareWXSceneTimeline(ClassDetail classDetail) {
        share(SHARE_TYPE.Type_WXSceneTimeline,classDetail);
    }

    private void share(SHARE_TYPE type,ClassDetail classDetail) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "https://github.com/leothon";
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = classDetail.getTeaClasss().getSelectlisttitle();
        msg.description = classDetail.getTeaClasss().getSelectdesc();
        BitmapDrawable bmpDraw = (BitmapDrawable) getResources().getDrawable(
                R.drawable.icon);
        Bitmap thumb = bmpDraw.getBitmap();
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        switch (type) {
            case Type_WXSceneSession:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Type_WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }
        wx_api.sendReq(req);
        //finish();
    }
}
