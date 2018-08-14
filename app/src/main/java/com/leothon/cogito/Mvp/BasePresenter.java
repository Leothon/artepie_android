package com.leothon.cogito.Mvp;

/**
 * created by leothon on 2018.7.23
 *
 * @param <M,V>
 */
public abstract class BasePresenter<M extends BaseContract.BaseIModel,V extends BaseContract.BaseIView>{

    public M mModel;
    public V mView;

    void attachWindow(M m,V v){
        this.mModel = m;
        this.mView = v;
    }

    void detachWindow(){
        this.mModel = null;
        this.mView = null;
    }




}
