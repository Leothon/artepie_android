package com.leothon.cogito.Mvp.View.Fragment.BagPage;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.BagPageData;

import java.util.ArrayList;

public class BagContract {

    public interface IBagModel{

        void getBagData(String token,OnBagFinishedListener listener);
        void getMoreFineClassData(int currentPage,String token,OnBagFinishedListener listener);

    }

    public interface IBagView{

        void loadBagData(BagPageData bagPageData);
        void loadFineClassMoreData(ArrayList<SelectClass> selectClasses);
        void showInfo(String msg);
    }

    public interface OnBagFinishedListener {
        void loadBagData(BagPageData bagPageData);
        void loadFineClassMoreData(ArrayList<SelectClass> selectClasses);
        void showInfo(String msg);

    }

    public interface IBagPresenter{
        void onDestroy();
        void getBagData(String token);
        void getMoreFineClassData(int currentPage,String token);
    }
}
