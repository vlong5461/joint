/**
 * 
 */
package com.third.view.tag;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;

import com.he.joint.R;

import java.util.ArrayList;
import java.util.List;


public class ChooseCountryTagListView extends FlowLayout implements OnClickListener {

	private boolean mIsDeleteMode;
//	private OnTagCheckedChangedListener mOnTagCheckedChangedListener;
	private OnTagClickListener mOnTagClickListener;
	private int mTagViewBackgroundResId;
//	private int mTagViewTextColorResId;
	private final List<Tag> mTags = new ArrayList<Tag>();

	/**
	 * @param context
	 */
	public ChooseCountryTagListView(Context context) {
		super(context);

		init();
	}

	/**
	 * @param context
	 * @param attributeSet
	 */
	public ChooseCountryTagListView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		init();
	}

	/**
	 * @param context
	 * @param attributeSet
	 * @param defStyle
	 */
	public ChooseCountryTagListView(Context context, AttributeSet attributeSet, int defStyle) {
		super(context, attributeSet, defStyle);

		init();
	}

	@Override
	public void onClick(View v) {
		if ((v instanceof TagView)) {
			Tag localTag = (Tag) v.getTag();
			if (ChooseCountryTagListView.this.mOnTagClickListener != null) {
				ChooseCountryTagListView.this.mOnTagClickListener.onTagClick((TagView) v, localTag);
			}
		}
	}

	private void init() {

	}

	private void inflateTagView(final Tag t, boolean b) {

		final TagView localTagView = (TagView) View.inflate(getContext(),
				R.layout.choose_country_tag_item, null);
		localTagView.setText(t.getTitle());
		localTagView.setTag(t);

		if (mTagViewBackgroundResId <= 0) {
			mTagViewBackgroundResId = R.drawable.choose_country_tag_bg;
		}
		localTagView.setBackgroundResource(mTagViewBackgroundResId);

		localTagView.setSelected(t.isChecked());
		if (mIsDeleteMode) {
			int k = (int) TypedValue.applyDimension(1, 5.0F, getContext()
					.getResources().getDisplayMetrics());
			localTagView.setPadding(localTagView.getPaddingLeft(),
					localTagView.getPaddingTop(), k,
					localTagView.getPaddingBottom());
		}
		if (t.getBackgroundResId() > 0) {
			localTagView.setBackgroundResource(t.getBackgroundResId());
		}
		if ((t.getLeftDrawableResId() > 0) || (t.getRightDrawableResId() > 0)) {
			localTagView.setCompoundDrawablesWithIntrinsicBounds(
					t.getLeftDrawableResId(), 0, t.getRightDrawableResId(), 0);
		}
		localTagView.setOnClickListener(this);

		addView(localTagView);
	}

	public void addTag(int i, String s) {
		addTag(i, s, false);
	}

	public void addTag(int i, String s, boolean b) {
		addTag(new Tag(i, s), b);
	}

	public void addTag(Tag tag) {
		addTag(tag, false);
	}

	public void addTag(Tag tag, boolean b) {
		mTags.add(tag);
		inflateTagView(tag, b);
	}

	public void addTags(List<Tag> lists) {
		addTags(lists, false);
	}

	public void addTags(List<Tag> lists, boolean b) {
		for (int i = 0; i < lists.size(); i++) {
			addTag((Tag) lists.get(i), b);
		}
	}

	public List<Tag> getTags() {
		return mTags;
	}

	public View getViewByTag(Tag tag) {
		return findViewWithTag(tag);
	}

	public void removeTag(Tag tag) {
		mTags.remove(tag);
		removeView(getViewByTag(tag));
	}

	public void setDeleteMode(boolean b) {
		mIsDeleteMode = b;
	}

//	public void setOnTagCheckedChangedListener(
//			OnTagCheckedChangedListener onTagCheckedChangedListener) {
//		mOnTagCheckedChangedListener = onTagCheckedChangedListener;
//	}

	public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
		mOnTagClickListener = onTagClickListener;
	}

	public void setTagViewBackgroundRes(int res) {
		mTagViewBackgroundResId = res;
	}

//	public void setTagViewTextColorRes(int res) {
//		mTagViewTextColorResId = res;
//	}

	public void setTags(List<? extends Tag> lists) {
		setTags(lists, false);
	}

	public void setTags(List<? extends Tag> lists, boolean b) {
		removeAllViews();
		mTags.clear();
		for (int i = 0; i < lists.size(); i++) {
			addTag((Tag) lists.get(i), b);
		}
	}

	public static abstract interface OnTagCheckedChangedListener {
		public abstract void onTagCheckedChanged(TagView tagView, Tag tag);
	}

	public static abstract interface OnTagClickListener {
		public abstract void onTagClick(TagView tagView, Tag tag);
	}

}
