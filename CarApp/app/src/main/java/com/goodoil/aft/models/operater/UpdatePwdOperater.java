package com.goodoil.aft.models.operater;

import android.content.Context;

import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/30.
 */

public class UpdatePwdOperater extends BaseOperater {

    public UpdatePwdOperater(Context context) {
        super(context);
    }

    public void setParams(String tel, String pwd) {
        try {
            paramsObj.put("tel", tel);
            paramsObj.put("pwd", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/user/forgetpwd";
    }

    @Override
    protected void onParser(JSONObject jsonObject) {
    }

    @Override
    protected void onParser(JSONArray response) {

    }

}
