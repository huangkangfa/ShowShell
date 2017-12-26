package app.application.sdk_base_lib.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.application.sdk_base_lib.act.BaseFragmentActivity;
import butterknife.ButterKnife;


/**
 * 基础碎片类
 * Created by huangkangfa on 2017/10/11.
 */
public class BaseFragment extends Fragment{

    public BaseFragmentActivity mActivity;
    public LayoutInflater mInflater;
    public FragmentManager mFManager;
    public Bundle mBundle;
    public Fragment mFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = (BaseFragmentActivity) getActivity();
        this.mFManager = mActivity.mFManager;
        this.mBundle = getArguments();  //碎片实例化时返回的数据
        this.mFragment = this;
    }


    public View setContentView(LayoutInflater inflater, int layoutResID, ViewGroup container) {
        return setContentView(inflater, layoutResID, container, false);
    }

    public View setContentView(LayoutInflater inflater, int layoutResID, ViewGroup container, boolean attachToRoot) {
        this.mInflater = inflater;
        View viewRoot = inflater.inflate(layoutResID, container, attachToRoot);
        return viewRoot;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
