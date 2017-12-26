package com.showshell.app.contracts;

import app.application.sdk_base_lib.mvp.BasePresenter;
import app.application.sdk_base_lib.mvp.IBaseView;

/**
 * Created by huangkangfa on 2017/12/25.
 */

public class WelcomeContract {
    public static interface WelcomeView extends IBaseView {
        void updateAdTime(int time);
    }

    public static abstract class WelcomePresenter extends BasePresenter<WelcomeView> {
        public abstract void next();
        public abstract void setADShowOver(boolean flag);
    }
}
