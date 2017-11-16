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

public class UpdateTelnumOperater extends BaseOperater {

    public UpdateTelnumOperater(Context context) {
        super(context);
    }

    public void setParams(String tel) {
        try {
            paramsObj.put("user_id", Account.get().user_id);
            paramsObj.put("tel", tel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/user/updatetelnum";
    }

    @Override
    protected void onParser(JSONObject jsonObject) {
    }

    @Override
    protected void onParser(JSONArray response) {

    }

}
