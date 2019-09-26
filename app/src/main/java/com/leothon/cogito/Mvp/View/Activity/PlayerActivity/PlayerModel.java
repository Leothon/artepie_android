package com.leothon.cogito.Mvp.View.Activity.PlayerActivity;

import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.DTO.VideoDetail;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailContract;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class PlayerModel implements PlayerContract.IPlayerModel {
    @Override
    public void loadVideoDetail(String token, String classdId, String classId, final PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getClassVideo(token,classdId,classId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        VideoDetail videoDetail = (VideoDetail)baseResponse.getData();
                        listener.getVideoDetailInfo(videoDetail);
                    }
                });
    }

    @Override
    public void loadMoreComment(String token, String classdId, int currentPage, PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreClassVideo(token,classdId,currentPage)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        ArrayList<Comment> comments = (ArrayList<Comment>)baseResponse.getData();
                        listener.getMoreComment(comments);
                    }
                });
    }


    @Override
    public void addLikeComment(String token, String commentId, final PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .addLikeComment(token,commentId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.showInfo("已点赞");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void removeLikeComment(String token, String commentId, final PlayerContract.OnPlayerFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .removeLikeComment(token,commentId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.showInfo("已取消");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void addLikeReply(String token, String replyId, final PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .addLikeReply(token,replyId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.showInfo("已点赞");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void removeLikeReply(String token, String replyId, final PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .removeLikeReply(token,replyId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.showInfo("已取消");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void postQaComment(String qaId, String token, String content, final PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .sendQaComment(qaId,token,content)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.showInfo("评论成功");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void postReply(String commentId, String token, String toUserId, String content,final PlayerContract.OnPlayerFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .sendReply(commentId,token,toUserId,content)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.showInfo("回复成功");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void deleteQaComment(String commentId, String token,final PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .deleteQaComment(commentId,token)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.showInfo("评论删除成功");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void deleteReply(String replyId, String token, final PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .deleteReply(replyId,token)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.showInfo("回复删除成功");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void insertVideoView(String token, String classId, String classdId, final PlayerContract.OnPlayerFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .addVideoView(token,classId,classdId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {

                    }
                });
    }
}
