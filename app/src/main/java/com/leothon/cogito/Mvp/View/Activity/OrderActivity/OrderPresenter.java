package com.leothon.cogito.Mvp.View.Activity.OrderActivity;

import com.leothon.cogito.Bean.OrderHis;

import java.util.ArrayList;

public class OrderPresenter implements OrderContract.IOrderPresenter,OrderContract.OnOrderFinishedListener{
    private OrderContract.IOrderView iOrderView;
    private OrderContract.IOrderModel iOrderModel;

    public OrderPresenter(OrderContract.IOrderView iOrderView){
        this.iOrderView = iOrderView;
        this.iOrderModel = new OrderModel();
    }
    @Override
    public void loadOrders(ArrayList<OrderHis> orderHis) {
        if (iOrderView != null){
            iOrderView.loadOrders(orderHis);
        }
    }

    @Override
    public void loadMoreOrders(ArrayList<OrderHis> orderHis) {
        if (iOrderView != null){
            iOrderView.loadMoreOrders(orderHis);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iOrderView != null){
            iOrderView.showMsg(msg);
        }
    }

    @Override
    public void getOrders(String token) {
        iOrderModel.getOrders(token,this);
    }

    @Override
    public void getMoreOrders(String token, int currentPage) {
        iOrderModel.getMoreOrders(token,currentPage,this);
    }

    @Override
    public void onDestroy() {
        iOrderModel = null;
        iOrderView = null;
    }
}
