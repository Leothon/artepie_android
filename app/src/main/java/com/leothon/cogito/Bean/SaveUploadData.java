package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

import java.util.ArrayList;

/**
 * created by leothon on 9/15/2018.
 */
public class SaveUploadData extends BaseResponse {

    private String title;
    private String desc;
    private ArrayList<String> tags;

    private ArrayList<UploadSave> saveUploadDatas;

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

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<UploadSave> getSaveUploadDatas() {
        return saveUploadDatas;
    }

    public void setSaveUploadDatas(ArrayList<UploadSave> saveUploadDatas) {
        this.saveUploadDatas = saveUploadDatas;
    }
}
