package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.corelibrary.models.entry.ArrayEntry;
import com.corelibrary.models.http.BaseArrayOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/10/9.
 */

public class GetYearStyleOperater extends BaseArrayOperater<VehicleItemEntry> {

    public GetYearStyleOperater(Context context) {
        super(context);
    }

    public void setParams(String brand_id, String series_id) {
        try {
            paramsObj.put("brand_id", brand_id);
            paramsObj.put("series_id", series_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayEntry<VehicleItemEntry> parseJsonObject(JSONObject jo) {
        ArrayEntry<VehicleItemEntry> arrayEntry = new ArrayEntry<>();
        try {
            JSONArray array = jo.getJSONObject("content").getJSONArray("modelyear");
            for (int i = 0; i < array.length(); i ++) {
                JSONObject jsonObject = array.getJSONObject(i);
                VehicleItemEntry entry = new VehicleItemEntry();
                entry.name =jsonObject.getString("year_style");
                arrayEntry.getArray().add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayEntry;
    }

    @Override
    protected ArrayEntry<VehicleItemEntry> parseJsonArray(JSONArray ja) {
        return null;
    }

    @Override
    protected String getUrlAction() {
        return "/vehicle/modelyear";
    }

}
