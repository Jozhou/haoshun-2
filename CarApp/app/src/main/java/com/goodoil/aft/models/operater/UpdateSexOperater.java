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

public class UpdateSexOperater extends BaseOperater {

    public UpdateSexOperater(Context context) {
        super(context);
    }

    public void setParams(int sex) {
        try {
            paramsObj.put("user_id", Account.get().user_id);
            paramsObj.put("sex", sex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/user/updatesex";
    }

    @Override
    protected void onParser(JSONObject jsonObject) {
    }

    @Override
    protected void onParser(JSONArray response) {

    }

}
