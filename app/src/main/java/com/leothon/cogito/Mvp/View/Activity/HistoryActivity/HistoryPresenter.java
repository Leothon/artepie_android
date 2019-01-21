package com.leothon.cogito.Mvp.View.Activity.HistoryActivity;

import com.leothon.cogito.Bean.ClassDetailList;

import java.util.ArrayList;

public class HistoryPresenter implements HistoryContract.IHisPresenter,HistoryContract.OnViewFinishedListener {

    private HistoryContract.IHisView iHisView;
    private HistoryContract.IHisModel iHisModel;

    public HistoryPresenter(HistoryContract.IHisView iHisView){
        this.iHisView = iHisView;
        this.iHisModel = new HistoryModel();
    }
    @Override
    public void loadViewClass(ArrayList<ClassDetailList> classDetailLists) {
        if (iHisView != null){
            iHisView.loadViewClass(classDetailLists);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iHisView != null){
            iHisView.showMsg(msg);
        }

    }

    @Override
    public void getViewClass(String token) {
        iHisModel.getViewClass(token,this);
    }

    @Override
    public void removeViewClass(String classdId, String token) {
        iHisModel.removeViewClass(classdId,token,this);
    }

    @Override
    public void onDestroy() {
        iHisModel = null;
        iHisView = null;
    }
}
