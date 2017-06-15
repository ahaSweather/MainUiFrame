package com.example.a51425.mainuiframe.ui.fragment;

import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.ui.activity.MainActivity;
import com.example.a51425.mainuiframe.ui.dialogFragment.ShareLoadingDialogFragment;
import com.example.a51425.mainuiframe.ui.fragment.IView.IShareFragmentView;
import com.example.a51425.mainuiframe.ui.presenter.HomeFragmentPresenter;
import com.example.a51425.mainuiframe.ui.presenter.ShareFragmentPresenter;
import com.example.a51425.mainuiframe.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class ShareFragment extends MyBaseFragment implements IShareFragmentView {


    @BindView(R.id.ll_status_height)
    public LinearLayout mStatusHeight;
    private ShareFragmentPresenter mShareFragmentPresenter;
    private MainActivity mainActivity;
    private String shareTitle = "他大舅";
    private String shareContent = "他二舅";
    private String shareImageUrl = "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg";
    private String jumpUrl = "www.baidu.com";

    @Override
    public void initView() {
        stateLayout.showStatusBar(true,getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        LogUtil.e(getClass().getName()+"_________initData");
        //正常来说是再返回数据成功时根据数据来判断显示那个布局
        stateLayout.showContentView();
    }



    @Override
    public View getContentView() {
        View inflate = View.inflate(mActivity, R.layout.share_fragment, null);
        mShareFragmentPresenter = new ShareFragmentPresenter(this);
        mainActivity = (MainActivity) mActivity;
        return inflate;
    }


        @OnClick({R.id.btn_shareWX1,R.id.btn_shareWX2})
    public void onCick(View view){
        switch (view.getId()){
            case R.id.btn_shareWX1:
                //分享到微信好友
                ShareLoadingDialogFragment shareLoading = new ShareLoadingDialogFragment();
                shareLoading.showDialog(mainActivity, "shareLoadingDialog");
                mShareFragmentPresenter.throughSdkShareWXFriends(mainActivity,shareTitle,shareContent,shareImageUrl,jumpUrl,0,shareLoading);
                break;
            case R.id.btn_shareWX2:
                //分享到微信朋友圈
                ShareLoadingDialogFragment shareLoading2 = new ShareLoadingDialogFragment();
                shareLoading2.showDialog(mainActivity, "shareLoadingDialog");
                mShareFragmentPresenter.throughSdkShareWXFriends(mainActivity,shareTitle,shareContent,shareImageUrl,jumpUrl,1,shareLoading2);
                break;
        }
    }

}
