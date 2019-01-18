package com.leothon.cogito.Mvp.View.Fragment.HomePage;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.HomeData;

import java.util.ArrayList;

public class HomeFragmentContract {
    public interface IHomeModel{
       //TODO 执行相关数据操作
        void getHomeData(String token,OnHomeFinishedListener listener);

        void getMoreData(int currentPage,String token,OnHomeFinishedListener listener);
    }

    public interface IHomeView{
        //TODO　执行前端操作

        void loadData(HomeData homeData);

        void loadMoreData(ArrayList<SelectClass> selectClassS);

        void showInfo(String msg);
    }

    public interface OnHomeFinishedListener {
        void loadData(HomeData homeData);

        void loadMoreData(ArrayList<SelectClass> selectClassS);
        void showInfo(String msg);

    }

    public interface IHomePresenter{
        void onDestroy();
        void loadHomeData(String token);
        void swipeHomeData(String token);
        void loadMoreClassData(int currentPage,String token);
    }
}
