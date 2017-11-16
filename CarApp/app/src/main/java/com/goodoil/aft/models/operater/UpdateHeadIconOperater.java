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

public class UpdateHeadIconOperater extends BaseOperater {

    private String imageurl;

    public UpdateHeadIconOperater(Context context) {
        super(context);
    }

    public void setParams(String headimage) {
        try {
            paramsObj.put("user_id", Account.get().user_id);
            paramsObj.put("headimage", headimage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/user/updateheadimage";
    }

    @Override
    protected void onParser(JSONObject jsonObject) {
        try {
            JSONObject response = jsonObject.getJSONObject("content");
            imageurl = response.getString("imageurl");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

    public String getImageurl() {
        return this.imageurl;
    }

}
