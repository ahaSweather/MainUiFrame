package com.cyxk.wrframelibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cyxk.wrframelibrary.base.APP;
import com.cyxk.wrframelibrary.config.ConfigUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * 该类用于操作SharedPreferance
 */
public class SharedPreferanceUtils {
	/** 分享 */
	private static SharedPreferences sharedPreferences = null;

	private static SharedPreferences.Editor editor = null;

	private static String shared_preferences_flag = null;
	private static SharedPreferanceUtils sharedPreferencesUtils;

	/**
	 * 构造方法
	 *
	 * @param shared_preferences_flag
	 * @param context
	 */
	public SharedPreferanceUtils(String shared_preferences_flag, Context context) {

		sharedPreferences = context.getSharedPreferences(
				ConfigUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		this.shared_preferences_flag = shared_preferences_flag;
		editor = sharedPreferences.edit();
	}

	/**
	 * 构造方法
	 *
	 */
	private SharedPreferanceUtils() {
			sharedPreferences = APP.getContext().getSharedPreferences(
					ConfigUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();

	}
	public static SharedPreferanceUtils getSp(){
		if (editor==null||sharedPreferences==null || sharedPreferencesUtils == null){
			synchronized (SharedPreferanceUtils.class){
				if (editor==null||sharedPreferences==null || sharedPreferencesUtils == null){
					sharedPreferencesUtils = new SharedPreferanceUtils();
				}
			}
		}
		return sharedPreferencesUtils;
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

	public void putLong(String key, long value){
		editor.putLong(key,value);
		editor.commit();
	}
	public long getLong(String key, long value){
		return sharedPreferences.getLong(key,value);
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public int getInt(String key, int value) {
		return sharedPreferences.getInt(key, value);
	}
	/**
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
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
	public boolean getBoolean(String key ) {
		return sharedPreferences.getBoolean(key, false);
	}
	/**
	 * @param key
	 */
	public boolean getBoolean(String key,boolean value ) {
		return sharedPreferences.getBoolean(key, value);
	}


	/**
	 * 保存List
	 * @param tag
	 * @param datalist
	 */
	public <T> void setDataList(String tag, List<T> datalist) {
		if (null == datalist || datalist.size() <= 0)
			return;

		Gson gson = new Gson();
		//转换成json数据，再保存
		String strJson = gson.toJson(datalist);
		editor.clear();
		editor.putString(tag, strJson);
		editor.commit();

	}

	/**
	 * 获取List
	 * @param tag
	 * @return
	 */
	public <T> List<T> getDataList(String tag) {
		List<T> datalist=new ArrayList<T>();
		String strJson = sharedPreferences.getString(tag, null);
		if (null == strJson) {
			return datalist;
		}
		Gson gson = new Gson();
		datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
		}.getType());
		return datalist;

	}



}
