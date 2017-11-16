package com.goodoil.aft.models.operater;

import android.content.Context;

import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/30.
 */

public class GetAdOperater extends BaseOperater {

    private List<String> adList;

    public GetAdOperater(Context context) {
        super(context);
    }

    @Override
    protected String getUrlAction() {
        return "/page/homepage";
    }

    @Override
    protected void onParser(JSONObject jo) {
        adList = new ArrayList<>();
        try {
            JSONArray array = jo.getJSONObject("content").getJSONArray("imageurl");
            for (int i = 0; i < array.length(); i ++) {
                JSONObject jsonObject = array.getJSONObject(i);
                String imageurl = jsonObject.getString("imageurl");
                adList.add(imageurl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

    public List<String> getAdList() {
        return this.adList;
    }

}
