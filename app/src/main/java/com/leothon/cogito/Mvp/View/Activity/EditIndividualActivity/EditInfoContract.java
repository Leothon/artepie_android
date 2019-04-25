package com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity;

import com.leothon.cogito.Bean.User;

public class EditInfoContract {
    public interface IEditInfoModel{
        void uploadIcon(String path,OnEditInfoFinishedListener listener);

        void uploadAllInfo(User user,OnEditInfoFinishedListener listener);

        void checkPhoneNumberIsExits(String number,OnEditInfoFinishedListener listener);

        void bindPhone(String number,String token,OnEditInfoFinishedListener listener);

        void setPassword(String token,String password,OnEditInfoFinishedListener listener);
        void changePassword(String token,String oldPassword,String password,OnEditInfoFinishedListener listener);
    }

    public interface IEditInfoView{

        void getIconUrl(String url);

        void showMsg(String msg);

        void updateSuccess();

        void checkNumberResult(String msg);

        void bindPhoneNumberSuccess(String msg);
        void bindPhoneNumberFailed(String msg);


        void setPasswordSuccess(String msg);
        void setPasswordFailed(String msg);
    }

    public interface OnEditInfoFinishedListener {


        void getIconUrl(String url);
        void showMsg(String msg);

        void updateSucess();

        void checkNumberResult(String msg);
        void bindPhoneNumberSuccess(String msg);
        void setPasswordSuccess(String msg);
        void setPasswordFailed(String msg);
        void bindPhoneNumberFailed(String msg);
    }

    public interface IEditInfoPresenter{

        void onDestroy();
        void updateUserIcon(String path);

        void updateUserInfo(User user);
        void checkPhoneNumberIsExits(String number);
        void bindPhone(String number,String token);
        void setPassword(String token,String password);
        void changePassword(String token,String oldPassword,String password);
    }
}
