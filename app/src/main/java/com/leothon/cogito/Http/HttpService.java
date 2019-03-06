package com.leothon.cogito.Http;


import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.AuthInfo;
import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Bean.FeedbackInfo;
import com.leothon.cogito.Bean.NoticeInfo;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Bean.verify;
import com.leothon.cogito.DTO.ArticleData;
import com.leothon.cogito.DTO.BagPageData;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.HomeData;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.DTO.SearchResult;
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

    @GET("usepasswordlogin")
    Observable<BaseResponse<User>> usePasswordLogin(@Query("phonenumber") String phoneNumber,@Query("password") String password);
    @GET("gethomedata")
    Observable<BaseResponse<HomeData>> getHomeData(@Query("token") String token);

    @GET("getmoredata")
    Observable<BaseResponse<ArrayList<SelectClass>>> getMoreData(@Query("currentpage") int currentPage,@Query("token") String token);

    @GET("getuserinfo")
    Observable<BaseResponse<User>> getUserInfo(@Query("token") String token);
    @GET("getuserinfobyid")
    Observable<BaseResponse<User>> getUserInfoById(@Query("userid") String userId);


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
    Observable<BaseResponse<ArrayList<QAData>>> getQADataById(@Query("userid") String userId);

    @GET("getmorequestionbyid")
    Observable<BaseResponse<ArrayList<QAData>>> getMoreQADataById(@Query("currentpage") int currentPage,@Query("userid") String userId);

    @GET("getqadetail")
    Observable<BaseResponse<QADataDetail>> getQADetail(@Query("token") String token,@Query("qaid") String qaId);
    @GET("getqainfo")
    Observable<BaseResponse<QAData>> getQA(@Query("token") String token,@Query("qaid") String qaId);
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

    @GET("getclassbyclassid")
    Observable<BaseResponse<SelectClass>> getClassByClassId(@Query("classid") String classId);

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

    @GET("getarticledata")
    Observable<BaseResponse<ArticleData>> getArticleData(@Query("token") String token);
    @GET("getmorearticledata")
    Observable<BaseResponse<ArrayList<Article>>> getMoreArticleData(@Query("token") String token,@Query("currentpage") int currentPage);

    @GET("getarticledatabyid")
    Observable<BaseResponse<ArrayList<Article>>> getArticleDataById(@Query("userid") String userId);

    @GET("getmorearticledatabyid")
    Observable<BaseResponse<ArrayList<Article>>> getMoreArticleDataById(@Query("userid") String userId,@Query("currentpage") int currentPage);

    @GET("getarticledetail")
    Observable<BaseResponse<Article>> getArticleDetail(@Query("token") String token,@Query("articleid") String articleId);

    @POST("uploadarticle")
    Observable<BaseResponse<String>> uploadArticle(@Body Article Article);

    @POST("sendre")
    Observable<BaseResponse<String>> sendRe(@Query("token") String token,@Query("content") String content,@Query("qaid") String qaId);

    @POST("deleteqa")
    Observable<BaseResponse<String>> deleteQa(@Query("token") String token,@Query("qaid") String qaId);

    @POST("deletearticle")
    Observable<BaseResponse<String>> deleteArticle(@Query("token") String token,@Query("articleid") String articleId);

    @POST("visiblenotice")
    Observable<BaseResponse<String>> visibleNotice(@Query("token") String token,@Query("noticeid") String noticeId);

    @POST("visiblenoticeall")
    Observable<BaseResponse<String>> visibleNoticeAll(@Query("token") String token);

    @GET("ishasnotice")
    Observable<BaseResponse<String>> isHasNotice(@Query("token") String token);

    @GET("getnoticeinfo")
    Observable<BaseResponse<ArrayList<NoticeInfo>>> getNoticeInfo(@Query("token") String token);

    @GET("getauthinfo")
    Observable<BaseResponse<ArrayList<AuthInfo>>> getAuthInfo(@Query("token") String token);

    @POST("uploadauthinfo")
    Observable<BaseResponse<String>> uploadAuthInfo(@Query("token") String token,@Query("authimg") String authImg,@Query("authcontent") String authContent);

    @GET("isphoneexits")
    Observable<BaseResponse<String>> isPhoneExits(@Query("phone") String phoneNumber);

    @POST("bindphone")
    Observable<BaseResponse<String>> bindPhoneNumber(@Query("token") String token,@Query("phonenumber") String phoneNumber);


    @POST("setpassword")
    Observable<BaseResponse<String>> setPassword(@Query("token") String token,@Query("password") String password);

    @POST("changepassword")
    Observable<BaseResponse<String>> changePassword(@Query("token") String token,@Query("oldpassword") String oldPassword,@Query("password") String password);


    @POST("sendfeedback")
    Observable<BaseResponse<String>> sendFeedback(@Body FeedbackInfo feedbackInfo);

    @GET("searchresult")
    Observable<BaseResponse<SearchResult>> searchResult(@Query("keyword") String keyword,@Query("token") String token);


    @POST("createclass")
    Observable<BaseResponse<String>> createClass(@Body SelectClass selectClass);

    @POST("editclass")
    Observable<BaseResponse<String>> editClass(@Body SelectClass selectClass);

    @POST("uploadclass")
    Observable<BaseResponse<String>> uploadClassDetail(@Body ClassDetailList classDetailList);



    @GET("getclassbyuserid")
    Observable<BaseResponse<ArrayList<SelectClass>>> getClassByUserId(@Query("userid") String userId);


    @POST("deleteclass")
    Observable<BaseResponse<String>> deleteClass(@Query("classid") String classId,@Query("token") String token);

    @POST("deleteclassdetail")
    Observable<BaseResponse<String>> deleteClassDetail(@Query("classdid") String classdId);
}
