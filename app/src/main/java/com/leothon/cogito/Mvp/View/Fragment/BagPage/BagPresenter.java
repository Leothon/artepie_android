package com.leothon.cogito.Mvp.View.Fragment.BagPage;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.BagPageData;

import java.util.ArrayList;

public class BagPresenter implements BagContract.IBagPresenter,BagContract.OnBagFinishedListener {

    private BagContract.IBagView iBagView;
    private BagContract.IBagModel iBagModel;


    public BagPresenter(BagContract.IBagView iBagView){
        this.iBagView = iBagView;
        this.iBagModel = new BagModel();
    }
    @Override
    public void loadBagData(BagPageData bagPageData) {
        if (iBagView != null){
            iBagView.loadBagData(bagPageData);
        }
    }

    @Override
    public void loadFineClassMoreData(ArrayList<SelectClass> moreFineClass) {

        if (iBagView != null){
            iBagView.loadFineClassMoreData(moreFineClass);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iBagView != null){
            iBagView.showInfo(msg);
        }
    }

    @Override
    public void onDestroy() {
        iBagModel = null;
        iBagView = null;
    }

    @Override
    public void getBagData(String token) {
        iBagModel.getBagData(token,this);
    }

    @Override
    public void getMoreFineClassData(int currentPage, String token) {
        iBagModel.getMoreFineClassData(currentPage,token,this);
    }
}
