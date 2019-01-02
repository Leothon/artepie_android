package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.Banner;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.Teacher;

import java.util.ArrayList;

public class HomeData {

    public ArrayList<Banner> banners;
    public ArrayList<Teacher> teachers;
    public ArrayList<SelectClass> selectClasses;

    public ArrayList<Banner> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<Banner> banners) {
        this.banners = banners;
    }

    public ArrayList<SelectClass> getSelectClasses() {
        return selectClasses;
    }

    public void setSelectClasses(ArrayList<SelectClass> selectClasses) {
        this.selectClasses = selectClasses;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

}
