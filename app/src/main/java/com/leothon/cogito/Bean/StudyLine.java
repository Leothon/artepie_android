package com.leothon.cogito.Bean;

import java.util.ArrayList;

public class StudyLine {

    private ArrayList<String> date;
    private int classCount;

    private ArrayList<String> classCountDat;

    public ArrayList<String> getClassCountDat() {
        return classCountDat;
    }

    public void setClassCountDat(ArrayList<String> classCountDat) {
        this.classCountDat = classCountDat;
    }
    public int getClassCount() {
        return classCount;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public void setDate(ArrayList<String> date) {
        this.date = date;
    }
}
