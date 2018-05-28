package com.third.view.tag;

import java.io.Serializable;

public class Tag implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2684657309332033242L;
	
	private int backgroundResId;
	private int id;
	private boolean isChecked;
	private int leftDrawableResId;
	private int rightDrawableResId;
	private String title;
	private int titleColor;
	private String BusinessID;
	private Object data;
	private boolean enable = true;

	public Tag() {
		
	}

	public Tag(int paramInt, String paramString) {
		this.id = paramInt;
		this.title = paramString;
	}

	public int getBackgroundResId() {
		return this.backgroundResId;
	}

	public int getId() {
		return this.id;
	}

	public int getLeftDrawableResId() {
		return this.leftDrawableResId;
	}

	public int getRightDrawableResId() {
		return this.rightDrawableResId;
	}

	public String getTitle() {
		return this.title;
	}

	public boolean isChecked() {
		return this.isChecked;
	}

	public void setBackgroundResId(int paramInt) {
		this.backgroundResId = paramInt;
	}

	public void setChecked(boolean paramBoolean) {
		this.isChecked = paramBoolean;
	}

	public void setId(int paramInt) {
		this.id = paramInt;
	}

	public void setLeftDrawableResId(int paramInt) {
		this.leftDrawableResId = paramInt;
	}

	public void setRightDrawableResId(int paramInt) {
		this.rightDrawableResId = paramInt;
	}

	public void setTitle(String paramString) {
		this.title = paramString;
	}

	public String getBusinessID() {
		return BusinessID;
	}

	public void setBusinessID(String businessID) {
		BusinessID = businessID;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(int titleColor) {
		this.titleColor = titleColor;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isEnable() {
		return enable;
	}
	
}
