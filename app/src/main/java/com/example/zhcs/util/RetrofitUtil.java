package com.example.zhcs.util;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 封装网络请求类
 */
public class RetrofitUtil {

    private static Context context;
    private static RetrofitService service;

    public static void init(Context context_back){

        context = context_back;
        //在这里设置了网络请求地址的域名，所以后面再传入url时就只需要传入域名后的路径了   /*Retrofit将 Http请求 抽象成 Java接口：采用 注解 描述网络请求参数 和配置网络请求参数*/
        service = new Retrofit.Builder().baseUrl(SPUtil.get(SPUtil.HTTP)).build().create(RetrofitService.class);
    }

    public interface OnRequest{
        void onRequest(String json);
    }

    /**
     * get请求的封装
     * @param url
     * @param onRequest
     */
    public static void get(final String url, final OnRequest onRequest){
        //这个service是一个设置了注解的接口，不明白工作流程，将存入共享参数存入的token取出来进行发送请求进行判断
        service.get(url, SPUtil.get(SPUtil.TOKEN)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    //string.replaceAll：字符串替换(这里替换是因为请求回来的图片地址前缀为/dev-api,需要转为/prod-api再进行图片获取)
                    string = string.replaceAll("/dev-api","/prod=api");

                    //这个对比url是查询好评率最高的律师列表
                    if (url.equals("/prod-api/api/lawyer-consultation/lawyer/list-top10")){
                        //替换data字符串第一次出现的位置为rows字符串 (请求返回回来的数据集合名字为data,将他转为rows好为后面json转换做适配)
                        string = string.replaceFirst("data","rows");
                    }

                    //在方法的参数中传递了这个接口OnRequest过来，这个是使用了他的方法，回调写在使用者那边，这边只负责将响应回来的数据解析完再传递过去
                    onRequest.onRequest(string);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            /*请求失败调用*/
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Delete请求的封装
     * @param url
     * @param onRequest
     */
    public static void delete(String url,final OnRequest onRequest){
        service.delete(url,SPUtil.get(SPUtil.TOKEN)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    onRequest.onRequest(response.body().string());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Post请求的封装
     * @param url
     * @param map
     * @param onRequest
     */
    public static void post(String url, Map map, final OnRequest onRequest){
        //构造一个RequestBody作为请求参数，第一个参数(MediaType)代表我们要发送的数据的媒体类型，第二个参数是一个Json格式的字符串，这里直接使用JSONObject进行转换 [好像好可以这样 JSONObject.fromObject（map）返回一个JSONObject对象]
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new JSONObject(map).toString());
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        /*这个create报错引完必须再引入okio的包*/
        RequestBody body = RequestBody.Companion.create(new JSONObject(map).toString(),mediaType);


        //service.post方法的返回值就是Call<ResponseBody>,所以直接可以链式.enqueue()
        //第一个为Post请求的地址，第二个参数为用户登录返回的token(从共享参数中获取),第三个参数为用户传递过来的登录信息(用户名,密码),已经封装成了一个ResponseBody发送
        service.post(url,SPUtil.get(SPUtil.TOKEN),body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    /*用户在使用这个Post方法时传入了一个onRequest对象，这个使用它传入的这个对象调用了onRequest方法*/
                    onRequest.onRequest(response.body().string());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Put请求的封装
     * @param url
     * @param map
     * @param onRequest
     */
    public static void put(String url,Map map,final OnRequest onRequest){
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"),new JSONObject(map).toString());
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        /*这个create报错引完必须再引入okio的包*/
        RequestBody body = RequestBody.Companion.create(new JSONObject(map).toString(),mediaType);

        service.put(url, SPUtil.get(SPUtil.TOKEN), body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    onRequest.onRequest(response.body().string());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(context,"网络连接失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}



























