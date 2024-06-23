package com.example.zhcs.guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhcs.MainActivity;
import com.example.zhcs.R;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActicity extends AppCompatActivity {

    private EditText nameEt;
    private EditText pwdEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acticity);
        //用户名输入框
        nameEt = findViewById(R.id.name_et);
        /*密码输入框*/
        pwdEt = findViewById(R.id.pwd_et);

        //初始化先判断用户是否登录过了存入了token SPUtil.get(SPUtil.TOKEN)：从共享参数中取出token
        if (SPUtil.get(SPUtil.TOKEN) != null){
            //RetrofitUtil.OnRequest()：是封装好的统一请求接口  /prod-api/api/common/user/getInfo(查询用户个人信息的接口)
            //调用GET请求，第二个参数token是是在get方法那边从共享参数获取放入的，用户已经自己填入了地址前面的IP地址，这个只需要路径就好了，因为地址都是统一的，但是路径不是    new RetrofitUtil.OnRequest()是在网络请求类中定义的接口
            RetrofitUtil.get("prod-api/api/common/user/getInfo", new RetrofitUtil.OnRequest() {

                /*直接使用返回回来的json字符串(它帮你转换好了)就行了*/
                @Override
                public void onRequest(String json) {

                    try {
                        /*将传入的json字符串转换为JSONObject对象*/
                        JSONObject obj = new JSONObject(json);
                        if (obj.getInt("code") == 200){

                            Toast.makeText(LoginActicity.this,"已登录",Toast.LENGTH_SHORT).show();

                            /*直接进入主页*/
                            startActivity(new Intent(LoginActicity.this,MainActivity.class));

                            finish();

                        }else {
                            //若是返回状态不为200,则弹出框输出后端返回的提错误示信息
                            Toast.makeText(LoginActicity.this,"" + obj.getString("msg"),Toast.LENGTH_SHORT).show();
                            //将共享参数的token设置为null
                            SPUtil.put(SPUtil.TOKEN,null);
                        }

                    } catch (JSONException e){
                        e.printStackTrace();
                    }


                }
            });
        }
    }


    /*登录按钮的点击事件*/
    public void login(View view) {
        /*定义一个Map集合*/
        final HashMap<Object,Object> map = new HashMap<>();

        /*将输入的用户名和密码放入Map集合中*/
        map.put("username",nameEt.getText().toString());
        map.put("password",pwdEt.getText().toString());


//        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
//        RequestBody requestBody = RequestBody.Companion.create(new JSONObject(map).toString(),mediaType);
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        /*登录的时候不能带上token值！！！,否则会报错*/
//        Request request = new Request.Builder()
//                .url(SPUtil.get(SPUtil.HTTP) + "/prod-api/api/login")       //设置请求的url
//                .post(requestBody)          //使用Post发送请求
////                .addHeader("Authorization",SPUtil.get(SPUtil.TOKEN))    //添加请求头信息
//                .build();
//
//        Call call = okHttpClient.newCall(request);
//
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(LoginActicity.this,"失败",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                String s = response.body().string();
//
//                try {
//                    JSONObject object = new JSONObject(s);
//
//                    if(object.getInt("code") == 200){
//                        startActivity(new Intent(LoginActicity.this, MainActivity.class));
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                /*??? Toast还必须在主线程中允许
//                Looper.prepare()和Looper.loop()是Android中用于创建消息队列和处理消息的方法。
//                当我们在一个非主线程中创建Handler对象时，需要先调用Looper.prepare()方法来创建消息队列，
//                然后再调用Looper.loop()方法来让消息处理在该线程中完成。
//                这样就可以在非主线程中使用Handler对象来发送和处理消息了。
//                而在主线程中，系统已经默认创建了消息队列和Looper对象，
//                因此不需要再调用Looper.prepare()和Looper.loop()方法。*/
//                Looper.prepare();
//                Toast.makeText(LoginActicity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
//                Looper.loop();
//
//            }
//        });
//
//        if (true){
//            return;
//        }

        RetrofitUtil.post("/prod-api/api/login", map, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                try {
                    /*将这个json格式的字符串通过JSONObject对象的构造方法转换为了一个JSONObject对象使用*/
                    JSONObject obj = new JSONObject(json);

                    /*从响应参数中获取状态码进行判断*/
                    if (obj.getInt("code") == 200){
                        //状态码200就是登录成功了
                        Toast.makeText(LoginActicity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        //登录成功将返回回来的token存入共享参数
                        SPUtil.put(SPUtil.TOKEN,obj.getString("token"));
                        //跳转进主页面
                        startActivity(new Intent(LoginActicity.this, MainActivity.class));
                        //结束当前的活动实例
                        finish();
                    }else {
                        /*状态码不为200就是出问题了*/
                        Toast.makeText(LoginActicity.this,"登录失败：" + obj.getString("msg"),Toast.LENGTH_SHORT).show();
                    }


                } catch ( JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }
}


























