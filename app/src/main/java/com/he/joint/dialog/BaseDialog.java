package com.he.joint.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import com.he.joint.mgr.DataMgr;


public class BaseDialog extends Dialog {
	protected Context context;

	protected BaseDialog(Context context) {
		super(context);
	}
	
	protected BaseDialog(Context context, int width, int height, int layout, int style, int gravity) {
		super(context, style);

		this.context = context;
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		if (width == -1) {
			params.width = WindowManager.LayoutParams.MATCH_PARENT;
		} else if (width == -2) {
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		} else {
			params.width = (int)(width * DataMgr.screenDensity + 0.5);
		}
		if (height == -1) {
			params.height = WindowManager.LayoutParams.MATCH_PARENT;
		} else if (height == -2) {
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		} else {
			params.height = (int)(height * DataMgr.screenDensity + 0.5);
		}
		params.gravity = gravity;
		window.setAttributes(params);
	}
	

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
}
