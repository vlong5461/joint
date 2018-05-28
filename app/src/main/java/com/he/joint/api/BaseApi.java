package com.he.joint.api;

import android.annotation.SuppressLint;
import android.util.Base64;

import com.google.gson.Gson;
import com.he.joint.utils.CollectionUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class BaseApi {

	public int requestCode;
	public int requestType;
	public int responseCode;
	public String responseMessage;
	public int contentCode;
	public String contentMesage;
	public long contentTime;
	public APIListener apiListener;
	public Object responseData;

	private AsyncHttpClient client;

	public interface APIListener {
		public void onApiResponse(BaseApi api);
	}

	protected AsyncHttpClient getClient() {
		if (client == null) {
			client = new AsyncHttpClient();
			client.setConnectTimeout(30 * 1000);
			client.setResponseTimeout(30 * 1000);
			client.setMaxRetriesAndTimeout(1, 30*1000);
//			String userAge= String.format("%s/%s (Linux; Android %s; %s Build/%s)", "travel", DataMgr.versionName, Build.VERSION.RELEASE, Build.MANUFACTURER, Build.ID);
//			client.addHeader("User-Agent", userAge);
		}
		return client;
	}

	protected void onApiFailure(int statusCode, Header[] headers, Throwable throwable) {
		responseCode = statusCode;
		String msg = "";
//		if (msg == null || msg.length() == 0) {
//			msg = "未知错误";
//		}
//		if (!DataMgr.isNetworkAvailable()) {
//			msg = "网络连接失败";
//		}
		if (statusCode == 500 || throwable == null) {
			msg = "服务器异常";
		} else {
			msg = "网络连接失败";
		}

		responseMessage = msg;
		if (apiListener != null) {
			apiListener.onApiResponse(this);
		}
	}

	protected void onApiSuccess(int statusCode, Header[] headers, JSONObject response) {
		responseCode = statusCode;
		responseMessage = "Success";
		contentCode = response.optInt("code", 0);
		contentMesage = response.optString("msg", "未知错误");
		contentTime = response.optLong("time",0);
	}

	protected void postStringData(String url,RequestParams params){
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  byte[] responseBody) {
				try {
					String result=new String(responseBody);
					JSONObject response = new JSONObject(result);
					onApiSuccess(statusCode, headers, response);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] responseBody, Throwable error) {
				// 打印错误信息
				onApiFailure(statusCode, headers, error);
			}
		});
	}

	protected void getStringData(String url){
		client.get(url,new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
								  byte[] responseBody) {
				try {
					String result=new String(responseBody);
					JSONObject response = new JSONObject(result);
					onApiSuccess(statusCode, headers, response);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
								  byte[] responseBody, Throwable error) {
				// 打印错误信息
				onApiFailure(statusCode, headers, error);
			}
		});
	}

	protected JSONObject getDataJson(JSONObject dataJson) throws JSONException {
		String result="";
		result = dataJson.getString("data");
//		result=new String(Base64.decode(result.toString().getBytes(), Base64.DEFAULT));
		dataJson = new JSONObject(result);
		return dataJson;
	}
	protected JSONArray getDataJsonArray(JSONObject dataJson) throws JSONException {
		String result="";
		result = dataJson.getString("data");
//		result=new String(Base64.decode(result.toString().getBytes(), Base64.DEFAULT));
		JSONArray array = new JSONArray(result);
		return array;
	}
	
	private static Gson gson = new Gson();

	public <T> T parseJsonObject(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	protected <T> List<T> parseJsonArray(JSONArray array, Class<T> t) throws JSONException {
		if (CollectionUtils.isNotEmpty(array)) {
			List<T> list = new ArrayList<T>();
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				if (json != null) {
					T bean = parseJsonObject(json.toString(), t);
					if (bean != null) {
						list.add(bean);
					}
				}
			}
			return list;
		} else {
			return null;
		}
	}

}
