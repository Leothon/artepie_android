package com.leothon.cogito.Mvp.View.Activity.UploadActivity;

import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Mvp.View.Fragment.AskPage.AskFragmentContract;
import com.leothon.cogito.Mvp.View.Fragment.AskPage.AskModel;

import java.util.ArrayList;

public class UploadPresenter implements UploadContract.IUploadPresenter,UploadContract.OnUploadFinishedListener {
    private UploadContract.IUploadView iUploadView;
    private UploadContract.IUploadModel iUploadModel;


    public UploadPresenter(UploadContract.IUploadView iUploadView){
        this.iUploadView = iUploadView;
        this.iUploadModel = new UploadModel();
    }
    @Override
    public void loadAskData(ArrayList<QAData> qaData) {
        if (iUploadView != null){
            iUploadView.loadAskData(qaData);
        }
    }

    @Override
    public void loadAskMoreData(ArrayList<QAData> qaData) {
        if (iUploadView != null){
            iUploadView.loadAskMoreData(qaData);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iUploadView != null){
            iUploadView.showInfo(msg);
        }
    }

    @Override
    public void onDestroy() {
        iUploadView = null;
        iUploadModel = null;
    }

    @Override
    public void getAskData(String token) {
        iUploadModel.getAskData(token,this);
    }

    @Override
    public void getAskMoreData(int currentPage,String token) {
        iUploadModel.getAskMoreData(currentPage,token,this);
    }

    @Override
    public void addLiked(String token, String qaId) {
        iUploadModel.addLike(token,qaId,this);
    }

    @Override
    public void removeLiked(String token, String qaId) {

        iUploadModel.removeLike(token,qaId,this);
    }
}
