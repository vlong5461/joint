package com.he.joint.common;

public class Cast {
	
	@SuppressWarnings("unchecked")
	public static<T> T cast(Object o){
		return (T)o;
	}
	
}
