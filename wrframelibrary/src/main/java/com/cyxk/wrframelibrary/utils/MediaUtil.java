package com.cyxk.wrframelibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.lang.reflect.Method;

/**
 * 多媒体工具类
 */
public class MediaUtil {

	/**
	 * 截取视频第一帧
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	public static Bitmap getVideoFirstFrame(Context context, Uri uri) {
		Bitmap bitmap = null;
		String className = "android.media.MediaMetadataRetriever";
		Object objectMediaMetadataRetriever = null;
		Method release = null;
		try {
			objectMediaMetadataRetriever = Class.forName(className)
					.newInstance();
			Method setDataSourceMethod = Class.forName(className).getMethod(
					"setDataSource", Context.class, Uri.class);
			setDataSourceMethod.invoke(objectMediaMetadataRetriever, context,
					uri);
			Method getFrameAtTimeMethod = Class.forName(className).getMethod(
					"getFrameAtTime");
			bitmap = (Bitmap) getFrameAtTimeMethod
					.invoke(objectMediaMetadataRetriever);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (release != null) {
					release.invoke(objectMediaMetadataRetriever);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}




}
