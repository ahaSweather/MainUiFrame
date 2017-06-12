package com.example.a51425.mainuiframe;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.example.a51425.mainuiframe.utils.LogUtil;
import com.example.a51425.mainuiframe.utils.MeasureUtil;
import com.squareup.leakcanary.LeakCanary;

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
        int width = MeasureUtil.getWidth(APP.getContext());
        int height = MeasureUtil.getHeight(APP.getContext());
        LogUtil.e("手机的宽——————————"+width);
        LogUtil.e("手机的高——————————"+height);


       initLeakCanary();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}
		LeakCanary.install(this);
    }


}
