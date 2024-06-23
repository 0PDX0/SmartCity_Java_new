package com.example.tablayoutorviewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.tablayoutorviewpager.viewpager.OneFram;
import com.example.tablayoutorviewpager.viewpager.ThreeFram;
import com.example.tablayoutorviewpager.viewpager.TwoFram;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] tabs = {"测试1","测试2","测试3"};

//    private List<Fragment> list;
    private Fragment[] fragments = {new OneFram(),new TwoFram(),new ThreeFram()};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPager viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabs[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        //添加tab
        for (int i = 0 ; i < tabs.length ; i++){
            tabLayout.addTab(tabLayout.newTab().setText(tabs[i]));
        }



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tab.getPosition();

//                Toast.makeText(MainActivity.this,"您点击了" + tab.getText(),Toast.LENGTH_SHORT).show();

                if (index == 0){
                    Toast.makeText(MainActivity.this,"您点击001",Toast.LENGTH_SHORT).show();
                }else if (index == 1){
                    Toast.makeText(MainActivity.this,"您点击了002",Toast.LENGTH_SHORT).show();
                }else if (index == 2){
                    Toast.makeText(MainActivity.this,"您点击了003",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

























