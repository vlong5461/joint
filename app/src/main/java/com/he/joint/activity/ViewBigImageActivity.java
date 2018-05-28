package com.he.joint.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.utils.CollectionUtils;
import com.he.joint.utils.StringUtils;

import java.util.List;

import touchgallery.GalleryWidget.GalleryViewPager;
import touchgallery.GalleryWidget.UrlPagerAdapter;

public class ViewBigImageActivity extends BaseActivity {
	
//	@SuppressWarnings("rawtypes")
//	private ConvenientBanner myBanner;
	private TextView tvTitle, tvPageIndex;
	private RelativeLayout rlContainer;
	
	private List<String> dataList;
	private String title;
	private int currentIndex;
	private List<String> imageDescList;

	

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_view_big_image);
		if (getIntent() != null && getIntent().getExtras() != null) {
			dataList = (List<String>) getIntent().getExtras().getSerializable("data");
			imageDescList = (List<String>) getIntent().getExtras().getSerializable("image_desc");
			title = getIntent().getExtras().getString("title", "");
			currentIndex = getIntent().getExtras().getInt("index", 0);
		}
		initView();
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void initView() {
//		myBanner = findView(R.id.recommendBanner);
		tvTitle = findView(R.id.tvTitle);
		tvPageIndex = findView(R.id.tvPageIndex);
		rlContainer = findView(R.id.rlContainer);

		
		UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this, dataList);
		GalleryViewPager mViewPager = (GalleryViewPager)findViewById(R.id.viewer);
		//mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOnItemClickListener(new GalleryViewPager.OnItemClickListener() {

			@Override
			public void onItemClicked(View view, int position) {
				finish();
			}
		});
		tvPageIndex.setText((currentIndex+1)+"/"+dataList.size());
		mViewPager.setCurrentItem(currentIndex);
		setImageDesc();
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				tvPageIndex.setText((arg0+1)+"/"+dataList.size());
				currentIndex = arg0;
				setImageDesc();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
//		myBanner.pageListener = new PageChangedListener() {
//
//			@Override
//			public void onChanged(int index) {
//				tvPageIndex.setText((index + 1) + "/"+dataList.size());
//			}
//		};
//		tvPageIndex.setText((currentIndex+1)+"/"+dataList.size());
//		myBanner.currentIndex = currentIndex;
//		setBanner(myBanner);
		
		rlContainer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		setImageDesc();
	}
	
	private void setImageDesc() {
		String desc;
		if (CollectionUtils.isNotEmpty(imageDescList) && currentIndex < imageDescList.size()) {
			desc = imageDescList.get(currentIndex);
			if (StringUtils.isEmpty(desc)) {
				desc = title;
			}
		} else {
			desc = title;
		}
		
		if (desc == null) {
			desc = "";
		}
		tvTitle.setText(desc);
	}
}
