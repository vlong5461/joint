package com.he.joint.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.bean.HomeRecommendBean;
import com.he.joint.mgr.DataMgr;
import com.he.joint.utils.CollectionUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.third.view.banner.CBPageAdapter;
import com.third.view.banner.CBViewHolderCreator;
import com.third.view.banner.ConvenientBanner;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/6/4.
 */

public class MainHomeRecommendAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HomeRecommendBean homeRecommendBean;

    public MainHomeRecommendAdapter(Context context, HomeRecommendBean homeData) {
        this.context = context;
        this.homeRecommendBean = homeData;
    }

    public void setData(HomeRecommendBean homeData) {
        this.homeRecommendBean = homeData;
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
        if (homeRecommendBean == null) {
            return 0;
        }
        int count = 2;
        if (homeRecommendBean.dataList.size() > 0) {
            count += homeRecommendBean.dataList.size();
        }
        return count;
    }

    @Override
    public int getGroupType(int groupPosition) {

        if (groupPosition == 0) {
            if (CollectionUtils.isNotEmpty(homeRecommendBean.imageList)) {
                return GroupType.TopTag;
            }
        } else if (groupPosition == 1) {
                return GroupType.News;
        } else if (groupPosition >= 2) {
            if (CollectionUtils.isNotEmpty(homeRecommendBean.dataList)) {
                return GroupType.Item;
            }
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
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_main_home_recommend_top, null);
                holder.banner = (ConvenientBanner) convertView.findViewById(R.id.banner);
                convertView.setTag(holder);
            } else {
                holder = (GroupTopTagHolder) convertView.getTag();
            }
            setRecommendBanner(holder.banner, true);

        } else if (type == GroupType.News) {
            GroupNewsHolder holder;
            if (convertView == null) {
                holder = new GroupNewsHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_main_home_recommend_news, null);
                holder.tvNews = (TextView) convertView.findViewById(R.id.tvNews);
                convertView.setTag(holder);
            } else {
                holder = (GroupNewsHolder) convertView.getTag();
            }

        } else if (type == GroupType.Item) {
            GroupItemHolder holder;
            if (convertView == null) {
                holder = new GroupItemHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_my_favorite, null);
                holder.ivPicture = (ImageView) convertView.findViewById(R.id.ivPicture);
                holder.ivMark = (ImageView) convertView.findViewById(R.id.ivMark);
                holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                holder.tvFrom = (TextView) convertView.findViewById(R.id.tvFrom);
                holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
                holder.tvFavNum = (TextView) convertView.findViewById(R.id.tvFavNum);
                convertView.setTag(holder);
            } else {
                holder = (GroupItemHolder) convertView.getTag();
            }
            int index = groupPosition - 2;
            if (index == homeRecommendBean.dataList.size() - 1 && mLoadMoreListener != null) {
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
        @SuppressWarnings("rawtypes")
        ConvenientBanner banner;
    }

    static class GroupNewsHolder {
        TextView tvNews;
    }

    static class GroupItemHolder {
        TextView tvTitle, tvFrom, tvTime,tvFavNum;
        ImageView ivPicture, ivMark;
    }

    private interface GroupType {
        int TopTag = 1;
        int News = 2;
        int Item = 3;
        int Empty = 4;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setRecommendBanner(ConvenientBanner banner, boolean canTurn) {
        if (CollectionUtils.isEmpty(homeRecommendBean.imageList)) {
            return;
        }
        banner.setPages(new CBViewHolderCreator<RecommendNannerHolder>() {
            @Override
            public RecommendNannerHolder createHolder() {
                return new RecommendNannerHolder();
            }
        }, homeRecommendBean.imageList)
                .setPageIndicator(new int[]{R.drawable.yuanbai, R.drawable.yuanhong})
                .setPageTransformer(ConvenientBanner.Transformer.DepthPageTransformer);
        int height = (int) (DataMgr.screenWidth / (640f / 300f) + 0.5);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(DataMgr.screenWidth, height);
        banner.setLayoutParams(param);

        banner.startTurning(5000);
    }

    public class RecommendNannerHolder implements CBPageAdapter.Holder<String> {
        private ImageView imageView;

        @SuppressLint("InflateParams")
        @Override
        public View createView(Context context) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_home_top_banner, null);
            imageView = (ImageView) v.findViewById(R.id.ivPicture);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return v;
        }

        @Override
        public void UpdateUI(final Context context, final int position, final String data) {
            ImageLoader.getInstance().displayImage(data, imageView, DataMgr.options);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


    private LoadMoreListener mLoadMoreListener;

    public void setLoadMoreListener(LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
    }

    public interface LoadMoreListener {
        public void checkLoadMoreData();
    }
}
