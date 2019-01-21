package com.leothon.cogito.Mvp.View.Activity.HistoryActivity;

import com.leothon.cogito.Bean.ClassDetailList;

import java.util.ArrayList;

public class HistoryContract {
    public interface IHisModel{

        void getViewClass(String token,OnViewFinishedListener listener);
        void removeViewClass(String classdId,String token,OnViewFinishedListener listener);
    }

    public interface IHisView{

        void loadViewClass(ArrayList<ClassDetailList> classDetailLists);
        void showMsg(String msg);


    }

    public interface OnViewFinishedListener {


        void loadViewClass(ArrayList<ClassDetailList> classDetailLists);
        void showMsg(String msg);


    }

    public interface IHisPresenter{

        void getViewClass(String token);
        void removeViewClass(String classdId,String token);
        void onDestroy();



    }
}
