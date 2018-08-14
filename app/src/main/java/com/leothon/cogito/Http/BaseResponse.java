package com.leothon.cogito.Http;

import java.io.Serializable;
/*
* created by leothon on 2018.7.23
* 实现对服务器返回的数据进行筛选的过程,根据约定的json内容进行相应的更改
* */
public class BaseResponse<T> implements Serializable {
    private T data;
    private String code;
    private String msg;

    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess(){
        if (code.equals("200")){
            return true;
        }else {
            return false;
        }
    }
}
