package com.third.view.pullablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableDownListView extends ListView implements Pullable{
	
	public PullableDownListView(Context context) {
		super(context);
	}

	public PullableDownListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableDownListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (getCount() == 0) {
			return true;
		} else if (getFirstVisiblePosition() == 0 && getChildAt(0) != null && getChildAt(0).getTop() >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canPullUp() {
		return false;
	}
}
