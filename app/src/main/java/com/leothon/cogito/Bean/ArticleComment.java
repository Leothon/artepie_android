package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

public class ArticleComment extends BaseResponse {

    private String articleCommentId;
    private String articleCommentUserId;
    private String articleCommentUserName;
    private String articleCommentUserIcon;
    private String articleCommentArticleId;
    private String articleCommentTime;
    private String articleCommentReply;
    private String articleComment;
    private String articleCommentLike;

    public String getArticleComment() {
        return articleComment;
    }

    public void setArticleComment(String articleComment) {
        this.articleComment = articleComment;
    }

    public String getArticleCommentArticleId() {
        return articleCommentArticleId;
    }

    public void setArticleCommentArticleId(String articleCommentArticleId) {
        this.articleCommentArticleId = articleCommentArticleId;
    }

    public String getArticleCommentId() {
        return articleCommentId;
    }

    public void setArticleCommentId(String articleCommentId) {
        this.articleCommentId = articleCommentId;
    }

    public String getArticleCommentLike() {
        return articleCommentLike;
    }

    public void setArticleCommentLike(String articleCommentLike) {
        this.articleCommentLike = articleCommentLike;
    }

    public String getArticleCommentReply() {
        return articleCommentReply;
    }

    public void setArticleCommentReply(String articleCommentReply) {
        this.articleCommentReply = articleCommentReply;
    }

    public String getArticleCommentTime() {
        return articleCommentTime;
    }

    public void setArticleCommentTime(String articleCommentTime) {
        this.articleCommentTime = articleCommentTime;
    }

    public String getArticleCommentUserIcon() {
        return articleCommentUserIcon;
    }

    public void setArticleCommentUserIcon(String articleCommentUserIcon) {
        this.articleCommentUserIcon = articleCommentUserIcon;
    }

    public String getArticleCommentUserId() {
        return articleCommentUserId;
    }

    public void setArticleCommentUserId(String articleCommentUserId) {
        this.articleCommentUserId = articleCommentUserId;
    }

    public String getArticleCommentUserName() {
        return articleCommentUserName;
    }

    public void setArticleCommentUserName(String articleCommentUserName) {
        this.articleCommentUserName = articleCommentUserName;
    }

}
