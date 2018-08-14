package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

public class ActivityAndVideo extends BaseResponse {

    private String avtitle;
    private String avurl;
    private int avmark;//视频为0，讲座为1

    public int getAvmark() {
        return avmark;
    }

    public void setAvmark(int avmark) {
        this.avmark = avmark;
    }

    public String getAvtitle() {
        return avtitle;
    }

    public void setAvtitle(String avtitle) {
        this.avtitle = avtitle;
    }

    public String getAvurl() {
        return avurl;
    }

    public void setAvurl(String avurl) {
        this.avurl = avurl;
    }
}
