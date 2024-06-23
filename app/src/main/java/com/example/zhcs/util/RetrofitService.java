package com.example.zhcs.util;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;


/*Retrofit将 Http请求 抽象成 Java接口：采用 注解 描述网络请求参数 和配置网络请求参数*/
public interface RetrofitService {

    /*@Header:请求头携带参数，参数名("Authorization),参数值String token,@Body请求正文,正文body*/
    @GET
    Call<ResponseBody> get(@Url String url, @Header("Authorization") String token);

    @DELETE
    Call<ResponseBody> delete(@Url String url, @Header("Authorization") String token);

    @POST
    Call<ResponseBody> post(@Url String url, @Header("Authorization") String token,@Body RequestBody body);

    @PUT
    Call<ResponseBody> put(@Url String url, @Header("Authorization") String token, @Body RequestBody body);
}



/*@GET
    Call<ResponseBody> get(@Url String url, @Header("Authorization") String token);

    @DELETE
    Call<ResponseBody> get(@Url String url, @Header("Authorization") String token);

    @POST
    Call<ResponseBody> get(@Url String url, @Header("Authorization") String token, @Body RequestBody body);

    @PUT
    Call<ResponseBody> get(@Url String url, @Header("Authorization") String token, @Body RequestBody body);*/



















