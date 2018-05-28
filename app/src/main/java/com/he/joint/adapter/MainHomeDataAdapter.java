package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.utils.CollectionUtils;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class MainHomeDataAdapter  extends BaseExpandableListAdapter {

    private Context context;
    private List<String> titleList;

    public MainHomeDataAdapter(Context context) {
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
        if (titleList == null) {
            return 0;
        }
        int count = 1;
        if (titleList.size() > 0) {
            count += titleList.size();
        }
        return count;
    }

    @Override
    public int getGroupType(int groupPosition) {

        if (groupPosition == 0) {
                return GroupType.TopTag;
        }else if (groupPosition >= 1) {
            if (CollectionUtils.isNotEmpty(titleList)) {
                return GroupType.Item;
            }
        }
        return GroupType.Empty;
    }

    @Override
    public int getGroupTypeCount() {
        return 3;
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
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_main_home_data_top, null);
                convertView.setTag(holder);
            } else {
                holder = (GroupTopTagHolder) convertView.getTag();
            }
        } else if (type == GroupType.Item) {
            GroupItemHolder holder;
            if (convertView == null) {
                holder = new GroupItemHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_main_home_data_item, null);
                holder.ivFrom = (ImageView) convertView.findViewById(R.id.ivFrom);
                holder.ivDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
                holder.tvReadNum = (TextView) convertView.findViewById(R.id.tvReadNum);
                convertView.setTag(holder);
            } else {
                holder = (GroupItemHolder) convertView.getTag();
            }
            int index = groupPosition - 1;
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
        LinearLayout llStandard,llLaw,llPolicy,llManagement;
    }

    static class GroupNewsHolder {
        TextView tvNews;
    }

    static class GroupItemHolder {
        TextView tvTitle, tvContent,tvTime, tvReadNum;
        ImageView ivFrom, ivDelete;
    }

    private interface GroupType {
        int TopTag = 1;
        int Item = 2;
        int Empty = 3;
    }

    private LoadMoreListener mLoadMoreListener;

    public void setLoadMoreListener(LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
    }

    public interface LoadMoreListener {
        public void checkLoadMoreData();
    }
}
