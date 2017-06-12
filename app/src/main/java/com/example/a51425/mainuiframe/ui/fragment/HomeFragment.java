package com.example.a51425.mainuiframe.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.ui.dialogFragment.ShareLoadingDialogFragment;
import com.example.a51425.mainuiframe.ui.activity.MainActivity;
import com.example.a51425.mainuiframe.ui.fragment.IView.IHomeFragmentView;
import com.example.a51425.mainuiframe.ui.presenter.HomeFragmentPresenter;
import com.example.a51425.mainuiframe.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeFragment extends MyBaseFragment implements IHomeFragmentView {
    @BindView(R.id.tv_show)
    TextView mTvShow;
    @BindView(R.id.btn_shareWX1)
    Button mShareButton1;
    @BindView(R.id.btn_shareWX2)
    Button mShareButton2;

    private HomeFragmentPresenter mHomeFragmentPresenter;
    private MainActivity mainActivity;
    String shareTitle = "呵呵";
    String shareContent = "嘿嘿";
    String shareImageUrl ="http://img2.3lian.com/2014/f2/37/d/40.jpg";
    String jumpUrl = "www.baidu.com";

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
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment, null);
        mainActivity = (MainActivity) mActivity;
        mHomeFragmentPresenter = new HomeFragmentPresenter(this);
        return view;
    }


    @OnClick({R.id.btn_shareWX1,R.id.btn_shareWX2})
    public void onCick(View view){
        switch (view.getId()){
            case R.id.btn_shareWX1:
                //分享到微信好友
                ShareLoadingDialogFragment shareLoading = new ShareLoadingDialogFragment();
                shareLoading.showDialog(mainActivity, "shareLoadingDialog");
                mHomeFragmentPresenter.throughSdkShareWXFriends(mainActivity,shareTitle,shareContent,shareImageUrl,jumpUrl,0,shareLoading);
                break;
            case R.id.btn_shareWX2:
                //分享到微信朋友圈
                ShareLoadingDialogFragment shareLoading2 = new ShareLoadingDialogFragment();
                shareLoading2.showDialog(mainActivity, "shareLoadingDialog");
                mHomeFragmentPresenter.throughSdkShareWXFriends(mainActivity,shareTitle,shareContent,shareImageUrl,jumpUrl,1,shareLoading2);
                break;
        }
    }



}
