package com.example.zhcs.util;

import android.app.Application;

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化共享参数
        SPUtil.init(getApplicationContext());

        //当用户输入了域名IP,共享参数中的值不为空,就可以实例化那个网络请求类了，不然直接实例化没有这个ip地址加端口就无法发送请求了
        if (SPUtil.get(SPUtil.HTTP) != null){
            RetrofitUtil.init(getApplicationContext());
        }

    }
}





















