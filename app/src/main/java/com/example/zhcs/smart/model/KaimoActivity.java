package com.example.zhcs.smart.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.zhcs.R;
import com.example.zhcs.bean.BannerBean;
import com.example.zhcs.smart.adap.SmartAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class KaimoActivity extends AppCompatActivity {

    private Banner banner;
    private RecyclerView recyc_view;

    private int[] imgs = {R.mipmap.wuye,
                        R.mipmap.service,
                        R.mipmap.news,
                        R.mipmap.like,
                        R.mipmap.prson};

    private int[] colors = {Color.BLACK,
                            Color.BLUE,
                            Color.rgb(255,184,61),
                            Color.TRANSPARENT,
                            Color.RED};

    private String[] names = {"楷模列表",
                                "英雄故事",
                                "学习心得",
                                "公益活动",
                                "身边的英雄"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaimo);

        /*初始化视图*/
        initView();

        /*初始化轮播图*/
        initBanner();

        /*初始化选项卡*/
        initTab();
    }

    /**
     * 初始化视图数据
     */
    private void initView() {

        /*轮播图*/
        banner = findViewById(R.id.banner);

        /*选项卡*/
        recyc_view = findViewById(R.id.recyc_kaimo);

    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.kaimo_banner1);
        list.add(R.mipmap.kaimo_banner2);
        list.add(R.mipmap.kaimo_banner3);
        list.add(R.mipmap.kaimo_banner4);
        list.add(R.mipmap.kaimo_banner5);

        /**/
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                Glide.with(KaimoActivity.this).load(data)
                        .error(R.mipmap.resource).into(holder.imageView);
            }
        }).setIndicator(new CircleIndicator(this));

        banner.isAutoLoop(true);
        banner.setLoopTime(3000);
    }


    /**
     * 初始化选项卡
     */
    public void initTab(){

        /*设置布局*/
        recyc_view.setLayoutManager(new LinearLayoutManager(this));

        /*设置适配器*/
        recyc_view.setAdapter(new SmartAdapter(this,imgs,names,colors));

    }


    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}