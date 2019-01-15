package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import com.leothon.cogito.Bean.AskDetail;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.QADataDetail;

import java.util.ArrayList;

public class AskDetailContract {
    public interface IAskDetailModel{

        void getQADetail(String token,String qaId,OnAskDetailFinishedListener listener);
        void getMoreComment(String qaId,String currentPage,OnAskDetailFinishedListener listener);
        //TODO 发送评论
        void postComment(String fromId,String toId,String content,OnAskDetailFinishedListener listener);
        void getCommentDetail(String commentId,OnAskDetailFinishedListener listener);

        void addLikeDetail(String token,String qaId,OnAskDetailFinishedListener listener);
        void removeLikeDetail(String token,String qaId,OnAskDetailFinishedListener listener);

        void addLikeComment(String token,String commentId,OnAskDetailFinishedListener listener);
        void removeLikeComment(String token,String commentId,OnAskDetailFinishedListener listener);

        void addLikeReply(String token,String replyId,OnAskDetailFinishedListener listener);
        void removeLikeReply(String token,String replyId,OnAskDetailFinishedListener listener);
    }

    public interface IAskDetailView{



        void loadDetail(QADataDetail qaDetail);
        void loadMoreComment(ArrayList<Comment> userComments);
        void showInfo(String msg);

        void getComment(CommentDetail commentDetail);

    }

    public interface OnAskDetailFinishedListener {

        void loadDetail(QADataDetail qaDetail);
        void loadMoreComment(ArrayList<Comment> userComments);
        void showInfo(String msg);

        void getComment(CommentDetail commentDetail);

    }

    public interface IAskDetailPresenter{
        void onDestory();
        void getQADetailData(String token,String qaId);
        void getMoreComment(String qaId,String currentPage);
        void loadCommentDetail(String commentId);
        void addLikeDetail(String token,String qaId);
        void removeLikeDetail(String token,String qaId);
        void addLikeComment(String token,String commentId);
        void removeLikeComment(String token,String commentId);
        void addLikeReply(String token,String replyId);
        void removeLikeReply(String token,String replyId);
    }
}
