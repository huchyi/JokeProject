package com.android.joke.jokeproject.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.joke.jokeproject.common.Constant;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

public class ImageUtils {



    public static File initFile(){
        File imgDir = new File(Constant.CACHEDIR_IMG);
        if(!imgDir.exists()){
            imgDir.mkdirs();
        }
        return imgDir;
    }

    /**
     * ImageLoaderConfiguration config = new ImageLoaderConfiguration
     .Builder(context)
     .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
     .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
     .threadPoolSize(3)//线程池内加载的数量
     .threadPriority(Thread.NORM_PRIORITY - 2)
     .denyCacheImageMultipleSizesInMemory()
     .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
     .memoryCacheSize(2 * 1024 * 1024)
     .discCacheSize(50 * 1024 * 1024)
     .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
     .tasksProcessingOrder(QueueProcessingType.LIFO)
     .discCacheFileCount(100) //缓存的文件数量
     .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
     .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
     .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
     .writeDebugLogs() // Remove for release app
     .build();//开始构建
     *
     *
     * **/
    public static void initImageLoader(Context context, DisplayImageOptions defaultDisplayImageOptions) {
        initImageLoader(context, defaultDisplayImageOptions, initFile(), new Md5FileNameGenerator());// 默认采用MD5缓存
    }

    /**
     * 初始化ImageLoader
     *
     * @param context
     * @param defaultDisplayImageOptions
     * @param cacheDir 缓存位置
     * @param fileNameGenerator 文件缓存规则
     */
    public static void initImageLoader(Context context, DisplayImageOptions defaultDisplayImageOptions, File cacheDir, FileNameGenerator fileNameGenerator) {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration.Builder configBuidler = new ImageLoaderConfiguration.Builder(context);
            configBuidler.threadPriority(Thread.NORM_PRIORITY-1);// 线程优先级
            configBuidler.memoryCache(new LruMemoryCache(2*1024*1024));
            configBuidler.memoryCacheSize(2*1024*1024);
            configBuidler.diskCacheSize(50 * 1024 * 1024);
            configBuidler.diskCacheFileCount(100);
            configBuidler.denyCacheImageMultipleSizesInMemory();// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。不这是默认会缓存多个不同的大小的相同图片
            configBuidler.tasksProcessingOrder(QueueProcessingType.FIFO);// 设置图片下载和显示的工作队列排序
            configBuidler.defaultDisplayImageOptions(defaultDisplayImageOptions);// 设置默认的DisplayImageOptions
            configBuidler.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000));
            if (cacheDir != null && cacheDir.exists()) {
                configBuidler.discCache(new UnlimitedDiscCache(cacheDir));// 使用无限制缓存，设置缓存位置，文件缓存规则
            } else {
                configBuidler.discCacheFileNameGenerator(fileNameGenerator);// 文件缓存规则
            }
            configBuidler.threadPoolSize(6);// 线程数量，默认3
            ImageLoader.getInstance().init(configBuidler.build());
        }
    }

    /**
     *
     * DisplayImageOptions options;
     options = new DisplayImageOptions.Builder()
     .showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片
     .showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
     .showImageOnFail(R.drawable.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
     .cacheInMemory(true)//设置下载的图片是否缓存在内存中
     .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
     .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
     .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
     .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
     .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
     //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
     //设置图片加入缓存前，对bitmap进行设置
     //.preProcessor(BitmapProcessor preProcessor)
     .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
     .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
     .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
     .build();//构建完成
     *
     *
     * **/

    /**
     *
     * 获取ImageLoader的DisplayImageOptions
     *
     * @param cacheInMemory 使用内存缓存 ,如果OOM则应该关闭内存缓存
     * @param ic_loading 加载中图片
     * @param ic_empty 空白时图片
     * @param ic_error 错误时图片
     * @param roundPixels 圆角度数
     * @param durationMillis 淡入时间毫秒
     * @return DisplayImageOptions
     */
    public static DisplayImageOptions getDisplayImageOptions(boolean cacheInMemory,boolean isZoom, int ic_loading, int ic_empty, int ic_error, int roundPixels, int durationMillis) {
        // LogUtils.i(LOG_TAG, "getDisplayImageOptions :  ic_stub " + ic_loading + " , ic_empty " + ic_empty + " , ic_error " + ic_error + " , roundPixels " + roundPixels + " , durationMillis " + durationMillis);
        DisplayImageOptions.Builder optionsBuilder = new DisplayImageOptions.Builder();
        optionsBuilder.bitmapConfig(Bitmap.Config.RGB_565);// 图片格式,RGB_565可以避免OOM
        optionsBuilder.cacheOnDisk(true);// 文件缓存
        optionsBuilder.cacheInMemory(cacheInMemory);// 内存缓存,开启后更容易OOM,如果容易OOM应该关闭此配置
        if (isZoom) {
            optionsBuilder.imageScaleType(ImageScaleType.EXACTLY);
        }else{
            optionsBuilder.imageScaleType(ImageScaleType.NONE);// 缩放模式，IN_SAMPLE_INT或者EXACTLY可以避免OOM
        }
        if (ic_loading > 0) {
            optionsBuilder.showImageOnLoading(ic_loading);
        }
        if (ic_empty > 0) {
            optionsBuilder.showImageForEmptyUri(ic_empty);
        }
        if (ic_error > 0) {
            optionsBuilder.showImageOnFail(ic_error);
        }
        if (roundPixels > 0) {
            optionsBuilder.displayer(new RoundedBitmapDisplayer(roundPixels));// 设置展现效果为圆角
        }
        if (durationMillis > 0) {
            optionsBuilder.displayer(new FadeInBitmapDisplayer(durationMillis));// 设置展现效果为淡入
        }
        optionsBuilder.resetViewBeforeLoading(false);
        // return DisplayImageOptions.createSimple();创建最简单的DisplayImageOptions
        return optionsBuilder.build();
    }


    public static void destory() {
        ImageLoader.getInstance().destroy();
    }

    public static void clearDiscCache() {
        ImageLoader.getInstance().clearDiscCache();
    }

}
