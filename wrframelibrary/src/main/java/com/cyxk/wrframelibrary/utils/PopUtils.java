package com.cyxk.wrframelibrary.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * popUpWindow
 */
public class PopUtils {
    /**
     * 弹出popupWindow变暗
     */
    public static void turnDark(Activity context) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.6f;
        context.getWindow().setAttributes(lp);
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 弹出popupWindow变暗, 可自定义暗度
     */
    public static void turnDark(Activity context, float dark) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = dark;
        context.getWindow().setAttributes(lp);
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    /**
     * popupWindow消失的时候恢复
     */
    public static void recover(Activity context) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 1.0f;
        context.getWindow().setAttributes(lp);
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}
