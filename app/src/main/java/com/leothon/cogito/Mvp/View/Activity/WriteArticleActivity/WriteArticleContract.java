package com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.DTO.VideoDetail;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleContract;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerContract;

public class WriteArticleContract {
    public interface IWriteArticleModel{

        void uploadImg(String path,OnWriteArticleFinishedListener listener);

        void uploadArticle(Article article, OnWriteArticleFinishedListener listener);
    }

    public interface IWriteArticleView{



        void isUploadSuccess(String info);
        void getUploadImgUrl(String url);
        void showInfo(String msg);

    }

    public interface OnWriteArticleFinishedListener {
        void isUploadSuccess(String info);
        void getUploadImgUrl(String url);
        void showInfo(String msg);

    }

    public interface IWriteArticlePresenter{
        void uploadArticleInfo(Article article);
        void onDestroy();
        void uploadSelectImg(String path);

    }
}
