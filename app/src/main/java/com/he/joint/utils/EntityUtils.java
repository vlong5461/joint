package com.he.joint.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EntityUtils {

	public static <T> void showInfo(T entity) {
		try {
			StringBuffer sb = new StringBuffer();
			Method method[] = entity.getClass().getMethods();
			if (CollectionUtils.isNotEmpty(method)) {
				for (int j = 0; j < method.length; j++) {
					Method m = method[j];
					String name = getFieldNameByMethod(m);
					if (StringUtils.isNotEmpty(name)) {
						Object value = m.invoke(entity);
						sb.append(name + " = " + value + ", ");
					}
				}
			}
			if (sb.toString().length() > 2) {
				sb.replace(sb.length() - 2, sb.length(), "");
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getFieldNameByMethod(Method method) {
		String str = method.getName();
		if (str.startsWith("get")) {
			String s = str.replace("get", "");
			return s;
		} else {
			return null;
		}
	}

	public static <T> String[] getEntityFieldNames(T entity) {
		String[] names = new String[0];
		Field[] fields = entity.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			names = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				names[i] = f.getName();
			}
		}
		return names;
	}

//	public static <T> T copyEntity(T entity) {
//		T newEntity = new <T> T();
//		Field[] fields = entity.getClass().getDeclaredFields();
//		for (int i = 0, j = fields.length; i < j; i++) {
//			String propertyName = fields[i].getName();
//			Object propertyValue = getProperty(entity, propertyName);
//		}
//
//
//	}

}
