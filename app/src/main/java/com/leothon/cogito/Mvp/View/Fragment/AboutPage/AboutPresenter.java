package com.leothon.cogito.Mvp.View.Fragment.AboutPage;

import com.leothon.cogito.Bean.User;

public class AboutPresenter implements AboutFragmentContract.IAboutPresenter,AboutFragmentContract.OnAboutFinishedListener {

    private AboutFragmentContract.IAboutView iAboutView;
    private AboutFragmentContract.IAboutModel iAboutModel;

    public AboutPresenter(AboutFragmentContract.IAboutView iAboutView){
        this.iAboutView = iAboutView;
        this.iAboutModel = new AboutModel();
    }

    @Override
    public void getUserInfo(User user) {
        if (iAboutView != null){
            iAboutView.getUserInfo(user);
        }
    }


    @Override
    public void onDestroy() {
        iAboutView = null;
        iAboutModel = null;
    }

    @Override
    public void loadUserAll(String token) {
        iAboutModel.loadUserInfo(token,this);
    }






    @Override
    public void showInfo(String msg) {
        if (iAboutView != null){
            iAboutView.showInfo(msg);
        }
    }




}
