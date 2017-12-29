package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.common.data.Account;
import com.goodoil.aft.models.entry.NewsEntry;
import com.corelibrary.models.entry.ArrayEntry;
import com.corelibrary.models.http.BaseArrayOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/10/9.
 */

public class GetMyNewsOperater extends BaseArrayOperater<NewsEntry> {

    public GetMyNewsOperater(Context context) {
        super(context);
        setParams();
    }

    public void setParams() {
        try {
            paramsObj.put("user_id", Account.get().user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayEntry<NewsEntry> parseJsonObject(JSONObject jo) {
        ArrayEntry<NewsEntry> arrayEntry = new ArrayEntry<>();
        try {
            JSONArray array = jo.getJSONObject("content").getJSONArray("data");
            for (int i = 0; i < array.length(); i ++) {
                JSONObject jsonObject = array.getJSONObject(i);
                NewsEntry entry = new NewsEntry();
                entry.news_id = jsonObject.getInt("news_id");
                entry.imgurl =jsonObject.getString("imgurl");
                entry.title =jsonObject.getString("title");
                entry.create_date =jsonObject.getString("create_date");
                entry.url =jsonObject.getString("url");
                entry.clickamount =jsonObject.getInt("clickamount");
                arrayEntry.getArray().add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayEntry;
    }

    @Override
    protected ArrayEntry<NewsEntry> parseJsonArray(JSONArray ja) {
        return null;
    }

    @Override
    protected String getUrlAction() {
        return "/user/mylastestnews";
    }

}
