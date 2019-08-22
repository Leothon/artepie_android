package com.leothon.cogito.Mvp.View.Activity.LoginActivity;
import	java.security.KeyStore.Builder;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

public class BindPhoneActivity extends BaseActivity implements  LoginContract.ILoginView{

    private LoginPresenter loginPresenter;
    @BindView(R.id.phone_bind_re)
    MaterialEditText phoneBind;

    @BindView(R.id.verify_code_bind_re)
    MaterialEditText verifyCodeBind;


    @BindView(R.id.get_verify_code_bind_re)
    Button getVerifyCode;

    @BindView(R.id.bind_number_re)
    Button bindNumberRe;

    String bindPhoneNumber;
    String bindVerifyCode;

    public int T = 60;
    private Handler mHandler = new Handler();
    private int qqOrWechat = 0;
    private Bundle bundle;
    private Intent intent;
    @Override
    public void initData() {
        intent = getIntent();
        bundle = intent.getExtras();
        loginPresenter = new LoginPresenter(this);
        if (bundle.get("type").equals("qq")){
            qqOrWechat = 1;
        }else {
            qqOrWechat = 2;
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_bindphone;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("绑定手机号码");






        phoneBind.addTextChangedListener(new TextWatcher() {
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
                    loginPresenter.checkPhoneNumberIsExits(phoneBind.getText().toString());
                }else {
                    getVerifyCode.setEnabled(false);
                    getVerifyCode.setBackground(getResources().getDrawable(R.drawable.btnenableback));
                }
            }
        });
    }


    @OnClick(R.id.bind_number_re)
    public void bindPhoneNumber(View view){
        bindPhoneNumber = phoneBind.getText().toString();
        bindVerifyCode = verifyCodeBind.getText().toString();
        if (!bindPhoneNumber.equals("") && !bindVerifyCode.equals("")){

            checkSmsCode(bindPhoneNumber,bindVerifyCode);

        }else if (!bindPhoneNumber.equals("")){
            MyToast.getInstance(this).show("手机号码为空", Toast.LENGTH_SHORT);
        }else {
            MyToast.getInstance(this).show("请填写完整信息",Toast.LENGTH_SHORT);
        }

    }

    @OnClick(R.id.get_verify_code_bind_re)
    public void getVerifyCodeRe(View view){
        if (!phoneBind.getText().toString().equals("") && CommonUtils.isPhoneNumber(phoneBind.getText().toString())){
            new Thread(new MyCountDownTimer()).start();
            getSmsCode(phoneBind.getText().toString());

        }else if (phoneBind.getText().toString().equals("")){
            MyToast.getInstance(this).show("手机号码为空",Toast.LENGTH_SHORT);
        }else {
            MyToast.getInstance(this).show("请输入正确格式的手机号码",Toast.LENGTH_SHORT);
        }
    }


    private void getSmsCode(String phone){

        SMSSDK.getInstance().getSmsCodeAsyn(phone, "161567", new SmscodeListener() {
            @Override
            public void getCodeSuccess(final String uuid) {
                // 获取验证码成功，uuid 为此次获取的唯一标识码。
                MyToast.getInstance(BindPhoneActivity.this).show("验证码发送成功",Toast.LENGTH_SHORT);
            }

            @Override
            public void getCodeFail(int errCode, final String errMsg) {
                MyToast.getInstance(BindPhoneActivity.this).show("验证码发送失败，请重试。" + errMsg,Toast.LENGTH_SHORT);
            }
        });
    }
    private void checkSmsCode(String phone,String code){
        SMSSDK.getInstance().checkSmsCodeAsyn(phone, code, new SmscheckListener() {
            /*
                    * @param code
             */
            @Override
            public void checkCodeSuccess(final String code) {
                // 验证码验证成功，code 为验证码信息。
                showLoadingAnim();
                MyToast.getInstance(BindPhoneActivity.this).show("验证成功",Toast.LENGTH_SHORT);
                User user = (User)bundle.getSerializable("user");
                user.setUser_phone(phoneBind.getText().toString() + "");
                if (qqOrWechat == 1){

                    loginPresenter.qqUserRegister(user);
                }else if (qqOrWechat == 2){
                    loginPresenter.weChatUserRegister(user);
                }else {
                    MyToast.getInstance(BindPhoneActivity.this).show("未知错误",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void checkCodeFail(int errCode, final String errMsg) {
                // 验证码验证失败, errCode 为错误码，详情请见文档后面的错误码表；errMsg 为错误描述。
                MyToast.getInstance(BindPhoneActivity.this).show("验证码不正确" + errMsg,Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void addverifycode(String code) {

    }

    @Override
    public void showFailInfo(String err) {

    }

    @Override
    public void registerORloginSuccess(User user) {

    }

    @Override
    public void isQQRegisterResult(String msg) {

    }

    @Override
    public void isWeChatRegisterResult(String msg) {

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

    @Override
    public void checkNumberResult(String msg) {
        hideLoadingAnim();
        if (msg.equals("yes")){
            phoneBind.setFloatingLabelText("该号码已被其他账号绑定，如果您确定绑定，则会导致那个账号信息丢失");
        }else {
            phoneBind.setFloatingLabelText("该号码可以被绑定");
        }
    }

    private void LoginSuccess(){
        Bundle bundle = new Bundle();
        bundle.putString("type","home");
        IntentUtils.getInstence().intent(BindPhoneActivity.this, HostActivity.class,bundle);
        activitysharedPreferencesUtils.setParams("login",true);
        finish();
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
                    if (phoneBind.getEditableText().toString().length() == 11){
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
}
