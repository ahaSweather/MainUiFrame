package com.example.a51425.mainuiframe;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.example.a51425.mainuiframe.utils.LogUtil;
import com.example.a51425.mainuiframe.utils.MeasureUtil;
import com.tencent.smtt.sdk.QbSdk;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by XinYue on 2016/12/14.
 */

public class APP extends Application {

    public static Context context;
    private static List<Activity> activities = new LinkedList<>();
    private static APP application;

    public static Context getContext() {
        return context;
    }

    public static APP getInstance() {
        return application;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //7.0Uri适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        int width = MeasureUtil.getWidth(APP.getContext());
        int height = MeasureUtil.getHeight(APP.getContext());
        LogUtil.e("手机的宽——————————"+width);
        LogUtil.e("手机的高——————————"+height);

        initX5();
//        initLeakCanary();
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
