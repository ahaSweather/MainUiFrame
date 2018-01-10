package com.cyxk.wrframelibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.cyxk.wrframelibrary.base.APP;


public class ToastUtil {

	private static Toast mToast;
	public static Handler mHandler = new Handler();

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
	public synchronized static void showToast(final Context context, final String text,
											  final int duration) {
		mHandler.removeCallbacksAndMessages(null);
		if (Looper.getMainLooper() == Looper.myLooper()){
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
		}else{
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					if (mToast == null) {// Toast第一次使用，初始化
						// 你也可以自定义Toast的布局
						mToast = Toast.makeText(context, text, duration);
//						mToast.setGravity(Gravity.LEFT,0,0);
						// mToast.show();
					} else {
						mToast.setText(text);
					}
					mToast.show();
					mHandler.postDelayed(runnable, 2000);
				}
			});
		}

	}

	public static void showToast(Context context, final String text) {


		showToast(APP.getContext(), text, 2000);



	}

	public static void showToast(Context context, int resId) {
		String text = APP.getContext().getResources().getString(resId);
		showToast(context, text, Toast.LENGTH_LONG);
	}

	public static void showToastLong( Context context,String content) {
		Toast.makeText(context,content,Toast.LENGTH_LONG).show();

	}
}
