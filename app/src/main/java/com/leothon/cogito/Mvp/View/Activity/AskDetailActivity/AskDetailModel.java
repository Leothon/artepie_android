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
    public void postComment(String fromId, String toId, String content, AskDetailContract.OnAskDetailFinishedListener listener) {

    }

    @Override
    public void getCommentDetail(String commentId, final AskDetailContract.OnAskDetailFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getCommentDetail(commentId)
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
}
