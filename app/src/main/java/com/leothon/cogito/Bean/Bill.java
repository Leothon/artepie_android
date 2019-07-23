package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

public class Bill extends BaseResponse {

    private String title;
    private String time;
    private String count;
    private String billId;
    private String billIcon;
    private int outIn;

    private String endCount;

    public String getEndCount() {
        return endCount;
    }

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setEndCount(String endCount) {
        this.endCount = endCount;
    }


    public int getOutIn() {
        return outIn;
    }

    public void setOutIn(int outIn) {
        this.outIn = outIn;
    }

    public String getBillIcon() {
        return billIcon;
    }

    public void setBillIcon(String billIcon) {
        this.billIcon = billIcon;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
