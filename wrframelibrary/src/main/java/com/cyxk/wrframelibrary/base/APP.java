package com.cyxk.wrframelibrary.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.cyxk.wrframelibrary.utils.GsonUtil;
import com.cyxk.wrframelibrary.utils.LogUtil;
import com.cyxk.wrframelibrary.utils.ToastUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import io.reactivex.Observable;


/**
 *

/**
 * Created by XinYue on 2016/12/14.
 *
 * 
 *----------Dragon be here!----------/ 
 * 　　　┏┓　　　┏┓ 
 * 　　┏┛┻━━━┛┻┓ 
 * 　　┃　　　　　　　┃ 
 * 　　┃　　　━　　　┃ 
 * 　　┃　┳┛　┗┳　┃ 
 * 　　┃　　　　　　　┃ 
 * 　　┃　　　┻　　　┃ 
 * 　　┃　　　　　　　┃ 
 * 　　┗━┓　　　┏━┛ 
 * 　　　　┃　　　┃神兽保佑 
 * 　　　　┃　　　┃代码无BUG！ 
 * 　　　　┃　　　┗━━━┓ 
 * 　　　　┃　　　　　　　┣┓ 
 * 　　　　┃　　　　　　　┏┛ 
 * 　　　　┗┓┓┏━┳┓┏┛ 
 * 　　　　　┃┫┫　┃┫┫ 
 * 　　　　　┗┻┛　┗┻┛ 
 * ━━━━━━神兽出没━━━━━━by:coder-pig 
 */



public abstract class APP extends Application {

    public static Context context;
    //    private static List<Activity> activities = new LinkedList<>();
    public static int showCount = 0;
//    public static String checkUpdate;

    //用来计算是否处于前台
    private int count = 0;

    // 计算是否是第一次连接
    public static boolean isFirstConnect = true;
    private Observable<String> mOb;

    public static Context getContext() {
        return context;
    }
    private static APP myApplication = null;

    public static APP getApplication() {
        return myApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        myApplication = this;
        //7.0Uri适配

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
//        initLeak();
//        try {
//            initX5();
//        } catch (Exception e) {
//            LogUtil.e(Log.getStackTraceString(e));
//        }
        registerListener();
        init();
    }

    protected abstract void init();


    /**
     * 注册前后台切换监听
     */
    private void registerListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityStopped(Activity activity) {
                LogUtil.e("onActivityStopped ---");
                count--;
                if (count == 0) {
                    LogUtil.e("切到后台");
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogUtil.e("onActivityStarted ---");
                if (count == 0) {
                    LogUtil.e("切到前台");
                    if (APP.isFirstConnect == false) {
                        LogUtil.e("切到前台后开始发送消息");
                        //如果之前已经连接了就要判断当前长连接是否处于连接的状态
                    }


                }
                count++;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.v("viclee", activity + "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityResumed(final Activity activity) {
                Log.v("viclee", activity + "onActivityResumed");


            }

            @Override
            public void onActivityPaused(Activity activity) {
//                Log.v("viclee", activity + "onActivityPaused");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.v("viclee", activity + "onActivityDestroyed");
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.v("viclee", activity + "onActivityCreated");
            }
        });


    }


    /**
     * 获取包信息
     *
     * @param context
     */
    private void getFileContent(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();

                if (entryName.startsWith("META-INF/extends.json")) { //xxx 表示要读取的文件名
                    //利用ZipInputStream读取文件
                    long size = entry.getSize();
                    if (size > 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(zipfile.getInputStream(entry)));
                        String line;
                        while ((line = br.readLine()) != null) {  //文件内容都在这里输出了，根据你的需要做改变
                            String author = GsonUtil.getFieldValue(line, "author");
                            ToastUtil.showToast(APP.getContext(), "成功获取师傅信息");
                        }
                        br.close();
                    }
                    break;
                }
            }
        } catch (Exception e) {

        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (Exception e) {

                }
            }
        }

    }

}
