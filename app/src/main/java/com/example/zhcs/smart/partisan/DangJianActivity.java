package com.example.zhcs.smart.partisan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.zhcs.R;
import com.example.zhcs.smart.adap.SmartAdapter;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

public class DangJianActivity extends AppCompatActivity {

    private Banner banner;
    private RecyclerView recyc_dangjian;

    private String[] names = {"党建动态",
                                "党员学习",
                                "组织活动",
                                "建言献策",
                                "随手拍"};

    private int[] imgs = {R.mipmap.dangjian_dongtai,
                        R.mipmap.dangjian_xuexi,
                        R.mipmap.dangjian_zhuzhi,
                        R.mipmap.dangjian_feed,
                        R.mipmap.dangjian_paizhao};

    private int[] colors = {0,0,0,0, Color.BLUE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_jian);

        initView();

        initData();
    }



    private void initView() {

        /*轮播图*/
        banner = findViewById(R.id.banner);

        /*选项卡视图*/
        recyc_dangjian = findViewById(R.id.recyc_dangjian);

    }

    private void initData() {

        initBanner();

        initTab();
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.dangjian_banner1);
        list.add(R.mipmap.dangjian_banner2);
        list.add(R.mipmap.dangjian_banner3);
        list.add(R.mipmap.dangjian_banner4);

        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                Glide.with(DangJianActivity.this).load(data)
                        .error(R.mipmap.resource).transform(new CenterCrop()).into(holder.imageView);

            }
        }).setIndicator(new CircleIndicator(this));

        banner.isAutoLoop(true);
        banner.setLoopTime(2000);

    }

    /**
     * 初始化选项卡
     */
    private void initTab() {

        recyc_dangjian.setLayoutManager(new LinearLayoutManager(this));

        recyc_dangjian.setAdapter(new SmartAdapter(this,imgs,names,colors));

        /*设置分隔线*/
        recyc_dangjian.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}