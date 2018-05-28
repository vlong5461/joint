package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.common.BitmapHelper;
import com.he.joint.mgr.DataMgr;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class ExpertGridViewAdapter extends BaseAdapter {

    public ExpertGridViewAdapterListener listener;
    private Context ctx;
    private List<String> dataList;
    private boolean isShowDel=false;

    public ExpertGridViewAdapter(Context ctx) {
        this.ctx = ctx;
    }


    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public void setShowDel(boolean isShowDel){
        this.isShowDel=isShowDel;
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

        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_expert_gridview, null);
            holder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            holder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(isShowDel){
            holder.ivDelete.setVisibility(View.VISIBLE);
        }else{
            holder.ivDelete.setVisibility(View.INVISIBLE);
        }
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (listener != null) {
                    listener.onDelete(position);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        ImageView ivPhoto, ivDelete;
        TextView tvName;
    }

    public interface ExpertGridViewAdapterListener {
        public void onDelete(int index);
    }
}

