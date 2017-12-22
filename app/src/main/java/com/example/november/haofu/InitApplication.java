package com.example.november.haofu;

import android.app.Application;
import android.os.Build;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author wanlijun
 * @time on 2017/12/21 10:23
 * @description 项目初始化
 */

public class InitApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化ImageLoader，没有设置图片缓存
        //加载网络图片，速度快，基本不会出现内存泄露，还有很好的缓存管理机制
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
