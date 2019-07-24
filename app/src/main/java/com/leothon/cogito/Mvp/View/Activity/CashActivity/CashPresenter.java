package com.leothon.cogito.Mvp.View.Activity.CashActivity;

import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavContract;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavModel;

public class CashPresenter implements CashContract.OnCashFinishedListener, CashContract.ICashPresenter {

    private CashContract.ICashModel iCashModel;
    private CashContract.ICashView iCashView;

    public CashPresenter(CashContract.ICashView iCashView){
        this.iCashView = iCashView;
        this.iCashModel = new CashModel();
    }

    @Override
    public void checkPsdResult(boolean isHasPsd) {
        if (iCashView != null){
            iCashView.checkPsdResult(isHasPsd);
        }
    }

    @Override
    public void maxCashResult(String msg) {
        if (iCashView != null){
            iCashView.maxCashResult(msg);
        }
    }

    @Override
    public void setPsdSuccess(String msg) {
        if (iCashView != null){
            iCashView.setPsdSuccess(msg);
        }
    }

    @Override
    public void verifyPsdSuccess(String msg) {
        if (iCashView != null){
            iCashView.verifyPsdSuccess(msg);
        }
    }

    @Override
    public void getCashSuccess(String msg) {
        if (iCashView != null){
            iCashView.getCashSuccess(msg);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iCashView != null){
            iCashView.showMsg(msg);
        }
    }

    @Override
    public void isHasPsd(String token) {
        iCashModel.isHasPsd(token,this);
    }

    @Override
    public void getMaxCash(String token) {
        iCashModel.getMaxCash(token,this);
    }

    @Override
    public void setPsd(String token, String psd) {
        iCashModel.setPsd(token,psd,this);
    }

    @Override
    public void verifyPsd(String token, String psd) {
        iCashModel.verifyPsd(token,psd,this);
    }

    @Override
    public void getCash(String info) {
        iCashModel.getCash(info,this);
    }

    @Override
    public void onDestroy() {
        iCashView = null;
        iCashModel = null;
    }
}
