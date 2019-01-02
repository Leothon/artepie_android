package com.leothon.cogito.Mvp.View.Fragment.HomePage;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.HomeData;

import java.util.ArrayList;

public class HomePresenter implements HomeFragmentContract.IHomePresenter,HomeFragmentContract.OnHomeFinishedListener {

    private HomeFragmentContract.IHomeView iHomeView;
    private HomeFragmentContract.IHomeModel iHomeModel;


    public HomePresenter(HomeFragmentContract.IHomeView iHomeView){
        this.iHomeView = iHomeView;
        this.iHomeModel = new HomeModel();
    }
    @Override
    public void loadData(HomeData homeData) {
        if (iHomeView != null){
            iHomeView.loadData(homeData);
        }
    }

    @Override
    public void loadMoreData(ArrayList<SelectClass> selectClassS) {
        if (iHomeView != null){
            iHomeView.loadMoreData(selectClassS);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iHomeView != null){
            iHomeView.showInfo(msg);
        }
    }


    @Override
    public void onDestory() {
        iHomeView = null;
    }





    @Override
    public void loadHomeData(String token) {
        iHomeModel.getHomeData(token,this);
    }

    @Override
    public void swipeHomeData(String token) {
        iHomeModel.getHomeData(token,this);
    }

    @Override
    public void loadMoreClassData(String token) {
        iHomeModel.getMoreData(token,this);
    }
}
