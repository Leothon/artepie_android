package com.leothon.cogito.Mvp.View.Activity.SearchActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.SearchResult;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class SearchModel implements SearchContract.ISearchModel {
    @Override
    public void sendSearchResult(String keyword,String token ,final SearchContract.OnSearchFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .searchResult(keyword,token)
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
                        SearchResult searchResult = (SearchResult)baseResponse.getData();
                        listener.searchSuccess(searchResult);
                    }
                });
    }

}
