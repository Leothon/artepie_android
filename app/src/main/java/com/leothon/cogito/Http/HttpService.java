package com.leothon.cogito.Http;


import com.leothon.cogito.Bean.AlipayBean;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.ArticleComment;
import com.leothon.cogito.Bean.AuthInfo;
import com.leothon.cogito.Bean.Bill;
import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Bean.FeedbackInfo;
import com.leothon.cogito.Bean.NoticeInfo;
import com.leothon.cogito.Bean.OrderHis;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.ArticleData;
import com.leothon.cogito.DTO.BagPageData;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.HomeData;
import com.leothon.cogito.DTO.Inform;
import com.leothon.cogito.DTO.Orders;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.DTO.SearchResult;
import com.leothon.cogito.DTO.SendQAData;
import com.leothon.cogito.DTO.TeaClass;
import com.leothon.cogito.DTO.TypeClass;
import com.leothon.cogito.DTO.Update;
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


    /**
     * 查询该电话号码是否被注册过
     * @param phoneNumber
     * @return
     */
    @GET("isphoneexits")
    Observable<BaseResponse<String>> isPhoneExits(@Query("phone") String phoneNumber);



    /**
     * 使用手机号码登录
     * @param phonenumber
     * @return
     */
    @GET("usephonelogin")
    Observable<BaseResponse<User>> usePhoneLogin(@Query("phonenumber") String phonenumber);

    /**
     * 使用密码登录
     * @param phoneNumber
     * @param password
     * @return
     */
    @GET("usepasswordlogin")
    Observable<BaseResponse<User>> usePasswordLogin(@Query("phonenumber") String phoneNumber,@Query("password") String password);

    /**
     * 获取首页数据
     * @param token
     * @return
     */
    @GET("gethomedata")
    Observable<BaseResponse<HomeData>> getHomeData(@Query("token") String token);

    /**
     * 获取首页更多数据
     * @param currentPage
     * @param token
     * @return
     */
    @GET("getmoredata")
    Observable<BaseResponse<ArrayList<SelectClass>>> getMoreData(@Query("currentpage") int currentPage,@Query("token") String token);

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @GET("getuserinfo")
    Observable<BaseResponse<User>> getUserInfo(@Query("token") String token);

    /**
     * 通过用户ID获取用户信息
     * @param userId
     * @return
     */
    @GET("getuserinfobyid")
    Observable<BaseResponse<User>> getUserInfoById(@Query("userid") String userId);

    /**
     * 获取互动页面数据
     * @param token
     * @return
     */
    @GET("getquestion")
    Observable<BaseResponse<ArrayList<QAData>>> getQAData(@Query("token") String token);




    @GET("getinform")
    Observable<BaseResponse<Inform>> getInform(@Query("token") String token);


    @GET("getupdate")
    Observable<BaseResponse<Update>> getUpdate(@Query("token") String token);

    /**
     * 获取互动页面更多数据
     * @param currentPage
     * @param token
     * @return
     */
    @GET("getmorequestion")
    Observable<BaseResponse<ArrayList<QAData>>> getMoreQAData(@Query("currentpage") int currentPage,@Query("token") String token);

    /**
     * 获取某用户的问题
     * @param userId
     * @return
     */
    @GET("getquestionbyid")
    Observable<BaseResponse<ArrayList<QAData>>> getQADataById(@Query("userid") String userId);

    /**
     * 获取更多某用户的问题
     * @param currentPage
     * @param userId
     * @return
     */
    @GET("getmorequestionbyid")
    Observable<BaseResponse<ArrayList<QAData>>> getMoreQADataById(@Query("currentpage") int currentPage,@Query("userid") String userId);

    /**
     * 获取问题详细数据
     * @param token
     * @param qaId
     * @return
     */
    @GET("getqadetail")
    Observable<BaseResponse<QADataDetail>> getQADetail(@Query("token") String token,@Query("qaid") String qaId);

    /**
     * 根据问题ID获取问题数据
     * @param token
     * @param qaId
     * @return
     */
    @GET("getqainfo")
    Observable<BaseResponse<QAData>> getQA(@Query("token") String token,@Query("qaid") String qaId);

    /**
     * 通过评论ID获取评论详细数据
     * @param commentId
     * @param token
     * @return
     */
    @GET("getcommentdetail")
    Observable<BaseResponse<CommentDetail>> getCommentDetail(@Query("commentid") String commentId,@Query("token") String token);

    /**
     * 获取课程详细数据
     * @param token
     * @param classId
     * @return
     */
    @GET("getclassdetail")
    Observable<BaseResponse<ClassDetail>> getClassDetail(@Query("token") String token,@Query("classid") String classId);


    /**
     * 通过课程ID获取课程数据
     * @param classId
     * @return
     */
    @GET("getclassbyclassid")
    Observable<BaseResponse<SelectClass>> getClassByClassId(@Query("classid") String classId);

    /**
     * 获取单节课程详细数据
     * @param token
     * @param classdId
     * @param classId
     * @return
     */
    @GET("getclassvideo")
    Observable<BaseResponse<VideoDetail>> getClassVideo(@Query("token") String token,@Query("classdid") String classdId,@Query("classid") String classId);



    /**
     * 通过讲师ID获取讲师信息及课程数据
     * @param token
     * @param teaId
     * @return
     */
    @GET("getteaclass")
    Observable<BaseResponse<TeaClass>> getTeaClass(@Query("token") String token,@Query("teaid") String teaId);

    /**
     * 通过课程类型获取课程数据
     * @param token
     * @param type
     * @return
     */
    @GET("getclassbytype")
    Observable<BaseResponse<TypeClass>> getTypeClass(@Query("token") String token, @Query("type") String type);

    /**
     * 获取购买课程的数据
     * @param classId
     * @return
     */
    @GET("getbuyclassinfo")
    Observable<BaseResponse<SelectClass>> getBuyClassInfo(@Query("classid") String classId);

    /**
     * 查询是否使用QQ注册过
     * @param accessToken
     * @return
     */
    @GET("isqqwechatregister")
    Observable<BaseResponse<String>> isQQOrWeChatRegister(@Query("accesstoken") String accessToken);

    /**
     * 使用QQ的token获取到用户信息
     * @param accessToken
     * @return
     */
    @GET("getuserinfobyqq")
    Observable<BaseResponse<User>> getUserInfoByQQ(@Query("accesstoken") String accessToken);

    /**
     * 获取某用户收藏课程信息
     * @param token
     * @return
     */
    @GET("getfavclassbyuid")
    Observable<BaseResponse<ArrayList<SelectClass>>> getFavClassByUid(@Query("token") String token);

    /**
     * 获取某用户观看记录
     * @param token
     * @return
     */
    @GET("getclassviewhis")
    Observable<BaseResponse<ArrayList<ClassDetailList>>> getClassViewHis(@Query("token") String token);

    /**
     * 获取小书包页面所有信息
     * @param token
     * @return
     */
    @GET("getbagpagedata")
    Observable<BaseResponse<BagPageData>> getBagPageData(@Query("token") String token);

    /**
     * 获取文章页面所有信息
     * @param token
     * @return
     */
    @GET("getarticledata")
    Observable<BaseResponse<ArticleData>> getArticleData(@Query("token") String token);

    /**
     * 获取文章页面更多信息
     * @param token
     * @param currentPage
     * @return
     */
    @GET("getmorearticledata")
    Observable<BaseResponse<ArrayList<Article>>> getMoreArticleData(@Query("token") String token,@Query("currentpage") int currentPage);

    /**
     * 获取某用户发表的文章信息
     * @param userId
     * @return
     */
    @GET("getarticledatabyid")
    Observable<BaseResponse<ArrayList<Article>>> getArticleDataById(@Query("userid") String userId);

    /**
     * 获取某用户发表的更多文章信息
     * @param userId
     * @param currentPage
     * @return
     */
    @GET("getmorearticledatabyid")
    Observable<BaseResponse<ArrayList<Article>>> getMoreArticleDataById(@Query("userid") String userId,@Query("currentpage") int currentPage);

    /**
     * 获取某文章详细信息
     * @param token
     * @param articleId
     * @return
     */
    @GET("getarticledetail")
    Observable<BaseResponse<Article>> getArticleDetail(@Query("token") String token,@Query("articleid") String articleId);

    /**
     * 查询该用户是否有新的消息
     * @param token
     * @return
     */
    @GET("ishasnotice")
    Observable<BaseResponse<String>> isHasNotice(@Query("token") String token);

    /**
     * 获取该用户的消息信息
     * @param token
     * @return
     */
    @GET("getnoticeinfo")
    Observable<BaseResponse<ArrayList<NoticeInfo>>> getNoticeInfo(@Query("token") String token);

    /**
     * 获取该用户的认证信息
     * @param token
     * @return
     */
    @GET("getauthinfo")
    Observable<BaseResponse<ArrayList<AuthInfo>>> getAuthInfo(@Query("token") String token);

    /**
     * 获取该用户发布的课程
     * @param userId
     * @return
     */
    @GET("getclassbyuserid")
    Observable<BaseResponse<ArrayList<SelectClass>>> getClassByUserId(@Query("userid") String userId);

    /**
     * 获取关键词搜索的结果
     * @param keyword
     * @param token
     * @return
     */
    @GET("searchresult")
    Observable<BaseResponse<SearchResult>> searchResult(@Query("keyword") String keyword,@Query("token") String token);


    /**
     * 获取文章留言
     * @param articleId
     * @return
     */
    @GET("getarticlecomment")
    Observable<BaseResponse<ArrayList<ArticleComment>>> getArticleComment(@Query("articleid") String articleId);

    /**
     * 分页获取文章留言
     * @param articleId
     * @param currentPage
     * @return
     */
    @GET("getarticlecommentmore")
    Observable<BaseResponse<ArrayList<ArticleComment>>> getArticleCommentMore(@Query("articleid") String articleId,@Query("currentPage") int currentPage);


    /**
     * 获取个人账单
     * @param token
     * @return
     */
    @GET("getbills")
    Observable<BaseResponse<ArrayList<Bill>>> getBill(@Query("token") String token);

    /**
     * 获取个人订单
     * @param token
     * @return
     */
    @GET("getorderhis")
    Observable<BaseResponse<ArrayList<OrderHis>>> getOrder(@Query("token") String token);

    /**
     * 获取购买的课程
     * @param token
     * @return
     */

    @GET("getbuyclass")
    Observable<BaseResponse<ArrayList<SelectClass>>> getBuyClass(@Query("token") String token);
    /**
     * 支付宝同步验单
     * @param orderInfo
     * @return
     */
    @GET("alipaynotifyend")
    Observable<BaseResponse<String>> alipayEndNotify(@Query("orderinfo") String orderInfo);

    /**
     * 创建订单
     * @param orders
     * @return
     */
    @POST("createorder")
    Observable<BaseResponse<Orders>> createOrder(@Body Orders orders);


    /**
     * 分享得艺币
     * @param token
     * @param artcoin
     * @return
     */
    @POST("addcoin")
    Observable<BaseResponse<String>> addCoin(@Query("token") String token,@Query("artcoin") String artcoin);
    /**
     * 拉取预付单
     * @param orders
     * @return
     */
    @POST("createtransaction")
    Observable<BaseResponse<Orders>> createTransaction(@Body Orders orders);



    /**
     * 验证支付宝订单
     * @param alipayBean
     * @return
     */
    @POST("verifyalipaytransaction")
    Observable<BaseResponse<String>> verifyAlipayTransaction(@Body AlipayBean alipayBean);





    /**
     * 留言
     * @param artComArtId
     * @param token
     * @param artCom
     * @return
     */
    @POST("insertarticlecomment")
    Observable<BaseResponse<String>> insertArticleComment(@Query("artcomartid") String artComArtId,@Query("token") String token,@Query("artcom") String artCom);

    /**
     * 作者回复留言
     * @param artComId
     * @param token
     * @param artComReply
     * @return
     */
    @POST("replyarticlecomment")
    Observable<BaseResponse<String>> replyArticleComment(@Query("artcomid") String artComId,@Query("token") String token,@Query("artcomreply") String artComReply);

    /**
     * 删除留言
     * @param artComId
     * @param token
     * @return
     */
    @POST("deletearticlecomment")
    Observable<BaseResponse<String>> deleteArticleComment(@Query("artcomid") String artComId,@Query("token") String token);

    /**
     * 作者删除对留言的回复
     * @param artComId
     * @param token
     * @return
     */
    @POST("deletereplyarticlecomment")
    Observable<BaseResponse<String>> deleteReplyArticleComment(@Query("artcomid") String artComId,@Query("token") String token);

    /**
     * 赞同留言
     * @param token
     * @param artCommentId
     * @return
     */
    @POST("likeariclecomment")
    Observable<BaseResponse<String>> likeArticleComment(@Query("token") String token,@Query("artcommentid") String artCommentId);

    /**
     * 取消赞同留言
     * @param artCommentId
     * @param token
     * @return
     */
    @POST("removelikearticlecomment")
    Observable<BaseResponse<String>> removeLikeArticleComment(@Query("artcommentid") String artCommentId,@Query("token") String token);

    /**
     * 设置初始密码
     * @param token
     * @param password
     * @return
     */
    @POST("setpassword")
    Observable<BaseResponse<String>> setPassword(@Query("token") String token,@Query("password") String password);


    /**
     * 增加阅读量
     * @param token
     * @param qaId
     * @return
     */
    @POST("addqaview")
    Observable<BaseResponse<String>> addQaView(@Query("token") String token,@Query("qaid") String qaId);

    /**
     * 修改已设置的密码
     * @param token
     * @param oldPassword
     * @param password
     * @return
     */
    @POST("changepassword")
    Observable<BaseResponse<String>> changePassword(@Query("token") String token,@Query("oldpassword") String oldPassword,@Query("password") String password);

    /**
     * 发送反馈信息
     * @param feedbackInfo
     * @return
     */
    @POST("sendfeedback")
    Observable<BaseResponse<String>> sendFeedback(@Body FeedbackInfo feedbackInfo);

    /**
     * 创建新的课程
     * @param selectClass
     * @return
     */
    @POST("createclass")
    Observable<BaseResponse<String>> createClass(@Body SelectClass selectClass);

    /**
     * 编辑已有的课程
     * @param selectClass
     * @return
     */
    @POST("editclass")
    Observable<BaseResponse<String>> editClass(@Body SelectClass selectClass);

    /**
     * 上传课程的内容
     * @param classDetailList
     * @return
     */
    @POST("uploadclass")
    Observable<BaseResponse<String>> uploadClassDetail(@Body ClassDetailList classDetailList);

    /**
     * 删除已创建的课程
     * @param classId
     * @param token
     * @return
     */
    @POST("deleteclass")
    Observable<BaseResponse<String>> deleteClass(@Query("classid") String classId,@Query("token") String token);

    /**
     * 删除已上传的课程的内容
     * @param classdId
     * @return
     */
    @POST("deleteclassdetail")
    Observable<BaseResponse<String>> deleteClassDetail(@Query("classdid") String classdId);

    /**
     * 上传文件（图片，视频，文档等）
     * @param file
     * @return
     */
    @Multipart
    @POST("uploadfile")
    Observable<BaseResponse<String>> updataFile(@Part MultipartBody.Part file);

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @POST("updateuserinfo")
    Observable<BaseResponse<String>> updateUserInfo(@Body User user);

    /**
     * 发送问题信息
     * @param sendQAData
     * @return
     */
    @POST("sendqadata")
    Observable<BaseResponse<String>> sendQAData(@Body SendQAData sendQAData);

    /**
     * 给问题点赞
     * @param token
     * @param qaId
     * @return
     */
    @POST("addlikeqa")
    Observable<BaseResponse<String>> addLikeQa(@Query("token") String token,@Query("qaid") String qaId);

    /**
     * 移除问题的点赞
     * @param token
     * @param qaId
     * @return
     */
    @POST("removelikeqa")
    Observable<BaseResponse<String>> removeLikeQa(@Query("token") String token,@Query("qaid") String qaId);

    /**
     * 给评论点赞
     * @param token
     * @param commentId
     * @return
     */
    @POST("addlikecomment")
    Observable<BaseResponse<String>> addLikeComment(@Query("token") String token,@Query("commentid") String commentId);

    /**
     * 移除评论的点赞
     * @param token
     * @param commentId
     * @return
     */
    @POST("removelikecomment")
    Observable<BaseResponse<String>> removeLikeComment(@Query("token") String token,@Query("commentid") String commentId);

    /**
     * 给回复点赞
     * @param token
     * @param replyId
     * @return
     */
    @POST("addlikereply")
    Observable<BaseResponse<String>> addLikeReply(@Query("token") String token,@Query("replyid") String replyId);

    /**
     * 移除回复的点赞
     * @param token
     * @param replyId
     * @return
     */
    @POST("removelikereply")
    Observable<BaseResponse<String>> removeLikeReply(@Query("token") String token,@Query("replyid") String replyId);

    /**
     * 对问题进行评论
     * @param qaId
     * @param token
     * @param content
     * @return
     */
    @POST("sendqacomment")
    Observable<BaseResponse<String>> sendQaComment(@Query("qaid") String qaId,@Query("token") String token,@Query("content") String content);

    /**
     * 对评论或者回复进行回复
     * @param commentId
     * @param token
     * @param toUserId
     * @param content
     * @return
     */
    @POST("sendreply")
    Observable<BaseResponse<String>> sendReply(@Query("commentid") String commentId,@Query("token") String token,@Query("touserid") String toUserId,@Query("content") String content);

    /**
     * 删除课程观看记录
     * @param token
     * @param classdId
     * @return
     */
    @POST("removeclassviewhis")
    Observable<BaseResponse<String>> removeClassViewHis(@Query("token") String token,@Query("classdid") String classdId);

    /**
     * 删除问题评论
     * @param commentId
     * @param token
     * @return
     */
    @POST("deleteqacomment")
    Observable<BaseResponse<String>> deleteQaComment(@Query("commentid") String commentId,@Query("token") String token);

    /**
     * 删除回复
     * @param replyId
     * @param token
     * @return
     */
    @POST("deletereply")
    Observable<BaseResponse<String>> deleteReply(@Query("replyId") String replyId,@Query("token") String token);

    /**
     * 收藏某节课程
     * @param token
     * @param classId
     * @return
     */
    @POST("favclass")
    Observable<BaseResponse<String>> favClass(@Query("token") String token,@Query("classid") String classId);

    /**
     * 取消收藏某节课程
     * @param token
     * @param classId
     * @return
     */
    @POST("unfavclass")
    Observable<BaseResponse<String>> unFavClass(@Query("token") String token,@Query("classid") String classId);

    /**
     * QQ新用户注册
     * @param user
     * @return
     */
    @POST("qquserregister")
    Observable<BaseResponse<User>> qqUserRegister(@Body User user);


    /**
     * 微信新用户注册
     * @param user
     * @return
     */
    @POST("wechatuserregister")
    Observable<BaseResponse<User>> weChatUserRegister(@Body User user);
    /**
     * 记录某视频点击量
     * @param token
     * @param classId
     * @param classdId
     * @return
     */
    @POST("addvideoview")
    Observable<BaseResponse<String>> addVideoView(@Query("token") String token,@Query("classid") String classId,@Query("classdid") String classdId);

    /**
     * 发布文章
     * @param Article
     * @return
     */
    @POST("uploadarticle")
    Observable<BaseResponse<String>> uploadArticle(@Body Article Article);

    /**
     * 转发问题
     * @param token
     * @param content
     * @param qaId
     * @return
     */
    @POST("sendre")
    Observable<BaseResponse<String>> sendRe(@Query("token") String token,@Query("content") String content,@Query("qaid") String qaId);

    /**
     * 删除问题
     * @param token
     * @param qaId
     * @return
     */
    @POST("deleteqa")
    Observable<BaseResponse<String>> deleteQa(@Query("token") String token,@Query("qaid") String qaId);

    /**
     * 删除文章
     * @param token
     * @param articleId
     * @return
     */
    @POST("deletearticle")
    Observable<BaseResponse<String>> deleteArticle(@Query("token") String token,@Query("articleid") String articleId);

    /**
     * 查阅已读信息
     * @param token
     * @param noticeId
     * @return
     */
    @POST("visiblenotice")
    Observable<BaseResponse<String>> visibleNotice(@Query("token") String token,@Query("noticeid") String noticeId);

    /**
     * 查阅已读所有信息
     * @param token
     * @return
     */
    @POST("visiblenoticeall")
    Observable<BaseResponse<String>> visibleNoticeAll(@Query("token") String token);

    /**
     * 上传个人认证信息
     * @param token
     * @param authImg
     * @param authContent
     * @return
     */
    @POST("uploadauthinfo")
    Observable<BaseResponse<String>> uploadAuthInfo(@Query("token") String token,@Query("authimg") String authImg,@Query("authcontent") String authContent);

    /**
     * 绑定或者换绑手机号码
     * @param token
     * @param phoneNumber
     * @return
     */
    @POST("bindphone")
    Observable<BaseResponse<String>> bindPhoneNumber(@Query("token") String token,@Query("phonenumber") String phoneNumber);


    /**
     * 推荐文章
     * @param token
     * @param articleId
     * @return
     */
    @POST("addlikearticle")
    Observable<BaseResponse<String>> addLikeArticle(@Query("token") String token,@Query("articleid") String articleId);

    /**
     * 取消推荐
     * @param token
     * @param articleId
     * @return
     */
    @POST("removelikearticle")
    Observable<BaseResponse<String>> removeLikeArticle(@Query("token") String token,@Query("articleid") String articleId);

}
