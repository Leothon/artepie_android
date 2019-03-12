package com.leothon.cogito.Mvp.View.Activity.VSureActivity;

import com.leothon.cogito.Bean.AuthInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class VSureModel implements VSureContract.IVSureModel {
    @Override
    public void sendAuthInfo(String token, String img, String content,final VSureContract.OnVSureFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .uploadAuthInfo(token,img,content)
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

                        listener.sendSuccess(url);
                    }
                });
    }

    @Override
    public void getAuthInfo(String token,final VSureContract.OnVSureFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getAuthInfo(token)
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
                        ArrayList<AuthInfo> authInfos = (ArrayList<AuthInfo>)baseResponse.getData();
                        listener.getInfoSuccess(authInfos);
                    }
                });
    }

    @Override
    public void uploadAuthImg(File file, final VSureContract.OnVSureFinishedListener listener) {
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

                        listener.uploadImgSuccess(url);
                    }
                });
    }

    @Override
    public void getUserInfo(String token, final VSureContract.OnVSureFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getUserInfo(token)
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
                        User user = (User)baseResponse.getData();

                        listener.getUserInfoSuccess(user);
                    }
                });
    }
}
