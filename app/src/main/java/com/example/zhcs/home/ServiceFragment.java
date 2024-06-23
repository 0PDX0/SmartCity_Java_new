package com.example.zhcs.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zhcs.R;
import com.example.zhcs.bean.ServiceBean;
import com.example.zhcs.home.adap.ServiceAdapter;
import com.example.zhcs.util.BaseFragment;
import com.example.zhcs.util.RetrofitUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ServiceFragment extends BaseFragment {


    private TabLayout tabLayout;
    private RecyclerView serviceRecyc;

    private List<ServiceBean.RowsDTO> mdata[] = new ArrayList[4];

    @Override
    protected View initView() {
        Log.i("碎片","全部服务的视图被实例化了");
        View view = View.inflate(getContext(), R.layout.fragment_service,null);
        findView(view);

        return view;
    }

    private void findView(View view) {
        /*全部服务的导航栏*/
        tabLayout = view.findViewById(R.id.tab_layout);

        /*全部服务各个导航栏对应的视图列表*/
        serviceRecyc = view.findViewById(R.id.service_recyc);
    }


    @Override
    protected void initData() {
        super.initData();

        /*初始化服务数据*/
        initTab();

        /*初始化全部服务的导航栏*/
        initServiceData();


    }

    /**
     * 初始化导航栏
     */
    private void initTab() {

        /*.getChildAt：在集合中返回指定位置的视图 返回值为View,强转为LinearLayout*/
        LinearLayout layout = (LinearLayout) tabLayout.getChildAt(0);

        /*setShowDividers：设置分隔线应如何在次布局中的项目之间显示。SHOW DIVIDER MIDDLE翻译(显示分隔线中间)*/
        layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);


        /*设置颜色*/
        layout.setDividerDrawable(new ColorDrawable(Color.GRAY));

        /*设置间距，不知道是不是设置LinearLayout.SHOW_DIVIDER_MIDDLE的原因，这个边距是上下的*/
        layout.setDividerPadding(20);

        /*给tabLayout设置栏块切换的监听*/
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            /*当选项卡进入选中状态时调用*/
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                serviceRecyc.setAdapter(new ServiceAdapter(getContext(),mdata[tab.getPosition()]));
            }

            /*当选项卡退出选中状态调用*/
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            /*重复选中某个选项卡调用*/
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * 初始化服务数据(包括各个服务分类存放进数组)
     */
    private void initServiceData() {

        /*设置网格布局，一行五列*/
        serviceRecyc.setLayoutManager(new GridLayoutManager(getContext(),5));
        /*设置集合数组的类型给每一个栏目设置一个单独的存放的空间*/
        mdata[1] = new ArrayList<>();
        mdata[2] = new ArrayList<>();
        mdata[3] = new ArrayList<>();

        /*发送网络请求，"/prod-api/api/service/list：获取系统全部服务"*/
        RetrofitUtil.get("/prod-api/api/service/list", new RetrofitUtil.OnRequest() {
            @Override
            public void onRequest(String json) {
                /*将获取出来的全部服务的Json格式的字符串对象转换为对象取出其中的列表数据放入第一个数组中*/
                mdata[0] = new Gson().fromJson(json,ServiceBean.class).getRows();

                /*给mdata[0]第一个页面(全部服务)添加数据 T:这里就不添加了*/
//                loadMore();

                /*遍历集合*/
                for (ServiceBean.RowsDTO row : mdata[0]) {
                    /*将服务的类别分别取出来分类*/
                    String serviceType = row.getServiceType();

                    if (serviceType == null){
                        continue;
                    }

                    /*给服务分类i，将不同分*/
                    if (serviceType.equals("车主服务")){
                        mdata[1].add(row);
                    }else if (serviceType.equals("生活服务")){
                        mdata[2].add(row);
                    }else if (serviceType.equals("便民服务")){
                        mdata[3].add(row);
                    }
                }

                /*设置适配器(因为一进入页面总要展示一个，这里就将第一个页面全部服务展示出来)，这里没有设置ServiceAdapter中有三个参数的构造所以会跳过设置更多服务的步骤*/
                serviceRecyc.setAdapter(new ServiceAdapter(getContext(),mdata[0]));

                /*tabLayout.getTabAt(2).select();前进到指定的页面*/

            }
        });


    }


}













/*        GradientDrawable drawable = new GradientDrawable();
        drawable.setSize(50,50);
        layout.setDividerDrawable(drawable);*/






