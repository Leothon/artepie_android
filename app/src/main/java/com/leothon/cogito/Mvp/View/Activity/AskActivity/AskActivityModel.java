package com.leothon.cogito.Mvp.View.Activity.AskActivity;

import android.util.Log;

import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.DTO.SendQAData;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Http.UploadProgressListener;
import com.leothon.cogito.Http.UploadRequestBody;

import java.io.File;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AskActivityModel implements AskActivityContract.IAskActivityModel {
    @Override
    public void uploadFile(String path,final AskActivityContract.OnAskActivityFinishedListener listener) {
        File file = new File(path);
        UploadRequestBody uploadRequestBody = new UploadRequestBody(file, "multipart/form-data", new UploadProgressListener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {

                listener.showProgress(bytesWritten,contentLength);
            }
        });
        //RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), uploadRequestBody);

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .updataFile(photo)
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
                        String url = baseResponse.getMsg();

                        listener.getUploadUrl(url);
                    }
                });
    }

    @Override
    public void sendQaData(SendQAData sendQAData, final AskActivityContract.OnAskActivityFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .sendQAData(sendQAData)
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
                        String msg = baseResponse.getMsg();

                        listener.sendSuccess(msg);
                    }
                });

    }

    @Override
    public void getReInfo(String qaId, String token, final AskActivityContract.OnAskActivityFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getQA(token,qaId)
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
                        QAData qaData = (QAData) baseResponse.getData();

                        listener.getReInfo(qaData);
                    }
                });
    }

    @Override
    public void reContent(String token, String content, String qaId,final AskActivityContract.OnAskActivityFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .sendRe(token,content,qaId)
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
                        String info = baseResponse.getMsg();
                        listener.reSuccess(info);
                    }
                });
    }

    @Override
    public void uploadVideoImg(File file, final AskActivityContract.OnAskActivityFinishedListener listener) {
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .updataFile(photo)
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
                        String url = baseResponse.getMsg();

                        listener.getImgUrl(url);
                    }
                });
    }
}
