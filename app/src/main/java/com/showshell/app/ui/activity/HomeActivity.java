package com.showshell.app.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.showshell.app.R;
import com.showshell.app.contracts.HomeContract;
import com.showshell.app.contracts.presents.HomePresent;

import app.application.sdk_base_lib.act.ActivityManager;
import app.application.sdk_base_lib.act.BaseFragmentActivity;
import app.application.sdk_base_lib.util.ToastUtils;
import butterknife.Bind;

public class HomeActivity extends BaseFragmentActivity implements HomeContract.HomeView {
    @Bind(R.id.content)
    RelativeLayout content;
    @Bind(R.id.bottom_index_0_img)
    ImageView bottomIndex0Img;
    @Bind(R.id.bottom_index_0_txt)
    TextView bottomIndex0Txt;
    @Bind(R.id.bottom_index_0)
    RelativeLayout bottomIndex0;
    @Bind(R.id.bottom_index_1_img)
    ImageView bottomIndex1Img;
    @Bind(R.id.bottom_index_1_txt)
    TextView bottomIndex1Txt;
    @Bind(R.id.bottom_index_1)
    RelativeLayout bottomIndex1;
    @Bind(R.id.bottom_index_2_img)
    ImageView bottomIndex2Img;
    @Bind(R.id.bottom_index_2_txt)
    TextView bottomIndex2Txt;
    @Bind(R.id.bottom_index_2)
    RelativeLayout bottomIndex2;
    @Bind(R.id.bottom_index_3_img)
    ImageView bottomIndex3Img;
    @Bind(R.id.bottom_index_3_txt)
    TextView bottomIndex3Txt;
    @Bind(R.id.bottom_index_3)
    RelativeLayout bottomIndex3;
    @Bind(R.id.bottom_index_4_img)
    ImageView bottomIndex4Img;
    @Bind(R.id.bottom_index_4_txt)
    TextView bottomIndex4Txt;
    @Bind(R.id.bottom_index_4)
    RelativeLayout bottomIndex4;


    private HomePresent presenter;

    private boolean isExit = false;  //退出标识


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        presenter = new HomePresent(mActivity);
        presenter.attachView(this);
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
        presenter.detachView();
    }

    //底部栏控制切换fragment
    @Override
    public void changeFragment(int index) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isExit) {
                ActivityManager.getActivity().finishAll();
            } else {
                isExit = true;
                ToastUtils.showShort(getString(R.string.activity_home_exit));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
