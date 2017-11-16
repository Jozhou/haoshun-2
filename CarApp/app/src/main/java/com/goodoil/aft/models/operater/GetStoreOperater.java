package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.models.entry.StoreEntry;
import com.corelibrary.models.entry.ArrayEntry;
import com.corelibrary.models.http.BaseArrayOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/10/9.
 */

public class GetStoreOperater extends BaseArrayOperater<StoreEntry> {

    public GetStoreOperater(Context context) {
        super(context);
    }

    public void setParams(String userlon, String userlat, int shoptype) {
        try {
            paramsObj.put("userlon", userlon);
            paramsObj.put("userlat", userlat);
            paramsObj.put("shoptype", shoptype);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayEntry<StoreEntry> parseJsonObject(JSONObject jo) {
        ArrayEntry<StoreEntry> arrayEntry = new ArrayEntry<>();
        try {
            JSONArray array = jo.getJSONObject("content").getJSONArray("data");
            for (int i = 0; i < array.length(); i ++) {
                JSONObject jsonObject = array.getJSONObject(i);
                StoreEntry entry = new StoreEntry();
                entry.shopid =jsonObject.getString("shopid");
                entry.shopname =jsonObject.getString("shopname");
                entry.image =jsonObject.getString("image");
                entry.evolution =jsonObject.getInt("evolution");
//                entry.businessstart =jsonObject.getString("businessstart");
//                entry.businessend =jsonObject.getString("businessend");
                entry.tel =jsonObject.getString("tel");
                entry.shoploacl =jsonObject.getString("shoploacl");
                entry.url =jsonObject.getString("url");
                entry.shoplon =jsonObject.optString("shoplon");
                entry.shoplat =jsonObject.optString("shoplat");
                entry.businessTime =jsonObject.getString("businessTime");
                entry.distance =jsonObject.optString("distance");

                arrayEntry.getArray().add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayEntry;
    }

    @Override
    protected ArrayEntry<StoreEntry> parseJsonArray(JSONArray ja) {
        return null;
    }

    @Override
    protected String getUrlAction() {
        return "/shop/shopmsg";
    }

}
