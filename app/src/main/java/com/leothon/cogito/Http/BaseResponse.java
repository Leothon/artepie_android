package com.leothon.cogito.Http;

import java.io.Serializable;
/*
* created by leothon on 2018.7.23
* 实现对服务器返回的数据进行筛选的过程,根据约定的json内容进行相应的更改
* */
public class BaseResponse<T> implements Serializable {
    private T data;
    private boolean success;
    private String error;

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccess(){
       return success;
    }
}
