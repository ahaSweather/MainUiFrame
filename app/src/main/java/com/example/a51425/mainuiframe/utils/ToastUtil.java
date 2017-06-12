package com.example.a51425.mainuiframe.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.a51425.mainuiframe.APP;



public class ToastUtil {

	private static Toast mToast;
	private static Handler mHandler = new Handler();

	private static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if (mToast != null) {
				mToast.cancel();
			}
		}
	};

	/**
	 * 
	 * @param context
	 *            上下文对象
	 * @param text
	 *            提示信息
	 * @param duration
	 *            显示时间
	 */
	public synchronized static void showToast(Context context, String text,
											  int duration) {
		mHandler.removeCallbacks(runnable);
		if (mToast == null) {// Toast第一次使用，初始化
			// 你也可以自定义Toast的布局

			mToast = Toast.makeText(context, text, duration);
//			mToast.setGravity(Gravity.LEFT,0,0);
			// mToast.show();
		} else {
			mToast.setText(text);

		}
		mToast.show();
		mHandler.postDelayed(runnable, 2000);
	}

	public static void showToast(Context context, String text) {
		context=null;
		showToast(APP.getContext(), text, Toast.LENGTH_LONG);
	}

	public static void showToast(Context context, int resId) {
		context=null;
		String text = APP.getContext().getResources().getString(resId);
		showToast(context, text, Toast.LENGTH_LONG);
	}

	public static void showToast(Context context, int resId, int duration) {
		context=null;
		String text = APP.getContext().getResources().getString(resId);
		showToast(context, text, duration);
	}
}
