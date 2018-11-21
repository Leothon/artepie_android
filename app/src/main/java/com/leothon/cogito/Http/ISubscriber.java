package com.leothon.cogito.Http;

import io.reactivex.disposables.Disposable;

/**
 * created by leothon on 2018.7.23
 * @param <T>
 */
public  interface ISubscriber<T extends BaseResponse> {
    void doOnSubscribe(Disposable d);

    void doOnError(String msg);

    void doOnNext(T t);

    void doOnCompleted();
}
