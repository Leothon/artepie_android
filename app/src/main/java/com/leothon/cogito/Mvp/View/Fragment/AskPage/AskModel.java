package com.leothon.cogito.Mvp.View.Fragment.AskPage;

import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.Inform;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class AskModel implements AskFragmentContract.IAskModel {
    @Override
    public void getAskData(String token, final AskFragmentContract.OnAskFinishedListener listener) {

        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getQAData(token)
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
                        ArrayList<QAData> qaData = (ArrayList<QAData>) baseResponse.getData();
                        listener.loadAskData(qaData);
                    }
                });
    }

    @Override
    public void getAskMoreData(int currentPage,String token ,final AskFragmentContract.OnAskFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreQAData(currentPage,token)
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
                        ArrayList<QAData> qaData = (ArrayList<QAData>) baseResponse.getData();
                        listener.loadAskMoreData(qaData);
                    }
                });

    }

    @Override
    public void addLike(String token, String qaId, final AskFragmentContract.OnAskFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .addLikeQa(token,qaId)
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
                        if (baseResponse.isSuccess()){
                            listener.showInfo("已点赞");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void removeLike(String token, String qaId,final AskFragmentContract.OnAskFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .removeLikeQa(token,qaId)
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
                        if (baseResponse.isSuccess()){
                            listener.showInfo("已取消");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void deleteQa(String token, String qaId, final AskFragmentContract.OnAskFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .deleteQa(token,qaId)
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
                        if (baseResponse.isSuccess()){
                            listener.deleteSuccess("删除成功");
                        }else {
                            listener.deleteSuccess("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void getInform(String token, AskFragmentContract.OnAskFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getInform(token)
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
                        Inform inform = (Inform)baseResponse.getData();
                        listener.getInformSuccess(inform.getInformText());
                    }
                });
    }
}
