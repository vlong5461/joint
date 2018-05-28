package com.he.joint.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.he.joint.R;
import com.he.joint.adapter.MainHomeDataAdapter;
import com.he.joint.adapter.MainHomeRecommendAdapter;
import com.he.joint.adapter.MainHomeReportAdapter;
import com.he.joint.bean.HomeAreaBean;
import com.he.joint.bean.HomeRecommendBean;
import com.he.joint.bean.ProductBriefBean;
import com.he.joint.common.Consts;
import com.third.view.pullablelistview.PullToRefreshLayout;
import com.third.view.pullablelistview.PullableExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luo_haogui on 2017/6/6.
 */

public class MainMessageViewItem extends PullToRefreshLayout {
    private Context mContext;
    public MainMessageViewItem(Context context) {
        super(context);
        mContext = context;
    }

    public MainMessageViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public MainMessageViewItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    private PullToRefreshLayout pullLayout;
    private ExpandableListView expandListView;
    private MainHomeRecommendAdapter recommendAdapter;
    private MainHomeReportAdapter reportAdapter;
    private MainHomeDataAdapter dataAdapter;
    private int pageIndex = 0;
    private int viewPagerPosition;
    private HomeAreaBean currentArea;
    private boolean viewDestroy = false;
    private boolean haveLoadAllData = false;
    private boolean isLoadingMore = false;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (!isInEditMode()) {
            initView();
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onDetachedFromWindow() {

        viewDestroy = true;
        pullLayout = null;
//        adapter = null;
        expandListView = null;
//        dataBean = null;
        currentArea = null;

        super.onDetachedFromWindow();
    }

    public void initData(int viewPagerPosition, HomeAreaBean currentArea, boolean canLoadCache) {

        this.viewPagerPosition = viewPagerPosition;
        this.currentArea = currentArea;

        switch (viewPagerPosition){
            case 0:
                setReportViewData();
                break;
            case 1:
                setRecommendViewData();
                break;
            case 2:
                setDataView();
                break;
        }
        getRegionalData(Consts.GetDataType.Normal);
    }

    private void initView() {
        pullLayout = (PullToRefreshLayout) this.findViewById(R.id.refresh_view);
        expandListView = (ExpandableListView) this.findViewById(R.id.expandListView);

        ((PullableExpandableListView)expandListView).setCanLoadMore(false);
        ((PullableExpandableListView)expandListView).addLoadView();
        ((PullableExpandableListView)expandListView).setFinishedViewVisibility(View.GONE);
        ((PullableExpandableListView)expandListView).setRunningViewVisibility(View.GONE);
        ((PullableExpandableListView)expandListView).setLoadViewBackground(R.color.transparent);

        pullLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                getAreaListData(Consts.GetDataType.Refresh);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//                scrollDirectionChanged(Consts.ScrollDirection.Up);
//                getRegionalData(Consts.GetDataType.LoadMore);
            }
        });

        expandListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mListViewFirstItem = 0;
            private int mScreenY = 0;
            boolean isScrollToUp = false;

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {

                notifyListViewScrolled();
            }

        });
    }

    private void notifyListViewScrolled() {

    }

    private void setReportViewData() {
        if (reportAdapter == null) {
            reportAdapter = new MainHomeReportAdapter(getContext());
            expandListView.setAdapter(reportAdapter);
        } else {
            reportAdapter.notifyDataSetChanged();
        }
        List<String> list=new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add("强制性认证产品信息");
        }
        reportAdapter.setData(list);
        reportAdapter.notifyDataSetChanged();
    }
    private void setRecommendViewData() {
        if (recommendAdapter == null) {
            recommendAdapter = new MainHomeRecommendAdapter(getContext(),null);
            expandListView.setAdapter(recommendAdapter);
        } else {
            recommendAdapter.notifyDataSetChanged();
        }
        HomeRecommendBean bean=new HomeRecommendBean();
        bean.imageList=new ArrayList<>();
        bean.imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497168208&di=3bcb5fa14034858cf1395a556611803d&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1011%2F05241G14R6%2F1F524114R6-4.jpg");
        bean.imageList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496573426961&di=dbcebc89a92d2101c9c8a8cfd57a61e6&imgtype=0&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Fmxy%2F12%2F10%2F15%2F10.jpg");
        bean.imageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=897795441,1342958893&fm=11&gp=0.jpg");
        bean.dataList = new ArrayList<>();
        for(int i=0;i<5;i++) {
            ProductBriefBean productBriefBean = new ProductBriefBean();
            productBriefBean.Title = "测试";
            bean.dataList.add(productBriefBean);
        }
        recommendAdapter.setData(bean);
        recommendAdapter.notifyDataSetChanged();

        recommendAdapter.setLoadMoreListener(new MainHomeRecommendAdapter.LoadMoreListener() {
            @Override
            public void checkLoadMoreData() {
                getRegionalData(Consts.GetDataType.LoadMore);
            }
        });
    }
    private void setDataView() {
        if (dataAdapter == null) {
            dataAdapter = new MainHomeDataAdapter(getContext());
            expandListView.setAdapter(dataAdapter);
        } else {
            dataAdapter.notifyDataSetChanged();
        }
        List<String> list=new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add("测试");
        }
        dataAdapter.setData(list);
        dataAdapter.notifyDataSetChanged();
    }
    private void getAreaListData(final int getDataType) {
        if(viewPagerPosition==1) {
            ((PullableExpandableListView) expandListView).setFinishedViewVisibility(View.VISIBLE);
            ((PullableExpandableListView) expandListView).setRunningViewVisibility(View.GONE);
        }else{
            ((PullableExpandableListView) expandListView).setFinishedViewVisibility(View.GONE);
            ((PullableExpandableListView) expandListView).setRunningViewVisibility(View.GONE);
        }
        pullLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    private void getRegionalData(final int getDataType) {
        if(viewPagerPosition==1) {
            if (getDataType == Consts.GetDataType.LoadMore) {
                ((PullableExpandableListView) expandListView).setFinishedViewVisibility(View.GONE);
                ((PullableExpandableListView) expandListView).setRunningViewVisibility(View.VISIBLE);
                ((PullableExpandableListView) expandListView).setLoadViewBackground(R.color.gray_f8f8f8);
            }
            ((PullableExpandableListView) expandListView).setFinishedViewVisibility(View.VISIBLE);
            ((PullableExpandableListView) expandListView).setRunningViewVisibility(View.GONE);
        }else{
            ((PullableExpandableListView) expandListView).setFinishedViewVisibility(View.GONE);
            ((PullableExpandableListView) expandListView).setRunningViewVisibility(View.GONE);
        }
        pullLayout.refreshFinish(PullToRefreshLayout.DONE);
    }

    private void loadCacheData() {

    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {

        }
    }
}
