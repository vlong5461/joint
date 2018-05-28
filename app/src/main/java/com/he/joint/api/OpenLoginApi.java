package com.he.joint.api;

import com.he.joint.bean.BaseBean;
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

public class OpenLoginApi extends BaseApi {

    public void sendRequest(String openid, String nickname,String sex, String headimgurl) {
        long time =System.currentTimeMillis();
        RequestParams params = new RequestParams();
        params.put("openid",openid);
        params.put("nickname",nickname);
        params.put("sex",sex);
        params.put("headimgurl",headimgurl);
        params.put("token", Algorithm.getTokenMD5(time));
        params.put("timestamp",time);
        postStringData(URLHelper.OpenLoginURL,params);
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
                JSONObject userinfoJson = dataJson.getJSONObject("user_info");
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