package com.leothon.cogito.Mvp.View.Activity.HistoryActivity;

import android.util.Log;

import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class HistoryModel implements HistoryContract.IHisModel {
    @Override
    public void getViewClass(String token, final HistoryContract.OnViewFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getClassViewHis(token)
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
                        ArrayList<ClassDetailList> classDetailLists = (ArrayList<ClassDetailList>)baseResponse.getData();
                        listener.loadViewClass(classDetailLists);
                    }
                });
    }

    @Override
    public void removeViewClass(String classdId, String token, final HistoryContract.OnViewFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .removeClassViewHis(token,classdId)
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
                        if (baseResponse.isSuccess()){
                            listener.showMsg("删除该条观看记录");
                        }else {
                            listener.showMsg("删除失败");
                        }
                    }
                });
    }
}
