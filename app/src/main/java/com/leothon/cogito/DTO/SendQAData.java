package com.leothon.cogito.DTO;

public class SendQAData {

    private String token;
    private String qa_content;
    private String qa_video;
    private String qa_audio;
    private String qa_video_cover;

    public String getQa_video_cover() {
        return qa_video_cover;
    }

    public void setQa_video_cover(String qa_video_cover) {
        this.qa_video_cover = qa_video_cover;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getQa_audio() {
        return qa_audio;
    }

    public void setQa_audio(String qa_audio) {
        this.qa_audio = qa_audio;
    }
}
