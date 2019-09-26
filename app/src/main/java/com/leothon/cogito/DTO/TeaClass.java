package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.User;

import java.util.ArrayList;

public class TeaClass {

    private User teacher;

    private ArrayList<SelectClass> teaClassses;

    public ArrayList<SelectClass> getTeaClassses() {
        return teaClassses;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeaClassses(ArrayList<SelectClass> teaClassses) {
        this.teaClassses = teaClassses;
    }
}
