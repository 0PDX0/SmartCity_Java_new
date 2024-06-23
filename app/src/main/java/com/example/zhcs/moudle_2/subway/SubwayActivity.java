package com.example.zhcs.moudle_2.subway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zhcs.R;
import com.example.zhcs.bean.SubwayBean;
import com.example.zhcs.moudle_1.event.CommentAdapter;
import com.example.zhcs.util.CommonAdapter;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubwayActivity extends AppCompatActivity {

    private RecyclerView recyc_subway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway);

        initView();

        initData();
    }


    /**
     * 本站点的地铁详细
     */
    private void initData() {

        recyc_subway.setLayoutManager(new LinearLayoutManager(SubwayActivity.this));

        RetrofitUtil.get("/prod-api/api/metro/list?currentName=建国门", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<SubwayBean.DataDTO> mdata = new Gson().fromJson(json,SubwayBean.class).getData();

                List<String> titles = new ArrayList<>();

                List<String> data = new ArrayList<>();

                for (SubwayBean.DataDTO dataDTO : mdata) {

                    titles.add(dataDTO.getLineName());

                    data.add("下一站名称:\u0020" + dataDTO.getNextStep().getName() + "\n" + "到达本站时长:\u0020" + dataDTO.getReachTime() + "分钟");
                }

                recyc_subway.setAdapter(new CommonAdapter(SubwayActivity.this, data, titles, new RetrofitUtil.OnRequest() {
                    @Override
                    public void onRequest(String json) {
                        /*返回的是用户点击的列表索引，因为这个CommonAdapter适配器是公共使用的，所以需要适配很多功能，所以使用返回索引，不然只能跳一种*/
                        Intent intent = new Intent(SubwayActivity.this,SubwayDetaActivity.class);
                        intent.putExtra("data", mdata.get(Integer.parseInt(json)));
                        startActivity(intent);
                    }
                }));
            }
        });



    }

    /**
     * 初始化视图数据
     */
    private void initView() {

        recyc_subway = findViewById(R.id.recyc_subway);

    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }


    /**
     * 跳转地铁地图
     * @param view
     */
    public void tomap(View view){
        startActivity(new Intent(this,SubwayMapActivity.class));
    }

}

/*    {
            "lineId": 31,
            "lineName": "2号线",
            "preStep": {
                "name": "朝阳门",
                "lines": [
                    {
                        "lineId": 21,
                        "lineName": "6号线"
                    },
                    {
                        "lineId": 24,
                        "lineName": "6号线"
                    },
                    {
                        "lineId": 31,
                        "lineName": "2号线"
                    },
                    {
                        "lineId": 38,
                        "lineName": "2号线"
                    }
                ]
            },
            "nextStep": {
                "name": "北京站",
                "lines": [
                    {
                        "lineId": 31,
                        "lineName": "2号线"
                    },
                    {
                        "lineId": 38,
                        "lineName": "2号线"
                    }
                ]
            },
            "currentName": "建国门",
            "reachTime": 3
        },*/