package com.he.joint.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.he.joint.activity.MainActivity;
import com.he.joint.R;
import com.he.joint.common.Cast;
import com.he.joint.dialog.WaitingDialog;
import com.he.joint.mgr.DataMgr;
import com.he.joint.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BaseActivity extends FragmentActivity {

	protected Context mContext;
	public WaitingDialog waitingDialog;

	private ImageView topNavBarLeftBack, topNavBarRightImg;
	protected TextView topNavBarTitle;
	private Button topNavBarRightButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = this;
		activityList.add(this);

		initFontScale();

	}


	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	private void initFontScale() {
		Configuration configuration = getResources().getConfiguration();
		configuration.fontScale = (float) 1;
		//0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		metrics.scaledDensity = configuration.fontScale * metrics.density;
		getBaseContext().getResources().updateConfiguration(configuration, metrics);
	}

	protected void initTopBar(String title) {
		initTopBar(title, null);
	}

	protected void initTopBar(String title, String rightTitle) {
		topNavBarLeftBack = findView(R.id.topNavBarLeftBack);
		topNavBarTitle = findView(R.id.topNavBarTitle);
		topNavBarRightButton = findView(R.id.topNavBarRightButton);
		if (topNavBarLeftBack != null) {
			topNavBarLeftBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					topNavBarBackClicked();
					finish();
				}
			});
		}
		if (topNavBarTitle != null) {
			topNavBarTitle.setText(title);
		}
		if (topNavBarRightButton != null) {
			if (StringUtils.isNotEmpty(rightTitle)) {
				topNavBarRightButton.setText(rightTitle);
				topNavBarRightButton.setVisibility(View.VISIBLE);
				topNavBarRightButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						topNavBarRightClicked();
					}
				});
			} else {
				topNavBarRightButton.setVisibility(View.INVISIBLE);
			}
		}
	}

	protected void initTopBar(String title, int rightImg) {
		topNavBarLeftBack = findView(R.id.topNavBarLeftBack);
		topNavBarTitle = findView(R.id.topNavBarTitle);
		topNavBarRightImg = findView(R.id.topNavBarRightImg);
		if (topNavBarLeftBack != null) {
			topNavBarLeftBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					topNavBarBackClicked();
					finish();
				}
			});
		}
		if (topNavBarTitle != null) {
			topNavBarTitle.setText(title);
		}
		if (topNavBarRightImg != null) {
			if (rightImg > 0) {
				topNavBarRightImg.setImageResource(rightImg);
				topNavBarRightImg.setVisibility(View.VISIBLE);
				topNavBarRightImg.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						topNavBarRightClicked();
					}
				});
			} else {
				topNavBarRightButton.setVisibility(View.INVISIBLE);
			}
		}
	}

	protected void topNavBarRightClicked() {

	}

	protected void topNavBarBackClicked() {

	}

	protected void setTopBarTitle(String title) {
		if (topNavBarTitle != null) {
			topNavBarTitle.setText(title);
		}
	}

	protected void setTopBarTitle(int title) {
		if (topNavBarTitle != null) {
			topNavBarTitle.setText(title);
		}
	}

	public void showWaitingDialog(Context context) {
		showWaitingDialog(mContext, null);
	}

	public void showWaitingDialog(Context context, String message) {
		if (waitingDialog == null) {
			waitingDialog = new WaitingDialog(context);
		}

		if (!waitingDialog.isShowing()) {
			waitingDialog.setCancelable(false);
			waitingDialog.show();
		}

		waitingDialog.setMessage(message);
	}

	public void dismissWaitingDialog() {
		if (waitingDialog != null && waitingDialog.isShowing()) {
			waitingDialog.dismiss();
			waitingDialog = null;
		}
	}

	protected <T> T findView(int id) {
		return Cast.cast(findViewById(id));
	}

	public static final List<Activity> activityList = new ArrayList<Activity>();

	@Override
	protected void onDestroy() {
		dismissWaitingDialog();
		Iterator<Activity> iterator = activityList.iterator();
		while (iterator.hasNext()) {
			Activity ac = iterator.next();
			if (ac != null && ac == this) {
				iterator.remove();
				break;
			}
		}

		super.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private float xDowm = -1f, yDowm, xUp, yUp, xDistance, yDistance;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (!(this instanceof MainActivity)) {
				xDowm = event.getRawX();
				yDowm = event.getRawY();
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {

			if (xDowm >= 0 && xDowm <= DataMgr.dip2px(25)) {
				xUp = event.getRawX();
				yUp = event.getRawY();
				xDistance = Math.abs(xUp - xDowm);
				yDistance = Math.abs(yUp - yDowm);

			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (xDowm == -2) {
				return false;
			}
		}

		boolean flag = super.dispatchTouchEvent(event);
		return flag;
	}



}
