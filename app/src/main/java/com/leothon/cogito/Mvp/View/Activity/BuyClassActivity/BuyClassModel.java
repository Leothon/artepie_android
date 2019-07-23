package com.leothon.cogito.Mvp.View.Activity.BuyClassActivity;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class BuyClassModel implements BuyClassContract.IBuyClassModel {
    @Override
    public void getBuyClass(String token, BuyClassContract.OnBuyClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getBuyClass(token)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showMsg(errorMsg);

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
                        listener.loadBuyClass(selectClasses);
                    }
                });
    }
}
