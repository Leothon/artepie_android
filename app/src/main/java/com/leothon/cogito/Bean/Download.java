package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

public class Download {
    private String downloadurl;

    private String downloadtitle;

    public String getDownloadtitle() {
        return downloadtitle;
    }

    public void setDownloadtitle(String downloadtitle) {
        this.downloadtitle = downloadtitle;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }
}
