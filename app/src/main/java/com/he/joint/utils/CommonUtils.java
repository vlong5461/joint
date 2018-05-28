package com.he.joint.utils;

public class CommonUtils {
	private static long lastClickTime=0;
	private static long fristClickTime=0;
	private static long fastlastClickTime=0;
	
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			lastClickTime = time;
			return true;
		}
		lastClickTime = time;
		return false;
	}
	
	public static boolean isOtherClick(int timeC) {
		long time = System.currentTimeMillis();
		long timeD = time - fristClickTime;
		if (0 < timeD && timeD < timeC) {
			return false;
		}
		if(isFastDoubleClick()){
			fristClickTime = time;
		}
		return true;
	}
	public static boolean isFastDoubleClick( int timeC) {
		long time = System.currentTimeMillis();
		long timeD = time - fastlastClickTime;
		if (0 < timeD && timeD < timeC) {
			return true;
		}
		fastlastClickTime = time;
		return false;
	}

}
