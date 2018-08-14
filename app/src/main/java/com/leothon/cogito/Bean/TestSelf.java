package com.leothon.cogito.Bean;

import java.util.ArrayList;

/**
 * created by leothon on 2018.8.5
 * 艺考数据
 */
public class TestSelf {
    private String testtitle;
    private String testdescription;
    private ArrayList<ClassItem> testclasses;

    public ArrayList<ClassItem> getTestclasses() {
        return testclasses;
    }

    public void setTestclasses(ArrayList<ClassItem> testclasses) {
        this.testclasses = testclasses;
    }

    public String getTestdescription() {
        return testdescription;
    }

    public void setTestdescription(String testdescription) {
        this.testdescription = testdescription;
    }

    public String getTesttitle() {
        return testtitle;
    }

    public void setTesttitle(String testtitle) {
        this.testtitle = testtitle;
    }

}
