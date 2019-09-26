package com.leothon.cogito.Bean;

public class Tag {

    private String tagInfo;
    private String tagType;

    public Tag(String tagInfo){
        this.tagInfo = tagInfo;
    }

    public Tag(String tagInfo,String tagType){
        this.tagInfo = tagInfo;
        this.tagType = tagType;
    }
    public String getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(String tagInfo) {
        this.tagInfo = tagInfo;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

}
