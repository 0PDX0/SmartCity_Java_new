package com.example.zhcs.moudle_1.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zhcs.R;
import com.example.zhcs.bean.HospitalBean;
import com.example.zhcs.bean.LoveBannerBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class HospitalDetailActivity extends AppCompatActivity {

    private Banner banner;
    private TextView name;
    private LinearLayout starList;
    private TextView desc;
    private HospitalBean.RowsDTO data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

        /*获取上个页面传递过来的医院对象*/
        data = (HospitalBean.RowsDTO) getIntent().getSerializableExtra("data");


        initView();
    }

    private void initView() {
        /*轮播图*/
        banner = findViewById(R.id.banner);

        /*医院名*/
        name = findViewById(R.id.name);

        /*展示星级的水平线性布局*/
        starList = findViewById(R.id.star_list);

        /*医院介绍*/
        desc = findViewById(R.id.desc);

        initBanner();

        initData();
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        RetrofitUtil.get("/prod-api/api/hospital/banner/list?hospitalId=" + data.getId(), new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                /*将轮播数据封装起来*/
                List<LoveBannerBean.RowsDTO> imgs = new Gson().fromJson(json,LoveBannerBean.class).getData();

                banner.setAdapter(new BannerImageAdapter<LoveBannerBean.RowsDTO>(imgs) {
                    @Override
                    public void onBindView(BannerImageHolder holder, LoveBannerBean.RowsDTO data, int position, int size) {
                        /*加载图片*/
                        Glide.with(HospitalDetailActivity.this).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                                .error(R.mipmap.resource).transform(new CenterCrop(), new RoundedCorners(20)).into(holder.imageView);

                    }
                }).setIndicator(new CircleIndicator(HospitalDetailActivity.this));
            }
        });
    }

    /**
     * 初始化该医院的所有信息
     */
    private void initData() {

        /*回显医院名*/
        name.setText(data.getHospitalName());

        /*回显医院星级*/
        for (int i = 0; i < data.getLevel(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.ic_baseline_star_fill);
            imageView.setColorFilter(Color.YELLOW);

            starList.addView(imageView);
        }

        /*FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
          FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔*/
        if (data.getBrief().contains("<p>")){
            desc.setText(Html.fromHtml(data.getBrief(),Html.FROM_HTML_MODE_LEGACY));
        }else {
            desc.setText(data.getBrief());
        }

    }


    /**
     * 预约挂号点击事件
     * @param view
     */
    public void go(View view) {

        /*跳转挂号页面*/
        startActivity(new Intent(this,PatientCardActivity.class));

    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}