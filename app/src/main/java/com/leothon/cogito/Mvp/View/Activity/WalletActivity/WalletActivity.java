package com.leothon.cogito.Mvp.View.Activity.WalletActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Adapter.WalletAdapter;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.ContractActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.Weight.CommonDialog;

import java.util.ArrayList;

import butterknife.BindView;
/**
 * created by leothon on 2018.8.12
 */
public class WalletActivity extends BaseActivity {


    @BindView(R.id.rv_wallet)
    RecyclerView rvWallet;
    @BindView(R.id.account_balance)
    TextView accountBalance;
    @BindView(R.id.art_coin_balance)
    TextView artCoin;
    @BindView(R.id.art_coin_detail)
    RelativeLayout artCoinDetail;

    @BindView(R.id.divider_title)
    TextView dividerTitle;
    @BindView(R.id.recharge_btn)
    Button rechargeBtn;
    @BindView(R.id.protocol_recharge)
    TextView protocolRecharge;

    private WalletAdapter walletAdapter;
    private ArrayList<String> list;
    @Override
    public int initLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initview() {
        StatusBarUtils.transparencyBar(this);
        loadPrice();
        dividerTitle.setText("充值");
        initAdapter();

        accountBalance.setText("￥563");
        artCoin.setText("526");
        Constants.rechargeCount = "0";
        artCoinDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialog();
            }
        });
        protocolRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转到用户充值协议
                Bundle bundle = new Bundle();
                bundle.putString("type","recharge");
                IntentUtils.getInstence().intent(WalletActivity.this, ContractActivity.class,bundle);
            }
        });
        rechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 充值操作
                if (Constants.rechargeCount.equals("0") || Constants.rechargeCount.equals("")){
                    CommonUtils.makeText(CommonUtils.getContext(),"请选择充值金额");
                }else {
                    CommonUtils.makeText(CommonUtils.getContext(),"向账户充值" + Constants.rechargeCount + "元");
                }

            }
        });

    }

    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("艺币是一种虚拟的货币。在本软件上购买课程的时候，使用艺币可以对价格进行折扣。我们提供多种方式获取到艺币，例如分享课程给朋友，推荐朋友下载等")
                .setTitle("艺币介绍")
                .setSingle(true)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        //TODO 删除该收藏

                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

    private void initAdapter(){
        walletAdapter = new WalletAdapter(list,this);
        rvWallet.setHasFixedSize(true);
        rvWallet.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        rvWallet.setAdapter(walletAdapter);

    }

    public void loadPrice() {
        list = new ArrayList<>();
        list.add("￥10");
        list.add("￥20");
        list.add("￥50");
        list.add("￥100");
        list.add("￥200");
        list.add("￥500");
    }
    @Override
    public void initdata() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
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
}
