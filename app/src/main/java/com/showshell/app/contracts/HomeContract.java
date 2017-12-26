package com.showshell.app.contracts;

import app.application.sdk_base_lib.mvp.BasePresenter;
import app.application.sdk_base_lib.mvp.IBaseView;

/**
 * Created by huangkangfa on 2017/12/25.
 */

public class HomeContract {
    public static interface HomeView extends IBaseView {
        void changeFragment(int index);
    }

    public static abstract class HomePresenter extends BasePresenter<HomeView> {

    }
}
