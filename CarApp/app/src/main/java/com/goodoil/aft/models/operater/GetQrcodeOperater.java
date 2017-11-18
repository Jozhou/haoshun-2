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

public class GetQrcodeOperater extends BaseOperater {

    private String pic = "";

    public GetQrcodeOperater(Context context) {
        super(context);
    }

    @Override
    protected String getUrlAction() {
        return "/share/shareqrc";
    }

    @Override
    protected void onParser(JSONObject jo) {
        try {
            JSONObject jsonObject = jo.getJSONObject("content");
            pic = jsonObject.getString("pic");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

    public String getPic() {
        return this.pic;
    }

}
