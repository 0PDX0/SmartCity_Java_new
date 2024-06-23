package com.example.zhcs.guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhcs.R;
import com.example.zhcs.util.SPUtil;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.BasePageTransformer;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener{

    private Banner banner;
    private Button net_btn;
    private Button home_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        SPUtil.init(this);

        /*判断用户是否是第一次进入*/
        if (SPUtil.get(SPUtil.FIRST) != null){
            //不是第一次进入，直接跳转登录界面并结束引导页面
            startActivity(new Intent(GuideActivity.this,LoginActicity.class));

            finish();
        }

        /*初始化视图*/
        initView();

        /*初始化数据*/
        initData();
    }

    /**
     * 初始化视图
     */
    public void initView(){

        /*图片轮播当引导页使用*/
        banner = findViewById(R.id.banner);

        /*设置网络按钮*/
        net_btn = findViewById(R.id.net_btn);

        /*进入主页按钮*/
        home_btn = findViewById(R.id.home_btn);


        /*设置点击事件监听*/
        net_btn.setOnClickListener(this);
        home_btn.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    public void initData(){

        /*初始化轮播图*/
        initBanner();


    }


    public void initBanner(){
        /*设置存放轮播图片资源的集合*/
        List<Integer> list = new ArrayList<>();

        /*将轮播图片放入集合中*/
        list.add(R.mipmap.guide1);
        list.add(R.mipmap.guide2);
        list.add(R.mipmap.guide3);
        list.add(R.mipmap.guide4);
        list.add(R.mipmap.guide5);

        List<String> list1 = new ArrayList<>();
        list1.add("https://tse3-mm.cn.bing.net/th/id/OIP-C.1MoJUFFgBjQ5cZfF68O7-QHaHa?w=207&h=207&c=7&r=0&o=5&dpr=1.5&pid=1.7");
        list1.add("https://tse2-mm.cn.bing.net/th/id/OIP-C.As86UzI-SsGgIMPi0dh6DAHaHa?w=187&h=187&c=7&r=0&o=5&dpr=1.5&pid=1.7");
        list1.add("https://tse4-mm.cn.bing.net/th/id/OIP-C.MmRY6CEQP4uq8-31VlhokQHaHT?w=187&h=187&c=7&r=0&o=5&dpr=1.5&pid=1.7");
        list1.add("https://tse4-mm.cn.bing.net/th/id/OIP-C.IxPgrgrpl7pCC1KsNLDxEAHaHa?w=217&h=217&c=7&r=0&o=5&dpr=1.5&pid=1.7");
        list1.add("https://tse2-mm.cn.bing.net/th/id/OIP-C.nGkt1Nr_w2pbcJ-0NCndfwAAAA?w=183&h=183&c=7&r=0&o=5&dpr=1.5&pid=1.7");

        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder bannerImageHolder, Integer integer, int position, int size) {

                //使用Glide加载图片
                Glide.with(GuideActivity.this)
                        .load(integer)
                        .error(R.mipmap.ic_launcher)
                        .into(bannerImageHolder.imageView);

            }
        }).setIndicator(new CircleIndicator(this));

        //取消它的自动轮播
        banner.isAutoLoop(false);
        //设置它的轮播间隔
//        banner.setLoopTime(2000);


        /*这个是坐标信息*/
//        banner.setPageTransformer(new BasePageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                Toast.makeText(GuideActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
//            }
//        });

        /*添加轮播图滑动监听*/
        banner.addOnPageChangeListener(new OnPageChangeListener() {

            /**
             * //这个方法会在屏幕滚动过程中不断被调用。
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }


            /**
             * onPageSelected(int position)：这个方法有一个参数position，代表哪个页面被选中。
             * 当用手指滑动翻页的时候，如果翻动成功了（滑动的距离够长），手指抬起来就会立即执行这个方法，
             * position就是当前滑动到的页面。如果直接setCurrentItem翻页，那position就和setCurrentItem的参数一致，
             * 这种情况在onPageScrolled执行方法前就会立即执行。
             * @param position
             */
            @Override
            public void onPageSelected(int position) {

                /*滑动到最后一个页面时显示按钮*/
                if (position == list.size() - 1){
                    net_btn.setVisibility(View.VISIBLE);
                    home_btn.setVisibility(View.VISIBLE);
                }else {
                    net_btn.setVisibility(View.GONE);
                    home_btn.setVisibility(View.GONE);
                }
            }

            /**
             * 这个方法在手指操作屏幕的时候发生变化。有三个值：0（END）,1(PRESS) , 2(UP) 。
             * 当用手指滑动翻页时，手指按下去的时候会触发这个方法，state值为1，
             * 手指抬起时，如果发生了滑动（即使很小），这个值会变为2，然后最后变为0 。
             * 总共执行这个方法三次。一种特殊情况是手指按下去以后一点滑动也没有发生，
             * 这个时候只会调用这个方法两次，state值分别是1,0 。
             * 当setCurrentItem翻页时，会执行这个方法两次，state值分别为2 , 0 。
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.net_btn){
            /*弹出提示框，进行网络设置*/
            new NetsetDialog(GuideActivity.this).show();
        }else if (v.getId() == R.id.home_btn){
            /*从共享参数获取数据来判断用户是否设置了网络的IP地址，如果没有设置点击就会给出提示并结束方法*/
            if (SPUtil.get(SPUtil.HTTP) == null){
                Toast.makeText(GuideActivity.this,"请设置网络",Toast.LENGTH_SHORT).show();
                return;
            }

            //跳转登录页面
            startActivity(new Intent(GuideActivity.this,LoginActicity.class));
            //将共享参数中是否第一次进入改为true,下次进入就不展示引导页了
            SPUtil.put(SPUtil.FIRST,"true");
            //结束页面
            finish();
        }
    }
}











