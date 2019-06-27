package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import android.util.Log;

import com.leothon.cogito.Bean.TokenInfo;
import com.leothon.cogito.Bean.User;
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

public class EditInfoModel implements EditInfoContract.IEditInfoModel {
    @Override
    public void uploadIcon(String path, final EditInfoContract.OnEditInfoFinishedListener listener) {

        File file = new File(path);
//        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);

        OssUtils.getInstance().upImage(CommonUtils.getContext(), new OssUtils.OssUpCallback() {
            @Override
            public void successImg(String img_url) {
                listener.getIconUrl(img_url);
                Log.e( "successImg: ",img_url);
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
//                        listener.showMsg(errorMsg);
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
//                        listener.getIconUrl(url);
//                    }
//                });
    }

    @Override
    public void uploadAllInfo(User user, final EditInfoContract.OnEditInfoFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .updateUserInfo(user)
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

                        listener.updateSucess();
                    }
                });
    }

    @Override
    public void checkPhoneNumberIsExits(String number, final EditInfoContract.OnEditInfoFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .isPhoneExits(number)
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

                        String phoneInfo = baseResponse.getMsg();

                        listener.checkNumberResult(phoneInfo);
                    }
                });
    }

    @Override
    public void bindPhone(String number, String token, final EditInfoContract.OnEditInfoFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .bindPhoneNumber(token,number)
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

                        if (baseResponse.isSuccess()){
                            String phoneInfo = baseResponse.getMsg();

                            listener.bindPhoneNumberSuccess(phoneInfo);
                        }else {
                            String phoneInfo = baseResponse.getMsg();

                            listener.bindPhoneNumberFailed(phoneInfo);
                        }

                    }
                });
    }



    @Override
    public void setPassword(String token, String password, final EditInfoContract.OnEditInfoFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .setPassword(token,password)
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
                        if (baseResponse.isSuccess()){
                            String info = baseResponse.getMsg();
                            listener.setPasswordSuccess(info);
                        }else {
                            listener.setPasswordFailed("密码设置失败");
                        }

                    }
                });
    }

    @Override
    public void changePassword(String token, String oldPassword, String password,final EditInfoContract.OnEditInfoFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .changePassword(token,oldPassword,password)
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

                        if (baseResponse.isSuccess()){
                            listener.setPasswordSuccess(baseResponse.getMsg());
                        }else {
                            listener.setPasswordFailed(baseResponse.getMsg());
                        }
                    }
                });
    }
}
