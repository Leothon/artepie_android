package com.leothon.cogito.Mvp.View.Activity.UploadDetailClassActivity;

import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Http.UploadProgressListener;
import com.leothon.cogito.Http.UploadRequestBody;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.OssUtils;

import java.io.File;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class UploadClassDetailModel implements UploadClassDetailContract.IUploadClassDetailModel {
    @Override
    public void uploadVideo(String path,final UploadClassDetailContract.OnUploadClassDetailFinishedListener listener) {
        File file = new File(path);

        OssUtils.getInstance().upVideo(CommonUtils.getContext(), new OssUtils.OssUpCallback() {
            @Override
            public void successImg(String img_url) {

            }

            @Override
            public void successVideo(String video_url) {
                listener.uploadVideoSuccess(video_url);
            }

            @Override
            public void inProgress(long progress, long allsi) {
                //listener.showProgress(progress,allsi);

            }
        },file.getName(),path);
//        UploadRequestBody uploadRequestBody = new UploadRequestBody(file, "multipart/form-data", new UploadProgressListener() {
//            @Override
//            public void onRequestProgress(long bytesWritten, long contentLength) {
//
//                listener.showProgress(bytesWritten,contentLength);
//            }
//        });
//        //RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part video = MultipartBody.Part.createFormData("file", file.getName(), uploadRequestBody);
//
//        RetrofitServiceManager.getInstance().create(HttpService.class)
//                .updataFile(video)
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
//                        listener.uploadVideoSuccess(url);
//                    }
//                });
    }

    @Override
    public void uploadImg(String name,byte[] img, final UploadClassDetailContract.OnUploadClassDetailFinishedListener listener) {


        OssUtils.getInstance().upImage(CommonUtils.getContext(), new OssUtils.OssUpCallback() {
            @Override
            public void successImg(String img_url) {
                listener.uploadImgSuccesss(img_url);

            }

            @Override
            public void successVideo(String video_url) {

            }

            @Override
            public void inProgress(long progress, long allsi) {


            }
        },name,img);
//        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
//
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
//                        listener.uploadImgSuccesss(url);
//                    }
//                });
    }

    @Override
    public void sendClassDetail(ClassDetailList classDetailList, final UploadClassDetailContract.OnUploadClassDetailFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .uploadClassDetail(classDetailList)
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

                        listener.sendClassDetailSuccess(msg);
                    }
                });
    }
}
