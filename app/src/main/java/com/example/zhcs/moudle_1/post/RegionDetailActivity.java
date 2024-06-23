package com.example.zhcs.moudle_1.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.bean.PolicyBean;
import com.example.zhcs.bean.RegionDetailBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;

import java.util.List;

public class RegionDetailActivity extends AppCompatActivity {

    private int id;
    private RecyclerView recyc_policy;
    private TextView content;
    private ImageView img;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_detail);

        /*获取传递过来的人才政策区域ID*/
        id = getIntent().getIntExtra("id",0);

        /*初始化视图数据*/
        initView();

        /*初始化详细页的数据*/
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        /*通过id获取该区域的详细信息*/
        RetrofitUtil.get("/prod-api/api/youth-inn/talent-policy-area/" + id, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                RegionDetailBean.RowsDTO data = new Gson().fromJson(json,RegionDetailBean.class).getData();

                /*回显逻辑一起放这里是方便万一要更新数据的话好直接调用这个方法就好了*/
                /*初始化标题*/
                title.setText(data.getName() + "详细");

                /*初始化图片*/
                Glide.with(RegionDetailActivity.this).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                        .transform(new CenterCrop()).into(img);

                /*初始化该区域详细*/
                content.setText(data.getIntroduce());

            }
        });

        /*通过id获取该区域的人才政策列表*/
        RetrofitUtil.get("/prod-api/api/youth-inn/talent-policy/list?areaId=" + id, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                List<PolicyBean.DataDTO> mdata = new Gson().fromJson(json, PolicyBean.class).getData();

                /*初始化人才政策文件列表*/
                recyc_policy.setLayoutManager(new LinearLayoutManager(RegionDetailActivity.this));

                /*默认分隔线*/
//                recyc_policy.addItemDecoration(new DividerItemDecoration(RegionDetailActivity.this,DividerItemDecoration.VERTICAL));
                /*自定义分隔线*/
//                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(RegionDetailActivity.this,DividerItemDecoration.VERTICAL);
//                dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.cs1,null));
//                recyc_policy.addItemDecoration(dividerItemDecoration);

                recyc_policy.setAdapter(new RegionDetailAdapter(RegionDetailActivity.this, mdata));
            }
        });

    }

    /**
     * 初始化视图信息
     */
    private void initView() {

        /*该区域名称标题*/
        title = findViewById(R.id.title);

        /*该区域详细图片信息*/
        img = findViewById(R.id.img);

        /*该区域图片*/
        content = findViewById(R.id.content);

        /*该区域人才政策文件列表*/
        recyc_policy = findViewById(R.id.recyc_policy);

    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}

















