
package com.example.zhcs.moudle_1.park;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zhcs.R;
import com.example.zhcs.bean.ParkBean;
import com.example.zhcs.util.CommonAdapter;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParkActivity extends AppCompatActivity {

    int size = 5;
    private RecyclerView parlRecyc;
    private Button moreBtn;
    private List<ParkBean.RowsDTO> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);

        /*展示停车信息的列表*/
        parlRecyc = findViewById(R.id.park_recyc);
        /*查看更多按钮*/
        moreBtn = findViewById(R.id.more_btn);

        /*给RecyclerView设置线性布局*/
        parlRecyc.setLayoutManager(new LinearLayoutManager(this));

        /*发送网络请求*/
        RetrofitUtil.get("/prod-api/api/park/lot/list", new RetrofitUtil.OnRequest() {

            @Override
            public void onRequest(String json) {
                /* 将请求回来的停车场数据封装进集合 */
                list = new Gson().fromJson(json,ParkBean.class).getRows();
                /* 根据size长度渲染集合列表 */
                loadData();
            }
        });

        /*用户点击查看更多就添加五条数据(不是刷新页面)*/
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size += 5;
                /*根据size长度渲染集合列表*/
                loadData();
            }
        });

    }

    /**
     * 渲染列表数据
     */
    private void loadData() {

        /*存放内容主体的集合*/
        List<String> arr = new ArrayList<>();
        /*存放标题的集合*/
        List<String> titles = new ArrayList<>();

        /*list.subList(x,y)：从索引x到y(索引从0开始)，但是不包括y，截取集合数据 【】
        2，父子list做的非结构性修改（non-structural changes）都会影响到彼此：所谓的“非结构性修改”，
        是指不涉及到list的大小改变的修改。相反，结构性修改，指改变了list大小的修改。3，对于结构性修改，
        子list的所有操作都会反映到父list上。但父list的修改将会导致返回的子list失效。

        tips：如何删除list中的某段数据：list.subList(from, to).clear();*/   /*Math.min 返回两数中最小*/

        /*根据size的长度(就是已经渲染的数据条数小于请求回来的总数据条数时)去获取集合中的停车场数据*/
        List<ParkBean.RowsDTO> dtos = list.subList(0,Math.min(size,list.size()));

        /*遍历(部分)停车场数据，依次将数据填充进集合中*/
        for (ParkBean.RowsDTO row : dtos) {
            /*添加标题数据*/
            titles.add(row.getParkName());

            /*添加主体内容数据*/
            arr.add("空位数量：" + row.getVacancy() + "个" +
                    "\n地址：" + row.getAddress() +
                    "\n收费地址：" + row.getRates() + "元/小时" +
                    "\n距离：" + row.getDistance() + "米" );
        }

        /*判断已经展示出来的数据与数据总数，如果相等就代表数据已经展示完毕就无需展示加载更多按钮了*/
        if (list.size() <= size){
            moreBtn.setVisibility(View.GONE);
        }else {
            moreBtn.setVisibility(View.VISIBLE);
        }

        /*将封装好的标题集合和主体内容集合传入是配置器进行渲染(在上面已经设置了这个parlRecyc为线性布局了，所以这里直接设置适配器)，还借用了OnRequest回调来使用*/
        parlRecyc.setAdapter(new CommonAdapter(ParkActivity.this, arr, titles, new RetrofitUtil.OnRequest() {

            /*适配器将点击框框的索引值返回了回来*/
            @Override
            public void onRequest(String json) {
                /*将*/
                Intent intent = new Intent(ParkActivity.this,ParkDetailActivity.class);
                /*根据适配器返回的索引获取到它再集合中的数据并将这个封装好的对象传递去下个页面*/
                intent.putExtra("data", list.get(Integer.parseInt(json)));  //使用intent传递对象时记得将对象序列化实现接口
                /*跳转*/
                startActivity(intent);
            }
        }));

    }

    /*返回按钮*/
    public void back(View view) {
        finish();
    }

    /*跟多按钮*/
    public void his(View view) {
        startActivity(new Intent(this,ParkRecordActivity.class));
    }
}


















