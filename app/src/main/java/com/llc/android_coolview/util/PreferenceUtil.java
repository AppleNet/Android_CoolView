package com.llc.android_coolview.util;

import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceUtil {
	private SharedPreferences Preferences;

	// 记录当前是否在正在优惠
	public static final String BENEFITNOW = "BENEFITNOW";
	// 记录当前的自定义提示信息
	public static final String TIPNOW = "TIPNOW";
	// 记录当前的用户信息
	public static final String USERINFO = "USERINFO";

	public PreferenceUtil(Context context) {
		this(context, "default");
	}

	public PreferenceUtil(Context context, String name) {
		Preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	public void setPreference(String key, boolean value) {
		Editor editor = Preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void setPreference(String key, String value) {
		Editor editor = Preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public void setPreference(String key, float value) {
		Editor editor = Preferences.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public void setPreference(String key, int value) {
		Editor editor = Preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public void setPreference(String key, long value) {
		Editor editor = Preferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	public void setBeanPreference(String key, Object value) {
		if (value != null) {
			setPreference(key, JSONUtil.toJson(value));
		} else {
			setPreference(key, "");
		}
	}

	public Boolean getBoolean(String key, Boolean defaultValue) {
		return Preferences.getBoolean(key, defaultValue);
	}

	public String getString(String key) {
		return Preferences.getString(key, "");
	}

	public String getString(String key, String defaultValue) {
		return Preferences.getString(key, defaultValue);
	}

	public float getFloat(String key) {
		return Preferences.getFloat(key, 0);
	}

	public int getInt(String key, int defaultValue) {
		return Preferences.getInt(key, defaultValue);
	}

	public long getLong(String key, long defaultValue) {
		return Preferences.getLong(key, defaultValue);
	}

	public <T> T getBean(String key, Class<T> clazz) {
		String json = getString(key);
		if (!json.equals("")) {
			return JSONUtil.fromJson(json, clazz);
		} else {
			return null;
		}
	}

	public <T> T getBean(String key, TypeToken<T> token) {
		String json = getString(key);
		if (!json.equals("")) {
			return JSONUtil.fromJson(json, token);
		} else {
			return null;
		}
	}

	/**
	 * 刷新状态数据
	 * 
	 * @return 是否刷新成功
	 */
	public boolean refresh() {
		return true;
	}

}
