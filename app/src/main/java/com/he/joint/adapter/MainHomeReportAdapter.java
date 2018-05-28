package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class MainHomeReportAdapter  extends BaseExpandableListAdapter {

    private Context context;
    private List<String> titleList;

    public MainHomeReportAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<String> titleList) {
        this.titleList = titleList;
    }
    @Override
    public Object getGroup(int groupPosition) {
        return 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getGroupCount() {
        int count = 2;
        if (CollectionUtils.isNotEmpty(titleList)) {
            count += titleList.size();
        }
        return count;
    }

    @Override
    public int getGroupType(int groupPosition) {

        if (groupPosition == 0) {
                return GroupType.TopTag;
        } else if (groupPosition == 1) {
            return GroupType.Product;
        } else if (groupPosition >= 2) {
            return GroupType.Info;
        }
        return GroupType.Empty;
    }

    @Override
    public int getGroupTypeCount() {
        return 4;
    }

    @SuppressWarnings("rawtypes")
    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        int type = getGroupType(groupPosition);

        if (type == GroupType.TopTag) {
            GroupTopTagHolder holder;
            if (convertView == null) {
                holder = new GroupTopTagHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_main_home_report_top, null);
                convertView.setTag(holder);
            } else {
                holder = (GroupTopTagHolder) convertView.getTag();
            }

        } else if (type == GroupType.Product) {
            GroupProuctsHolder holder;
            if (convertView == null) {
                holder = new GroupProuctsHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_main_home_report_product, null);
//                holder.gvProuct = (GridView) convertView.findViewById(R.id.gvProuct);
//                ReportGridViewAdapter adapter = new ReportGridViewAdapter(context);
//                holder.gvProuct.setAdapter(adapter);
//                List<String> dataList=new ArrayList<>();
//                for(int i=0;i<4;i++) {
//                    dataList.add("火灾报警产品");
//                }
//                adapter.setDataList(dataList);
//                adapter.notifyDataSetChanged();
                convertView.setTag(holder);
            } else {
                holder = (GroupProuctsHolder) convertView.getTag();
            }

        } else if (type == GroupType.Info) {
            GroupInfoHolder holder;
            if (convertView == null) {
                holder = new GroupInfoHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adpater_main_home_report_info, null);
                holder.tvTitle=(TextView)convertView.findViewById(R.id.tvTitle);
                convertView.setTag(holder);
            } else {
                holder = (GroupInfoHolder) convertView.getTag();
            }
            int index = groupPosition - 2;
            holder.tvTitle.setText(titleList.get(index));
            if (index == titleList.size() - 1 && mLoadMoreListener != null) {
                mLoadMoreListener.checkLoadMoreData();
            }
        } else {
            convertView = new View(context);
        }

        return convertView;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildTypeCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                             ViewGroup parent) {
        int type = getChildType(groupPosition, childPosition);

        convertView = new View(context);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupTopTagHolder {
        EditText etSearch;
    }

    static class GroupProuctsHolder {
        GridView gvProuct;
    }

    static class GroupInfoHolder {
        LinearLayout llAuthenticationProductInfo,llAppraisalProductInfo,llOtherProductInfo;
        TextView tvTitle;
    }

    private interface GroupType {
        int TopTag = 1;
        int Product = 2;
        int Info = 3;
        int Empty = 4;
    }

    private LoadMoreListener mLoadMoreListener;

    public void setLoadMoreListener(LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
    }

    public interface LoadMoreListener {
        public void checkLoadMoreData();
    }
}
