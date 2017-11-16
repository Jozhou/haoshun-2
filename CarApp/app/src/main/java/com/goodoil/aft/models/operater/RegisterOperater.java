package com.goodoil.aft.models.operater;

import android.content.Context;

import com.android.minivolley.Request;
import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/30.
 */

public class RegisterOperater extends BaseOperater {

    public RegisterOperater(Context context) {
        super(context);
    }

    public void setParams(String type, String tel, String pwd, String carcode) {
        try {
            paramsObj.put("type", type);
            paramsObj.put("tel", tel);
            paramsObj.put("pwd", pwd);
            paramsObj.put("carcode", carcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/user/register";
    }

    @Override
    protected void onParser(JSONObject jsonObject) {
        try {
            JSONObject response = jsonObject.getJSONObject("content");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

    @Override
    protected int getHttpMethod() {
        return Request.Method.POST;
    }
}
