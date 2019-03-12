package com.leothon.cogito.Mvp.View.Activity.EditClassActivity;

import android.util.Log;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Http.Api;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class EditClassModel implements EditClassContract.IEditClassModel {
    @Override
    public void getSelectClassInfo(String userId, final EditClassContract.OnEditClassFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getClassByUserId(userId)
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
                        listener.loadClassSuccess(selectClasses);
                    }
                });
    }

    @Override
    public void deleteClass(String token, String classId, final EditClassContract.OnEditClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .deleteClass(classId,token)
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
                        String msg = baseResponse.getMsg();
                        listener.deleteClassSuccess(msg);
                    }
                });
    }
}
