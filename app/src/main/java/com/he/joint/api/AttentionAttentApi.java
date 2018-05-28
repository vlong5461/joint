package com.he.joint.api;

import com.he.joint.bean.FavoriteListsBean;
import com.he.joint.common.Algorithm;
import com.he.joint.common.Consts;
import com.he.joint.common.URLHelper;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2017/6/7.
 */

public class AttentionAttentApi extends BaseApi {

    public void sendRequest(String expert_uid,String user_token,String user_id) {
        long time =System.currentTimeMillis();
        String url= URLHelper.attentionattentURL+
                "?user_token="+user_token+"&user_id=" +user_id+"&expert_uid=" +expert_uid
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

        }

        if (apiListener != null) {
            apiListener.onApiResponse(this);
        }
    }

}
