package app.application.sdk_base_lib.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangkangfa on 2017/12/14.
 */

public class PermissionUtils {

    /**
     * 申请权限
     * @param activity
     * @param permissions
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void applyPermissions(Activity activity,String[] permissions) {
//        //Android 6.0以下不必申请
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)
//            return ;
        List<String> needApply=new ArrayList<>();
        for(String x:permissions){
            //对应的权限还没被允许
            if(activity.checkSelfPermission(x) != PackageManager.PERMISSION_GRANTED){
                needApply.add(x);
            }
        }
        if(needApply.size()>0){
            int requestCode = 200;
            String[] result = needApply.toArray(new String[needApply.size()]);  //集合转数组
//            List list = Arrays.asList(permissions);   //数组转集合
            activity.requestPermissions(result, requestCode);
        }
    }


    /**
     * 跳转至对应应用的权限设置详情界面
     * @param context
     */
    public static void setPermissions(Context context){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }


    /**
     * 检查权限是否全被允许了
     * @param activity
     * @param permissions
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermissions(Activity activity,String[] permissions){
//        //Android 6.0以下不必申请
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1)
//            return true;
        for(String x:permissions){
            //对应的权限还没被允许
            if(activity.checkSelfPermission(x) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}
