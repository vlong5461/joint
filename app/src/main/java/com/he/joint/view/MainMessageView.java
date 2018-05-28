package com.he.joint.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.bean.HomeAreaBean;
import com.he.joint.mgr.DataMgr;
import com.he.joint.utils.CollectionUtils;
import com.third.view.pullablelistview.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luo_haogui on 2017/6/6.
 */

public class MainMessageView extends LinearLayout {

    private Context mContext;
    private List<HomeAreaBean> areaList;

    public MainMessageView(Context context) {
        super(context);
        mContext = context;
    }

    public MainMessageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public MainMessageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    private LinearLayout llTopAreaContainer;
    private HorizontalScrollView hScrollView;
    private ViewPager viewPager;
    private HomeViewPagerAdapter adapter;
    private PullToRefreshLayout refresh_view;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (!isInEditMode()) {
            initView();
        }
    }

    public void setCountryArea() {
        int index;
        index = 0;

        if (viewPager != null) {
            viewPager.setCurrentItem(index, false);
        }

        if (hScrollView != null && llTopAreaContainer != null) {
            int width = llTopAreaContainer.getWidth();
            int average_width = width / 3;
            if (index > 2) {
                hScrollView.smoothScrollTo((index - 2) * average_width, 0);
            } else {
                hScrollView.smoothScrollTo(0, 0);
            }

        }

    }

    private void initView() {
        llTopAreaContainer = (LinearLayout) findViewById(R.id.llTopAreaContainer);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                boolean needUpdateUI = true;
                int oldPosition = 0;
                for (int j = 0; j < areaList.size(); j++) {
                    HomeAreaBean bean = areaList.get(j);
                    if (bean.t_selected) {
                        if (i == j) {
                            needUpdateUI = false;
                            break;
                        } else {
                            bean.t_selected = false;
                        }
                        oldPosition = j;
                    }
                }
                if (needUpdateUI) {
                    areaList.get(i).t_selected = true;
                    View v = llTopAreaContainer.getChildAt(oldPosition);
                    if (v != null) {
                        Rect rect = new Rect();
                        v.getGlobalVisibleRect(rect);
                        if (rect.right > DataMgr.screenWidth - DataMgr.dip2px(100)) {
                            hScrollView.smoothScrollBy(rect.width(), 0);
                        } else if (rect.left < DataMgr.dip2px(100)) {
                            hScrollView.smoothScrollBy(-rect.width(), 0);
                        }
                    }
                    setTopAreaView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

            }
        });
        setData();
        setTopAreaView(); //luo
        updateViewPager(1);
    }

    public int getViewPagerIndex() {
        return viewPager.getCurrentItem();
    }

    public void setData() {
        areaList = new ArrayList<>();
        HomeAreaBean bean1 = new HomeAreaBean();
        bean1.AreaName = "报告";
        bean1.t_selected = false;
        areaList.add(bean1);
        HomeAreaBean bean2 = new HomeAreaBean();
        bean2.AreaName = "推荐";
        bean2.t_selected = true;
        areaList.add(bean2);
        HomeAreaBean bean3 = new HomeAreaBean();
        bean3.AreaName = "资料";
        bean3.t_selected = false;
        areaList.add(bean3);
    }

    public void updateViewPagerData() {
        if (adapter == null) {
            adapter = new HomeViewPagerAdapter();
            viewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public void resetViewPager() {
        adapter = new HomeViewPagerAdapter();
        viewPager.setAdapter(adapter);
    }

    public void resetViewPager(final int index) {
        adapter = new HomeViewPagerAdapter();
        viewPager.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(index, true);
            }
        }, 100);
    }

    private void updateViewPager(int index) {
        if (adapter == null) {
            adapter = new HomeViewPagerAdapter();
            viewPager.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();

        viewPager.setCurrentItem(index, false);
    }

    @SuppressLint("InflateParams")
    public synchronized void setTopAreaView() {

        llTopAreaContainer.removeAllViews();
        if (CollectionUtils.isEmpty(areaList)) {
            return;
        }

        for (int i = 0; i < areaList.size(); i++) {
            HomeAreaBean bean = areaList.get(i);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            final View item = inflater.inflate(R.layout.item_home_top_area, null);
            RelativeLayout rlHomeTop = (RelativeLayout) item.findViewById(R.id.rlHomeTop);

            TextView tvName = (TextView) item.findViewById(R.id.tvName);
            View bottomLine = item.findViewById(R.id.bottomLine);

            tvName.setText(bean.AreaName);
            if (bean.t_selected) {
                bottomLine.setVisibility(View.VISIBLE);
                tvName.setTextColor(mContext.getResources().getColor(R.color.orange_red));
                tvName.setTextSize(14);
                tvName.getPaint().setFakeBoldText(false);
            } else {
                bottomLine.setVisibility(View.INVISIBLE);
                tvName.setTextColor(mContext.getResources().getColor(R.color.black_666666));
                tvName.setTextSize(14);
                tvName.getPaint().setFakeBoldText(false);
            }

            int textWidth = (int) (tvName.getPaint().measureText(bean.AreaName) + 0.5);
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(textWidth, DataMgr.dip2px(2));
            p.rightMargin = DataMgr.dip2px(25);
            p.leftMargin = DataMgr.dip2px(25);
            p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            bottomLine.setLayoutParams(p);

            RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams(textWidth + p.leftMargin + p.rightMargin, RelativeLayout.LayoutParams.MATCH_PARENT);
            rlHomeTop.setLayoutParams(p2);

            final int index = i;
            item.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    boolean needUpdateUI = true;
                    for (int j = 0; j < areaList.size(); j++) {
                        HomeAreaBean bean = areaList.get(j);
                        if (bean.t_selected) {
                            if (index == j) {
                                needUpdateUI = false;
                                break;
                            } else {
                                bean.t_selected = false;
                            }
                        }
                    }
                    if (needUpdateUI) {
                        areaList.get(index).t_selected = true;

                        Rect rect = new Rect();
                        arg0.getGlobalVisibleRect(rect);
                        if (rect.right > DataMgr.screenWidth - DataMgr.dip2px(100)) {
                            hScrollView.smoothScrollBy(rect.width(), 0);
                        } else if (rect.left < DataMgr.dip2px(100)) {
                            hScrollView.smoothScrollBy(-rect.width(), 0);
                        }
                        setTopAreaView();
                        if (viewPager != null) {
                            viewPager.setCurrentItem(index, false);
                        }

                    }

                }
            });

            llTopAreaContainer.addView(item);
        }
    }

    public class HomeViewPagerAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            MainMessageViewItem v = (MainMessageViewItem) LayoutInflater.from(mContext).inflate(R.layout.view_main_viewpaper_item, MainMessageView.this, false);
            v.initData(position, areaList.get(position), false);
            container.addView(v);
            return v;
        }


        @Override
        public int getCount() {
            if (areaList == null) {
                return 0;
            }
            return areaList.size();
        }
    }


    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            if (areaList == null && DataMgr.isNetworkAvailable(mContext)) {

            }
        } else {

        }
    }
}
