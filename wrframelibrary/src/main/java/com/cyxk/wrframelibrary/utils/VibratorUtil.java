package com.cyxk.wrframelibrary.utils;

import android.app.Service;
import android.os.Vibrator;

import com.cyxk.wrframelibrary.base.APP;


public class VibratorUtil {
 
    /**
     * final Activity activity ：调用该方法的Activity实例
     * long pattern ：震动的时长，单位是毫秒
     */
    public static void Vibrate(long milliseconds) {
        try{
            Vibrator vib = (Vibrator) APP.getContext().getSystemService(Service.VIBRATOR_SERVICE);
            vib.vibrate(milliseconds);
        }catch (Exception e){

        }

    }
 
    /**
     *
     * <strong>@param </strong>activity 调用该方法的Activity实例
     * <strong>@param </strong>pattern long[] pattern ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒
     * <strong>@param </strong>isRepeat 是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void Vibrate( long[] pattern,boolean isRepeat) {
        Vibrator vib = (Vibrator) APP.getContext().getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}