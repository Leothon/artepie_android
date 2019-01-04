package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

public class EditInfoContract {
    public interface IEditInfoModel{
        void uploadIcon(String path,OnEditInfoFinishedListener listener);

        void uploadAllInfo(String icon,String name,int sex,String birth,String phone,String signal,String address,OnEditInfoFinishedListener listener);
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

        void updateUserInfo(String icon,String name,int sex,String birth,String phone,String signal,String address);
    }
}
