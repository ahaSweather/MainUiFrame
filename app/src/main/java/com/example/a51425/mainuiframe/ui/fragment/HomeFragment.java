package com.example.a51425.mainuiframe.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 51425 on 2017/3/25.
 */
public class HomeFragment extends MyBaseFragment {
    @BindView(R.id.tv_show)
    TextView mTvShow;



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
        CharSequence text = mTvShow.getText();
        LogUtil.e(getClass().getName()+"_text_"+text);
        mTvShow.setText("showContentView");
    }

    @Override
    public View getContentView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment, null);

        return view;
    }


}
