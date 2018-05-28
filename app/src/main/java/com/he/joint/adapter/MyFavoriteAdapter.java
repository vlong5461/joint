package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.bean.ProductBriefBean;
import com.he.joint.mgr.DataMgr;
import com.he.joint.slidelistview.SlideBaseAdapter;
import com.he.joint.utils.CollectionUtils;
import com.he.joint.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.third.view.allen.DeleteLineTextView;

import java.util.List;

public class MyFavoriteAdapter extends SlideBaseAdapter {

	public MyFavoriteAdapterListener listener;
	private List<ProductBriefBean> dataList;
	private Context ctx;

	public MyFavoriteAdapter(Context ctx) {
		super(ctx);
		this.ctx = ctx;
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

	public void setDataList(List<ProductBriefBean> dataList) {
		this.dataList = dataList;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = createConvertView(position);
			holder.ivPicture = (ImageView) convertView.findViewById(R.id.ivPicture);
			holder.ivMark = (ImageView) convertView.findViewById(R.id.ivMark);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvFrom = (TextView) convertView.findViewById(R.id.tvFrom);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.tvFavNum = (TextView) convertView.findViewById(R.id.tvFavNum);
			holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (holder.btnDelete != null) {
			holder.btnDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (listener != null) {
						listener.onDelete(position);
					}
				}
			});
		}
		
		ProductBriefBean bean = dataList.get(position);
		
		holder.tvTitle.setText(bean.Title);
		if(StringUtils.isNotEmpty(bean.ViewImage)) {
			if (!bean.ViewImage.equals(holder.ivPicture.getTag())) {
				holder.ivPicture.setTag(bean.ViewImage);
				ImageLoader.getInstance().displayImage(bean.ViewImage, holder.ivPicture, DataMgr.options);
			}
		}

		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle, tvFrom, tvTime,tvFavNum;
		ImageView ivPicture, ivMark;
		Button btnDelete;
	}

	@Override
	public int getFrontViewId(int position) {
		return R.layout.adapter_my_favorite;
	}

	@Override
	public int getLeftBackViewId(int position) {
		return 0;
	}

	@Override
	public int getRightBackViewId(int position) {
		return R.layout.row_right_delete_back_view;
	}
	
	public interface MyFavoriteAdapterListener {
		public void onDelete(int index);
	}

}
