package com.leothon.cogito.Mvp.View.Activity.FavActivity;

import com.leothon.cogito.Bean.SelectClass;

import java.util.ArrayList;

public class FavContract {
    public interface IFavModel{

        void getFavClass(String token,OnFavFinishedListener listener);
        void removeFavClass(String classId,String token,OnFavFinishedListener listener);
    }

    public interface IFavView{

        void loadFavClass(ArrayList<SelectClass> selectClasses);
        void showMsg(String msg);


    }

    public interface OnFavFinishedListener {


        void loadFavClass(ArrayList<SelectClass> selectClasses);
        void showMsg(String msg);


    }

    public interface IFavPresenter{

        void getFavClass(String token);
        void removeFavClass(String classId,String token);
        void onDestroy();



    }
}
