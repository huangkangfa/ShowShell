package app.application.sdk_base_lib.util;

import android.widget.Toast;

import app.application.sdk_base_lib.BaseApplication;

/**
 * Toast统一管理类
 */
public class ToastUtils {

    public static boolean isShow = true;

    /*cannot be instantiated*/
    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(String message) {
        if (isShow)
            Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong( String message) {
        if (isShow)
            Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show( String message, int duration) {
        if (isShow)
            Toast.makeText(BaseApplication.getContext(), message, duration).show();
    }
}