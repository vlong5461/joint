package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.he.joint.R;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class ReportGridViewAdapter extends BaseAdapter {

    private Context ctx;
    private List<String> dataList;

    public ReportGridViewAdapter(Context ctx) {
        this.ctx = ctx;
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

        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_report_gridview, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(dataList.get(position).toString());
        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
    }

}
