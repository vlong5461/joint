package com.he.joint.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class PreferenceHelper {

	private static SharedPreferences.Editor editor = null;
	private static SharedPreferences preferences = null;

	private PreferenceHelper() {

	}

	private static class InstanceHolder {
		public static PreferenceHelper instance = new PreferenceHelper();
	}

	public synchronized static PreferenceHelper getInstance() {
		return InstanceHolder.instance;
	}

	public static SharedPreferences.Editor getEditor(Context context) {
		if (editor == null) {
			editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		}

		return editor;
	}

	public static SharedPreferences getSharedPreferences(Context context) {
		if (preferences == null) {
			preferences = PreferenceManager.getDefaultSharedPreferences(context);
		}

		return preferences;
	}

	@SuppressWarnings("unchecked")
	public static void saveToSharedPreferences(String currentKey, Object value) {

		if (value instanceof String) {
			editor.putString(currentKey, String.valueOf(value));
		} else if (value instanceof Integer) {
			editor.putInt(currentKey, Integer.parseInt(String.valueOf(value)));
		} else if (value instanceof Boolean) {
			editor.putBoolean(currentKey, (Boolean) value);
		} else if (value instanceof Long) {
			editor.putLong(currentKey, Long.parseLong(String.valueOf(value)));
		} else if (value instanceof Set<?>) {
			editor.putStringSet(currentKey, (Set<String>) value);
		} else if (value instanceof Float) {
			editor.putFloat(currentKey, Float.parseFloat(String.valueOf(value)));
		}

		editor.commit();
	}
	
	public static void removeFromSharedPreference(String key) {
		editor.remove(key);
		editor.commit();
	}

	public static Object getFromSharedPreferences(String currentKey, Object type) {
		Object value = null;

		if (type.equals("java.lang.String")) {
			value = preferences.getString(currentKey, "");
		} else if (type.equals("java.lang.Integer")) {
			value = preferences.getInt(currentKey, 0);
		} else if (type.equals("java.lang.Boolean")) {
			value = preferences.getBoolean(currentKey, true);
		} else if (type.equals("java.lang.Long")) {
			value = preferences.getLong(currentKey, 0);
		} else if (type.equals("java.util.Set")) {
			value = preferences.getStringSet(currentKey, new HashSet<String>());
		} else if (type.equals("java.lang.Float")) {
			value = preferences.getFloat(currentKey, (float) 0.0);
		}

		return value;
	}
}
