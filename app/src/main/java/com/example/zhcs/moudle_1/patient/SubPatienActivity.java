package com.example.zhcs.moudle_1.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zhcs.R;
import com.example.zhcs.util.RetrofitUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SubPatienActivity extends AppCompatActivity {

    private String name;
    private String type;
    private TextView no_data;
    private RecyclerView recyc_sub_patien;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_patien);

        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");

        initView();

    }

    /**
     * 初始化视图变量
     */
    private void initView() {
        /*导航栏*/
        tabLayout = findViewById(R.id.tab_layout);

        /*预约科室列表*/
        recyc_sub_patien = findViewById(R.id.recyc_sub_patien);

        /*暂无数据*/
        no_data = findViewById(R.id.no_data);

        /*给TabLayout添加监听*/
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0){
                    /*普通科室*/
                    if (type.equals("2")){
                        recyc_sub_patien.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        recyc_sub_patien.setVisibility(View.GONE);
                    }

                }else if(tab.getPosition() == 1){
                    /*专家科室*/
                    if (type.equals("1")){
                        recyc_sub_patien.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                    }else {
                        no_data.setVisibility(View.VISIBLE);
                        recyc_sub_patien.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        initData();
    }

    /**
     * 初始化列表数据
     */
    private void initData() {

        List<String> titles = new ArrayList<>();
        List<String> arr = new ArrayList<>();

        for (int i = 0 ; i < 10 ; i++){
            titles.add("预约科室：" + name);
        }

        arr.add("2020-9-21 周一，下午14:00");
        arr.add("2020-9-21 周一，下午15:00");
        arr.add("2020-9-21 周一，下午16:00");
        arr.add("2020-9-21 周一，下午17:00");
        arr.add("2020-9-21 周一，下午18:00");
        arr.add("2020-9-21 周一，下午19:00");
        arr.add("2020-9-21 周一，下午20:00");
        arr.add("2020-9-22 周一，下午8:00");
        arr.add("2020-9-22 周一，下午9:00");
        arr.add("2020-9-22 周一，下午10:00");

        /**/
        SubPatientAdapter adapter = new SubPatientAdapter(this, titles, arr, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                int index = Integer.parseInt(json);

                /*这里的json是用户点击的列表的索引*/
                Intent intent = new Intent(SubPatienActivity.this, SubResultActivity.class);
                intent.putExtra("title",titles.get(index));
                intent.putExtra("outPatient",type.equals("1")? "专家诊":"普通诊");
                intent.putExtra("arr",arr.get(index));

                startActivity(intent);
            }
        });

        tabLayout.getTabAt(1).select();
        tabLayout.getTabAt(0).select();

        recyc_sub_patien.setLayoutManager(new LinearLayoutManager(this));
        recyc_sub_patien.setAdapter(adapter);

    }


    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}












