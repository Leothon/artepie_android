package com.leothon.cogito.Mvp.View.Activity.TestActivity;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.TypeClass;

import java.util.ArrayList;

public class TestPresenter implements TestContract.OnTestFinishedListener,TestContract.ITestPresenter {
    private TestContract.ITestView iTestView;
    private TestContract.ITestModel iTestModel;

    public TestPresenter(TestContract.ITestView iTestView){
        this.iTestView = iTestView;
        this.iTestModel = new TestModel();
    }
    @Override
    public void getTypeClass(TypeClass typeClass) {
        if (iTestView != null){
            iTestView.getTypeClass(typeClass);
        }
    }

    @Override
    public void getMoreTypeClass(ArrayList<SelectClass> selectClasses) {
        if (iTestView != null){
            iTestView.getMoreTypeClass(selectClasses);
        }
    }

    @Override
    public void showInfo(String msg) {
        if (iTestView != null){
            iTestView.showInfo(msg);
        }
    }

    @Override
    public void loadTypeClass(String token, String type) {
        iTestModel.loadClassByType(token,type,this);
    }

    @Override
    public void loadMoreClassByType(String token, String type, int currentPage) {
        iTestModel.loadMoreClassByType(token,type,currentPage,this);
    }

    @Override
    public void onDestroy() {
        iTestView = null;
        iTestModel = null;
    }
}
