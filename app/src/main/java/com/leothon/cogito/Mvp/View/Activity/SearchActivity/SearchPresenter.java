package com.leothon.cogito.Mvp.View.Activity.SearchActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.SearchResult;
import com.leothon.cogito.DTO.TeaClass;

import java.util.ArrayList;

public class SearchPresenter implements SearchContract.OnSearchFinishedListener,SearchContract.ISearchPresenter {

    private SearchContract.ISearchModel iSearchModel;
    private SearchContract.ISearchView iSearchView;

    public SearchPresenter(SearchContract.ISearchView iSearchView){
        this.iSearchView = iSearchView;
        this.iSearchModel = new SearchModel();
    }
    @Override
    public void searchSuccess(SearchResult searchResult) {
        if (iSearchView != null){
            iSearchView.searchSuccess(searchResult);
        }
    }


    @Override
    public void showInfo(String msg) {

        if (iSearchView != null){
            iSearchView.showInfo(msg);
        }
    }

    @Override
    public void onDestroy() {
        iSearchView = null;
        iSearchModel = null;
    }

    @Override
    public void sendSearchResult(String keyword,String token) {
        iSearchModel.sendSearchResult(keyword,token,this);
    }


}
