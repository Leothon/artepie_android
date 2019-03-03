package com.leothon.cogito.Mvp.View.Activity.ArticleHisActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class ArticleHisModel implements ArticleHisContract.IArticleHisModel {
    @Override
    public void getArticleHisData(String userId, final ArticleHisContract.OnArticleHisFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getArticleDataById(userId)
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
                        ArrayList<Article> articles = (ArrayList<Article>) baseResponse.getData();
                        listener.loadArticleHisData(articles);
                    }
                });
    }

    @Override
    public void getArticleHisMoreData(int currentPage, String userId,final ArticleHisContract.OnArticleHisFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getMoreArticleDataById(userId,currentPage)
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
                        ArrayList<Article> articles = (ArrayList<Article>) baseResponse.getData();
                        listener.loadArticleHisMoreData(articles);
                    }
                });
    }
}
