package com.leothon.cogito.Mvp.View.Fragment.BagPage;

import android.util.Log;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.BagPageData;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class BagModel implements BagContract.IBagModel {
    @Override
    public void getBagData(String token, final BagContract.OnBagFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getBagPageData(token)
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
                        BagPageData bagPageData = (BagPageData)baseResponse.getData();
                        listener.loadBagData(bagPageData);
                    }
                });
    }

    @Override
    public void getMoreFineClassData(int currentPage, String token, final BagContract.OnBagFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreData(currentPage,token)
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
                        ArrayList<SelectClass> selectClasses = (ArrayList<SelectClass>)baseResponse.getData();
                        listener.loadFineClassMoreData(selectClasses);
                    }
                });
    }
}
