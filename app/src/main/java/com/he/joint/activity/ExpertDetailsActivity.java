package com.he.joint.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.he.joint.R;
import com.he.joint.adapter.MyFavoriteAdapter;
import com.he.joint.bean.ProductBriefBean;
import com.he.joint.common.Consts;
import com.he.joint.common.UIHelper;
import com.he.joint.slidelistview.SlideListView;
import com.he.joint.utils.CollectionUtils;
import com.third.view.pullablelistview.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class ExpertDetailsActivity  extends BaseActivity {

    private ListView listview;
    private PullToRefreshLayout pullLayout;
    private MyFavoriteAdapter adapter;
    private List<ProductBriefBean> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_details);
        initView();
    }

    private void initView() {
        favoriteList = new ArrayList<ProductBriefBean>();
        pullLayout = findView(R.id.refresh_view);
        listview = findView(R.id.listview);
        adapter = new MyFavoriteAdapter(this);
        listview.setAdapter(adapter);
        setData();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                ProductBriefBean bean = favoriteList.get(arg2);
                Bundle b = new Bundle();
                b.putString(Consts.Travel_ID, bean.TravelID);
                b.putString(Consts.Top_Title, bean.Title);
                UIHelper.startActivity(mContext, PublicWebViewActivity.class, b);
            }
        });
        pullLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//				requestData(GetDataType.LoadMore);
            }
        });

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (!isLoadingMore && !haveLoadAllData && listview != null &&
//                        listview.getLastVisiblePosition() == CollectionUtils.getSize(favoriteList) - 1) {
//                    requestData(Consts.GetDataType.LoadMore);
//                }

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
    }

    private void setData() {
        for (int i = 0; i < 5; i++) {
            ProductBriefBean productBriefBean = new ProductBriefBean();
            productBriefBean.Title = "测试";
            favoriteList.add(productBriefBean);
        }
        if (CollectionUtils.isNotEmpty(favoriteList)) {
            adapter.setDataList(favoriteList);
            adapter.notifyDataSetChanged();
        }
    }
}
