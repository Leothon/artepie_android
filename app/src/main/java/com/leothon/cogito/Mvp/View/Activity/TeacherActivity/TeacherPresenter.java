package com.leothon.cogito.Mvp.View.Activity.TeacherActivity;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.TeaClass;

import java.util.ArrayList;

public class TeacherPresenter implements TeacherContract.ITeacherPresenter,TeacherContract.OnTeacherFinishedListener {

    private TeacherContract.ITeacherView iTeacherView;
    private TeacherContract.ITeacherModel iTeacherModel;

    public TeacherPresenter(TeacherContract.ITeacherView iTeacherView){
        this.iTeacherView = iTeacherView;
        this.iTeacherModel = new TeacherModel();
    }
    @Override
    public void getTeacherClass(TeaClass teaClass) {
        if (iTeacherView != null){
            iTeacherView.getTeacherClass(teaClass);
        }
    }

    @Override
    public void getMoreTeacherClass(ArrayList<SelectClass> selectClasses) {
        if (iTeacherView != null){
            iTeacherView.getMoreTeacherClass(selectClasses);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iTeacherView != null){
            iTeacherView.showInfo(msg);
        }
    }

    @Override
    public void loadTeaClass(String token, String teaId) {
        iTeacherModel.loadClassByTeacher(token,teaId,this);
    }

    @Override
    public void loadMoreClassByTeacher(String token, String teaId, int currentPage) {
        iTeacherModel.loadMoreClassByTeacher(token,teaId,currentPage,this);
    }

    @Override
    public void onDestroy() {
        iTeacherView = null;
        iTeacherModel = null;
    }
}
