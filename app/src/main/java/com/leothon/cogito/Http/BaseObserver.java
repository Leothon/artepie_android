package com.leothon.cogito.Http;

import android.widget.Toast;

import org.reactivestreams.Subscriber;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.7.23
 * 封装被观察者
 */
public abstract class BaseObserver<T extends BaseResponse> implements Observer<T>,ISubscriber<T> {


    protected void doOnNetError(){}

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        /**
         * 根据后台处理的数据进行代码的重新编写
         */
        doOnNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String error = ApiException.handleException(e).getMessage();
        setError(error);
    }
    @Override
    public void onComplete() {
        doOnCompleted();
    }

    private void setError(String errorMsg) {
        //showToast(errorMsg);
        doOnError(errorMsg);
        doOnNetError();
    }
//    /**
//     * Toast提示 *
//     * @param msg 提示内容
//     */
//    protected void showToast(String msg) {
//        if (toast == null) {
//            toast = Toast.makeText(BaseRxHttpApplication.getContext(), msg, Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(msg);
//        }
//        toast.show();
//    }



}
