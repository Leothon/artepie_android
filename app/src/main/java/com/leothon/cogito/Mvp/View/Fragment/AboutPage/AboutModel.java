package com.leothon.cogito.Mvp.View.Fragment.AboutPage;


import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import io.reactivex.disposables.Disposable;

public class AboutModel implements AboutFragmentContract.IAboutModel {
    @Override
    public void loadUserInfo(String token, final AboutFragmentContract.OnAboutFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getUserInfo(token)
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
                        User user = (User)baseResponse.getData();
                        listener.getUserInfo(user);
                    }
                });
    }
}
