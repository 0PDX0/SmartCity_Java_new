package com.example.zhcs.moudle_1.love;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zhcs.LoveBannerBean;
import com.example.zhcs.R;
import com.example.zhcs.bean.BannerBean;
import com.example.zhcs.bean.LoveCateBean;
import com.example.zhcs.bean.LoveRecommendBean;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class LoveActivity extends AppCompatActivity {

    private SearchView search_et;
    private Banner banner;
    private RecyclerView recyc_love;
    private RecyclerView recyc_recommend;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);

        initView();

        /*初始化搜索框组件*/
        initSearch();

        /*初始化轮播图*/
        initBanner();

        /*初始化公益分类*/
        initLove();

        /*初始化推荐公益*/
        initRecommend();

    }

    /**
     * 初始化推荐公益
     */
    private void initRecommend() {

        RetrofitUtil.get("/prod-api/api/public-welfare/public-welfare-activity/recommend-list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                List<LoveRecommendBean.RowsDTO> mdata = new Gson().fromJson(json,LoveRecommendBean.class).getRows();

                recyc_recommend.setLayoutManager(new LinearLayoutManager(LoveActivity.this,RecyclerView.VERTICAL,false));

                recyc_recommend.setAdapter(new LoveRecommendAdapter(LoveActivity.this,mdata));

            }
        });

    }

    /**
     * 初始化公益分类
     */
    private void initLove() {

        RetrofitUtil.get("/prod-api/api/public-welfare/public-welfare-type/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                List<LoveCateBean.DataDTO> mdata = new Gson().fromJson(json,LoveCateBean.class).getData();

                recyc_love.setLayoutManager(new GridLayoutManager(LoveActivity.this,4,RecyclerView.VERTICAL,false));

                recyc_love.setAdapter(new LoveCateAdapter(LoveActivity.this,mdata));

            }
        });

    }

    /**
     * 初始化搜索框组件
     */
    private void initSearch() {

//        search_et.requestFocus();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * 初始化轮播图
     */
    private void initBanner() {

        RetrofitUtil.get("/prod-api/api/public-welfare/ad-banner/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                List<LoveBannerBean.DataDTO> banners = new Gson().fromJson(json,LoveBannerBean.class).getData();

                banner.setAdapter(new BannerImageAdapter<LoveBannerBean.DataDTO>(banners) {
                    @Override
                    public void onBindView(BannerImageHolder holder, LoveBannerBean.DataDTO data, int position, int size) {

                        Glide.with(LoveActivity.this).load(SPUtil.get(SPUtil.HTTP) + data.getImgUrl())
                                .error(R.mipmap.resource).transform(new CenterCrop(),new RoundedCorners(30)).into(holder.imageView);

                    }
                }).setIndicator(new CircleIndicator(LoveActivity.this));

                banner.isAutoLoop(true);
                banner.setLoopTime(2000);

            }
        });

    }

    /**
     * 初始化视图数据
     */
    private void initView() {

        /*搜索框组件*/
        search_et = findViewById(R.id.search_et);

        /*轮播图组件*/
        banner = findViewById(R.id.banner);

        /*公益分类*/
        recyc_love = findViewById(R.id.recyc_love);

        /*推荐公益*/
        recyc_recommend = findViewById(R.id.recyc_recommend);

        /*历史搜索*/
        listView = findViewById(R.id.list_view);
    }

    /**
     * 结束当前页面
     * @param view
     */
    public void back(View view) {
        finish();
    }
}



//    List<String> list = new ArrayList<>();
//
//
///*监听焦点获取*/
////        search_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
////            @Override
////            public void onFocusChange(View v, boolean hasFocus) {
////
////                if (hasFocus){
////                    listView.setAdapter(new ArrayAdapter<String>(LoveActivity.this, android.R.layout.simple_list_item_1,list));
////                    listView.setVisibility(View.VISIBLE);
////                }else {
////                    listView.setVisibility(View.GONE);
////                }
////
////
////            }
////        });
//
///*监听文本和搜索按钮*/
//        search_et.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//@Override
//public boolean onQueryTextSubmit(String query) {
//        if (!list.contains(query)){
//        list.add(query);
//        }
//        listView.setVisibility(View.GONE);
//        return false;
//        }
//
//@Override
//public boolean onQueryTextChange(String newText) {
//        listView.setAdapter(new ArrayAdapter<String>(LoveActivity.this, android.R.layout.simple_list_item_1,list));
//        noListView(listView);
//        listView.setVisibility(View.VISIBLE);
//        return false;
//        }
//        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//@Override
//public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                /*query	字符序列！：查询字符串。这将替换文本字段中已存在的任何查询文本。
//                  submit 布尔值：是立即提交查询还是仅更新文本字段的内容。*/
//        search_et.setQuery(list.get(position),true);
//        }
//        });


/*public void noListView(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null){
            return;
        }

        int totalHeight = 0;
        for (int i = 0 ; i < listAdapter.getCount() ; i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }
*/