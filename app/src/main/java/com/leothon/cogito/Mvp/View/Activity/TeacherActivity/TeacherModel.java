package com.leothon.cogito.Mvp.View.Activity.TeacherActivity;

import android.util.Log;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.TeaClass;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class TeacherModel implements TeacherContract.ITeacherModel {
    @Override
    public void loadClassByTeacher(String token, String teaId, final TeacherContract.OnTeacherFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getTeaClass(token,teaId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                        Log.e("错误", "doOnError: " + errorMsg );
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        TeaClass teaClass = (TeaClass)baseResponse.getData();
                        listener.getTeacherClass(teaClass);
                    }
                });
    }

    @Override
    public void loadMoreClassByTeacher(String token, String teaId, int currentPage, TeacherContract.OnTeacherFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreTeaClass(token,teaId,currentPage)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo(errorMsg);
                        Log.e("错误", "doOnError: " + errorMsg );
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
                        listener.getMoreTeacherClass(selectClasses);
                    }
                });
    }
}
