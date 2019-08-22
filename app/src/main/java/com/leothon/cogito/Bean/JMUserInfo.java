package com.leothon.cogito.Bean;

import android.text.TextUtils;

import java.io.File;
import java.util.Map;

import cn.jiguang.imui.commons.models.IUser;
import cn.jpush.im.android.api.callback.DownloadAvatarCallback;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public  class JMUserInfo extends UserInfo implements IUser {

    private UserInfo userInfo;

    public JMUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public String getId() {
        return String.valueOf(userID);
    }

    @Override
    public String getDisplayName() {
        if (userInfo.getNotename() != null || !TextUtils.isEmpty(userInfo.getNotename())) {
            return userInfo.getNotename();
        } else if (userInfo.getNickname() != null || !TextUtils.isEmpty(userInfo.getNickname())) {
            return userInfo.getNickname();
        } else {
            return userInfo.getUserName();
        }
    }

    @Override
    public String getAvatarFilePath() {
        if (!TextUtils.isEmpty(userInfo.getAvatar())) {
            return userInfo.getAvatarFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    @Override
    public String getNotename() {
        return userInfo.getNotename();
    }

    @Override
    public String getNoteText() {
        return userInfo.getNoteText();
    }

    @Override
    public long getBirthday() {
        return userInfo.getBirthday();
    }

    @Override
    public File getAvatarFile() {
        return userInfo.getAvatarFile();
    }

    @Override
    public void getAvatarFileAsync(DownloadAvatarCallback downloadAvatarCallback) {

    }

    @Override
    public void getAvatarBitmap(GetAvatarBitmapCallback getAvatarBitmapCallback) {

    }

    @Override
    public File getBigAvatarFile() {
        return null;
    }

    @Override
    public void getBigAvatarBitmap(GetAvatarBitmapCallback getAvatarBitmapCallback) {

    }

    @Override
    public int getBlacklist() {
        return 0;
    }

    @Override
    public int getNoDisturb() {
        return 0;
    }

    @Override
    public boolean isFriend() {
        return false;
    }

    @Override
    public String getAppKey() {
        return null;
    }

    @Override
    public void setUserExtras(Map<String, String> map) {

    }

    @Override
    public void setUserExtras(String s, String s1) {

    }

    @Override
    public void setBirthday(long l) {

    }

    @Override
    public void setNoDisturb(int i, BasicCallback basicCallback) {

    }

    @Override
    public void removeFromFriendList(BasicCallback basicCallback) {

    }

    @Override
    public void updateNoteName(String s, BasicCallback basicCallback) {

    }

    @Override
    public void updateNoteText(String s, BasicCallback basicCallback) {

    }
}
