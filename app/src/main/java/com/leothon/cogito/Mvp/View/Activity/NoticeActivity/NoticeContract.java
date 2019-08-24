package com.leothon.cogito.Mvp.View.Activity.NoticeActivity;

import com.leothon.cogito.Bean.NoticeInfo;
import com.leothon.cogito.Bean.User;

import java.util.ArrayList;

public class NoticeContract {
    public interface INoticeModel{

        void getNoticeInfo(String token,OnNoticeFinishedListener listener);
        void getMoreNoticeInfo(int page,String token,OnNoticeFinishedListener listener);
        void setNoticeVisible(String token,String noticeId,OnNoticeFinishedListener listener);

        void setAllNoticeVisible(String token,OnNoticeFinishedListener listener);

    }

    public interface INoticeView{


        void loadNoticeInfo(ArrayList<NoticeInfo> noticeInfos);
        void loadMoreNoticeInfo(ArrayList<NoticeInfo> noticeInfos);
        void setNoticeVisibleSuccess(String msg);
        void setNoticeAllVisibleSuccess(String msg);
        void showMsg(String msg);


    }

    public interface OnNoticeFinishedListener {

        void loadNoticeInfo(ArrayList<NoticeInfo> noticeInfos);
        void loadMoreNoticeInfo(ArrayList<NoticeInfo> noticeInfos);
        void setNoticeVisibleSuccess(String msg);
        void setNoticeAllVisibleSuccess(String msg);
        void showMsg(String msg);
    }

    public interface INoticePresenter{
        void getNoticeInfo(String token);
        void getMoreNoticeInfo(int page,String token);
        void setNoticeVisible(String token,String noticeId);

        void setAllNoticeVisible(String token);
        void onDestroy();



    }
}
