package com.he.joint.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.activity.ExpertInforActivity;
import com.he.joint.activity.LoginActivity;
import com.he.joint.activity.MoreFeedbackActivity;
import com.he.joint.activity.MyAttentionActivity;
import com.he.joint.activity.MyFavoriteActivity;
import com.he.joint.activity.MyInfoActivity;
import com.he.joint.activity.MyQuestionActivity;
import com.he.joint.activity.RecoderAcitivity;
import com.he.joint.activity.SettingActivity;
import com.he.joint.bean.MyCenterBean;
import com.he.joint.bean.UserInfoBean;
import com.he.joint.common.Cast;
import com.he.joint.common.UIHelper;
import com.he.joint.mgr.DataMgr;
import com.he.joint.mgr.LoginMgr;
import com.he.joint.utils.CommonUtils;
import com.he.joint.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class MainMyView extends LinearLayout implements View.OnClickListener {

	public MainMyView(Context context) {
		super(context);
	}

	public MainMyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MainMyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private LinearLayout llExpertInformation, llMyAnswer, llMyQuestion,llFavorite, llViewRecord, llSetting, llFeedback, llAbout;
	private ImageView ivUserHead;
	private TextView tvUserName;
	private TextView tvAnswerCount,tvQuestionCount;
	private MyCenterBean myCenterBean;
	private String msgcontent="";
	private int messagecount=0;
	private boolean isShow=false;
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (!isInEditMode()) {
			initView();
			checkLoginStatus();
		}

	}

	private void initView() {
		llExpertInformation = findView(R.id.llExpertInformation);
		llMyAnswer = findView(R.id.llMyAnswer);
		llMyQuestion= findView(R.id.llMyQuestion);
		llFavorite = findView(R.id.llFavorite);
		llViewRecord = findView(R.id.llViewRecord);
		llSetting = findView(R.id.llSetting);
		llFeedback = findView(R.id.llFeedback);
		llAbout = findView(R.id.llAbout);
		ivUserHead = findView(R.id.ivUserHead);
		tvUserName = findView(R.id.tvUserName);
		tvAnswerCount = findView(R.id.tvAnswerCount);

		ivUserHead.setOnClickListener(this);
		tvUserName.setOnClickListener(this);
		llExpertInformation.setOnClickListener(this);
		llMyAnswer.setOnClickListener(this);
		llMyQuestion.setOnClickListener(this);
		llFavorite.setOnClickListener(this);
		llViewRecord.setOnClickListener(this);
		llSetting.setOnClickListener(this);
		llFeedback.setOnClickListener(this);
		llAbout.setOnClickListener(this);

	}

	private <T> T findView(int id) {
		return Cast.cast(findViewById(id));
	}

	@Override
	public void onClick(View view) {
		if(CommonUtils.isFastDoubleClick()){
			return;
		}
		if (view.getId() == ivUserHead.getId() || view.getId() == tvUserName.getId()) {
//			UIHelper.startActivity(getContext(), LoginActivity.class);
			UIHelper.startActivity(getContext(), MyInfoActivity.class);
		}else if (view.getId() == llExpertInformation.getId()) {
            UIHelper.startActivity(getContext(), ExpertInforActivity.class);
		} else if (view.getId() == llMyAnswer.getId()) {
			UIHelper.startActivity(getContext(), MyQuestionActivity.class);
		} else if (view.getId() == llMyQuestion.getId()){
			UIHelper.startActivity(getContext(), MyQuestionActivity.class);
		}else if (view.getId() == llFavorite.getId()) {
			UIHelper.startActivity(getContext(), MyFavoriteActivity.class);
		} else if (view.getId() == llViewRecord.getId()) {
			UIHelper.startActivity(getContext(), MyAttentionActivity.class);  //RecoderAcitivity
		} else if (view.getId() == llSetting.getId()) {
			UIHelper.startActivity(getContext(), SettingActivity.class);
		} else if (view.getId() == llFeedback.getId()) {
//			if (!LoginMgr.shareInstance().getLoginStatus()){
//				UIHelper.startActivity(getContext(), LoginActivity.class);
//				return;
//			}
			UIHelper.startActivity(getContext(), MoreFeedbackActivity.class);
		} else if (view.getId() == llAbout.getId()) {

		}
	}
	
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (visibility == View.VISIBLE) {
			checkLoginStatus();
		}
	}
	
	private void checkLoginStatus() {
		if (LoginMgr.shareInstance().getLoginStatus()) {
			UserInfoBean bean = LoginMgr.shareInstance().getUserInfo();
			if (bean != null) {
				if(StringUtils.isNotEmpty(bean.nickname)) {
					tvUserName.setText(bean.nickname);
				}
				if (StringUtils.isNotEmpty(bean.avatar_url)) {
					ImageLoader.getInstance().displayImage(bean.avatar_url, ivUserHead,
							DataMgr.options, new ImageLoadingListener() {
								@Override
								public void onLoadingStarted(String s, View view) {

								}

								@Override
								public void onLoadingFailed(String s, View view, FailReason failReason) {
									ivUserHead.setImageResource(R.drawable.wode_weidenglu);
								}

								@Override
								public void onLoadingComplete(String s, View view, Bitmap bitmap) {

								}

								@Override
								public void onLoadingCancelled(String s, View view) {

								}
							});
				}
			}
		}
		getData();
		isShow=false;
		getMessageData();
	}
	
	private void setDataView(){
		if(myCenterBean!=null){

			if(!myCenterBean.usercount.Cert_Count.isEmpty()){
				tvAnswerCount.setText(myCenterBean.usercount.Cert_Count);
				if(myCenterBean.usercount.Cert_Count.equals("0")){
					tvAnswerCount.setVisibility(View.GONE);
				}else{
					tvAnswerCount.setVisibility(View.VISIBLE);
				}
			}
			
		}
	}
	private void setMessageDataView(){

	}
	private void getData() {

	}
	private void getMessageData() {

	}
}
