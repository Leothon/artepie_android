package com.leothon.cogito.Mvp.View.Activity.TeacherActivity;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.TeaClass;

import java.util.ArrayList;

public class TeacherContract {
    public interface ITeacherModel{

        void loadClassByTeacher(String token,String teaId,OnTeacherFinishedListener listener);

        void loadMoreClassByTeacher(String token,String teaId,int currentPage,OnTeacherFinishedListener listener);
    }

    public interface ITeacherView{


        void getTeacherClass(TeaClass teaClass);
        void getMoreTeacherClass(ArrayList<SelectClass> selectClasses);

        void showInfo(String msg);

    }

    public interface OnTeacherFinishedListener {

        void getTeacherClass(TeaClass teaClass);
        void getMoreTeacherClass(ArrayList<SelectClass> selectClasses);
        void showInfo(String msg);

    }

    public interface ITeacherPresenter{

        void loadTeaClass(String token,String teaId);
        void loadMoreClassByTeacher(String token,String teaId,int currentPage);
        void onDestroy();



    }
}
