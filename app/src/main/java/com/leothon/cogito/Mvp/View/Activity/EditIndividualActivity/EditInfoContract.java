package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import com.leothon.cogito.Bean.User;

public class EditInfoContract {
    public interface IEditInfoModel{
        void uploadIcon(String path,OnEditInfoFinishedListener listener);

        void uploadAllInfo(User user,OnEditInfoFinishedListener listener);
    }

    public interface IEditInfoView{

        void getIconUrl(String url);

        void showMsg(String msg);

        void updateSuccess();
    }

    public interface OnEditInfoFinishedListener {


        void getIconUrl(String url);
        void showMsg(String msg);

        void updateSucess();
    }

    public interface IEditInfoPresenter{

        void onDestory();
        void updateUserIcon(String path);

        void updateUserInfo(User user);
    }
}
