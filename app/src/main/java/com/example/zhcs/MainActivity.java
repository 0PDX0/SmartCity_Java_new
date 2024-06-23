package com.example.zhcs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.zhcs.data.DataFragment;
import com.example.zhcs.home.HomeFragment;
import com.example.zhcs.home.ServiceFragment;
import com.example.zhcs.smart.SmartFragment;
import com.example.zhcs.person.PersonFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*主页面底部导航栏的标题*/
    String ns[] = {
            "首页", "全部服务", "智慧城市","数据分析", "个人中心"
    };

    /*主页面的底部导航栏图片*/
    int imgs[] = {
            R.mipmap.home, R.mipmap.service,
            R.mipmap.build,R.mipmap.news,R.mipmap.prson
    };

    /*主页面的导航栏对应的各个碎片页面*/
    private Class<?>[] frags = {
            HomeFragment.class, ServiceFragment.class,
            SmartFragment.class, DataFragment.class, PersonFragment.class
    };

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化视图数据
        initView();
    }

    /*初始化视图数据*/
    private void initView() {

        /*底部导航栏*/
        tabLayout = findViewById(R.id.tab_layout);

        /*主页主体*/
        viewPager = findViewById(R.id.view_pager);

        //初始化数据
        initData();
    }

    /*初始化数据*/
    private void initData() {

        //初始化底部导航栏
        initTab();

    }

    /*初始化底部导航栏*/
    private void initTab() {

        /*初始化碎片集合*/
        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new ServiceFragment());
        list.add(new SmartFragment());
        list.add(new DataFragment());
        list.add(new PersonFragment());

        /*getSupportFragmentManager()就是获取所在fragment的父容器的管理器*/
        MainTabAdapter mainTabAdapter = new MainTabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mainTabAdapter);

        /*将TabLayout与ViewPager建立关联*/
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0 ; i < tabLayout.getTabCount() ; i++){
            /*getTabAt(int index)返回指定索引处的制表符*/
            TabLayout.Tab tab = tabLayout.getTabAt(i);

            /*初始化导航栏的小图标样式*/
            tab.setCustomView(mainTabAdapter.getView(i));

        }

        /**
         * addOnTabSelectedListener是Android中的接口
         * 监听选项卡选中事件。当用户点击一项选项卡时，会触发接口中的OnTabSelected方法,开发者可以在其中实现相应的逻辑处理
         */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            /*用户点击某个选项卡触发*/
            //因setTextColor时、color是一个资源文件 会set失败 没有效果，所以使用@SuppressLint注解android中@SuppressLint还有忽略指定警告的作用
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*	getCustomView()：返回用于此选项卡的自定义视图*/
                View view = tab.getCustomView();

                ImageView img = view.findViewById(R.id.img);
                TextView tv = view.findViewById(R.id.tv);

                String title = tv.getText().toString();

//                if (title.equals("首页")){
//                    img.setImageResource(imgs[0]);
//                }else if (title.equals("全部服务")){
//                    img.setImageResource(imgs[1]);
//                }else if (title.equals("智慧城市")){
//                    img.setImageResource(imgs[2]);
//                }else if (title.equals("数据分析")){
//                    img.setImageResource(imgs[3]);
//                }else if (title.equals("个人中心")){
//                    img.setImageResource(imgs[4]);
//                }

                /*用户点击某个选项卡，就将该选项卡的图片和标题都设置为蓝色的选中状态*/   /*这个设置样式写在XML文件了*/
//                img.setColorFilter(R.color.blue);
//                tv.setTextColor(R.color.blue); //设置这个就得在方法上加注解@SuppressLint("ResourceAsColor")

            }

            /*用户离开某个选项卡触发*/
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /*	getCustomView()：返回用于此选项卡的自定义视图*/
                View view = tab.getCustomView();

                ImageView img = view.findViewById(R.id.img);
                TextView tv = view.findViewById(R.id.tv);

                String title = tv.getText().toString();

//                if (title.equals("首页")){
//                    img.setImageResource(imgs[0]);
//                }else if (title.equals("全部服务")){
//                    img.setImageResource(imgs[1]);
//                }else if (title.equals("智慧城市")){
//                    img.setImageResource(imgs[2]);
//                }else if (title.equals("数据分析")){
//                    img.setImageResource(imgs[3]);
//                }else if (title.equals("个人中心")){
//                    img.setImageResource(imgs[4]);
//                }

                /*用户离开某个选项卡，就将该选项卡的图片和标题都设置为黑色的未选中状态*/  /*这个设置样式写在XML文件了*/
//                img.setColorFilter(null);
//                tv.setTextColor(R.color.black);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    public class MainTabAdapter extends FragmentPagerAdapter {

        public MainTabAdapter(FragmentManager fm){
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        /**
         * 返回与指定位置关联的片段
         */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        /**
         * 告诉它有多少个View要显示
         * @return
         */
        @Override
        public int getCount() {
            return ns.length;
        }

        /**
         * 设置导航栏的小标题
         * @param position
         * @return
         */
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return ns[position];
        }

        /**
         * 设置小图标
         */
        @SuppressLint("ResourceAsColor")
        public View getView(int position){
            View view = View.inflate(MainActivity.this,R.layout.main_tab_item,null);
            ImageView img = view.findViewById(R.id.img);
            TextView tv = view.findViewById(R.id.tv);

            /*初始化小图标,后续的变化是上面实现的*/
            /*getTabAt(int index)返回指定索引处的制表符,isSelected()判断有没有选中*/
            if (tabLayout.getTabAt(position).isSelected()){
                /*被选中就将它的图片设置为被选中的图片样式*/
                img.setImageResource(imgs[position]);
                /*这个设置样式写在XML文件了 这个一但设置了初始化，XML中的样式就不起效果了！！！！*/
//                img.setColorFilter(R.color.blue);
//                tv.setTextColor(R.color.blue);
            }else {
                /*否则就设置为黑色(未选中)*/
                img.setImageResource(imgs[position]);
                /*这个设置样式写在XML文件了*/
//                img.setColorFilter(null);
//                tv.setTextColor(R.color.black);
            }

            /*TC,*/
            img.setPadding(10,10,10,10);

            /*设置将要显示的视图文字，这里设置的Text是上面进行判断用户点击了那个栏目进行变色的Text!!!*/
            tv.setText(ns[position]);
//            /*tabLayout.getTabRippleColor()：返回此选项卡布局的波纹色*/
//            tv.setTextColor(tabLayout.getTabRippleColor());

            return view;

        }

    }


    public void ser(){
        //设置当前的默认页，这个就是底部导航栏的索引，从0开始，这里设置1就是进入全部服务页面
        viewPager.setCurrentItem(1);
    }

}













