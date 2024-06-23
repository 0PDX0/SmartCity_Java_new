package com.example.zhcs.home.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/**
 * 1、重写ViewPager并重写覆盖ViewPager的onInterceptTouchEvent(MotionEvent arg0)方法和onTouchEvent(MotionEvent arg0)方法，
 * 这两个方法的返回值都是boolean类型的，只需要将返回值改为false，那么ViewPager就不会消耗掉手指滑动的事件了，转而传递给上层View去处理或者该事件就直接终止了。

 * 2、和TabLayout一起使用的时候，点击TabLayout上的按钮还会有滑动的效果，接下来的处理super.setCurrentItem(item, false);表示切换的时候，不需要切换时间。
 * 就可以去掉那个滑动效果了
 */
public class NoScrollViewPager extends ViewPager {


    public boolean canScroll = false;

    public NoScrollViewPager(Context context){
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canScroll && onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroll && onInterceptTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item,false);
    }
}


















