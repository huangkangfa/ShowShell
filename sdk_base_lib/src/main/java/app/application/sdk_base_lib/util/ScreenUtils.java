package app.application.sdk_base_lib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import app.application.sdk_base_lib.BaseApplication;


/**
 * Created by huangkangfa on 2017/3/15 0015.
 */
public class ScreenUtils {
    private static int displayWidth;  //屏幕宽度
    private static int displayHeight; //屏幕高度

    /**
     * 初始化 宽高
     * @param context
     */
    public static void init(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager mWindowManager  = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth=displayMetrics.widthPixels;
        displayHeight=displayMetrics.heightPixels;
    }

    /**
     * 获取百分比的宽度
     * @param percent
     * @return
     */
    public static float getWidthPercent(float percent){
        if(percent>1f||percent<0f){
            return -1;
        }
        return displayWidth*percent+0.5f;
    }
    /**
     * 获取百分比的高度
     * @param percent
     * @return
     */
    public static float getHeightPercent(float percent){
        if(percent>1f||percent<0f){
            return -1;
        }
        return displayHeight*percent+0.5f;
    }

    /**
     * 获得状态栏的高度
     * @return
     */
    public static int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = BaseApplication.getContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获得手机屏幕宽度得到像素px
     */
    public static int getWidth() {
        return BaseApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得手机屏幕高度得到像素px
     */
    public static int getHeight() {
        return BaseApplication.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @return
     */
    public static Bitmap snapShotWithStatusBar() {
        View view = ((Activity) BaseApplication.getContext()).getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getWidth();
        int height = getHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar() {
        View view = ((Activity) BaseApplication.getContext()).getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        ((Activity) BaseApplication.getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getWidth();
        int height = getHeight();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }
}
