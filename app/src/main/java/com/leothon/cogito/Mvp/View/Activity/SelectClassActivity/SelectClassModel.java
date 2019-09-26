package com.leothon.cogito.Mvp.View.Activity.SelectClassActivity;

import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class SelectClassModel implements SelectClassContract.ISelectClassModel {

    @Override
    public void loadClassDetail(String token, String classId, final SelectClassContract.OnSelectClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getClassDetail(token,classId)
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
                        ClassDetail classDetail = (ClassDetail) baseResponse.getData();
                        listener.getClassDetailInfo(classDetail);
                    }
                });
    }

    @Override
    public void loadMoreClassDetail(String token, String classId, int currentPage, SelectClassContract.OnSelectClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreClassDetail(token,classId,currentPage)
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
                        ArrayList<ClassDetailList> classDetailLists = (ArrayList<ClassDetailList>) baseResponse.getData();
                        listener.getMoreClassDetailInfo(classDetailLists);
                    }
                });
    }


    @Override
    public void favClass(String token, String classId, final SelectClassContract.OnSelectClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .favClass(token,classId)
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
                            listener.showInfo("收藏成功");
                        }else {
                            listener.showInfo("收藏失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void unFavClass(String token, String classId, final SelectClassContract.OnSelectClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .unFavClass(token,classId)
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
                            listener.showInfo("取消收藏成功");
                        }else {
                            listener.showInfo("取消失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void deleteClassDetail(String classdId, final SelectClassContract.OnSelectClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .deleteClassDetail(classdId)
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
                        String msg = baseResponse.getMsg();
                        listener.deleteSuccess(msg);
                    }
                });
    }
}
