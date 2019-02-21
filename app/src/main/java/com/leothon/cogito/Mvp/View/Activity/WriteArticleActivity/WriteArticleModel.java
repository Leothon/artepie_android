package com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity;

import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.io.File;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class WriteArticleModel implements WriteArticleContract.IWriteArticleModel {
    @Override
    public void uploadImg(String path, final WriteArticleContract.OnWriteArticleFinishedListener listener) {
        File file = new File(path);
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
                        String url = baseResponse.getError();

                        listener.getUploadImgUrl(url);
                    }
                });
    }
}
