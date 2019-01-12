package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.Comment;

import java.util.ArrayList;

public class QADataDetail {

    private QAData qaData;

    public QAData getQaData() {
        return qaData;
    }

    public void setQaData(QAData qaData) {
        this.qaData = qaData;
    }

    private ArrayList<Comment> comments;

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }


}
