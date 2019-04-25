package com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity;

import android.util.Log;

import com.leothon.cogito.Bean.Article;
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

public class WriteArticleModel implements WriteArticleContract.IWriteArticleModel {
    @Override
    public void uploadImg(File file, final WriteArticleContract.OnWriteArticleFinishedListener listener) {


//        OssUtils.getInstance().upImage(CommonUtils.getContext(), new OssUtils.OssUpCallback() {
//            @Override
//            public void successImg(String img_url) {
//
//                listener.getUploadImgUrl(img_url);
//
//
//            }
//
//            @Override
//            public void successVideo(String video_url) {
//
//            }
//
//            @Override
//            public void inProgress(long progress, long allsi) {
//
//
//            }
//        },name,img);
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

                        listener.getUploadImgUrl(url);
                    }
                });
    }

    @Override
    public void uploadArticle(Article article, final WriteArticleContract.OnWriteArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .uploadArticle(article)
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

                        listener.isUploadSuccess(url);
                    }
                });
    }
}
