package com.example.zhcs.smart.environ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.zhcs.R;
import com.example.zhcs.smart.adap.LitterAdapter;
import com.example.zhcs.smart.adap.SmartAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class HuanbaoActivity extends AppCompatActivity {

    private Banner banner;
    private RecyclerView recyc_huanbao;
    private RecyclerView recyc_laji;

    private int[] imgs = {R.mipmap.huanbao_icon1,
                            R.mipmap.huanbao_icon2,
                            R.mipmap.huanbao_icon3,
                            R.mipmap.huanbao_icon4};

    private String[] names = {"预约垃圾上门回收","预约回收垃圾历史","信息预留","附件回收机"};

    private int[] imgss = {R.mipmap.kele,
                            R.mipmap.keleguan,
                            R.mipmap.keleping,
                            R.mipmap.mianbei,
                            R.mipmap.shuben,
                            R.mipmap.zhixiang};

    private String[] namess = {"可乐易拉罐","可乐瓶","可乐瓶","棉被","书本","纸箱"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huanbao);

        initView();

        initData();
    }

    private void initView() {

        /*轮播图*/
        banner = findViewById(R.id.banner);

        recyc_huanbao = findViewById(R.id.recyc_huanbao);

        recyc_laji = findViewById(R.id.recyc_laji);
    }

    private void initData() {

        /*初始化轮播图*/
        initBanner();

        /*初始化选择选项卡*/
        initTab();

        /*初始化垃圾物品展示选项卡*/
        initLitterTab();
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.huanbao_banner1);
        list.add(R.mipmap.huanbao_banner2);
        list.add(R.mipmap.huanbao_banner3);
        list.add(R.mipmap.huanbao_banner4);
        list.add(R.mipmap.huanbao_banner5);

        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                Glide.with(HuanbaoActivity.this).load(data)
                        .error(R.mipmap.huanbao_icon1).into(holder.imageView);
            }
        }).setIndicator(new CircleIndicator(this));

        banner.isAutoLoop(true);
        banner.setLoopTime(2000);

    }

    /**
     * 初始化选项卡
     */
    private void initTab() {

        recyc_huanbao.setLayoutManager(new LinearLayoutManager(this));

        recyc_huanbao.setAdapter(new SmartAdapter(this,imgs,names));

        recyc_huanbao.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


    }

    /**
     * 初始化垃圾展示选项卡
     */
    private void initLitterTab() {

        recyc_laji.setLayoutManager(new GridLayoutManager(this,2));

        recyc_laji.setAdapter(new LitterAdapter(this,imgss,namess));

    }


    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}