package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

public class verifyCode extends BaseResponse {

    private String  err_code;

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }
}
