package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import com.leothon.cogito.Bean.AskDetail;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.Bean.Reply;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.QADataDetail;

import java.util.ArrayList;

public class AskDetailContract {
    public interface IAskDetailModel{

        void getQADetail(String token,String qaId,OnAskDetailFinishedListener listener);
        void getMoreComment(String token,String qaId,int currentPage,OnAskDetailFinishedListener listener);
        //TODO 发送评论

        void getCommentDetail(String commentId,String token,OnAskDetailFinishedListener listener);
        void getMoreCommentDetail(String commentId,String token,int currentPage,OnAskDetailFinishedListener listener);

        void addLikeDetail(String token,String qaId,OnAskDetailFinishedListener listener);
        void removeLikeDetail(String token,String qaId,OnAskDetailFinishedListener listener);

        void addLikeComment(String token,String commentId,OnAskDetailFinishedListener listener);
        void removeLikeComment(String token,String commentId,OnAskDetailFinishedListener listener);

        void addLikeReply(String token,String replyId,OnAskDetailFinishedListener listener);
        void removeLikeReply(String token,String replyId,OnAskDetailFinishedListener listener);

        void postQaComment(String qaId,String token,String content,OnAskDetailFinishedListener listener);
        void postReply(String commentId,String token,String toUserId,String content,OnAskDetailFinishedListener listener);

        void deleteQaComment(String commentId,String token,OnAskDetailFinishedListener listener);
        void deleteReply(String replyId,String token,OnAskDetailFinishedListener listener);

        void deleteQa(String token,String qaId,OnAskDetailFinishedListener listener);
    }

    public interface IAskDetailView{


        void loadError(String msg);

        void loadDetail(QADataDetail qaDetail);
        void loadMoreComment(ArrayList<Comment> userComments);
        void showInfo(String msg);

        void getComment(CommentDetail commentDetail);
        void getMoreComment(ArrayList<Reply> replies);
        void deleteSuccess(String msg);
    }

    public interface OnAskDetailFinishedListener {

        void loadDetail(QADataDetail qaDetail);
        void loadMoreComment(ArrayList<Comment> userComments);
        void showInfo(String msg);
        void loadError(String msg);
        void getComment(CommentDetail commentDetail);
        void getMoreComment(ArrayList<Reply> replies);
        void deleteSuccess(String msg);
    }

    public interface IAskDetailPresenter{
        void onDestroy();
        void getQADetailData(String token,String qaId);
        void getMoreComment(String token,String qaId,int currentPage);
        void loadCommentDetail(String commentId,String token);

        void loadMoreCommentDetail(String commentId,String token,int currentPage);

        void addLikeDetail(String token,String qaId);
        void removeLikeDetail(String token,String qaId);
        void addLikeComment(String token,String commentId);
        void removeLikeComment(String token,String commentId);
        void addLikeReply(String token,String replyId);
        void removeLikeReply(String token,String replyId);


        void sendQaComment(String qaId,String token,String content);
        void sendReply(String commentId,String token,String toUserId,String content);

        void deleteQaComment(String commentId,String token);
        void deleteReply(String replyId,String token);
        void deleteQa(String token,String qaId);
    }
}
