package com.example.a51425.mainuiframe.ui.ShareTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.cyxk.wrframelibrary.base.BaseActivity;
import com.cyxk.wrframelibrary.data.api.ApiException;
import com.cyxk.wrframelibrary.utils.AppUtils;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.ToastUtil;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.data.DataSource;
import com.example.a51425.mainuiframe.ui.dialogFragment.ShareLoadingDialogFragment;
import com.example.a51425.mainuiframe.utils.ShareUtils;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 51425 on 2017/6/12.
 */

public class ShareFragmentPresenter extends ShareContract.Presenter<BaseActivity> {

    private final DataSource mShareLogic;
    private final ShareContract.View mView;
    private String shareAppId;
    private String shareAppPackageName;

    public ShareFragmentPresenter(ShareContract.View view, DataSource dataSource) {
        mShareLogic = dataSource;
        mView = view;
    }

    /**
     * 通过intent 分享内容给微信好友
     *
     * @param content 分享描述
     */
    public void throughIntentShareWXFriends(String content, int type) {
        if (!AppUtils.checkApkExist(getView(), "com.tencent.mm")) {
            ToastUtil.showToast(getView(), "亲，你还没安装微信");
            return;
        }
        if (content == null || "".equals(content)) {
            return;
        }
        if (type == 0) {
            ShareUtils.throughIntentShareWXdesc(content);
        } else if (type == 1) {
        }
    }

    /**
     * @param shareTiele   分享的标题
     * @param shareContent 分享的内容
     * @param shareImage   分享的图片
     * @param jumpUrl      分享出去跳转的链接
     * @param type         分享到哪里
     */
    public void throughSdkShareWXFriends(final Activity context, final String shareTiele, final String shareContent, final String shareImage, final String jumpUrl, final int type, final ShareLoadingDialogFragment shareLoading) {
        Observable.just(ShareUtils.shareWXReadyRx(shareImage))
                .map(new Function<String[], Bitmap>() {
                    @Override
                    public Bitmap apply(String[] strings) throws Exception {
                        if (strings == null) {
                            LogUtil.e("没有任何可以分享的平台");
                            //这里根据需求来修改，我们是如果没有任何可以而分享的平台就走intent　方式进行分享，utils中也有，这里就不细写了
                            throw new ApiException("没有任何可以分享的平台");
                        } else {
                            shareAppId = strings[0];
                            shareAppPackageName = strings[1];
                            LogUtil.e("分享的appId:::" + shareAppId + "////" + shareAppPackageName);
                            String sharImage = strings[2];
                            if (TextUtils.isEmpty(shareImage)) {
                                return BitmapFactory.decodeResource(context.getResources(), R.drawable.wx);
                            }
                            return ShareUtils.getHttpBitmap(sharImage);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    public void accept(Bitmap bitmap) throws Exception {
                        if (shareLoading != null) {
                            shareLoading.dismissDialog(context);
                        }
                        ShareUtils.shareWXRX(new WeakReference<Activity>(context), shareAppId, shareAppPackageName, shareTiele
                                , shareContent, jumpUrl, type, bitmap
                        );
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (shareLoading != null) {
                            shareLoading.dismissDialog(context);
                        }
                        LogUtil.e("share_error___" + throwable.toString());
                    }
                });
    }

    @Override
    protected void subscribe() {
        super.subscribe();

    }

    @Override
    protected void unsubscribe() {

        super.unsubscribe();
    }
}
