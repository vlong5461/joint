package com.he.joint.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	

	public static String convertToDate(long timestampString, String formats) {
		if (StringUtils.isEmpty(formats)) {
			return null;
		}
		try {
			String date = new SimpleDateFormat(formats).format(new Date(timestampString * 1000L));
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public static String convertToDate2(long timestampString, String formats) {
		if (StringUtils.isEmpty(formats)) {
			return null;
		}
		try {
			String date = new SimpleDateFormat(formats).format(new Date(timestampString));
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	public static long convertToTimestamp(String dateString, String format) {
		if (StringUtils.isEmpty(dateString) || StringUtils.isEmpty(format)) {
			return 0;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(dateString);
			return date.getTime() / 1000L;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;

	}

	public static String convertDateToString(Date date, String format) {
		if (StringUtils.isEmpty(format) || date == null) {
			return "";
		}

		String dateString = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			dateString = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dateString;
	}

	public static Date convertStringToDate(String string, String format) {
		if (StringUtils.isEmpty(string) || StringUtils.isEmpty(format)) {
			return null;
		}

		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

}
