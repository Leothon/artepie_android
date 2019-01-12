package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import com.leothon.cogito.Bean.AskDetail;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.DTO.QADataDetail;

import java.util.ArrayList;

public class AskDetailContract {
    public interface IAskDetailModel{

        void getQADetail(String token,String qaId,OnAskDetailFinishedListener listener);
        void getMoreComment(String qaId,String currentPage,OnAskDetailFinishedListener listener);
        //TODO 发送评论
        void postComment(String fromId,String toId,String content,OnAskDetailFinishedListener listener);
    }

    public interface IAskDetailView{



        void loadDetail(QADataDetail qaDetail);
        void loadMoreComment(ArrayList<Comment> userComments);
        void showInfo(String msg);

    }

    public interface OnAskDetailFinishedListener {

        void loadDetail(QADataDetail qaDetail);
        void loadMoreComment(ArrayList<Comment> userComments);
        void showInfo(String msg);

    }

    public interface IAskDetailPresenter{
        void onDestory();
        void getQADetailData(String token,String qaId);
        void getMoreComment(String qaId,String currentPage);

    }
}
