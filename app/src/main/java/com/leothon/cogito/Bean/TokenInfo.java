package com.leothon.cogito.Bean;

public class TokenInfo {
    private String token;
    private String info;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "token信息=" + token + "状态" + info;
    }
}
