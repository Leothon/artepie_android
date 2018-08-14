package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;
/**
 * created by leothon on 2018.8.6
 */
public class Voice extends BaseResponse{

    private String imgurl;
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

}
