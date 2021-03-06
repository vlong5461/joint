package com.he.joint.api;

import com.he.joint.bean.CategorySearchBean;
import com.he.joint.bean.DocumentSearchBean;
import com.he.joint.common.Algorithm;
import com.he.joint.common.Consts;
import com.he.joint.common.URLHelper;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/6/7.
 */

public class DocumentSearchApi extends BaseApi {

    public void sendRequest(String keywords) {
        long time = System.currentTimeMillis();
        String url = URLHelper.documentSearchURL +
                "?keywords=" + keywords + "&token=" + Algorithm.getTokenMD5(time) + "&timestamp=" + time;
        getStringData(url);
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
                DocumentSearchBean bean = parseJsonObject(dataJson.toString(), DocumentSearchBean.class);
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