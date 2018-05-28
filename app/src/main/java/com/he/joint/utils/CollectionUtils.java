package com.he.joint.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;

public class CollectionUtils {
	
	public static <T> boolean isEmpty(List<T> list) {
		if (list != null && list.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static <T> boolean isNotEmpty(List<T> list) {
		return !isEmpty(list);
	}
	
	public static <T> boolean isEmpty(T[] array) {
		if (array != null && array.length > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static <T> boolean isNotEmpty(T[] array) {
		return !isEmpty(array);
	}
	
	public static <T> boolean isEmpty(Collection<T> collection) {
		if (collection != null && collection.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static <T> boolean isNotEmpty(Collection<T> collection) {
		return !isEmpty(collection);
	}
	
	public static boolean isEmpty(JSONArray array) {
		if (array != null && array.length() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isNotEmpty(JSONArray array) {
		return !isEmpty(array);
	}
	
	public static <T> List<T> asList(Collection<T> collection) {
		List<T> list = new ArrayList<T>();
		if (collection != null && collection.size() > 0) {
			Iterator<T> iterator = collection.iterator();
			while (iterator.hasNext()) {
				list.add(iterator.next());
			}
		}
		return list;
	}

	public static <T> int getSize(Collection<T> collection) {
		if (isNotEmpty(collection)) {
			return collection.size();
		} else {
			return  0;
		}
	}

}
