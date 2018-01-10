package com.example.a51425.mainuiframe.ui.serivce;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.cyxk.wrframelibrary.utils.LogUtil;
import com.example.a51425.mainuiframe.IServiceAidlInterface;

import java.util.Timer;
import java.util.TimerTask;

public class LocalService extends Service {
    LocalServiceBinder localServiceBinder;
    LocalServiceConnection localServiceConnection;
    private final static int GRAY_SERVICE_ID = -1001;
    public LocalService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //实例化，因为需要反复的创建service，localServiceBinder 是一个静态内部类所以需要判空
        if (localServiceBinder ==null){
        localServiceBinder = new LocalServiceBinder();
        }
        //非静态内部类
        if (localServiceConnection == null){

        localServiceConnection = new LocalServiceConnection();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
//        builder.setContentTitle("helloWorld");
//        builder.setContentInfo("info");
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setWhen(System.currentTimeMillis());
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
//        builder.setContentIntent(pendingIntent);
        //将Sevice process 提高到 foreground Process优先级

//        startForeground(startId,builder.build());

        // 将通知栏隐藏掉
        if (Build.VERSION.SDK_INT < 18) {
            LogUtil.e("LocalInnerService启动<18");
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            LogUtil.e("LocalInnerService启动>18");
            Intent innerIntent = new Intent(this, LocalInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return localServiceBinder;
    }


    class LocalServiceConnection implements ServiceConnection{

        //当外面的客户程序和我们绑定时调用的
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        //分开时调用的 当删除Local会启动Remote
        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d(getClass().getName()+"___onServiceDisconnected");
            //让两个进程进行绑定
            LocalService.this.bindService(new Intent(LocalService.this, RemoteService.class),localServiceConnection
                ,Context.BIND_IMPORTANT
            );
            LocalService.this.startService(new Intent(LocalService.this, RemoteService.class));

        }
    }

    class LocalServiceBinder extends IServiceAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            LogUtil.d(getClass().getName()+"____basicTypes ————————");
        }
    }

    @Override
    public void onDestroy() {

        try {
            LocalService.this.startService(new Intent(LocalService.this, RemoteService.class));
            LocalService.this.bindService(new Intent(LocalService.this, RemoteService.class), localServiceConnection
                    , Context.BIND_IMPORTANT
            );
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e) + "失败1");
        }

        try {
            super.onDestroy();
        } catch (Exception e) {
            LogUtil.e(Log.getStackTraceString(e) + "失败2");
        }

    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;
    private int counter;

    public void startTimer(){
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask,1000,1000);
    }
    public void stopTimerTask(){
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

    private void initializeTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
//                Log.e(getClass().getName()," init Timer ____"+(counter ++));
            }
        };
    }



    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class LocalInnerService extends Service {

        @Override
        public void onCreate() {
            LogUtil.i( "InnerService -> onCreate");
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            LogUtil.i( "InnerService -> onStartCommand");
            startForeground(GRAY_SERVICE_ID, new Notification());
            //stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            LogUtil.i( "InnerService -> onDestroy");
            super.onDestroy();
        }
    }


}
