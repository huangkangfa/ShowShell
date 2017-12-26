package com.showshell.app.ui;

import android.content.Intent;

import com.showshell.app.R;
import com.showshell.app.ui.activity.GuideActivity;
import com.showshell.app.ui.activity.HomeActivity;
import com.showshell.app.ui.activity.LoginActivity;
import com.showshell.app.ui.activity.WelcomeActivity;

import app.application.sdk_base_lib.act.BaseActivity;

/**
 * 跳转管理
 *
 * Created by huangkangfa on 2017/12/25.
 */

public class GoManager {

    //跳转至欢迎界面
    public static void goWelComeActivity(BaseActivity activity){
        Intent it=new Intent(activity, WelcomeActivity.class);
        activity.startActivity(it);
        activity.overridePendingTransition(R.anim.start_to_activity, R.anim.finish_to_activity);
    }

    //跳转至登录界面
    public static void goLoginActivity(BaseActivity activity){
        Intent it=new Intent(activity, LoginActivity.class);
        activity.startActivity(it);
        activity.overridePendingTransition(R.anim.start_to_activity, R.anim.finish_to_activity);
    }

    //跳转至引导界面
    public static void goGuideActivity(BaseActivity activity,int code){
        Intent it=new Intent(activity, GuideActivity.class);
        activity.startActivityForResult(it,code);
        activity.overridePendingTransition(R.anim.start_to_activity, R.anim.finish_to_activity);
    }

    //跳转至Home界面
    public static void goHomeActivity(BaseActivity activity){
        Intent it=new Intent(activity, HomeActivity.class);
        activity.startActivity(it);
        activity.overridePendingTransition(R.anim.start_to_activity, R.anim.finish_to_activity);
    }

}
