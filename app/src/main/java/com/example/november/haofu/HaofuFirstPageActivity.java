package com.example.november.haofu;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.november.R;
import com.example.november.haofu.bean.BannerBean;
import com.example.november.haofu.view.ViewPagerScroller;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HaofuFirstPageActivity extends AppCompatActivity {
    private static final int MAX_BANNER_SIZE = Integer.MAX_VALUE;
    private static final int AUTO_CYCLE = 1;
    private ViewPager mBannerViewPager;
    private List<BannerBean> mBannerBeanList = new ArrayList<>();
    private BannerAdapter bannerAdapter;
    private int currentPosition = MAX_BANNER_SIZE / 2; //轮播图当前显示图片的位置
    private TimerTask mTimerTask; //自动轮播定时器
    private boolean mIsUserTouched = false; //用户是否滑动轮播图
    private Timer mTimer;
    private ViewPagerScroller mViewPagerScroller;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case AUTO_CYCLE:
                    //smoothScroll为true时，切换有平移动画，为false时，切换没动画，直接替换
//                    currentPosition = currentPosition % MAX_BANNER_SIZE;
                    mBannerViewPager.setCurrentItem(currentPosition,true);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //顶部状态栏还在，只是背景色变了
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //全屏，状态栏隐藏了，底部导航栏还在
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //底部导航栏还在，只是背景色变了
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        setContentView(R.layout.activity_haofu_first_page);
        initUI();
        initData();
        initValue();
        autoCycle();
    }
    private void initUI(){
        mBannerViewPager = (ViewPager)findViewById(R.id.bannerViewPager);
    }
    private void initData(){
        BannerBean bannerBean = new BannerBean();
        bannerBean.setUrl("http://www.baidu.com");
        bannerBean.setTitle("Angelababy");
        bannerBean.setPicUrl("http://p1.so.qhimgs1.com/t01a5209467d0d906cd.jpg");
        mBannerBeanList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setUrl("http://www.hao123.com");
        bannerBean.setTitle("杨颖");
        bannerBean.setPicUrl("http://p0.so.qhimgs1.com/t01f8b335c794aa1e64.jpg");
        mBannerBeanList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setUrl("http://www.runoob.com");
        bannerBean.setTitle("baby");
        bannerBean.setPicUrl("http://img.idol001.com/origin/2017/12/12/08ffa4b01c7177df677e7a30e444ba921513070346.jpg");
        mBannerBeanList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setUrl("http://blog.csdn.net");
        bannerBean.setTitle("haha");
        bannerBean.setPicUrl("http://p3.ifengimg.com/a/2017_25/782d078ea70da54_size125_w1200_h837.jpg");
        mBannerBeanList.add(bannerBean);
        bannerBean = new BannerBean();
        bannerBean.setUrl("http://write.blog.csdn.net/postlist");
        bannerBean.setTitle("moezu");
        bannerBean.setPicUrl("http://img5.duitang.com/uploads/item/201412/10/20141210173745_tLaic.jpeg");
        mBannerBeanList.add(bannerBean);
    }
    private void initValue(){
        mViewPagerScroller = new ViewPagerScroller(HaofuFirstPageActivity.this);
        bannerAdapter = new BannerAdapter(getApplicationContext(),mBannerBeanList);
        mBannerViewPager.setAdapter(bannerAdapter);
        mBannerViewPager.setCurrentItem(currentPosition,true);
        mViewPagerScroller.setViewPagerScrollSpeed(mBannerViewPager);
        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position != currentPosition){
                    currentPosition = position;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //当用户滑动轮播图时，禁止自动轮播
        mBannerViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        mIsUserTouched = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        mIsUserTouched = false;
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 用定时器实现自动循环轮播
     */
    private void autoCycle(){
        if(mTimerTask != null){
            mTimerTask.cancel();
        }
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(!mIsUserTouched){
                    currentPosition ++;
                    mHandler.sendEmptyMessage(AUTO_CYCLE);
                }
            }
        };
        if(mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(mTimerTask,3000,3000);
    }
    public class BannerAdapter extends PagerAdapter{
        private Context mContext;
        private  List<BannerBean> mBannerBeanList;
        public BannerAdapter(Context context ,List<BannerBean> bannerBeanList){
            this.mContext = context;
            this.mBannerBeanList = bannerBeanList;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public int getCount() {
            return MAX_BANNER_SIZE;
//            return mBannerBeanList.size() + 2;
        }
       @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int relativePosition = position % mBannerBeanList.size();
            View view = LayoutInflater.from(mContext).inflate(R.layout.banner_item,container,false);
            ImageView bannerImage = (ImageView) view.findViewById(R.id.bannerImage);
            BannerBean bannerBean = mBannerBeanList.get(relativePosition);
            //无需缓存加载的图片
            ImageLoader.getInstance().displayImage(bannerBean.getPicUrl(),bannerImage);
            //缓存加载的图片
            /*DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.timg)
                    .showImageOnFail(R.drawable.timg)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .displayer(new RoundedBitmapDisplayer(5))//图片带圆角
                    .build();
            ImageLoader.getInstance().displayImage(bannerBean.getPicUrl(),bannerImage,options);
            */
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        /*@Override
        public void finishUpdate(ViewGroup container) {
            //当ViewPager的页面数为图片数+2时
            int position = mBannerViewPager.getCurrentItem();
            if(position == 0){
                position = mBannerBeanList.size();
                mBannerViewPager.setCurrentItem(position,false);
            }
            if(position == mBannerBeanList.size() + 2 - 1){
                position = 1;
                mBannerViewPager.setCurrentItem(position,false);
            }
        }*/
    }

    public void setCurrent(ViewPager viewPager,int position){
        try {
            Field mCurItem = ViewPager.class.getDeclaredField("mCurItem");
            mCurItem.setAccessible(true);
            mCurItem.set(viewPager,position);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimerTask != null){
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }
}
