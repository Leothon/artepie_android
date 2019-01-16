package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import io.reactivex.disposables.Disposable;

public class AskDetailModel implements AskDetailContract.IAskDetailModel {
    @Override
    public void getQADetail(String token, String qaId, final AskDetailContract.OnAskDetailFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getQADetail(token,qaId)
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
                        QADataDetail qaDataDetail = (QADataDetail)baseResponse.getData();

                        listener.loadDetail(qaDataDetail);
                    }
                });
    }

    @Override
    public void getMoreComment(String qaId, String currentPage, AskDetailContract.OnAskDetailFinishedListener listener) {

    }


    @Override
    public void getCommentDetail(String commentId,String token, final AskDetailContract.OnAskDetailFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getCommentDetail(commentId,token)
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
                        CommentDetail commentDetail = (CommentDetail)baseResponse.getData();

                        listener.getComment(commentDetail);
                    }
                });
    }

    @Override
    public void addLikeDetail(String token, String qaId, final AskDetailContract.OnAskDetailFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .addLikeQa(token,qaId)
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
    public void removeLikeDetail(String token, String qaId, final AskDetailContract.OnAskDetailFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .removeLikeQa(token,qaId)
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
    public void addLikeComment(String token, String commentId, final AskDetailContract.OnAskDetailFinishedListener listener) {
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
    public void removeLikeComment(String token, String commentId, final AskDetailContract.OnAskDetailFinishedListener listener) {

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
    public void addLikeReply(String token, String replyId, final AskDetailContract.OnAskDetailFinishedListener listener) {
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
    public void removeLikeReply(String token, String replyId, final AskDetailContract.OnAskDetailFinishedListener listener) {
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
    public void postQaComment(String qaId, String token, String content, final AskDetailContract.OnAskDetailFinishedListener listener) {
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
    public void postReply(String commentId, String token, String toUserId, String content,final AskDetailContract.OnAskDetailFinishedListener listener) {

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
    public void deleteQaComment(String commentId, String token,final AskDetailContract.OnAskDetailFinishedListener listener) {
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
    public void deleteReply(String replyId, String token, final AskDetailContract.OnAskDetailFinishedListener listener) {
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
}
