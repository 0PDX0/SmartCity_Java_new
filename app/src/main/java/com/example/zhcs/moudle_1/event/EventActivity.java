package com.example.zhcs.moudle_1.event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zhcs.R;
import com.example.zhcs.bean.BannerBean;
import com.example.zhcs.bean.EventBean;
import com.example.zhcs.bean.NewsTypeBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class EventActivity extends AppCompatActivity {

    private Banner banner;
    private TabLayout tabLayout;
    private RecyclerView recyc_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        initView();

    }

    /**
     * 初始化当前视图
     */
    private void initView() {
        /*轮播图*/
        banner = findViewById(R.id.banner);

        /*导航栏*/
        tabLayout = findViewById(R.id.tab_layout);

        /*活动列表*/
        recyc_event = findViewById(R.id.recyc_event);

        /*初始化轮播图*/
        initBanner();

        /*初始话TabLayout导航栏信息*/
        initTab();

//        tabLayout.getTabAt(0).select();
    }

    /**
     * 初始化导航栏信息
     */
    private void initTab() {

//        RetrofitUtil.get("/prod-api/api/activity/category/list", new RetrofitUtil.OnRequest() {
//            @Override
//            public void onRequest(String json) {
//                List<NewsTypeBean.DataDTO> data = new Gson().fromJson()
//            }
//        });

          OkHttpClient client = new OkHttpClient();
          Request request = new Request.Builder()
                  .get()
                  .url(SPUtil.get(SPUtil.HTTP) + "/prod-api/api/activity/category/list")
                  .header("Authorization",SPUtil.get(SPUtil.TOKEN))
                  .build();

          Call call = client.newCall(request);

          call.enqueue(new Callback() {
              @Override
              public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                  String json = response.body().string();

                  List<NewsTypeBean.DataDTO> news = new Gson().fromJson(json, NewsTypeBean.class).getData();

                  /*要去主线程操作视图数据*/
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          for (NewsTypeBean.DataDTO dataDTO : news) {
                              tabLayout.addTab(tabLayout.newTab().setText(dataDTO.getName()));
                          }
                      }
                  });

                  /*监听TabLayout的切换*/
                  tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                      @Override
                      public void onTabSelected(TabLayout.Tab tab) {
                          NewsTypeBean.DataDTO data = news.get(tab.getPosition());
                          initData(data.getId());

                      }

                      @Override
                      public void onTabUnselected(TabLayout.Tab tab) {

                      }

                      @Override
                      public void onTabReselected(TabLayout.Tab tab) {

                      }
                  });
              }

              @Override
              public void onFailure(@NonNull Call call, @NonNull IOException e) {
                  /*失败*/

              }

          });
    }

    private void initData(int categoryId){
        /*发送请求获取活动信息*/
        RetrofitUtil.get("/prod-api/api/activity/activity/list?categoryId=" + categoryId, new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<EventBean.RowsDTO> data = new Gson().fromJson(json,EventBean.class).getRows();

                Collections.sort(data); //实体类那边实现了Comparable接口的compareTo方法
                recyc_event.setLayoutManager(new LinearLayoutManager(EventActivity.this));

                recyc_event.setAdapter(new EventAdapter(EventActivity.this,data));
            }
        });
    }

    /**
     * 初始化轮播图
     */
    private void initBanner() {
        RetrofitUtil.get("/prod-api/api/activity/rotation/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<BannerBean.RowsDTO> bannerBeans = new Gson().fromJson(json,BannerBean.class).getRows();

                banner.setAdapter(new BannerImageAdapter<BannerBean.RowsDTO>(bannerBeans) {
                    @Override
                    public void onBindView(BannerImageHolder holder, BannerBean.RowsDTO data, int position, int size) {

                        /*加载轮播图图片*/
                        Glide.with(EventActivity.this).load(SPUtil.get(SPUtil.HTTP) + data.getAdvImg())
                                .error(R.mipmap.resource).transform(new RoundedCorners(30)).into(holder.imageView);


                    }
                }).setIndicator(new CircleIndicator(    EventActivity.this));

                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        Intent intent = new Intent(EventActivity.this,EventDetailActivity.class);
                        intent.putExtra("id",bannerBeans.get(position).getTargetId());
                        startActivity(intent);
                    }
                });
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














