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

public class UserEditApi extends BaseApi {

    public void sendRequest(String sex, String birthday,String nickname,
                            String cover_id, String signature,String avatar,
                            String occupation, String industry,String user_token,
                            String user_id, String mobile) {
        long time =System.currentTimeMillis();
        RequestParams params = new RequestParams();
        params.put("sex",sex);
        params.put("birthday",birthday);
        params.put("nickname",nickname);
        params.put("cover_id",cover_id);
        params.put("signature",signature);
        params.put("avatar",avatar);
        params.put("occupation",occupation);
        params.put("industry",industry);
        params.put("user_token",user_token);
        params.put("user_id",user_id);
        params.put("mobile",mobile);
        params.put("token", Algorithm.getTokenMD5(time));
        params.put("timestamp",time);
        postStringData(URLHelper.usereditURL,params);
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
