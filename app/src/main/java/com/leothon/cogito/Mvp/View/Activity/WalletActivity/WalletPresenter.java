package com.leothon.cogito.Mvp.View.Activity.WalletActivity;

import com.leothon.cogito.Bean.Bill;
import com.leothon.cogito.Bean.User;

import java.util.ArrayList;

public class WalletPresenter implements WalletContract.IWalletPresenter, WalletContract.OnWalletFinishedListener {

    private WalletContract.IWalletView iWalletView;
    private WalletContract.IWalletModel iWalletModel;

    public WalletPresenter(WalletContract.IWalletView iWalletView){
        this.iWalletView = iWalletView;
        this.iWalletModel = new WalletModel();
    }

    @Override
    public void loadUserInfo(User user) {
        if (iWalletView != null){
            iWalletView.loadUserInfo(user);
        }
    }

    @Override
    public void loadBills(ArrayList<Bill> bills) {
        if (iWalletView != null){
            iWalletView.loadBills(bills);
        }
    }

    @Override
    public void loadMoreBills(ArrayList<Bill> bills) {
        if (iWalletView != null){
            iWalletView.loadMoreBills(bills);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iWalletView != null){
            iWalletView.showMsg(msg);
        }
    }

    @Override
    public void getUserInfo(String token) {
        iWalletModel.getUserInfo(token,this);
    }

    @Override
    public void getBills(String token) {
        iWalletModel.getBills(token,this);
    }

    @Override
    public void getMoreBills(String token, int currentPage) {
        iWalletModel.getMoreBills(token,currentPage,this);
    }

    @Override
    public void onDestroy() {
        iWalletModel = null;
        iWalletView = null;
    }
}
