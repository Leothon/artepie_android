package com.leothon.cogito.Mvp.View.Activity.CustomShowActivity;

import com.leothon.cogito.Bean.CustomShow;

import java.util.ArrayList;

public class CustomShowPresenter implements CustomShowContract.ICustomShowPresenter, CustomShowContract.OnCustomShowFinishedListener {

    private CustomShowContract.ICustomShowView iCustomShowView;
    private CustomShowContract.ICustomShowModel iCustomShowModel;

    public CustomShowPresenter(CustomShowContract.ICustomShowView iCustomShowView){
        this.iCustomShowView = iCustomShowView;
        this.iCustomShowModel = new CustomShowModel();
    }

    @Override
    public void loadCustomShow(ArrayList<CustomShow> customShows) {
        if (iCustomShowView != null){
            iCustomShowView.loadCustomShow(customShows);
        }
    }

    @Override
    public void loadMoreCustomShow(ArrayList<CustomShow> customShows) {
        if (iCustomShowView != null){
            iCustomShowView.loadMoreCustomShow(customShows);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iCustomShowView != null){
            iCustomShowView.showMsg(msg);
        }
    }

    @Override
    public void getCustomShow() {
        iCustomShowModel.getCustomShow(this);
    }

    @Override
    public void getMoreCustomShow(int currentPage) {
        iCustomShowModel.getMoreCustomShow(currentPage,this);
    }

    @Override
    public void onDestroy() {
        iCustomShowView = null;
        iCustomShowModel = null;
    }
}
