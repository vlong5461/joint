package com.he.joint.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.mgr.DataMgr;

public class CusSeekBar extends SeekBar {
	private PopupWindow mPopupWindow;

	private LayoutInflater mInflater;
	private View mView;
	private int[] mPosition;

	private Context mContext;

	private final int mThumbWidth = 25;
	private TextView mTvProgress;

	private int mWidth;
	private int mHeight;
	int mProgress;
	int mViewWidth;
	int mDX;
	int mOneStep;
	int mStartX;

	public CusSeekBar(Context context) {
		super(context);
		init(context);
	}

	public CusSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CusSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mView = mInflater.inflate(R.layout.popwindow_layout, null);
		mTvProgress = (TextView) mView.findViewById(R.id.tvPop);
		mPopupWindow = new PopupWindow(mView, mView.getWidth(),
				mView.getHeight(), true);
		mPosition = new int[2];
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);

		mWidth = wm.getDefaultDisplay().getWidth();
		mHeight = wm.getDefaultDisplay().getHeight();

		mViewWidth = getWidth();
		mDX = mWidth - mViewWidth;
		mOneStep = mViewWidth / getMax();
		mStartX = mWidth - mDX / 2;
	}

	public void setSeekBarText(String str) {
		mTvProgress.setText(str);
		mProgress = getProgress();
		mPopupWindow.showAsDropDown(this, mStartX + mOneStep * mProgress, mHeight - 30);
		mPopupWindow.setTouchable(false);
		mPopupWindow.setFocusable(false);
		mPopupWindow.setOutsideTouchable(false);
	}

	private int getViewWidth(View v) {
		int w = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
		return v.getMeasuredWidth();
	}

	private int getViewHeight(View v) {
		int w = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
		return v.getMeasuredHeight();
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		int thumb_x = this.getProgress() * (this.getWidth() - mThumbWidth)
				/ this.getMax();
		int middle = DataMgr.dip2px(55);
		super.onDraw(canvas);

		if (mPopupWindow != null) {
			try {
				this.getLocationOnScreen(mPosition);
				mPopupWindow.update(thumb_x + mPosition[0]
						- getViewWidth(mView) / 2 + mThumbWidth / 2, middle,
						getViewWidth(mView), getViewHeight(mView));
				mPopupWindow.setTouchable(false);
				mPopupWindow.setFocusable(false);
				mPopupWindow.setOutsideTouchable(false);
			} catch (Exception e) {
				
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//原来是要将TouchEvent传递下去的,我们不让它传递下去就行了
		//return super.onTouchEvent(event);
		return false ;
	}
}
