package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.common.data.Account;
import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/10/9.
 */

public class NewsCollectOperater extends BaseOperater {

    private int collectetimes;

    public NewsCollectOperater(Context context) {
        super(context);
    }

    public void setParams(int news_id) {
        try {
            paramsObj.put("user_id", Account.get().user_id);
            paramsObj.put("news_id", news_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/page/collecte";
    }

    @Override
    protected void onParser(JSONObject jo) {
        try {
            JSONObject obj = jo.getJSONObject("content");
            collectetimes = obj.optInt("collectetimes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

    public int getCollectetimes() {
        return collectetimes;
    }
}
