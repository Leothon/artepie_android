package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.3
 */
public class Comment extends BaseResponse{

    private String comment_q_id;
    private String comment_q_qa_id;
    private String comment_q_user_id;
    private String comment_q_content;
    private String comment_q_like;
    private String user_name;
    private String user_icon;
    private String comment_q_time;

    public String getComment_q_time() {
        return comment_q_time;
    }

    public void setComment_q_time(String comment_q_time) {
        this.comment_q_time = comment_q_time;
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

    private ArrayList<Reply> replies;

    public String getComment_q_id() {
        return comment_q_id;
    }

    public void setComment_q_id(String comment_q_id) {
        this.comment_q_id = comment_q_id;
    }

    public String getComment_q_qa_id() {
        return comment_q_qa_id;
    }

    public void setComment_q_qa_id(String comment_q_qa_id) {
        this.comment_q_qa_id = comment_q_qa_id;
    }

    public String getComment_q_user_id() {
        return comment_q_user_id;
    }

    public void setComment_q_user_id(String comment_q_user_id) {
        this.comment_q_user_id = comment_q_user_id;
    }

    public String getComment_q_content() {
        return comment_q_content;
    }

    public void setComment_q_content(String comment_q_content) {
        this.comment_q_content = comment_q_content;
    }

    public String getComment_q_like() {
        return comment_q_like;
    }

    public void setComment_q_like(String comment_q_like) {
        this.comment_q_like = comment_q_like;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<Reply> replies) {
        this.replies = replies;
    }
}
