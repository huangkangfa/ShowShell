package com.showshell.app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.showshell.app.R;
import com.showshell.app.contracts.WelcomeContract;
import com.showshell.app.contracts.presents.WelcomePresent;

import app.application.sdk_base_lib.act.BaseActivity;

/**
 * Created by huangkangfa on 2017/12/25.
 */

public class WelcomeActivity extends BaseActivity implements WelcomeContract.WelcomeView {

    public static final int INTRO_CODE = 2;                 //引导页标识
    private WelcomePresent presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        init();
    }

    private void init() {
        presenter = new WelcomePresent(mActivity);
        presenter.attachView(this);
        presenter.next();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!presenter.isViewAttach()) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.setADShowOver(true);
        presenter.detachView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == INTRO_CODE) {
            presenter.next();
        }
    }

    @Override
    public void updateAdTime(int time) {

    }
}
