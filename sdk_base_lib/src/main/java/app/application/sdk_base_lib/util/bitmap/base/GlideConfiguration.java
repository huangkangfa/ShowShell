package app.application.sdk_base_lib.util.bitmap.base;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import app.application.sdk_base_lib.BaseApplication;


/**
 * Created by huangkangfa on 2017/3/14.
 * Glide配置文件
 */
public class GlideConfiguration implements GlideModule {
    private final String ImageExternalCatchDir = BaseApplication.getContext().getExternalCacheDir() + "/image_cache";
    private final String ImageCatchDir = BaseApplication.getContext().getCacheDir() + "/image_cache";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {


        /**
         * 1.设置Glide内存缓存大小
         */
        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));



        /**
         * 2.设置Glide磁盘缓存大小
         */
//        File cacheDir = context.getCacheDir();//指定的是数据的缓存地址
        int diskCacheSize = 1024 * 1024 * 100;//最多可以缓存多少字节的数据
        //设置磁盘缓存大小
        builder.setDiskCache(new DiskLruCacheFactory(ImageCatchDir, "glide", diskCacheSize));
//        //存放在data/data/xxxx/cache/
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
//        //存放在外置文件浏览器
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "glide", diskCacheSize));


        /**
         * 3.设置图片解码格式
         * 默认格式RGB_565使用内存是ARGB_8888的一半，但是图片质量就没那么高了，而且不支持透明度
         */
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);


        /**
         * 4.设置缓存内存大小
         */
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}