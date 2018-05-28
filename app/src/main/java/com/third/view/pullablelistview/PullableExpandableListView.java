package com.third.view.pullablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.he.joint.R;


public class PullableExpandableListView extends ExpandableListView implements Pullable {

	public PullableExpandableListView(Context context) {
		super(context);
	}

	public PullableExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableExpandableListView(Context context, AttributeSet attrs, int defStyle) {
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
		if (!canLoadMore) {
			return false;
		}

		if (getCount() == 0) {
			return false;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}

	private boolean canLoadMore = true;

	public void setCanLoadMore(boolean canLoadMore) {
		this.canLoadMore = canLoadMore;
	}

	private View footerView;
	private LinearLayout llLoadRunning, llLoadFinished;
	public void addLoadView() {
		View container = LayoutInflater.from(this.getContext()).inflate(R.layout.view_load_more_footer, this, false);
		footerView = container.findViewById(R.id.flFooter);
		llLoadRunning = (LinearLayout) container.findViewById(R.id.llLoadRunning);
		llLoadFinished = (LinearLayout) container.findViewById(R.id.llLoadFinished);

		container.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//禁用footer view的点击事件
			}
		});

		this.addFooterView(container);
	}

	public void setLoadViewVisibility(int flag) {
		footerView.setVisibility(flag);
	}

	public void setFinishedViewVisibility(int flag) {
		llLoadFinished.setVisibility(flag);
	}

	public void setRunningViewVisibility(int flag) {
		llLoadRunning.setVisibility(flag);
	}

	public void setLoadViewBackground(int res) {
		if (footerView != null) {
			footerView.setBackgroundColor(getResources().getColor(res));
		}
	}

}
