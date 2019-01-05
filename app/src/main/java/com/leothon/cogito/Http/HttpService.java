package com.leothon.cogito.Http;


import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Bean.verify;
import com.leothon.cogito.DTO.HomeData;

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

    @GET("/login")
    Observable login(@Query("username") String username, @Query("password") String password);


    @GET("verifyphone")
    Observable<BaseResponse<verify>> verifyphone(@Query("phonenumber") String phonenumber);

    @GET("usephonelogin")
    Observable<BaseResponse<TokenInfo>> usePhoneLogin(@Query("phonenumber") String phonenumber, @Query("verifycode") String verfiCode);

    @GET("gethomedata")
    Observable<BaseResponse<HomeData>> getHomeData(@Query("token") String token);

    @GET("getmoredata")
    Observable<BaseResponse<ArrayList<SelectClass>>> getMoreData();

    @GET("getuserinfo")
    Observable<BaseResponse<User>> getUserInfo(@Query("token") String token);

    @Multipart
    @POST("uploadimg")
    Observable<BaseResponse<String>> updataFile(@Part MultipartBody.Part pic);

    @POST("updateuserinfo")
    Observable<BaseResponse<String>> updateUserInfo(@Body User user);


}
