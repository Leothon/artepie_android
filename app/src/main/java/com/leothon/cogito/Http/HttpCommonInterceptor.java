package com.leothon.cogito.Http;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created by leothon on 2018.7.23
 */
public class HttpCommonInterceptor implements Interceptor {

    private Map<String,String> mHeaderParamsMap = new HashMap<>();
    public HttpCommonInterceptor(){ }
    private static final String TAG = "HttpCommonIntercept";
    public Response intercept(Chain chain) throws IOException{
        Log.d(TAG, "add common params");
        Request oldrequest = chain.request();

        Request.Builder requestBuilder = oldrequest.newBuilder();
        requestBuilder.method(oldrequest.method(),oldrequest.body());

        if (mHeaderParamsMap.size() > 0){
            for(Map.Entry<String,String> params:mHeaderParamsMap.entrySet()){
                requestBuilder.header(params.getKey(),params.getValue());
            }
        }
        Request newrequest = requestBuilder.build();
        return chain.proceed(newrequest);
    }

    public static class Builder{
        HttpCommonInterceptor mHttpCommonInterceptor;
        public Builder(){
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key,String value){
            mHttpCommonInterceptor.mHeaderParamsMap.put(key,value);
            return this;
        }

        public Builder addHeaderParams(String key, int value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value){
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value){
            return addHeaderParams(key, String.valueOf(value));
        }
        
        public HttpCommonInterceptor build(){
            return mHttpCommonInterceptor;
        }






    }
}
