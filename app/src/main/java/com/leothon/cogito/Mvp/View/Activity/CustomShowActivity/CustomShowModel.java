package com.leothon.cogito.Mvp.View.Activity.CustomShowActivity;

import com.leothon.cogito.Bean.CustomShow;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class CustomShowModel implements CustomShowContract.ICustomShowModel{

    @Override
    public void getCustomShow(CustomShowContract.OnCustomShowFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getCustomShow(0)
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
                        ArrayList<CustomShow> customShows = (ArrayList<CustomShow>)baseResponse.getData();
                        listener.loadCustomShow(customShows);
                    }
                });
    }

    @Override
    public void getMoreCustomShow(int currentPage, CustomShowContract.OnCustomShowFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getCustomShow(currentPage)
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
                        ArrayList<CustomShow> customShows = (ArrayList<CustomShow>)baseResponse.getData();
                        listener.loadMoreCustomShow(customShows);
                    }
                });
    }
}
