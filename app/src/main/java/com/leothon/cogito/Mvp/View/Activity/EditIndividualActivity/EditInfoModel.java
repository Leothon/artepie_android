package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import android.util.Log;

import com.leothon.cogito.Bean.TokenInfo;
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

public class EditInfoModel implements EditInfoContract.IEditInfoModel {
    @Override
    public void uploadIcon(String path, final EditInfoContract.OnEditInfoFinishedListener listener) {

        File file = new File(path);
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("pic", file.getName(), photoRequestBody);


        RetrofitServiceManager.getInstance().create(HttpService.class)
                .updataFile(photo)
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
                        String url = baseResponse.getError();

                        listener.getIconUrl(url);
                    }
                });
    }

    @Override
    public void uploadAllInfo(String icon, String name, int sex, String birth, String phone, String signal, String address, final EditInfoContract.OnEditInfoFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .updateUserInfo(icon,name,sex,birth,phone,signal,address)
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
                        Log.e("返回", "完成");
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {

                        listener.updateSucess();
                    }
                });
    }
}
