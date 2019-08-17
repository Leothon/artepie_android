package com.leothon.cogito.DTO;

public class Update {

    private String updateId;
    private String updateVersion;
    private String updateTime;
    private int updateCode;

    private String updateContent;

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public int getUpdateCode() {
        return updateCode;
    }

    public void setUpdateCode(int updateCode) {
        this.updateCode = updateCode;
    }
    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

}
