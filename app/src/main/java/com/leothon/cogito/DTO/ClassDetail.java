package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Bean.SelectClass;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassDetail implements Serializable {
    private SelectClass teaClasss;
    private ArrayList<ClassDetailList> classDetailLists;

    public ArrayList<ClassDetailList> getClassDetailLists() {
        return classDetailLists;
    }

    public void setClassDetailLists(ArrayList<ClassDetailList> classDetailLists) {
        this.classDetailLists = classDetailLists;
    }

    public SelectClass getTeaClasss() {
        return teaClasss;
    }

    public void setTeaClasss(SelectClass teaClasss) {
        this.teaClasss = teaClasss;
    }
}
