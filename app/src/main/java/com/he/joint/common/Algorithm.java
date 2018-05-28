package com.he.joint.common;

import android.annotation.SuppressLint;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Algorithm {
	
	public static String getMD5(String data) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(data.getBytes());
			byte[] m = md5.digest();
			return bytes2HexString(m);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getTokenMD5(long time) {
		MessageDigest md5;
		try {
			String original = "hz.com2017"+time;
			md5 = MessageDigest.getInstance("MD5");
			md5.update(original.getBytes());
			byte[] m = md5.digest();
			return bytes2HexString(m);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	@SuppressLint("DefaultLocale")
	public static String bytes2HexString(byte[] bts) {

		StringBuffer sb = new StringBuffer();
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
		}
		return sb.toString().toLowerCase();
	}

}
