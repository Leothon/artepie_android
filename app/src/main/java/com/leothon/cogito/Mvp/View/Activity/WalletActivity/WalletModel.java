package com.leothon.cogito.Mvp.View.Activity.WalletActivity;

import android.widget.Toast;

import com.leothon.cogito.Bean.Bill;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.View.MyToast;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class WalletModel implements WalletContract.IWalletModel {
    @Override
    public void getUserInfo(String token, WalletContract.OnWalletFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getUserInfo(token)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showMsg("出错");
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

                        listener.loadUserInfo(user);
                    }
                });
    }

    @Override
    public void getBills(String token, WalletContract.OnWalletFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getBill(token)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showMsg("出错");

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        ArrayList<Bill> bills = (ArrayList<Bill>)baseResponse.getData();

                        listener.loadBills(bills);
                    }
                });
    }

    @Override
    public void getMoreBills(String token, int currentPage, WalletContract.OnWalletFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreBill(token,currentPage)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showMsg("出错");

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        ArrayList<Bill> bills = (ArrayList<Bill>)baseResponse.getData();

                        listener.loadMoreBills(bills);
                    }
                });
    }
}
