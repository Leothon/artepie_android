package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import com.leothon.cogito.Bean.AskDetail;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.QADataDetail;

import java.util.ArrayList;

public class AskDetailPresenter implements AskDetailContract.IAskDetailPresenter,AskDetailContract.OnAskDetailFinishedListener {
    private AskDetailContract.IAskDetailView iAskDetailView;
    private AskDetailContract.IAskDetailModel iAskDetailModel;


    public AskDetailPresenter(AskDetailContract.IAskDetailView iAskDetailView){
        this.iAskDetailView = iAskDetailView;
        this.iAskDetailModel = new AskDetailModel();
    }
    @Override
    public void loadDetail(QADataDetail qaDetail) {
        if (iAskDetailView != null){
            iAskDetailView.loadDetail(qaDetail);
        }
    }

    @Override
    public void loadMoreComment(ArrayList<Comment> userComments) {
        if (iAskDetailView != null){
            iAskDetailView.loadMoreComment(userComments);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iAskDetailView != null){
            iAskDetailView.showInfo(msg);
        }
    }

    @Override
    public void getComment(CommentDetail commentDetail) {
        if (iAskDetailView != null){
            iAskDetailView.getComment(commentDetail);
        }
    }

    @Override
    public void onDestory() {
        iAskDetailView = null;
    }

    @Override
    public void getQADetailData(String token, String qaId) {
        iAskDetailModel.getQADetail(token,qaId,this);
    }

    @Override
    public void getMoreComment(String qaId, String currentPage) {
        iAskDetailModel.getMoreComment(qaId,currentPage,this);
    }

    @Override
    public void loadCommentDetail(String commentId) {
        iAskDetailModel.getCommentDetail(commentId,this);
    }
}
