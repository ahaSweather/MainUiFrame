package com.example.a51425.mainuiframe.ui.ShareTask;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.cyxk.wrframelibrary.base.MyBaseFragment;
import com.cyxk.wrframelibrary.framework.CallBackListener;
import com.cyxk.wrframelibrary.utils.ToastUtil;
import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.data.DataSource;
import com.example.a51425.mainuiframe.databinding.ShareFragmentBinding;
import com.example.a51425.mainuiframe.ui.dialogFragment.ShareLoadingDialogFragment;
import com.example.a51425.mainuiframe.utils.ShareUtils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;


public class ShareFragment extends MyBaseFragment<ShareFragmentBinding> implements ShareContract.View<ShareFragmentPresenter>, View.OnClickListener {

    private String shareTitle = "有问题吗？真的有问题吗？";
    private String shareContent = "请点击查看答案";
    private String shareImageUrl = "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg";
    private String jumpUrl = "www.baidu.com";
    private SendMessageToWX.Req localReq;
    private Activity localActivity;
    private ShareFragmentPresenter mShareFragmentPresenter;

    @Override
    protected void initPresenter() {
        mShareFragmentPresenter = new ShareFragmentPresenter(this, DataSource.getInstance());
        mShareFragmentPresenter.attachView(mActivity);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        bindingView.btnShareWX1.setOnClickListener(this);
        bindingView.btnShareWX2.setOnClickListener(this);
        bindingView.btnShareWX3.setOnClickListener(this);
        bindingView.btnShareWX4.setOnClickListener(this);
        bindingView.btnShareWX5.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        stateLayout.showContentView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.share_fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shareWX1:
                //分享到微信好友
                ShareLoadingDialogFragment shareLoading = new ShareLoadingDialogFragment();
                shareLoading.showDialog(mActivity, "shareLoadingDialog");
                mShareFragmentPresenter.throughSdkShareWXFriends(mActivity, shareTitle, shareContent, shareImageUrl, jumpUrl, 0, shareLoading);
                break;
            case R.id.btn_shareWX2:
                //分享到微信朋友圈
                ShareLoadingDialogFragment shareLoading2 = new ShareLoadingDialogFragment();
                shareLoading2.showDialog(mActivity, "shareLoadingDialog");
                mShareFragmentPresenter.throughSdkShareWXFriends(mActivity, shareTitle, shareContent, shareImageUrl, jumpUrl, 1, shareLoading2);

                break;
            case R.id.btn_shareWX3:
                //通过intent 分享到微信好友（只分享图片）
                String shareUrl = "http://bo.5173cdn.com/5173_2/data/201705/02/36/RQKowFknx1cAAAAAAACtQXcf_5g23.jpg";
                String filePath = ShareUtils.createPhotoFile();
                if (TextUtils.isEmpty(filePath)) {
                    ToastUtil.showToast(APP.getContext(),
                            "请检查是否插入SD卡");
                    return;
                }
                final String imagePath = filePath + "/"
                        + "shareWx.png";

                ShareUtils.saveFile(shareUrl, imagePath, new CallBackListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ShareUtils.throughIntentShareWXImage(uri);
                    }

                    @Override
                    public void onFailure() {

                    }

                });
                break;
            case R.id.btn_shareWX4:
                ShareUtils.throughIntentShareWXdesc("hello");
                break;
            case R.id.btn_shareWX5:
                String url = "http://bo.5173cdn.com/5173_2/data/201705/02/36/RQKowFknx1cAAAAAAACtQXcf_5g23.jpg";
                String path = ShareUtils.createPhotoFile();
                if (TextUtils.isEmpty(path)) {
                    ToastUtil.showToast(APP.getContext(),
                            "请检查是否插入SD卡");
                    return;
                }
                final String imagePath2 = path + "/"
                        + "shareWx.png";

                ShareUtils.saveFile(url, imagePath2, new CallBackListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ShareUtils.throughIntentShareWXCircle("hello", uri);
                    }

                    @Override
                    public void onFailure() {

                    }

                });
                break;

        }
    }

    @Override
    public void setPresenter(ShareFragmentPresenter presenter) {
        mShareFragmentPresenter = presenter;
    }

}
