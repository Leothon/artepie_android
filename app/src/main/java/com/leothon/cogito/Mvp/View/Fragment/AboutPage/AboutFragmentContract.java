package com.leothon.cogito.Mvp.View.Fragment.AboutPage;

import com.leothon.cogito.Bean.User;

public class AboutFragmentContract {
    public interface IAboutModel{

        void loadUserInfo(String token,OnAboutFinishedListener listener);
    }

    public interface IAboutView{



        void getUserInfo(User user);
        void showInfo(String msg);

    }

    public interface OnAboutFinishedListener {

        void getUserInfo(User user);
        void showInfo(String msg);

    }

    public interface IAboutPresenter{
        void onDestroy();
        void loadUserAll(String token);
    }
}
