package com.he.joint.api;

import com.he.joint.bean.UploadBean;
import com.he.joint.common.Algorithm;
import com.he.joint.common.Consts;
import com.he.joint.common.URLHelper;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/6/7.
 */

public class UploadApi  extends BaseApi {

    public void sendRequest(String user_id, String user_token, String file, String upload_type) {
        long time =System.currentTimeMillis();
        RequestParams params = new RequestParams();
        params.put("user_id",user_id);
        params.put("user_token",user_token);
        try {
            params.put("file", new File(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        params.put("upload_type",upload_type);
        params.put("token", Algorithm.getTokenMD5(time));
        params.put("timestamp",time);
        postStringData(URLHelper.uploadURL,params);
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
                UploadBean bean = parseJsonObject(userinfoJson.toString(), UploadBean.class);
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
