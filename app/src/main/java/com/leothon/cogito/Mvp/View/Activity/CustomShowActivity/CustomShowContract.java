package com.leothon.cogito.Mvp.View.Activity.CustomShowActivity;

import com.leothon.cogito.Bean.CustomShow;

import java.util.ArrayList;

public class CustomShowContract {
    public interface ICustomShowModel{
        void getCustomShow(OnCustomShowFinishedListener listener);
        void getMoreCustomShow(int currentPage,OnCustomShowFinishedListener listener);
    }

    public interface ICustomShowView{


        void loadCustomShow(ArrayList<CustomShow> customShows);
        void loadMoreCustomShow(ArrayList<CustomShow> customShows);

        void showMsg(String msg);


    }

    public interface OnCustomShowFinishedListener {

        void loadCustomShow(ArrayList<CustomShow> customShows);
        void loadMoreCustomShow(ArrayList<CustomShow> customShows);

        void showMsg(String msg);


    }

    public interface ICustomShowPresenter{

        void getCustomShow();
        void getMoreCustomShow(int currentPage);
        void onDestroy();

    }
}
