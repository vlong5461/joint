package com.he.joint.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;

import com.he.joint.R;
import com.he.joint.adapter.FeedbackGridViewAdapter;
import com.he.joint.common.UIHelper;
import com.he.joint.dialog.ActionSheetDialog;
import com.he.joint.photo.Bimp;
import com.he.joint.utils.FileUtils;
import com.he.joint.utils.ToastUtils;
import com.third.grant.PermissionsManager;
import com.third.grant.PermissionsResultAction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MoreFeedbackActivity extends BaseActivity {
	
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	  
	private EditText etContent;
	private GridView gvPhoto;
	private FeedbackGridViewAdapter adapter;
	
	private List<String> imageList;
	private File currPicFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_feedback);
		initTopBar("提问","提交");
		initView();

		PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity) mContext,
				new String[] { Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
					@Override
					public void onGranted() {

					}

					@Override
					public void onDenied(String permission) {

					}
				});
	}

	private void initView() {
		etContent = findView(R.id.etContent);
		gvPhoto = findView(R.id.gvPhoto);
		
		adapter = new FeedbackGridViewAdapter(mContext);
		gvPhoto.setAdapter(adapter);
		adapter.listener = new FeedbackGridViewAdapter.FeedbackGridViewAdapterListener() {
			
			@Override
			public void onDelete(int index) {
				if (imageList != null){
					if(imageList.size() > index) {
						String filePath = imageList.get(index);
						imageList.remove(index);
						adapter.setDataList(imageList);
						adapter.notifyDataSetChanged();
					}
				}
			}
			
			@Override
			public void onAdd() {
				showDialog();
			}
		};
		
		imageList = new ArrayList<String>();
		currPicFile = new File(FileUtils.getDiskCacheDir(mContext).getPath() + "/pic.png");
		
//		etContent.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
		etContent.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode != RESULT_CANCELED) {
			if(requestCode == CAMERA_REQUEST_CODE){
				Uri selectedImage = Uri.fromFile(currPicFile);;
                String filePath = FileUtils.FeedbackImagePath + System.currentTimeMillis();
                try {
                    FileUtils.copyFile(selectedImage, filePath, mContext);
                    imageList.add(filePath);
                    adapter.setDataList(imageList);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}else if(requestCode == IMAGE_REQUEST_CODE){
//				Uri selectedImage = intent.getData();
//                String filePath = FileUtils.FeedbackImagePath + System.currentTimeMillis();
//                try {
//                    FileUtils.copyFile(selectedImage, filePath, mContext);
//                    imageList.add(filePath);
//                    adapter.setDataList(imageList);
//                    adapter.notifyDataSetChanged();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
				for(int j = 0; j< Bimp.tempSelectBitmap.size(); j++){
					imageList.add(Bimp.tempSelectBitmap.get(j).getImagePath());
				}
				adapter.setDataList(imageList);
				adapter.notifyDataSetChanged();
			}
		}
	}

	protected void topNavBarRightClicked() {
		String text = etContent.getText().toString().trim();
		if (text.length() == 0) {
			ToastUtils.show(mContext, "请输入反馈信息");
			return;
		}
		if (text.length() < 3) {
			ToastUtils.show(mContext, "反馈内容长度必须大于3！");
			return;
		}
		showWaitingDialog(mContext);
//		MoreFeedbackApi api = new MoreFeedbackApi();
//		api.apiListener = new APIListener() {
//
//			@Override
//			public void onApiResponse(BaseApi api) {
//				dismissWaitingDialog();
//				if (api.responseCode == ResponseCode.Success) {
//					if (api.contentCode == ContentCode.Success) {
//						if (api.responseMessage.equals("Success")) {
//							ToastUtils.show(mContext, "内容提交成功！感谢您的反馈");
//							finish();
//						} else {
//							ToastUtils.show(mContext, "内容提交失败");
//						}
//					} else {
//						ToastUtils.show(mContext, api.contentMesage);
//					}
//				} else {
//					ToastUtils.show(mContext, api.responseMessage);
//				}
//			}
//		};
//		api.sendRequest(String.valueOf(LoginMgr.shareInstance().getUserId()), text, imageList);
	}

	private void showDialog() {
		if(imageList.size()>8){
    		ToastUtils.show(mContext, "最多只能上传9张图片");
    		return;
		}
	    new ActionSheetDialog(mContext).builder().setCancelable(true)
	        .setCanceledOnTouchOutside(true)
	        .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
	          @Override
	          public void onClick(int which) {  

				  boolean hasPermiss = PermissionsManager.getInstance().hasPermission( mContext,
						  Manifest.permission.CAMERA);
				  if (!hasPermiss) {
					  ToastUtils.show(mContext, "你已拒绝使用相机，如果需要使用相机，请打开应用的相机权限（设置->应用->任游->权限）", 2000);
				  } else {
					  Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					  intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currPicFile));
					  startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
				  }
	          }
	        }).addSheetItem("从相册中选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
	          @Override
	          public void onClick(int which) { 
//	            Intent intentFromGallery = new Intent();
//	            intentFromGallery.setType("image/*"); // 设置文件类型
//	            intentFromGallery.setAction(Intent.ACTION_PICK);
//	            intentFromGallery
//	                .setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//	            startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
				  boolean hasPermission = PermissionsManager.getInstance().hasPermission((Activity)mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
				  if(!hasPermission){
					  ToastUtils.show(mContext, "请打开应用的存储权限（设置->应用->任游->权限）");
				  }else {
					  Bundle b = new Bundle();
					  b.putInt("photoNum", imageList.size());
					  UIHelper.startActivityForResult(mContext, AlbumActivity.class, b, IMAGE_REQUEST_CODE);
				  }
	          }
	        }).show();
	  }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FileUtils.deleteAllFiles(new File(FileUtils.FeedbackImagePath));
	}
	
	
}
