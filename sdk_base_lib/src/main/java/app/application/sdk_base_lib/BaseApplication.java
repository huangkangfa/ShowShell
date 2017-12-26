package app.application.sdk_base_lib;

import android.app.Application;
import android.content.Context;

import app.application.sdk_base_lib.util.ServiceUtils;


/**
 * Created by huangkangfa on 2017/3/14 0014.
 */
public class BaseApplication extends Application{
    protected static Context context;  //全局上下文对象

    /**
     * 每次app开启会调用
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        if(!ServiceUtils.isServiceRunning(context,"app.application.sdk_base_lib.InitService")){
            InitService.start(context);
        }
    }

    /**
     * 全局上下文对象
     */
    public static Context getContext() {
        return context;
    }
}
