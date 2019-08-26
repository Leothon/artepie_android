package com.leothon.cogito.Mvp.View.Activity.EditClassActivity;

import com.leothon.cogito.Bean.SelectClass;

import java.util.ArrayList;

public class EditClassContract {
    public interface IEditClassModel{
        void getSelectClassInfo(String userId,OnEditClassFinishedListener listener);
        void getMoreSelectClassInfo(String userId,int currentPage,OnEditClassFinishedListener listener);

        void deleteClass(String token,String classId,OnEditClassFinishedListener listener);
    }

    public interface IEditClassView{
        void loadClassSuccess(ArrayList<SelectClass> selectClasses);

        void loadMoreClassSuccess(ArrayList<SelectClass> selectClasses);

        void showMsg(String msg);

        void deleteClassSuccess(String msg);
    }

    public interface OnEditClassFinishedListener {

        void loadClassSuccess(ArrayList<SelectClass> selectClasses);
        void loadMoreClassSuccess(ArrayList<SelectClass> selectClasses);
        void showMsg(String msg);
        void deleteClassSuccess(String msg);
    }

    public interface IEditClassPresenter{
        void onDestroy();
        void getSelectClassInfo(String userId);
        void getMoreSelectClassInfo(String userId,int currentPage);
        void deleteClass(String token,String classId);
    }
}
