package com.he.joint.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.he.joint.common.Algorithm;
import com.he.joint.common.Consts;
import com.he.joint.common.URLHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.util.Base64;
import cz.msebera.android.httpclient.Header;

public class MoreFeedbackApi extends BaseApi {

	public void sendRequest(String userid, String memo, List<String> fileList) {
		JSONObject json = new JSONObject();
		try {
			json.put("userid", userid);
			json.put("memo", memo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestParams params = new RequestParams();
		params.put("action", "save_my_feedback");  
		params.put("data", Base64.encodeToString(json.toString().getBytes(),Base64.DEFAULT));  
		params.put("sign", "");
		for (int i = 0; i < fileList.size(); i++) {
		   try {
		      params.put("images[" + i + "]", new File(fileList.get(i)));
		   } catch (FileNotFoundException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		   }
		}
		
		getClient().post(URLHelper.BaseURL,params,new JsonHttpResponseHandler() {
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				onApiFailure(statusCode, headers, throwable);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				onApiSuccess(statusCode, headers, response);
			}
		});
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
				String result = dataJson.optString("result");
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
