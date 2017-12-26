package app.application.sdk_base_lib.util.bitmap;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.application.sdk_base_lib.util.bitmap.base.GlideCatchUtil;
import app.application.sdk_base_lib.util.bitmap.base.GlideCircleTransform;
import app.application.sdk_base_lib.util.bitmap.base.GlideConfiguration;
import app.application.sdk_base_lib.util.bitmap.base.GlideRoundTransform;


/**
 * Created by huangkangfa on 2017/3/14.
 * 封装glide的图片管理类
 * 1.加载静态、动态图片
 * 2.清除图片缓存
 * 3.获取已有缓存大小
 *
 * 注意：KsBitmapManager使用前必须初始化一次，一般放在application中初始化
 */
public class BitmapManager {
    /**
     * 初始化glide设置
     * @param context
     */
    public static void init(Context context){
        GlideConfiguration config=new GlideConfiguration();
        config.applyOptions(context,new GlideBuilder(context));
    }

    /**
     * 加载指定地址的图片
     * @param context
     * @param img
     * @param uri
     */
    public static void display(Context context, ImageView img, String uri){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }
    public static void display(Context context, ImageView img, int uri){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }

    /**
     * 加载指定地址的圆形图片
     * @param context
     * @param img
     * @param uri
     */
    public static void displayCircular(Context context,ImageView img, String uri){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .transform(new GlideCircleTransform(context))//圆角
                .into(img);
    }
    public static void displayCircular(Context context,ImageView img, String uri,int errorImg){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .transform(new GlideCircleTransform(context))//圆角
                .error(errorImg)
                .into(img);
    }
    public static void displayCircular(Context context,ImageView img, int uri){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .transform(new GlideCircleTransform(context))//圆角
                .into(img);
    }
    /**
     * 加载指定地址的圆角图片
     * @param context
     * @param img
     * @param uri
     */
    public static void displayRound(Context context,ImageView img,int round, String uri){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .transform(new GlideRoundTransform(context,round))//圆角
                .into(img);
    }
    public static void displayRound(Context context,ImageView img,int round, int uri){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .transform(new GlideRoundTransform(context,round))//圆角
                .into(img);
    }

    /**
     * 详细设置条件后加载指定地址的图片
     * @param context
     * @param img
     * @param uri
     * @param loading
     * @param error
     * @param p
     * @param skipMemoryCache  //是否跳过内存缓存
     */
    public static void display(Context context, ImageView img, String uri, int loading, int error, Priority p, boolean skipMemoryCache){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .placeholder(loading)  //加载中的图片
                .error(error) //加载失败的图片
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .skipMemoryCache(skipMemoryCache)   //是否跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .priority(p==null? Priority.NORMAL:p)  //普通优先级
                .into(img);
    }
    public static void display(Context context, ImageView img, int uri, int loading, int error, Priority p, boolean skipMemoryCache){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .placeholder(loading)  //加载中的图片
                .error(error) //加载失败的图片
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .skipMemoryCache(skipMemoryCache)   //是否跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .priority(p==null? Priority.NORMAL:p)  //普通优先级
                .into(img);
    }

    /**
     * 详细设置条件后加载指定地址的圆形图片
     * @param context
     * @param img
     * @param uri
     * @param loading
     * @param error
     * @param p
     * @param skipMemoryCache  //是否跳过内存缓存
     */
    public static void displayCircular(Context context, ImageView img, String uri, int loading, int error, Priority p, boolean skipMemoryCache){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .placeholder(loading)  //加载中的图片
                .error(error) //加载失败的图片
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .skipMemoryCache(skipMemoryCache)   //是否跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .priority(p==null? Priority.NORMAL:p)  //普通优先级
                .transform(new GlideCircleTransform(context))//圆形
                .into(img);
    }
    public static void displayCircular(Context context, ImageView img, int uri, int loading, int error, Priority p, boolean skipMemoryCache){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .placeholder(loading)  //加载中的图片
                .error(error) //加载失败的图片
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .skipMemoryCache(skipMemoryCache)   //是否跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .priority(p==null? Priority.NORMAL:p)  //普通优先级
                .transform(new GlideCircleTransform(context))//圆形
                .into(img);
    }

    /**
     * 详细设置条件后加载指定地址的圆角图片
     * @param context
     * @param img
     * @param uri
     * @param loading
     * @param error
     * @param p
     * @param skipMemoryCache  //是否跳过内存缓存
     */
    public static void displayRound(Context context, ImageView img, int round, String uri, int loading, int error, Priority p, boolean skipMemoryCache){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .placeholder(loading)  //加载中的图片
                .error(error) //加载失败的图片
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .skipMemoryCache(skipMemoryCache)   //是否跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .priority(p==null? Priority.NORMAL:p)  //普通优先级
                .transform(new GlideRoundTransform(context,round))//圆角
                .into(img);
    }
    public static void displayRound(Context context, ImageView img, int round, int uri, int loading, int error, Priority p, boolean skipMemoryCache){
        Glide.with(context)
                .load(uri)   //加载的图片地址
                .placeholder(loading)  //加载中的图片
                .error(error) //加载失败的图片
                .centerCrop()  //中心裁剪,缩放填充至整个ImageView
                .skipMemoryCache(skipMemoryCache)   //是否跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .priority(p==null? Priority.NORMAL:p)  //普通优先级
                .transform(new GlideRoundTransform(context,round))//圆角
                .into(img);
    }

    /**
     * 加载动态图
     * @param context
     * @param img
     * @param uri
     */
    public static void displayAsGif(Context context, ImageView img, String uri){
        Glide.with(context)
                .load(uri)
                .asGif()  //转成gif动态图格式
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }
    public static void displayAsGif(Context context, ImageView img, int uri){
        Glide.with(context)
                .load(uri)
                .asGif()  //转成gif动态图格式
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }

    /**
     * 详细设置加载动态图
     * @param context
     * @param img
     * @param uri
     * @param loading
     * @param error
     * @param p
     * @param skipMemoryCache
     */
    public static void displayAsGif(Context context, ImageView img, String uri, int loading, int error, Priority p, boolean skipMemoryCache){
        Glide.with(context)
                .load(uri)
                .asGif()  //转成gif动态图格式
                .placeholder(loading)  //加载中的图片
                .error(error) //加载失败的图片
                .centerCrop()
                .priority(p==null? Priority.NORMAL:p)  //普通优先级
                .skipMemoryCache(skipMemoryCache)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }
    public static void displayAsGif(Context context, ImageView img, int uri, int loading, int error, Priority p, boolean skipMemoryCache){
        Glide.with(context)
                .load(uri)
                .asGif()  //转成gif动态图格式
                .placeholder(loading)  //加载中的图片
                .error(error) //加载失败的图片
                .centerCrop()
                .priority(p==null? Priority.NORMAL:p)  //普通优先级
                .skipMemoryCache(skipMemoryCache)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(img);
    }

    /**
     * 清除图片磁盘缓存
     */
    public static void clearImageDiskCache(){
        GlideCatchUtil.getInstance().clearImageDiskCache();
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache() {
        GlideCatchUtil.getInstance().clearImageMemoryCache();
    }

    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache() {
        GlideCatchUtil.getInstance().clearImageAllCache();
    }

    /**
     * 获取Glide造成的缓存大小
     * @return CacheSize
     */
    public static String getCacheSize() {
        return GlideCatchUtil.getInstance().getCacheSize();
    }
}
