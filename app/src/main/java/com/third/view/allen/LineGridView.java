package com.third.view.allen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.he.joint.R;
import com.he.joint.mgr.DataMgr;

public class LineGridView extends GridView {
	public LineGridView(Context context) {
		super(context);
	}

	public LineGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LineGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (isInEditMode()) {
			return;
		}
//		View localView1 = getChildAt(0);
//		int column = getWidth() / localView1.getWidth();// 计算出一共有多少列，假设有3列
		int column = DataMgr.dip2px(80);
		int childCount = getChildCount();// 子view的总数
		Paint localPaint;// 画笔
		localPaint = new Paint();
		localPaint.setStyle(Paint.Style.STROKE);
		localPaint.setColor(getContext().getResources().getColor(R.color.splite_line));// 设置画笔的颜色
		for (int i = 0; i < childCount; i++) {// 遍历子view
			View cellView = getChildAt(i);// 获取子view
			if (i < 3) {// 第一行
				canvas.drawLine(cellView.getLeft(), cellView.getTop(), cellView.getRight(), cellView.getTop(),
						localPaint);
			}
			if (i % column == 0) {// 第一列
				canvas.drawLine(cellView.getLeft(), cellView.getTop(), cellView.getLeft(), cellView.getBottom(),
						localPaint);
			}
			if ((i + 1) % column == 0) {// 第三列
				// 画子view底部横线
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
						localPaint);
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(),
						localPaint);
			} else if ((i + 1) > (childCount - (childCount % column))) {// 如果view是最后一行
				// 画子view的右边竖线
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(),
						localPaint);
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
						localPaint);
			} else {// 如果view不是最后一行
				// 画子view的右边竖线
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(),
						localPaint);
				// 画子view的底部横线
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
						localPaint);
			}
		}
	}
}
