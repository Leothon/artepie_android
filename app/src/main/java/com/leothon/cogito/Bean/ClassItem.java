package com.leothon.cogito.Bean;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.4
 * 单个课程数据类
 */
public class ClassItem {
    private String classurl;
    private String classtitle;
    private String classdescription;
    private String  classprice;
    private ArrayList<SelectClass> selectClasses;

    private String authorname;

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public ArrayList<SelectClass> getVideoClasses() {
        return selectClasses;
    }

    public void setVideoClasses(ArrayList<SelectClass> selectClasses) {
        this.selectClasses = selectClasses;
    }

    public String getClassdescription() {
        return classdescription;
    }

    public void setClassdescription(String classdescription) {
        this.classdescription = classdescription;
    }

    public String getClassprice() {
        return classprice;
    }

    public void setClassprice(String classprice) {
        this.classprice = classprice;
    }

    public String getClasstitle() {
        return classtitle;
    }

    public void setClasstitle(String classtitle) {
        this.classtitle = classtitle;
    }

    public String getClassurl() {
        return classurl;
    }

    public void setClassurl(String classurl) {
        this.classurl = classurl;
    }
}
