package com.cyxk.wrframelibrary.utils;



import com.cyxk.wrframelibrary.config.ConfigUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wr on 2017/10/21.
 */

public class TimeUtils {
    public static boolean isSameDay(Date date, Date sameDate) {



        if (null == date || null == sameDate) {

            return false;

        }

        Calendar nowCalendar = Calendar.getInstance();

        nowCalendar.setTime(sameDate);

        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(date);

        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)

                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)

                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {

            return true;

        }

        // if (date.getYear() == sameDate.getYear() && date.getMonth() == sameDate.getMonth()

        // && date.getDate() == sameDate.getDate()) {

        // return true;

        // }

        return false;

    }
    public static boolean isSameDay() {
        long aLong = SharedPreferanceUtils.getSp().getLong(ConfigUtil.time, 0);
        long currentTimeMillis = System.currentTimeMillis();
        if (aLong == 0){
            //第一次进来,代表不是同一天，清楚数据
            SharedPreferanceUtils.getSp().putLong(ConfigUtil.time,currentTimeMillis);
            LogUtil.e("第一次进来。。。");
            return false;
        }
        Date sameDate = new Date(aLong);

        Date date = new Date(currentTimeMillis);

        Calendar nowCalendar = Calendar.getInstance();

        nowCalendar.setTime(sameDate);

        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(date);

        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)

                && nowCalendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)

                && nowCalendar.get(Calendar.DATE) == dateCalendar.get(Calendar.DATE)) {
            LogUtil.e("同一天。。");

            return true;

        }

        // if (date.getYear() == sameDate.getYear() && date.getMonth() == sameDate.getMonth()

        // && date.getDate() == sameDate.getDate()) {

        // return true;

        // }

        LogUtil.e("不是同一天。。。");
        SharedPreferanceUtils.getSp().putLong(ConfigUtil.time,currentTimeMillis);
        return false;

    }
}
