package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.StudyLine;

import java.util.ArrayList;

public class BagPageData {

    private StudyLine studyLine;

    private ArrayList<SelectClass> selectClasses;

    private ArrayList<SelectClass> fineClasses;

    public ArrayList<SelectClass> getSelectClasses() {
        return selectClasses;
    }

    public void setSelectClasses(ArrayList<SelectClass> selectClasses) {
        this.selectClasses = selectClasses;
    }

    public ArrayList<SelectClass> getFineClasses() {
        return fineClasses;
    }

    public void setFineClasses(ArrayList<SelectClass> fineClasses) {
        this.fineClasses = fineClasses;
    }

    public StudyLine getStudyLine() {
        return studyLine;
    }

    public void setStudyLine(StudyLine studyLine) {
        this.studyLine = studyLine;
    }

}
