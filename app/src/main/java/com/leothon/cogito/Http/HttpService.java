package com.leothon.cogito.Http;


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

}
