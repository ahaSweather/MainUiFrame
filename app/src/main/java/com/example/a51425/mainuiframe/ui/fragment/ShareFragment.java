package com.example.a51425.mainuiframe.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.constant.ConfigUtil;
import com.example.a51425.mainuiframe.constant.Constant;
import com.example.a51425.mainuiframe.interfaces.GetResultListener;
import com.example.a51425.mainuiframe.ui.activity.MainActivity;
import com.example.a51425.mainuiframe.ui.dialogFragment.ShareLoadingDialogFragment;
import com.example.a51425.mainuiframe.ui.fragment.IView.IShareFragmentView;
import com.example.a51425.mainuiframe.ui.presenter.HomeFragmentPresenter;
import com.example.a51425.mainuiframe.ui.presenter.ShareFragmentPresenter;
import com.example.a51425.mainuiframe.utils.LogUtil;
import com.example.a51425.mainuiframe.utils.ShareUtils;
import com.example.a51425.mainuiframe.utils.StatusBarUtil;
import com.example.a51425.mainuiframe.utils.ToastUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;



public class ShareFragment extends MyBaseFragment implements IShareFragmentView {



    @BindView(R.id.tv_base_title)
    TextView mTitle;
    private ShareFragmentPresenter mShareFragmentPresenter;
    private MainActivity mainActivity;
    private String shareTitle = "有问题吗？真的有问题吗？";
    private String shareContent = "请点击查看答案";
    private String shareImageUrl = "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg";
    private String jumpUrl = "www.baidu.com";
    private SendMessageToWX.Req localReq;
    private Activity localActivity;

    @Override
    public void initView() {
        StatusBarUtil.setColor(getmActivity(),getResources().getColor(R.color.colorPrimary),0);

    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        mTitle.setText("hello");
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

    @OnClick({R.id.btn_shareWX1,R.id.btn_shareWX2,R.id.btn_shareWX3, R.id.btn_shareWX4, R.id.btn_shareWX5})
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

                ShareUtils.saveFile(shareUrl,imagePath, new GetResultListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ShareUtils.throughIntentShareWXImage(uri);
                    }

                    @Override
                    public void onError() {

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

                ShareUtils.saveFile(url,imagePath2, new GetResultListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ShareUtils.throughIntentShareWXCircle("hello",uri);
                    }

                    @Override
                    public void onError() {

                    }
                });
                break;

        }
    }




}
