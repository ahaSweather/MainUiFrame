package com.example.a51425.mainuiframe.ui.fragment;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.ui.activity.MainActivity;
import com.example.a51425.mainuiframe.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyFragment extends MyBaseFragment {
    @BindView(R.id.tv_show)
    TextView mTvShow;
    @BindView(R.id.ll_status_height)
    LinearLayout mStatusHeight;
    @BindView(R.id.tv_base_title)
    TextView mTitle;
    private MainActivity mainActivity;


    @Override
    public void initView() {
        mainActivity = (MainActivity) mActivity;

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        mTitle.setText("hello1");
        LogUtil.e(getClass().getName()+"_________initData");
        stateLayout.showFailView();
    }

    @Override
    public View getContentView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment, null);

        return view;
    }


}
