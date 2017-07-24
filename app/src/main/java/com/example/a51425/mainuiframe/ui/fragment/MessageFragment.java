package com.example.a51425.mainuiframe.ui.fragment;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.MyBaseFragment;
import com.example.a51425.mainuiframe.ui.activity.MainActivity;
import com.example.a51425.mainuiframe.ui.presenter.MessageFragmentPresenter;
import com.example.a51425.mainuiframe.utils.LogUtil;
import com.example.a51425.mainuiframe.utils.SharedPreferanceUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;


public class MessageFragment extends MyBaseFragment {

    @BindView(R.id.tv_base_title)
    TextView mTitle;
    @BindView(R.id.webView)
    WebView mWebView;
    private MainActivity mainActivity;
    private SharedPreferanceUtils sharedPreferanceUtils;
    private MessageFragmentPresenter messageFragmentPresenter;




    @Override
    public View getContentView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_message, null);
        sharedPreferanceUtils = new SharedPreferanceUtils();
        messageFragmentPresenter = new MessageFragmentPresenter(this);
        return view;
    }
    @Override
    public void initView() {
        mainActivity = (MainActivity) mActivity;
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
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
    public void initListener() {

    }

    @Override
    public void initData() {
        mTitle.setText("hello2");
        LogUtil.e(getClass().getName()+"_________initData");
        mWebView.loadUrl(" file:///android_asset/helloWorld.html");
        stateLayout.showContentView();
    }



}
