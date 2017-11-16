package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.common.data.Account;
import com.goodoil.aft.models.entry.NameValueEntry;
import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */

public class GetFrameNumOperater extends BaseOperater {

    private List<NameValueEntry> mData;

    public GetFrameNumOperater(Context context) {
        super(context);
    }

    public void setParams(String vin) {
        try {
            paramsObj.put("user_id", Account.get().user_id);
            paramsObj.put("vin", vin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/vehicle/searchvin";
    }

    @Override
    protected void onParser(JSONObject jsonObject) {
        mData = new ArrayList<>();
        try {
            JSONArray array = jsonObject.getJSONObject("content").getJSONArray("vinList").getJSONObject(0).getJSONArray("SubVin");
            for(int i = 0; i < array.length(); i ++) {
                NameValueEntry entry = new NameValueEntry();
                JSONObject obj = array.getJSONObject(i);
                entry.name = obj.getString("key");
                entry.value = obj.getString("value");
                mData.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {
    }

    public List<NameValueEntry> getData() {
        return this.mData;
    }

}
