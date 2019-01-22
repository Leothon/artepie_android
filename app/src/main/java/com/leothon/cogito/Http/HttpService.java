package com.leothon.cogito.Http;


import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Bean.verify;
import com.leothon.cogito.DTO.BagPageData;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.HomeData;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.DTO.SendQAData;
import com.leothon.cogito.DTO.TeaClass;
import com.leothon.cogito.DTO.TypeClass;
import com.leothon.cogito.DTO.VideoDetail;

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
    Observable<BaseResponse<ArrayList<SelectClass>>> getMoreData(@Query("currentpage") int currentPage,@Query("token") String token);

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


    @GET("getquestionbyid")
    Observable<BaseResponse<ArrayList<QAData>>> getQADataById(@Query("token") String token);

    @GET("getmorequestionbyid")
    Observable<BaseResponse<ArrayList<QAData>>> getMoreQADataById(@Query("currentpage") int currentPage,@Query("token") String token);

    @GET("getqadetail")
    Observable<BaseResponse<QADataDetail>> getQADetail(@Query("token") String token,@Query("qaid") String qaId);

    @GET("getcommentdetail")
    Observable<BaseResponse<CommentDetail>> getCommentDetail(@Query("commentid") String commentId,@Query("token") String token);

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

    @POST("sendqacomment")
    Observable<BaseResponse<String>> sendQaComment(@Query("qaid") String qaId,@Query("token") String token,@Query("content") String content);
    @POST("sendreply")
    Observable<BaseResponse<String>> sendReply(@Query("commentid") String commentId,@Query("token") String token,@Query("touserid") String toUserId,@Query("content") String content);

    @POST("deleteqacomment")
    Observable<BaseResponse<String>> deleteQaComment(@Query("commentid") String commentId,@Query("token") String token);
    @POST("deletereply")
    Observable<BaseResponse<String>> deleteReply(@Query("replyId") String replyId,@Query("token") String token);

    @GET("getclassdetail")
    Observable<BaseResponse<ClassDetail>> getClassDetail(@Query("token") String token,@Query("classid") String classId);

    @POST("favclass")
    Observable<BaseResponse<String>> favClass(@Query("token") String token,@Query("classid") String classId);
    @POST("unfavclass")
    Observable<BaseResponse<String>> unFavClass(@Query("token") String token,@Query("classid") String classId);

    @GET("getclassvideo")
    Observable<BaseResponse<VideoDetail>> getClassVideo(@Query("token") String token,@Query("classdid") String classdId,@Query("classid") String classId);

    @POST("addvideoview")
    Observable<BaseResponse<String>> addVideoView(@Query("token") String token,@Query("classid") String classId,@Query("classdid") String classdId);

    @GET("getteaclass")
    Observable<BaseResponse<TeaClass>> getTeaClass(@Query("token") String token,@Query("teaid") String teaId);

    @GET("getclassbytype")
    Observable<BaseResponse<TypeClass>> getTypeClass(@Query("token") String token, @Query("type") String type);

    @GET("getbuyclassinfo")
    Observable<BaseResponse<SelectClass>> getBuyClassInfo(@Query("classid") String classId);

    @GET("isqqregister")
    Observable<BaseResponse<String>> isQQRegister(@Query("accesstoken") String accessToken);

    @POST("qquserregister")
    Observable<BaseResponse<User>> qqUserRegister(@Body User user);

    @GET("getuserinfobyqq")
    Observable<BaseResponse<User>> getUserInfoByQQ(@Query("accesstoken") String accessToken);

    @GET("getfavclassbyuid")
    Observable<BaseResponse<ArrayList<SelectClass>>> getFavClassByUid(@Query("token") String token);


    @GET("getclassviewhis")
    Observable<BaseResponse<ArrayList<ClassDetailList>>> getClassViewHis(@Query("token") String token);

    @POST("removeclassviewhis")
    Observable<BaseResponse<String>> removeClassViewHis(@Query("token") String token,@Query("classdid") String classdId);

    @GET("getbagpagedata")
    Observable<BaseResponse<BagPageData>> getBagPageData(@Query("token") String token);
}
