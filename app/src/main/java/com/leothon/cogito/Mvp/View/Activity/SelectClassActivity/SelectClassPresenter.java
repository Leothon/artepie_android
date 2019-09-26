package com.leothon.cogito.Mvp.View.Activity.SelectClassActivity;

import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.DTO.ClassDetail;

import java.util.ArrayList;

public class SelectClassPresenter implements SelectClassContract.ISelectClassPresenter,SelectClassContract.OnSelectClassFinishedListener {
    private SelectClassContract.ISelectClassView iSelectClassView;
    private SelectClassContract.ISelectClassModel iSelectClassModel;

    public SelectClassPresenter(SelectClassContract.ISelectClassView iSelectClassView){
        this.iSelectClassView = iSelectClassView;
        this.iSelectClassModel = new SelectClassModel();
    }
    @Override
    public void getClassDetailInfo(ClassDetail classDetail) {
        if (iSelectClassView != null){
            iSelectClassView.getClassDetailInfo(classDetail);
        }
    }

    @Override
    public void getMoreClassDetailInfo(ArrayList<ClassDetailList> classDetailLists) {
        if (iSelectClassView != null){
            iSelectClassView.getMoreClassDetailInfo(classDetailLists);
        }
    }


    @Override
    public void showInfo(String msg) {
        if (iSelectClassView != null){
            iSelectClassView.showInfo(msg);
        }

    }

    @Override
    public void deleteSuccess(String msg) {
        if (iSelectClassView != null){
            iSelectClassView.deleteSuccess(msg);
        }
    }

    @Override
    public void getClassDetail(String token, String classId) {
        iSelectClassModel.loadClassDetail(token,classId,this);
    }

    @Override
    public void loadMoreClassDetail(String token, String classId, int currentPage) {
        iSelectClassModel.loadMoreClassDetail(token,classId,currentPage,this);
    }


    @Override
    public void onDestroy() {
        iSelectClassModel = null;
        iSelectClassView = null;
    }

    @Override
    public void setfavClass(String token, String classId) {
        iSelectClassModel.favClass(token,classId,this);
    }

    @Override
    public void setunFavClass(String token, String classId) {
        iSelectClassModel.unFavClass(token,classId,this);
    }

    @Override
    public void deleteClassDetail(String classdId) {
        iSelectClassModel.deleteClassDetail(classdId,this);
    }
}
