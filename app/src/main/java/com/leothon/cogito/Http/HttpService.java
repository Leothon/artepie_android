package com.leothon.cogito.Http;


import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Bean.verify;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.HomeData;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.DTO.SendQAData;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * created by leothon on 2018.7.23
 * 定义HTTP访问中的接口管理
 */
public interface HttpService {

//    @GET("/login")
//    Observable login(@Query("username") String username, @Query("password") String password);


    @GET("verifyphone")
    Observable<BaseResponse<verify>> verifyphone(@Query("phonenumber") String phonenumber);

    @GET("usephonelogin")
    Observable<BaseResponse<User>> usePhoneLogin(@Query("phonenumber") String phonenumber, @Query("verifycode") String verfiCode);

    @GET("gethomedata")
    Observable<BaseResponse<HomeData>> getHomeData(@Query("token") String token);

    @GET("getmoredata")
    Observable<BaseResponse<ArrayList<SelectClass>>> getMoreData(@Query("currentpage") int currentPage);

    @GET("getuserinfo")
    Observable<BaseResponse<User>> getUserInfo(@Query("token") String token);

    @Multipart
    @POST("uploadfile")
    Observable<BaseResponse<String>> updataFile(@Part MultipartBody.Part file);

    @POST("updateuserinfo")
    Observable<BaseResponse<String>> updateUserInfo(@Body User user);



    @POST("sendqadata")
    Observable<BaseResponse<String>> sendQAData(@Body SendQAData sendQAData);

    @GET("getquestion")
    Observable<BaseResponse<ArrayList<QAData>>> getQAData(@Query("token") String token);

    @GET("getmorequestion")
    Observable<BaseResponse<ArrayList<QAData>>> getMoreQAData(@Query("currentpage") int currentPage,@Query("token") String token);


    @GET("getqadetail")
    Observable<BaseResponse<QADataDetail>> getQADetail(@Query("token") String token,@Query("qaid") String qaId);

    @GET("getcommentdetail")
    Observable<BaseResponse<CommentDetail>> getCommentDetail(@Query("commentid") String commentId);

    @POST("addlikeqa")
    Observable<BaseResponse<String>> addLikeQa(@Query("token") String token,@Query("qaid") String qaId);
    @POST("removelikeqa")
    Observable<BaseResponse<String>> removeLikeQa(@Query("token") String token,@Query("qaid") String qaId);

    @POST("addlikecomment")
    Observable<BaseResponse<String>> addLikeComment(@Query("token") String token,@Query("commentid") String commentId);
    @POST("removelikecomment")
    Observable<BaseResponse<String>> removeLikeComment(@Query("token") String token,@Query("commentid") String commentId);

    @POST("addlikereply")
    Observable<BaseResponse<String>> addLikeReply(@Query("token") String token,@Query("replyid") String replyId);
    @POST("removelikereply")
    Observable<BaseResponse<String>> removeLikeReply(@Query("token") String token,@Query("replyid") String replyId);

}
