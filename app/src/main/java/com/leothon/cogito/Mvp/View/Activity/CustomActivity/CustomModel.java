package com.leothon.cogito.Mvp.View.Activity.CustomActivity;

import com.leothon.cogito.Bean.CustomShow;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class CustomModel implements CustomContract.ICustomModel {

    @Override
    public void uploadInfo(String token, String info, CustomContract.OnCustomFinishedListener listener) {


        RetrofitServiceManager.getInstance().create(HttpService.class)
                .uploadCustomInfo(token,info)
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

                        listener.uploadSuccess(baseResponse.getMsg());
                    }
                });
    }
}
