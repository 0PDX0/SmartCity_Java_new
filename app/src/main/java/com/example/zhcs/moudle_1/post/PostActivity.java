package com.example.zhcs.moudle_1.post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.zhcs.R;
import com.example.zhcs.bean.PostBean;
import com.example.zhcs.bean.RegionBean;
import com.example.zhcs.util.RetrofitUtil;
import com.google.gson.Gson;

import java.util.List;

public class PostActivity extends AppCompatActivity {

    private RecyclerView recyc_region;
    private RecyclerView recyc_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        /*初始化视图信息*/
        initView();

        /*初始化人才政策列表*/
        initRegion();

        /*初始化驿站列表*/
        initPost();
    }

    /**
     * 初始化驿站列表
     */
    private void initPost() {

        recyc_post.setLayoutManager(new LinearLayoutManager(PostActivity.this));

        RetrofitUtil.get("/prod-api/api/youth-inn/youth-inn/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<PostBean.RowsDTO> mdata = new Gson().fromJson(json,PostBean.class).getRows();

                recyc_post.setAdapter(new PostAdapter(PostActivity.this,mdata));
            }
        });

    }

    /**
     * 初始化人才政策列表
     */
    private void initRegion() {

        /*给RecyclerView添加布局信息,这样添加水平线性自带水平滚动*/
//        recyc_region.setLayoutManager(new LinearLayoutManager(PostActivity.this,LinearLayoutManager.HORIZONTAL,false));

        recyc_region.setLayoutManager(new GridLayoutManager(PostActivity.this,3));

        /**/
        RetrofitUtil.get("/prod-api/api/youth-inn/talent-policy-area/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<RegionBean.RowsDTO> data = new Gson().fromJson(json,RegionBean.class).getData();

                recyc_region.setAdapter(new RegionAdapter(PostActivity.this,data));

            }
        });



    }

    /**
     * 初始化视图信息
     */
    private void initView() {

        /*区域人才政策信息*/
        recyc_region = findViewById(R.id.recyc_region);

        /*驿站信息*/
        recyc_post = findViewById(R.id.recyc_post);

    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}