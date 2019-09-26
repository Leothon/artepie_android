package com.leothon.cogito.Mvp.View.Activity.PayInfoActivity;

import android.util.Log;

import com.leothon.cogito.Bean.AlipayBean;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.Orders;
import com.leothon.cogito.DTO.VideoDetail;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import io.reactivex.disposables.Disposable;

public class PayModel implements PayContract.IPayModel {
    @Override
    public void getClassPayInfo(String token, String classId, final PayContract.OnPayFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getBuyClassInfo(classId)
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
                        SelectClass selectClass = (SelectClass)baseResponse.getData();
                        listener.getClassInfo(selectClass);
                    }
                });
    }

    @Override
    public void createTransactionInfo(Orders orders, PayContract.OnPayFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .createOrder(orders)
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
                        Orders orders = (Orders)baseResponse.getData();

                        listener.createOrderSuccess(orders);
                    }
                });
    }

    @Override
    public void getTransaction(Orders orders, PayContract.OnPayFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .createTransaction(orders)
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
                        Orders orders = (Orders)baseResponse.getData();

                        listener.getTransactionSuccess(orders);
                    }
                });
    }

    @Override
    public void verifyAlipayTransaction(AlipayBean alipayBean, PayContract.OnPayFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .verifyAlipayTransaction(alipayBean)
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


                        listener.verifyResult(baseResponse.getMsg());
                    }
                });
    }

    @Override
    public void checkAliPayOrder(String orderInfo, PayContract.OnPayFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .alipayEndNotify(orderInfo)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.endCheckFail(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {

                        listener.endCheckSuccess(baseResponse.getMsg());
                    }
                });
    }


}
