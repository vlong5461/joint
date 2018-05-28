package com.he.joint.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.he.joint.R;
import com.he.joint.api.BaseApi;
import com.he.joint.api.RegisterApi;
import com.he.joint.api.SendCheckCodeApi;
import com.he.joint.bean.UserInfoBean;
import com.he.joint.common.Consts;
import com.he.joint.mgr.DataMgr;
import com.he.joint.mgr.LoginMgr;
import com.he.joint.utils.StringUtils;
import com.he.joint.utils.ToastUtils;
import com.he.joint.utils.WidgetUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener, BaseApi.APIListener {
	
	private EditText etUserName, etVerificationCode, etPassword;
	private Button btnGetVerificationCode, btnRegister;
	private ImageView ivShowPassword;
	private MyCount mc; 
	private boolean showPassword = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initTopBar(getString(R.string.register));
		initView();
		setPasswordStatus();
	}
	
	private void initView() {
		etUserName = findView(R.id.etUserName);
		etVerificationCode = findView(R.id.etVerificationCode);
		etPassword = findView(R.id.etPassword);
		btnGetVerificationCode = findView(R.id.btnGetVerificationCode);
		btnRegister = findView(R.id.btnRegister);
		ivShowPassword = findView(R.id.ivShowPassword);
		
		btnRegister.setOnClickListener(this);
		ivShowPassword.setOnClickListener(this);
		btnGetVerificationCode.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == btnRegister.getId()) {
			registerAction();
		} else if (view.getId() == btnGetVerificationCode.getId()) {
			sendCheckCode();
		} else if (view.getId() == ivShowPassword.getId()) {
			showPassword = !showPassword;
			setPasswordStatus();
		}
	}
	
	private void setPasswordStatus() {
		if (showPassword) {
			ivShowPassword.setImageResource(R.drawable.denglu_xianshi_2);
			etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		} else {
			ivShowPassword.setImageResource(R.drawable.denglu_xianshi);
			etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		}
	}
	
	private void registerAction() {
		View v = this.getCurrentFocus();
		if (v != null) {
			WidgetUtils.hideSoftInput(mContext, v);
		}
		
		String mobile = etUserName.getText().toString().trim();
		String validate = etVerificationCode.getText().toString().trim();
		String password = etPassword.getText().toString();
		
		if (mobile.length() == 0) {
			ToastUtils.show(mContext, getString(R.string.register_input_phone_number), 1000);
			return;
		}
		if (validate.length() == 0) {
			ToastUtils.show(mContext, getString(R.string.register_input_phone_code), 1000);
			return;
		}
		if (password.length() == 0) {
			ToastUtils.show(mContext, getString(R.string.register_input_password), 1000);
			return;
		} else {
			if (password.length() < 6) {
				ToastUtils.show(mContext, "密码长度需大于等于6", 1000);
				return;
			} else if (password.length() > 20) {
				ToastUtils.show(mContext, "密码长度需小于等于20", 1000);
				return;
			}
		}
		showWaitingDialog(mContext);
		RegisterApi api = new RegisterApi();
		api.requestCode = Consts.RequestCode.Register;
		api.sendRequest(validate, mobile, password);//, "0");
		api.apiListener = this;
	}
	
	private void sendCheckCode() {
		String mobile = etUserName.getText().toString().trim();
		if (mobile.length() == 0) {
			ToastUtils.show(mContext, getString(R.string.register_input_phone_number));
			return;
		}
		showWaitingDialog(mContext);
		SendCheckCodeApi api = new SendCheckCodeApi();
		api.requestCode = Consts.RequestCode.Get_Verification_Code;
		api.sendRequest(mobile, Consts.SendCheckCode.FindPassword);
		api.apiListener = this;
	}

	@Override
	public void onApiResponse(BaseApi api) {
		dismissWaitingDialog();
		if (api.responseCode == Consts.ResponseCode.Success) {
			if (api.contentCode == Consts.ContentCode.Success) {
				if (api.requestCode == Consts.RequestCode.Register) {
					UserInfoBean bean = (UserInfoBean) api.responseData;
					if(bean!=null){
//						PreferenceHelper.saveToSharedPreferences(Consts.USER_SIG, bean.Sig);
						LoginMgr.shareInstance().setUserInfo(bean);
						ToastUtils.show(mContext, "注册成功");
						setResult(RESULT_OK);
						finish();
					}
				} else if (api.requestCode == Consts.RequestCode.Get_Verification_Code) {
					String data = (String)api.responseData;
					if (StringUtils.isNotEmpty(data) && data.equals("success")) {
						ToastUtils.show(mContext, "获取验证码成功");
						mc = new MyCount(60000, 1000);  
					    mc.start(); 
					} else {
						ToastUtils.show(mContext, "获取验证码失败");
					}
				}
			} else {
				ToastUtils.show(mContext, api.contentMesage);
			}
		} else {
			ToastUtils.show(mContext, api.responseMessage);
		}
	}

	/*定义一个倒计时的内部类*/  
    class MyCount extends CountDownTimer {     
        public MyCount(long millisInFuture, long countDownInterval) {     
            super(millisInFuture, countDownInterval);     
        }     
        @Override     
        public void onFinish() {     
        	btnGetVerificationCode.setEnabled(true); 
        	btnGetVerificationCode.setBackgroundResource(R.drawable.shape_red_point);
        	btnGetVerificationCode.setText(getResources().getString(R.string.register_get_verification_code));
        }     
        @Override     
        public void onTick(long millisUntilFinished) {
        	btnGetVerificationCode.setEnabled(false);
        	btnGetVerificationCode.setBackgroundResource(R.drawable.shape_gray_btn);
        	btnGetVerificationCode.setText((millisUntilFinished / 1000)+"");
        }    
    }
}
