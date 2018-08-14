package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

/**
 * created by leothon on 8/12/2018.
 */
public class Notice extends BaseResponse{
    private String noticecontent;
    private String noticeuser;
    private String usericon;

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    public String getNoticecontent() {
        return noticecontent;
    }

    public void setNoticecontent(String noticecontent) {
        this.noticecontent = noticecontent;
    }

    public String getNoticeuser() {
        return noticeuser;
    }

    public void setNoticeuser(String noticeuser) {
        this.noticeuser = noticeuser;
    }
}
