package com.cyxk.wrframelibrary.utils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by 10067 on 2017/6/14.
 * <p>
 * 检测app是否在前台运行
 */

public class AppIsReceptionUtils {


    /**
     * 判断app是否处于前台
     *
     * @param context
     * @return
     */
    public static boolean isAppForeground(Context context) {
        boolean isForeground = true;
        try{
            if ( Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH){
                ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
                if (runningAppProcessInfoList == null) {
                    isForeground = false;
                    return isForeground;
                }
                for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
                    if (processInfo.processName.equals(context.getPackageName()) && processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        isForeground = true;
                        return isForeground;
                    }else {
                        isForeground = false;
                        return isForeground;
                    }
                }
            }else{
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                String currentPackageName = cn.getPackageName();
                if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
                    isForeground = true;
                    return isForeground;
                }
                isForeground = false;
                return isForeground;
            }
        }catch (Exception e){
            isForeground = true;
        }


        return isForeground;
    }

//    /**
//     * 或者是这种方式来检测
//     * 判断app是否处于前台
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isRunningForeground(Context context) {
//
//    }


}
