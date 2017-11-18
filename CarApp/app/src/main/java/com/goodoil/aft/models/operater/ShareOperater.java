package com.goodoil.aft.models.operater;

import android.content.Context;

import com.corelibrary.models.http.BaseOperater;
import com.goodoil.aft.models.entry.ShareEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/30.
 */

public class ShareOperater extends BaseOperater {

    public ShareOperater(Context context) {
        super(context);
    }

    @Override
    protected String getUrlAction() {
        return "/share/share";
    }

    @Override
    protected void onParser(JSONObject jo) {
        try {
            JSONObject jsonObject = jo.getJSONObject("content");
            ShareEntry.getInstance().title = jsonObject.getString("title");
            ShareEntry.getInstance().content = jsonObject.getString("content");
            ShareEntry.getInstance().pic = jsonObject.getString("pic");
            ShareEntry.getInstance().url = jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

}
