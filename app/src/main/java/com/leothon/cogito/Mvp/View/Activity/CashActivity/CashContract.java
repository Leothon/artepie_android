package com.leothon.cogito.Mvp.View.Activity.CashActivity;

public class CashContract {

    public interface ICashModel{

        void isHasPsd(String token,OnCashFinishedListener listener);
        void getMaxCash(String token,OnCashFinishedListener listener);
        void setPsd(String token,String psd,OnCashFinishedListener listener);
        void verifyPsd(String token,String psd,OnCashFinishedListener listener);
        void getCash(String info,OnCashFinishedListener listener);

    }

    public interface ICashView{

        void checkPsdResult(boolean isHasPsd);
        void maxCashResult(String msg);
        void setPsdSuccess(String msg);
        void verifyPsdSuccess(String msg);
        void getCashSuccess(String msg);
        void showMsg(String msg);


    }

    public interface OnCashFinishedListener {

        void checkPsdResult(boolean isHasPsd);
        void maxCashResult(String msg);
        void setPsdSuccess(String msg);
        void verifyPsdSuccess(String msg);
        void getCashSuccess(String msg);

        void showMsg(String msg);


    }

    public interface ICashPresenter{

        void isHasPsd(String token);
        void getMaxCash(String token);
        void setPsd(String token,String psd);
        void verifyPsd(String token,String psd);
        void getCash(String info);
        void onDestroy();



    }
}
