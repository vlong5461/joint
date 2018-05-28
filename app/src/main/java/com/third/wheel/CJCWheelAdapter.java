package com.third.wheel;

import com.he.joint.mgr.DataMgr;

import java.util.List;


public class CJCWheelAdapter implements WheelAdapter {
	
	private List<String> dataList;
	
	public CJCWheelAdapter (List<String> dataList) {
		this.dataList = dataList;
	}

	@Override
	public int getItemsCount() {
		if (dataList != null) {
			return dataList.size();
		}
		return 0;
	}

	@Override
	public String getItem(int index) {
		return dataList.get(index);
	}

	@Override
	public int getMaximumLength() {
		return DataMgr.screenWidth;
	}

}
