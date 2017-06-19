package com.example.a51425.mainuiframe.ui.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.BaseActivity;
import com.example.a51425.mainuiframe.bean.HomeFragmentBean;
import com.example.a51425.mainuiframe.utils.LogUtil;
import com.example.a51425.mainuiframe.utils.ToastUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by 51425 on 2017/6/15.
 */
public class WebViewActivity extends BaseActivity{
    @BindView(R.id.webView)
    public WebView mWebView;
//    @BindView(R.id.fl_web)
//    public FrameLayout mFrameLayout;
    private HomeFragmentBean mBean;


    @Override
    protected void beforeLoading() {
        super.beforeLoading();
        //解决部分机型进来闪烁
        try{
            getWindow().setFormat(PixelFormat.TRANSLUCENT);
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }
        setBaseTitleStatus(false);
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_web, null);
        showSlidr = true;
        hideStatusBar = true;
        return view;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String mUrl) {
                view.loadUrl(mUrl);
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mBean = (HomeFragmentBean) getIntent().getSerializableExtra("bean");
        mWebView.setHapticFeedbackEnabled(false);
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        //隐藏缩放按钮
        mWebSettings.setBuiltInZoomControls(true);
        //设置用鼠标激活被选项
        mWebSettings.setLightTouchEnabled(true);
        //设置支持变焦
        mWebSettings.setSupportZoom(true);
        //禁止缓存
        mWebSettings.setAppCacheEnabled(false);
        mWebSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        mWebSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        mWebSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

        });
    }

    @Override
    protected void initData() {
        super.initData();
        mWebView.loadUrl(mBean.getJumpUrl());
        LogUtil.e("loadurl____"+mBean.getJumpUrl());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroy();


    }
//
//    /**
//     * 防止webview缩放时退出崩溃。
//     */
//    @Override
//    public void finish() {
//        ViewGroup view = (ViewGroup) getWindow().getDecorView();
//        view.removeAllViews();
//        super.finish();
//    }


    public void destroy() {
        try{
            if (mWebView != null) {
                ViewParent parent = mWebView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(mWebView);
                }
                mWebView.stopLoading();
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                mWebView.getSettings().setJavaScriptEnabled(false);
                mWebView.setTag(null);
                mWebView.clearHistory();
                mWebView.clearView();
                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
//                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//                activityManager.killBackgroundProcesses(getPackageName());
            }
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }

    }

//
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
//        // TODO Auto-generated method stub
        try {
            super.onConfigurationChanged(newConfig);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            }
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
