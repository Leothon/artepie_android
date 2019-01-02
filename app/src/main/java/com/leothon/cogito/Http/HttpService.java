package com.leothon.cogito.Http;


import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.TokenInfo;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Bean.verify;
import com.leothon.cogito.DTO.HomeData;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
    Observable<BaseResponse<ArrayList<SelectClass>>> getMoreData(@Query("token") String token);

}
