package com.leothon.cogito.Mvp.View.Activity.CashActivity;

import android.widget.Toast;

import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.View.MyToast;

import io.reactivex.disposables.Disposable;

public class CashModel implements CashContract.ICashModel {
    @Override
    public void isHasPsd(String token, CashContract.OnCashFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .isHasPsd(token)
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
                        if (baseResponse.isSuccess()){
                            String msg = baseResponse.getMsg();
                            if (msg.equals("true")){
                                listener.checkPsdResult(true);
                            }else {
                                listener.checkPsdResult(false);
                            }
                        }


                    }
                });
    }

    @Override
    public void getMaxCash(String token, CashContract.OnCashFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getCashTotal(token)
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
                        if (baseResponse.isSuccess()){
                            String maxCash = baseResponse.getMsg();

                            listener.maxCashResult(maxCash);

                        }


                    }
                });
    }

    @Override
    public void setPsd(String token, String psd, CashContract.OnCashFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .setPsd(token,psd)
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
                        if (baseResponse.isSuccess()){
                            String msg = baseResponse.getMsg();

                            listener.setPsdSuccess(msg);

                        }


                    }
                });
    }

    @Override
    public void verifyPsd(String token, String psd, CashContract.OnCashFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .verifyPsd(token,psd)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showMsg("密码出错");
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
                            String msg = baseResponse.getMsg();

                            listener.verifyPsdSuccess(msg);

                        }else {
                            listener.verifyPsdFail("密码错误");
                        }


                    }
                });
    }

    @Override
    public void getCash(String info, String token,CashContract.OnCashFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getCash(info,token)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.getCashFail("提现出错，请检查账户信息");
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
                            String msg = baseResponse.getMsg();

                            listener.getCashSuccess(msg);

                        }else{
                            listener.getCashFail("提现出错，请检查账户信息");
                        }


                    }
                });
    }
}
