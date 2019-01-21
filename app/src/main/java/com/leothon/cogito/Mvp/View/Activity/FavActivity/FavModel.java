package com.leothon.cogito.Mvp.View.Activity.FavActivity;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class FavModel implements FavContract.IFavModel {
    @Override
    public void getFavClass(String token, final FavContract.OnFavFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getFavClassByUid(token)
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
                        listener.loadFavClass(selectClasses);
                    }
                });
    }

    @Override
    public void removeFavClass(String classId, String token, final FavContract.OnFavFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .unFavClass(token,classId)
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
                            listener.showMsg("取消收藏成功");
                        }else {
                            listener.showMsg("取消失败，请重试");
                        }
                    }
                });
    }
}
