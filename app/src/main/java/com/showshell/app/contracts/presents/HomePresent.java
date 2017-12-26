package com.showshell.app.contracts.presents;

import android.content.Context;

import com.showshell.app.contracts.HomeContract;

/**
 * Created by huangkangfa on 2017/12/25.
 */

public class HomePresent extends HomeContract.HomePresenter{
    private static String TAG;
    private Context mContext;

    public HomePresent(Context mContext){
        init(mContext);
    }

    private void init(Context mContext) {
        this.mContext=mContext;
        TAG=mContext.getClass().getSimpleName();
    }

    @Override
    public void attachView(HomeContract.HomeView IView) {
        super.attachView(IView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
