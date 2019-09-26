package com.leothon.cogito.Mvp.View.Activity.TestActivity;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.TypeClass;

import java.util.ArrayList;

public class TestContract {
    public interface ITestModel{

        void loadClassByType(String token,String type,OnTestFinishedListener listener);
        void loadMoreClassByType(String token,String type,int currentPage,OnTestFinishedListener listener);


    }

    public interface ITestView{


        void getTypeClass(TypeClass typeClass);
        void getMoreTypeClass(ArrayList<SelectClass> selectClasses);
        void showInfo(String msg);

    }

    public interface OnTestFinishedListener {

        void getTypeClass(TypeClass typeClass);
        void getMoreTypeClass(ArrayList<SelectClass> selectClasses);
        void showInfo(String msg);

    }

    public interface ITestPresenter{

        void loadTypeClass(String token,String type);
        void loadMoreClassByType(String token,String type,int currentPage);
        void onDestroy();



    }
}
