package com.leothon.cogito.Mvp.View.Activity.LoginActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Bean.WeChatUserInfo;
import com.leothon.cogito.Constants;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.ContractActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

/**
 * created by leothon on 2018.7.24
 */

public class LoginActivity extends BaseActivity implements LoginContract.ILoginView{


    //有密码登录页面控件绑定
    @BindView(R.id.login_page)
    RelativeLayout loginPage;
    @BindView(R.id.account_login)
    MaterialEditText accountLogin;
    @BindView(R.id.password_login)
    MaterialEditText passwordLogin;
    @BindView(R.id.login_button)
    Button loginButton;



    //免密码登录页面的控件绑定
    @BindView(R.id.register_page)
    RelativeLayout registerPage;
    @BindView(R.id.phone_register)
    MaterialEditText phoneRegister;
    @BindView(R.id.verify_code)
    MaterialEditText verifyCode;
    @BindView(R.id.get_verify_code)
    Button getVerifyCode;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.register_contract)
    TextView registerContract;

    @BindView(R.id.use_password_login)
    TextView usePasswordLogin;

    @BindView(R.id.bar)
    CardView bar;

    @BindView(R.id.add_name_page)
    RelativeLayout addNamePage;
    @BindView(R.id.qq_login)
    RoundedImageView qqLogin;
    @BindView(R.id.wechat_login)
    RoundedImageView wechatLogin;




    private WeChatUserInfo weChatUserInfo;


    private LoginPresenter loginPresenter;
    private static boolean isRegisterPage = true;
    private String accountString;
    private String passwordString;

    private String rephone;
    private String reverifyCode;


    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    public int T = 60;


    private Object loginSuccessResult;
    private Handler mHandler = new Handler();



    private IWXAPI wx_api;

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        loginPresenter = new LoginPresenter(this);
        mTencent = Tencent.createInstance(Constants.APP_ID,LoginActivity.this.getApplicationContext());
        activitysharedPreferencesUtils.clear();

        wx_api = WXAPIFactory.createWXAPI(this,Constants.WeChat_APP_ID,true);
        wx_api.registerApp(Constants.WeChat_APP_ID);
    }

    @Override
    public void initView() {
        bar.setVisibility(View.VISIBLE);
        getVerifyCode.setEnabled(false);
        getToolbar().setNavigationIcon(R.drawable.baseline_clear_black_24);
        setToolbarTitle("");
        setToolbarSubTitle("暂不登录");

        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("type","home");
                activitysharedPreferencesUtils.setParams("token",Constants.visitor_token);
                IntentUtils.getInstence().intent(LoginActivity.this, HostActivity.class,bundle);
                activitysharedPreferencesUtils.setParams("login",false);
                finish();

            }
        });
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegisterPage){

                    EventBus.getDefault().post(1);
                }else {
                    //打开免密码登录
                    toRegisterPage();
                }
            }
        });


        phoneRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 11){
                    getVerifyCode.setEnabled(true);
                    getVerifyCode.setBackground(getResources().getDrawable(R.drawable.btnbackground));
                }else {
                    getVerifyCode.setEnabled(false);
                    getVerifyCode.setBackground(getResources().getDrawable(R.drawable.btnenableback));
                }
            }
        });



    }
    @Override
    protected boolean isShowBacking() {
        return false;
    }

    /**
     * 使用免密码登录
     */
    public void toRegisterPage(){
        registerPage.setVisibility(View.VISIBLE);
        loginPage.setVisibility(View.GONE);
        addNamePage.setVisibility(View.GONE);
        getToolbar().setNavigationIcon(R.drawable.baseline_clear_black_24);
        isRegisterPage = true;
    }


    @OnClick(R.id.register)
    public void registerLogin(View view){
        rephone = phoneRegister.getText().toString();
        reverifyCode = verifyCode.getText().toString();
        if (!rephone.equals("") && !reverifyCode.equals("")){

            checkSmsCode(rephone,reverifyCode);

        }else if (!rephone.equals("")){
            MyToast.getInstance(this).show("手机号码为空",Toast.LENGTH_SHORT);
        }else {
            MyToast.getInstance(this).show("请填写完整信息",Toast.LENGTH_SHORT);
        }

    }

    @OnClick(R.id.get_verify_code)
    public void getVerifyCode(View view){
        if (!phoneRegister.getText().toString().equals("") && CommonUtils.isPhoneNumber(phoneRegister.getText().toString())){
            //MyToast.getInstance(this).show("已向" + phoneRegister.getText().toString() + "发送验证码",Toast.LENGTH_SHORT);
            new Thread(new MyCountDownTimer()).start();
            //loginPresenter.verifyphone(phoneRegister.getText().toString());
            getSmsCode(phoneRegister.getText().toString());

        }else if (phoneRegister.getText().toString().equals("")){
            MyToast.getInstance(this).show("手机号码为空",Toast.LENGTH_SHORT);
        }else {
            MyToast.getInstance(this).show("请输入正确格式的手机号码",Toast.LENGTH_SHORT);
        }
    }
    @OnClick(R.id.use_password_login)
    public void usePasswordLogin(View view){
        registerPage.setVisibility(View.GONE);
        loginPage.setVisibility(View.VISIBLE);
        addNamePage.setVisibility(View.GONE);
        getToolbar().setNavigationIcon(R.drawable.baseline_arrow_back_24);
        isRegisterPage = false;
    }
    @OnClick(R.id.login_button)
    public void loginTo(View view){
        accountString = accountLogin.getText().toString();
        passwordString = passwordLogin.getText().toString();
        if (accountString.equals("") || passwordString.equals("")){
            MyToast.getInstance(this).show("请填写完整手机号和密码",Toast.LENGTH_SHORT);
        }else if (CommonUtils.isPhoneNumber(accountString)){
            loginPresenter.login(accountString,passwordString);
            showLoadingAnim();
        }else {
            MyToast.getInstance(this).show("请输入正确格式的手机号码",Toast.LENGTH_SHORT);
        }



    }
    @OnClick(R.id.register_contract)
    public void registerContract(View view){

        IntentUtils.getInstence().intent(LoginActivity.this, ContractActivity.class);
    }

    @OnClick(R.id.wechat_login)
    public void wechatLogin(View view){
        showLoadingAnim();
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        wx_api.sendReq(req);
    }
    @OnClick(R.id.qq_login)
    public void qqLogin(View view){
        showLoadingAnim();
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(LoginActivity.this,"all", mIUiListener);
    }


    private void getSmsCode(String phone){

        SMSSDK.getInstance().getSmsCodeAsyn(phone, "161567", new SmscodeListener() {
            @Override
            public void getCodeSuccess(final String uuid) {
                // 获取验证码成功，uuid 为此次获取的唯一标识码。
                MyToast.getInstance(LoginActivity.this).show("验证码发送成功",Toast.LENGTH_SHORT);
            }

            @Override
            public void getCodeFail(int errCode, final String errMsg) {
                MyToast.getInstance(LoginActivity.this).show("验证码发送失败，请重试。" + errMsg,Toast.LENGTH_SHORT);
            }
        });
    }

    private void checkSmsCode(String phone,String code){
        SMSSDK.getInstance().checkSmsCodeAsyn(phone, code, new SmscheckListener() {
            @Override
            public void checkCodeSuccess(final String code) {
                // 验证码验证成功，code 为验证码信息。
                showLoadingAnim();
                loginPresenter.registerInfo(phone);
            }

            @Override
            public void checkCodeFail(int errCode, final String errMsg) {
                // 验证码验证失败, errCode 为错误码，详情请见文档后面的错误码表；errMsg 为错误描述。
                MyToast.getInstance(LoginActivity.this).show("验证码不正确" + errMsg,Toast.LENGTH_SHORT);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        hideLoadingAnim();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle.containsKey("wechatinfo")){
            WeChatUserInfo weChatUserInfo = (WeChatUserInfo)bundle.getSerializable("wechatinfo");
            Log.e(TAG, "姓名" + weChatUserInfo.getNickname() + "\n性别" + weChatUserInfo.getSex() + "\n位置" + weChatUserInfo.getCountry() + weChatUserInfo.getCity() + weChatUserInfo.getProvince() + "\naccessToken" + weChatUserInfo.getAccesstoken() + "\n头像" + weChatUserInfo.getHeadimgurl());
            loginPresenter.isWechatRegister(weChatUserInfo.getAccesstoken());
            this.weChatUserInfo = weChatUserInfo;
        }
    }

    @Override
    public void addverifycode(String code) {
        verifyCode.setText(code);

    }

    @Override
    public void showFailInfo(String err) {
        hideLoadingAnim();
        MyToast.getInstance(this).show(err,Toast.LENGTH_SHORT);
    }

    @Override
    public void registerORloginSuccess(User user) {
        hideLoadingAnim();
        MyToast.getInstance(this).show("成功!",Toast.LENGTH_SHORT);


        UserEntity userEntity = new UserEntity(user.getUser_id(),user.getUser_name(),user.getUser_icon(),user.getUser_birth(),user.getUser_sex(),user.getUser_signal(),user.getUser_address(),user.getUser_password(),user.getUser_token(),user.getUser_status(),user.getUser_register_time(),user.getUser_register_ip(),user.getUser_lastlogin_time(),user.getUser_phone(),user.getUser_role(),user.getUser_balance(),user.getUser_art_coin());
        getDAOSession().insert(userEntity);
        LoginSuccess();
    }

    @Override
    public void isQQRegisterResult(String msg) {
        if (msg.equals("0")){

            loginPresenter.loginByQQ(mTencent.getAccessToken());

        }else if (msg.equals("1")){
            User user = new User();
            JSONObject jsonObject = (JSONObject)loginSuccessResult;
            try {
                user.setUser_icon(jsonObject.getString("figureurl_2"));
                user.setUser_name(jsonObject.getString("nickname"));
                if (jsonObject.getString("gender").equals("男")){
                    user.setUser_sex(1);
                }else if (jsonObject.getString("gender").equals("女")){
                    user.setUser_sex(2);
                }else {
                    user.setUser_sex(0);
                }
                user.setTencent_token(mTencent.getAccessToken());
                loginPresenter.qqUserRegister(user);
            }catch (JSONException e){
                e.printStackTrace();
            }

        }else {
            MyToast.getInstance(this).show("未知错误",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void isWeChatRegisterResult(String msg) {
        if (msg.equals("0")){

            loginPresenter.loginByQQ(mTencent.getAccessToken());

        }else if (msg.equals("1")){
            User user = new User();


            user.setUser_icon(weChatUserInfo.getHeadimgurl());
            user.setUser_name(weChatUserInfo.getNickname());
            user.setUser_sex(weChatUserInfo.getSex());
            user.setTencent_token(weChatUserInfo.getAccesstoken());
            user.setUser_address(weChatUserInfo.getCountry() + weChatUserInfo.getProvince() + weChatUserInfo.getCity());

            loginPresenter.weChatUserRegister(user);


        }else {
            MyToast.getInstance(this).show("未知错误",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void qqUserRegisterSuccess(User user) {
        UserEntity userEntity = new UserEntity(user.getUser_id(),user.getUser_name(),user.getUser_icon(),user.getUser_birth(),user.getUser_sex(),user.getUser_signal(),user.getUser_address(),user.getUser_password(),user.getUser_token(),user.getUser_status(),user.getUser_register_time(),user.getUser_register_ip(),user.getUser_lastlogin_time(),user.getUser_phone(),user.getUser_role(),user.getUser_balance(),user.getUser_art_coin());
        getDAOSession().insert(userEntity);
        hideLoadingAnim();
        LoginSuccess();
    }

    @Override
    public void weChatUserRegisterSuccess(User user) {
        UserEntity userEntity = new UserEntity(user.getUser_id(),user.getUser_name(),user.getUser_icon(),user.getUser_birth(),user.getUser_sex(),user.getUser_signal(),user.getUser_address(),user.getUser_password(),user.getUser_token(),user.getUser_status(),user.getUser_register_time(),user.getUser_register_ip(),user.getUser_lastlogin_time(),user.getUser_phone(),user.getUser_role(),user.getUser_balance(),user.getUser_art_coin());
        getDAOSession().insert(userEntity);
        hideLoadingAnim();
        LoginSuccess();
    }

    private void LoginSuccess(){
        Bundle bundle = new Bundle();
        bundle.putString("type","home");
        IntentUtils.getInstence().intent(LoginActivity.this,HostActivity.class,bundle);
        activitysharedPreferencesUtils.setParams("login",true);
        finish();
    }




    @Override
    public void showBack() {
        super.showBack();
    }


    @Override
    public void onBackPressed() {
        //removeAllActivity();
        EventBus.getDefault().post(1);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }

    /**
     * 自定义倒计时类，实现Runnable接口
     */
    class MyCountDownTimer implements Runnable {

        @Override
        public void run() {

            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getVerifyCode.setClickable(false);
                        getVerifyCode.setText(T + "秒后重新获取");
                        getVerifyCode.setBackground(getResources().getDrawable(R.drawable.btnenableback));
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (phoneRegister.getEditableText().toString().length() == 11){
                        getVerifyCode.setClickable(true);
                        getVerifyCode.setText("获取验证码");
                        getVerifyCode.setBackground(getResources().getDrawable(R.drawable.btnbackground));
                    }else {
                        getVerifyCode.setClickable(false);
                        getVerifyCode.setText("获取验证码");
                        getVerifyCode.setBackground(getResources().getDrawable(R.drawable.btnenableback));
                    }

                }
            });
            T = 60; //最后再恢复倒计时时长
        }
    }





    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            MyToast.getInstance(LoginActivity.this).show("授权成功",Toast.LENGTH_SHORT);
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                final String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        //MyToast.getInstance(this).show(LoginActivity.this,"登录成功");
                        loginSuccessResult = response;
                        loginPresenter.isQQRegister(accessToken);
                        //Log.e(TAG,"登录成功" + response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        MyToast.getInstance(LoginActivity.this).show("登录失败请重试",Toast.LENGTH_SHORT);
                        Log.e(TAG,"登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);

        super.onActivityResult(requestCode, resultCode, data);
    }
}
