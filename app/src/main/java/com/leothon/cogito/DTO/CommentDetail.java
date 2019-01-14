package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.Bean.Reply;

import java.util.ArrayList;

public class CommentDetail {
    private Comment comment;

    private ArrayList<Reply> replies;

    public Comment getComment() {
        return comment;
    }


    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<Reply> replies) {
        this.replies = replies;
    }
}
