package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.he.joint.R;
import com.he.joint.common.BitmapHelper;
import com.he.joint.mgr.DataMgr;

import java.util.List;

public class FeedbackGridViewAdapter extends BaseAdapter {

	public FeedbackGridViewAdapterListener listener;
	private Context ctx;
	private List<String> dataList;

	public FeedbackGridViewAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	

	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}



	@Override
	public int getCount() {
		if (dataList == null) {
			return 1;
		}
		return dataList.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_feedback_gridview, null);
			holder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
			holder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.ivDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (listener != null) {
					listener.onDelete(position);
				}
			}
		});
		
		holder.ivPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (dataList == null || position == dataList.size()) {
					if (listener != null) {
						listener.onAdd();
					}
				}
			}
		});
		
		if (dataList == null || position == dataList.size()) {
			holder.ivPhoto.setImageResource(R.drawable.pinglun_tianjia);
			holder.ivDelete.setVisibility(View.INVISIBLE);
		} else {
			holder.ivDelete.setVisibility(View.VISIBLE);
			int size = (int)(55* DataMgr.getInstance().screenDensity+0.5);
			Bitmap bitmap = BitmapHelper.decodeBitmap(dataList.get(position), size, size);
			holder.ivPhoto.setImageBitmap(bitmap);
		}
		

		return convertView;
	}

	static class ViewHolder {
		ImageView ivPhoto, ivDelete;
	}
	
	public interface FeedbackGridViewAdapterListener {
		public void onDelete(int index);
		public void onAdd();
	}

}
