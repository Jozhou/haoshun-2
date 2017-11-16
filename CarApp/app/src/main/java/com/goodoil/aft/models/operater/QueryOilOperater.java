package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.common.data.Account;
import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/30.
 */

public class QueryOilOperater extends BaseOperater {

    private String url;

    public QueryOilOperater(Context context) {
        super(context);
    }

    public void setParams(String carcode) {
        try {
            paramsObj.put("user_id", Account.get().user_id);
            paramsObj.put("carcode", carcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/vehicle/changeoil";
    }

    @Override
    protected void onParser(JSONObject jsonObject) {
        try {
            JSONObject obj = jsonObject.getJSONObject("content");
            url = obj.optString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {
    }

    public String getUrl() {
        return url;
    }
}
