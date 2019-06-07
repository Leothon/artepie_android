package com.leothon.cogito.Mvp.View.Activity.ArticleActivity;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.ArticleComment;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class ArticleModel implements ArticleContract.IArticleModel {
    @Override
    public void getArticleInfo(String articleId, String token, final ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getArticleDetail(token,articleId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        //listener.showInfo(errorMsg);

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Article article = (Article)baseResponse.getData();

                        listener.loadArticleData(article);
                    }
                });
    }

    @Override
    public void deleteArticle(String token, String articleId, final ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .deleteArticle(token,articleId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        //listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.deleteSuccess("删除成功");
                        }else {
                            listener.deleteSuccess("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void addLikeArticle(String token, String articleId,final ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .addLikeArticle(token,articleId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {

                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.addLikeSuccess("推荐成功");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void removeLikeArticle(String token, String articleId, final ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .removeLikeArticle(token,articleId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        //listener.showInfo(errorMsg);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.isSuccess()){
                            listener.removeLikeSuccess("取消推荐成功");
                        }else {
                            listener.showInfo("失败，请重试");
                        }
                    }
                });
    }

    @Override
    public void sendComment(String token, String articleId, String articleComment, ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .insertArticleComment(articleId,token,articleComment)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo("失败");
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.sendSuccess(baseResponse.getMsg());
                    }
                });
    }

    @Override
    public void getComment(String articleId, ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getArticleComment(articleId)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo("失败");
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.getCommentSuccess((ArrayList<ArticleComment>) baseResponse.getData());
                    }
                });
    }

    @Override
    public void getCommentMore(String articleId, int currentPage, ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getArticleCommentMore(articleId,currentPage)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo("失败");
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.getMoreCommentSuccess((ArrayList<ArticleComment>) baseResponse.getData());
                    }
                });
    }

    @Override
    public void replyArticleComment(String commentId, String token, String reply, ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .replyArticleComment(commentId,token,reply)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo("失败");
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.showInfo("回复成功");
                    }
                });
    }

    @Override
    public void deleteComment(String commentId, String token, ArticleContract.OnArticleFinishedListener listener) {
        RetrofitServiceManager.getInstance().create(HttpService.class)
                .deleteArticleComment(commentId,token)
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        listener.showInfo("失败");
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        listener.showInfo("删除成功");
                    }
                });
    }
}
