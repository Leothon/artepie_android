package com.leothon.cogito.Mvp.View.Fragment.AskPage;

import com.leothon.cogito.DTO.QAData;

import java.util.ArrayList;

public class AskFragmentContract {

    public interface IAskModel{
        //TODO 执行相关数据操作

        void getAskData(String token,OnAskFinishedListener listener);
        void getAskMoreData(int currentPage,String token,OnAskFinishedListener listener);

        void addLike(String token,String qaId,OnAskFinishedListener listener);
        void removeLike(String token,String qaId,OnAskFinishedListener listener);

        void deleteQa(String token,String qaId,OnAskFinishedListener listener);

        void getInform(String token,OnAskFinishedListener listener);
    }

    public interface IAskView{
        //TODO　执行前端操作

        void loadAskData(ArrayList<QAData> qaData);
        void loadAskMoreData(ArrayList<QAData> qaData);
        void showInfo(String msg);
        void deleteSuccess(String msg);

        void getInformSuccess(String text);
    }

    public interface OnAskFinishedListener {
        void loadAskData(ArrayList<QAData> qaData);
        void loadAskMoreData(ArrayList<QAData> qaData);
        void showInfo(String msg);
        void deleteSuccess(String msg);
        void getInformSuccess(String text);
    }

    public interface IAskPresenter{
        void onDestroy();
        void getAskData(String token);
        void getAskMoreData(int currentPage,String token);

        void addLiked(String token,String qaId);
        void removeLiked(String token,String qaId);
        void deleteQa(String token,String qaId);

        void getInform(String token);
    }
}
