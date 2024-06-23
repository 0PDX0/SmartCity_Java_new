package com.example.zhcs.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zhcs.R;
import com.example.zhcs.bean.NewsBean;
import com.example.zhcs.home.adap.NewsAdapter;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /*搜索条数*/
        TextView cut = findViewById(R.id.cut);

        /*搜索后展示的数据列表*/
        RecyclerView newsRecyc = findViewById(R.id.news_recyc);

        /*获取上个页面传递过来的用户要查询的新闻关键字*/
        name = getIntent().getStringExtra("name");

        /*发送请求*/
        RetrofitUtil.get("/prod-api/press/press/list?title=" + name, new RetrofitUtil.OnRequest() {

            @Override
            public void onRequest(String json) {

                /*拿到返回回来的数据*/
                List<NewsBean.RowsDTO> list = new Gson().fromJson(json,NewsBean.class).getRows();

//                List<NewsBean> list1 = new Gson().fromJson(json,new TypeToken<NewsBean>(){}.getType());   转换纯集合的Json字符串

                /*根据查询到的数据条数设置搜索结果条数*/
                cut.setText("搜索结果" + list.size() + "条");

                newsRecyc.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                newsRecyc.setAdapter(new NewsAdapter(SearchActivity.this,list));

            }
        });
    }

    /*返回按钮*/
    public void back(View view) {
        finish();
    }
}