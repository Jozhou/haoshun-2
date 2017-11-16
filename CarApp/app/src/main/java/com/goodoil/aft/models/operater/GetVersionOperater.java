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

public class GetVersionOperater extends BaseArrayOperater<VehicleItemEntry> {

    public GetVersionOperater(Context context) {
        super(context);
    }

    public void setParams(String brand_id, String series_id, String year_style) {
        try {
            paramsObj.put("brand_id", brand_id);
            paramsObj.put("series_id", series_id);
            paramsObj.put("year_style", year_style);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayEntry<VehicleItemEntry> parseJsonObject(JSONObject jo) {
        ArrayEntry<VehicleItemEntry> arrayEntry = new ArrayEntry<>();
        try {
            JSONArray array = jo.getJSONObject("content").getJSONArray("car_version");
            for (int i = 0; i < array.length(); i ++) {
                JSONObject jsonObject = array.getJSONObject(i);
                VehicleItemEntry entry = new VehicleItemEntry();
                String column1 = jsonObject.getString("column1");
                if (column1 != null && column1.contains(",")) {
                    String[] strs = column1.split(",");
                    entry.carcode = strs[0];
                    entry.name = strs[1];
                }
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
        return "/vehicle/version";
    }

}
