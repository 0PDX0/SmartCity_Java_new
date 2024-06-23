package com.example.zhcs.moudle_2.subway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zhcs.R;
import com.example.zhcs.bean.SubwayBean;
import com.example.zhcs.bean.SubwayDetaBean;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SubwayDetaActivity extends AppCompatActivity {

    private SubwayBean.DataDTO data;
    private RecyclerView recyc_subDeta;
    private TextView data1;
    private TextView end;
    private TextView begin;
    private TextView line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_deta);

        /*初始化页面视图*/
        initView();

        /*获取上个页面传递的站点信息*/
        data = (SubwayBean.DataDTO) getIntent().getSerializableExtra("data");

        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {

        RetrofitUtil.get("/prod-api/api/metro/line/" + data.getLineId(), new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                /*地铁站点信息*/
                SubwayDetaBean.DataDTO mdata = new Gson().fromJson(json,SubwayDetaBean.class).getData();

                /*地铁所有站点信息*/
                List<SubwayDetaBean.DataDTO.MetroStep> mmdata = mdata.getMetroStepList();

                List<String> names = new ArrayList<>();

                for (SubwayDetaBean.DataDTO.MetroStep datum : mmdata) {
                    names.add(datum.getName());
                }

                /*第三个参数代表是否反转*/
                recyc_subDeta.setLayoutManager(new LinearLayoutManager(SubwayDetaActivity.this,RecyclerView.HORIZONTAL,false));

                data.getCurrentName();
                recyc_subDeta.setAdapter(new SubwayDetaAdapter(SubwayDetaActivity.this,names,data.getCurrentName()));

                line.setText(mdata.getName());

                begin.setText(mdata.getFirst());

                end.setText(mdata.getEnd());

                data1.setText("剩余时间:\u0020" + data.getReachTime() + "分钟" + "\n" +
                              "间隔" + mdata.getStationsNumber() + "站" + "\n" +
                              "剩余距离:\u0020" + mdata.getKm() + "Km");

            }
        });

    }

    /**
     * 初始化视图数据
     */
    private void initView() {

        /*地铁线路号*/
        line = findViewById(R.id.line);

        /*始发地*/
        begin = findViewById(R.id.begin);

        /*目的地*/
        end = findViewById(R.id.end);

        /*该趟地铁详细信息*/
        data1 = findViewById(R.id.data);

        /*地铁路线列表*/
        recyc_subDeta = findViewById(R.id.recyc_subdeta);

    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}