package com.example.a51425.mainuiframe.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.cyxk.wrframelibrary.framework.CallBackListener;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WxShare {

    private static SendMessageToWX.Req localReq;
    private static CallBackListener localOnShareListener;

    private static boolean isAppInstalled(Context paramContext, String paramString) {
        List localList = paramContext.getPackageManager().getInstalledPackages(0);
        ArrayList localArrayList = new ArrayList();
        if (localList != null) {
            for (int i = 0; i < localList.size(); i++) {
                localArrayList.add(((PackageInfo) localList.get(i)).packageName);
            }
        }
        return localArrayList.contains(paramString);
    }

    public static void sendReq(WeakReference<Activity> weakReference, CallBackListener onShareLitener, SendMessageToWX.Req req, final String appId, final String packageName) {
        LogUtil.e("sendReq----------");

        localOnShareListener = onShareLitener;
        for (; ; ) {
            final Activity localActivity;
            String str;
            try {
                Activity activity = weakReference.get();
                localActivity = activity;
                localReq = req;
                if ((localActivity == null) || (localReq == null)) {

                    return;
                }

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    public void run() {
                                        try {
                                            Bundle localBundle = new Bundle();
                                            localReq.toBundle(localBundle);
                                            Intent localIntent = new Intent();
                                            localIntent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity");
                                            localIntent.putExtras(localBundle);
                                            localIntent.putExtra("_mmessage_sdkVersion", 587268097);
                                            localIntent.putExtra("_mmessage_appPackage", packageName);
                                            localIntent.putExtra("_mmessage_content", "weixin://sendreq?appid=" + appId);
//                                            localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                            Class localClass = Class.forName("com.tencent.mm.sdk.a.a.b");
                                            Class[] arrayOfClass = new Class[3];
                                            arrayOfClass[0] = String.class;
                                            arrayOfClass[1] = Integer.TYPE;
                                            arrayOfClass[2] = String.class;
                                            Method localMethod = localClass.getDeclaredMethod("a", arrayOfClass);
                                            Object[] arrayOfObject = new Object[3];
                                            arrayOfObject[0] = ("weixin://sendreq?appid=" + appId);
                                            arrayOfObject[1] = Integer.valueOf(587268097);
                                            arrayOfObject[2] = localActivity.getPackageName();
                                            localIntent.putExtra("_mmessage_checksum", (byte[]) localMethod.invoke(null, arrayOfObject));
                                            localIntent.addFlags(268435456).addFlags(134217728);
                                            if (localActivity!=null){
                                                    localActivity.startActivity(localIntent);
                                            }
                                            localOnShareListener.onSuccess("success");
//                                            MyApplication.getContext().startActivity(localIntent);

                                return;
                            } catch (Exception localException) {
                                LogUtil.e("22222");
                                LogUtil.e(Log.getStackTraceString(localException));
                                localOnShareListener.onFailure();
//
                            }
                        }
                    }, 0);
                    return;

            } catch (Exception localException) {

                LogUtil.e(Log.getStackTraceString(localException));
                localOnShareListener.onFailure();
                return;
            }
//
        }
    }

    /**
     * 通过rxjava进行分享
     * @param weakReference
     * @param req
     * @param appId
     * @param packageName
     */
    public static void sendReq(WeakReference<Activity> weakReference, SendMessageToWX.Req req, final String appId, final String packageName) {
        LogUtil.e("sendReq----------");

//        localOnShareListener = onShareLitener;
        for (; ; ) {
            final Activity localActivity;
            String str;
            try {
                Activity activity = weakReference.get();
                localActivity = activity;
                localReq = req;
                if ((localActivity == null) || (localReq == null)) {

                    return;
                }
                LogUtil.e("开始分享");
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    public void run() {
                        try {
                            Bundle localBundle = new Bundle();
                            localReq.toBundle(localBundle);
                            Intent localIntent = new Intent();
                            localIntent.setClassName("com.tencent.mm", "com.tencent.mm.plugin.base.stub.WXEntryActivity");
                            localIntent.putExtras(localBundle);
                            localIntent.putExtra("_mmessage_sdkVersion", 587268097);
                            localIntent.putExtra("_mmessage_appPackage", packageName);
                            localIntent.putExtra("_mmessage_content", "weixin://sendreq?appid=" + appId);

                            Class localClass = Class.forName("com.tencent.mm.sdk.a.a.b");
                            Class[] arrayOfClass = new Class[3];
                            arrayOfClass[0] = String.class;
                            arrayOfClass[1] = Integer.TYPE;
                            arrayOfClass[2] = String.class;
                            Method localMethod = localClass.getDeclaredMethod("a", arrayOfClass);
                            Object[] arrayOfObject = new Object[3];
                            arrayOfObject[0] = ("weixin://sendreq?appid=" + appId);
                            arrayOfObject[1] = Integer.valueOf(587268097);
//                            arrayOfObject[2] = packageName;
                            arrayOfObject[2] = localActivity.getPackageName();
                            localIntent.putExtra("_mmessage_checksum", (byte[]) localMethod.invoke(null, arrayOfObject));
                            localIntent.addFlags(268435456).addFlags(134217728);
                            if (localActivity!=null){
                                    localActivity.startActivity(localIntent);
                            }
                            return;
                        } catch (Exception localException) {
                            LogUtil.e(Log.getStackTraceString(localException));
                        }
                    }
                }, 0);
                return;

            } catch (Exception localException) {
                LogUtil.e(Log.getStackTraceString(localException));

                return;
            }

        }
    }

}
