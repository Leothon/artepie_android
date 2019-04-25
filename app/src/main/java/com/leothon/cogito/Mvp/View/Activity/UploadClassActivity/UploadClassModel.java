package com.leothon.cogito.Mvp.View.Activity.UploadClassActivity;

import android.util.Log;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.OssUtils;

import java.io.File;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadClassModel implements UploadClassContract.IUploadClassModel {
    @Override
    public void createClass(SelectClass selectClass, final UploadClassContract.OnUploadClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .createClass(selectClass)
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
                        String classId = baseResponse.getMsg();

                        listener.createSuccess(classId);
                    }
                });
    }

    @Override
    public void uploadImg(String path, final UploadClassContract.OnUploadClassFinishedListener listener) {
        File file = new File(path);

        OssUtils.getInstance().upImage(CommonUtils.getContext(), new OssUtils.OssUpCallback() {
            @Override
            public void successImg(String img_url) {
                listener.imgSendSuccess(img_url);
            }

            @Override
            public void successVideo(String video_url) {

            }

            @Override
            public void inProgress(long progress, long allsi) {

            }
        },file.getName(),path);
//        RetrofitServiceManager.getInstance().create(HttpService.class)
//                .updataFile(photo)
//                .compose(ThreadTransformer.switchSchedulers())
//                .subscribe(new BaseObserver() {
//                    @Override
//                    public void doOnSubscribe(Disposable d) { }
//                    @Override
//                    public void doOnError(String errorMsg) {
//                        listener.showInfo(errorMsg);
//
//                    }
//                    @Override
//                    public void doOnNext(BaseResponse baseResponse) {
//
//                    }
//                    @Override
//                    public void doOnCompleted() {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseResponse baseResponse) {
//                        String url = baseResponse.getMsg();
//
//                        listener.imgSendSuccess(url);
//                    }
//                });
    }

    @Override
    public void getClassInfo(String classId, final UploadClassContract.OnUploadClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getClassByClassId(classId)
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

                        listener.getClassSuccess(selectClass);
                    }
                });
    }

    @Override
    public void editClassInfo(SelectClass selectClass, final UploadClassContract.OnUploadClassFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .editClass(selectClass)
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
                        String classId = baseResponse.getMsg();

                        listener.editClassSuccess(classId);
                    }
                });
    }
}
