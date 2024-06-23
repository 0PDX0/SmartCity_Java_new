package com.example.zhcs.home;

import android.content.Intent;
import android.util.Log;
import android.view.RoundedCorner;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zhcs.R;
import com.example.zhcs.bean.BannerBean;
import com.example.zhcs.bean.NewsBean;
import com.example.zhcs.bean.NewsTypeBean;
import com.example.zhcs.bean.ServiceBean;
import com.example.zhcs.home.adap.NewsAdapter;
import com.example.zhcs.home.adap.ServiceAdapter;
import com.example.zhcs.home.adap.ThemeAdapter;
import com.example.zhcs.util.BaseFragment;
import com.example.zhcs.util.RetrofitUtil;
import com.example.zhcs.util.SPUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

public class HomeFragment extends BaseFragment {


    private Banner banner;
    private SearchView searchEt;
    private RecyclerView serviceRecyc;
    private RecyclerView themeRecyc;
    private TabLayout tabLayout;
    private RecyclerView newsRecys;

    @Override
    protected View initView() {
        Log.i("碎片","主页的视图被实例化了");
        View view = View.inflate(getContext(), R.layout.fragment_home,null);
        findView(view);

        return view;
    }


    private void findView(View view) {
        /*SearchView：搜索视图,搜索框控件*/
        searchEt = view.findViewById(R.id.search_et);
        /*轮播图控件*/
        banner = view.findViewById(R.id.banner);
        /*主页推荐服务的视图*/
        serviceRecyc = view.findViewById(R.id.service_recyc);
        /*推荐服务下的两个视窗*/
        themeRecyc = view.findViewById(R.id.theme_recyc);
        /*主页新闻的导航栏*/
        tabLayout = view.findViewById(R.id.tab_layout);
        /*主页新闻的遍历信息视图*/
        newsRecys = view.findViewById(R.id.news_recyc);

        //requestFocus() 方法在三种情况下获取焦点不能生效。
        //1）对应的View不支持Focus;
        //2) 对应的View支持Focus，但是不支持在Touch模式下的Focus;
        //3) 对应的View其祖先View 设置了FOCUS_BLOCK_DESCENDANTS 标志， 阻止其子View获取焦点。
        //而requestFocusFromTouch() 方法设计的目的就是解决requestFocus()
        // 在第二种不能获得焦点的情况下，Touch模式下不支持焦点，也能够获得焦点使用的。
        banner.requestFocusFromTouch();
        banner.requestFocus();
    }


    @Override
    protected void initData() {
        super.initData();

        /*初始化布局文件中要使用的所有的控件,findView方法已经实现了*/
//        initView();

        /*初始化搜索组件的监听*/
        initSearch();

        /*初始化轮播图*/
        initBanner();

        /*初始化系统全部服务*/
        initService();

        /*初始化热点新闻数据*/
        initTheme();

        /*初始化各个导航栏的列表数据*/
        initTab();

    }


    /**
     * 初始化搜索组件的监听
     */
    private void initSearch() {

//        searchEt.setIconifiedByDefault(false);
        //TODO 搜索框
//        searchEt.requestFocus();
        searchEt.requestFocusFromTouch();
        searchEt.clearFocus();


        /*点击事件只有点击那个搜索框的按钮有效*/
//        searchEt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"666",Toast.LENGTH_SHORT).show();
//            }
//        });

        Toast.makeText(getContext(),String.valueOf(searchEt.isFocusable()),Toast.LENGTH_SHORT).show();

        //setOnQueryTextListener设置侦听器以在查询文本字段的焦点更改时发出通知。，OnQueryTextListener：当用户在 SearchView 中执行操作（例如单击按钮或键入查询）时接收回调的侦听器对象。
        searchEt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /**
             * 在用户提交查询时调用
             * 在用户提交查询时调用。这可能是由于按下键盘上的键或按下提交按钮。侦听器可以通过返回 true 来覆盖标准行为，以指示它已处理提交请求。
             * 否则返回 false 以允许 SearchView 通过启动任何关联的意图来处理提交
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                //用户提交搜索跳转搜索结果页面、
                Intent intent = new Intent(getContext(),SearchActivity.class);
                //将用户输入的搜索值通过意图传递过去
                intent.putExtra("name",query);
                //跳转
                startActivity(intent);

                //事件中返回true，点击搜索按钮后软键盘不自动消息，返回false，表示点击搜索按钮后软键盘自动消失
                return false;
            }

            /**
             * 当用户更改查询文本时调用
             * 如果查询已由侦听器处理，则为 true，如果让 SearchView 执行默认操作，则为 false。
             * @param newText
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }


    /**
     * 初始化轮播图
     */
    public void initBanner(){
        final List<BannerBean.RowsDTO>[] list = new List[1];

        //发送网络请求获取首页轮播的图片，2为首页轮播，1为引导页轮播
        RetrofitUtil.get("/prod-api/api/rotation/list?type=2", new RetrofitUtil.OnRequest() {

            @Override
            public void onRequest(String json) {
                list[0] = new Gson().fromJson(json,BannerBean.class).getRows();

                /*不知道怎么回事，它的执行顺序好像是先把整个方法执行完最后再执行的这个里面的回调，所有必须放里面，放外面没效果*/
                banner.setAdapter(new BannerImageAdapter<BannerBean.RowsDTO>(list[0]) {

                    @Override
                    public void onBindView(BannerImageHolder holder, BannerBean.RowsDTO data, int position, int size) {
                        //使用Glide加载网络图片 .transform(new CenterCrop(), new RoundedCorners(20))：设置圆角和裁剪，要一起放在这里面
                        Glide.with(getContext()).load(SPUtil.get(SPUtil.HTTP) + data.getAdvImg())
                                .transform(new CenterCrop(), new RoundedCorners(20)).into(holder.imageView);


                    }
                }).setIndicator(new CircleIndicator(getContext())); /*设置圆角导航点*/



                /*设置轮播间隔*/
//                banner.setLoopTime(700);

                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object data, int position) {
                        /*从集合列表中得到它的跳转详细id*/
                        int targetId = list[0].get(position).getTargetId();
                        /*设置跳转的意图*/
                        Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
                        /*再意图中存入跳转详细的id*/
                        intent.putExtra("id",targetId);
                        /*页面跳转*/
                        startActivity(intent);
                    }
                });

            }
        });
    }

    /**
     * 初始化系统全部服务
     * prod-api/api/service/list：获取系统全部服务
     */
    private void initService() {
        //GridLayoutManager(表格布局)：第一个参数表示上下文，第二个参数表示表格有多少列，这里是一行五列
        serviceRecyc.setLayoutManager(new GridLayoutManager(getContext(),5));

        //发送请求，设置回调
        RetrofitUtil.get("prod-api/api/service/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                /*取出其中的数据集合rows*/
                List<ServiceBean.RowsDTO> list = new Gson().fromJson(json,ServiceBean.class).getRows();

                /*这里设置截取的原因是主页就10个服务图标，所以就10个*/
                /*设置RecyclerView的适配器，list.subList(0,10)：截取集合下标从第一个参数开始，到第二个参数但不包含第二个参数的位置。不改变原集合对象*/
                serviceRecyc.setAdapter(new ServiceAdapter(getContext(),list.subList(0,10),true));
            }
        });
    }

    /**
     * 初始化热点新闻数据
     */
    private void initTheme() {

        //给这个回收期视图(RecyclerView)设置一个自定义控件(LayoutManager),这里设置了一个网格布局管理器(GridLayoutManager)
        //GridLayoutManager(表格布局)：第一个参数表示上下文，第二个参数表示表格有多少列，这里是一行两列
        themeRecyc.setLayoutManager(new GridLayoutManager(getContext(),2));

        //发送网络请求设置回调
        RetrofitUtil.get("/prod-api/press/press/list?hot=Y", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                /*将传递回来的两条热点新闻的数据通过Json转换封装成对象集合使用*/
                List<NewsBean.RowsDTO> list = new Gson().fromJson(json,NewsBean.class).getRows();
                /*设置适配器*/
                themeRecyc.setAdapter(new ThemeAdapter(getContext(),list));

            }
        });

    }

    /**
     * 初始化导航栏，以及第一页的初始列表数据
     */
    private void initTab() {

        //RecyclerView是官方在5.0之后新添加的控件，推出用来替换传统的ListView和GridView列表控件。
        //LayoutManager是一个抽象类，有3个字类；
        //LinearLayoutManager 线性布局管理器
        //GridLayoutManager 表格布局管理器
        //StaggeredGridLayoutManager 瀑布流布局管理器
        /*这里用线性，因为新闻就是一条一条跟下去的*/
        newsRecys.setLayoutManager(new LinearLayoutManager(getContext()));

        //获取新闻分类的接口，(里面没有新闻数据，它包含的是这个新闻栏目的id，需要用这个id去获取详细数据)
        RetrofitUtil.get("/prod-api/press/category/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {

                /*将处理好的Json字符串使用Gson封装进该对象，再获取出这个对象的数据列表 TODO 明明接口文档写的是rows,但是实际去浏览器访问时data*/
                final List<NewsTypeBean.DataDTO> list = new Gson().fromJson(json,NewsTypeBean.class).getData();

                //遍历数据列表
                for (NewsTypeBean.DataDTO row : list) {
                    /*遍历数据列表，向tabLayout添加选项卡。设置主页新闻每个导航栏的标题，不然是没有导航栏的标题数据的*/
                    tabLayout.addTab(tabLayout.newTab().setText(row.getName()));
                }

                /*将查询出来的新闻列表集合的id获取出来再发送请求获取该id下的新闻列表使用适配器添加进视图中，默认先展示第一个栏目的新闻列表*/
                loadNews(list.get(0).getId());

                /*添加将在选项卡选择更改时调用的监听器*/
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        loadNews(list.get(tab.getPosition()).getId());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });


            }


        });


    }

    /**
     * 初始化指定栏目id的新闻列表
     * @param id
     */
    private void loadNews(int id) {

        /*获取指定id的新闻类型的列表*/
        RetrofitUtil.get("/prod-api/press/press/list?type=" + id, new RetrofitUtil.OnRequest() {

            /*再那边发送网络请求后调用了你这边传入的OnRequest()对象，并且调用了这个重写的方法，将请求成功构造的json字符串也传递过来了*/
            @Override
            public void onRequest(String json) {

                /*成功将数据返回取出其中的数据列表集合*/
                List<NewsBean.RowsDTO> list = new Gson().fromJson(json,NewsBean.class).getRows();

                /*添加自定义适配器，将获取出来的新闻数据传递过去*/
                newsRecys.setAdapter(new NewsAdapter(getContext(),list));


            }
        });
    }

}


















