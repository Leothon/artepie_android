package com.leothon.cogito.Mvp.View.Activity.SearchActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.SearchResult;
import com.leothon.cogito.DTO.TeaClass;

import java.util.ArrayList;

public class SearchContract {

    public interface ISearchModel{


        void sendSearchResult(String keyword,String token,OnSearchFinishedListener listener);



    }

    public interface ISearchView{
        void searchSuccess(SearchResult searchResult);



        void showInfo(String msg);
    }

    public interface OnSearchFinishedListener {
        void searchSuccess(SearchResult searchResult);


        void showInfo(String msg);

    }

    public interface ISearchPresenter{
        void onDestroy();
        void sendSearchResult(String keyword,String token);


    }
}
