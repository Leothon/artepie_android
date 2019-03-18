package com.leothon.cogito.Mvp.View.Activity.QAHisActivity;

import com.leothon.cogito.DTO.QAData;

import java.util.ArrayList;

public class QAHisPresenter implements QAHisContract.IQAHisPresenter,QAHisContract.OnQAHisFinishedListener {
    private QAHisContract.IQAHisView iQAHisView;
    private QAHisContract.IQAHisModel iQAHisModel;


    public QAHisPresenter(QAHisContract.IQAHisView iQAHisView){
        this.iQAHisView = iQAHisView;
        this.iQAHisModel = new QAHisModel();
    }
    @Override
    public void loadAskData(ArrayList<QAData> qaData) {
        if (iQAHisView != null){
            iQAHisView.loadAskData(qaData);
        }
    }

    @Override
    public void loadAskMoreData(ArrayList<QAData> qaData) {
        if (iQAHisView != null){
            iQAHisView.loadAskMoreData(qaData);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iQAHisView != null){
            iQAHisView.showInfo(msg);
        }
    }

    @Override
    public void deleteSuccess(String msg) {
        if (iQAHisView != null){
            iQAHisView.deleteSuccess(msg);
        }
    }

    @Override
    public void onDestroy() {
        iQAHisView = null;
        iQAHisModel = null;
    }

    @Override
    public void getAskData(String userId) {
        iQAHisModel.getAskData(userId,this);
    }

    @Override
    public void getAskMoreData(int currentPage,String userId) {
        iQAHisModel.getAskMoreData(currentPage,userId,this);
    }

    @Override
    public void addLiked(String token, String qaId) {
        iQAHisModel.addLike(token,qaId,this);
    }

    @Override
    public void removeLiked(String token, String qaId) {

        iQAHisModel.removeLike(token,qaId,this);
    }

    @Override
    public void deleteQa(String token, String qaId) {
        iQAHisModel.deleteQa(token,qaId,this);
    }
}
