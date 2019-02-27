package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

public class NoticeInfo extends BaseResponse {

    private String noticeId;
    private String noticeFromUserId;
    private String noticeToUserId;
    private String noticeContent;
    private String noticeType;
    private String noticeTargetId;
    private int noticeStatus;
    private String noticeTime;

    private String userName;
    private String userIcon;

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getNoticeFromUserId() {
        return noticeFromUserId;
    }

    public void setNoticeFromUserId(String noticeFromUserId) {
        this.noticeFromUserId = noticeFromUserId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public int getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(int noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getNoticeTargetId() {
        return noticeTargetId;
    }

    public void setNoticeTargetId(String noticeTargetId) {
        this.noticeTargetId = noticeTargetId;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getNoticeToUserId() {
        return noticeToUserId;
    }

    public void setNoticeToUserId(String noticeToUserId) {
        this.noticeToUserId = noticeToUserId;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }
}
