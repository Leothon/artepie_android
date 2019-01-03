package com.leothon.cogito.Mvp.View.Activity.LoginActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.ContractActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.weibo_login)
    RoundedImageView weiboLogin;




    private LoginPresenter loginPresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private static boolean isRegisterPage = true;
    private String accountString;
    private String passwordString;

    private String rephone;
    private String reverifyCode;

    public int T = 60;

    ZLoadingDialog dialog = new ZLoadingDialog(LoginActivity.this);
    private Handler mHandler = new Handler();



    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        sharedPreferencesUtils = new SharedPreferencesUtils(this,"saveToken");
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
                IntentUtils.getInstence().intent(LoginActivity.this, HostActivity.class,bundle);
                Constants.loginStatus = 0;
                finish();

            }
        });
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegisterPage){
                    removeAllActivity();
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
        //TODO 免密码登录
        rephone = phoneRegister.getText().toString();
        reverifyCode = verifyCode.getText().toString();
        User usere = new User();
        usere.setUser_phone(rephone);
        usere.setVerifyCode(reverifyCode);
        showLoadingAnim();
        loginPresenter.registerInfo(usere);
    }

    @OnClick(R.id.get_verify_code)
    public void getVerifyCode(View view){
        //TODO 获取验证码
        if (!phoneRegister.getText().toString().equals("") && CommonUtils.isPhoneNumber(phoneRegister.getText().toString())){
            CommonUtils.makeText(LoginActivity.this,"已向" + phoneRegister.getText().toString() + "发送验证码");
            //TODO 进行获取验证码操作
            new Thread(new MyCountDownTimer()).start();
            loginPresenter.verifyphone(phoneRegister.getText().toString());


        }else if (phoneRegister.getText().toString().equals("")){
            CommonUtils.makeText(LoginActivity.this,"手机号码为空");
        }else {
            CommonUtils.makeText(LoginActivity.this,"请输入正确格式的手机号码");
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
        //TODO 密码登录
        accountString = accountLogin.getText().toString();
        passwordString = passwordLogin.getText().toString();
        User user = new User();
        user.setUser_name(accountString);
        user.setUser_password(passwordString);
        loginPresenter.validateCrendentials(user);
    }
    @OnClick(R.id.register_contract)
    public void registerContract(View view){
        Bundle bundle = new Bundle();
        bundle.putString("type","register");
        IntentUtils.getInstence().intent(LoginActivity.this, ContractActivity.class,bundle);
    }

    @OnClick(R.id.wechat_login)
    public void wechatLogin(View view){
        //TODO 使用微信登录
    }
    @OnClick(R.id.qq_login)
    public void qqLogin(View view){
        //TODO 使用QQ登录
    }
    @OnClick(R.id.weibo_login)
    public void weiboLogin(View view){
        //TODO 使用微博登录
    }


    @Override
    public void initData() {
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void setUsernameORPassWordEmpty() {
        //设置为空显示的东西
        CommonUtils.makeText(LoginActivity.this,"账号或者密码不能为空");
    }

    @Override
    public void showSuccess() {

        //TODO 登录成功，则跳转
        LoginSuccess();
    }

    @Override
    public void showFail() {
        CommonUtils.makeText(this,"登录错误，用户名或者密码不正确，请重试");
    }

    @Override
    public void setSomeEmpty() {
        CommonUtils.makeText(LoginActivity.this,"请填写完整信息");
    }



    @Override
    public void showRegisterFail() {
        CommonUtils.makeText(this,"显示注册错误信息");
    }

    @Override
    public void showphoneIllegal() {
        CommonUtils.makeText(LoginActivity.this,"请输入正确格式的手机号码");
    }

    @Override
    public void addverifycode(String code) {
        verifyCode.setText(code);


    }

    @Override
    public void showFailInfo(String err) {
        CommonUtils.makeText(LoginActivity.this,err);
    }

    @Override
    public void registerORloginSuccess(String info) {
        hideLoadingAnim();
        CommonUtils.makeText(LoginActivity.this,info);
        //TODO 执行注册后的动作
        LoginSuccess();
    }

    private void LoginSuccess(){
        Bundle bundle = new Bundle();
        bundle.putString("type","home");
        IntentUtils.getInstence().intent(LoginActivity.this,HostActivity.class,bundle);
        Constants.loginStatus = 1;//表示登录成功
        finish();
    }

    @Override
    public BaseModel initModel() {
        return null;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showBack() {
        super.showBack();
    }


    @Override
    public void onBackPressed() {
        removeAllActivity();



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


    private void showLoadingAnim(){
        dialog.setLoadingBuilder(Z_TYPE.SEARCH_PATH)
                .setLoadingColor(Color.GRAY)
                .setHintText("请稍后...")
                .setHintTextSize(16)
                .setHintTextColor(Color.GRAY)
                .setDurationTime(0.5)
                .setDialogBackgroundColor(Color.parseColor("#ffffff")) // 设置背景色，默认白色
                .show();
    }

    private void hideLoadingAnim(){
        dialog.cancel();
    }
}
