package com.leothon.cogito.Mvp.View.Activity.WalletActivity;

import com.leothon.cogito.Bean.Bill;
import com.leothon.cogito.Bean.User;

import java.util.ArrayList;

public class WalletContract {




    public interface IWalletModel{

        void getUserInfo(String token,OnWalletFinishedListener listener);
        void getBills(String token,OnWalletFinishedListener listener);
        void getMoreBills(String token,int currentPage,OnWalletFinishedListener listener);

    }

    public interface IWalletView{

        void loadUserInfo(User user);
        void loadBills(ArrayList<Bill> bills);
        void loadMoreBills(ArrayList<Bill> bills);
        void showMsg(String msg);


    }

    public interface OnWalletFinishedListener {

        void loadUserInfo(User user);
        void loadBills(ArrayList<Bill> bills);
        void loadMoreBills(ArrayList<Bill> bills);
        void showMsg(String msg);
    }

    public interface IWalletPresenter{
        void getUserInfo(String token);
        void getBills(String token);
        void getMoreBills(String token,int currentPage);
        void onDestroy();



    }
}
