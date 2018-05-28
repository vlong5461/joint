package com.he.joint.api;

import com.he.joint.bean.MaterialListBean;
import com.he.joint.bean.QuestionListsBean;
import com.he.joint.common.Algorithm;
import com.he.joint.common.Consts;
import com.he.joint.common.URLHelper;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/6/7.
 */

public class QuestionListsApi  extends BaseApi {

    public void sendRequest(String page,String sort) {
        long time =System.currentTimeMillis();
        String url= URLHelper.questionlistsURL+
                "?page="+page+"&sort=" +sort
                +"&token=" + Algorithm.getTokenMD5(time)+"&timestamp=" + time;
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
                QuestionListsBean bean = parseJsonObject(dataJson.toString(), QuestionListsBean.class);
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
