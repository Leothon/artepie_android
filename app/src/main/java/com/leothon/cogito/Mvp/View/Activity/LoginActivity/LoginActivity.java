package com.leothon.cogito.Mvp.View.Activity.LoginActivity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import butterknife.BindView;

/**
 * created by leothon on 2018.7.24
 */

public class LoginActivity extends BaseActivity implements LoginContract.ILoginView,View.OnClickListener{


    //登录页面控件绑定
    @BindView(R.id.login_page)
    LinearLayout loginPage;
    @BindView(R.id.account_login)
    MaterialEditText accountLogin;
    @BindView(R.id.password_login)
    MaterialEditText passwordLogin;
    @BindView(R.id.register_button)
    Button registerButton;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.divider_title)
    TextView dividerTitle;
    @BindView(R.id.qq_login)
    RoundedImageView qqLogin;
    @BindView(R.id.wechat_login)
    RoundedImageView wechatLogin;

    //注册页面的控件绑定
    @BindView(R.id.register_page)
    RelativeLayout registerPage;
    @BindView(R.id.phone_register)
    MaterialEditText phoneRegister;
    @BindView(R.id.account_register)
    MaterialEditText accountRegister;
    @BindView(R.id.password_register)
    MaterialEditText passwordRegister;
    @BindView(R.id.verify_code)
    MaterialEditText verifyCode;
    @BindView(R.id.get_verify_code)
    Button getVerifyCode;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.sound_code)
    TextView soundCode;
    @BindView(R.id.register_contract)
    TextView registerContract;
    @BindView(R.id.password_register_re)
    MaterialEditText passwordRegisterRe;

    @BindView(R.id.bar)
    CardView bar;

    private LoginPresenter loginPresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private static boolean isLoginPage = true;
    private String accountString;
    private String passwordString;

    private String rephone;
    private String reUsername;
    private String repassword;
    private String repasswordre;

    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initview() {
        sharedPreferencesUtils = new SharedPreferencesUtils(this,"AccountPassword");
        bar.setVisibility(View.VISIBLE);
        getToolbar().setNavigationIcon(R.drawable.baseline_clear_black_24);
        setToolbarTitle("");
        setToolbarSubTitle("暂不登录");
        dividerTitle.setText("使用社交账号登录");
        getVerifyCode.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        soundCode.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        wechatLogin.setOnClickListener(this);
        register.setOnClickListener(this);
        registerContract.setOnClickListener(this);
        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isLoginPage){
                    //TODO 跳转到未登录主页面
                    Bundle bundle = new Bundle();
                    bundle.putString("type","home");
                    IntentUtils.getInstence().intent(LoginActivity.this, HostActivity.class,bundle);
                    Constants.loginStatus = 0;
                    finish();
                }else {
                   toLoginPage();
                }
            }
        });
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoginPage){
                    removeAllActivity();
                }else {
                    toLoginPage();
                }
            }
        });

        passwordRegisterRe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals(passwordRegister.getText().toString()) && !passwordRegister.getText().toString().equals("")){
                    passwordRegisterRe.setError("两次输入的密码不一致");
                }
            }
        });


    }
    @Override
    protected boolean isShowBacking() {
        return false;
    }

    public void toLoginPage(){
        setToolbarTitle("");
        setToolbarSubTitle("暂不登录");
        loginPage.setVisibility(View.VISIBLE);
        registerPage.setVisibility(View.GONE);
        dividerTitle.setText("使用社交账号登录");
        getToolbar().setNavigationIcon(R.drawable.baseline_clear_black_24);
        isLoginPage = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                accountString = accountLogin.getText().toString();
                passwordString = passwordLogin.getText().toString();
                User user = new User();
                user.setU_name(accountString);
                user.setU_password(passwordString);
                loginPresenter.validateCrendentials(user);
                break;
            case R.id.get_verify_code:
                if (!phoneRegister.getText().toString().equals("") && CommonUtils.isPhoneNumber(phoneRegister.getText().toString())){
                    CommonUtils.makeText(LoginActivity.this,"已向" + phoneRegister.getText().toString() + "发送验证码");
                    //TODO 进行获取验证码操作
                }else if (phoneRegister.getText().toString().equals("")){
                    CommonUtils.makeText(LoginActivity.this,"手机号码为空");
                }else {
                    CommonUtils.makeText(LoginActivity.this,"手机号码不存在");
                }
                break;
            case R.id.register_button:
                loginPage.setVisibility(View.GONE);
                registerPage.setVisibility(View.VISIBLE);
                dividerTitle.setText("没有收到验证码？");
                setToolbarSubTitle("登录");
                getToolbar().setNavigationIcon(R.drawable.baseline_arrow_back_24);
                isLoginPage = false;
                break;
            case R.id.sound_code:
                CommonUtils.makeText(LoginActivity.this,"暂未开启语音验证码，敬请期待");
                break;
            case R.id.qq_login:
                CommonUtils.makeText(LoginActivity.this,"暂未开放QQ登录");
                break;
            case R.id.wechat_login:
                CommonUtils.makeText(LoginActivity.this,"暂未开放微信登录");
                break;
            case R.id.register:
                rephone = phoneRegister.getText().toString();
                reUsername = accountRegister.getText().toString();
                repassword = passwordRegister.getText().toString();
                repasswordre = passwordRegisterRe.getText().toString();
                User usere = new User();
                usere.setU_phone(rephone);
                usere.setU_name(reUsername);
                usere.setU_password(repassword);
                usere.setReplayLoginPassword(repasswordre);
                loginPresenter.registerInfo(usere);
                break;
            case R.id.register_contract:
                Bundle bundle = new Bundle();
                bundle.putString("type","register");
                IntentUtils.getInstence().intent(LoginActivity.this, ContractActivity.class,bundle);
            default:
                break;
        }

    }

    @Override
    public void initdata() {
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
        Constants.loginStatus = 1;
        sharedPreferencesUtils.setParams("account",accountString);
        sharedPreferencesUtils.setParams("password",passwordString);
        Bundle bundle = new Bundle();
        bundle.putString("type","home");
        IntentUtils.getInstence().intent(LoginActivity.this,HostActivity.class,bundle);
        finish();
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
    public void showRegisterSuccess() {
        CommonUtils.makeText(this,"注册成功");
        toLoginPage();
        accountLogin.setText(reUsername);
        passwordLogin.setText(repassword);
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
}
