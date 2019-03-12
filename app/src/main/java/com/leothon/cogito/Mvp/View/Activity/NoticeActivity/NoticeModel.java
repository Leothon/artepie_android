package com.leothon.cogito.Mvp.View.Activity.NoticeActivity;

import com.leothon.cogito.Bean.NoticeInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class NoticeModel implements NoticeContract.INoticeModel {
    @Override
    public void getNoticeInfo(String token, final NoticeContract.OnNoticeFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getNoticeInfo(token)
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
                        ArrayList<NoticeInfo> noticeInfos = (ArrayList<NoticeInfo>)baseResponse.getData();
                        listener.loadNoticeInfo(noticeInfos);
                    }
                });
    }

    @Override
    public void setNoticeVisible(String token, String noticeId, final NoticeContract.OnNoticeFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .visibleNotice(token,noticeId)
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
                        String info = baseResponse.getMsg();
                        listener.setNoticeVisibleSuccess(info);
                    }
                });
    }

    @Override
    public void setAllNoticeVisible(String token, final NoticeContract.OnNoticeFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .visibleNoticeAll(token)
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
                        String info = baseResponse.getMsg();
                        listener.setNoticeAllVisibleSuccess(info);
                    }
                });
    }

}
