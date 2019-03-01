package com.leothon.cogito.Bean;

public class FeedbackInfo {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    private String versionCode;
    private String deviceBrand;
    private String deviceModel;
    private String buildApi;
    private String androidVersion;
    private String feedbackContent;

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getBuildApi() {
        return buildApi;
    }

    public void setBuildApi(String buildApi) {
        this.buildApi = buildApi;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionCode() {
        return versionCode;
    }

}
