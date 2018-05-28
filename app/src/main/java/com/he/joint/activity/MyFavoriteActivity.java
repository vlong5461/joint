package com.he.joint.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.he.joint.R;
import com.he.joint.adapter.MyFavoriteAdapter;
import com.he.joint.bean.ProductBriefBean;
import com.he.joint.common.Consts;
import com.he.joint.common.UIHelper;
import com.he.joint.slidelistview.SlideListView;
import com.he.joint.utils.CollectionUtils;
import com.third.view.pullablelistview.PullToRefreshLayout;
import com.third.view.pullablelistview.PullToRefreshLayout.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class MyFavoriteActivity extends BaseActivity {
	
	private SlideListView listview;
	private MyFavoriteAdapter adapter;
	private List<ProductBriefBean> favoriteList;
	private PullToRefreshLayout pullLayout;
	private int pager = 1;
	private boolean haveLoadAllData = false;
	private boolean isLoadingMore = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_favorite);
		initTopBar(getString(R.string.main_my_favorite));
		initView();
		requestData(Consts.GetDataType.Normal);
	}
	
	private void initView() {
		favoriteList = new ArrayList<ProductBriefBean>();
		listview = findView(R.id.listview);
		adapter = new MyFavoriteAdapter(this);
		listview.setAdapter(adapter);
		setData();
		listview.setSlideMode(SlideListView.SlideMode.RIGHT);
		((SlideListView)listview).setCanPullDown(true);
		pullLayout = findView(R.id.pullLayout);

		listview.setCanLoadMore(false);
		listview.addLoadView();
		listview.setFinishedViewVisibility(View.GONE);
		listview.setRunningViewVisibility(View.GONE);
		listview.setLoadViewBackground(R.color.transparent);

		adapter.listener = new MyFavoriteAdapter.MyFavoriteAdapterListener() {
			
			@Override
			public void onDelete(int index) {
				deleteFavorite(favoriteList.get(index));
			}
		};
		
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ProductBriefBean bean = favoriteList.get(arg2);
				Bundle b = new Bundle();
				b.putString(Consts.Travel_ID, bean.TravelID);
				b.putString(Consts.Top_Title, bean.Title);
				UIHelper.startActivity(mContext, PublicWebViewActivity.class, b);
			}
		});
		pullLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				listview.setLoadViewBackground(R.color.transparent);
				requestData(Consts.GetDataType.Refresh);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//				requestData(GetDataType.LoadMore);
			}
		});

		listview.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				if (!isLoadingMore && !haveLoadAllData && listview != null &&
						listview.getLastVisiblePosition() == CollectionUtils.getSize(favoriteList) - 1) {
					requestData(Consts.GetDataType.LoadMore);
				}

			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}
		});
	}
	private void setData(){
		for(int i=0;i<5;i++) {
			ProductBriefBean productBriefBean = new ProductBriefBean();
			productBriefBean.Title = "测试";
			favoriteList.add(productBriefBean);
		}
		if(CollectionUtils.isNotEmpty(favoriteList)) {
			adapter.setDataList(favoriteList);
			adapter.notifyDataSetChanged();
		}
	}
	private void deleteFavorite(final ProductBriefBean bean) {
//		showWaitingDialog(mContext);
//		MyFavoriteSetApi api = new MyFavoriteSetApi();
//		api.apiListener = new APIListener() {
//
//			@Override
//			public void onApiResponse(BaseApi api) {
//				dismissWaitingDialog();
//				if (api.responseCode == ResponseCode.Success) {
//					if (api.contentCode == ContentCode.Success) {
//						if(api.responseData!=null){
//							if (api.responseData.equals("success")) {
//								favoriteList.remove(bean);
//								adapter.setDataList(favoriteList);
//								adapter.notifyDataSetChanged();
//								if(CollectionUtils.isEmpty(favoriteList)){
//									View emptyView = findViewById(R.id.empty);
//									emptyView.setVisibility(View.VISIBLE);
//									pullLayout.setVisibility(View.GONE);
//								}
//							}
//						}
//					} else {
//						ToastUtils.show(mContext, api.contentMesage);
//					}
//				} else {
//					ToastUtils.show(mContext, api.responseMessage);
//				}
//			}
//		};
//		api.sendRequest(String.valueOf(LoginMgr.shareInstance().getUserId()), bean.TravelID, "0");
	}
	
	private void requestData(final int getDataType) {
//		if (getDataType == Consts.GetDataType.Normal) {
//			showWaitingDialog(mContext);
//		} else if (getDataType == Consts.GetDataType.LoadMore) {
//			listview.setLoadViewBackground(R.color.gray_f8f8f8);
//			listview.setFinishedViewVisibility(View.GONE);
//			listview.setRunningViewVisibility(View.VISIBLE);
//		}
//		isLoadingMore = true;
		
//		MyFavoriteApi api = new MyFavoriteApi();
//		api.apiListener = new APIListener() {
//
//			@SuppressWarnings("unchecked")
//			@Override
//			public void onApiResponse(BaseApi api) {
//				if (listview != null) {
//					listview.setFinishedViewVisibility(View.GONE);
//					listview.setRunningViewVisibility(View.GONE);
//				}
//				int status;
//				dismissWaitingDialog();
//				if (api.responseCode == ResponseCode.Success) {
//					if (api.contentCode == ContentCode.Success) {
//						List<ProductBriefBean> list = (List<ProductBriefBean>) api.responseData;
//						status = PullToRefreshLayout.SUCCEED;
//						if (getDataType == GetDataType.LoadMore) {
//							if (CollectionUtils.isNotEmpty(list)) {
//								favoriteList.addAll(list);
//								adapter.setDataList(favoriteList);
//								adapter.notifyDataSetChanged();
//								pager++;
//								haveLoadAllData = false;
//							} else {
////								ToastUtils.show(mContext, "没有更多数据了");
//								status = PullToRefreshLayout.DOWN_BOTTOM;
//								listview.setFinishedViewVisibility(View.VISIBLE);
//								listview.setRunningViewVisibility(View.GONE);
//								haveLoadAllData = true;
//
//							}
//						} else {
//							favoriteList.clear();
//							if (CollectionUtils.isNotEmpty(list)) {
//								favoriteList.addAll(list);
//							}
//							adapter.setDataList(favoriteList);
//							adapter.notifyDataSetChanged();
//							pager = 1;
//							haveLoadAllData = false;
//						}
//
//						if(CollectionUtils.isEmpty(favoriteList)){
//							View emptyView = findViewById(R.id.empty);
//							emptyView.setVisibility(View.VISIBLE);
//							pullLayout.setVisibility(View.GONE);
//						} else {
//							View emptyView = findViewById(R.id.empty);
//							emptyView.setVisibility(View.GONE);
//							pullLayout.setVisibility(View.VISIBLE);
//						}
//
//
//					} else {
//						ToastUtils.show(mContext, api.contentMesage);
//						status = PullToRefreshLayout.FAIL;
//					}
//				} else {
//					ToastUtils.show(mContext, api.responseMessage);
//					status = PullToRefreshLayout.FAIL;
//				}
//
////				if (getDataType == GetDataType.LoadMore) {
////					pullLayout.loadmoreFinish(status);
////				} else
//				if (getDataType == GetDataType.Refresh) {
//					pullLayout.refreshFinish(status);
//				}
//
//				isLoadingMore = false;
//			}
//		};
//		int index;
//		if (getDataType == GetDataType.LoadMore) {
//			index = pager + 1;
//		} else {
//			index = 1;
//		}
//		api.sendRequest(String.valueOf(LoginMgr.shareInstance().getUserId()), index+"");
	}

}
