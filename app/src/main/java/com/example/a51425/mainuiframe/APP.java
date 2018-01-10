package com.example.a51425.mainuiframe;

import android.app.Activity;
import android.content.Context;

import com.cyxk.wrframelibrary.utils.LogUtil;
import com.tencent.smtt.sdk.QbSdk;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by XinYue on 2016/12/14.
 */

public class APP extends com.cyxk.wrframelibrary.base.APP {

    @Override
    protected void init() {
        initX5();
    }

    //    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//			// This process is dedicated to LeakCanary for heap analysis.
//			// You should not init your app in this process.
//			return;
//		}
//		LeakCanary.install(this);
//    }
    private void initX5() {
//        Intent intent = new Intent(this, PreLoadX5Service.class);
//        startService(intent);
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.e("app+++ onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);



    }

}
