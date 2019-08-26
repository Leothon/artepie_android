package com.leothon.cogito.Mvp.View.Activity.FavActivity;

import com.leothon.cogito.Bean.SelectClass;

import java.util.ArrayList;

public class FavPresenter implements FavContract.IFavPresenter,FavContract.OnFavFinishedListener {

    private FavContract.IFavView iFavView;
    private FavContract.IFavModel iFavModel;

    public FavPresenter(FavContract.IFavView iFavView){
        this.iFavView = iFavView;
        this.iFavModel = new FavModel();
    }
    @Override
    public void loadFavClass(ArrayList<SelectClass> selectClasses) {
        if (iFavView != null){
            iFavView.loadFavClass(selectClasses);
        }
    }

    @Override
    public void loadMoreFavClass(ArrayList<SelectClass> selectClasses) {
        if (iFavView != null){
            iFavView.loadMoreFavClass(selectClasses);
        }
    }

    @Override
    public void showMsg(String msg) {

        if (iFavView != null){
            iFavView.showMsg(msg);
        }
    }

    @Override
    public void getFavClass(String token) {
        iFavModel.getFavClass(token,this);
    }

    @Override
    public void getMoreFavClass(String token, int currentPage) {
        iFavModel.getMoreFavClass(token,currentPage,this);
    }

    @Override
    public void removeFavClass(String classId, String token) {
        iFavModel.removeFavClass(classId,token,this);
    }

    @Override
    public void onDestroy() {
        iFavModel = null;
        iFavView = null;
    }
}
