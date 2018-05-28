package com.he.joint.api;
import com.he.joint.common.Consts;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class SendCheckCodeApi extends BaseApi {
	
	public void sendRequest(String mobile, String from) {

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
				String result = dataJson.optString("result", "");
				responseData = result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if (apiListener != null) {
			apiListener.onApiResponse(this);
		}
	}

}
