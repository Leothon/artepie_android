package com.leothon.cogito.Bean;

import android.graphics.drawable.Drawable;

import com.leothon.cogito.Http.BaseResponse;

/**
 * created by leothon on 9/15/2018.
 */
public class UploadSave  extends BaseResponse{
    private String title;
    private String desc;
    private String content;
    private String imgUri;
    private String videoUri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }
}
