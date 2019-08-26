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
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.CashActivity.CashActivity;
import com.leothon.cogito.Mvp.View.Activity.ContractActivity;
import com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity.WriteArticleContract;
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
public class WalletActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, WalletContract.IWalletView {


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

    private ArrayList<Bill> bills;

    private WalletPresenter walletPresenter;


    private UserEntity userEntity;
    @Override
    public int initLayout() {
        return R.layout.activity_wallet;
    }
    @Override
    public void initData() {

        walletPresenter = new WalletPresenter(this);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);

        swpBill.setProgressViewOffset (false,100,300);
        swpBill.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

        showLoadingAnim();
        walletPresenter.getUserInfo(activitysharedPreferencesUtils.getParams("token","").toString());


    }
    @Override
    public void initView() {
        StatusBarUtils.transparencyBar(this);
        //loadPrice();
//        dividerTitle.setText("充值");
        //initAdapter();



        bills = new ArrayList<>();
        artCoinDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialog();
            }
        });


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
                "2.最低提现额度为100元人民币\n" +
                "3.仅支持提现到支付宝")
                .setPositive("继续提现")
                .setNegtive("取消提现")
                .setTitle("提款提示")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        IntentUtils.getInstence().intent(WalletActivity.this, CashActivity.class);
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
                "在艺派客户端购买商品(专栏,课程,直播)时,可用您账户上的艺币抵扣部分金额.\n" +
                "抵扣规则\n" +
                "1.100艺币等价1元人民币.\n" +
                "2.每单最高可抵扣订单金额的4.8%.\n" +
                "艺币可以提现吗?\n" +
                "不支持提现,仅限于艺派APP内支付抵扣.\n" +
                "艺币的发放及使用最终解释权归本平台所有.\n" )
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

    private void initAdapter(){
        swpBill.setOnRefreshListener(this);
        walletAdapter = new WalletAdapter(this,bills);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        rvBill.setLayoutManager(linearLayoutManager);
        rvBill.setAdapter(walletAdapter);

        rvBill.addOnScrollListener(new loadMoreDataListener(linearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpBill.setRefreshing(true);
                walletPresenter.getMoreBills(activitysharedPreferencesUtils.getParams("token","").toString(),currentPage * 20);
            }
        });
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
    protected void onRestart() {
        super.onRestart();
        walletPresenter.getUserInfo(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    @Override
    public void onRefresh() {
        walletPresenter.getBills(activitysharedPreferencesUtils.getParams("token","").toString());
    }


    @Override
    protected void onDestroy() {
        walletPresenter.onDestroy();
        super.onDestroy();

    }

    @Override
    public void loadUserInfo(User user) {
        hideLoadingAnim();
        userEntity.setUser_art_coin(user.getUser_art_coin());
        userEntity.setUser_balance(user.getUser_balance());
        getDAOSession().update(userEntity);

        if (userEntity.getUser_balance() == null){
            accountBalance.setText("￥0");
        }else {
            accountBalance.setText("￥" + userEntity.getUser_balance());
        }

        if (userEntity.getUser_art_coin() == null){
            artCoin.setText("0");
        }else {
            artCoin.setText(userEntity.getUser_art_coin());
        }
        swpBill.setRefreshing(true);
        walletPresenter.getBills(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    @Override
    public void loadBills(ArrayList<Bill> bills) {
        if (swpBill.isRefreshing()){
            swpBill.setRefreshing(false);
        }
        this.bills = bills;
        initAdapter();
    }

    @Override
    public void loadMoreBills(ArrayList<Bill> bills) {
        if (swpBill.isRefreshing()){
            swpBill.setRefreshing(false);
        }

        for (int i = 0;i < bills.size(); i++){
            this.bills.add(bills.get(i));

        }
        walletAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }
}
