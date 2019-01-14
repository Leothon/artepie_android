package com.leothon.cogito.Mvp.View.Fragment.AskPage;

import com.leothon.cogito.DTO.QAData;

import java.util.ArrayList;

public class AskFragmentContract {

    public interface IAskModel{
        //TODO 执行相关数据操作

        void getAskData(String token,OnAskFinishedListener listener);
        void getAskMoreData(int currentPage,OnAskFinishedListener listener);

        void addLike(String token,String qaId,OnAskFinishedListener listener);
        void removeLike(String token,String qaId,OnAskFinishedListener listener);
    }

    public interface IAskView{
        //TODO　执行前端操作

        void loadAskData(ArrayList<QAData> qaData);
        void loadAskMoreData(ArrayList<QAData> qaData);
        void showInfo(String msg);
    }

    public interface OnAskFinishedListener {
        void loadAskData(ArrayList<QAData> qaData);
        void loadAskMoreData(ArrayList<QAData> qaData);
        void showInfo(String msg);

    }

    public interface IAskPresenter{
        void onDestory();
        void getAskData(String token);
        void getAskMoreData(int currentPage);

        void addLiked(String token,String qaId);
        void removeLiked(String token,String qaId);
    }
}
