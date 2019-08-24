package com.leothon.cogito.Mvp.View.Activity.NoticeActivity;

import com.leothon.cogito.Bean.NoticeInfo;
import com.leothon.cogito.Bean.User;

import java.util.ArrayList;

public class NoticePresenter implements NoticeContract.INoticePresenter,NoticeContract.OnNoticeFinishedListener {

    private NoticeContract.INoticeView iNoticeView;
    private NoticeContract.INoticeModel iNoticeModel;

    public NoticePresenter(NoticeContract.INoticeView iNoticeView){
        this.iNoticeView = iNoticeView;
        this.iNoticeModel = new NoticeModel();
    }
    @Override
    public void loadNoticeInfo(ArrayList<NoticeInfo> noticeInfos) {

        if (iNoticeView != null){
            iNoticeView.loadNoticeInfo(noticeInfos);
        }
    }

    @Override
    public void loadMoreNoticeInfo(ArrayList<NoticeInfo> noticeInfos) {
        if (iNoticeView != null){
            iNoticeView.loadMoreNoticeInfo(noticeInfos);
        }
    }

    @Override
    public void setNoticeVisibleSuccess(String msg) {
        if (iNoticeView != null){
            iNoticeView.setNoticeVisibleSuccess(msg);
        }
    }

    @Override
    public void setNoticeAllVisibleSuccess(String msg) {
        if (iNoticeView != null){
            iNoticeView.setNoticeAllVisibleSuccess(msg);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iNoticeView != null){
            iNoticeView.showMsg(msg);
        }
    }



    @Override
    public void getNoticeInfo(String token) {
        iNoticeModel.getNoticeInfo(token,this);
    }

    @Override
    public void getMoreNoticeInfo(int page, String token) {
        iNoticeModel.getMoreNoticeInfo(page,token,this);
    }

    @Override
    public void setNoticeVisible(String token, String noticeId) {
        iNoticeModel.setNoticeVisible(token,noticeId,this);
    }

    @Override
    public void setAllNoticeVisible(String token) {
        iNoticeModel.setAllNoticeVisible(token,this);
    }

    @Override
    public void onDestroy() {
        iNoticeModel = null;
        iNoticeView = null;
    }

}
