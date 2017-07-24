package com.example.a51425.mainuiframe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.a51425.mainuiframe.APP;
import com.example.a51425.mainuiframe.constant.Constants;

/**
 * 该类用于操作SharedPreferance
 */
public class SharedPreferanceUtils {
	/** 分享 */
	private static SharedPreferences sharedPreferences = null;

	private static SharedPreferences.Editor editor = null;

	private static String shared_preferences_flag = null;



	/**
	 * 构造方法
	 * 
	 */
	public SharedPreferanceUtils() {
		if (editor==null||sharedPreferences==null) {

			sharedPreferences = APP.getContext().getSharedPreferences(
					Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
		}
	}
	public static SharedPreferences getSp(){
		if (editor==null||sharedPreferences==null){
			new SharedPreferanceUtils();
			return sharedPreferences;
		}else{
			return sharedPreferences;
		}
	}


	/**
	 * 用于从SharedPreferance中获取知识库程序版本
	 * 
	 * @return
	 */
	public int getKnowledgeDBVersion() {
		return sharedPreferences.getInt(shared_preferences_flag, 0);
	}

	/**
	 * 用于设置SharedPreferance中的程序版本
	 */
	public void setKnowledgeDBVersion(int version) {
		editor.putInt(shared_preferences_flag, version);
		editor.commit();
	}

	/**
	 * 用于从SharedPreferance中获取知识库程序版本
	 * 
	 * @return
	 */
	public int getShareConunt() {
		return sharedPreferences.getInt(shared_preferences_flag, 0);
	}

	/**
	 * 用于设置SharedPreferance中的程序版本
	 */
	public void setShareConunt(int count) {
		editor.putInt(shared_preferences_flag, count);
		editor.commit();
	}

	/**
	 * 纠错或查漏补缺时获取用户头像列表时用此参数；
	 */
	public int getKnowLedgeType() {
		return sharedPreferences.getInt(shared_preferences_flag, 0);

	}

	/**
	 * 纠错或查漏补缺时获取用户头像列表时用此参数；
	 */
	public void setKnowLedgeType(int type) {
		editor.putInt(shared_preferences_flag, type);
		editor.commit();

	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * @param key
	 * @param value
	 */
	public void putString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * @param key
	 * @param value
	 */
	public void putInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public int getInt(String key, int value) {
		return sharedPreferences.getInt(key, 0);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public String getString(String key, String value) {
		return sharedPreferences==null?null:sharedPreferences.getString(key, value);
	}

	/**
	 * @param key
	 */
	public boolean getBoolean(String key) {
		return sharedPreferences.getBoolean(key, false);
	}

}
