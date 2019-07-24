package com.leothon.cogito.Mvp.View.Activity.CashActivity;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.ArticleCommentAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.Mvp.View.Activity.WalletActivity.WalletActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.MD5Utils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;
import com.leothon.cogito.Weight.PsdEditText;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class CashActivity extends BaseActivity implements CashContract.ICashView {


    @BindView(R.id.alipay_cash_account)
    MaterialEditText cashAccount;
    @BindView(R.id.alipay_cash_name)
    MaterialEditText cashName;
    @BindView(R.id.alipay_cash_total)
    MaterialEditText cashTotal;


    private CashPresenter cashPresenter;

    private String cashMax;
    private boolean isHasPsd = false;

    @Override
    public int initLayout() {
        return R.layout.activity_cash;
    }

    @Override
    public void initData() {

        cashPresenter = new CashPresenter(this);


        showSheetDialog();

        cashTotal.setHelperText("输入提现金额");


    }

    //{ "out_biz_no":"3142321423432","payee_type":"ALIPAY_LOGONID","payee_account":"18301102433","amount":"0.5","payer_show_name":"账户余额提现","payee_real_name":"王呈阁"}
    @Override
    public void initView() {
        setToolbarTitle("提现");
        setToolbarSubTitle("");


        showLoadingAnim();
        cashPresenter.isHasPsd(activitysharedPreferencesUtils.getParams("token","").toString());

    }

    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior mDialogBehavior;

    private ImageView close_psd;
    private PsdEditText psdEt;
    private TextView psdInfo;

    private String info;

    private void showSheetDialog() {
        View view = View.inflate(CashActivity.this, R.layout.pay_pas_layout, null);

        close_psd = (ImageView) view.findViewById(R.id.close_psd);
        psdEt = (PsdEditText) view.findViewById(R.id.psd_et);
        psdInfo = (TextView)view.findViewById(R.id.psd_info);
        bottomSheetDialog = new BottomSheetDialog(CashActivity.this, R.style.dialog);
        bottomSheetDialog.setContentView(view);
        mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setPeekHeight(getWindowHeight());
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss();
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        psdEt.initStyle(R.drawable.pay_psd_btn, 6, 1f, R.color.dividerColor, R.color.black, 20);

        close_psd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        Window window = bottomSheetDialog.getWindow();
        window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
    }

    @OnClick(R.id.psd_get_cash)
    public void getCash(View view){

        loadWarnDialog();

    }


    private void startGetCash(){
        if (Float.valueOf(cashMax) >= Float.valueOf(cashTotal.getText().toString())){

            if (isHasPsd){
                getCash(cashAccount.getText().toString(),cashName.getText().toString(),cashTotal.getText().toString());
            }else {
                setPsd();
            }
        }else {
            MyToast.getInstance(this).show("您最多可提现：￥" + cashMax,Toast.LENGTH_SHORT);
        }
    }
    private int getWindowHeight() {
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    private void getCash(String account,String name,String count){
        bottomSheetDialog.show();
        psdInfo.setText("请输入艺派提现密码,进行提现");

        psdEt.setOnTextFinishListener(new PsdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                //监听输入完成
                showLoadingAnim();
                cashPresenter.verifyPsd(activitysharedPreferencesUtils.getParams("token","").toString(),MD5Utils.encrypt(str));
                info = "\"payee_type\":\"ALIPAY_LOGONID\",\"payee_account\":\"" + account + "\",\"amount\":\"" + count + "\",\"payer_show_name\":\"账户余额提现\",\"payee_real_name\":\""  + name + "\"}";



            }
        });
    }



    private void setPsd(){
        bottomSheetDialog.show();
        psdInfo.setText("慎重设置6位提现密码，如忘记密码，请咨询客服");
        psdEt.setOnTextFinishListener(new PsdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                //监听输入完成
                showLoadingAnim();
                cashPresenter.setPsd(activitysharedPreferencesUtils.getParams("token","").toString(), MD5Utils.encrypt(str));


            }
        });

    }

    private void loadWarnDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setCanceledOnTouchOutside(true);
        dialog.setMessage(
                "请确认好支付宝账户和实名是您本人的信息")
                .setPositive("继续提现")
                .setNegtive("重新填写")
                .setTitle("提现提示")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        startGetCash();
                        dialog.dismiss();

                    }

                    @Override
                    public void onNegativeClick() {

                        dialog.dismiss();

                    }

                })
                .show();
    }


    @Override
    public void checkPsdResult(boolean isHasPsd) {
        this.isHasPsd = isHasPsd;
        cashPresenter.getMaxCash(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    @Override
    public void maxCashResult(String msg) {
        hideLoadingAnim();
        cashTotal.setHelperText("输入提现金额，最大可提现金额为：￥" + msg);
    }

    @Override
    public void setPsdSuccess(String msg) {
        hideLoadingAnim();
        MyToast.getInstance(this).show("密码设置成功",Toast.LENGTH_SHORT);
        bottomSheetDialog.dismiss();
    }

    @Override
    public void verifyPsdSuccess(String msg) {

        cashPresenter.getCash(info);

    }

    /**
     * @param msg
     */
    @Override
    public void getCashSuccess(String msg) {
        hideLoadingAnim();
        MyToast.getInstance(this).show("提现成功,请前往支付宝查看账单",Toast.LENGTH_SHORT);
        bottomSheetDialog.dismiss();
        onBackPressed();
    }

    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cashPresenter.onDestroy();
    }
}
