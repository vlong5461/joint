package com.he.joint.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


import android.os.Environment;

import com.he.joint.common.PreferenceHelper;

public class MIUIUtils {
	private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
	private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
	private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

	public static boolean isMIUI() {
		Properties prop = new Properties();
		boolean isMIUI;
		try {
			Integer obj = (Integer) PreferenceHelper.getFromSharedPreferences("Android_System_MIUI",
					Integer.class.getName());
			if (obj != null && obj.intValue() > 0) {
				return true;
			} else {
				prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
				|| prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
				|| prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
		
		PreferenceHelper.saveToSharedPreferences("Android_System_MIUI", isMIUI? 1: 0);

		return isMIUI;
	}
	
	
	
}
