package com.he.joint.slidelistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.he.joint.R;
import com.third.view.pullablelistview.Pullable;

@SuppressLint("RtlHardcoded")
public class SlideListView extends ListView  implements Pullable {
	public static final boolean DEUBG = true;
	public static final String TAG = SlideListView.class.getSimpleName();
	// extern Listener
	private OnItemClickListener mOnItemClickListener;
	private OnScrollListener mOnScrollListener;
	private SlideItemListener mSlideItemListener;
	// inner listener
	private SlideTouchListener mTouchListener;
	// Slide value
	private long mAnimationTime;
	private SlideMode mSlideMode;
	private SlideAction mSlideLeftAction;
	private SlideAction mSlideRightAction;

	private SlideBaseAdapter mAdapter;

	private boolean isInScrolling = false;

	public SlideListView(Context context) {
		super(context);
		init(null);
	}

	public SlideListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public SlideListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	/**
	 * Init ListView
	 * 
	 * @param attrs
	 *            AttributeSet
	 */
	@SuppressLint("ClickableViewAccessibility")
	private void init(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray styled = getContext().obtainStyledAttributes(attrs, R.styleable.SlideListView);
			mAnimationTime = styled.getInteger(R.styleable.SlideListView_slideAnimationTime, 0);
			mSlideMode = SlideMode.mapIntToValue(styled.getInteger(R.styleable.SlideListView_slideMode, 0));
			mSlideLeftAction = SlideAction.mapIntToValue(styled.getInteger(R.styleable.SlideListView_slideLeftAction, 0));
			mSlideRightAction = SlideAction.mapIntToValue(styled.getInteger(R.styleable.SlideListView_slideRightAction, 0));
			styled.recycle();
		}
		mTouchListener = new SlideTouchListener(this);
		// You can't use setOnTouchListener() in your own code
		setOnTouchListener(mTouchListener);
		// You can use setOnScrollListener() in your own code
		setOnScrollListener(mInnerOnScrollListener);
		// You can use setOnItemClickListener() in your own code
		setOnItemClickListener(mInnerOnItemClickListener);
	}

	@Override
	public void setOnItemClickListener(OnItemClickListener listener) {
		if (listener != mInnerOnItemClickListener) {
			mOnItemClickListener = listener;
		} else {
			super.setOnItemClickListener(listener);
		}
	}

	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		if (listener != mInnerOnScrollListener) {
			mOnScrollListener = listener;
		} else {
			super.setOnScrollListener(listener);
		}
	}

	private OnScrollListener mInnerOnScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == SCROLL_STATE_IDLE) {
				isInScrolling = false;
			} else {
				isInScrolling = true;
			}
			if (mOnScrollListener != null) {
				mOnScrollListener.onScrollStateChanged(view, scrollState);
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			if (mOnScrollListener != null) {
				mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		}
	};

	private OnItemClickListener mInnerOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (mTouchListener.isOpend()) {
				mTouchListener.closeOpenedItem();
				return;
			}
			if (mOnItemClickListener != null) {
				mOnItemClickListener.onItemClick(parent, view, position, id);
			}
		}
	};

	public void setSlideItemListener(SlideItemListener listener) {
		mSlideItemListener = listener;
	}

	// notify opend
	void onOpend(int position, boolean left) {
		if (DEUBG) {
			Log.d(TAG, (left ? "left" : "right") + " back view " + "is opend at position " + position);
		}
		if (mSlideItemListener != null) {
			mSlideItemListener.onOpend(position, left);
		}
	}

	// notify closed
	void onClosed(int position, boolean left) {
		if (DEUBG) {
			Log.d(TAG, (left ? "left" : "right") + " back view " + "is closed at position " + position);
		}
		if (mSlideItemListener != null) {
			mSlideItemListener.onClosed(position, left);
		}
	}

	boolean isInScrolling() {
		return isInScrolling;
	}

	boolean isSlideAdapter() {
		return mAdapter != null;
	}

	SlideBaseAdapter getSlideAdapter() {
		return mAdapter;
	}

	boolean isSlideEnable() {
		return isSlideAdapter() && mSlideMode != SlideMode.NONE;
	}

	public void setSlideMode(SlideMode slideMode) {
		if (mSlideMode != slideMode) {
			if (isSlideAdapter()) {
				if (mTouchListener.isOpend()) {
					mTouchListener.closeOpenedItem();
				}
				mAdapter.setSlideMode(slideMode);
				mAdapter.notifyDataSetInvalidated();
			}
			mSlideMode = slideMode;
		}
	}

	public SlideMode getSlideMode() {
		return mSlideMode;
	}

	public void setSlideLeftAction(SlideAction slideAction) {
		if (mSlideLeftAction != slideAction) {
			if (isSlideAdapter()) {
				if (mTouchListener.isOpend()) {
					mTouchListener.closeOpenedItem();
				}
			}
			mSlideLeftAction = slideAction;
			if (isSlideAdapter()) {
				SlideBaseAdapter adapter = mAdapter;
				setAdapter(null);
				setAdapter(adapter);
			}
		}
	}

	public SlideAction getSlideLeftAction() {
		return mSlideLeftAction;
	}

	public void setSlideRightAction(SlideAction slideAction) {
		if (mSlideRightAction != slideAction) {
			if (isSlideAdapter()) {
				if (mTouchListener.isOpend()) {
					mTouchListener.closeOpenedItem();
				}
			}
			mSlideRightAction = slideAction;
			if (isSlideAdapter()) {
				SlideBaseAdapter adapter = mAdapter;
				setAdapter(null);
				setAdapter(adapter);
			}
		}
	}

	public SlideAction getSlideRightAction() {
		return mSlideRightAction;
	}

	public long getAnimationTime() {
		return mAnimationTime;
	}

	public void setAnimationTime(long animationTime) {
		this.mAnimationTime = animationTime;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		mAdapter = null;
		if (adapter != null && adapter instanceof SlideBaseAdapter) {
			mAdapter = (SlideBaseAdapter) adapter;
			mAdapter.setSlideMode(mSlideMode);
			mAdapter.setSlideLeftAction(mSlideLeftAction);
			mAdapter.setSlideRightAction(mSlideRightAction);
		}
		super.setAdapter(adapter);
		mTouchListener.reset();
		if (adapter != null) {
			adapter.registerDataSetObserver(new DataSetObserver() {
				@Override
				public void onChanged() {
					super.onChanged();
					if (DEUBG) {
						Log.e(TAG, "Adapter data has changed");
					}
					if (mTouchListener.isOpend()) {
						mTouchListener.closeOpenedItem();
					}
					mTouchListener.reset();
				}
			});
		}
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (isEnabled() && isSlideEnable()) {
			int action = MotionEventCompat.getActionMasked(ev);
			if (action == MotionEvent.ACTION_DOWN) {
				int downPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
				int opendPosition = mTouchListener.getOpendPosition();
				// There is a item in opend or half opend(exception occured in
				// previous slideing event) status
				if (opendPosition != INVALID_POSITION) {
					// if slideing or auto
					// slideing(SlideTouchListener.autoScroll()) has not
					// finished,drop this motion event(avoid
					// NullPointerException)
					if (mTouchListener.isInSliding()) {
						return false;
					}
					// if down position not equals the opend position,drop this
					// motion event and close the opend item
					if (downPosition != opendPosition) {
						mTouchListener.closeOpenedItem();
						return false;
					}
				}
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	@SuppressLint("RtlHardcoded")
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (isEnabled() && isSlideEnable()) {
			if (mTouchListener.onInterceptTouchEvent(ev)) {
				return true;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}

	public static enum SlideMode {
		NONE(0x0), LEFT(0x1), RIGHT(0x2), BOTH(0x3);
		/**
		 * Maps an int to a specific mode. This is needed when saving state, or
		 * inflating the view from XML where the mode is given through a attr
		 * int.
		 * 
		 * @param modeInt
		 *            - int to map a Mode to
		 * @return Mode that modeInt maps to, or PULL_FROM_START by default.
		 */
		static SlideMode mapIntToValue(final int modeInt) {
			for (SlideMode value : SlideMode.values()) {
				if (modeInt == value.getIntValue()) {
					return value;
				}
			}
			// If not, return default
			return getDefault();
		}

		static SlideMode getDefault() {
			return NONE;
		}

		private int mIntValue;

		// The modeInt values need to match those from attrs.xml
		SlideMode(int modeInt) {
			mIntValue = modeInt;
		}

		int getIntValue() {
			return mIntValue;
		}

	}

	public static enum SlideAction {
		SCROLL(0x0), REVEAL(0x1);
		/**
		 * Maps an int to a specific mode. This is needed when saving state, or
		 * inflating the view from XML where the mode is given through a attr
		 * int.
		 * 
		 * @param modeInt
		 *            - int to map a Mode to
		 * @return Mode that modeInt maps to, or PULL_FROM_START by default.
		 */
		static SlideAction mapIntToValue(final int actionInt) {
			for (SlideAction value : SlideAction.values()) {
				if (actionInt == value.getIntValue()) {
					return value;
				}
			}

			// If not, return default
			return getDefault();
		}

		static SlideAction getDefault() {
			return SCROLL;
		}

		private int mIntValue;

		// The modeInt values need to match those from attrs.xml
		SlideAction(int actionInt) {
			mIntValue = actionInt;
		}

		int getIntValue() {
			return mIntValue;
		}

	}
	
	@Override
	public boolean canPullDown() {
		if (canPullDown) {
			if (getCount() == 0) {
				return true;
			} else if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean canPullUp() {
		if (!canLoadMore) {
			return false;
		}

		if (getCount() == 0) {
			return true;
		} else if (getLastVisiblePosition() == (getCount() - 1)) {
			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
					&& getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
				return true;
		}
		return false;
	}
	
	private boolean canPullDown = false;
	public void setCanPullDown(boolean flag) {
		canPullDown = flag;
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
				//禁用footer view响应点击事件
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
