package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

import java.io.Serializable;

public class Article extends BaseResponse implements Serializable {

    private String articleId;
    private String articleImg;
    private String articleTitle;
    private String articleContent;
    private String articleAuthorIcon;
    private String articleAuthorName;
    private String articleTime;
    private String articleAuthorId;


    private String authorRole;

    public String getAuthorRole() {
        return authorRole;
    }

    public void setAuthorRole(String authorRole) {
        this.authorRole = authorRole;
    }


    public String getArticleAuthorId() {
        return articleAuthorId;
    }

    public void setArticleAuthorId(String articleAuthorId) {
        this.articleAuthorId = articleAuthorId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleAuthorIcon() {
        return articleAuthorIcon;
    }

    public void setArticleAuthorIcon(String articleAuthorIcon) {
        this.articleAuthorIcon = articleAuthorIcon;
    }

    public String getArticleAuthorName() {
        return articleAuthorName;
    }

    public void setArticleAuthorName(String articleAuthorName) {
        this.articleAuthorName = articleAuthorName;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public String getArticleTime() {
        return articleTime;
    }

    public void setArticleTime(String articleTime) {
        this.articleTime = articleTime;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
}
