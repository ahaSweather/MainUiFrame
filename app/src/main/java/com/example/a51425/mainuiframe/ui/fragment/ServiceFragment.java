package com.example.a51425.mainuiframe.ui.fragment;

import android.view.View;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.utils.LogUtil;

/**
 * Created by 51425 on 2017/3/25.
 */
public class ServiceFragment extends MyBaseFragment {


    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        LogUtil.e(getClass().getName()+"_________initData");
        stateLayout.showContentView();
    }

    @Override
    public View getContentView() {
        View inflate = View.inflate(getActivity(), R.layout.fragment, null);
        return inflate;
    }
}
