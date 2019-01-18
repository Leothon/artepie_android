package com.leothon.cogito.Mvp.View.Fragment.HomePage;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.verify;
import com.leothon.cogito.DTO.HomeData;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class HomeModel implements HomeFragmentContract.IHomeModel {


    @Override
    public void getHomeData(String token,final HomeFragmentContract.OnHomeFinishedListener listener) {
        //获取首页内容
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getHomeData(token)
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
                        HomeData homeData = (HomeData)baseResponse.getData();
                        listener.loadData(homeData);
                    }
                });
    }

    @Override
    public void getMoreData(int currentPage,String token,final HomeFragmentContract.OnHomeFinishedListener listener) {
        //获取更多内容

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
                        ArrayList<SelectClass> selectClasses = (ArrayList<SelectClass>) baseResponse.getData();
                        listener.loadMoreData(selectClasses);
                    }
                });

    }
}
