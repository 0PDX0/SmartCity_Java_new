package com.example.zhcs.moudle_1.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.zhcs.R;
import com.example.zhcs.bean.HospitalBean;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;

import java.util.List;

public class HospitalActivity extends AppCompatActivity {

    private ImageView layout;
    private SearchView search_et;
    private RecyclerView recyc_hospital;
    private List<HospitalBean.RowsDTO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        initView();
    }

    /**
     * 初始化视图数据
     */
    private void initView() {

        /*切换布局的按钮*/
        layout = findViewById(R.id.layout);

        /*输入框组件*/
        search_et = findViewById(R.id.search_et);
        search_et.setFocusedByDefault(false);
        search_et.requestFocus();
        search_et.clearFocus();
//        search_et.setFocusableInTouchMode(false);

        /*监听搜索框的提交和文字变化*/
        search_et.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                initList(query);
                return false;
            }

            /*当搜索框清空的时候重新展示所有的医院信息*/
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().isEmpty()){
                    initList("");
                }
                return false;
            }
        });


        /*医院列表*/
        recyc_hospital = findViewById(R.id.recyc_hospital);

        initData();

        layout.setTag(0);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout.getTag().equals(0)){
                    /*只需要更改布局管理器就行了，不用重新添加适配器*/
                    recyc_hospital.setLayoutManager(new GridLayoutManager(HospitalActivity.this,2));
                    layout.setTag(1);
                }else {
                    recyc_hospital.setLayoutManager(new GridLayoutManager(HospitalActivity.this,1));
                    layout.setTag(0);
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        initList("");
    }

    /**
     * 初始化医院列表信息
     */
    private void initList(String query) {

        RetrofitUtil.get("/prod-api/api/hospital/hospital/list?hospitalName=" + query, new RetrofitUtil.OnRequest() {

            @Override
            public void onRequest(String json) {

                list = new Gson().fromJson(json,HospitalBean.class).getRows();

                recyc_hospital.setLayoutManager(new LinearLayoutManager(HospitalActivity.this));

                recyc_hospital.setAdapter(new HospitalAdapter(HospitalActivity.this, list));
            }
        });



    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}