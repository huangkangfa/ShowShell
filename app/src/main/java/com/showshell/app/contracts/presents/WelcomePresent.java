package com.showshell.app.contracts.presents;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.showshell.app.contracts.WelcomeContract;
import com.showshell.app.global.Config;
import com.showshell.app.ui.GoManager;
import com.showshell.app.ui.activity.WelcomeActivity;

import app.application.sdk_base_lib.act.BaseActivity;
import app.application.sdk_base_lib.util.SPUtils;

/**
 * Created by huangkangfa on 2017/12/26.
 */

public class WelcomePresent extends WelcomeContract.WelcomePresenter {
    private static String TAG;

    private Context mContext;

    private int WAIT_TIME = 500;                //决定跳转后的延时时间 单位毫秒

    private int ADVERTISEMENT_TIME = 5;         //广告倒计时时间  单位秒

    private boolean isFirst = true;             //是否是第一次使用这个APP

    private boolean isLogin = false;            //用户是否处于登录状态

    private boolean isShowADOver = false;       //广告展示是否结束

    public WelcomePresent(Context mContext) {
        init(mContext);
    }

    private void init(Context mContext) {
        this.mContext = mContext;
        TAG = mContext.getClass().getSimpleName();
    }


    @Override
    public void next() {
         new Thread(){
             @Override
             public void run() {
                 super.run();
                 showAd();
                 goNext();
             }
         }.start();
    }

    //广告倒计时
    private void showAd() {
        while (!isShowADOver && ADVERTISEMENT_TIME >= 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ADVERTISEMENT_TIME--;
            handler.sendEmptyMessageDelayed(0, 0);
        }
        isShowADOver = true;
    }

    //跳转
    private void goNext() {
        isFirst = SPUtils.getInstance().getBoolean(Config.IS_FIRST, true);
        if (isFirst) {
            SPUtils.getInstance().putBoolean(Config.IS_FIRST, false);
            handler.sendEmptyMessageDelayed(1, WAIT_TIME);
        } else {
            handler.sendEmptyMessageDelayed(2, WAIT_TIME);
        }
    }

    @Override
    public void setADShowOver(boolean flag) {
        isShowADOver=flag;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //更新广告倒计时
                    mView.updateAdTime(ADVERTISEMENT_TIME);
                    break;
                case 1:
                    //引导界面跳转
                    GoManager.goGuideActivity((BaseActivity) mContext, WelcomeActivity.INTRO_CODE);
                    break;
                case 2:
                    //根据用户的登录状态跳转登录界面还是home界面
                    isLogin = SPUtils.getInstance().getBoolean(Config.USER_IS_LOGIN, false);
                    if (isLogin) {
                        GoManager.goHomeActivity((BaseActivity) mContext);
                    } else {
                        GoManager.goLoginActivity((BaseActivity) mContext);
                    }
                    break;

            }
        }
    };
}
