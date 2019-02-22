package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import io.reactivex.disposables.Disposable;

public class ArticleModel implements ArticleContract.IArticleModel {
    @Override
    public void getArticleInfo(String articleId, String token, final ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getArticleDetail(token,articleId)
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
                        Article article = (Article)baseResponse.getData();

                        listener.loadArticleData(article);
                    }
                });
    }
}
