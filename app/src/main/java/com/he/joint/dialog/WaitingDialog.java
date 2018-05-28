package com.he.joint.dialog;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.utils.StringUtils;

public class WaitingDialog extends BaseDialog {

	private static int default_width = -1;
	private static int default_height = -2;

	private TextView tvMessage;

	public WaitingDialog(Context context) {
		super(context, default_width, default_height, R.layout.dialog_waiting_view, R.style.Waiting_View,
				Gravity.CENTER);
		this.setCancelable(false);
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	public void setMessage(String message) {

		if (StringUtils.isNotEmpty(message)) {
			tvMessage = (TextView) findViewById(R.id.tvMessage);
			tvMessage.setText(message);
		}
	}

}
