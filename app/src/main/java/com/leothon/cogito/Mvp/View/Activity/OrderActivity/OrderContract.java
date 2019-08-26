package com.leothon.cogito.Mvp.View.Activity.OrderActivity;

import com.leothon.cogito.Bean.OrderHis;

import java.util.ArrayList;

public class OrderContract {

    public interface IOrderModel{


        void getOrders(String token,OnOrderFinishedListener listener);
        void getMoreOrders(String token,int currentPage,OnOrderFinishedListener listener);

    }

    public interface IOrderView{


        void loadOrders(ArrayList<OrderHis> orderHis);
        void loadMoreOrders(ArrayList<OrderHis> orderHis);
        void showMsg(String msg);


    }

    public interface OnOrderFinishedListener {


        void loadOrders(ArrayList<OrderHis> orderHis);
        void loadMoreOrders(ArrayList<OrderHis> orderHis);
        void showMsg(String msg);
    }

    public interface IOrderPresenter{
        void getOrders(String token);
        void getMoreOrders(String token,int currentPage);
        void onDestroy();



    }
}
