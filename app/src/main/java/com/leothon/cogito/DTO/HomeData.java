package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.Banner;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.Teacher;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class HomeData {

    public ArrayList<Banner> banners;
    public ArrayList<Teacher> teachers;
    public ArrayList<SelectClass> teaClasses;

    public ArrayList<Banner> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<Banner> banners) {
        this.banners = banners;
    }

    public ArrayList<SelectClass> getSelectClasses() {
        return teaClasses;
    }

    public void setSelectClasses(ArrayList<SelectClass> teaClasses) {
        this.teaClasses = teaClasses;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    @NonNull
    @Override
    public String toString() {
        return "banner" + banners + "selectclass" + teaClasses;
    }
}
