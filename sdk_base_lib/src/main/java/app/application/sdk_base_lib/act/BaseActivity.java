package app.application.sdk_base_lib.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import butterknife.ButterKnife;

/**
 * 基础Activity
 * Created by huangkangfa on 2017/10/11.
 */

public class BaseActivity extends AppCompatActivity{
    public BaseActivity mActivity;
    public LayoutInflater mInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivity().add(this);
        mActivity = this;
        mInflater = LayoutInflater.from(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getActivity().remove(this);
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
