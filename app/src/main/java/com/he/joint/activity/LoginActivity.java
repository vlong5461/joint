package com.he.joint.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.he.joint.R;
import com.he.joint.api.BaseApi;
import com.he.joint.api.LoginApi;
import com.he.joint.api.ThirdLoginApi;
import com.he.joint.bean.UserInfoBean;
import com.he.joint.common.Consts;
import com.he.joint.common.UIHelper;
import com.he.joint.mgr.DataMgr;
import com.he.joint.mgr.LoginMgr;
import com.he.joint.utils.CommonUtils;
import com.he.joint.utils.ToastUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends BaseActivity implements OnClickListener, BaseApi.APIListener {

	private EditText etUserName, etPassword;
	private Button btnLogin;
	private TextView tvForgetPassword;
	private ImageView ivQQLogin, ivWeiboLogin, ivWeixinLogin;
	private RelativeLayout rlQQLogin,rlWeiboLogin,rlWeixinLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initTopBar(getString(R.string.login), getString(R.string.register));
		initView();
		ShareSDK.initSDK(mContext);
	}

	private void initView() {
		etUserName = findView(R.id.etUserName);
		etPassword = findView(R.id.etPassword);
		btnLogin = findView(R.id.btnLogin);
		tvForgetPassword = findView(R.id.tvForgetPassword);
		ivQQLogin = findView(R.id.ivQQLogin);
		ivWeiboLogin = findView(R.id.ivWeiboLogin);
		ivWeixinLogin = findView(R.id.ivWeixinLogin);
		rlQQLogin = findView(R.id.rlQQLogin);
		rlWeiboLogin= findView(R.id.rlWeiboLogin);
		rlWeixinLogin= findView(R.id.rlWeixinLogin);

		btnLogin.setOnClickListener(this);
		tvForgetPassword.setOnClickListener(this);
		ivQQLogin.setOnClickListener(this);
		ivWeiboLogin.setOnClickListener(this);
		ivWeixinLogin.setOnClickListener(this);
		rlQQLogin.setOnClickListener(this);
		rlWeiboLogin.setOnClickListener(this);
		rlWeixinLogin.setOnClickListener(this);
	}

	@Override
	protected void topNavBarRightClicked() {
		super.topNavBarRightClicked();
		// UIHelper.startActivity(mContext, RegisterActivity.class);
		UIHelper.startActivityForResult(mContext, RegisterActivity.class, null, Consts.ActivityRequestCode.Register);
	}

	@Override
	public void onClick(View view) {
		if(CommonUtils.isFastDoubleClick()){
			return;
		}
		if (view.getId() == btnLogin.getId()) {
			LoginAction();
		} else if (view.getId() == tvForgetPassword.getId()) {
			// UIHelper.startActivity(mContext, ForgetPasswordActivity.class);
			UIHelper.startActivityForResult(mContext, ForgetPasswordActivity.class, null,
					Consts.ActivityRequestCode.ModifyPassword);
		} else if (view.getId() == ivQQLogin.getId()||view.getId() ==rlQQLogin.getId()) {
			qqLogin();
		} else if (view.getId() == ivWeiboLogin.getId()||view.getId() ==rlWeiboLogin.getId()) {
			weiboLogin();
		} else if (view.getId() == ivWeixinLogin.getId()||view.getId() ==rlWeixinLogin.getId()) {
			weixinLogin();
		}
	}

	private void LoginAction() {
		String mobile = etUserName.getText().toString().trim();
		String password = etPassword.getText().toString();

		if (mobile.length() == 0) {
			ToastUtils.show(mContext, getString(R.string.register_input_phone_number));
			return;
		}
		if (password.length() == 0) {
			ToastUtils.show(mContext, getString(R.string.register_input_password_2));
			return;
		}

		showWaitingDialog(mContext);
		LoginApi api = new LoginApi();
		api.apiListener = this;
		api.sendRequest(mobile, password);

	}

	@Override
	public void onApiResponse(BaseApi api) {
//		dismissWaitingDialog();
		if (api.responseCode == Consts.ResponseCode.Success) {
			if (api.contentCode == Consts.ContentCode.Success) {
				UserInfoBean bean = (UserInfoBean) api.responseData;
				if(bean!=null){
					LoginMgr.shareInstance().setUserInfo(bean);
					ToastUtils.show(mContext, "登录成功");
					finish();
				}
			} else {
				ToastUtils.show(mContext, api.contentMesage);
				dismissWaitingDialog();
			}
		} else {
			ToastUtils.show(mContext, api.responseMessage);
			dismissWaitingDialog();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			if (requestCode == Consts.ActivityRequestCode.Register) {
				finish();
			} else if (requestCode == Consts.ActivityRequestCode.ModifyPassword) {

			}
		}
	}

	private void weiboLogin() {
		showWaitingDialog(mContext);
		Platform weibo = ShareSDK.getPlatform(mContext, SinaWeibo.NAME);
		weibo.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform platform, int arg1, Throwable arg2) {
//				ToastUtils.show(mContext, "登录失败");
				Log.e("sharesdk","登录失败:" + arg2.getMessage());
				platform.removeAccount(true);
				ToastUtils.show(mContext, "登录失败，请重新登录" + arg2.getMessage(), 2000);
				dismissWaitingDialog();
			}

			@Override
			public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
//				ToastUtils.show(mContext, "登录成功");
				if (action == Platform.ACTION_USER_INFOR) {
		            PlatformDb platDB = platform.getDb();
		            final String token = platDB.getToken();  
		            String sex = platDB.getUserGender();
		            if (sex.equals("m")) {
						sex = "1";
					} else if (sex.equals("f")) {
						sex = "2";
					} else {
						sex = "0";
					}
		            final String head = platDB.getUserIcon();
		            final String uid = platDB.getUserId();
		            final String name = platDB.getUserName();
		            final String addr = res.get("province").toString() + res.get("city").toString();
		            final String sex_ = sex;
		            
		            Looper.prepare();
		            new Handler().post(new Runnable() {
						
						@Override
						public void run() {
							thirdLogin(uid, token, name, head, sex_, addr, 1);
						}
					});
		            Looper.loop();
		        }
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
//				ToastUtils.show(mContext, "取消登录");
				dismissWaitingDialog();
			}
		});
		weibo.showUser(null);// 执行登录，登录后在回调里面获取用户资料
		// weibo.showUser(“3189087725”);//获取账号为“3189087725”的资料
		weibo.removeAccount(true);
	}
	private void openWeixin(){
		Intent intent = new Intent();
		ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(cmp);
		startActivity(intent);
	}
	private void weixinLogin() {
		showWaitingDialog(mContext);
		Platform weixin = ShareSDK.getPlatform(mContext, Wechat.NAME);
		if(!weixin.isClientValid()){
			ToastUtils.show(mContext, "请您安装微信后再登录",2000);
			dismissWaitingDialog();
			return;
		}
		weixin.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
//				ToastUtils.show(mContext, "登录失败");
				Log.e("sharesdk","登录失败:" + arg2.getMessage());
				ToastUtils.show(mContext, "登录失败，请重新登录" + arg2.getMessage(), 2000);
				openWeixin();
				dismissWaitingDialog();
			}

			@Override
			public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
//				ToastUtils.show(mContext, "登录成功");
				if (action == Platform.ACTION_USER_INFOR) {
		            PlatformDb platDB = platform.getDb();
		            final String token = platDB.getToken();  
		            String sex = platDB.getUserGender();
		            if (sex.equals("m")) {
						sex = "1";
					} else if (sex.equals("f")) {
						sex = "2";
					} else {
						sex = "0";
					}
		            final String head = platDB.getUserIcon();
		            final String uid = platDB.getUserId();
		            final String name = platDB.getUserName();
		            final String addr = res.get("province").toString() + res.get("city").toString();
		            final String sex_ = sex;
		            
		            Looper.prepare();
		            new Handler().post(new Runnable() {
						
						@Override
						public void run() {
							thirdLogin(uid, token, name, head, sex_, addr, 5);
						}
					});
		            Looper.loop();
		        }
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
//				ToastUtils.show(mContext, "取消登录");
				dismissWaitingDialog();
			}
		});
		weixin.showUser(null);
		weixin.removeAccount(true);
	}
	
	private void qqLogin() {
		showWaitingDialog(mContext);
		Platform qq = ShareSDK.getPlatform(mContext, QQ.NAME);
		qq.setPlatformActionListener(new PlatformActionListener() {

			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
				Log.e("sharesdk","登录失败:" + arg2.getMessage());
				ToastUtils.show(mContext, "登录失败，请重新登录" + arg2.getMessage(), 2000);
				dismissWaitingDialog();
			}

			@Override
			public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
				
				if (action == Platform.ACTION_USER_INFOR) {
		            PlatformDb platDB = platform.getDb();
		            final String token = platDB.getToken();  
		            String sex = platDB.getUserGender();
		            if (sex.equals("m")) {
						sex = "1";
					} else if (sex.equals("f")) {
						sex = "2";
					} else {
						sex = "0";
					}
		            final String head = platDB.getUserIcon();
		            final String uid = platDB.getUserId();
		            final String name = platDB.getUserName();
		            final String addr = res.get("province").toString() + res.get("city").toString();
		            final String sex_ = sex;
		            
		            Looper.prepare();
		            new Handler().post(new Runnable() {
						
						@Override
						public void run() {
							thirdLogin(uid, token, name, head, sex_, addr, 2);
						}
					});
		            Looper.loop();
		        }
			}
			
			@Override
			public void onCancel(Platform arg0, int arg1) {
				dismissWaitingDialog();
			}
		});
		qq.showUser(null);
		qq.removeAccount(true);
	}
	
	private void thirdLogin(String uid, String token, String name, String head, String sex, String addr, int from) {
		showWaitingDialog(mContext);
		ThirdLoginApi api =new ThirdLoginApi();
		api.apiListener = new BaseApi.APIListener() {
			
			@Override
			public void onApiResponse(BaseApi api) {
//				dismissWaitingDialog();
				if (api.responseCode == Consts.ResponseCode.Success) {
					if (api.contentCode == Consts.ContentCode.Success) {
						UserInfoBean bean = (UserInfoBean) api.responseData;
						if(bean!=null){
							LoginMgr.shareInstance().setUserInfo(bean);
							ToastUtils.show(mContext, "登录成功");
							DataMgr.needScrollToPintForTravelList = true;
							DataMgr.isRefreshTravel=true;
							finish();
						}
					} else {
						dismissWaitingDialog();
						ToastUtils.show(mContext, api.contentMesage);
					}
				} else {
					dismissWaitingDialog();
					ToastUtils.show(mContext, api.responseMessage);
				}
			}
		};
		//来源 from ：1-新浪微博，2-QQ登录，3-腾讯微博，5-微信登录
		api.sendRequest(uid, token, name, head, sex, addr, from);
	}

}
