package com.example.november.haofu.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * @author wanlijun
 * @description 修改ViewPager调用setCurrentItem()函数时切换的动画有点卡顿的感觉，不够流畅自然的问题
 *               去掉滑屏速度
 * @time 2017/12/22 8:56
 */

public class ViewPagerScroller extends Scroller {
    private int mDuration = 1000;
    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context,interpolator);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context,interpolator,flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy,mDuration);
    }
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
    public void setViewPagerScrollSpeed(ViewPager viewPager){
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager,this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
