package com.he.joint.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.he.joint.R;
import com.he.joint.adapter.AlbumGridViewAdapter;
import com.he.joint.photo.AlbumHelper;
import com.he.joint.photo.Bimp;
import com.he.joint.photo.ImageBucket;
import com.he.joint.photo.ImageItem;
import com.he.joint.photo.PublicWay;
import com.he.joint.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是进入相册显示所有图片的界面
 * 
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:47:15
 */
public class AlbumActivity extends Activity {
	//显示手机里的所有图片的列表控件
	private GridView gridView;
	//当手机里没有图片时，提示用户没有图片的控件
	private TextView tv;
	//gridView的adapter
	private AlbumGridViewAdapter gridImageAdapter;
	// 返回按钮
	private TextView back,finished;
	private Intent intent;
	private Context mContext;
	private ArrayList<ImageItem> dataList;
	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	public static Bitmap bitmap;
	private int photoNum;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plugin_camera_album);
		PublicWay.activityList.add(this);
		mContext = this;
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.plugin_camera_no_pictures);
        init();
		initListener();
		//这个函数主要用来控制预览和完成按钮的状态
		isShowOkBt();
	}
	
	// 完成按钮的监听
	private class AlbumSendListener implements OnClickListener {
		public void onClick(View v) {
			setResult(1, intent);
			finish();
		}

	}

	// 取消按钮的监听
	private class CancelListener implements OnClickListener {
		public void onClick(View v) {
			setResult(0, intent);
			finish();
		}
	}

	

	// 初始化，给一些对象赋值
	private void init() {
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		
		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();
		for(int i = 0; i<contentList.size(); i++){
			dataList.addAll( contentList.get(i).imageList );
		}
		
		back = (TextView) findViewById(R.id.back);
		finished = (TextView) findViewById(R.id.finished);
		back.setOnClickListener(new CancelListener());
		finished.setOnClickListener(new AlbumSendListener());
		
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		photoNum = bundle.getInt("photoNum");
		gridView = (GridView) findViewById(R.id.myGrid);
		Bimp.tempSelectBitmap.clear();
		gridImageAdapter = new AlbumGridViewAdapter(this,dataList,
				Bimp.tempSelectBitmap);
		gridView.setAdapter(gridImageAdapter);
		tv = (TextView) findViewById(R.id.myText);
		gridView.setEmptyView(tv);
	}

	private void initListener() {

		gridImageAdapter
				.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(final ToggleButton toggleButton,
							int position, boolean isChecked,Button chooseBt) {
						if (Bimp.tempSelectBitmap.size() >= (PublicWay.num-photoNum)) {
							toggleButton.setChecked(false);
							chooseBt.setVisibility(View.GONE);
							if (!removeOneData(dataList.get(position))) {
								ToastUtils.show(mContext, "最多只能上传9张图片");
							}
							return;
						}
						if (isChecked) {
							chooseBt.setVisibility(View.VISIBLE);
							Bimp.tempSelectBitmap.add(dataList.get(position));
						} else {
							Bimp.tempSelectBitmap.remove(dataList.get(position));
							chooseBt.setVisibility(View.GONE);		
						}
						isShowOkBt();
					}
				});
	}

	private boolean removeOneData(ImageItem imageItem) {
			if (Bimp.tempSelectBitmap.contains(imageItem)) {
				Bimp.tempSelectBitmap.remove(imageItem);
				return true;
			}
		return false;
	}
	
	public void isShowOkBt() {
		if (Bimp.tempSelectBitmap.size() > 0) {

		} else {

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(0, intent);
			finish();
		}
		return false;

	}
@Override
protected void onRestart() {
	isShowOkBt();
	super.onRestart();
}
}
