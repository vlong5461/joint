package com.he.joint.api;

import com.he.joint.bean.BaseBean;
import com.he.joint.common.Algorithm;
import com.he.joint.common.Consts;
import com.he.joint.common.URLHelper;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/6/7.
 */

public class ResetPwdApi extends BaseApi {

    public void sendRequest(String mobile, String password,String repassword) {
        long time =System.currentTimeMillis();
        RequestParams params = new RequestParams();
        params.put("mobile",mobile);
        params.put("password",password);
        params.put("repassword",repassword);
        params.put("token", Algorithm.getTokenMD5(time));
        params.put("timestamp",time);
        postStringData(URLHelper.resetPwdURL,params);
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
