package com.leothon.cogito.Mvp.View.Activity.PlayerActivity;

import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.DTO.VideoDetail;

import java.util.ArrayList;

public class PlayerPresenter implements PlayerContract.IPlayerPresenter,PlayerContract.OnPlayerFinishedListener {
    private PlayerContract.IPlayerView iPlayerView;
    private PlayerContract.IPlayerModel iPlayerModel;

    public PlayerPresenter(PlayerContract.IPlayerView iPlayerView){
        this.iPlayerView = iPlayerView;
        this.iPlayerModel = new PlayerModel();
    }
    @Override
    public void getVideoDetailInfo(VideoDetail videoDetail) {
        if (iPlayerView != null){
            iPlayerView.getVideoDetailInfo(videoDetail);
        }
    }

    @Override
    public void getMoreComment(ArrayList<Comment> comments) {
        if (iPlayerView != null){
            iPlayerView.getMoreComment(comments);
        }
    }


    @Override
    public void showInfo(String msg) {
        if (iPlayerView != null){
            iPlayerView.showInfo(msg);
        }
    }

    @Override
    public void getVideoDetail(String token, String classdId, String classId) {
        iPlayerModel.loadVideoDetail(token,classdId,classId,this);
    }

    @Override
    public void loadMoreComment(String token, String classdId, int currentPage) {
        iPlayerModel.loadMoreComment(token,classdId,currentPage,this);
    }


    @Override
    public void onDestroy() {
        iPlayerModel = null;
        iPlayerView = null;
    }

    @Override
    public void addLikeComment(String token, String commentId) {
        iPlayerModel.addLikeComment(token,commentId,this);
    }

    @Override
    public void removeLikeComment(String token, String commentId) {
        iPlayerModel.removeLikeComment(token,commentId,this);
    }

    @Override
    public void addLikeReply(String token, String replyId) {
        iPlayerModel.addLikeReply(token,replyId,this);
    }

    @Override
    public void removeLikeReply(String token, String replyId) {

        iPlayerModel.removeLikeReply(token,replyId,this);
    }

    @Override
    public void sendQaComment(String qaId, String token, String content) {
        iPlayerModel.postQaComment(qaId,token,content,this);
    }

    @Override
    public void sendReply(String commentId, String token, String toUserId, String content) {
        iPlayerModel.postReply(commentId,token,toUserId,content,this);
    }

    @Override
    public void deleteQaComment(String commentId, String token) {
        iPlayerModel.deleteQaComment(commentId,token,this);
    }

    @Override
    public void deleteReply(String replyId, String token) {
        iPlayerModel.deleteReply(replyId,token,this);
    }

    @Override
    public void addVideoView(String token, String classId, String classdId) {
        iPlayerModel.insertVideoView(token,classId,classdId,this);
    }
}
