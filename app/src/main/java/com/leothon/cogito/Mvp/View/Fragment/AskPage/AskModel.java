package com.leothon.cogito.Mvp.View.Fragment.AskPage;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class AskModel implements AskFragmentContract.IAskModel {
    @Override
    public void getAskData(String token, final AskFragmentContract.OnAskFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getQAData(token)
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
    public void getAskMoreData(int currentPage, final AskFragmentContract.OnAskFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreQAData(currentPage)
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
}