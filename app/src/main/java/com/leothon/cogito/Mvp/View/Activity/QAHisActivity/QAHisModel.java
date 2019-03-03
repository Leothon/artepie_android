package com.leothon.cogito.Mvp.View.Activity.QAHisActivity;

import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class QAHisModel implements QAHisContract.IQAHisModel {
    @Override
    public void getAskData(String userId,final QAHisContract.OnQAHisFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getQADataById(userId)
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
                        ArrayList<QAData> qaData = (ArrayList<QAData>) baseResponse.getData();
                        listener.loadAskData(qaData);
                    }
                });
    }

    @Override
    public void getAskMoreData(int currentPage, String userId,final QAHisContract.OnQAHisFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreQADataById(currentPage,userId)
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
                        ArrayList<QAData> qaData = (ArrayList<QAData>) baseResponse.getData();
                        listener.loadAskMoreData(qaData);
                    }
                });
    }

    @Override
    public void addLike(String token, String qaId, final QAHisContract.OnQAHisFinishedListener listener) {
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
    public void removeLike(String token, String qaId, final QAHisContract.OnQAHisFinishedListener listener) {
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
}
