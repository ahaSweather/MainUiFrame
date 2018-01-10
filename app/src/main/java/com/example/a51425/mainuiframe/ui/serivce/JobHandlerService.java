package com.example.a51425.mainuiframe.ui.serivce;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.cyxk.wrframelibrary.utils.LogUtil;

import java.util.List;

/**
 * Created by 51425 on 2017/6/18.
 */



@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobHandlerService extends JobService {

    private int jobId = 0x0008;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("JobHandlerService___onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //初始化时提交
        scheduleJob(getJobInfo());
        LogUtil.d(getClass().getName()+"onStartCommand ");
        return START_STICKY;
    }

    private void scheduleJob(JobInfo job){
        JobScheduler js = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        js.schedule(job);
    }
    private JobInfo getJobInfo(){
        JobInfo.Builder builder = new JobInfo.Builder(jobId,new ComponentName(
                this,JobHandlerService.class
        ));
        builder.setPersisted(true);//这个  JobScheduler会一直生效
        builder.setPeriodic(100);//100毫秒唤醒一次
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);//所有网络都可以
        builder.setRequiresCharging(false);//是不是充电时才执行
        builder.setRequiresDeviceIdle(false);//设备忙碌空闲都执行
        return  builder.build();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        LogUtil.d("onStartJob—————— 执行了 ");
        //创建两个进程，因为每隔100毫秒需要唤醒下 所以判断下
        boolean isLocalServiceWorking = isServiceWork(this,"com.example.a51425.mainuiframe.ui.serivce.LocalService");
        boolean isRemoteServiceWorking = isServiceWork(this,"com.example.a51425.mainuiframe.ui.serivce.RemoteService");
        //只要有一个死了就都创建
        if (!isLocalServiceWorking || !isRemoteServiceWorking )
        {
            LogUtil.e("JobHandlerService——————startService");
        this.startService(new Intent(this,LocalService.class));
        this.startService(new Intent(this,RemoteService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        scheduleJob(getJobInfo());
        //结束时候再启动
        return false;
    }

    //判断当前服务是否正在运行
    public boolean isServiceWork(Context context,String serviceName){
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得正在运行的服务
        List<ActivityManager.RunningServiceInfo> list = activityManager.getRunningServices(128);
        if (list.size() < 0){
            return false;
        }
        for (int i = 0; i < list.size() ; i++) {
            String name = list.get(i).service.getClassName().toString();
            if (serviceName.equals(name)){
                return true;
            }

        }
        return false;

    }

}
