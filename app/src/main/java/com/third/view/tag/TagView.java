package com.third.view.tag;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;

import com.he.joint.R;

/**
 * 
 * @author allen.chen
 *
 */
public class TagView extends Button {
	
	private boolean mCheckEnable = true;

	public TagView(Context paramContext) {
		super(paramContext);
		init();
	}

	public TagView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init();
	}

	public TagView(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, 0);
		init();
	}

	private void init() {
		setBackgroundResource(R.drawable.lib_tag_bg);
		setLines(1);
		setEllipsize(TextUtils.TruncateAt.valueOf("END"));
	}
	
	public void setSelected(boolean paramBoolean) {
		if (this.mCheckEnable) {
			super.setSelected(paramBoolean);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
