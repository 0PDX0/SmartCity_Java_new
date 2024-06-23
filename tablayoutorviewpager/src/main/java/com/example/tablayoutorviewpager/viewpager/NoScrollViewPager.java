package com.example.tablayoutorviewpager.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager {


    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /*主要就是这个必须存在，好像为false或者true都一样*/
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//    /*只要不返回他父类的构造函数，好像都可以禁用*/
//        return false;
//    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

}
