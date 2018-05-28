package com.he.joint.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import android.content.ClipboardManager;
import android.content.Context;

public class StringUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);

	}

	public static boolean isNull(String str) {
		return str == null;
	}

	public static boolean isNotNull(String str) {
		return str != null;
	}

	public static String changeBooleanToString(boolean b) {
		if (b) {
			return "true";
		} else {
			return "false";
		}
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String s) {
		return !StringUtils.isBlank(s);
	}

	public static boolean isValidEMail(String eMail) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return Pattern.matches(regex, eMail);
	}

	/**
	 * 四舍五入保留�?位小�?
	 * 
	 * @param f
	 * @return
	 */
	public static String getDecimalFormat(float f) {
		String parten = "#.#";
		DecimalFormat decimal = new DecimalFormat(parten);
		String str = decimal.format(f);
		return str;
	}

	/**
	 * 将字符串中的字符全角�?
	 * 
	 * @param target
	 * @return
	 */
	public static String toDBC(String target) {
		if (isEmpty(target)) {
			return "";
		}
		char[] c = target.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String getNotNullString(String s) {
		if (isEmpty(s)) {
			return "";
		}

		return s;
	}

	/**
	 * 处理double类型数据,小数点后有两位数以上的保留两位小�?
	 * 
	 * @param
	 * @return
	 */
	public static String doubleFormat(double target) {
		if (target % 1.0 == 0) {
			return String.valueOf((long) target);
		}

		String targetString = String.valueOf(target);
		DecimalFormat formater = new DecimalFormat();
		String[] priceArray = null;
		try {
			priceArray = targetString.split("\\.");
		} catch (Exception e) {

		}
		if (priceArray != null && priceArray.length >= 2) {
			if (priceArray[1].length() == 1) {
				formater = new DecimalFormat("#.0");
			} else {
				formater = new DecimalFormat("#.00");
			}
		}

		return formater.format(target);

	}
	
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	}  
	
	public static void copy(String content, Context context){  
		// �õ�����������  
		ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
		cmb.setText(content.trim());  
	}

	public static int getLength(String text) {
		if (text != null) {
			return text.length();
		} else {
			return 0;
		}
	}

	public static String removeMoneyPoint(String money) {
		String text = "";
		try {
			if (getLength(money) > 0 && money.contains(".") ) {
				text = money.substring(0, money.indexOf("."));
			} else {
				text = money;
			}
		} catch (Exception e) {

		}

		return text;
	}

	public static int getInt(String text) {
		if (getLength(text) > 0) {
			try {
				return Integer.parseInt(text);
			} catch (Exception e) {
				return 0;
			}
		} else {
			return 0;
		}
	}

}
