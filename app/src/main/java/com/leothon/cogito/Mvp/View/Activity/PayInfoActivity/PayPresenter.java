package com.leothon.cogito.Mvp.View.Activity.PayInfoActivity;

import com.leothon.cogito.Bean.AlipayBean;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.Orders;

public class PayPresenter implements PayContract.IPayPresenter,PayContract.OnPayFinishedListener {

    private PayContract.IPayView iPayView;
    private PayContract.IPayModel iPayModel;

    public PayPresenter(PayContract.IPayView iPayView){
        this.iPayView = iPayView;
        this.iPayModel = new PayModel();
    }
    @Override
    public void getClassInfo(SelectClass selectClass) {
        if (iPayView != null){
            iPayView.getClassInfo(selectClass);
        }
    }

    @Override
    public void showInfo(String msg) {

        if (iPayView != null){
            iPayView.showInfo(msg);
        }

    }

    @Override
    public void createOrderSuccess(Orders orders) {
        if (iPayView != null){
            iPayView.createOrderSuccess(orders);
        }
    }

    @Override
    public void getTransactionSuccess(Orders orders) {
        if (iPayView != null){
            iPayView.getTransactionSuccess(orders);
        }
    }

    @Override
    public void verifyResult(String result) {
        if (iPayView != null){
            iPayView.verifyResult(result);
        }
    }

    @Override
    public void endCheckSuccess(String msg) {
        if (iPayView != null){
            iPayView.endCheckSuccess(msg);
        }
    }

    @Override
    public void endCheckFail(String msg) {
        if (iPayView != null){
            iPayView.endCheckFail(msg);
        }
    }

    @Override
    public void getClassPayInfo(String token, String classId) {
        iPayModel.getClassPayInfo(token,classId,this);
    }

    @Override
    public void createTransactionInfo(Orders orders) {
        iPayModel.createTransactionInfo(orders,this);
    }



    @Override
    public void onDestroy() {
        iPayView = null;
        iPayModel = null;

    }

    @Override
    public void verifyAlipayTransaction(AlipayBean alipayBean) {
        iPayModel.verifyAlipayTransaction(alipayBean,this);
    }

    @Override
    public void getTransaction(Orders orders) {
        iPayModel.getTransaction(orders,this);
    }

    @Override
    public void checkAliPayOrder(String orderInfo) {
        iPayModel.checkAliPayOrder(orderInfo,this);
    }
}
