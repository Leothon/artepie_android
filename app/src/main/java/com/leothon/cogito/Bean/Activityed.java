package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;
/**
 * created by leothon on 2018.8.8
 */
public class Activityed extends BaseResponse {

    private String classurl;
    private String classtitle;
    private String classdescription;
    private String classprice;

    public String getClassurl() {
        return classurl;
    }

    public void setClassurl(String classurl) {
        this.classurl = classurl;
    }

    public String getClasstitle() {
        return classtitle;
    }

    public void setClasstitle(String classtitle) {
        this.classtitle = classtitle;
    }

    public String getClassprice() {
        return classprice;
    }

    public void setClassprice(String classprice) {
        this.classprice = classprice;
    }

    public String getClassdescription() {
        return classdescription;
    }

    public void setClassdescription(String classdescription) {
        this.classdescription = classdescription;
    }
}
