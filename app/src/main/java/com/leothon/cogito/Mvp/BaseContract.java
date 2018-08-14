package com.leothon.cogito.Mvp;


import android.support.annotation.NonNull;

/**
 * created by leothon on 2018.7.24
 * 封装接口的契约类
 */
public class BaseContract {

    public interface BaseIModel{}

    public interface BaseIPresenter{
        void start();

        void onDestory();

        void getData(boolean isRefresh);

        void getMoreData();
    }

    public interface BaseIView{
        /**
         * 显示加载
         */

        void showLoading();

        /**
         * 隐藏加载
         */

        void hideLoading();

        /**
         * 显示信息
         */

        void showMessage(@NonNull String message);

        /**
         * 绑定生命周期
         * 用于减少使用rxjava造成的内存泄漏
         * @param <T>
         * @return
         */


    }

}
