package com.example.a51425.mainuiframe.ui.fragment;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.utils.LogUtil;

import butterknife.BindView;


public class MessageFragment extends MyBaseFragment {

    @BindView(R.id.ll_status_height)
    LinearLayout mStatusHeight;
    @Override
    public void initView() {
        stateLayout.showStatusBar(true,getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        LogUtil.e(getClass().getName()+"_________initData");
        stateLayout.showEmptyView();
    }

    @Override
    public View getContentView() {
        return LayoutInflater.from(mActivity).inflate(R.layout.fragment,null);
    }
}
