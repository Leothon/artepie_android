package com.leothon.cogito.Bean;

public class Reply {

    private String reply_id;
    private String reply_r_id;
    private String reply_comment;
    private String reply_user_id;
    private String reply_to_user_id;
    private String reply_like;

    private String user_name;
    private String user_icon;

    private String to_user_name;
    private String to_user_icon;

    private String reply_time;

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getTo_user_icon() {
        return to_user_icon;
    }

    public void setTo_user_icon(String to_user_icon) {
        this.to_user_icon = to_user_icon;
    }

    public String getTo_user_name() {
        return to_user_name;
    }

    public void setTo_user_name(String to_user_name) {
        this.to_user_name = to_user_name;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getReply_r_id() {
        return reply_r_id;
    }

    public void setReply_r_id(String reply_r_id) {
        this.reply_r_id = reply_r_id;
    }

    public String getReply_comment() {
        return reply_comment;
    }

    public void setReply_comment(String reply_comment) {
        this.reply_comment = reply_comment;
    }

    public String getReply_user_id() {
        return reply_user_id;
    }

    public void setReply_user_id(String reply_user_id) {
        this.reply_user_id = reply_user_id;
    }

    public String getReply_to_user_id() {
        return reply_to_user_id;
    }

    public void setReply_to_user_id(String reply_to_user_id) {
        this.reply_to_user_id = reply_to_user_id;
    }

    public String getReply_like() {
        return reply_like;
    }

    public void setReply_like(String reply_like) {
        this.reply_like = reply_like;
    }
}
