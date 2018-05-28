package com.third.view.pullablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class PullableDownExpandableListView extends ExpandableListView implements Pullable {

	public PullableDownExpandableListView(Context context) {
		super(context);
	}

	public PullableDownExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableDownExpandableListView(Context context, AttributeSet attrs, int defStyle) {
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
