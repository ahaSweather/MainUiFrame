package com.example.a51425.mainuiframe.ui.activity;

import android.content.Intent;
import android.util.Log;

import com.cyxk.wrframelibrary.base.BaseActivity;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.example.a51425.mainuiframe.R;

/**
 * Created by 51425 on 2017/6/15.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void unRegister() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean isSetSwipeBack() {
        return false;
    }

    @Override
    protected void beforeLoading() {
        super.beforeLoading();
        // 防止home键后部分情况再点击app重新加载（尤其是在360加固后）
        try {
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e));
        }
    }

    @Override
    protected void initData() {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToActivity(SplashActivity.this, MainActivity.class, null, true);
//                startService(new Intent(SplashActivity.this, LocalService.class));
//                startService(new Intent(SplashActivity.this, RemoteService.class));
//                startService(new Intent(SplashActivity.this, JobHandlerService.class));
                finishActivity(true);
            }
        }, 0);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }
}
