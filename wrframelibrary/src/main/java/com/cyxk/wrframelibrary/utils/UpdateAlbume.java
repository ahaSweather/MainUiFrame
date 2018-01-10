package com.cyxk.wrframelibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * 发送广播 更新系统相册
 * 
 */
public class UpdateAlbume {
	public static void updateAlbume(Activity activity, File file, String name) {
		// try {
		// MediaStore.Images.Media.insertImage(activity.getContentResolver(),
		// file.getAbsolutePath(), name, null);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		activity.sendBroadcast(intent);
		activity=null;
	}
	public static void updateAlbume(Activity activity, File file) {
		// try {
		// MediaStore.Images.Media.insertImage(activity.getContentResolver(),
		// file.getAbsolutePath(), name, null);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }

		try{
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(file);
			intent.setData(uri);
			activity.sendBroadcast(intent);
			activity=null;
		}catch (Exception e){
			LogUtil.e(Log.getStackTraceString(e));
		}

	}
}
