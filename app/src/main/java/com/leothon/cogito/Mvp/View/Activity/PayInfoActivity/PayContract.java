package com.leothon.cogito.Mvp.View.Activity.PayInfoActivity;

import com.leothon.cogito.Bean.AlipayBean;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.Orders;

public class PayContract {
    public interface IPayModel{

        void getClassPayInfo(String token,String classId,OnPayFinishedListener listener);

        void createTransactionInfo(Orders orders, OnPayFinishedListener listener);

        void getTransaction(Orders orders, OnPayFinishedListener listener);

        void verifyAlipayTransaction(AlipayBean alipayBean,OnPayFinishedListener listener);

        void checkAliPayOrder(String orderInfo,OnPayFinishedListener listener);
    }

    public interface IPayView{


        void getClassInfo(SelectClass selectClass);

        void showInfo(String msg);

        void createOrderSuccess(Orders orders);

        void getTransactionSuccess(Orders orders);

        void verifyResult(String result);

        void endCheckSuccess(String msg);
        void endCheckFail(String msg);
    }

    public interface OnPayFinishedListener {
        void getClassInfo(SelectClass selectClass);

        void showInfo(String msg);
        void createOrderSuccess(Orders orders);
        void getTransactionSuccess(Orders orders);
        void verifyResult(String result);

        void endCheckSuccess(String msg);
        void endCheckFail(String msg);
    }

    public interface IPayPresenter{

        void getClassPayInfo(String token,String classId);
        void createTransactionInfo(Orders orders);
        void onDestroy();
        void verifyAlipayTransaction(AlipayBean alipayBean);
        void getTransaction(Orders orders);

        void checkAliPayOrder(String orderInfo);

    }
}
