package com.leothon.cogito.Mvp.View.Fragment.ArticleListPage;

import com.leothon.cogito.DTO.ArticleData;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class ArticleListModel implements ArticleListContract.IArticleListModel {
    @Override
    public void getArticleData(String token, final ArticleListContract.OnArticleListFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getArticleData(token)
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
                        ArticleData articleData = (ArticleData)baseResponse.getData();
                        listener.loadArticlePageData(articleData);
                    }
                });
    }
}
