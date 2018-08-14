package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.3
 */
public class VideoClass extends BaseResponse{

    private String videoTitle;

    private String videoDescription;

    private String videoAuthor;

    private String videoIconUrl;

    private String videoUrl;

    private String viewCount;

    private String favCount;

    private ArrayList<UserComment> userComments;

    public ArrayList<UserComment> getUserComments() {
        return userComments;
    }

    public void setUserComments(ArrayList<UserComment> userComments) {
        this.userComments = userComments;
    }

    public String getFavCount() {
        return favCount;
    }

    public void setFavCount(String favCount) {
        this.favCount = favCount;
    }

    public String getVideoAuthor() {
        return videoAuthor;
    }

    public void setVideoAuthor(String videoAuthor) {
        this.videoAuthor = videoAuthor;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoIconUrl() {
        return videoIconUrl;
    }

    public void setVideoIconUrl(String videoIconUrl) {
        this.videoIconUrl = videoIconUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }


}

