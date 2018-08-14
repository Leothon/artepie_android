package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

/**
 * created by leothon on 2018.7.30
 */
public class Teacher extends BaseResponse {
    private String name;
    private int resId;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
