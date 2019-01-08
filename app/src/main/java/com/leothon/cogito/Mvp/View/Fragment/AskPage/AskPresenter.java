package com.leothon.cogito.Mvp.View.Fragment.AskPage;

import com.leothon.cogito.DTO.QAData;

import java.util.ArrayList;

public class AskPresenter implements AskFragmentContract.IAskPresenter,AskFragmentContract.OnAskFinishedListener {

    private AskFragmentContract.IAskView iAskView;
    private AskFragmentContract.IAskModel iAskModel;


    public AskPresenter(AskFragmentContract.IAskView iAskView){
        this.iAskView = iAskView;
        this.iAskModel = new AskModel();
    }
    @Override
    public void loadAskData(ArrayList<QAData> qaData) {
        if (iAskView != null){
            iAskView.loadAskData(qaData);
        }
    }

    @Override
    public void loadAskMoreData(ArrayList<QAData> qaData) {
        if (iAskView != null){
            iAskView.loadAskMoreData(qaData);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iAskView != null){
            iAskView.showInfo(msg);
        }
    }

    @Override
    public void onDestory() {
        iAskView = null;
    }

    @Override
    public void getAskData(String token) {
        iAskModel.getAskData(token,this);
    }

    @Override
    public void getAskMoreData(int currentPage) {
        iAskModel.getAskMoreData(currentPage,this);
    }
}
