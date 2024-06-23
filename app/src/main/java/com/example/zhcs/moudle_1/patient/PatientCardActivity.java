package com.example.zhcs.moudle_1.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zhcs.R;
import com.example.zhcs.bean.PatientBean;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;

import java.util.List;

public class PatientCardActivity extends AppCompatActivity {

    private RecyclerView recyc_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_card);

        initView();
    }

    private void initView() {

        /*历史挂号列表*/
        recyc_card = findViewById(R.id.recyc_card);

        /**/

        initCard();
    }

    /**
     * 初始化历史挂号列表
     */
    private void initCard() {

//        RetrofitUtil.get("/prod-api/api/hospital/patient/list", new RetrofitUtil.OnRequest() {
//            @Override
//            public void onRequest(String json) {
//
//                List<PatientBean.RowsDTO> rows = new Gson().fromJson(json, PatientBean.class).getRows();
//
//                recyc_card.setLayoutManager(new LinearLayoutManager(PatientCardActivity.this));
//
//                recyc_card.setAdapter(new PatientAdapter(PatientCardActivity.this,rows));
//
//            }
//        });

    }

    /*OnCreate只会调用一次，进入添加就诊人页面返回后是不会调用的，所以添加就诊人信息返回后不会同步更新，要放在onStart内*/
    @Override
    protected void onStart() {
        super.onStart();

        RetrofitUtil.get("/prod-api/api/hospital/patient/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                List<PatientBean.RowsDTO> rows = new Gson().fromJson(json, PatientBean.class).getRows();

                recyc_card.setLayoutManager(new LinearLayoutManager(PatientCardActivity.this));

                recyc_card.setAdapter(new PatientAdapter(PatientCardActivity.this,rows));

            }
        });

    }

    /**
     * 添加就诊人
     * @param view
     */
    public void add(View view) {
        startActivity(new Intent(this,AddPatietnActivity.class));
    }

    /**
     * 关闭当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}