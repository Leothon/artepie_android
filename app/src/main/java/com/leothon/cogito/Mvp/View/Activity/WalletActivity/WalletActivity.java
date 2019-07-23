package com.leothon.cogito.Mvp.View.Activity.WalletActivity;

import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.WalletAdapter;
import com.leothon.cogito.Bean.Bill;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Constants;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.ContractActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.8.12
 */
public class WalletActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.account_balance)
    TextView accountBalance;
    @BindView(R.id.art_coin_balance)
    TextView artCoin;
    @BindView(R.id.art_coin_detail)
    RelativeLayout artCoinDetail;

    @BindView(R.id.swp_bill)
    SwipeRefreshLayout swpBill;
    @BindView(R.id.rv_bill)
    RecyclerView rvBill;


    @BindView(R.id.get_cash)
    Button getCash;

    private WalletAdapter walletAdapter;


    private UserEntity userEntity;
    @Override
    public int initLayout() {
        return R.layout.activity_wallet;
    }
    @Override
    public void initData() {
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);

        swpBill.setProgressViewOffset (false,100,300);
        swpBill.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getUserInfo(activitysharedPreferencesUtils.getParams("token","").toString())
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        MyToast.getInstance(WalletActivity.this).show(errorMsg,Toast.LENGTH_SHORT);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {

                        User user = (User)baseResponse.getData();

                        userEntity.setUser_art_coin(user.getUser_art_coin());
                        userEntity.setUser_balance(user.getUser_balance());
                        getDAOSession().update(userEntity);
                    }
                });


    }
    @Override
    public void initView() {
        StatusBarUtils.transparencyBar(this);
        //loadPrice();
//        dividerTitle.setText("充值");
        //initAdapter();

        accountBalance.setText("￥" + userEntity.getUser_balance());
        artCoin.setText(userEntity.getUser_art_coin());

        artCoinDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialog();
            }
        });
        swpBill.setRefreshing(true);
        getNewBills();


    }

    @OnClick(R.id.get_cash)
    public void getCash(View view){

        loadCashDialog();
    }

    private void loadCashDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setCanceledOnTouchOutside(true);
        dialog.setMessage(
                "1.仅支持提取15个工作日前的交易额\n" +
                "2.最低提现额度为100元人民币")
                .setPositive("继续提现")
                .setNegtive("取消提现")
                .setTitle("提款提示")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        MyToast.getInstance(WalletActivity.this).show("继续提现",Toast.LENGTH_SHORT);
                        dialog.dismiss();

                    }

                    @Override
                    public void onNegativeClick() {

                        dialog.dismiss();

                    }

                })
                .show();
    }

    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setCanceledOnTouchOutside(true);
        dialog.setMessage(
                "什么是艺币?\n" +
                "艺币是艺派提供给忠实用户的奖励代币,可用于艺派APP内购买课程时支付抵扣.\n" +
                "怎么获取艺币?\n" +
                "1.每天签到+1艺币.\n" +
                "2.对外分享平台秀吧视频+1艺币.\n" +
                "3.对外分享平台文章+2艺币.\n" +
                "4.对外分享平台课程+3艺币.\n" +
                "5.更多获取方式,请咨询客服!\n" +
                "如何使用艺币?\n" +
                "1.在艺派客户端购买商品(专栏,课程,直播)时,可用您账户上的艺币抵扣部分金额.\n" +
                "2.艺币不可与其他优惠(平台促销,代金券)等叠加使用.\n" +
                "抵扣规则\n" +
                "1.100艺币等价1元人民币.\n" +
                "2.每单最高可抵扣订单金额的4.8%.\n" +
                "艺币可以提现吗?\n" +
                "艺币不支持体现,仅限于艺派APP内支付抵扣使用.\n" +
                "艺币的发放及使用最终解释权归艺派平台所有.\n")
                .setTitle("")
                .setSingle(true)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();

                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

    private void initAdapter(ArrayList<Bill> bills){
        swpBill.setOnRefreshListener(this);
        walletAdapter = new WalletAdapter(this,bills);

        rvBill.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        rvBill.setAdapter(walletAdapter);

        walletAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                //TODO 跳转详细账单
            }
        });

        walletAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

    }

    @Override
    public void onRefresh() {
        getNewBills();
    }

    private void getNewBills(){

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getBill(activitysharedPreferencesUtils.getParams("token","").toString())
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        MyToast.getInstance(WalletActivity.this).show(errorMsg,Toast.LENGTH_SHORT);

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        ArrayList<Bill> bills = (ArrayList<Bill>)baseResponse.getData();


                        if (swpBill.isRefreshing()){
                            swpBill.setRefreshing(false);
                        }
                        initAdapter(bills);
                    }
                });

    }

}
