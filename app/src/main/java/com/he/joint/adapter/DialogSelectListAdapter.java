package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.he.joint.R;

import java.util.List;

public class DialogSelectListAdapter extends BaseAdapter {

	private Context ctx;
	private List<String> dataList;

	public DialogSelectListAdapter(Context ctx, List<String> dataList) {
		this.ctx = ctx;
		this.dataList=dataList;
	}

	public void setDataList(List<String> dataList) {
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		if (dataList == null) {
			return 0;
		}
		return dataList.size();
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

		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(R.layout.dialog_simple_list_item, null);
			holder.tvText = (TextView)convertView.findViewById(R.id.tvText);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(dataList.size()>0){
			holder.tvText.setText(dataList.get(position).toString());
		}
		return convertView;
	}

	static class ViewHolder {
		TextView tvText;
	}
	
}
