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

public class RemoteService extends Service {
    private RemoteServiceBinder remoteServiceBinder;
    private RemoteServiceConnection remoteServiceConnection;
    private final static int GRAY_SERVICE_ID = -1001;
    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return remoteServiceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //实例化，因为需要反复的创建service，localServiceBinder 是一个静态内部类所以需要判空
        if (remoteServiceBinder == null){
            remoteServiceBinder = new RemoteServiceBinder();
        }
        //非静态内部类
        if (remoteServiceConnection == null){

        remoteServiceConnection = new RemoteServiceConnection();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(getClass().getName()+"——————onStartCommand");
        startTimer();
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
//        builder.setContentTitle("helloWorld");
//        builder.setContentInfo("info");
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setWhen(System.currentTimeMillis());
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
//        builder.setContentIntent(pendingIntent);
//        //将Sevice process 提高到 foreground Process优先级
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            startForeground(startId,builder.build());
//        }

        if (Build.VERSION.SDK_INT < 18) {
            LogUtil.e("RemoteInnerService<18");
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            LogUtil.e("RemoteInnerService>18");
            Intent innerIntent = new Intent(this, RemoteInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        return START_STICKY;
    }





    class RemoteServiceConnection implements ServiceConnection {

        //当外面的客户程序和我们绑定时调用的
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        //分开时调用的
        @Override
        public void onServiceDisconnected(ComponentName name) {

            LogUtil.d(getClass().getName()+"___onServiceDisconnected");
            //让两个进程进行绑定
            RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class),remoteServiceConnection
                    , Context.BIND_IMPORTANT
            );
            RemoteService.this.startService(new Intent(RemoteService.this, LocalService.class));
        }
    }

    class RemoteServiceBinder extends IServiceAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            LogUtil.d(getClass().getName()+"—————— basicTypes ————————");
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

    @Override
    public void onDestroy() {
//        super.onDestroy();

        try{
            RemoteService.this.startService(new Intent(RemoteService.this, LocalService.class));
            //让两个进程进行绑定
            RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class),remoteServiceConnection
                    , Context.BIND_IMPORTANT
            );
        }catch (Exception e){
            LogUtil.e(Log.getStackTraceString(e));
        }
        try{
            super.onDestroy();
        }catch (Exception e){

        }
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class RemoteInnerService extends Service {

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
