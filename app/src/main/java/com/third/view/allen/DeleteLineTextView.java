package com.third.view.allen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.he.joint.mgr.DataMgr;

public class DeleteLineTextView extends TextView {
	public DeleteLineTextView(Context context) {
		super(context);
		initPaint();
	}

	public DeleteLineTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	public DeleteLineTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		paint = new Paint();
		paint.setColor(this.getCurrentTextColor());
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(DataMgr.dip2px(1));
	}

	private Paint paint;

	@Override
	protected void onDraw(Canvas canvas) {
		if (showDeleteLine) {
			float x = this.getWidth();
			float y = this.getHeight();
			canvas.drawLine(DataMgr.dip2px(2), y / 2f + DataMgr.dip2px(1), x, y / 2f + DataMgr.dip2px(1), paint);
		} else {

		}
		
		super.onDraw(canvas);
	}
	
	private boolean showDeleteLine = true;
	public void setShowDeleteLine(boolean showDeleteLine) {
		this.showDeleteLine = showDeleteLine;
	}
}
