package com.leothon.cogito.Mvp.View.Activity.TestActivity;


import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.TeaClass;
import com.leothon.cogito.DTO.TypeClass;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.net.URLEncoder;
import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class TestModel implements TestContract.ITestModel {
    @Override
    public void loadClassByType(String token, String type, final TestContract.OnTestFinishedListener listener) {

        String encodeType = "";
        try {
            encodeType = URLEncoder.encode(type,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getTypeClass(token,encodeType)
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
                        TypeClass typeClass = (TypeClass)baseResponse.getData();
                        listener.getTypeClass(typeClass);
                    }
                });
    }

    @Override
    public void loadMoreClassByType(String token, String type, int currentPage, TestContract.OnTestFinishedListener listener) {
        String encodeType = "";
        try {
            encodeType = URLEncoder.encode(type,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreTypeClass(token,encodeType,currentPage)
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
                        listener.getMoreTypeClass(selectClasses);
                    }
                });
    }
}
