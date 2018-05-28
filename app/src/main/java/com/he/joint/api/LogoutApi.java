package com.he.joint.api;

import com.he.joint.bean.UserInfoBean;
import com.he.joint.common.Algorithm;
import com.he.joint.common.Consts;
import com.he.joint.common.URLHelper;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/6/7.
 */

public class LogoutApi extends BaseApi {

    public void sendRequest(String user_token, String user_id) {
        long time =System.currentTimeMillis();
        RequestParams params = new RequestParams();
        params.put("user_token",user_token);
        params.put("user_id",user_id);
        params.put("token", Algorithm.getTokenMD5(time));
        params.put("timestamp",time);
        postStringData(URLHelper.logoutURL,params);
    }

    @Override
    protected void onApiSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onApiSuccess(statusCode, headers, response);
        parseData(response);
    }

    protected void parseData(JSONObject response) {
        if (contentCode == Consts.ContentCode.Success) {
            
        }

        if (apiListener != null) {
            apiListener.onApiResponse(this);
        }
    }

}