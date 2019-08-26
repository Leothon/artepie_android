package com.leothon.cogito.Mvp.View.Activity.BuyClassActivity;

import com.leothon.cogito.Bean.SelectClass;

import java.util.ArrayList;

public class BuyClassContract {
    public interface IBuyClassModel{

        void getBuyClass(String token,OnBuyClassFinishedListener listener);
        void getMoreBuyClass(String token,int currentPage,OnBuyClassFinishedListener listener);

    }

    public interface IBuyClassView{

        void loadBuyClass(ArrayList<SelectClass> selectClasses);
        void loadMoreBuyClass(ArrayList<SelectClass> selectClasses);
        void showMsg(String msg);


    }

    public interface OnBuyClassFinishedListener {


        void loadBuyClass(ArrayList<SelectClass> selectClasses);
        void loadMoreBuyClass(ArrayList<SelectClass> selectClasses);
        void showMsg(String msg);


    }

    public interface IBuyClassPresenter{

        void getBuyClass(String token);
        void getMoreBuyClass(String token,int currentPage);
        void onDestroy();



    }
}
