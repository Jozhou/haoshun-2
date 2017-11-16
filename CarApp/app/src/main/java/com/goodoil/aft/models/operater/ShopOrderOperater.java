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

public class ShopOrderOperater extends BaseOperater {

    public ShopOrderOperater(Context context) {
        super(context);
    }

    public void setParams(int shop_id) {
        try {
            paramsObj.put("user_id", Account.get().user_id);
            paramsObj.put("shop_id", shop_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/user/bespeak";
    }

    @Override
    protected void onParser(JSONObject jo) {
    }

    @Override
    protected void onParser(JSONArray response) {

    }

}
