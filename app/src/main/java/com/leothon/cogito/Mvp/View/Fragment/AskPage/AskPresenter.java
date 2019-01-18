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
    public void onDestroy() {
        iAskView = null;
        iAskModel = null;
    }

    @Override
    public void getAskData(String token) {
        iAskModel.getAskData(token,this);
    }

    @Override
    public void getAskMoreData(int currentPage,String token) {
        iAskModel.getAskMoreData(currentPage,token,this);
    }

    @Override
    public void addLiked(String token, String qaId) {
        iAskModel.addLike(token,qaId,this);
    }

    @Override
    public void removeLiked(String token, String qaId) {

        iAskModel.removeLike(token,qaId,this);
    }
}
