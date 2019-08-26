package com.leothon.cogito.Mvp.View.Activity.BuyClassActivity;

import com.leothon.cogito.Bean.SelectClass;

import java.util.ArrayList;

public class BuyClassPresenter implements BuyClassContract.IBuyClassPresenter, BuyClassContract.OnBuyClassFinishedListener {
    private BuyClassContract.IBuyClassView iBuyClassView;
    private BuyClassContract.IBuyClassModel iBuyClassModel;

    public BuyClassPresenter(BuyClassContract.IBuyClassView iBuyClassView){
        this.iBuyClassView = iBuyClassView;
        this.iBuyClassModel = new BuyClassModel();
    }


    @Override
    public void loadBuyClass(ArrayList<SelectClass> selectClasses) {
        if (iBuyClassView != null){
            iBuyClassView.loadBuyClass(selectClasses);
        }
    }

    @Override
    public void loadMoreBuyClass(ArrayList<SelectClass> selectClasses) {
        if (iBuyClassView != null){
            iBuyClassView.loadMoreBuyClass(selectClasses);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iBuyClassView != null){
            iBuyClassView.showMsg(msg);
        }
    }

    @Override
    public void getBuyClass(String token) {
        iBuyClassModel.getBuyClass(token,this);
    }

    @Override
    public void getMoreBuyClass(String token, int currentPage) {
        iBuyClassModel.getMoreBuyClass(token,currentPage,this);
    }

    @Override
    public void onDestroy() {
        iBuyClassModel = null;
        iBuyClassView = null;
    }
}
