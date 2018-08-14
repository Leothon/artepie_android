package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

/**
 * created by leothon on 2018.8.3
 */
public class UserComment extends BaseResponse{

    private String usericon;
    private String username;
    private String usercomment;
    private int userIcon;

    public int getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(int userIcon) {
        this.userIcon = userIcon;
    }

    public String getUsercomment() {
        return usercomment;
    }

    public void setUsercomment(String usercomment) {
        this.usercomment = usercomment;
    }

    public String getUsericon() {
        return usericon;
    }

    public void setUsericon(String usericon) {
        this.usericon = usericon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
