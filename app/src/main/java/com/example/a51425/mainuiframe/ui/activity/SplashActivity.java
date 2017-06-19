package com.example.a51425.mainuiframe.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.a51425.mainuiframe.R;
import com.example.a51425.mainuiframe.base.BaseActivity;
import com.example.a51425.mainuiframe.ui.serivce.JobHandlerService;
import com.example.a51425.mainuiframe.ui.serivce.LocalService;
import com.example.a51425.mainuiframe.ui.serivce.RemoteService;
import com.example.a51425.mainuiframe.utils.LogUtil;

/**
 * Created by 51425 on 2017/6/15.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void beforeLoading() {
        super.beforeLoading();
        // 防止home键后部分情况再点击app重新加载（尤其是在360加固后）
        try{
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }
        setBaseTitleStatus(false);
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_splash, null);
        //不加入侧滑
        showSlidr = false;
        hideStatusBar = true;
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToActivity(SplashActivity.this,MainActivity.class,null);
//                startService(new Intent(SplashActivity.this, LocalService.class));
//                startService(new Intent(SplashActivity.this, RemoteService.class));
//                startService(new Intent(SplashActivity.this, JobHandlerService.class));
                finish();
            }
        },0);
    }
}
