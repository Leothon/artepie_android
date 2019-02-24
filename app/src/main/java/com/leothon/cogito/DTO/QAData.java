package com.leothon.cogito.DTO;

import java.util.ArrayList;

public class QAData {
    private String qa_id;
    private String qa_user_id;
    private String qa_content;
    private String qa_video;
    private String qa_like;
    private String qa_comment;
    private String qa_time;
    private String qa_audio;

    private String user_name;
    private String user_icon;
    private String user_signal;

    private boolean liked;
    private String qa_video_cover;

    private String qa_re_id;

    public String getQa_re_id() {
        return qa_re_id;
    }

    public void setQa_re_id(String qa_re_id) {
        this.qa_re_id = qa_re_id;
    }

    private QAData qaData;

    public QAData getQaData() {
        return qaData;
    }

    public void setQaData(QAData qaData) {
        this.qaData = qaData;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getQa_video_cover() {
        return qa_video_cover;
    }

    public void setQa_video_cover(String qa_video_cover) {
        this.qa_video_cover = qa_video_cover;
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

    public String getUser_signal() {
        return user_signal;
    }

    public void setUser_signal(String user_signal) {
        this.user_signal = user_signal;
    }

    public String getQa_id() {
        return qa_id;
    }

    public void setQa_id(String qa_id) {
        this.qa_id = qa_id;
    }

    public String getQa_user_id() {
        return qa_user_id;
    }

    public void setQa_user_id(String qa_user_id) {
        this.qa_user_id = qa_user_id;
    }

    public String getQa_content() {
        return qa_content;
    }

    public void setQa_content(String qa_content) {
        this.qa_content = qa_content;
    }

    public String getQa_video() {
        return qa_video;
    }

    public void setQa_video(String qa_video) {
        this.qa_video = qa_video;
    }

    public String getQa_like() {
        return qa_like;
    }

    public void setQa_like(String qa_like) {
        this.qa_like = qa_like;
    }

    public String getQa_comment() {
        return qa_comment;
    }

    public void setQa_comment(String qa_comment) {
        this.qa_comment = qa_comment;
    }

    public String getQa_time() {
        return qa_time;
    }

    public void setQa_time(String qa_time) {
        this.qa_time = qa_time;
    }

    public String getQa_audio() {
        return qa_audio;
    }

    public void setQa_audio(String qa_audio) {
        this.qa_audio = qa_audio;
    }
}
