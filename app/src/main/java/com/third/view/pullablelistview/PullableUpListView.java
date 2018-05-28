package com.third.view.pullablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableUpListView extends ListView implements Pullable{

	public PullableUpListView(Context context) {
		super(context);
	}

	public PullableUpListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableUpListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		return false;
	}

	@Override
	public boolean canPullUp() {
		if (getCount() == 0) {
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}
}
