package com.example.zhcs.moudle_1.gover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zhcs.LoveBannerBean;
import com.example.zhcs.R;
import com.example.zhcs.bean.AppealBean;
import com.example.zhcs.bean.LoveTypeBean;
import com.example.zhcs.util.CommonAdapter;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GoverActivity extends AppCompatActivity {

    private Banner banner;
    private RecyclerView recyc_gover;
    private RecyclerView recyc_igover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gover);

        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        /*轮播图*/
        banner = findViewById(R.id.banner);

        /*市民诉求分类列表*/
        recyc_gover = findViewById(R.id.recyc_gover);

        /*我的诉求列表*/
        recyc_igover = findViewById(R.id.recyc_igover);

        /*初始化数据*/
        initData();

        /*初始化轮播图*/
        initBanner();

        /*初始化我的诉求列表*/
        initAppeal();

        /*初始化诉求分类的选项卡*/
        initType();

    }


    private void initData() {

    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {

        RetrofitUtil.get("/prod-api/api/gov-service-hotline/ad-banner/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<LoveBannerBean.DataDTO> mdata = new Gson().fromJson(json,LoveBannerBean.class).getData();

                banner.setAdapter(new BannerImageAdapter<LoveBannerBean.DataDTO>(mdata) {
                    @Override
                    public void onBindView(BannerImageHolder holder, LoveBannerBean.DataDTO data, int position, int size) {
                        Glide.with(GoverActivity.this).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                                .error(R.mipmap.resource).transform(new CenterCrop(),new RoundedCorners(20)).into(holder.imageView);
                    }
                }).setIndicator(new CircleIndicator(GoverActivity.this));

            }
        });

    }

    /**
     * 初始化诉求分类的列表
     */
    private void initType() {
        recyc_gover.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        RetrofitUtil.get("/prod-api/api/gov-service-hotline/appeal-category/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<LoveTypeBean.DataDTO> data = new Gson().fromJson(json,LoveTypeBean.class).getRows();

                recyc_gover.setAdapter(new AppealTypeAdapter(GoverActivity.this,data));
            }
        });

    }


    /**
     * 回显我的诉求列表
     */
    private void initAppeal() {

        RetrofitUtil.get("/prod-api/api/gov-service-hotline/appeal/my-list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<AppealBean.RowsDTO> list = new Gson().fromJson(json,AppealBean.class).getRows();

                /*使用这个方法排序的类必须实现Comparator的compare方法*/
                Collections.sort(list);
//                list.sort(new Comparator<AppealBean.RowsDTO>() {
//                    @Override
//                    public int compare(AppealBean.RowsDTO o1, AppealBean.RowsDTO o2) {
//                        return o1.getState().compareTo(o2.getState());
//                    }
//                });

                List<String> title = new ArrayList<>();
                List<String> mdata = new ArrayList<>();

                for (AppealBean.RowsDTO rowsDTO : list) {
                    title.add(rowsDTO.getAppealCategoryName());

                    mdata.add("承办单位: " + rowsDTO.getUndertaker() +
                            "\n提交时间: " + rowsDTO.getCreateTime() +
                            "\n处理状态: " + (rowsDTO.getState().equals("0") ? "已处理":"未处理"));
                }

                recyc_igover.setLayoutManager(new LinearLayoutManager(GoverActivity.this));

                recyc_igover.setAdapter(new CommonAdapter(GoverActivity.this, mdata, title, new RetrofitUtil.OnRequest() {

                    /*这里的字符串返回的是用户选择的诉求的索引*/
                    @Override
                    public void onRequest(String json) {
                        Intent intent = new Intent(GoverActivity.this,AppealDetailActivity.class);

                        intent.putExtra("data",list.get(Integer.parseInt(json)));   //记得对传输的类序列化

                        startActivity(intent);
                    }
                }));


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