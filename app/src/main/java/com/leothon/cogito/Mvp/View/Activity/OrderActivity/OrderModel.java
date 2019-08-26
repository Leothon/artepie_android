package com.leothon.cogito.Mvp.View.Activity.OrderActivity;

import android.widget.Toast;

import com.leothon.cogito.Bean.OrderHis;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.View.MyToast;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class OrderModel implements OrderContract.IOrderModel {

    @Override
    public void getOrders(String token, OrderContract.OnOrderFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getOrder(token)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showMsg("出错,请重试");

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        ArrayList<OrderHis> orderHis = (ArrayList<OrderHis>)baseResponse.getData();
                        listener.loadOrders(orderHis);
                    }
                });
    }

    @Override
    public void getMoreOrders(String token, int currentPage, OrderContract.OnOrderFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreOrder(token,currentPage)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showMsg("出错,请重试");

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        ArrayList<OrderHis> orderHis = (ArrayList<OrderHis>)baseResponse.getData();
                        listener.loadMoreOrders(orderHis);
                    }
                });
    }
}
