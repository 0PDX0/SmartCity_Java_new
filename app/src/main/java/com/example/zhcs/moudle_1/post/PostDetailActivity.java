package com.example.zhcs.moudle_1.post;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zhcs.R;
import com.example.zhcs.bean.PostDetailBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {

    private int id;
    private Banner banner;
    private TextView tv_address;
    private TextView tv_tel;
    private TextView tv_date;
    private TextView beds;
    private TextView profile;
    private TextView room;
    private TextView around;
    private TextView service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        /*拿到用户点击的驿站的ID*/
        id = getIntent().getIntExtra("id",8);

        initView();

        /*根据id获取指定驿站的详细*/
        initDetail(id);

    }

    /**
     * 初始化视图数据
     */
    private void initView() {

        /*轮播图*/
        banner = findViewById(R.id.banner);

        /*驿站地址*/
        tv_address = findViewById(R.id.tv_address);

        /*驿站电话*/
        tv_tel = findViewById(R.id.tv_tel);

        /*驿站时间*/
        tv_date = findViewById(R.id.tv_date);

        /*剩余男/女床位*/
        beds = findViewById(R.id.beds);

        /*驿站简介*/
        profile = findViewById(R.id.profile);

        /*房间配置*/
        room = findViewById(R.id.room);

        /*周边配套*/
        around = findViewById(R.id.around);

        /*特色服务*/
        service = findViewById(R.id.service);

    }

    /**
     * 根据id获取指定驿站的详细
     * @param id
     */
    private void initDetail(int id) {

        RetrofitUtil.get("/prod-api/api/youth-inn/youth-inn/" + id, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                PostDetailBean.DataDTO data = new Gson().fromJson(json,PostDetailBean.class).getData();

                /*将轮播图的图片分割出来*/
                String[] img = data.getImageUrlList().split(",");

                List<String> imgs = new ArrayList<>();

                for (String s : img) {
                    imgs.add(s);
                }

                banner.setAdapter(new BannerImageAdapter<String>(imgs) {
                    @Override
                    public void onBindView(BannerImageHolder holder, String data, int position, int size) {

                        Glide.with(PostDetailActivity.this).load(SPUtil.get(SPUtil.HTTP) + data)
                                .transform(new CenterCrop(),new RoundedCorners(30)).into(holder.imageView);

                    }
                }).setIndicator(new CircleIndicator(PostDetailActivity.this));

                /*驿站地址*/
                tv_address.setText(data.getAddress());

                /*驿站电话*/
                tv_tel.setText(data.getPhone());

                /*驿站工作时间*/
                tv_date.setText("办理入住时间段: " + data.getWorkTime());

                /*驿站剩余男/女床位*/
                beds.setText("剩余床位: 男|" + data.getBedsCountBoy() + " 女|" + data.getBedsCountGirl());

                /*驿站简介*/
                profile.setText(data.getIntroduce());

                /*房间配置*/
                room.setText(data.getInternalFacilities());

                /*周边配套*/
                around.setText(data.getSurroundingFacilities());

                /*特色服务*/
                service.setText(data.getSpecialService());


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