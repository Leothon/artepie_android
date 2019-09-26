package com.leothon.cogito.Mvp.View.Activity.SelectClassActivity;

import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.ClassDetail;

import java.util.ArrayList;

public class SelectClassContract {
    public interface ISelectClassModel{

        void loadClassDetail(String token,String classId,OnSelectClassFinishedListener listener);

        void loadMoreClassDetail(String token,String classId,int currentPage,OnSelectClassFinishedListener listener);

        void favClass(String token,String classId,OnSelectClassFinishedListener listener);
        void unFavClass(String token,String classId,OnSelectClassFinishedListener listener);

        void deleteClassDetail(String classdId,OnSelectClassFinishedListener listener);
    }

    public interface ISelectClassView{

        void getClassDetailInfo(ClassDetail classDetail);

        void getMoreClassDetailInfo(ArrayList<ClassDetailList> classDetailLists);

        void showInfo(String msg);

        void deleteSuccess(String msg);

    }

    public interface OnSelectClassFinishedListener {

        void getClassDetailInfo(ClassDetail classDetail);
        void getMoreClassDetailInfo(ArrayList<ClassDetailList> classDetailLists);
        void showInfo(String msg);
        void deleteSuccess(String msg);

    }

    public interface ISelectClassPresenter{

        void getClassDetail(String token,String classId);
        void loadMoreClassDetail(String token,String classId,int currentPage);

        void onDestroy();

        void setfavClass(String token,String classId);
        void setunFavClass(String token,String classId);
        void deleteClassDetail(String classdId);

    }
}
