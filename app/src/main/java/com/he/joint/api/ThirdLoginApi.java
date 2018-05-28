package com.he.joint.api;

import com.he.joint.bean.UserInfoBean;
import com.he.joint.common.Consts;
import com.he.joint.common.PreferenceHelper;
import com.he.joint.mgr.DataMgr;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ThirdLoginApi extends BaseApi {
	
	public void sendRequest(String uid, String token, String name, String head, String sex, String addr, int from) {

	}
	
	@Override
	protected void onApiSuccess(int statusCode, Header[] headers, JSONObject response) {
		super.onApiSuccess(statusCode, headers, response);
		
		parseData(response);
	}
	
	protected void parseData(JSONObject response) {
		if (contentCode == Consts.ContentCode.Success) {
			try {
				JSONObject dataJson = getDataJson(response);
				JSONObject userinfoJson = dataJson.getJSONObject("userinfo");
				UserInfoBean bean = parseJsonObject(userinfoJson.toString(), UserInfoBean.class);
				responseData = bean;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if (apiListener != null) {
			apiListener.onApiResponse(this);
		}
	}

}
