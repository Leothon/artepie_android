package com.leothon.cogito.Bean;

public class AuthInfo {
    private String authId;
    private String authUserId;
    private String authImg;
    private String authContent;
    private String authTime;
    private int authStatus;
    private String authAdvice;

    public String getAuthAdvice() {
        return authAdvice;
    }

    public void setAuthAdvice(String authAdvice) {
        this.authAdvice = authAdvice;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthContent(String authContent) {
        this.authContent = authContent;
    }

    public String getAuthContent() {
        return authContent;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthImg(String authImg) {
        this.authImg = authImg;
    }

    public String getAuthImg() {
        return authImg;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
    }

    public String getAuthTime() {
        return authTime;
    }

    public void setAuthTime(String authTime) {
        this.authTime = authTime;
    }

    public String getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(String authUserId) {
        this.authUserId = authUserId;
    }
}
