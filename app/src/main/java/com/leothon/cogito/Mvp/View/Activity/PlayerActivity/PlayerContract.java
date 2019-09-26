package com.leothon.cogito.Mvp.View.Activity.PlayerActivity;


import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.DTO.VideoDetail;

import java.util.ArrayList;

public class PlayerContract {

    public interface IPlayerModel{

        void loadVideoDetail(String token,String classdId,String classId,OnPlayerFinishedListener listener);


        void loadMoreComment(String token,String classdId,int currentPage,OnPlayerFinishedListener listener);
        void addLikeComment(String token,String commentId,OnPlayerFinishedListener listener);
        void removeLikeComment(String token,String commentId,OnPlayerFinishedListener listener);

        void addLikeReply(String token,String replyId,OnPlayerFinishedListener listener);
        void removeLikeReply(String token,String replyId,OnPlayerFinishedListener listener);

        void postQaComment(String qaId,String token,String content,OnPlayerFinishedListener listener);
        void postReply(String commentId,String token,String toUserId,String content,OnPlayerFinishedListener listener);

        void deleteQaComment(String commentId,String token,OnPlayerFinishedListener listener);
        void deleteReply(String replyId,String token,OnPlayerFinishedListener listener);

        void insertVideoView(String token,String classId,String classdId,OnPlayerFinishedListener listener);
    }

    public interface IPlayerView{


        void getVideoDetailInfo(VideoDetail videoDetail);
        void getMoreComment(ArrayList<Comment> comments);
        void showInfo(String msg);

    }

    public interface OnPlayerFinishedListener {

        void getVideoDetailInfo(VideoDetail videoDetail);
        void getMoreComment(ArrayList<Comment> comments);
        void showInfo(String msg);

    }

    public interface IPlayerPresenter{

        void getVideoDetail(String token,String classdId,String classId);
        void loadMoreComment(String token,String classdId,int currentPage);
        void onDestroy();

        void addLikeComment(String token,String commentId);
        void removeLikeComment(String token,String commentId);
        void addLikeReply(String token,String replyId);
        void removeLikeReply(String token,String replyId);


        void sendQaComment(String qaId,String token,String content);
        void sendReply(String commentId,String token,String toUserId,String content);

        void deleteQaComment(String commentId,String token);
        void deleteReply(String replyId,String token);

        void addVideoView(String token,String classId,String classdId);

    }
}
