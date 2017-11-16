package com.goodoil.aft.models.operater;

import android.content.Context;

import com.goodoil.aft.models.entry.VehicleItemEntry;
import com.corelibrary.models.http.BaseOperater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class GetBrandOperater2 extends BaseOperater {

    List<VehicleItemEntry> vehicleItemEntries = new ArrayList<>();

    public GetBrandOperater2(Context context) {
        super(context);
        setParams();
    }

    public void setParams() {
        try {
            paramsObj.put("size", 10000);
            paramsObj.put("page", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getUrlAction() {
        return "/vehicle/brand";
    }

    @Override
    protected void onParser(JSONObject jo) {
        vehicleItemEntries = new ArrayList<>();
        try {
            JSONArray array = jo.getJSONObject("content").getJSONArray("brand");
            for (int i = 0; i < array.length(); i ++) {
                JSONObject jsonObject = array.getJSONObject(i);
                VehicleItemEntry entry = new VehicleItemEntry();
                entry.id =jsonObject.getString("brand_id");
                entry.name =jsonObject.getString("brand_name");
                vehicleItemEntries.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onParser(JSONArray response) {

    }

    public List<VehicleItemEntry> getVehicleItemEntries() {
        return this.vehicleItemEntries;
    }

}
