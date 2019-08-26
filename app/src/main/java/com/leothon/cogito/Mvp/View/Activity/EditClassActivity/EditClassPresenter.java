package com.leothon.cogito.Mvp.View.Activity.EditClassActivity;

import com.leothon.cogito.Bean.SelectClass;

import java.util.ArrayList;

public class EditClassPresenter implements EditClassContract.OnEditClassFinishedListener,EditClassContract.IEditClassPresenter {

    private EditClassContract.IEditClassModel iEditClassModel;
    private EditClassContract.IEditClassView iEditClassView;

    public EditClassPresenter(EditClassContract.IEditClassView iEditClassView){
        this.iEditClassModel = new EditClassModel();
        this.iEditClassView = iEditClassView;
    }
    @Override
    public void loadClassSuccess(ArrayList<SelectClass> selectClasses) {
        if (iEditClassView != null){
            iEditClassView.loadClassSuccess(selectClasses);
        }
    }

    @Override
    public void loadMoreClassSuccess(ArrayList<SelectClass> selectClasses) {
        if (iEditClassView != null){
            iEditClassView.loadMoreClassSuccess(selectClasses);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (iEditClassView != null){
            iEditClassView.showMsg(msg);
        }

    }

    @Override
    public void deleteClassSuccess(String msg) {
        if (iEditClassView != null){
            iEditClassView.deleteClassSuccess(msg);
        }
    }

    @Override
    public void onDestroy() {
        iEditClassModel = null;
        iEditClassView = null;
    }

    @Override
    public void getSelectClassInfo(String userId) {
        iEditClassModel.getSelectClassInfo(userId,this);
    }

    @Override
    public void getMoreSelectClassInfo(String userId, int currentPage) {
        iEditClassModel.getMoreSelectClassInfo(userId,currentPage,this);
    }

    @Override
    public void deleteClass(String token, String classId) {
        iEditClassModel.deleteClass(token,classId,this);
    }
}
