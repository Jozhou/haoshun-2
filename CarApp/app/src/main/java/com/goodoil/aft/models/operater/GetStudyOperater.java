package com.goodoil.aft.models.operater;

import android.content.Context;

import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/30.
 */

public class GetStudyOperater extends BaseOperater {

    private String imgurl = "";

    public GetStudyOperater(Context context) {
        super(context);
    }

    @Override
    protected String getUrlAction() {
        return "/share/learningol";
    }

    @Override
    protected void onParser(JSONObject jo) {
        try {
            JSONObject jsonObject = jo.getJSONObject("content");
            imgurl = jsonObject.getString("imgurl");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

    public String getImgurl() {
        return this.imgurl;
    }

}
